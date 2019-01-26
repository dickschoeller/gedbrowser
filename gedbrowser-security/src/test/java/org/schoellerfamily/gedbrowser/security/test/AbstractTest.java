package org.schoellerfamily.gedbrowser.security.test;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.schoellerfamily.gedbrowser.security.auth.AnonAuthentication;
import org.schoellerfamily.gedbrowser.security.auth.TokenBasedAuthentication;
import org.schoellerfamily.gedbrowser.security.model.User;
import org.schoellerfamily.gedbrowser.security.model.UserImpl;
import org.schoellerfamily.gedbrowser.security.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class })
public abstract class AbstractTest {
    /** */
    @Autowired
    private Users users;

    /** */
    @Autowired
    private ObjectMapper objectMapper;

    /** */
    private SecurityContext securityContext;

    /** */
    @Before
    public final void beforeAbstractTest() {
        securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(securityContext.getAuthentication())
                .thenReturn(new AnonAuthentication());
    }

    /** */
    @After
    public final void afterAbstractTest() {
        SecurityContextHolder.clearContext();
    }

    /**
     * @param user the user
     */
    protected void mockAuthenticatedUser(final User user) {
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
    protected User buildTestAnonUser() {
        final UserImpl user = new UserImpl();
        user.setUsername("anon");
        return user;
    }

    /**
     * @return the user
     */
    protected User buildTestUser() {
        final UserImpl user = new UserImpl();
        user.addRole("USER");
        user.setUsername("user");
        return user;
    }

    /**
     * @return the user
     */
    protected User buildTestAdmin() {
        final UserImpl admin = new UserImpl();
        admin.setUsername("admin");
        admin.addRole("ADMIN");
        admin.addRole("USER");
        return admin;
    }

    /**
     * @return the users
     */
    public Users getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(final Users users) {
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
