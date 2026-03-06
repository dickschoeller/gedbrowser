package org.schoellerfamily.gedbrowser.security.token.test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.awaitility.Awaitility.await;

import java.time.Duration;

import org.joda.time.DateTimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.security.token.TokenHelper;

import io.jsonwebtoken.ExpiredJwtException;

/**
 * @author Dick Schoeller
 */
public class TokenHelperTest {
    /** */
    private static final String KEY = "mySecret";
    /** */
    private static final int TOKEN_EXPIRY_TIMEOUT_SECONDS = 3;
    /** */
    private TokenHelper tokenHelper;

    /**
     * Setup token helper for testing.
     */
    @BeforeEach
    void setUp() {
        tokenHelper = new TokenHelper("none", KEY, 1, "Authorization", "AUTH-TOKEN");
        final long twentyMillis = 20L;
        DateTimeUtils.setCurrentMillisFixed(twentyMillis);
    }

    /**
     * Test expired token.
     */
    @Test
    @SuppressWarnings("PMD.UnitTestShouldIncludeAssert")
    void testGenerateTokenExpired() {
        final String token = tokenHelper.generateToken("fanjin");
        await()
            .atMost(Duration.ofSeconds(TOKEN_EXPIRY_TIMEOUT_SECONDS))
            .untilAsserted(() -> assertThatExceptionOfType(ExpiredJwtException.class)
                .isThrownBy(() -> tokenHelper.parseClaimsOrThrow(token)));
    }
}
