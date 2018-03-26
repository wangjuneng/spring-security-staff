package org.springframework.security.web.access.expression;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;

public class WebExpressionConfigAttribute implements ConfigAttribute, EvaluationContextPostProcessor<FilterInvocation> {

    private final Expression authorizeExpression;

    private final EvaluationContextPostProcessor<FilterInvocation> postProcessor;

    public WebExpressionConfigAttribute(Expression authorizeExpression,
        EvaluationContextPostProcessor<FilterInvocation> postProcessor) {
        this.authorizeExpression = authorizeExpression;
        this.postProcessor = postProcessor;
    }

    Expression getAuthorizeExpression() {
        return this.authorizeExpression;
    }

    @Override
    public EvaluationContext postProcess(EvaluationContext context, FilterInvocation fi) {
        return this.postProcessor == null ? context : this.postProcessor.postProcess(context, fi);
    }

    @Override
    public String getAttribute() {
        return null;
    }

    @Override
    public String toString() {
        return this.authorizeExpression.getExpressionString();
    }

}
