package org.springframework.security.authorization;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;

import reactor.core.publisher.Mono;

public interface ReactiveAuthorizationManager<T> {
    Mono<AuthorizationDecision> check(Mono<Authentication> authentication, T object);

    default Mono<Void> verify(Mono<Authentication> authentication, T object) {
        return check(authentication, object)
            .filter( d -> d.isGranted())
            .switchIfEmpty( Mono.error(new AccessDeniedException("Access Denied")) )
            .flatMap( d -> Mono.empty() );
    }
}
