package org.schoellerfamily.gedbrowser.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Dick Schoeller
 */
@Service
public class SecurityPropertiesService {
    /** */
    @Value("${gedbrowser.home:/var/lib/gedbrowser}")
    private transient String gedbrowserHome;

    /** */
    @Value("${gedbrowser.userFile:userFile.csv}")
    private String userFile;

    /** */
    @Value("${jwt.cookie:AUTH-TOKEN}")
    private String cookie;

    /** */
    @Value("${spring.profiles.active:production}")
    private String activeProfile;

    /**
     * Get the name of the cookie with the authentication token.
     * Defaults to AUTH-TOKEN.
     *
     * @return the name of the cookie
     */
    public String cookie() {
        return cookie;
    }

    /**
     * Get the active profile name. Defaults to production.
     *
     * @return the profile name
     */
    public String activeProfile() {
        return activeProfile;
    }

    /**
     * Get the userFile value.
     *
     * @return the full path to the user file
     */
    public String userFile() {
        return gedbrowserHome + "/" + userFile;
    }
}
