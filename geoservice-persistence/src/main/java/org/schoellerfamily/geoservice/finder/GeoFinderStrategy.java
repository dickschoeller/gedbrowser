package org.schoellerfamily.geoservice.finder;

import org.schoellerfamily.geoservice.persistence.GeoCodeItem;

/**
 * Defines the contract for geo finder strategy.
 *
 * @author Richard Schoeller
 */
public interface GeoFinderStrategy {
    /**
     * Find a geocode item from its place name.
     *
     * @param placeName the place name
     * @return the item
     */
    GeoCodeItem find(String placeName);
}
