package org.schoellerfamily.geoservice.persistence.domain;

import org.schoellerfamily.geoservice.persistence.GeoCodeItem;

import com.google.maps.model.GeocodingResult;

/**
 * @author Dick Schoeller
 */
public interface GeoDocument {
    /**
     * Get the historical name. This is the ID
     * string.
     *
     * @return the name
     */
    String getName();

    /**
     * Get the modern equivalent name.
     *
     * @return the modern name
     */
    String getModernName();

    /**
     * Get the associated geocoding result.
     *
     * @return the result
     */
    GeocodingResult getResult();

    /**
     * Populate this from the geocode item.
     *
     * @param gci the geocode item
     */
    void loadGeoCodeItem(GeoCodeItem gci);

    /**
     * Get this item's geo code item.
     *
     * @return the item
     */
    GeoCodeItem getGeoItem();

    /**
     * Populate this from the geocode item.
     *
     * @param item the geocode item
     */
    void setGeoItem(GeoCodeItem item);
}
