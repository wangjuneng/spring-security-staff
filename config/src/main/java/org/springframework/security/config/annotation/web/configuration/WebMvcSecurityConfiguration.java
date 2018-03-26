package org.springframework.security.config.annotation.web.configuration;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.core.MethodParameter;
import org.springframework.expression.BeanResolver;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.security.web.method.annotation.CsrfTokenArgumentResolver;
import org.springframework.security.web.servlet.support.csrf.CsrfRequestDataValueProcessor;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.support.RequestDataValueProcessor;

public class WebMvcSecurityConfiguration implements WebMvcConfigurer,ApplicationContextAware{
    private BeanResolver beanResolver;
    

    public void addArgumentResolvers(java.util.List<HandlerMethodArgumentResolver> resolvers) {
        
        AuthenticationPrincipalArgumentResolver authenResolver = new AuthenticationPrincipalArgumentResolver();
        authenResolver.setBeanResolver(beanResolver);
        resolvers.add(authenResolver);
        resolvers.add(new AuthenticationPrincipalArgumentResolver());
        
        resolvers.add(new CsrfTokenArgumentResolver());
    }
    
    
    @Bean
    public RequestDataValueProcessor requestDataValueProcessor() {
        return new CsrfRequestDataValueProcessor();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.beanResolver = new BeanFactoryResolver(applicationContext.getAutowireCapableBeanFactory());
    }
}
