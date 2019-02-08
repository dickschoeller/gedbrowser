package org.schoellerfamily.gedbrowser.security.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.schoellerfamily.gedbrowser.security.exception.ResourceConflictException;
import org.schoellerfamily.gedbrowser.security.model.SecurityUser;
import org.schoellerfamily.gedbrowser.security.model.UserRequest;
import org.schoellerfamily.gedbrowser.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Dick Schoeller
 */
@RestController
@RequestMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    /** */
    @Autowired
    private UserService userService;

    /**
     * @param username the username
     * @return the user object
     */
    @RequestMapping(method = GET, value = "/users/{username:.+}")
    @PreAuthorize("hasRole('USER')")
    public SecurityUser loadById(@PathVariable final String username) {
        return userService.findByUsername(username);
    }

    /**
     * @return a list of all users
     */
    @RequestMapping(method = GET, value = "/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<SecurityUser> loadAll() {
        return userService.findAll();
    }

    /**
     * @return a result map
     */
    @RequestMapping(method = GET, value = "/reset-credentials")
    public ResponseEntity<Map<String, String>> resetCredentials() {
      userService.resetCredentials();
      final Map<String, String> result = new HashMap<>();
      result.put("result", "success");
      return ResponseEntity.accepted().body(result);
    }

    /**
     * @param userRequest the description of the requested user
     * @param ucBuilder uri builder
     * @return the new user
     */
    @RequestMapping(method = POST, value = "/signup")
    public ResponseEntity<?> addUser(
            @RequestBody final UserRequest userRequest,
            final UriComponentsBuilder ucBuilder) {
        final SecurityUser existUser =
                userService.findByUsername(userRequest.getUsername());
        if (existUser != null) {
          throw new ResourceConflictException(
                  1L, /* userRequest.getId(),*/ "Username already exists");
        }
        final SecurityUser user = this.userService.save(userRequest);
        // TODO I think creating the header has no effect.
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/v1/user/{userUsername}")
                .buildAndExpand(user.getUsername()).toUri());
        return new ResponseEntity<SecurityUser>(user, HttpStatus.CREATED);
    }

    /**
     * We are not using userService.findByUsername here(we could), so it is good
     * that we are making sure that the user has role "USER" to access this
     * endpoint.
     *
     * @return the current user
     */
    @RequestMapping("/whoami")
    @PreAuthorize("hasRole('USER')")
    public SecurityUser user() {
        return (SecurityUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
