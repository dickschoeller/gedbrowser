package org.schoellerfamily.gedbrowser.api;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;



/**
 * Represents dummy encoder.
 *
 * @author Richard Schoeller
 */
@Component
public class DummyEncoder implements PasswordEncoder {
    /**
     * Returns the string.
     *
     * @param rawPassword the raw password
     * @return the resulting string
     */
    @Override
    public String encode(final CharSequence rawPassword) {
        return rawPassword.toString();
    }

    /**
     * Executes matches.
     *
     * @param rawPassword the raw password
     * @param encodedPassword the encoded password to use
     * @return the resulting boolean
     */
    @Override
    public boolean matches(final CharSequence rawPassword,
            final String encodedPassword) {
        if (encodedPassword == null && rawPassword == null) {
            return true;
        }
        if (encodedPassword == null || rawPassword == null) {
            return false;
        }
        return encodedPassword.equals(rawPassword.toString());
    }
}
