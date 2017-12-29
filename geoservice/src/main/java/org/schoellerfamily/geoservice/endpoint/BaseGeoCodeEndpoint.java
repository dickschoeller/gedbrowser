package org.schoellerfamily.geoservice.endpoint;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.geoservice.persistence.GeoCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.Endpoint;

/**
 * @author Dick Schoeller
 */
public abstract class BaseGeoCodeEndpoint implements Endpoint<List<String>> {

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
     * {@inheritDoc}
     */
    @Override
    public final boolean isEnabled() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isSensitive() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
