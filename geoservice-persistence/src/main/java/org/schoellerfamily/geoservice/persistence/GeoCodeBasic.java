package org.schoellerfamily.geoservice.persistence;

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.geoservice.geocoder.GeoCoder;
import org.schoellerfamily.geoservice.persistence.domain.GeoDocument;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.maps.model.GeocodingResult;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.CommentSize")
public abstract class GeoCodeBasic implements GeoCode {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** The key string to use for talking to Google's map APIs. */
    @Autowired
    private transient GeoCoder geoCoder;

    /**
     * {@inheritDoc}
     */
    @Override
    public final GeoCodeItem find(final String placeName) {
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
    public final GeoCodeItem find(final String placeName,
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
    public final void dump() {
        System.out.println(toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int countNotFound() {
        logger.debug("Count the places that couldn't be found");
        final int count = notFoundKeys().size();
        logger.debug(count + " places not found");
        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Collection<String> allKeys() {
        final SortedSet<String> names = new TreeSet<>();
        for (final GeoDocument gdm : findAllDocuments()) {
            names.add(gdm.getName());
        }
        return names;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Collection<String> notFoundKeys() {
        logger.debug("Captures the places that couldn't be found");
        final SortedSet<String> notFoundSet = new TreeSet<>();
        for (final GeoDocument gdm : findAllDocuments()) {
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
    public final GeoCodeItem add(final GeoCodeItem item) {
        addDocument(create(item));
        return item;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final GeoCodeItem delete(final GeoCodeItem item) {
        final String placeName = item.getPlaceName();
        if (get(placeName) == null) {
            logger.debug("Didn't find for removal: " + placeName);
        } else {
            deleteDocument(placeName);
            logger.debug("Removed: " + placeName);
        }
        return item;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final GeoCodeItem get(final String placeName) {
        final GeoDocument geoDocument = getDocument(placeName);
        if (geoDocument == null) {
            return null;
        }
        return geoDocument.getGeoItem();
    }

    /**
     * Dump the cache as a string, one location per line. The format is:
     * <br>&nbsp;&nbsp;&nbsp;&nbsp;
     * place name|modern place name|lat, lng|formatted address<br>
     * Only the place name is required to be present. All of the separators
     * will be present.
     *
     * @return the cache contents in string format
     */
    @Override
    public final String toString() {
        final StringBuilder builder = new StringBuilder();
        final SortedSet<String> names = new TreeSet<>();
        for (final GeoDocument gdm : findAllDocuments()) {
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
}
