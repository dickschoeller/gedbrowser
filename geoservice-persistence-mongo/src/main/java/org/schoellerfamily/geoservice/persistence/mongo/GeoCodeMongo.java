package org.schoellerfamily.geoservice.persistence.mongo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.geoservice.geocoder.GeoCoder;
import org.schoellerfamily.geoservice.persistence.GeoCodeDao;
import org.schoellerfamily.geoservice.persistence.GeoCodeItem;
import org.schoellerfamily.geoservice.persistence.domain.GeoDocument;
import org.schoellerfamily.geoservice.persistence.mongo.domain.GeoDocumentMongo;
import org.schoellerfamily.geoservice.persistence.mongo.domain.GeoDocumentMongoFactory;
import org.schoellerfamily.geoservice.persistence.mongo.repository.GeoDocumentRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.maps.model.GeocodingResult;

/**
 * This class implements the cache that allows applications to work with the
 * Google geocoding APIs, without running the queries per day limitations.
 *
 * TODO rework singleton management to make this be Spring loadable.
 *
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.CommentSize", "PMD.TooManyMethods" })
public final class GeoCodeMongo implements GeoCodeDao {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** The key string to use for talking to Google's map APIs. */
    @Autowired
    private transient GeoCoder geoCoder;

    /** */
    @Autowired
    private transient GeoDocumentRepositoryMongo geoDocumentRepository;

    /**
     * Public constructor. Using Spring to manage as a singleton.
     */
    public GeoCodeMongo() {
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
    public GeoCodeItem find(final String placeName) {
        logger.debug("find(\"" + placeName + "\")");
        final GeoDocument geoDocument = getDocument(placeName);
        GeoCodeItem gcce;
        if (geoDocument != null) {
            gcce = geoDocument.getGeoItem();
            if (gcce.getGeocodingResult() != null) {
                return gcce;
            }

            // It doesn't have a coding result, see if there is one.
            final GeocodingResult[] results = geoCoder.geocode(
                    gcce.getModernPlaceName());
            if (results.length == 0) {
                // Nope, so we live with the current entry from the cache.
                return gcce;
            } else {
                // Replace item in cache with new one.
                gcce = new GeoCodeItem(gcce.getPlaceName(),
                        gcce.getModernPlaceName(), results[0]);
                add(gcce);
                return gcce;
            }
        }
        // Not found in cache. Let's see what we can find.
        final GeocodingResult[] results = geoCoder.geocode(placeName);
        if (results.length > 0) {
            /* Work with the first result. */
            gcce = new GeoCodeItem(placeName, results[0]);
        } else {
            // Not found, create empty.
            gcce = new GeoCodeItem(placeName);
        }
        add(gcce);
        return gcce;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeoCodeItem find(final String placeName,
            final String modernPlaceName) {
        if (modernPlaceName == null || modernPlaceName.isEmpty()) {
            return find(placeName);
        }
        logger.debug(
                "find(\"" + placeName + "\", \"" + modernPlaceName + "\")");
        final GeoDocument geoDocument = getDocument(placeName);
        GeoCodeItem gcce;
        if (geoDocument != null) {
            // We found one.
            if (modernPlaceName.equals(geoDocument.getModernName())) {
                // Modern name matches existing, so we don't have a change.
                if (geoDocument.getResult() == null) {
                    // No result, try to get.
                    final GeocodingResult[] results = geoCoder.geocode(
                            modernPlaceName);
                    if (results.length == 0) {
                        // Not found, so current is good enough.
                        return geoDocument.getGeoItem();
                    }

                    // Replace item in cache with new one.
                    gcce = new GeoCodeItem(placeName, modernPlaceName,
                            results[0]);
                    add(gcce);
                    return gcce;
                } else {
                    // Fully formed return it.
                    return geoDocument.getGeoItem();
                }
            } else {
                // Modern place names don't match, replace.
                // No result, try to get.
                final GeocodingResult[] results = geoCoder.geocode(
                        modernPlaceName);
                if (results.length == 0) {
                    gcce = new GeoCodeItem(placeName, modernPlaceName);
                    add(gcce);
                    return gcce;
                } else {
                    gcce = new GeoCodeItem(placeName, modernPlaceName,
                            results[0]);
                    add(gcce);
                    return gcce;
                }
            }
        }

        // Not found in cache. Let's see what we can find.
        final GeocodingResult[] results = geoCoder.geocode(modernPlaceName);
        if (results.length > 0) {
            /* Work with the first result. */
            gcce = new GeoCodeItem(placeName, modernPlaceName, results[0]);
        } else {
            // Not found, create empty.
            gcce = new GeoCodeItem(placeName, modernPlaceName);
        }
        add(gcce);
        return gcce;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dump() {
        System.out.println(toString());
    }

    /**
     * Dump the cache as a string, one location per line. The format is:
     * <br>
     * place name|modern place name|lat, lng|formatted address
     * <br>
     * Only the place name is required to be present. All of the separators
     * will be present.
     *
     * @return the cache contents in string format
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        final SortedSet<String> names = new TreeSet<>();
        for (final GeoDocument gdm : geoDocumentRepository.findAll()) {
            final String name = gdm.getName();
            names.add(name);
        }

        for (final String name : names) {
            final GeoDocument geoDocument = getDocument(name);
            builder.append(geoDocument.getName());
            builder.append("|");
            if (!geoDocument.getName().equals(geoDocument.getModernName())) {
                builder.append(geoDocument.getModernName());
            }
            builder.append("|");
            if (geoDocument.getResult() == null) {
                builder.append("NOT FOUND");
                builder.append("|");
            } else {
                builder.append(geoDocument.getResult().geometry.location);
                builder.append("|");
                builder.append(geoDocument.getResult().formattedAddress);
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int countNotFound() {
        logger.debug("Count the places that couldn't be found");
        final Set<String> notFound = notFoundKeys();
        final int count = notFound.size();
        logger.debug(count + " places not found");
        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> allKeys() {
        final SortedSet<String> names = new TreeSet<>();
        for (final GeoDocument gdm : geoDocumentRepository.findAll()) {
            names.add(gdm.getName());
        }
        return names;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> notFoundKeys() {
        logger.debug("Captures the places that couldn't be found");
        final Iterable<GeoDocumentMongo> all = geoDocumentRepository.findAll();
        final Set<String> notFoundSet = new TreeSet<>();
        for (final GeoDocument gdm : all) {
            final String name = gdm.getName();
            if (gdm.getResult() == null) {
                notFoundSet.add(name);
                logger.debug(name);
            }
        }
        return notFoundSet;
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
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public void add(final GeoCodeItem item) {
        final GeoDocumentMongo geoDocument =
                (GeoDocumentMongo) GeoDocumentMongoFactory.getInstance()
                        .createGeoDocument(item);
        geoDocumentRepository.save(geoDocument);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(final GeoCodeItem item) {
        final String placeName = item.getPlaceName();
        if (geoDocumentRepository.find(placeName) == null) {
            logger.debug("Didn't find for removal: " + placeName);
        } else {
            geoDocumentRepository.delete(placeName);
            logger.debug("Removed: " + placeName);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeoCodeItem get(final String placeName) {
        final GeoDocument geoDocument = getDocument(placeName);
        if (geoDocument == null) {
            return null;
        }
        return geoDocument.getGeoItem();
    }

    /**
     * @param document a document
     */
    public void addDocument(final GeoDocument document) {
        if (document == null || document.getName() == null) {
            return;
        }
        geoDocumentRepository.save((GeoDocumentMongo) document);
    }

    /**
     * @param placeName the key
     * @return the document
     */
    public GeoDocument getDocument(final String placeName) {
        return geoDocumentRepository.find(placeName);
    }
}
