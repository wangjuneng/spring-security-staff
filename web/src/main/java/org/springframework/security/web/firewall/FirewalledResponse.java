package org.springframework.security.web.firewall;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class FirewalledResponse extends HttpServletResponseWrapper {

    private static final Pattern CR_OR_LF = Pattern.compile("\\r|\\n");
    private static final String LOCATION_HEADER = "Location";
    private static final String SET_COOKIE_HEADER = "Set-Cookie";
    
    public FirewalledResponse(HttpServletResponse response) {
        super(response);
    }
    
    public void sendRedirect(String location) throws IOException{
        validateCrlf(LOCATION_HEADER, location);
        super.sendRedirect(location);
    }
    
    @Override
    public void setHeader(String name, String value) {
        validateCrlf(name, value);
        super.setHeader(name, value);
    }

    @Override
    public void addHeader(String name, String value) {
        validateCrlf(name, value);
        super.addHeader(name, value);
    }
    
    @Override
    public void addCookie(Cookie cookie) {
        if(cookie != null) {
            validateCrlf(SET_COOKIE_HEADER, cookie.getName());
            validateCrlf(SET_COOKIE_HEADER, cookie.getValue());
            validateCrlf(SET_COOKIE_HEADER, cookie.getPath());
            validateCrlf(SET_COOKIE_HEADER, cookie.getDomain());
            validateCrlf(SET_COOKIE_HEADER, cookie.getComment());
        }
        super.addCookie(cookie);
    }

    void validateCrlf(String name, String value) {
        if (hasCrlf(name) || hasCrlf(value)) {
            throw new IllegalArgumentException(
                    "Invalid characters (CR/LF) in header " + name);
        }
    }
    
    private boolean hasCrlf(String value) {
        return value != null && CR_OR_LF.matcher(value).find();
    }

}
