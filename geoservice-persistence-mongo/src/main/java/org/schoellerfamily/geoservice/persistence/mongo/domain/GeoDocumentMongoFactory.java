package org.schoellerfamily.geoservice.persistence.mongo.domain;

import org.schoellerfamily.geoservice.persistence.GeoCodeItem;
import org.schoellerfamily.geoservice.persistence.domain.GeoDocument;

/**
 * Creates geo document mongo instances.
 *
 * @author Richard Schoeller
 */
public final class GeoDocumentMongoFactory {
    /** Singleton instance. */
    private static final GeoDocumentMongoFactory INSTANCE =
        new GeoDocumentMongoFactory();

    private GeoDocumentMongoFactory() {
        // Empty constructor.
    }

    /**
     * Gets the instance.
     *
     * @return the instance
     */
    public static GeoDocumentMongoFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Creates the geo document.
     *
     * @param gci the gci
     * @return the resulting geo document
     */
    public GeoDocument createGeoDocument(final GeoCodeItem gci) {
        final GeoDocument retval = new GeoDocumentMongo();
        if (gci != null) {
            retval.loadGeoCodeItem(gci);
        }
        return retval;
    }

    /**
     * Creates the geo code item.
     *
     * @param gd the gd
     * @return the resulting geo code item
     */
    public GeoCodeItem createGeoCodeItem(final GeoDocument gd) {
        if (gd == null) {
            return new GeoCodeItem();
        }
        return new GeoCodeItem(gd.getName(),
                gd.getModernName(), gd.getResult());
    }
}
