package org.springframework.security.authentication;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.util.Assert;

/**
 * An {@link AuthenticationProvider} implementation that validates {@link AnonymousAuthenticationToken}s.
 * <p>
 * To be successfully validated, the {@link AnonymousAuthenticationToken#getKeyHash()} must match this class'
 * {@link #getKey()}.
 *
 * @author Ben Alex
 */
public class AnonymousAuthenticationProvider implements AuthenticationProvider, MessageSourceAware {

    // ~ Instance fields
    // ================================================================================================

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    private String key;

    public AnonymousAuthenticationProvider(String key) {
        Assert.hasLength(key, "A Key is required");
        this.key = key;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }

        if (this.key.hashCode() != ((AnonymousAuthenticationToken) authentication).getKeyHash()) {
            throw new BadCredentialsException(messages.getMessage("AnonymousAuthenticationProvider.incorrectKey",
                "The presented AnonymousAuthenticationToken does not contain the expected key"));
        }

        return authentication;
    }
    
    public void setMessageSource(MessageSource messageSource) {
        Assert.notNull(messageSource, "messageSource cannot be null");
        this.messages = new MessageSourceAccessor(messageSource);
    }

    public boolean supports(Class<?> authentication) {
        return (AnonymousAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
