package org.schoellerfamily.geoservice.persistence.fixture;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.schoellerfamily.geoservice.geocoder.GeoCoder;
import org.schoellerfamily.geoservice.persistence.GeoCodeBasic;
import org.schoellerfamily.geoservice.persistence.GeoCodeItem;
import org.schoellerfamily.geoservice.persistence.domain.GeoDocument;

import lombok.extern.slf4j.Slf4j;



/**
 * Provides a stub implementation for geo code.
 *
 * @author Richard Schoeller
 */
@Slf4j
public final class GeoCodeStub extends GeoCodeBasic {

    /** The in-memory version of the cache. */
    private final Map<String, GeoDocument> map = new HashMap<>();

    /**
     * Public constructor. Using Spring to manage as a singleton.
     *
     * @param geoCoder the geoCoder to use here.
     */
    public GeoCodeStub(final GeoCoder geoCoder) {
        super(geoCoder);
        log.debug("Initializing GeoCodeCache");
    }

    /**
     * Executes clear.
     */
    @Override
    public void clear() {
        map.clear();
    }

    /**
     * Executes size.
     *
     * @return the resulting long
     */
    @Override
    public long size() {
        final long size = map.size();
        log.debug("Geocode cache contains {} entries", size);
        return size;
    }

    /**
     * Creates and returns a new geo document stub.
     *
     * @param item the item
     * @return the resulting geo document
     */
    @Override
    public GeoDocument create(final GeoCodeItem item) {
        return new GeoDocumentStub(item);
    }

    /**
     * Finds all documents.
     *
     * @return the resulting geo document>
     */
    @Override
    public Iterable<? extends GeoDocument> findAllDocuments() {
        final SortedSet<String> names = new TreeSet<>();
        names.addAll(map.keySet());
        return names.stream()
            .map(this::getDocument)
            .toList();
    }

    /**
     * Executes add document.
     *
     * @param document the document
     * @return the resulting geo document
     */
    @Override
    public GeoDocument addDocument(final GeoDocument document) {
        map.put(document.getName(), document);
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
        return map.get(placeName);
    }

    /**
     * Returns the geo document.
     *
     * @param placeName the place name to use
     * @return the resulting geo document
     */
    @Override
    public GeoDocument deleteDocument(final String placeName) {
        return map.remove(placeName);
    }
}
