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
 * @author Dick Schoeller
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

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public long size() {
        final long size = map.size();
        log.debug("Geocode cache contains {} entries", size);
        return size;
    }

    @Override
    public GeoDocument create(final GeoCodeItem item) {
        return new GeoDocumentStub(item);
    }

    @Override
    public Iterable<? extends GeoDocument> findAllDocuments() {
        final SortedSet<String> names = new TreeSet<>();
        names.addAll(map.keySet());
        return names.stream()
            .map(this::getDocument)
            .toList();
    }

    @Override
    public GeoDocument addDocument(final GeoDocument document) {
        map.put(document.getName(), document);
        return document;
    }

    @Override
    public GeoDocument getDocument(final String placeName) {
        return map.get(placeName);
    }

    @Override
    public GeoDocument deleteDocument(final String placeName) {
        return map.remove(placeName);
    }
}
