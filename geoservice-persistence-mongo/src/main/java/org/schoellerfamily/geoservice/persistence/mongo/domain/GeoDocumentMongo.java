package org.schoellerfamily.geoservice.persistence.mongo.domain;

import org.schoellerfamily.geoservice.persistence.GeoCodeItem;
import org.schoellerfamily.geoservice.persistence.domain.GeoDocument;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;

import com.google.maps.model.GeocodingResult;

/**
 * @author Dick Schoeller
 */
public final class GeoDocumentMongo implements GeoDocument {
    /** */
    @Id
    private String name;

    /** */
    @Indexed
    private String modernName;

    /** */
    private GeocodingResult result;

    /** */
    @Transient
    private GeoCodeItem geoItem;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getModernName() {
        return modernName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeocodingResult getResult() {
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadGeoCodeItem(final GeoCodeItem gci) {
        geoItem = gci;
        name = geoItem.getPlaceName();
        modernName = geoItem.getModernPlaceName();
        result = geoItem.getGeocodingResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeoCodeItem getGeoItem() {
        return geoItem;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGeoItem(final GeoCodeItem geoItem) {
        this.geoItem = geoItem;
    }
}
