package org.schoellerfamily.gedbrowser.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;



/**
 * Provides services for mongo properties.
 *
 * @author dick
 */
@Service
@RequiredArgsConstructor
@Getter
@Accessors(fluent = true)
public class MongoPropertiesService {
    /** */
    @Value("${spring.data.mongodb.host:localhost}")
    private final String mongoHost;

    /** */
    @Value("${spring.data.mongodb.port:27017}")
    private final int mongoPort;
}
