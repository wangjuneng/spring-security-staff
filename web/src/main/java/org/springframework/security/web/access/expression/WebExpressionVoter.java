package org.springframework.security.web.access.expression;

import java.util.Collection;

import org.springframework.expression.EvaluationContext;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.expression.ExpressionUtils;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

public class WebExpressionVoter implements AccessDecisionVoter<FilterInvocation> {
    private SecurityExpressionHandler<FilterInvocation> expressionHandler = new DefaultWebSecurityExpressionHandler();

    private WebExpressionConfigAttribute findConfigAttribute(Collection<ConfigAttribute> attributes) {
        for (ConfigAttribute attribute : attributes) {
            if (attribute instanceof WebExpressionConfigAttribute) {
                return (WebExpressionConfigAttribute) attribute;
            }
        }
        return null;
    }

    public int vote(Authentication authentication, FilterInvocation fi, Collection<ConfigAttribute> attributes) {
        assert authentication != null;
        assert fi != null;
        assert attributes != null;

        WebExpressionConfigAttribute weca = findConfigAttribute(attributes);

        if (weca == null) {
            return ACCESS_ABSTAIN;
        }

        EvaluationContext ctx = expressionHandler.createEvaluationContext(authentication, fi);
        ctx = weca.postProcess(ctx, fi);

        return ExpressionUtils.evaluateAsBoolean(weca.getAuthorizeExpression(), ctx) ? ACCESS_GRANTED : ACCESS_DENIED;
    }

    public boolean supports(ConfigAttribute attribute) {
        return attribute instanceof WebExpressionConfigAttribute;
    }

    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    public void setExpressionHandler(SecurityExpressionHandler<FilterInvocation> expressionHandler) {
        this.expressionHandler = expressionHandler;
    }
}
