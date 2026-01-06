package org.schoellerfamily.gedbrowser.security.token.test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.joda.time.DateTimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.security.token.TokenHelper;
import org.springframework.test.util.ReflectionTestUtils;

import io.jsonwebtoken.ExpiredJwtException;

/**
 * @author Dick Schoeller
 */
public class TokenHelperTest {
    /** */
    private static final String KEY = "mySecret";
    /** */
    private TokenHelper tokenHelper;

    /**
     * Setup token helper for testing.
     */
    @BeforeEach
    public void init() {
        tokenHelper = new TokenHelper();
        final long twentyMillis = 20L;
        DateTimeUtils.setCurrentMillisFixed(twentyMillis);
        ReflectionTestUtils.setField(tokenHelper, "expiresIn", 1);
        ReflectionTestUtils.setField(tokenHelper, "secret", KEY);
    }

    /**
     * Test expired token.
     *
     * @throws InterruptedException won't happen
     */
    @Test
    public void testGenerateTokenExpired() throws InterruptedException {
        final String token = tokenHelper.generateToken("fanjin");
        final int twoSecondsInMillis = 2000;
        Thread.sleep(twoSecondsInMillis);
        assertThatExceptionOfType(ExpiredJwtException.class)
            .isThrownBy(() -> tokenHelper.parseClaimsOrThrow(token));
    }
}
