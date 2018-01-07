package org.springframework.security.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.springframework.core.io.AbstractResource;
import org.springframework.util.Assert;

public class InMemoryResource extends AbstractResource {

    // ~ Instance fields
    // ================================================================================================

    private final byte[] source;

    private final String description;

    public InMemoryResource(byte[] source, String description) {
        Assert.notNull(source, "source cannot be null");
        this.source = source;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(source);
    }
    
    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object res) {
        if (!(res instanceof InMemoryResource)) {
            return false;
        }

        return Arrays.equals(source, ((InMemoryResource) res).source);
    }

}
