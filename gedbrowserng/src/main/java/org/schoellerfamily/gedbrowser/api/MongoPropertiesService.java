package org.schoellerfamily.gedbrowser.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;



/**
 * Provides services for mongo properties.
 *
 * @author dick
 */
@Service
@RequiredArgsConstructor
public class MongoPropertiesService {
    /** */
    @Value("${spring.data.mongodb.host:localhost}")
    private final String mongoHost;

    /** */
    @Value("${spring.data.mongodb.port:27017}")
    private final int mongoPort;

    /**
     * Get the host name where the MongoDB server resides.
     * Defaults to localhost.
     *
     * @return the host name
     */
    public String mongoHost() {
        return mongoHost;
    }

    /**
     * Get the port number where the MongoDB server resides. Defaults to 27017.
     *
     * @return the port number
     */
    public int mongoPort() {
        return mongoPort;
    }
}
