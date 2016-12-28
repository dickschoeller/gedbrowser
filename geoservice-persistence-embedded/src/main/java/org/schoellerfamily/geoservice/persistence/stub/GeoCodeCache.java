package org.schoellerfamily.geoservice.persistence.stub;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
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
import org.schoellerfamily.geoservice.persistence.stub.domain.GeoDocumentStub;

/**
 * This class implements the cache that allows applications to work with the
 * Google geocoding APIs, without running the queries per day limitations.
 *
 * TODO rework singleton management to make this be Spring loadable.
 *
 * @author Dick Schoeller
 */
public final class GeoCodeCache extends GeoCodeBasic {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** The in-memory version of the cache. */
    private final Map<String, GeoDocumentStub> map = new HashMap<>();

    /**
     * Public constructor. Using Spring to manage as a singleton.
     */
    public GeoCodeCache() {
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
     * Read places from the default data file. The file is | separated. It may
     * contain just a historical place name or both historical and modern places
     * names.
     */
    public void load() {
        load(getStandardPlacesPath());
    }

    /**
     * Read places from a data file. The file is | separated. It may contain
     * just a historical place name or both historical and modern places names.
     *
     * @param filename the name of the file to load
     */
    public void load(final String filename) {
        logger.debug("Loading the cache from places file: " + filename);
        try (
            InputStream fis = new FileInputStream(filename);
        ) {
            load(fis);
        } catch (IOException e) {
            logger.error("Problem reading places file", e);
        }
    }

    /**
     * Read places from an input stream. The format is | separated. It may
     * contain just a historical place name or both historical and modern
     * places names.
     *
     * @param istream the input stream
     */
    public void load(final InputStream istream) {
        logger.debug("Loading the cache from input stream");
        String line;
        try (
            InputStreamReader isr =
                    new InputStreamReader(istream, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
        ) {
            while ((line = br.readLine()) != null) {
                final String[] splitLine = line.split("[|]", 4);
                if (splitLine.length > 2) {
                    find(splitLine[0], splitLine[1]);
                } else {
                    find(splitLine[0]);
                }
            }
        } catch (IOException e) {
            logger.error("Problem reading places stream", e);
        }
    }

    /**
     * @return path to the standard location for the places file to initialize
     *         from.
     */
    public String getStandardPlacesPath() {
        return "/var/lib/gedbrowser/places.txt";
    }

    /**
     * @return path to the standard location for a test file, shorter than
     *         standard places.
     */
    public String getTestFilePath() {
        return "/var/lib/gedbrowser/test.txt";
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
