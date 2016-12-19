package org.schoellerfamily.gedbrowser.geocode.dao;

import java.util.Collection;
import java.util.Set;
import java.util.PrimitiveIterator.OfDouble;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.CommentSize")
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
    int size();

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
}
