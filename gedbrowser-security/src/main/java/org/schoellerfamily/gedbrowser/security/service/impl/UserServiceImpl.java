package org.schoellerfamily.gedbrowser.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.security.model.SecurityUser;
import org.schoellerfamily.gedbrowser.security.model.SecurityUsers;
import org.schoellerfamily.gedbrowser.security.model.UserImpl;
import org.schoellerfamily.gedbrowser.security.model.UserRequest;
import org.schoellerfamily.gedbrowser.security.service.UserService;
import org.schoellerfamily.gedbrowser.writer.users.UsersWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Dick Schoeller
 */
@Service
public class UserServiceImpl implements UserService {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private SecurityUsers users;
    /** */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * {@inheritDoc}
     */
    @Override
    public final void resetCredentials() {
        for (final SecurityUser user : users) {
            user.setPassword(passwordEncoder.encode("123"));
            users.add(user);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final SecurityUser findByUsername(final String username)
            throws UsernameNotFoundException {
        return users.get(username);
    }

    // /**
    // * {@inheritDoc}
    // */
    // @PreAuthorize("hasRole('ADMIN')")
    // @Override
    // public final User findById(final Long id) throws AccessDeniedException {
    // final User u = users.findOne(id);
    // return u;
    // }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public final List<SecurityUser> findAll() throws AccessDeniedException {
        final List<SecurityUser> result = new ArrayList<>();
        for (final SecurityUser user : users) {
            result.add(user);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SecurityUser save(final UserRequest userRequest) {
        logger.info("save user" + userRequest.getUsername());
        final UserImpl user = new UserImpl();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setFirstname(userRequest.getFirstname());
        user.setLastname(userRequest.getLastname());
        user.addRole("USER");
        users.add(user);
        final UsersWriter usersWriter = new UsersWriter(users,
                users.getUserFileName());
        usersWriter.write();
        return user;
    }
}
