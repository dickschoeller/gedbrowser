package org.schoellerfamily.gedbrowser.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author dick
 */
@Service
public class MongoPropertiesService {
    /** */
    @Value("${spring.data.mongodb.host:localhost}")
    private transient String host;

    /** */
    @Value("${spring.data.mongodb.port:27017}")
    private transient int port;

    /**
     * Get the host name where the MongoDB server resides.
     * Defaults to localhost.
     *
     * @return the host name
     */
    public String mongoHost() {
        return host;
    }

    /**
     * Get the port number where the MongoDB server resides. Defaults to 27017.
     *
     * @return the port number
     */
    public int mongoPort() {
        return port;
    }
}
