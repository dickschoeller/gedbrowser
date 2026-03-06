package org.schoellerfamily.gedbrowser.api;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Dick Schoeller
 */
@Component
public class DummyEncoder implements PasswordEncoder {
    /**
     * {@inheritDoc}
     */
    @Override
    public String encode(final CharSequence rawPassword) {
        return rawPassword.toString();
    }

    /**
     * {@inheritDoc}
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
