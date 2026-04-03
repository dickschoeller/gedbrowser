package org.schoellerfamily.gedbrowser.security.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.security.model.Authority;
import org.schoellerfamily.gedbrowser.security.service.impl.AuthorityServiceImpl;

/**
 * Unit tests for AuthorityServiceImpl.
 */
@SuppressWarnings({"MissingJavadocType", "MissingJavadocMethod", "MagicNumber", "LineLength"})
class AuthorityServiceImplTest {
    /** */
    private final AuthorityServiceImpl authorityService = new AuthorityServiceImpl();

    @Test
    void testFindByIdReturnsCorrectAuthority() {
        final List<Authority> authorities = authorityService.findById(0L);
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertEquals("USER", authorities.get(0).getUserRoleName().name());
    }

    @Test
    void testFindByNameReturnsCorrectAuthority() {
        final List<Authority> authorities = authorityService.findByname("ADMIN");
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertEquals("ADMIN", authorities.get(0).getUserRoleName().name());
    }

    @Test
    void testFindByIdWithInvalidIdThrowsException() {
        // Use a clearly out-of-range value for id
        final long invalidId = 100L;
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> authorityService.findById(invalidId));
    }

    @Test
    void testFindByNameWithInvalidNameThrowsException() {
        // Use an invalid role name
        final String invalidRole = "INVALID_ROLE";
        assertThrows(IllegalArgumentException.class,
            () -> authorityService.findByname(invalidRole));
    }
}
