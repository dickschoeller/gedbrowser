package org.schoellerfamily.geoservice.persistence.mongo;

import org.schoellerfamily.geoservice.persistence.GeoCodeBasic;
import org.schoellerfamily.geoservice.persistence.GeoCodeItem;
import org.schoellerfamily.geoservice.persistence.domain.GeoDocument;
import org.schoellerfamily.geoservice.persistence.mongo.domain.GeoDocumentMongo;
import org.schoellerfamily.geoservice.persistence.mongo.domain.GeoDocumentMongoFactory;
import org.schoellerfamily.geoservice.persistence.mongo.repository.GeoDocumentRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

/**
 * This class implements the cache that allows applications to work with the
 * Google geocoding APIs, without running the queries per day limitations.
 *
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.CommentSize", "PMD.TooManyMethods" })
@Slf4j
public final class GeoCodeMongo extends GeoCodeBasic {

    /** */
    @Autowired
    private transient GeoDocumentRepositoryMongo geoDocumentRepository;

    /**
     * Public constructor. Using Spring to manage as a singleton.
     */
    public GeoCodeMongo() {
        super();
        log.debug("Initializing GeoCodeCache");
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
        log.debug("Geocode cache contains " + count + " entries");
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
        return getDocumentMongo(placeName);
    }

    private GeoDocumentMongo getDocumentMongo(final String placeName) {
        if (placeName == null) {
            return null;
        }
        return (GeoDocumentMongo) geoDocumentRepository.find(placeName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeoDocument deleteDocument(final String placeName) {
        final GeoDocumentMongo document = getDocumentMongo(placeName);
        if (document != null) {
            geoDocumentRepository.delete(document);
        }
        return document;
    }
}
