package org.schoellerfamily.gedbrowser.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Dick Schoeller
 */
@Service
public class PropertiesConfigurationService {
    /** */
    @Value("${jwt.cookie:AUTH-TOKEN}")
    private String cookie;

    /** */
    @Value("${spring.profiles.active:production}")
    private String activeProfile;

    /** */
    @Value("${gedbrowser.home:/var/lib/gedbrowser}")
    private transient String gedbrowserHome;

    /** */
    @Value("${spring.data.mongodb.host:localhost}")
    private transient String host;

    /** */
    @Value("${spring.data.mongodb.port:27017}")
    private transient int port;

    /** */
    @Value("${gedbrowser.userFile:userFile.csv}")
    private String userFile;

    /**
     * Get the name of the cookie with the authentication token.
     * Defaults to AUTH-TOKEN.
     * @return the name of the cookie
     */
    public String cookie() {
        return cookie;
    }

    /**
     * Get the active profile name. Defaults to production.
     * @return the profile name
     */
    public String activeProfile() {
        return activeProfile;
    }

    /**
     * Get the location of the gedbrowser configuration directory.
     * Defaults to /var/lib/gedbrowser.
     * @return the location string
     */
    public String gedbrowserHome() {
        return gedbrowserHome;
    }

    /**
     * Get the host name where the MongoDB server resides.
     * Defaults to localhost.
     * @return the host name
     */
    public String mongoHost() {
        return host;
    }

    /**
     * Get the port number where the MongoDB server resides. Defaults to 27017.
     * @return the port number
     */
    public int mongoPort() {
        return port;
    }

    /**
     * Get the userFile value.
     * @return
     */
    public String getUserFile() {
        return gedbrowserHome + "/" + userFile;
    }
}
