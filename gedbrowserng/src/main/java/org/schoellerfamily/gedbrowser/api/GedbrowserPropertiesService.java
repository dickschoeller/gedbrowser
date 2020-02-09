package org.schoellerfamily.gedbrowser.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Dick Schoeller
 */
@Service
public class GedbrowserPropertiesService {
    /** */
    @Value("${gedbrowser.home:/var/lib/gedbrowser}")
    private transient String gedbrowserHome;

    /**
     * Get the location of the gedbrowser configuration directory.
     * Defaults to /var/lib/gedbrowser.
     * @return the location string
     */
    public String gedbrowserHome() {
        return gedbrowserHome;
    }
}
