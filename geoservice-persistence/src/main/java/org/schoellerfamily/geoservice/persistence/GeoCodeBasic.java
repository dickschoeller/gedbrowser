package org.schoellerfamily.geoservice.persistence;

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.schoellerfamily.geoservice.geocoder.GeoCoder;
import org.schoellerfamily.geoservice.persistence.domain.GeoDocument;

import com.google.maps.model.GeocodingResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@Slf4j
@RequiredArgsConstructor
public abstract class GeoCodeBasic implements GeoCode {

    /** The key string to use for talking to Google's map APIs. */
    private final GeoCoder geoCoder;

    @Override
    public final GeoCodeItem find(final String placeName) {
        log.debug("find(\"{}\")", placeName);
        final GeoDocument geoDocument = getDocument(placeName);
        if (geoDocument == null) {
            return addToCacheIfFound(placeName);
        }
        final GeoCodeItem gcce = geoDocument.getGeoItem();
        if (gcce.getGeocodingResult() != null) {
            return gcce;
        }

        // It doesn't have a coding result, see if there is one.
        final GeocodingResult[] results = geoCoder.geocode(gcce.getModernPlaceName());
        if (results.length == 0) {
            // Nope, so we live with the current entry from the cache.
            return gcce;
        }
        return replaceItemInCache(gcce.getPlaceName(), gcce.getModernPlaceName(),
            results);
    }

    private GeoCodeItem addToCacheIfFound(final String placeName) {
        // Not found in cache. Let's see what we can find.
        final GeocodingResult[] results = geoCoder.geocode(placeName);
        final GeoCodeItem gcce = createGeoCodeItem(placeName, results);
        add(gcce);
        return gcce;
    }

    private GeoCodeItem createGeoCodeItem(final String placeName, final GeocodingResult[] results) {
        if (results.length > 0) {
            /* Work with the first result. */
            return new GeoCodeItem(placeName, results[0]);
        }
        // Not found, create empty.
        return new GeoCodeItem(placeName);
    }

    private GeoCodeItem replaceItemInCache(final String placeName, final String modernPlaceName,
        final GeocodingResult[] results) {
        final GeoCodeItem newGcce = new GeoCodeItem(placeName, modernPlaceName, results[0]);
        add(newGcce);
        return newGcce;
    }

    @Override
    public final GeoCodeItem find(final String placeName, final String modernPlaceName) {
        if (StringUtils.isEmpty(modernPlaceName)) {
            return find(placeName);
        }
        log.debug("find(\"{}\", \"{}\")", placeName, modernPlaceName);
        final GeoDocument geoDocument = getDocument(placeName);
        if (geoDocument == null) {
            return addToCacheIfFound(placeName, modernPlaceName);
        }
        // We found one.
        if (modernPlaceName.equals(geoDocument.getModernName())) {
            // Modern name matches existing, so we don't have a change.
            if (geoDocument.getResult() == null) {
                return modernWithNoResult(placeName, modernPlaceName, geoDocument);
            }
            // Fully formed return it.
            return geoDocument.getGeoItem();
        }
        // Modern place names don't match, replace.
        // No result, try to get.
        final GeocodingResult[] results = geoCoder.geocode(modernPlaceName);
        if (results.length == 0) {
            return noModernResult(placeName, modernPlaceName);
        }
        return replaceItemInCache(placeName, modernPlaceName, results);
    }

    private GeoCodeItem addToCacheIfFound(final String placeName, final String modernPlaceName) {
        // Not found in cache. Let's see what we can find.
        final GeocodingResult[] results = geoCoder.geocode(modernPlaceName);
        final GeoCodeItem gcce = createGeoCodeItem(placeName, modernPlaceName, results);
        add(gcce);
        return gcce;
    }

    private GeoCodeItem createGeoCodeItem(final String placeName, final String modernPlaceName,
        final GeocodingResult[] results) {
        if (results.length > 0) {
            /* Work with the first result. */
            return new GeoCodeItem(placeName, modernPlaceName, results[0]);
        }
        // Not found, create empty.
        return new GeoCodeItem(placeName, modernPlaceName);
    }

    private GeoCodeItem noModernResult(final String placeName, final String modernPlaceName) {
        final GeoCodeItem gcce = new GeoCodeItem(placeName, modernPlaceName);
        add(gcce);
        return gcce;
    }

    private GeoCodeItem modernWithNoResult(final String placeName, final String modernPlaceName,
        final GeoDocument geoDocument) {
        // No result, try to get.
        final GeocodingResult[] results = geoCoder.geocode(modernPlaceName);
        if (results.length == 0) {
            // Not found, so current is good enough.
            return geoDocument.getGeoItem();
        }

        final GeoCodeItem gcce = new GeoCodeItem(placeName, modernPlaceName, results[0]);
        add(gcce);
        return gcce;
    }

    @Override
    public final void dump() {
        System.out.println(toString());
    }

    @Override
    public final int countNotFound() {
        log.debug("Count the places that couldn't be found");
        final int count = notFoundKeys().size();
        log.debug("{} places not found", count);
        return count;
    }

    @Override
    public final Collection<String> allKeys() {
        final SortedSet<String> names = new TreeSet<>();
        for (final GeoDocument gdm : findAllDocuments()) {
            names.add(gdm.getName());
        }
        return names;
    }

    @Override
    public final Collection<String> notFoundKeys() {
        log.debug("Captures the places that couldn't be found");
        final SortedSet<String> notFoundSet = new TreeSet<>();
        for (final GeoDocument gdm : findAllDocuments()) {
            final String name = gdm.getName();
            if (gdm.getResult() == null) {
                notFoundSet.add(name);
                final String mName = gdm.getModernName();
                if (name.equals(mName)) {
                    log.debug(name);
                } else {
                    log.debug("{}|{}", name, mName);
                }
            }
        }
        return notFoundSet;
    }

    @Override
    public final GeoCodeItem add(final GeoCodeItem item) {
        addDocument(create(item));
        return item;
    }

    @Override
    public final GeoCodeItem delete(final GeoCodeItem item) {
        final String placeName = item.getPlaceName();
        if (get(placeName) == null) {
            log.debug("Didn't find for removal: {}", placeName);
        } else {
            deleteDocument(placeName);
            log.debug("Removed: {}", placeName);
        }
        return item;
    }

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

        appendNamesToBuilder(builder, names);
        return builder.toString();
    }

    private void appendNamesToBuilder(final StringBuilder builder, final SortedSet<String> names) {
        for (final String name : names) {
            final GeoDocument geoDocument = getDocument(name);
            builder.append(geoDocument.getName());
            builder.append("|");
            if (!geoDocument.getName().equals(geoDocument.getModernName())) {
                builder.append(geoDocument.getModernName());
            }
            builder.append("|");
            appendResultToBuilder(builder, geoDocument);
            builder.append("\n");
        }
    }

    private void appendResultToBuilder(final StringBuilder builder, final GeoDocument geoDocument) {
        if (geoDocument.getResult() == null) {
            builder.append("NOT FOUND");
            builder.append("|");
        } else {
            builder.append(geoDocument.getResult().geometry.location);
            builder.append("|");
            builder.append(geoDocument.getResult().formattedAddress);
        }
    }
}
