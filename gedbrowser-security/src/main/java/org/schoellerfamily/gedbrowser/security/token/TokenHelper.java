package org.schoellerfamily.gedbrowser.security.token;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;



/**
 * Represents token helper.
 *
 * @author Richard Schoeller
 */
@Component
@RequiredArgsConstructor
public final class TokenHelper {
    /**
     * The app name value.
     */
    @Value("${app.name:none}")
    private final String appName;

    /**
     * The secret value.
     */
    @Value("${jwt.secret:queenvictoria}")
    private final String secret;
    /**
     * The expires in value.
     */
    @Value("${jwt.expires_in:600}")
    private final int expiresIn;

    /**
     * The auth header name value.
     */
    @Value("${jwt.header:Authorization}")
    private final String authHeaderName;

    /**
     * The auth cookie name value.
     */
    @Value("${jwt.cookie:AUTH-TOKEN}")
    private final String authCookieName;

    @SuppressFBWarnings("REC_CATCH_EXCEPTION")
    private SecretKey getSigningKey() {
        try {
            byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
            final int minBytes = 64; // 512 bits / 8
            if (keyBytes.length < minBytes) {
                final MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
                keyBytes = sha512.digest(keyBytes);
            }
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (Exception _) {
            // Fallback: wrap raw bytes (may fail at runtime if too short)
            return new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        }
    }

    /**
     * Returns the username from token.
     *
     * @param token the token
     * @return the username from token
     */
    public String getUsernameFromToken(final String token) {
        try {
            final Claims claims = this.getClaimsFromToken(token);
            return claims.getSubject();
        } catch (io.jsonwebtoken.ExpiredJwtException eje) {
            // Let callers who care about expiration handle it explicitly
            throw eje;
        } catch (Exception e_) {
            return null;
        }
    }

    /**
     * Returns the string.
     *
     * @param username the username to use
     * @return the resulting string
     */
    public String generateToken(final String username) {
        return Jwts.builder()
            .issuer(appName)
            .subject(username)
            .issuedAt(generateCurrentDate())
            .expiration(generateExpirationDate())
            .signWith(getSigningKey())
            .compact();
    }

    /**
     * Parse the token and return its claims, letting parsing exceptions propagate.
     *
     * @param token the token
     * @return the claims
     */
    public Claims parseClaimsOrThrow(final String token) {
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    @SuppressWarnings("java:S1168")
    private Claims getClaimsFromToken(final String token) {
        try {
            return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        } catch (io.jsonwebtoken.ExpiredJwtException eje) {
            // Let callers who care about expiration handle it explicitly
            throw eje;
        } catch (Exception _) {
            return null;
        }
    }

    /**
     * Executes can token be refreshed.
     *
     * @param token the token
     * @return the resulting boolean
     */
    public Boolean canTokenBeRefreshed(final String token) {
        try {
            final Date expirationDate = getClaimsFromToken(token).getExpiration();
            return expirationDate.compareTo(generateCurrentDate()) > 0;
        } catch (Exception _) {
            return false;
        }
    }

    /**
     * Executes refresh token.
     *
     * @param token the token
     * @return the resulting string
     */
    public String refreshToken(final String token) {
        try {
            final Claims claims = getClaimsFromToken(token);
            return Jwts.builder()
                .claims(claims)
                .issuedAt(generateCurrentDate())
                .expiration(generateExpirationDate())
                .signWith(getSigningKey())
                .compact();
        } catch (Exception _) {
            return null;
        }
    }

    private long getCurrentTimeMillis() {
        return Instant.now().toEpochMilli();
    }

    private Date generateCurrentDate() {
        return new Date(getCurrentTimeMillis());
    }

    private Date generateExpirationDate() {
        final int millisPerSecond = 1000;
        return new Date(getCurrentTimeMillis() + this.expiresIn * millisPerSecond);
    }

    /**
     * Returns the token.
     *
     * @param request the request
     * @return the token
     */
    public String getToken(final HttpServletRequest request) {
        final Cookie authCookie = getCookieValueByName(request, authCookieName);
        if (authCookie != null) {
            return authCookie.getValue();
        }
        final String authHeader = request.getHeader(authHeaderName);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            final int len = "Bearer ".length();
            return authHeader.substring(len);
        }

        return null;
    }

    /**
     * Find a specific HTTP cookie in a request.
     *
     * @param request The HTTP request object.
     * @param name    The cookie name to look for.
     * @return The cookie, or <code>null</code> if not found.
     */
    public Cookie getCookieValueByName(final HttpServletRequest request, final String name) {
        if (request.getCookies() == null) {
            return null;
        }
        for (int i = 0; i < request.getCookies().length; i++) {
            if (request.getCookies()[i].getName().equals(name)) {
                return request.getCookies()[i];
            }
        }
        return null;
    }
}
