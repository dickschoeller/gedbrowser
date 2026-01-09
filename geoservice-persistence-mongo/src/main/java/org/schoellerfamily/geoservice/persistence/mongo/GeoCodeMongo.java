package org.schoellerfamily.geoservice.persistence.mongo;

import org.schoellerfamily.geoservice.geocoder.GeoCoder;
import org.schoellerfamily.geoservice.persistence.GeoCodeBasic;
import org.schoellerfamily.geoservice.persistence.GeoCodeItem;
import org.schoellerfamily.geoservice.persistence.domain.GeoDocument;
import org.schoellerfamily.geoservice.persistence.mongo.domain.GeoDocumentMongo;
import org.schoellerfamily.geoservice.persistence.mongo.domain.GeoDocumentMongoFactory;
import org.schoellerfamily.geoservice.persistence.mongo.repository.GeoDocumentRepositoryMongo;

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
    private final GeoDocumentRepositoryMongo repository;

    /**
     * Public constructor. Using Spring to manage as a singleton.
     *
     * @param geoCoder the GeoCoder
     * @param repository the GeoDocumentRepository
     */
    public GeoCodeMongo(final GeoCoder geoCoder, final GeoDocumentRepositoryMongo repository) {
        super(geoCoder);
        log.debug("Initializing GeoCodeCache");
        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        repository.deleteAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<? extends GeoDocument> findAllDocuments() {
        return repository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long size() {
        final long count = repository.count();
        log.debug("Geocode cache contains {} entries", count);
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
        repository.save((GeoDocumentMongo) document);
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
        return (GeoDocumentMongo) repository.find(placeName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeoDocument deleteDocument(final String placeName) {
        final GeoDocumentMongo document = getDocumentMongo(placeName);
        if (document != null) {
            repository.delete(document);
        }
        return document;
    }
}
