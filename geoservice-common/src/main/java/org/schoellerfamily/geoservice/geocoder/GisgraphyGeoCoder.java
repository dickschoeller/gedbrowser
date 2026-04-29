package org.schoellerfamily.geoservice.geocoder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.gisgraphy.addressparser.Address;
import com.gisgraphy.addressparser.AddressQuery;
import com.gisgraphy.addressparser.AddressResultsDto;
import com.gisgraphy.geocoding.GeocodingClient;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import com.google.maps.model.LatLng;

import lombok.extern.slf4j.Slf4j;

/**
 * Provides behavior related to Gisgraphy geo coder.
 *
 * @author Richard Schoeller
 */
@Slf4j
public final class GisgraphyGeoCoder implements GeoCoder {
    /** */
    public static final String DEFAULT_COUNTRY = "US";

    /** */
    private final String baseUrl;

    /** */
    private final String country;

    /**
     * Creates a Gisgraphy geo coder with a default country.
     *
     * @param baseUrl the base URL, for example http://services.gisgraphy.com
     */
    public GisgraphyGeoCoder(final String baseUrl) {
        this(baseUrl, DEFAULT_COUNTRY);
    }

    /**
     * Creates a Gisgraphy geo coder.
     *
     * @param baseUrl the base URL, for example http://services.gisgraphy.com
     * @param country the ISO country code to send with geocoding queries
     */
    public GisgraphyGeoCoder(final String baseUrl, final String country) {
        this.baseUrl = normalizeBaseUrl(baseUrl);
        this.country = country;
    }

    /**
     * Executes geocode.
     *
     * @param placeName the place name to use
     * @return the resulting geocoding result array
     */
    @Override
    public GeocodingResult[] geocode(final String placeName) {
        log.debug("Querying Gisgraphy for place: {}", placeName);
        try {
            final GeocodingClient client = new GeocodingClient(baseUrl);
            final AddressQuery query = new AddressQuery(placeName, country);
            final AddressResultsDto resultsDto = client.geocode(query);
            return toGeocodingResults(resultsDto, placeName);
        } catch (Exception e) {
            log.error("Problem querying the place: {}", placeName, e);
            return new GeocodingResult[0];
        }
    }

    private static GeocodingResult[] toGeocodingResults(
            final AddressResultsDto resultsDto,
            final String placeName) {
        if (resultsDto == null || resultsDto.getResult() == null) {
            return new GeocodingResult[0];
        }
        final List<GeocodingResult> results = new ArrayList<>();
        for (final Address address : resultsDto.getResult()) {
            results.add(toGeocodingResult(address, placeName));
        }
        return results.toArray(new GeocodingResult[0]);
    }

    private static GeocodingResult toGeocodingResult(final Address address,
            final String placeName) {
        final GeocodingResult result = new GeocodingResult();
        result.formattedAddress = buildFormattedAddress(address, placeName);
        final Geometry geometry = new Geometry();
        final Double lat = address == null ? null : address.getLat();
        final Double lng = address == null ? null : address.getLng();
        if (lat != null && lng != null) {
            geometry.location = new LatLng(lat, lng);
        }
        result.geometry = geometry;
        return result;
    }

    private static String buildFormattedAddress(final Address address, final String placeName) {
        if (address == null) {
            return placeName;
        }
        final String name = address.getName();
        if (name != null && !name.isBlank()) {
            return name;
        }
        final List<String> parts = new ArrayList<>();
        addIfPresent(parts, address.getHouseNumber());
        addIfPresent(parts, address.getStreetName());
        addIfPresent(parts, address.getCity());
        addIfPresent(parts, address.getState());
        addIfPresent(parts, address.getZipCode());
        addIfPresent(parts, address.getCountry());
        if (parts.isEmpty()) {
            return placeName;
        }
        return String.join(", ", parts);
    }

    private static void addIfPresent(final List<String> parts, final String value) {
        if (value != null && !value.isBlank()) {
            parts.add(value);
        }
    }

    private static String normalizeBaseUrl(final String configuredBaseUrl) {
        if (configuredBaseUrl == null || configuredBaseUrl.isBlank()) {
            return "http://services.gisgraphy.com/geocoding";
        }
        final String trimmed = configuredBaseUrl.trim();
        try {
            final URI uri = new URI(trimmed);
            final String path = uri.getPath();
            if (path == null || path.isBlank() || "/".equals(path)) {
                return trimmed.endsWith("/") ? trimmed + "geocoding" : trimmed + "/geocoding";
            }
            return trimmed;
        } catch (URISyntaxException e) {
            return trimmed;
        }
    }
}
