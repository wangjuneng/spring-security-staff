package org.springframework.security.util;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.util.Assert;

public final class MethodInvocationUtils {

    public static MethodInvocation create(Object object, String methodName, Object... args) {
        Assert.notNull(object, "Object required");

        Class<?>[] classArgs = null;
        if (args != null) {
            classArgs = new Class<?>[args.length];

            for (int i = 0; i < args.length; i++) {
                classArgs[i] = args[i].getClass();
            }
        }

        Class<?> target = AopUtils.getTargetClass(object);
        if (object instanceof Advised) {
            Advised a = (Advised) object;
            if (!a.isProxyTargetClass()) {
                Class<?>[] possibleInterfaces = a.getProxiedInterfaces();
                for (Class<?> possibleInterface : possibleInterfaces) {
                    try {
                        possibleInterface.getMethod(methodName, classArgs);
                        // to get here means no exception happened
                        target = possibleInterface;
                        break;
                    }
                    catch (Exception ignored) {
                        // try the next one
                    }
                }
            }
        }

        return createFromClass(object, target, methodName, classArgs, args);
    }

    /**
     * Generates a <code>MethodInvocation</code> for the specified <code>methodName</code> on the passed class. If a
     * method with this name, taking no arguments does not exist, it will check through the declared methods on the
     * class, until one is found matching the supplied name. If more than one method name matches, an
     * <tt>IllegalArgumentException</tt> will be raised.
     *
     * @param clazz the class of object that will be used to find the relevant <code>Method</code>
     * @param methodName the name of the method to find
     * @return a <code>MethodInvocation</code>, or <code>null</code> if there was a problem
     */
    public static MethodInvocation createFromClass(Class<?> clazz, String methodName) {
        MethodInvocation mi = createFromClass(null, clazz, methodName, null, null);

        if (mi == null) {
            for (Method m : clazz.getDeclaredMethods()) {
                if (m.getName().equals(methodName)) {
                    if (mi != null) {
                        throw new IllegalArgumentException("The class " + clazz + " has more than one method named"
                            + " '" + methodName + "'");
                    }
                    mi = new SimpleMethodInvocation(null, m);
                }
            }
        }

        return mi;
    }

    /**
     * Generates a <code>MethodInvocation</code> for specified <code>methodName</code> on the passed class, using the
     * <code>args</code> to locate the method.
     *
     * @param targetObject the object being invoked
     * @param clazz the class of object that will be used to find the relevant <code>Method</code>
     * @param methodName the name of the method to find
     * @param classArgs arguments that are required to locate the relevant method signature
     * @param args the actual arguments that should be passed to SimpleMethodInvocation
     * @return a <code>MethodInvocation</code>, or <code>null</code> if there was a problem
     */
    public static MethodInvocation createFromClass(Object targetObject, Class<?> clazz, String methodName,
        Class<?>[] classArgs, Object[] args) {
        Assert.notNull(clazz, "Class required");
        Assert.hasText(methodName, "MethodName required");

        Method method;

        try {
            method = clazz.getMethod(methodName, classArgs);
        }
        catch (NoSuchMethodException e) {
            return null;
        }

        return new SimpleMethodInvocation(targetObject, method, args);
    }
}
