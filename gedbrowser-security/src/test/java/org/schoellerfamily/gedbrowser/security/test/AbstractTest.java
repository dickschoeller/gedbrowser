package org.schoellerfamily.gedbrowser.security.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.schoellerfamily.gedbrowser.security.auth.AnonAuthentication;
import org.schoellerfamily.gedbrowser.security.auth.TokenBasedAuthentication;
import org.schoellerfamily.gedbrowser.security.model.SecurityUser;
import org.schoellerfamily.gedbrowser.security.model.SecurityUsers;
import org.schoellerfamily.gedbrowser.security.model.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import tools.jackson.databind.ObjectMapper;

/**
 * @author Dick Schoeller
 */
@SpringBootTest(classes = { Application.class, TestConfiguration.class, WebSecurityConfig.class })
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public abstract class AbstractTest {
    /** */
    @Autowired
    private SecurityUsers users;

    /** */
    @Autowired
    private ObjectMapper objectMapper;

    /** */
    private SecurityContext securityContext;

    @BeforeEach
    final void setUpBase() {
        securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(securityContext.getAuthentication())
                .thenReturn(new AnonAuthentication());
    }

    @AfterEach
    final void tearDownBase() {
        SecurityContextHolder.clearContext();
    }

    /**
     * Executes mock authenticated user.
     *
     * @param user the user
     */
    protected void mockAuthenticatedUser(final SecurityUser user) {
        mockAuthentication(new TokenBasedAuthentication(user, null));
    }

    private void mockAuthentication(final TokenBasedAuthentication auth) {
        auth.setAuthenticated(true);
        Mockito.when(securityContext.getAuthentication()).thenReturn(auth);
    }

    /**
     * Builds test anon user.
     *
     * @return the resulting security user
     */
    protected SecurityUser buildTestAnonUser() {
        final UserImpl user = new UserImpl();
        user.setUsername("anon");
        return user;
    }

    /**
     * Builds test user.
     *
     * @return the resulting security user
     */
    protected SecurityUser buildTestUser() {
        final UserImpl user = new UserImpl();
        user.addRole("USER");
        user.setUsername("user");
        return user;
    }

    /**
     * Builds test admin.
     *
     * @return the resulting security user
     */
    protected SecurityUser buildTestAdmin() {
        final UserImpl admin = new UserImpl();
        admin.setUsername("admin");
        admin.addRole("ADMIN");
        admin.addRole("USER");
        return admin;
    }

    /**
     * Gets the users.
     *
     * @return the users
     */
    public SecurityUsers getUsers() {
        return users;
    }

    /**
     * Sets the users.
     *
     * @param users the users
     */
    public void setUsers(final SecurityUsers users) {
        this.users = users;
    }

    /**
     * Gets the object mapper.
     *
     * @return the object mapper
     */
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /**
     * Sets the object mapper.
     *
     * @param objectMapper the object mapper
     */
    public void setObjectMapper(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Gets the security context.
     *
     * @return the security context
     */
    public SecurityContext getSecurityContext() {
        return securityContext;
    }

    /**
     * Sets the security context.
     *
     * @param securityContext the security context to use
     */
    public void setSecurityContext(final SecurityContext securityContext) {
        this.securityContext = securityContext;
    }
}
