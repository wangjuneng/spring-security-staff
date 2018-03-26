package org.springframework.security.web.access.channel;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.Assert;

public class InsecureChannelProcessor implements InitializingBean, ChannelProcessor {

    private ChannelEntryPoint entryPoint = new RetryWithHttpEntryPoint();

    private String insecureKeyword = "REQUIRES_INSECUE_CHANNEL";

    public void decide(FilterInvocation invocation, Collection<ConfigAttribute> config) throws IOException,
        ServletException {
        if ((invocation == null) || (config == null)) {
            throw new IllegalArgumentException("Nulls cannot be provided");
        }

        for (ConfigAttribute attribute : config) {
            if (supports(attribute)) {
                if (invocation.getHttpRequest().isSecure()) {
                    entryPoint.commence(invocation.getRequest(), invocation.getResponse());
                }
            }
        }
    }

    public ChannelEntryPoint getEntryPoint() {
        return entryPoint;
    }

    public String getInsecureKeyword() {
        return insecureKeyword;
    }

    public void setEntryPoint(ChannelEntryPoint entryPoint) {
        this.entryPoint = entryPoint;
    }

    public void setInsecureKeyword(String secureKeyword) {
        this.insecureKeyword = secureKeyword;
    }

    public boolean supports(ConfigAttribute attribute) {
        return (attribute != null) && (attribute.getAttribute() != null)
            && attribute.getAttribute().equals(getInsecureKeyword());
    }

    // ~ Methods
    // ========================================================================================================

    public void afterPropertiesSet() throws Exception {
        Assert.hasLength(insecureKeyword, "insecureKeyword required");
        Assert.notNull(entryPoint, "entryPoint required");
    }

}
