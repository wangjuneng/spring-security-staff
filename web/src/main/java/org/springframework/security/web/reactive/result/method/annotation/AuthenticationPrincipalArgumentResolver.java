package org.springframework.security.web.reactive.result.method.annotation;

import java.lang.annotation.Annotation;

import org.springframework.core.MethodParameter;
import org.springframework.core.ReactiveAdapter;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolverSupport;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

public class AuthenticationPrincipalArgumentResolver extends HandlerMethodArgumentResolverSupport {

    private ExpressionParser parser = new SpelExpressionParser();

    private BeanResolver beanResolver;
    
    public AuthenticationPrincipalArgumentResolver(ReactiveAdapterRegistry adapterRegistry) {
        super(adapterRegistry);
    }
    
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return findMethodAnnotation(AuthenticationPrincipal.class, parameter) != null;
    }

    @Override
    public Mono<Object> resolveArgument(MethodParameter parameter, BindingContext bindingContext,
        ServerWebExchange exchange) {
        ReactiveAdapter adapter = getAdapterRegistry().getAdapter(parameter.getParameterType());
        return exchange.getPrincipal()
            .ofType(Authentication.class)
            .flatMap( a -> {
                Object p = resolvePrincipal(parameter, a.getPrincipal());
                Mono<Object> principal = Mono.justOrEmpty(p);
                return adapter == null ? principal : Mono.just(adapter.fromPublisher(principal));
            });
    }
    
    private Object resolvePrincipal(MethodParameter parameter, Object principal) {
        AuthenticationPrincipal authPrincipal = findMethodAnnotation(
            AuthenticationPrincipal.class, parameter);

        String expressionToParse = authPrincipal.expression();
        if (StringUtils.hasLength(expressionToParse)) {
            StandardEvaluationContext context = new StandardEvaluationContext();
            context.setRootObject(principal);
            context.setVariable("this", principal);
            context.setBeanResolver(beanResolver);

            Expression expression = this.parser.parseExpression(expressionToParse);
            principal = expression.getValue(context);
        }

        return principal;
    }
    
    
    private <T extends Annotation> T findMethodAnnotation(Class<T> annotationClass,MethodParameter parameter){
        T annotation = parameter.getParameterAnnotation(annotationClass);
        if(annotation != null){
            return annotation;
        }
        
        Annotation[] annotationsToSearch = parameter.getParameterAnnotations();
        for (Annotation toSearch : annotationsToSearch) {
            annotation = AnnotationUtils.findAnnotation(toSearch.annotationType(),
                annotationClass);
            if (annotation != null) {
                return annotation;
            }
        }
        return null;
    }

}
