package org.springframework.security.access.annotation;

import java.lang.annotation.Annotation;
import java.util.Collection;

import org.springframework.security.access.ConfigAttribute;

/**
 * Strategy to process a custom security annotation to extract the relevant {@code ConfigAttribute}s for securing a
 * method.
 * <p>
 * Used by {@code SecuredAnnotationSecurityMetadataSource}.
 *
 * @author Luke Taylor
 */
public interface AnnotationMetadataExtractor<A extends Annotation> {
    Collection<? extends ConfigAttribute> extractAttributes(A securityAnnotation);
}
