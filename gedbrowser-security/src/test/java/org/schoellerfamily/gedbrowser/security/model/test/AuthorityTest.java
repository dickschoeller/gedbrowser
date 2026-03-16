package org.schoellerfamily.gedbrowser.security.model.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.datamodel.users.UserRoleName;
import org.schoellerfamily.gedbrowser.security.model.Authority;

/**
 * Contains tests for authority.
 *
 * @author Richard Schoeller
 */
class AuthorityTest {
    @Test
    void testDefault() {
        final Authority authority = Authority.builder().build();
        assertNull(authority.getAuthority(), "Should be null");
    }

    @Test
    void testDefaultUserRoleName() {
        final Authority authority = Authority.builder().build();
        assertNull(authority.getUserRoleName(), "Should be null");
    }

    @Test
    void testSetGet() {
        final Authority authority = Authority.builder()
                .userRoleName(UserRoleName.USER)
                .build();
        assertEquals("ROLE_USER", authority.getAuthority(), "Should be USER");
    }
}
