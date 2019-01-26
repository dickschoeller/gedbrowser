package org.schoellerfamily.gedbrowser.security.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @author Dick Schoeller
 */
@Component
public final class TokenHelper {
    /** */
    @Value("${app.name:none}")
    private String appName;

    /** */
    @Value("${jwt.secret:queenvictoria}")
    private String secret;

    /** */
    @Value("${jwt.expires_in:600}")
    private int expiresIn;

    /** */
    @Value("${jwt.header:Authorization}")
    private String authHeaderName;

    /** */
    @Value("${jwt.cookie:AUTH-TOKEN}")
    private String authCookieName;

//    /** */
//    @Autowired
//    private UserDetailsService userDetailsService;

    /** */
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM =
            SignatureAlgorithm.HS512;

    /**
     * @param token the token
     * @return the user name
     */
    public String getUsernameFromToken(final String token) {
        try {
            final Claims claims = this.getClaimsFromToken(token);
            return claims.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param username the username
     * @return the token
     */
    public String generateToken(final String username) {
        return Jwts.builder()
                .setIssuer(appName)
                .setSubject(username)
                .setIssuedAt(generateCurrentDate())
                .setExpiration(generateExpirationDate())
                .signWith(SIGNATURE_ALGORITHM, secret)
                .compact();
    }

    /**
     * @param token the token
     * @return the claims
     */
    private Claims getClaimsFromToken(final String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(this.secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param claims the claims
     * @return the token
     */
    String generateToken(final Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SIGNATURE_ALGORITHM, secret)
                .compact();
    }

    /**
     * @param token the token
     * @return true if the token can be refreshed
     */
    public Boolean canTokenBeRefreshed(final String token) {
        try {
            final Date expirationDate =
                    getClaimsFromToken(token).getExpiration();
//            String username = getUsernameFromToken(token);
//            final UserDetails userDetails =
//                    userDetailsService.loadUserByUsername(username);
            return expirationDate.compareTo(generateCurrentDate()) > 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param token the old token
     * @return the new token
     */
    public String refreshToken(final String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.setIssuedAt(generateCurrentDate());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * @return the current time
     */
    private long getCurrentTimeMillis() {
        return DateTime.now().getMillis();
    }

    /**
     * @return the current date
     */
    private Date generateCurrentDate() {
        return new Date(getCurrentTimeMillis());
    }

    /**
     * @return the expiration date
     */
    private Date generateExpirationDate() {
        final int millisPerSecond = 1000;
        return new Date(
                getCurrentTimeMillis() + this.expiresIn * millisPerSecond);
    }

    /**
     * @param request the servlet request
     * @return the token
     */
    public String getToken(final HttpServletRequest request) {
        /**
         *  Getting the token from Cookie store
         */
        Cookie authCookie = getCookieValueByName(request, authCookieName);
        if (authCookie != null) {
            return authCookie.getValue();
        }
        /**
         *  Getting the token from Authentication header
         *  e.g Bearer your_token
         */
        String authHeader = request.getHeader(authHeaderName);
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
     * @param name The cookie name to look for.
     * @return The cookie, or <code>null</code> if not found.
     */
    public Cookie getCookieValueByName(final HttpServletRequest request,
            final String name) {
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
