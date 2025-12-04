package org.schoellerfamily.gedbrowser.security.service.impl;

import org.schoellerfamily.gedbrowser.security.model.SecurityUser;
import org.schoellerfamily.gedbrowser.security.model.SecurityUsers;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    /** */
    private final SecurityUsers users;

    /** */
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {
        final SecurityUser user = users.get(username);
        if (user == null) {
            throw new UsernameNotFoundException(String
                    .format("No user found with username '%s'.", username));
        }
        return user;
    }

    /**
     * Change password of current user.
     *
     * @param oldPassword old password
     * @param newPassword new password
     * @param authenticationManager the authentication manager
     */
    public void changePassword(final String oldPassword,
            final String newPassword,
            final AuthenticationManager authenticationManager) {

        final Authentication currentUser =
                SecurityContextHolder.getContext().getAuthentication();
        final String username = currentUser.getName();

        if (authenticationManager == null) {
            log.debug(
                    "No authentication manager set. can't change Password!");

            return;
        } else {
            log.debug("Re-authenticating user '" + username
                    + "' for password change request.");

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username,
                            oldPassword));
        }

        log.debug("Changing password for user '" + username + "'");

        final SecurityUser user = (SecurityUser) loadUserByUsername(username);

        user.setPassword(passwordEncoder.encode(newPassword));
        users.add(user);
    }
}
