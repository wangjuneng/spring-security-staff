package org.springframework.security.core.authority.mapping;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;

public class SimpleAuthorityMapper implements GrantedAuthoritiesMapper, InitializingBean {

    private GrantedAuthority defaultAuthority;

    private String prefix = "ROLE_";

    private boolean convertToUpperCase = false;

    private boolean convertToLowerCase = false;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.isTrue(!(convertToUpperCase && convertToLowerCase),
            "Either convertToUpperCase or convertToLowerCase can be set to true, but not both");
    }

    /**
     * Creates a mapping of the supplied authorities based on the case-conversion and prefix settings. The mapping will
     * be one-to-one unless duplicates are produced during the conversion. If a default authority has been set, this
     * will also be assigned to each mapping.
     *
     * @param authorities the original authorities
     * @return the converted set of authorities
     */
    public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {
        HashSet<GrantedAuthority> mapped = new HashSet<GrantedAuthority>(authorities.size());
        for (GrantedAuthority authority : authorities) {
            mapped.add(mapAuthority(authority.getAuthority()));
        }

        if (defaultAuthority != null) {
            mapped.add(defaultAuthority);
        }

        return mapped;
    }

    private GrantedAuthority mapAuthority(String name) {
        if (convertToUpperCase) {
            name = name.toUpperCase();
        }
        else if (convertToLowerCase) {
            name = name.toLowerCase();
        }

        if (prefix.length() > 0 && !name.startsWith(prefix)) {
            name = prefix + name;
        }

        return new SimpleGrantedAuthority(name);

    }

    /**
     * Sets the prefix which should be added to the authority name (if it doesn't already exist)
     *
     * @param prefix the prefix, typically to satisfy the behaviour of an {@code AccessDecisionVoter}.
     */
    public void setPrefix(String prefix) {
        Assert.notNull(prefix, "prefix cannot be null");
        this.prefix = prefix;
    }

    /**
     * Whether to convert the authority value to upper case in the mapping.
     *
     * @param convertToUpperCase defaults to {@code false}
     */
    public void setConvertToUpperCase(boolean convertToUpperCase) {
        this.convertToUpperCase = convertToUpperCase;
    }

    /**
     * Whether to convert the authority value to lower case in the mapping.
     *
     * @param convertToLowerCase defaults to {@code false}
     */
    public void setConvertToLowerCase(boolean convertToLowerCase) {
        this.convertToLowerCase = convertToLowerCase;
    }

    /**
     * Sets a default authority to be assigned to all users
     *
     * @param authority the name of the authority to be assigned to all users.
     */
    public void setDefaultAuthority(String authority) {
        Assert.hasText(authority, "The authority name cannot be set to an empty value");
        this.defaultAuthority = new SimpleGrantedAuthority(authority);
    }

}
