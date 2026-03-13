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
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.google.maps.model.AddressType;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implements making read requests to geoservice.
 *
 * @author Dick Schoeller
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class GeoServiceClient {

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
    public GeoServiceItem get(final String placeName) {
        log.debug("Get: {}", placeName);
        final String url = protocol + "://" + host + ":" + port
                + "/geocode?name=" + URLEncoder.encode(placeName, StandardCharsets.UTF_8);
        try {
            return restClient.get()
                .uri(URI.create(url))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(GeoServiceItem.class)
                .getBody();
        } catch (RestClientException rce) {
            final GeoServiceItem recovered = tryRecoverFromNullableFeatures(url);
            if (recovered != null) {
                return recovered;
            }
            if (log.isDebugEnabled()) {
                log.debug("Unable to get geocode from geoservice at {}", url, rce);
            } else {
                log.error("Unable to get geocode from geoservice at {}", url);
                log.error("host: {}", host);
                log.error("port: {}", port);
                log.error("protocol: {}", protocol);
            }
            return new GeoServiceItem(placeName, placeName, null);
        }
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
