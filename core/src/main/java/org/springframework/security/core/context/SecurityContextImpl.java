package org.springframework.security.core.context;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityCoreVersion;

public class SecurityContextImpl implements SecurityContext {

    /**
     * serialVersionUID <br>
     */
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private Authentication authentication;
    
    public SecurityContextImpl(){}
    
    public SecurityContextImpl(Authentication authentication){
        this.authentication = authentication;
    }
    
    @Override
    public Authentication getAuthentication() {
        return this.authentication;
    }

    @Override
    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }
    
    
    // ~ Methods
    // ========================================================================================================

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SecurityContextImpl) {
            SecurityContextImpl test = (SecurityContextImpl) obj;

            if ((this.getAuthentication() == null) && (test.getAuthentication() == null)) {
                return true;
            }

            if ((this.getAuthentication() != null) && (test.getAuthentication() != null)
                    && this.getAuthentication().equals(test.getAuthentication())) {
                return true;
            }
        }

        return false;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());

        if (this.authentication == null) {
            sb.append(": Null authentication");
        }
        else {
            sb.append(": Authentication: ").append(this.authentication);
        }

        return sb.toString();
    }
    

    @Override
    public int hashCode() {
        if (this.authentication == null) {
            return -1;
        }
        else {
            return this.authentication.hashCode();
        }
    }

}
