package org.springframework.security.web.header;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Contract for writing headers to a {@link HttpServletResponse}
 *
 * @see HeaderWriterFilter
 * @author Marten Deinum
 * @author Rob Winch
 * @since 3.2
 */
public interface HeaderWriter {
    void writeHeaders(HttpServletRequest request, HttpServletResponse response);
}
