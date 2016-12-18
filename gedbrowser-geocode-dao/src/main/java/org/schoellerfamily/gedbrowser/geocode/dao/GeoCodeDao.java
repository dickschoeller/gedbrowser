package org.schoellerfamily.gedbrowser.geocode.dao;

import java.util.Set;

/**
 * @author Dick Schoeller
 */
public interface GeoCodeDao {
    /**
     * Clear the cache.
     */
    public void clear();

    /**
     * Search the cache for a particular historical place name. This method is
     * the most likely to be used from an application, in that applications
     * typically won't have the associated modern name that helps the Google
     * API find the right place.
     *
     * @param placeName the historical place name to find
     * @return the cache entry
     */
    public GeoCodeCacheEntry find(final String placeName);
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
    public GeoCodeCacheEntry find(String placeName, String modernPlaceName);

    /**
     * Dump the place list in a form that is valuable for manual analysis.
     */
    public void dump();

    /**
     * @return the number of not found places
     */
    public int countNotFound();

    /**
     * @return the set of place names not found
     */
    public Set<String> notFoundKeys();

    /**
     * @return the size of the cache
     */
    public int size();
}
