package org.schoellerfamily.geoservice.persistence.repository;

import org.schoellerfamily.geoservice.persistence.domain.GeoDocument;

/**
 * Represents the persisted form of geocodable.
 *
 * @author Richard Schoeller
 */
public interface GeocodableDocument {
    /**
     * Return a geodocument for the provided place name.
     *
     * @param placeName the place name
     * @return the item
     */
    GeoDocument find(String placeName);
}
