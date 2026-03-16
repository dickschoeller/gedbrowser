package org.schoellerfamily.gedbrowser.security.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Dick Schoeller
 */
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class PasswordEncoderConfig {
    /**
     * Creates and configures the password encoder bean.
     *
     * @return the configured password encoder bean
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // This should be replaced with BCryptPasswordEncoder or similar in production,
        // but for testing we want to be able to easily verify the password.
        return new PasswordEncoder() {

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
            } };
    }
}
