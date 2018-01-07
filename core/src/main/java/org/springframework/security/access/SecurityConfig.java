package org.springframework.security.access;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class SecurityConfig implements ConfigAttribute {

    // ~ Instance fields
    // ================================================================================================

    private final String attrib;

    // ~ Constructors
    // ===================================================================================================

    public SecurityConfig(String config) {
        Assert.hasText(config, "You must provide a configuration attribute");
        this.attrib = config;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ConfigAttribute) {
            ConfigAttribute attr = (ConfigAttribute) obj;

            return this.attrib.equals(attr.getAttribute());
        }

        return false;
    }

    @Override
    public String getAttribute() {
        return this.attrib;
    }

    @Override
    public int hashCode() {
        return this.attrib.hashCode();
    }

    public static List<ConfigAttribute> createListFromCommaDelimitedString(String access) {
        return createList(StringUtils.commaDelimitedListToStringArray(access));
    }

    public static List<ConfigAttribute> createList(String... attributeNames) {
        Assert.notNull(attributeNames, "You must supply an array of attribute names");
        List<ConfigAttribute> attributes = new ArrayList<ConfigAttribute>(attributeNames.length);

        for (String attribute : attributeNames) {
            attributes.add(new SecurityConfig(attribute.trim()));
        }

        return attributes;
    }

}
