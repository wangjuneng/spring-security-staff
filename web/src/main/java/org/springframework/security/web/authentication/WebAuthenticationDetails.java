package org.springframework.security.web.authentication;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <Description> A holder of selected HTTP details related to a web authentication request. <br>
 * 
 * @author wang.jun<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年2月11日 <br>
 * @since V7.3<br>
 * @see org.springframework.security.web.authentication <br>
 */
public class WebAuthenticationDetails implements Serializable {

    /**
     * serialVersionUID <br>
     */
    private static final long serialVersionUID = 1L;

    private final String remoteAddress;

    private final String sessionId;

    /**
     * Records the remote address and will also set the session Id if a session already exists (it won't create one).
     *
     * @param request that the authentication request was received from
     */
    public WebAuthenticationDetails(HttpServletRequest request) {
        this.remoteAddress = request.getRemoteAddr();

        HttpSession session = request.getSession(false);
        this.sessionId = (session != null) ? session.getId() : null;
    }

    /**
     * Constructor to add Jackson2 serialize/deserialize support
     *
     * @param remoteAddress remote address of current request
     * @param sessionId session id
     */
    private WebAuthenticationDetails(final String remoteAddress, final String sessionId) {
        this.remoteAddress = remoteAddress;
        this.sessionId = sessionId;
    }

    // ========================================================================================================

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof WebAuthenticationDetails) {
            WebAuthenticationDetails rhs = (WebAuthenticationDetails) obj;

            if ((remoteAddress == null) && (rhs.getRemoteAddress() != null)) {
                return false;
            }

            if ((remoteAddress != null) && (rhs.getRemoteAddress() == null)) {
                return false;
            }

            if (remoteAddress != null) {
                if (!remoteAddress.equals(rhs.getRemoteAddress())) {
                    return false;
                }
            }

            if ((sessionId == null) && (rhs.getSessionId() != null)) {
                return false;
            }

            if ((sessionId != null) && (rhs.getSessionId() == null)) {
                return false;
            }

            if (sessionId != null) {
                if (!sessionId.equals(rhs.getSessionId())) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    /**
     * Indicates the TCP/IP address the authentication request was received from.
     *
     * @return the address
     */
    public String getRemoteAddress() {
        return remoteAddress;
    }

    /**
     * Indicates the <code>HttpSession</code> id the authentication request was received from.
     *
     * @return the session ID
     */
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public int hashCode() {
        int code = 7654;

        if (this.remoteAddress != null) {
            code = code * (this.remoteAddress.hashCode() % 7);
        }

        if (this.sessionId != null) {
            code = code * (this.sessionId.hashCode() % 7);
        }

        return code;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(": ");
        sb.append("RemoteIpAddress: ").append(this.getRemoteAddress()).append("; ");
        sb.append("SessionId: ").append(this.getSessionId());

        return sb.toString();
    }
}
