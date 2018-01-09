package org.springframework.security.acls.model;

import java.util.List;

import org.springframework.security.core.Authentication;

/**
 * Strategy interface that provides an ability to determine the {@link Sid} instances
 * applicable for an {@link Authentication}.
 *
 * @author Ben Alex
 */
public interface SidRetrievalStrategy {

    List<Sid> getSids(Authentication authentication);
}
