package org.schoellerfamily.gedbrowser.security.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.users.UserRoleName;
import org.schoellerfamily.gedbrowser.security.model.Authority;

/**
 * @author Dick Schoeller
 */
public class AuthorityTest {
    /** */
    @Test
    public void testDefault() {
        final Authority authority = new Authority();
        assertNull("Should be null", authority.getAuthority());
    }

    /** */
    @Test
    public void testDefaultUserRoleName() {
        final Authority authority = new Authority();
        assertNull("Should be null", authority.getUserRoleName());
    }

    /** */
    @Test
    public void testSetGet() {
        final Authority authority = new Authority();
        authority.setUserRoleName(UserRoleName.USER);
        assertEquals("Should be USER", "ROLE_USER", authority.getAuthority());
    }
}
