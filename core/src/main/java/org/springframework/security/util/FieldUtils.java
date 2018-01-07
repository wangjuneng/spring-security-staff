package org.springframework.security.util;

import java.lang.reflect.Field;

import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

/**
 * Offers static methods for directly manipulating fields. <br>
 * 
 * @author wang.jun<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年1月6日 <br>
 * @since V7.3<br>
 * @see org.springframework.security.util <br>
 */
public final class FieldUtils {

    public static Field getField(Class<?> clazz, String fieldName) throws IllegalStateException {
        Assert.notNull(clazz, "Class required");
        Assert.notNull(fieldName, "Field name required");

        try {
            return clazz.getDeclaredField(fieldName);
        }
        catch (NoSuchFieldException nsf) {
            if (clazz.getSuperclass() != null) {
                return getField(clazz.getSuperclass(), fieldName);
            }

            throw new IllegalStateException("Could not locate field '" + fieldName + "' on class " + clazz);
        }
    }

    /**
     * Returns the value of a (nested) field on a bean. Intended for testing.
     * 
     * @param bean the object
     * @param fieldName the field name, with "." separating nested properties
     * @return the value of the nested field
     */
    public static Object getFieldValue(Object bean, String fieldName) throws IllegalAccessException {
        Assert.notNull(bean, "Bean cannot be null");
        Assert.hasText(fieldName, "Field name required");

        String[] nestedFields = StringUtils.tokenizeToStringArray(fieldName, ".");
        Class<?> componentClass = bean.getClass();
        Object value = bean;

        for (String nestedField : nestedFields) {
            Field field = getField(componentClass, nestedField);
            field.setAccessible(true);
            value = field.get(value);
            if (value != null) {
                componentClass = value.getClass();
            }
        }

        return value;
    }

    public static Object getProtectedFieldValue(String protectedField, Object object) {
        Field field = FieldUtils.getField(object.getClass(), protectedField);

        try {
            field.setAccessible(true);

            return field.get(object);
        }
        catch (Exception ex) {
            ReflectionUtils.handleReflectionException(ex);

            return null; // unreachable - previous line throws exception
        }
    }

    public static void setProtectedFieldValue(String protectedField, Object object, Object newValue) {
        Field field = FieldUtils.getField(object.getClass(), protectedField);

        try {
            field.setAccessible(true);
            field.set(object, newValue);
        }
        catch (Exception ex) {
            ReflectionUtils.handleReflectionException(ex);
        }
    }

}
