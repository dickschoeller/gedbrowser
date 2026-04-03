package org.schoellerfamily.geoservice.endpoint;

import java.util.List;

import org.schoellerfamily.geoservice.persistence.GeoCode;

import lombok.RequiredArgsConstructor;



/**
 * Exposes operations for the base geo code endpoint.
 *
 * @author Richard Schoeller
 */
@RequiredArgsConstructor
public abstract class BaseGeoCodeEndpoint {

    /** */
    private final GeoCode gcc;

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
        geoCodeAction();
        final long totalLocations = gcc.size();
        final long codedLocations = totalLocations - gcc.countNotFound();
        return List.of(
            "Load complete",
            "%d locations in the cache".formatted(totalLocations),
            "%d geocoded locations in cache".formatted(codedLocations)
        );
    }
}
