package org.springframework.security.access.intercept;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.util.Assert;

/**
 * An {@link AuthenticationProvider} implementation that can authenticate a {@link RunAsUserToken}.
 * <P>
 * Configured in the bean context with a key that should match the key used by adapters to generate the
 * <code>RunAsUserToken</code>. It treats as valid any <code>RunAsUserToken</code> instance presenting a hash code that
 * matches the <code>RunAsImplAuthenticationProvider</code>-configured key.
 * </p>
 * <P>
 * If the key does not match, a <code>BadCredentialsException</code> is thrown.
 * </p>
 */
public class RunAsImplAuthenticationProvider implements InitializingBean, AuthenticationProvider, MessageSourceAware {

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    private String key;

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(key, "A Key is required and should match that configured for the RunAsManagerImpl");
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RunAsUserToken token = (RunAsUserToken) authentication;

        if (token.getKeyHash() == key.hashCode()) {
            return authentication;
        }
        else {
            throw new BadCredentialsException(messages.getMessage("RunAsImplAuthenticationProvider.incorrectKey",
                "The presented RunAsUserToken does not contain the expected key"));
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    public boolean supports(Class<?> authentication) {
        return RunAsUserToken.class.isAssignableFrom(authentication);
    }

}
