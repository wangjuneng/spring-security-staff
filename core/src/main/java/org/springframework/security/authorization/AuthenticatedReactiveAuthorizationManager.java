package org.springframework.security.authorization;

import org.springframework.security.core.Authentication;

import reactor.core.publisher.Mono;

public class AuthenticatedReactiveAuthorizationManager<T> implements ReactiveAuthorizationManager<T> {
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, T object) {
        return authentication.map(a -> new AuthorizationDecision(a.isAuthenticated())).defaultIfEmpty(
            new AuthorizationDecision(false));
    }

    public static <T> AuthenticatedReactiveAuthorizationManager<T> authenticated() {
        return new AuthenticatedReactiveAuthorizationManager<>();
    }

    private AuthenticatedReactiveAuthorizationManager() {
    }
}
