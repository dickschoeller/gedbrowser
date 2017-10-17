package org.schoellerfamily.geoservice.persistence.mongo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.geoservice.persistence.GeoCodeBasic;
import org.schoellerfamily.geoservice.persistence.GeoCodeItem;
import org.schoellerfamily.geoservice.persistence.domain.GeoDocument;
import org.schoellerfamily.geoservice.persistence.mongo.domain.GeoDocumentMongo;
import org.schoellerfamily.geoservice.persistence.mongo.domain.GeoDocumentMongoFactory;
import org.schoellerfamily.geoservice.persistence.mongo.repository.GeoDocumentRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class implements the cache that allows applications to work with the
 * Google geocoding APIs, without running the queries per day limitations.
 *
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.CommentSize", "PMD.TooManyMethods" })
public final class GeoCodeMongo extends GeoCodeBasic {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private transient GeoDocumentRepositoryMongo geoDocumentRepository;

    /**
     * Public constructor. Using Spring to manage as a singleton.
     */
    public GeoCodeMongo() {
        super();
        logger.debug("Initializing GeoCodeCache");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        geoDocumentRepository.deleteAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<? extends GeoDocument> findAllDocuments() {
        return geoDocumentRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long size() {
        final long count = geoDocumentRepository.count();
        logger.debug("Geocode cache contains " + count + " entries");
        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeoDocument create(final GeoCodeItem item) {
        return (GeoDocumentMongo) GeoDocumentMongoFactory.getInstance()
                .createGeoDocument(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeoDocument addDocument(final GeoDocument document) {
        if (document == null || document.getName() == null) {
            return document;
        }
        geoDocumentRepository.save((GeoDocumentMongo) document);
        return document;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeoDocument getDocument(final String placeName) {
        if (placeName == null) {
            return null;
        }
        return geoDocumentRepository.find(placeName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeoDocument deleteDocument(final String placeName) {
        final GeoDocument document = getDocument(placeName);
        if (document != null) {
            geoDocumentRepository.delete((GeoDocumentMongo) document);
        }
        return document;
    }
}
