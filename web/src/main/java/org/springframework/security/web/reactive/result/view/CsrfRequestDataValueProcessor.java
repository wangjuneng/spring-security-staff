package org.springframework.security.web.reactive.result.view;

import java.util.Collections;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.web.reactive.result.view.RequestDataValueProcessor;
import org.springframework.web.server.ServerWebExchange;


public class CsrfRequestDataValueProcessor implements RequestDataValueProcessor {

    private static final Pattern DISABLE_CSRF_TOKEN_PATTERN = Pattern.compile("(?i)^(GET|HEAD|TRACE|OPTIONS)$");
    
    private static final String DISABLE_CSRF_TOKEN_ATTR = "DISABLE_CSRF_TOKEN_ATTR";

    @Override
    public String processAction(ServerWebExchange exchange, String action, String httpMethod) {
        if (httpMethod != null && DISABLE_CSRF_TOKEN_PATTERN.matcher(httpMethod).matches()) {
            exchange.getAttributes().put(DISABLE_CSRF_TOKEN_ATTR, Boolean.TRUE);
        }
        else {
            exchange.getAttributes().remove(DISABLE_CSRF_TOKEN_ATTR);
        }
        return action;
    }

    @Override
    public String processFormFieldValue(ServerWebExchange exchange, String name, String value, String type) {
        return value;
    }

    @Override
    public Map<String, String> getExtraHiddenFields(ServerWebExchange exchange) {
        if (Boolean.TRUE.equals(exchange.getAttribute(DISABLE_CSRF_TOKEN_ATTR))) {
            exchange.getAttributes().remove(DISABLE_CSRF_TOKEN_ATTR);
            return Collections.emptyMap();
        }
        CsrfToken token = exchange.getAttribute(CsrfToken.class.getName());
        if(token == null) {
            return Collections.emptyMap();
        }
        return Collections.singletonMap(token.getParameterName(), token.getToken());
    }

    @Override
    public String processUrl(ServerWebExchange exchange, String url) {
        return url;
    }
    
     

}
