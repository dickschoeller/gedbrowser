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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import com.google.maps.model.AddressType;

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
        cacheNames = GeoServiceCacheConfig.GEOCODE_CACHE,
        key = "#placeName",
        unless = "#result == null || #result.result == null")
    public GeoServiceItem get(final String placeName) {
        log.debug("Get: {}", placeName);

        final String url = buildUrl(placeName);

        try {
            return normalizeModernPlaceName(resilientCaller.fetchPrimary(url));
        } catch (Exception t) {
            log.error("Failed to fetch geocode from geoservice at {}", url, t);
            return normalizeModernPlaceName(handleFetchFailure(url, placeName, t));
        }
    }

    /**
     * Upsert a geocode item for a historical place name and modern place name.
     *
     * @param placeName the historical place name
     * @param modernPlaceName the canonical/modern place name
     */
    public void upsert(final String placeName, final String modernPlaceName) {
        if (isBlank(placeName)) {
            return;
        }
        final String normalizedModern = isBlank(modernPlaceName)
            ? placeName
            : modernPlaceName;
        final GeoServiceItem item = new GeoServiceItem(placeName, normalizedModern, null);
        if (!create(item)) {
            update(item);
        }
    }

    /**
     * Update an existing geocode item, creating it when missing.
     *
     * @param placeName the historical place name
     * @param modernPlaceName the canonical/modern place name
     */
    public void updateOrCreate(final String placeName, final String modernPlaceName) {
        if (isBlank(placeName)) {
            return;
        }
        final String normalizedModern = isBlank(modernPlaceName)
            ? placeName
            : modernPlaceName;
        final GeoServiceItem item = new GeoServiceItem(placeName, normalizedModern, null);
        if (!update(item)) {
            create(item);
        }
    }

    private boolean create(final GeoServiceItem item) {
        final String url = buildCollectionUrl();
        log.info("GeoService POST attempt: url={} placeName={} modernPlaceName={}",
            url, item.getPlaceName(), item.getModernPlaceName());
        try {
            restClient.post()
                .uri(URI.create(url))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(item)
                .retrieve()
                .toBodilessEntity();
            log.info("GeoService POST success: url={} placeName={} modernPlaceName={}",
                url, item.getPlaceName(), item.getModernPlaceName());
            return true;
        } catch (HttpClientErrorException.Conflict _) {
            log.info("GeoService POST conflict (existing entry): url={} placeName={}",
                url, item.getPlaceName());
            return false;
        } catch (ResourceAccessException e) {
            logGeoServiceFailure("POST", url, item, e);
        } catch (RestClientResponseException e) {
            logGeoServiceFailure("POST", url, item, e);
        } catch (RuntimeException e) {
            logGeoServiceFailure("POST", url, item, e);
        }
        return false;
    }

    private boolean update(final GeoServiceItem item) {
        final String url = buildCollectionUrl();
        log.info("GeoService PUT attempt: url={} placeName={} modernPlaceName={}",
            url, item.getPlaceName(), item.getModernPlaceName());
        try {
            restClient.put()
                .uri(URI.create(url))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(item)
                .retrieve()
                .toBodilessEntity();
            log.info("GeoService PUT success: url={} placeName={} modernPlaceName={}",
                url, item.getPlaceName(), item.getModernPlaceName());
            return true;
        } catch (HttpClientErrorException.NotFound _) {
            log.info("GeoService PUT not found (will create): url={} placeName={}",
                url, item.getPlaceName());
            return false;
        } catch (ResourceAccessException e) {
            logGeoServiceFailure("PUT", url, item, e);
        } catch (RestClientResponseException e) {
            logGeoServiceFailure("PUT", url, item, e);
        } catch (RuntimeException e) {
            logGeoServiceFailure("PUT", url, item, e);
        }
        return false;
    }

    private String buildUrl(final String placeName) {
        return "%s://%s:%d/geocode?name=%s"
            .formatted(protocol, host, port, URLEncoder.encode(placeName, StandardCharsets.UTF_8));
    }

    private String buildCollectionUrl() {
        return "%s://%s:%d/geocode".formatted(protocol, host, port);
    }

    private GeoServiceItem handleFetchFailure(final String url, final String placeName,
            final Throwable t) {
        if (shouldAttemptFallbackRecovery(t)) {
            final GeoServiceItem recovered = tryRecoverFromNullableFeatures(url);
            if (recovered != null) {
                return recovered;
            }
        }
        logGeoServiceFailure("GET", url, new GeoServiceItem(placeName, placeName, null), t);
        return new GeoServiceItem(placeName, placeName, null);
    }

    private void logGeoServiceFailure(final String operation, final String url,
            final GeoServiceItem item, final Throwable t) {
        final Throwable root = rootCause(t);
        if (t instanceof RestClientResponseException responseException) {
            log.error("GeoService {} failed: url={} placeName={} modernPlaceName={}"
                + " status={} statusText={} responseBody={} host={} port={} protocol={}"
                + " cause={}",
                operation,
                url,
                item.getPlaceName(),
                item.getModernPlaceName(),
                responseException.getStatusCode().value(),
                responseException.getStatusText(),
                abbreviate(responseException.getResponseBodyAsString()),
                host,
                port,
                protocol,
                root.toString(),
                t);
            return;
        }
        log.error("GeoService {} failed: url={} placeName={} modernPlaceName={} host={}"
            + " port={} protocol={} cause={}",
            operation,
            url,
            item.getPlaceName(),
            item.getModernPlaceName(),
            host,
            port,
            protocol,
            root.toString(),
            t);
    }

    private String abbreviate(final String responseBody) {
        final int max = 512;
        if (responseBody == null || responseBody.length() <= max) {
            return responseBody;
        }
        return responseBody.substring(0, max) + "...";
    }

    private Throwable rootCause(final Throwable throwable) {
        Throwable current = throwable;
        while (current.getCause() != null) {
            current = current.getCause();
        }
        return current;
    }

    private boolean isBlank(final String value) {
        return value == null || value.isBlank();
    }

    private GeoServiceItem normalizeModernPlaceName(final GeoServiceItem item) {
        if (item == null || !isBlank(item.getModernPlaceName())) {
            return item;
        }
        final String formattedAddress = item.getResult() == null
            ? null
            : item.getResult().getFormattedAddress();
        if (!isBlank(formattedAddress)) {
            return new GeoServiceItem(item.getPlaceName(), formattedAddress, item.getResult());
        }
        if (!isBlank(item.getPlaceName())) {
            return new GeoServiceItem(item.getPlaceName(), item.getPlaceName(), item.getResult());
        }
        return item;
    }

    /**
     * Returns true when the fallback raw-JSON parse should be attempted.
     * The fallback is only useful for deserialization/conversion failures —
     * connectivity errors (e.g. {@link ResourceAccessException}) are excluded
     * because re-fetching the URL would also fail.  When the circuit-breaker
     * is open it rethrows the last {@link ResourceAccessException}, so this
     * connectivity check also covers the circuit-open case.
     *
     * @param t the exception thrown during the primary fetch
     * @return true when a recovery attempt makes sense
     */
    private boolean shouldAttemptFallbackRecovery(final Throwable t) {
        return !isConnectivityError(t);
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
