package org.schoellerfamily.gedbrowser.security.token.test;

import org.joda.time.DateTimeUtils;
import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.security.token.TokenHelper;
import org.springframework.test.util.ReflectionTestUtils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

/**
 * @author Dick Schoeller
 */
public class TokenHelperTest {
    /** */
    private TokenHelper tokenHelper;

    /**
     * Setup token helper for testing.
     */
    @Before
    public void init() {
        tokenHelper = new TokenHelper();
        final long twentyMillis = 20L;
        DateTimeUtils.setCurrentMillisFixed(twentyMillis);
        ReflectionTestUtils.setField(tokenHelper, "expiresIn", 1);
        ReflectionTestUtils.setField(tokenHelper, "secret", "mySecret");
    }

    /**
     * Test expired token.
     */
    @Test(expected = ExpiredJwtException.class)
    public void testGenerateTokenExpired() {
        String token = tokenHelper.generateToken("fanjin");
        Jwts.parser().setSigningKey("mySecret").parseClaimsJws(token).getBody();
    }
}
