package org.springframework.security.provisioning;

import org.springframework.security.core.userdetails.UserDetails;

public interface MutableUserDetails extends UserDetails {
    void setPassword(String password);
}
