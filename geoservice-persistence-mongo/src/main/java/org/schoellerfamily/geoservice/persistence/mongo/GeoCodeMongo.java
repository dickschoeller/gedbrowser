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
 * Provides behavior related to geo code mongo.
 *
 * @author Richard Schoeller
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
     * Executes clear.
     */
    @Override
    public void clear() {
        repository.deleteAll();
    }

    /**
     * Finds the all documents.
     *
     * @return the resulting geo document>
     */
    @Override
    public Iterable<? extends GeoDocument> findAllDocuments() {
        return repository.findAll();
    }

    /**
     * Executes size.
     *
     * @return the resulting long
     */
    @Override
    public long size() {
        final long count = repository.count();
        log.debug("Geocode cache contains {} entries", count);
        return count;
    }

    /**
     * Returns the geo document.
     *
     * @param item the item
     * @return the resulting geo document
     */
    @Override
    public GeoDocument create(final GeoCodeItem item) {
        return GeoDocumentMongoFactory.getInstance().createGeoDocument(item);
    }

    /**
     * Executes add document.
     *
     * @param document the document
     * @return the resulting geo document
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
     * Gets the document.
     *
     * @param placeName the place name to use
     * @return the document
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
     * Executes delete document.
     *
     * @param placeName the place name to use
     * @return the resulting geo document
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
