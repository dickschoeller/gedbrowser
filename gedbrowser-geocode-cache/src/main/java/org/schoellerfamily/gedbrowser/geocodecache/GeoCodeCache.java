package org.schoellerfamily.gedbrowser.geocodecache;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
@SuppressWarnings({ "PMD.LawOfDemeter", "PMD.TooManyMethods", "PMD.GodClass",
        "PMD.CommentSize" })
public final class GeoCodeCache {
    /** The singleton instance. */
    private static GeoCodeCache instance;

    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** The key string to use for talking to Google's map APIs. */
    private final String key;

    /** The in-memory version of the cache. */
    private final Map<String, GeoCodeCacheEntry> map = new HashMap<>();

    /**
     * Private constructor to keep a singleton.
     */
    private GeoCodeCache() {
        logger.debug("Initializing GeoCodeCache");
        if (instance != null) {
            throw new IllegalStateException("Already instantiated");
        }
        try {
            key = readKeyFile(getGoogleGeoCodingKeyPath());
        } catch (IOException e) {
            throw new GeoCodeCacheRuntimeException("Couldn't open key file", e);
        }
    }

    /**
     * @return the singleton
     */
    public static GeoCodeCache instance() {
        synchronized (GeoCodeCache.class) {
            if (instance == null) {
                instance = new GeoCodeCache();
            }
        }
        return instance;
    }

    /**
     * Read the key string.
     *
     * @param fileName the name of the file to read.
     * @return the key string
     * @throws IOException if we can't read the file
     */
    private String readKeyFile(final String fileName) throws IOException {
        try (FileInputStream fileStream = new FileInputStream(fileName);
                InputStreamReader iStreamReader =
                        new InputStreamReader(fileStream, "UTF-8");
                BufferedReader br = new BufferedReader(iStreamReader);) {
            final StringBuilder sb = new StringBuilder();
            final String line = br.readLine();
            if (line != null) {
                sb.append(line);
            }
            return sb.toString();
        }
    }

    /**
     * Clear the cache.
     */
    public void clear() {
        map.clear();
    }

    /**
     * Search the cache for a particular historical place name. This method is
     * the most likely to be used from an application, in that applications
     * typically won't have the associated modern name that helps the Google
     * API find the right place.
     *
     * @param placeName the historical place name to find
     * @return the cache entry
     */
    public GeoCodeCacheEntry find(final String placeName) {
        logger.debug("find(\"" + placeName + "\")");
        GeoCodeCacheEntry gcce = map.get(placeName);
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
                // Replace item in cache with new one.
                gcce = new GeoCodeCacheEntry(gcce.getPlaceName(),
                        gcce.getModernPlaceName(), results[0]);
                map.put(placeName,  gcce);
                return gcce;
            }
        }
        // Not found in cache. Let's see what we can find.
        final GeocodingResult[] results = geocode(placeName);
        if (results.length > 0) {
            /* Work with the first result. */
            gcce = new GeoCodeCacheEntry(placeName, results[0]);
            map.put(placeName,  gcce);
        } else {
            // Not found, create empty.
            gcce = new GeoCodeCacheEntry(placeName);
            map.put(placeName, gcce);
        }
        return gcce;
    }

    /**
     * Search the cache for a particular historical place name. Assistance is
     * given by providing an accompanying modern name for the place. This
     * method is the most likely to be used when initializing the cache from
     * a data file.
     *
     * @param placeName the historical place name to find
     * @param modernPlaceName the modern place name to use for geo-coding
     * @return the cache entry
     */
    public GeoCodeCacheEntry find(final String placeName,
            final String modernPlaceName) {
        if (modernPlaceName == null || modernPlaceName.isEmpty()) {
            return find(placeName);
        }
        logger.debug(
                "find(\"" + placeName + "\", \"" + modernPlaceName + "\")");
        GeoCodeCacheEntry gcce = map.get(placeName);
        if (gcce != null) {
            // Entry found in cache.
            if (!modernPlaceName.equals(gcce.getModernPlaceName())) {
                // We're changing the associated modern place name.
                gcce = new GeoCodeCacheEntry(placeName, modernPlaceName);
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
                gcce = new GeoCodeCacheEntry(gcce.getPlaceName(),
                        modernPlaceName, results[0]);
                map.put(placeName,  gcce);
                return gcce;
            }
        }
        // Not found in cache. Let's see what we can find.
        final GeocodingResult[] results = geocode(modernPlaceName);
        if (results.length > 0) {
            /* Work with the first result. */
            gcce = new GeoCodeCacheEntry(placeName, modernPlaceName,
                    results[0]);
            map.put(placeName,  gcce);
            return gcce;
        }
        // Not found, create empty.
        gcce = new GeoCodeCacheEntry(placeName, modernPlaceName);
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
     * Dump the place list in a form that is valuable for manual analysis.
     */
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
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        final SortedSet<String> mapKeys = new TreeSet<>();
        mapKeys.addAll(map.keySet());
        for (final String mapKey : mapKeys) {
            final GeoCodeCacheEntry gcce = map.get(mapKey);
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
     * @return the number of not found places
     */
    public int countNotFound() {
        logger.debug("Count the places that couldn't be found");
        final Set<String> notFound = notFoundKeys();
        final int count = notFound.size();
        logger.debug(count + " places not found");
        return count;
    }

    /**
     * @return the set of place names not found
     */
    public Set<String> notFoundKeys() {
        logger.debug("Captures the places that couldn't be found");
        final Set<String> notFoundSet = new TreeSet<>();
        for (final Entry<String, GeoCodeCacheEntry> entry : map.entrySet()) {
            final String mapKey = entry.getKey();
            final GeoCodeCacheEntry gcce = entry.getValue();
            if (gcce.getGeocodingResult() == null) {
                notFoundSet.add(mapKey);
                logger.debug(gcce.getPlaceName());
            }
        }
        return notFoundSet;
    }

    /**
     * @return the size of the cache
     */
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
        String line;
        try (
            InputStream fis = new FileInputStream(filename);
            InputStreamReader isr =
                    new InputStreamReader(fis, Charset.forName("UTF-8"));
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
            logger.error("Problem reading places file", e);
        }
    }

    /**
     * Load the cache from an array of strings. Particularly valuable for
     * testing. Each string has planeName|modernPlaceName. The second part can
     * be empty.
     *
     * @param strings the array of strings
     */
    @SuppressWarnings("PMD.UseVarargs")
    public void load(final String[][] strings) {
        for (final String[] line : strings) {
            if (line.length < 2 || line[1] == null || line[1].isEmpty()) {
                find(line[0]);
            } else {
                find(line[0], line[1]);
            }
        }
    }

    /**
     * Load and dump an input file, one line at a time. Allows dumping
     * all known data without blowing up on memory.
     *
     * @param filename input filename
     */
    private void oneAtATime(final String filename) {
        String line;
        try (
            InputStream fis = new FileInputStream(filename);
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
     * @param args standard main arguments are ignored
     */
    public static void main(final String[] args) {
        final GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.oneAtATime(gcc.getTestFilePath());
    }

    /**
     * @return path to the standard location for the places file to initialize
     *         from.
     */
    private String getStandardPlacesPath() {
        return "/var/lib/gedbrowser/places.txt";
    }

    /**
     * @return path to the standard location for a test file, shorter than
     *         standard places.
     */
    private String getTestFilePath() {
        return "/var/lib/gedbrowser/test.txt";
    }

    /**
     * @return path to the standard location for the google key.
     */
    private String getGoogleGeoCodingKeyPath() {
        return "/var/lib/gedbrowser/google-geocoding-key";
    }
}
