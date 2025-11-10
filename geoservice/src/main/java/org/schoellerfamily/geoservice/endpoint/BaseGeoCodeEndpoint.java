package org.schoellerfamily.geoservice.endpoint;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.geoservice.persistence.GeoCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Dick Schoeller
 */
public abstract class BaseGeoCodeEndpoint {

    /** */
    @Autowired
    private GeoCode gcc;

    /**
     * Do the appropriate load action.
     */
    public abstract void geoCodeAction();

    /**
     * Return the GeoCode object for these actions.
     *
     * @return the GeoCode
     */
    public final GeoCode getGeoCode() {
        return gcc;
    }

    /**
     * Return an identifier for logging. Subclasses currently implement
     * {@code getId()} so keep that contract via an abstract method here.
     *
     * @return endpoint id
     */
    protected abstract String getId();

    /**
     * Do the invocation and return messages.
     *
     * @return messages
     */
    public final List<String> invoke() {
        final List<String> messages = new ArrayList<>();
        geoCodeAction();
        messages.add("Load complete");
        final long totalLocations = gcc.size();
        messages.add(totalLocations + " locations in the cache");
        final long codedLocations = totalLocations
                - gcc.countNotFound();
        messages.add(codedLocations + " geocoded locations in cache");
        return messages;
    }
}
