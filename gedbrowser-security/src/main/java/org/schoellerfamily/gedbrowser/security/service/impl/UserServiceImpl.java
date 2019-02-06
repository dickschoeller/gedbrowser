package org.schoellerfamily.gedbrowser.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.security.model.User;
import org.schoellerfamily.gedbrowser.security.model.UserImpl;
import org.schoellerfamily.gedbrowser.security.model.UserRequest;
import org.schoellerfamily.gedbrowser.security.model.Users;
import org.schoellerfamily.gedbrowser.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author Dick Schoeller
 */
@Service
public class UserServiceImpl implements UserService {
    /** */
    @Autowired
    private Users users;

    /** */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * {@inheritDoc}
     */
    @Override
    public final void resetCredentials() {
        for (final User user : users) {
            user.setPassword(passwordEncoder.encode("123"));
            users.add(user);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final User findByUsername(final String username)
            throws UsernameNotFoundException {
        return users.get(username);
    }

//    /**
//     * {@inheritDoc}
//     */
//    @PreAuthorize("hasRole('ADMIN')")
//    @Override
//    public final User findById(final Long id) throws AccessDeniedException {
//        final User u = users.findOne(id);
//        return u;
//    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public final List<User> findAll() throws AccessDeniedException {
      final List<User> result = new ArrayList<>();
      for (final User user: users) {
          result.add(user);
      }
      return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User save(final UserRequest userRequest) {
      final UserImpl user = new UserImpl();
      user.setUsername(userRequest.getUsername());
      user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
      user.setFirstname(userRequest.getFirstname());
      user.setLastname(userRequest.getLastname());
      user.addRole("USER");
      users.add(user);
      return user;
    }
}
