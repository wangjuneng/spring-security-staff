package org.springframework.security.util;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

public class SimpleMethodInvocation implements MethodInvocation {
    private Method method;

    private Object[] arguments;

    private Object targetObject;

    // ~ Constructors
    // ===================================================================================================

    public SimpleMethodInvocation(Object targetObject, Method method, Object... arguments) {
        this.targetObject = targetObject;
        this.method = method;
        this.arguments = arguments == null ? new Object[0] : arguments;
    }

    public SimpleMethodInvocation() {
    }
    
    
    public Object[] getArguments() {
        return arguments;
    }

    public Method getMethod() {
        return method;
    }

    public AccessibleObject getStaticPart() {
        throw new UnsupportedOperationException("mock method not implemented");
    }

    public Object getThis() {
        return targetObject;
    }

    public Object proceed() throws Throwable {
        throw new UnsupportedOperationException("mock method not implemented");
    }
}
