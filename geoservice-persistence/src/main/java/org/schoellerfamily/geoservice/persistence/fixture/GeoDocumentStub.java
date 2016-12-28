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
     * Constructor.
     */
    public GeoDocumentStub() {
        item = null;
    }

    /**
     * Constructor.
     *
     * @param item the wrapped item
     */
    public GeoDocumentStub(final GeoCodeItem item) {
        this.item = item;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        if (item == null) {
            return null;
        }
        return item.getPlaceName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getModernName() {
        if (item == null) {
            return null;
        }
        return item.getModernPlaceName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeocodingResult getResult() {
        if (item == null) {
            return null;
        }
        return item.getGeocodingResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadGeoCodeItem(final GeoCodeItem gci) {
        setGeoItem(gci);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeoCodeItem getGeoItem() {
        return item;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGeoItem(final GeoCodeItem gci) {
        item = gci;
    }
}
