package org.schoellerfamily.geoservice.persistence;

import java.io.InputStream;
import java.util.Collection;
import java.util.Set;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.CommentSize", "PMD.TooManyMethods" })
public interface GeoCodeDao {
    /**
     * Clear the cache.
     */
    void clear();

    /**
     * Search the cache for a particular historical place name. This method is
     * the most likely to be used from an application, in that applications
     * typically won't have the associated modern name that helps the Google
     * API find the right place.
     *
     * @param placeName the historical place name to find
     * @return the cache entry
     */
    GeoCodeItem find(final String placeName);

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
    GeoCodeItem find(String placeName, String modernPlaceName);

    /**
     * Get the complete set of the data.
     *
     * @return a collection of the place names
     */
    Collection<String> allKeys();

    /**
     * Dump the place list in a form that is valuable for manual analysis.
     */
    void dump();

    /**
     * @return the number of not found places
     */
    int countNotFound();

    /**
     * @return the set of place names not found
     */
    Set<String> notFoundKeys();

    /**
     * @return the size of the cache
     */
    long size();

    /**
     * Add this item to the data set.
     *
     * @param item the item
     */
    void add(GeoCodeItem item);

    /**
     * Delete this item from the data set.
     *
     * @param item the item
     */
    void delete(GeoCodeItem item);

    /**
     * Get the item without hitting Google.
     *
     * @param placeName the placeName
     * @return the item
     */
    GeoCodeItem get(String placeName);

    /**
     * Read places from a data file. The file is | separated. It may contain
     * just a historical place name or both historical and modern places names.
     *
     * @param filename the name of the file to load
     */
    void load(String filename);

    /**
     * Read places from an input stream. The format is | separated. It may
     * contain just a historical place name or both historical and modern
     * places names.
     *
     * @param istream the input stream
     */
    void load(InputStream istream);
}
