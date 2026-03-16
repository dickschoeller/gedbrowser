package org.schoellerfamily.gedbrowser.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.users.UserRoleName;
import org.schoellerfamily.gedbrowser.security.model.Authority;
import org.schoellerfamily.gedbrowser.security.service.AuthorityService;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * Provides behavior related to authority service impl.
 *
 * @author Richard Schoeller
 */
@Service
@SuppressWarnings("java:S125")
public class AuthorityServiceImpl implements AuthorityService {
//    /** */
//    @Autowired
//    private AuthorityRepository authorityRepository;

    /**
     * Finds by id.
     *
     * @param id the unique identifier for the target
     * @return the resulting list of authority
     */
    @Override
    public final List<Authority> findById(final Long id) {
//      Authority auth = this.authorityRepository.findOne(id);
          final List<Authority> auths = new ArrayList<>();
          final Authority auth = Authority.builder()
              .userRoleName(UserRoleName.values()[id.intValue()])
              .build();
          auths.add(auth);
      return auths;
    }

    /**
     * Finds the byname.
     *
     * @param name the name to use
     * @return the resulting list
     */
    @Override
    public final List<Authority> findByname(final String name) {
//      Authority auth = this.authorityRepository.findByName(name);
        final Authority authority = Authority.builder()
            .userRoleName(UserRoleName.valueOf(name))
            .build();

        final List<Authority> authorities = new ArrayList<>();
        authorities.add(authority);
        return authorities;
    }
}
