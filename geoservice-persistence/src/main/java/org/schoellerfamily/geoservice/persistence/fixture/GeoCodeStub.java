package org.schoellerfamily.geoservice.persistence.fixture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.geoservice.persistence.GeoCodeBasic;
import org.schoellerfamily.geoservice.persistence.GeoCodeItem;
import org.schoellerfamily.geoservice.persistence.domain.GeoDocument;

/**
 * @author Dick Schoeller
 */
public final class GeoCodeStub extends GeoCodeBasic {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** The in-memory version of the cache. */
    private final Map<String, GeoDocumentStub> map = new HashMap<>();

    /**
     * Public constructor. Using Spring to manage as a singleton.
     */
    public GeoCodeStub() {
        logger.debug("Initializing GeoCodeCache");
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
        logger.debug("Geocode cache contains " + map.size() + " entries");
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
        final List<GeoDocument> list = new ArrayList<>();
        for (final String name : names) {
            list.add(getDocument(name));
        }
        return list;
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
