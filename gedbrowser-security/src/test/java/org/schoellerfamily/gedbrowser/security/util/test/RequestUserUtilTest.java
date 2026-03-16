package org.schoellerfamily.gedbrowser.security.util.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.datamodel.users.UserRoleName;
import org.schoellerfamily.gedbrowser.security.model.SecurityUser;
import org.schoellerfamily.gedbrowser.security.service.UserService;
import org.schoellerfamily.gedbrowser.security.util.RequestUserUtil;

/**
 * Contains tests for request user util.
 *
 * @author Richard Schoeller
 */
class RequestUserUtilTest {
    @Test
    void testGood() {
        final Principal principal = createPrincipal("fred");
        final RequestUserUtil util = createUtil(principal);
        assertEquals(principal.getName(), util.getUser().getUsername(), "username wrong");
    }

    @Test
    void testNullPrincipal() {
        final Principal principal = null;
        final RequestUserUtil util = createUtil(principal);
        assertNull(util.getUser(), "username wrong");
    }

    @Test
    void testNullName() {
        final Principal principal = createPrincipal(null);
        final RequestUserUtil util = createUtil(principal);
        assertNull(util.getUser(), "username wrong");
    }

    @Test
    void testEmptyName() {
        final Principal principal = createPrincipal("");
        final RequestUserUtil util = createUtil(principal);
        assertNull(util.getUser(), "username wrong");
    }

    @Test
    void testGoodHasUser() {
        final Principal principal = createPrincipal("fred");
        final RequestUserUtil util = createUtil(principal);
        assertTrue(util.hasUser(), "should have user");
    }

    @Test
    void testNullPrincipalHasUser() {
        final Principal principal = null;
        final RequestUserUtil util = createUtil(principal);
        assertFalse(util.hasUser(), "should not have user");
    }

    @Test
    void testNullNameHasUser() {
        final Principal principal = createPrincipal(null);
        final RequestUserUtil util = createUtil(principal);
        assertFalse(util.hasUser(), "should not have user");
    }

    @Test
    void testEmptyNameHasUser() {
        final Principal principal = createPrincipal("");
        final RequestUserUtil util = createUtil(principal);
        assertFalse(util.hasUser(), "should not have user");
    }

    @Test
    void testGoodHasAdmin() {
        final Principal principal = createPrincipal("fred");
        final RequestUserUtil util = createUtil(principal);
        assertTrue(util.hasAdmin(), "should have user");
    }

    @ParameterizedTest(name = "should not have admin for principal {0}")
    @MethodSource("adminFalseCases")
    void testHasAdminFalse(final String username, final boolean nullPrincipal) {
        final Principal principal = nullPrincipal ? null : createPrincipal(username);
        final RequestUserUtil util = createUtil(principal);
        assertFalse(util.hasAdmin(), "should not have user");
    }

    private static Stream<Arguments> adminFalseCases() {
        return Stream.of(
            Arguments.of("bob", false),
            Arguments.of(null, true),
            Arguments.of(null, false),
            Arguments.of("", false)
        );
    }

    private RequestUserUtil createUtil(final Principal principal) {
        return new RequestUserUtil(principal, createUserService());
    }

    private Principal createPrincipal(final String name) {
        final Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(name);
        return principal;
    }

    private UserService createUserService() {
        final UserService userService = mock(UserService.class);
        when(userService.findByUsername(anyString())).thenAnswer(invocation -> {
            final String username = invocation.getArgument(0);
            final SecurityUser user = mock(SecurityUser.class);
            when(user.getUsername()).thenReturn(username);
            when(user.hasRole(UserRoleName.ADMIN)).thenReturn("fred".equals(username));
            when(user.hasRole(UserRoleName.USER)).thenReturn(true);
            return user;
        });
        return userService;
    }
}
