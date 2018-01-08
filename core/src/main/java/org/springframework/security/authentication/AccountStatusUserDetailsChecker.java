package org.springframework.security.authentication;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

public class AccountStatusUserDetailsChecker implements UserDetailsChecker {
    protected final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    public void check(UserDetails user){
        if(!user.isAccountNonLocked()){
            throw new LockedException(messages.getMessage(
                "AccountStatusUserDetailsChecker.locked", "User account is locked"));
        }
        
        if(!user.isEnabled()){
            throw new DisabledException(messages.getMessage("AccountStatusUserDetailsChecker.disabled", "User is disabled"));
        }
        
        if (!user.isAccountNonExpired()) {
            throw new AccountExpiredException(
                    messages.getMessage("AccountStatusUserDetailsChecker.expired",
                            "User account has expired"));
        }
        
        if (!user.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(messages.getMessage(
                    "AccountStatusUserDetailsChecker.credentialsExpired",
                    "User credentials have expired"));
        }
    }
}
