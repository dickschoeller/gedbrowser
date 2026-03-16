package org.schoellerfamily.geoservice.persistence.fixture;

import org.schoellerfamily.geoservice.persistence.GeoCodeItem;
import org.schoellerfamily.geoservice.persistence.domain.GeoDocument;

import com.google.maps.model.GeocodingResult;

/**
 * @author Dick Schoeller
 */
public final class GeoDocumentStub implements GeoDocument {
    /** */
    private GeoCodeItem item;

    /**
     * Creates a new GeoDocumentStub.
     *
     */
    public GeoDocumentStub() {
        item = null;
    }

    /**
     * Creates a new GeoDocumentStub.
     *
     * @param item the item
     */
    public GeoDocumentStub(final GeoCodeItem item) {
        this.item = item;
    }

    /**
     * Returns the name.
     *
     * @return the name
     */
    @Override
    public String getName() {
        if (item == null) {
            return null;
        }
        return item.getPlaceName();
    }

    /**
     * Returns the modern name.
     *
     * @return the modern name
     */
    @Override
    public String getModernName() {
        if (item == null) {
            return null;
        }
        return item.getModernPlaceName();
    }

    /**
     * Returns the result.
     *
     * @return the result
     */
    @Override
    public GeocodingResult getResult() {
        if (item == null) {
            return null;
        }
        return item.getGeocodingResult();
    }

    /**
     * Loads the geo code item.
     *
     * @param gci the gci
     */
    @Override
    public void loadGeoCodeItem(final GeoCodeItem gci) {
        setGeoItem(gci);
    }

    /**
     * Returns the geo item.
     *
     * @return the geo item
     */
    @Override
    public GeoCodeItem getGeoItem() {
        return item;
    }

    /**
     * Sets the geo item.
     *
     * @param gci the gci
     */
    @Override
    public void setGeoItem(final GeoCodeItem gci) {
        item = gci;
    }
}
