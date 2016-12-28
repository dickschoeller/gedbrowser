package org.schoellerfamily.geoservice.persistence.mongo.domain;

import org.schoellerfamily.geoservice.exception.GeoCodeRuntimeException;
import org.schoellerfamily.geoservice.persistence.GeoCodeItem;
import org.schoellerfamily.geoservice.persistence.domain.GeoDocument;

/**
 * @author Dick Schoeller
 */
public final class GeoDocumentMongoFactory {
    /** */
    private static final GeoDocumentMongoFactory INSTANCE =
            new GeoDocumentMongoFactory();

    /**
     * Constructor.
     */
    private GeoDocumentMongoFactory() {
        // Empty constructor.
    }

    /**
     * @return the singleton
     */
    public static GeoDocumentMongoFactory getInstance() {
        return INSTANCE;
    }

    /**
     * @param gci the input geocode item
     * @return the output geo document for persistence
     */
    public GeoDocument createGeoDocument(final GeoCodeItem gci) {
        final Class<GeoDocumentMongo> mongoClass =
                GeoDocumentMongo.class;
//        if (mongoClass == null) {
//            throw new GeoCodeRuntimeException("Class not found");
//        }
        try {
            final GeoDocument retval = mongoClass.newInstance();
            if (gci != null) {
                retval.loadGeoCodeItem(gci);
            }
            return retval;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new GeoCodeRuntimeException("Could not instanciate class", e);
        }
    }

    /**
     * @param gd the input geodocument
     * @return the output geocode item
     */
    public GeoCodeItem createGeoCodeItem(final GeoDocument gd) {
        if (gd == null) {
            return new GeoCodeItem();
        }
        return new GeoCodeItem(gd.getName(),
                gd.getModernName(), gd.getResult());
    }
}
