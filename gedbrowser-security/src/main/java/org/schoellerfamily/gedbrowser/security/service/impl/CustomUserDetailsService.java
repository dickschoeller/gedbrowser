package org.schoellerfamily.gedbrowser.security.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.security.model.User;
import org.schoellerfamily.gedbrowser.security.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Dick Schoeller
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private Users users;

    /** */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /** */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {
        final User user = users.get(username);
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
     */
    public void changePassword(final String oldPassword,
            final String newPassword) {

        final Authentication currentUser =
                SecurityContextHolder.getContext().getAuthentication();
        final String username = currentUser.getName();

        if (authenticationManager != null) {
            logger.debug("Re-authenticating user '" + username
                    + "' for password change request.");

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username,
                            oldPassword));
        } else {
            logger.debug(
                    "No authentication manager set. can't change Password!");

            return;
        }

        logger.debug("Changing password for user '" + username + "'");

        final User user = (User) loadUserByUsername(username);

        user.setPassword(passwordEncoder.encode(newPassword));
        users.add(user);
    }
}
