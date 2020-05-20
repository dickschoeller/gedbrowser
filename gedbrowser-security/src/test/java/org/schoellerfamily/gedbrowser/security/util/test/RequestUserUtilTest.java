package org.schoellerfamily.gedbrowser.security.util.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.users.UserRoleName;
import org.schoellerfamily.gedbrowser.security.model.SecurityUser;
import org.schoellerfamily.gedbrowser.security.model.UserRequest;
import org.schoellerfamily.gedbrowser.security.service.UserService;
import org.schoellerfamily.gedbrowser.security.util.RequestUserUtil;
import org.springframework.security.core.GrantedAuthority;

public class RequestUserUtilTest {
    @Test
    public void testGood() {
        final StubPrincipal principal = new StubPrincipal("fred");
        final RequestUserUtil util = new RequestUserUtil(principal, new StubUserService());
        assertEquals("username wrong", principal.getName(), util.getUser().getUsername());
    }

    @Test
    public void testNullPrincipal() {
        final StubPrincipal principal = null;
        final RequestUserUtil util = new RequestUserUtil(principal, new StubUserService());
        assertNull("username wrong", util.getUser());
    }

    @Test
    public void testNullName() {
        final StubPrincipal principal = new StubPrincipal(null);
        final RequestUserUtil util = new RequestUserUtil(principal, new StubUserService());
        assertNull("username wrong", util.getUser());
    }

    @Test
    public void testEmptyName() {
        final StubPrincipal principal = new StubPrincipal("");
        final RequestUserUtil util = new RequestUserUtil(principal, new StubUserService());
        assertNull("username wrong", util.getUser());
    }

    @Test
    public void testGoodHasUser() {
        final StubPrincipal principal = new StubPrincipal("fred");
        final RequestUserUtil util = new RequestUserUtil(principal, new StubUserService());
        assertTrue("should have user", util.hasUser());
    }

    @Test
    public void testNullPrincipalHasUser() {
        final StubPrincipal principal = null;
        final RequestUserUtil util = new RequestUserUtil(principal, new StubUserService());
        assertFalse("should not have user", util.hasUser());
    }

    @Test
    public void testNullNameHasUser() {
        final StubPrincipal principal = new StubPrincipal(null);
        final RequestUserUtil util = new RequestUserUtil(principal, new StubUserService());
        assertFalse("should not have user", util.hasUser());
    }

    @Test
    public void testEmptyNameHasUser() {
        final StubPrincipal principal = new StubPrincipal("");
        final RequestUserUtil util = new RequestUserUtil(principal, new StubUserService());
        assertFalse("should not have user", util.hasUser());
    }

    @Test
    public void testGoodHasAdmin() {
        final StubPrincipal principal = new StubPrincipal("fred");
        final RequestUserUtil util = new RequestUserUtil(principal, new StubUserService());
        assertTrue("should have user", util.hasAdmin());
    }

    @Test
    public void testGoodHasFalseAdmin() {
        final StubPrincipal principal = new StubPrincipal("bob");
        final RequestUserUtil util = new RequestUserUtil(principal, new StubUserService());
        assertFalse("should have user", util.hasAdmin());
    }

    @Test
    public void testNullPrincipalHasAdmin() {
        final StubPrincipal principal = null;
        final RequestUserUtil util = new RequestUserUtil(principal, new StubUserService());
        assertFalse("should not have user", util.hasAdmin());
    }

    @Test
    public void testNullNameHasAdmin() {
        final StubPrincipal principal = new StubPrincipal(null);
        final RequestUserUtil util = new RequestUserUtil(principal, new StubUserService());
        assertFalse("should not have user", util.hasAdmin());
    }

    @Test
    public void testEmptyNameHasAdmin() {
        final StubPrincipal principal = new StubPrincipal("");
        final RequestUserUtil util = new RequestUserUtil(principal, new StubUserService());
        assertFalse("should not have user", util.hasAdmin());
    }

    private class StubPrincipal implements Principal {
        /**
         * Hold a name to consume.
         */
        private final String name;

        /**
         * Constructor.
         *
         * @param name the name of this principal
         */
        StubPrincipal(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private class StubUserService implements UserService {
        @Override
        public void resetCredentials() {
        }

        @Override
        public SecurityUser findByUsername(final String username) {
            return new SecurityUser() {
                /** */
                private static final long serialVersionUID = 1L;

                @Override
                public String getUsername() {
                    return username;
                }

                @Override
                public void setUsername(final String username) {
                    // do nothing
                }

                @Override
                public String getFirstname() {
                    return "";
                }

                @Override
                public void setFirstname(final String firstname) {
                    // do nothing
                }

                @Override
                public String getLastname() {
                    return "";
                }

                @Override
                public void setLastname(final String lastname) {
                    // do nothing
                }

                @Override
                public String getEmail() {
                    return "";
                }

                @Override
                public void setEmail(final String email) {
                    // do nothing
                }

                @Override
                public String getPassword() {
                    return "";
                }

                @Override
                public UserRoleName[] getRoles() {
                    return null;
                }

                @Override
                public void addRole(final String role) {
                    // do nothing
                }

                @Override
                public boolean hasRole(final UserRoleName role) {
                    if (role == UserRoleName.ADMIN) {
                        return "fred".equals(username);
                    }
                    return role == UserRoleName.USER;
                }

                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    return null;
                }

                @Override
                public boolean isAccountNonExpired() {
                    return false;
                }

                @Override
                public boolean isAccountNonLocked() {
                    return false;
                }

                @Override
                public boolean isCredentialsNonExpired() {
                    return false;
                }

                @Override
                public boolean isEnabled() {
                    return false;
                }

                @Override
                public void setPassword(final String password) {
                    // do nothing
                }
            };
        }

        @Override
        public List<SecurityUser> findAll() {
            return new ArrayList<>();
        }

        @Override
        public SecurityUser save(final UserRequest user) {
            return null;
        }
    }
}
