package org.schoellerfamily.geoservice.persistence.mongo.domain;

import org.schoellerfamily.geoservice.persistence.GeoCodeItem;
import org.schoellerfamily.geoservice.persistence.domain.GeoDocument;

import com.google.maps.model.GeocodingResult;

/**
 * Provides behavior related to geo document mongo.
 *
 * @author Richard Schoeller
 */
public final class GeoDocumentMongo implements GeoDocument {
    /** */
    private String name;

    /** */
    private String modernName;

    /** */
    private GeocodingResult result;

    /** */
    private GeoCodeItem geoItem;

    /**
     * Returns the name.
     *
     * @return the name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the modern name.
     *
     * @return the modern name
     */
    @Override
    public String getModernName() {
        return modernName;
    }

    /**
     * Returns the result.
     *
     * @return the result
     */
    @Override
    public GeocodingResult getResult() {
        return result;
    }

    /**
     * Loads the geo code item.
     *
     * @param gci the gci
     */
    @Override
    public void loadGeoCodeItem(final GeoCodeItem gci) {
        geoItem = gci;
        name = geoItem.getPlaceName();
        modernName = geoItem.getModernPlaceName();
        result = geoItem.getGeocodingResult();
    }

    /**
     * Returns the geo item.
     *
     * @return the geo item
     */
    @Override
    public GeoCodeItem getGeoItem() {
        return geoItem;
    }

    /**
     * Sets the geo item.
     *
     * @param geoItem the geo item
     */
    @Override
    public void setGeoItem(final GeoCodeItem geoItem) {
        this.geoItem = geoItem;
    }

    /**
     * Loads field values directly from persistence.
     *
     * @param nameValue place name
     * @param modernNameValue modern place name
     * @param resultValue geocoding result
     */
    public void loadPersistedValues(final String nameValue, final String modernNameValue,
            final GeocodingResult resultValue) {
        this.name = nameValue;
        this.modernName = modernNameValue;
        this.result = resultValue;
    }
}
