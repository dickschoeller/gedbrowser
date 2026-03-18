package org.schoellerfamily.geoservice.client;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.Point;
import org.schoellerfamily.geoservice.model.GeoServiceGeocodingResult;
import org.schoellerfamily.geoservice.model.GeoServiceItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import com.google.maps.model.AddressType;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

/**
 * Provides client access to geo service.
 *
 * @author Richard Schoeller
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class GeoServiceClient {
    /** */
    private final GeoServiceResilientCaller resilientCaller;

    /** */
    private final RestClient restClient;

    /** */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /** */
    @Value("${geoservice.host:localhost}")
    private final String host;

    /** */
    @Value("${geoservice.port:8080}")
    private final int port;

    /** */
    @Value("${geoservice.protocol:http}")
    private final String protocol;

    /**
     * Get an item that associates a place name with a canonical place name and coordinates.
     *
     * @param placeName the place name
     * @return the item
     */
    @Cacheable(
        cacheNames = "geocode",
        key = "#placeName",
        unless = "#result == null || #result.result == null")
    public GeoServiceItem get(final String placeName) {
        log.debug("Get: {}", placeName);

        final String url = buildUrl(placeName);

        try {
            return resilientCaller.fetchPrimary(url);
        } catch (Exception t) {
            return handleFetchFailure(url, placeName, t);
        }
    }

    private String buildUrl(final String placeName) {
        return "%s://%s:%d/geocode?name=%s"
            .formatted(protocol, host, port, URLEncoder.encode(placeName, StandardCharsets.UTF_8));
    }

    private GeoServiceItem handleFetchFailure(final String url, final String placeName,
            final Throwable t) {
        if (shouldAttemptFallbackRecovery(t)) {
            final GeoServiceItem recovered = tryRecoverFromNullableFeatures(url);
            if (recovered != null) {
                return recovered;
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("Unable to get geocode from geoservice at {}", url, t);
        } else {
            log.error("Unable to get geocode from geoservice at {}", url);
            log.error("host: {}", host);
            log.error("port: {}", port);
            log.error("protocol: {}", protocol);
        }
        return new GeoServiceItem(placeName, placeName, null);
    }

    /**
     * Returns true when the fallback raw-JSON parse should be attempted.
     * The fallback is only useful for deserialization/conversion failures —
     * connectivity errors (e.g. {@link ResourceAccessException}) and
     * circuit-breaker rejections are excluded because re-fetching the URL
     * would also fail.
     *
     * @param t the exception thrown during the primary fetch
     * @return true when a recovery attempt makes sense
     */
    private boolean shouldAttemptFallbackRecovery(final Throwable t) {
        return !isConnectivityError(t) && !isCircuitBreakerError(t);
    }

    private boolean isCircuitBreakerError(final Throwable t) {
        Throwable current = t;
        while (current != null) {
            final String typeName = current.getClass().getName();
            if (current instanceof CallNotPermittedException
                    || typeName.contains("CircuitBreaker")) {
                return true;
            }
            current = current.getCause();
        }
        return false;
    }

    private boolean isConnectivityError(final Throwable t) {
        Throwable current = t;
        while (current != null) {
            if (current instanceof ResourceAccessException) {
                return true;
            }
            current = current.getCause();
        }
        return false;
    }

    /**
     * Fallback parser for responses with nullable entries in geometry.features.
     *
     * @param url geoservice URL
     * @return parsed GeoServiceItem or null if recovery fails
     */
    private GeoServiceItem tryRecoverFromNullableFeatures(final String url) {
        try {
            final String payload = restClient.get()
                .uri(URI.create(url))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(String.class);
            if (payload == null || payload.isBlank()) {
                return null;
            }
            final JsonNode root = OBJECT_MAPPER.readTree(payload);
            final String placeName = textValue(root.path("placeName"));
            final String modernPlaceName = textValue(root.path("modernPlaceName"));

            final JsonNode resultNode = root.path("result");
            final FeatureCollection geometry = buildGeometry(
                resultNode.path("geometry").path("features"));
            final AddressType[] types = parseAddressTypes(resultNode.path("types"));
            final String[] postcodeLocalities = parseStringArray(
                resultNode.path("postcodeLocalities"));
            final boolean partialMatch = resultNode.path("partialMatch").asBoolean(false);
            final String placeId = textValue(resultNode.path("placeId"));
            final String formattedAddress = textValue(resultNode.path("formattedAddress"));
            final GeoServiceGeocodingResult result = new GeoServiceGeocodingResult(
                null,
                formattedAddress,
                postcodeLocalities,
                geometry,
                types,
                partialMatch,
                placeId);

            return new GeoServiceItem(placeName, modernPlaceName, result);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("Fallback geocode parse failed for {}", url, e);
            }
            return null;
        }
    }

    private FeatureCollection buildGeometry(final JsonNode featuresNode) {
        final FeatureCollection geometry = new FeatureCollection();
        if (featuresNode.isArray()) {
            for (final JsonNode featureNode : featuresNode) {
                final Feature location = featureNode == null || featureNode.isNull()
                    ? null
                    : toLocationFeature(featureNode);
                if (location != null) {
                    // Only the first valid point feature is used as the primary location.
                    geometry.add(location);
                    return geometry;
                }
            }
        }
        return geometry;
    }

    private Feature toLocationFeature(final JsonNode featureNode) {
        final JsonNode coordinates = featureNode.path("geometry").path("coordinates");
        if (!coordinates.isArray() || coordinates.size() < 2) {
            return null;
        }
        final JsonNode lngNode = coordinates.get(0);
        final JsonNode latNode = coordinates.get(1);
        if (!lngNode.isNumber() || !latNode.isNumber()) {
            return null;
        }
        final double lng = lngNode.asDouble(Double.NaN);
        final double lat = latNode.asDouble(Double.NaN);
        if (!Double.isFinite(lng) || !Double.isFinite(lat)) {
            return null;
        }
        final Feature feature = new Feature();
        feature.setGeometry(new Point(lng, lat));

        final String id = textValue(featureNode.path("id"));
        if (id != null) {
            feature.setId(id);
        }
        final String locationType = textValue(featureNode.path("properties").path("locationType"));
        if (locationType != null) {
            feature.setProperty("locationType", locationType);
        }
        return feature;
    }

    private AddressType[] parseAddressTypes(final JsonNode typesNode) {
        if (!typesNode.isArray()) {
            return new AddressType[0];
        }
        final List<AddressType> values = new ArrayList<>();
        for (final JsonNode typeNode : typesNode) {
            final String value = textValue(typeNode);
            if (value == null) {
                continue;
            }
            try {
                values.add(AddressType.valueOf(value));
            } catch (IllegalArgumentException _) {
                // Ignore unknown values from geoservice payloads.
            }
        }
        return values.toArray(new AddressType[0]);
    }

    private String[] parseStringArray(final JsonNode node) {
        if (!node.isArray()) {
            return new String[0];
        }
        final List<String> values = new ArrayList<>();
        for (final JsonNode valueNode : node) {
            final String value = textValue(valueNode);
            if (value != null) {
                values.add(value);
            }
        }
        return values.toArray(new String[0]);
    }

    private String textValue(final JsonNode node) {
        if (node == null || node.isNull() || !node.isValueNode()) {
            return null;
        }
        final String value = node.asString();
        if (value == null || value.isBlank()) {
            return null;
        }
        return value;
    }
}
