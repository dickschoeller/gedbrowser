package org.schoellerfamily.gedbrowser.security.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class PasswordEncoderConfig {
    /**
     * @return the encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
        return new PasswordEncoder() {

            @Override
            public String encode(final CharSequence rawPassword) {
                return rawPassword.toString();
            }

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
