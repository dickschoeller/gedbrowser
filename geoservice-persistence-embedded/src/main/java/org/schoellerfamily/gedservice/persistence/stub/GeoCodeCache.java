package org.schoellerfamily.gedservice.persistence.stub;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.geoservice.persistence.GeoCodeDao;
import org.schoellerfamily.geoservice.persistence.GeoCodeItem;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

/**
 * This class implements the cache that allows applications to work with the
 * Google geocoding APIs, without running the queries per day limitations.
 *
 * TODO rework singleton management to make this be Spring loadable.
 *
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.TooManyMethods",
    "PMD.GodClass",
    "PMD.CommentSize" })
public final class GeoCodeCache implements GeoCodeDao {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** The key string to use for talking to Google's map APIs. */
    private final String key;

    /** The in-memory version of the cache. */
    private final Map<String, GeoCodeItem> map = new HashMap<>();

    /**
     * Public constructor. Using Spring to manage as a singleton.
     *
     * @param key the Google Geocoding Service key string
     */
    public GeoCodeCache(final String key) {
        logger.debug("Initializing GeoCodeCache");
        this.key = key;
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
    public GeoCodeItem find(final String placeName) {
        logger.debug("find(\"" + placeName + "\")");
        GeoCodeItem gcce = map.get(placeName);
        if (gcce != null) {
            // If we already have a result, then we're done.
            if (gcce.getGeocodingResult() != null) {
                return gcce;
            }
            // It doesn't have a coding result, see if there is one.
            final GeocodingResult[] results = geocode(
                    gcce.getModernPlaceName());
            if (results.length == 0) {
                // Nope, so we live with the current entry from the cache.
                return gcce;
            } else {
// TODO given the current behavior of loads, we should never get here.
// Should consider a load that doesn't geocode, deferring until the
// location is actually needed.
//                // Replace item in cache with new one.
//                gcce = new GeoCodeCacheEntry(gcce.getPlaceName(),
//                        gcce.getModernPlaceName(), results[0]);
//                map.put(placeName,  gcce);
                return gcce;
            }
        }
        // Not found in cache. Let's see what we can find.
        final GeocodingResult[] results = geocode(placeName);
        if (results.length > 0) {
            /* Work with the first result. */
            gcce = new GeoCodeItem(placeName, results[0]);
            map.put(placeName,  gcce);
        } else {
            // Not found, create empty.
            gcce = new GeoCodeItem(placeName);
            map.put(placeName, gcce);
        }
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
        GeoCodeItem gcce = map.get(placeName);
        if (gcce != null) {
            // Entry found in cache.
            if (!modernPlaceName.equals(gcce.getModernPlaceName())) {
                // We're changing the associated modern place name.
                gcce = new GeoCodeItem(placeName, modernPlaceName);
                map.put(placeName, gcce);
            }
            // If we already have a result, then we're done.
            if (gcce.getGeocodingResult() != null) {
                return gcce;
            }
            // It doesn't have a coding result, see if there is one.
            final GeocodingResult[] results = geocode(modernPlaceName);
            if (results.length == 0) {
                // Nope, so we live with the current entry from the cache.
                return gcce;
            } else {
                // Replace item in cache with new one.
                gcce = new GeoCodeItem(gcce.getPlaceName(),
                        modernPlaceName, results[0]);
                map.put(placeName,  gcce);
                return gcce;
            }
        }
        // Not found in cache. Let's see what we can find.
        final GeocodingResult[] results = geocode(modernPlaceName);
        if (results.length > 0) {
            /* Work with the first result. */
            gcce = new GeoCodeItem(placeName, modernPlaceName,
                    results[0]);
            map.put(placeName,  gcce);
            return gcce;
        }
        // Not found, create empty.
        gcce = new GeoCodeItem(placeName, modernPlaceName);
        map.put(placeName, gcce);
        return gcce;
    }

    /**
     * Request the geocode from Google.
     *
     * @param placeName the place name to find
     * @return the geo-coding result
     */
    private GeocodingResult[] geocode(final String placeName) {
        logger.debug("Querying Google APIs for place: " + placeName);
        final GeoApiContext context = new GeoApiContext().setApiKey(key);
        GeocodingResult[] results;
        try {
            results = GeocodingApi.geocode(context, placeName).await();
        } catch (Exception e) {
            logger.error("Problem querying the place: " + placeName, e);
            results = new GeocodingResult[0];
        }
        return results;
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
        final SortedSet<String> mapKeys = new TreeSet<>();
        mapKeys.addAll(map.keySet());
        for (final String mapKey : mapKeys) {
            final GeoCodeItem gcce = map.get(mapKey);
            builder.append(gcce.getPlaceName());
            builder.append("|");
            if (!gcce.getPlaceName().equals(gcce.getModernPlaceName())) {
                builder.append(gcce.getModernPlaceName());
            }
            builder.append("|");
            if (gcce.getGeocodingResult() == null) {
                builder.append("NOT FOUND");
                builder.append("|");
            } else {
                builder.append(gcce.getGeocodingResult().geometry.location);
                builder.append("|");
                builder.append(gcce.getGeocodingResult().formattedAddress);
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
        final Set<String> set = new TreeSet<>();
        for (final String placeName : map.keySet()) {
            set.add(placeName);
        }
        return set;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> notFoundKeys() {
        logger.debug("Captures the places that couldn't be found");
        final Set<String> notFoundSet = new TreeSet<>();
        for (final Entry<String, GeoCodeItem> entry : map.entrySet()) {
            final String mapKey = entry.getKey();
            final GeoCodeItem gcce = entry.getValue();
            if (gcce.getGeocodingResult() == null) {
                notFoundSet.add(mapKey);
                logger.debug(gcce.getPlaceName());
            }
        }
        return notFoundSet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
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
     * Load and dump an input file, one line at a time. Allows dumping
     * all known data without blowing up on memory.
     *
     * @param filename input filename
     */
    public void oneAtATime(final String filename) {
        try (
            InputStream fis = new FileInputStream(filename);
        ) {
            oneAtATime(fis);
        } catch (IOException e) {
            logger.error("Problem reading places file", e);
        }
    }

    /**
     * Load and dump an input file, one line at a time. Allows dumping
     * all known data without blowing up on memory.
     *
     * @param fis the input stream
     */
    public void oneAtATime(final InputStream fis) {
        String line;
        try (
            InputStreamReader isr =
                    new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
        ) {
            while ((line = br.readLine()) != null) {
                clear();
                final String[] splitLine = line.split("[|]", 4);
                if (splitLine.length > 2) {
                    find(splitLine[0], splitLine[1]);
                } else {
                    find(splitLine[0]);
                }
                dump();
            }
        } catch (IOException e) {
            logger.error("Problem reading places file", e);
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
    public void add(final GeoCodeItem item) {
        map.put(item.getPlaceName(), item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(final GeoCodeItem item) {
        if (map.remove(item.getPlaceName(), item)) {
            logger.debug("Removed: " + item.getPlaceName());
        } else {
            logger.debug("Didn't find for removal: " + item.getPlaceName());
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeoCodeItem get(final String placeName) {
        return map.get(placeName);
    }
}
