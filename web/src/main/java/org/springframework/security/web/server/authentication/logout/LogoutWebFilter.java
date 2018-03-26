/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.security.web.server.authentication.logout;

import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

/**
 * If the request matches, logs an authenticated user out by delegating to a
 * {@link ServerLogoutHandler}.
 *
 * @author Rob Winch
 * @since 5.0
 */
public class LogoutWebFilter implements WebFilter {
	private AnonymousAuthenticationToken anonymousAuthenticationToken = new AnonymousAuthenticationToken("key", "anonymous",
		AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));

	private ServerLogoutHandler serverLogoutHandler = new SecurityContextServerLogoutHandler();

	private ServerLogoutSuccessHandler serverLogoutSuccessHandler = new RedirectServerLogoutSuccessHandler();

	private ServerWebExchangeMatcher requiresLogout = ServerWebExchangeMatchers
		.pathMatchers(HttpMethod.POST, "/logout");

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		return this.requiresLogout.matches(exchange)
			.filter( result -> result.isMatch())
			.switchIfEmpty(chain.filter(exchange).then(Mono.empty()))
			.map(result -> exchange)
			.flatMap(this::flatMapAuthentication)
			.flatMap( authentication -> {
				WebFilterExchange webFilterExchange = new WebFilterExchange(exchange,chain);
				return logout(webFilterExchange, authentication);
			});
	}

	private Mono<Authentication> flatMapAuthentication(ServerWebExchange exchange) {
		return exchange.getPrincipal()
			.cast(Authentication.class)
			.defaultIfEmpty(this.anonymousAuthenticationToken);
	}

	private Mono<Void> logout(WebFilterExchange webFilterExchange, Authentication authentication) {
		return this.serverLogoutHandler.logout(webFilterExchange, authentication)
			.then(this.serverLogoutSuccessHandler.onLogoutSuccess(webFilterExchange, authentication))
			.subscriberContext(ReactiveSecurityContextHolder.clearContext());
	}

	/**
	 * Sets the {@link ServerLogoutSuccessHandler}. The default is {@link RedirectServerLogoutSuccessHandler}.
	 * @param serverLogoutSuccessHandler the handler to use
	 */
	public void setServerLogoutSuccessHandler(
		ServerLogoutSuccessHandler serverLogoutSuccessHandler) {
		Assert.notNull(serverLogoutSuccessHandler, "serverLogoutSuccessHandler cannot be null");
		this.serverLogoutSuccessHandler = serverLogoutSuccessHandler;
	}

	public void setServerLogoutHandler(ServerLogoutHandler serverLogoutHandler) {
		Assert.notNull(serverLogoutHandler, "logoutHandler must not be null");
		this.serverLogoutHandler = serverLogoutHandler;
	}

	public void setRequiresLogout(ServerWebExchangeMatcher serverWebExchangeMatcher) {
		Assert.notNull(serverWebExchangeMatcher, "serverWebExchangeMatcher must not be null");
		this.requiresLogout = serverWebExchangeMatcher;
	}
}
