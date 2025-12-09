package org.schoellerfamily.gedbrowser.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.users.UserRoleName;
import org.schoellerfamily.gedbrowser.security.model.Authority;
import org.schoellerfamily.gedbrowser.security.service.AuthorityService;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dick Schoeller
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {
//    /** */
//    @Autowired
//    private AuthorityRepository authorityRepository;

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
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
