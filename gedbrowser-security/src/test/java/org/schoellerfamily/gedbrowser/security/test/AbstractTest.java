package org.schoellerfamily.gedbrowser.security.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tools.jackson.databind.ObjectMapper;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { Application.class, TestConfiguration.class, WebSecurityConfig.class })
public abstract class AbstractTest {
    /** */
    @Autowired
    private SecurityUsers users;

    /** */
    @Autowired
    private ObjectMapper objectMapper;

    /** */
    private SecurityContext securityContext;

    /** */
    @BeforeEach
    protected void setUpBase() {
        securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(securityContext.getAuthentication())
                .thenReturn(new AnonAuthentication());
    }

    /** */
    @AfterEach
    protected void tearDownBase() {
        SecurityContextHolder.clearContext();
    }

    /**
     * @param user the user
     */
    protected void mockAuthenticatedUser(final SecurityUser user) {
        mockAuthentication(new TokenBasedAuthentication(user));
    }

    /**
     * @param auth the authentication
     */
    private void mockAuthentication(final TokenBasedAuthentication auth) {
        auth.setAuthenticated(true);
        Mockito.when(securityContext.getAuthentication()).thenReturn(auth);
    }

    /**
     * @return the user
     */
    protected SecurityUser buildTestAnonUser() {
        final UserImpl user = new UserImpl();
        user.setUsername("anon");
        return user;
    }

    /**
     * @return the user
     */
    protected SecurityUser buildTestUser() {
        final UserImpl user = new UserImpl();
        user.addRole("USER");
        user.setUsername("user");
        return user;
    }

    /**
     * @return the user
     */
    protected SecurityUser buildTestAdmin() {
        final UserImpl admin = new UserImpl();
        admin.setUsername("admin");
        admin.addRole("ADMIN");
        admin.addRole("USER");
        return admin;
    }

    /**
     * @return the users
     */
    public SecurityUsers getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(final SecurityUsers users) {
        this.users = users;
    }

    /**
     * @return the objectMapper
     */
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /**
     * @param objectMapper the objectMapper to set
     */
    public void setObjectMapper(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * @return the securityContext
     */
    public SecurityContext getSecurityContext() {
        return securityContext;
    }

    /**
     * @param securityContext the securityContext to set
     */
    public void setSecurityContext(final SecurityContext securityContext) {
        this.securityContext = securityContext;
    }
}
