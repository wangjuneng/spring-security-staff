package org.springframework.security.core.parameters;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.PrioritizedParameterNameDiscoverer;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
/**
 * Spring Security's default {@link ParameterNameDiscoverer} which tries a number of {@link ParameterNameDiscoverer}
 * depending on what is found on the classpath.
 * <ul>
 * <li>Will use an instance of {@link AnnotationParameterNameDiscoverer} with {@link P} as a valid annotation. If,
 * Spring Data is on the classpath will also add Param annotation.</li>
 * <li>If Spring 4 is on the classpath, then DefaultParameterNameDiscoverer is added. This attempts to use JDK 8
 * information first and falls back to {@link LocalVariableTableParameterNameDiscoverer}.</li>
 * <li>If Spring 4 is not on the classpath, then {@link LocalVariableTableParameterNameDiscoverer} is added directly.</li>
 * </ul>
 *
 * @see AnnotationParameterNameDiscoverer
 * @author Rob Winch
 * @since 3.2
 */
public class DefaultSecurityParameterNameDiscoverer extends PrioritizedParameterNameDiscoverer {
    private final Log logger = LogFactory.getLog(getClass());

    private static final String DATA_PARAM_CLASSNAME = "org.springframework.data.repository.query.Param";

    private static final boolean DATA_PARAM_PRESENT = ClassUtils.isPresent(DATA_PARAM_CLASSNAME,
        DefaultSecurityParameterNameDiscoverer.class.getClassLoader());

    /**
     * Creates a new instance with only the default {@link ParameterNameDiscoverer} instances.
     */
    public DefaultSecurityParameterNameDiscoverer() {
        this(Collections.<ParameterNameDiscoverer> emptyList());
    }

    /**
     * Creates a new instance that first tries the passed in {@link ParameterNameDiscoverer} instances.
     * 
     * @param parameterNameDiscovers the {@link ParameterNameDiscoverer} before trying the defaults. Cannot be null.
     */
    @SuppressWarnings("unchecked")
    public DefaultSecurityParameterNameDiscoverer(List<? extends ParameterNameDiscoverer> parameterNameDiscovers) {
        Assert.notNull(parameterNameDiscovers, "parameterNameDiscovers cannot be null");
        for (ParameterNameDiscoverer discover : parameterNameDiscovers) {
            addDiscoverer(discover);
        }

        Set<String> annotationClassesToUse = new HashSet<String>(2);
        annotationClassesToUse.add("org.springframework.security.access.method.P");
        annotationClassesToUse.add(P.class.getName());
        if (DATA_PARAM_PRESENT) {
            annotationClassesToUse.add(DATA_PARAM_CLASSNAME);
        }

        addDiscoverer(new AnnotationParameterNameDiscoverer(annotationClassesToUse));
        addDiscoverer(new DefaultParameterNameDiscoverer());
    }
}
