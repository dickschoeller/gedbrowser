package org.schoellerfamily.geoservice.persistence.fixture;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.schoellerfamily.geoservice.persistence.GeoCodeBasic;
import org.schoellerfamily.geoservice.persistence.GeoCodeItem;
import org.schoellerfamily.geoservice.persistence.domain.GeoDocument;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@Slf4j
public final class GeoCodeStub extends GeoCodeBasic {

    /** The in-memory version of the cache. */
    private final Map<String, GeoDocumentStub> map = new HashMap<>();

    /**
     * Public constructor. Using Spring to manage as a singleton.
     */
    public GeoCodeStub() {
        log.debug("Initializing GeoCodeCache");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        map.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long size() {
        log.debug("Geocode cache contains {} entries", map.size());
        return map.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeoDocument create(final GeoCodeItem item) {
        return new GeoDocumentStub(item);
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public GeoDocument addDocument(final GeoDocument document) {
        map.put(document.getName(), (GeoDocumentStub) document);
        return document;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeoDocument getDocument(final String placeName) {
        return map.get(placeName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeoDocument deleteDocument(final String placeName) {
        return map.remove(placeName);
    }
}
