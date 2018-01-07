package org.springframework.security.access.intercept;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AfterInvocationProvider;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;

public class AfterInvocationProviderManager implements AfterInvocationManager, InitializingBean {

    // ~ Static fields/initializers
    // =====================================================================================

    protected static final Log logger = LogFactory.getLog(AfterInvocationProviderManager.class);

    private List<AfterInvocationProvider> providers;

    public void afterPropertiesSet() throws Exception {
        checkIfValidList(this.providers);
    }

    private void checkIfValidList(List<?> listToCheck) {
        if ((listToCheck == null) || (listToCheck.size() == 0)) {
            throw new IllegalArgumentException("A list of AfterInvocationProviders is required");
        }
    }

    public Object decide(Authentication authentication, Object object, Collection<ConfigAttribute> config,
        Object returnedObject) throws AccessDeniedException {
        Object result = returnedObject;

        for (AfterInvocationProvider provider : providers) {
            result = provider.decide(authentication, object, config, returnedObject);
        }

        return result;
    }

    public List<AfterInvocationProvider> getProviders() {
        return this.providers;
    }

    public void setProviders(List<?> newList) {
        checkIfValidList(newList);

        providers = new ArrayList<AfterInvocationProvider>(newList.size());

        for (Object currentObject : newList) {
            Assert.isInstanceOf(AfterInvocationProvider.class, currentObject, "AfterInvocationProvider "
                + currentObject.getClass().getName() + " must implement AfterInvocationProvider");
            providers.add((AfterInvocationProvider) currentObject);
        }
    }

    public boolean supports(ConfigAttribute attribute) {
        for (AfterInvocationProvider provider : providers) {
            if (logger.isDebugEnabled()) {
                logger.debug("Evaluating " + attribute + " against " + provider);
            }

            if (provider.supports(attribute)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Iterates through all <code>AfterInvocationProvider</code>s and ensures each can support the presented class.
     * <p>
     * If one or more providers cannot support the presented class, <code>false</code> is returned.
     *
     * @param clazz the secure object class being queries
     * @return if the <code>AfterInvocationProviderManager</code> can support the secure object class, which requires
     *         every one of its <code>AfterInvocationProvider</code>s to support the secure object class
     */
    public boolean supports(Class<?> clazz) {
        for (AfterInvocationProvider provider : providers) {
            if (!provider.supports(clazz)) {
                return false;
            }
        }

        return true;
    }
}
