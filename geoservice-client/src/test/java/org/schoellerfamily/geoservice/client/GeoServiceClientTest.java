package org.schoellerfamily.geoservice.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.geoservice.model.GeoServiceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.RequestMatcher;

import com.google.maps.model.AddressType;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.ObjectNode;

/**
 * Contains tests for geo service client.
 */
@SuppressWarnings({ "PMD.UnitTestContainsTooManyAsserts", "PMD.TooManyMethods",
    "PMD.ExcessiveImports", "PMD.CouplingBetweenObjects" })
@org.springframework.boot.test.context.SpringBootTest(
    classes = {
        GeoServiceClientTestApplication.class,
        GeoServiceClientTestConfig.class
    },
    webEnvironment = org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE)
class GeoServiceClientTest {

    /** */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /** */
    private static final double EXPECTED_LATITUDE = 42.0;

    /** */
    private static final double EXPECTED_LONGITUDE = -71.0;

    /** The Spring-managed client under test. */
    @Autowired
    private GeoServiceClient client;

    /** Mocked resilient caller; prevents real circuit-breaker from interfering. */
    @MockitoBean
    private GeoServiceResilientCaller resilientCaller;

    /** The mock server. */
    @Autowired
    private MockRestServiceServer server;

    /** Cache manager configured by {@link GeoServiceCacheConfig}. */
    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void resetServer() {
        server.reset();
        final Cache cache = geocodeCache();
        if (cache != null) {
            cache.clear();
        }
    }

    @Test
    void testGetReturnsPrimaryResponseWhenDeserializationSucceeds() {
        final String place = "Primary Place";
        final String encoded = URLEncoder.encode(place, StandardCharsets.UTF_8);
        final String url = "http://localhost:8080/geocode?name=" + encoded;

        final GeoServiceItem expected =
            new GeoServiceItem("Primary Place", "Primary Modern Place", null);
        when(resilientCaller.fetchPrimary(url)).thenReturn(expected);

        final GeoServiceItem item = client.get(place);

        assertNotNull(item);
        assertEquals("Primary Place", item.getPlaceName());
        assertEquals("Primary Modern Place", item.getModernPlaceName());
        assertNull(item.getResult());
    }

    @Test
    void testGetReturnsCachedItemWithoutCallingBackend() {
        final String place = "Cached Place";
        final String encoded = URLEncoder.encode(place, StandardCharsets.UTF_8);
        final String url = "http://localhost:8080/geocode?name=" + encoded;

        final RequestMatcher requestToUrl = request -> {
            assertEquals(url, request.getURI().toString());
            assertEquals("GET", request.getMethod().name());
        };

        // Primary fetch fails, triggering fallback raw-JSON parse.
        when(resilientCaller.fetchPrimary(url)).thenThrow(new RuntimeException("bad JSON"));

        // Fallback raw JSON fetch returns a well-formed item with a result.
        server.expect(requestToUrl)
            .andRespond(withSuccess(
                """
                {
                    "placeName":"Cached Place",
                    "modernPlaceName":"Cached Modern Place",
                    "result": {
                        "formattedAddress":"Cached Address",
                        "partialMatch": false,
                        "placeId":"cached-place-id",
                        "types":["LOCALITY"],
                        "postcodeLocalities":[],
                        "geometry": {
                            "features": [
                                {
                                    "id":"cached-feature",
                                    "properties":{"locationType":"ROOFTOP"},
                                    "geometry": {"coordinates": [-71.0, 42.0]}
                                }
                            ]
                        }
                    }
                }
                """,
                MediaType.APPLICATION_JSON));

        final GeoServiceItem first = client.get(place);
        final GeoServiceItem second = client.get(place);

        assertNotNull(first);
        assertNotNull(second);
        assertEquals(first.getPlaceName(), second.getPlaceName());
        assertNotNull(first.getResult());
        assertNotNull(second.getResult());
        assertNotNull(geocodeCache());
        assertNotNull(geocodeCache().get(place, GeoServiceItem.class));
        server.verify();
    }

    @Test
    void testGetFallsBackWhenPrimaryDeserializationFails() {
        final String place = "Fallback Place";
        final String encoded = URLEncoder.encode(place, StandardCharsets.UTF_8);
        final String url = "http://localhost:8080/geocode?name=" + encoded;

        final String fallbackPayload = """
                {
                  "placeName":"Fallback Place",
                  "modernPlaceName":"Fallback Modern Place",
                  "result": {
                    "formattedAddress":"Fallback Address",
                    "partialMatch": true,
                    "placeId": "place-1",
                    "types": ["LOCALITY", "not_a_real_type"],
                    "postcodeLocalities": ["Boston"],
                    "geometry": {
                      "features": [
                        null,
                        {
                          "id":"feature-1",
                          "properties":{"locationType":"ROOFTOP"},
                          "geometry": {"coordinates": [-71.0, 42.0]}
                        }
                      ]
                    }
                  }
                }
                """;

        // Primary fetch fails, triggering fallback raw-JSON parse.
        when(resilientCaller.fetchPrimary(url)).thenThrow(new RuntimeException("bad JSON"));

        // Second request is used by fallback parser as raw JSON.
        final RequestMatcher requestToUrl = request -> {
            assertEquals(url, request.getURI().toString());
            assertEquals("GET", request.getMethod().name());
        };
        server.expect(requestToUrl)
            .andRespond(withSuccess(fallbackPayload, MediaType.APPLICATION_JSON));

        final GeoServiceItem item = client.get(place);

        assertNotNull(item);
        assertEquals("Fallback Place", item.getPlaceName());
        assertEquals("Fallback Modern Place", item.getModernPlaceName());
        assertNotNull(item.getResult());
        final FeatureCollection geometry = item.getResult().getGeometry();
        assertNotNull(geometry);
        assertNotNull(geometry.getFeatures());
        assertEquals(1, geometry.getFeatures().size());

        final Feature feature = geometry.getFeatures().get(0);
        assertNotNull(feature);
        assertEquals("feature-1", feature.getId());
        assertEquals("ROOFTOP", feature.getProperty("locationType"));
        final Point point = (Point) feature.getGeometry();
        assertEquals(EXPECTED_LATITUDE, point.getCoordinates().getLatitude());
        assertEquals(EXPECTED_LONGITUDE, point.getCoordinates().getLongitude());

        server.verify();
    }

    @Test
    void testGetReturnsDefaultItemWhenFallbackAlsoFails() {
        final String place = "Nowhere";
        final String encoded = URLEncoder.encode(place, StandardCharsets.UTF_8);
        final String url = "http://localhost:8080/geocode?name=" + encoded;

        final RequestMatcher requestToUrl = request -> {
          assertEquals(url, request.getURI().toString());
          assertEquals("GET", request.getMethod().name());
        };

        // Primary fetch fails, triggering fallback.
        when(resilientCaller.fetchPrimary(url)).thenThrow(new RuntimeException("bad JSON"));

        // Fallback also fails with empty response.
        server.expect(requestToUrl)
            .andRespond(withSuccess("", MediaType.APPLICATION_JSON));

        final GeoServiceItem item = client.get(place);

        assertNotNull(item);
        assertEquals(place, item.getPlaceName());
        assertEquals(place, item.getModernPlaceName());
        assertNull(item.getResult());

        server.verify();
    }

    @Test
    void testGetReturnsDefaultItemWhenFallbackPayloadIsMalformed() {
        final String place = "Broken Payload";
        final String encoded = URLEncoder.encode(place, StandardCharsets.UTF_8);
        final String url = "http://localhost:8080/geocode?name=" + encoded;

        final RequestMatcher requestToUrl = request -> {
            assertEquals(url, request.getURI().toString());
            assertEquals("GET", request.getMethod().name());
        };

        // Primary fetch fails, triggering fallback.
        when(resilientCaller.fetchPrimary(url)).thenThrow(new RuntimeException("bad JSON"));

        // Fallback also returns malformed JSON.
        server.expect(requestToUrl)
            .andRespond(withSuccess("{still-not-json", MediaType.APPLICATION_JSON));

        final GeoServiceItem item = client.get(place);

        assertNotNull(item);
        assertEquals(place, item.getPlaceName());
        assertEquals(place, item.getModernPlaceName());
        assertNull(item.getResult());

        server.verify();
    }

    @Test
    void testGetReturnsDefaultItemWhenFallbackBodyIsMissing() {
        final String place = "Missing Payload";
        final String encoded = URLEncoder.encode(place, StandardCharsets.UTF_8);
        final String url = "http://localhost:8080/geocode?name=" + encoded;

        final RequestMatcher requestToUrl = request -> {
            assertEquals(url, request.getURI().toString());
            assertEquals("GET", request.getMethod().name());
        };

        // Primary fetch fails, triggering fallback.
        when(resilientCaller.fetchPrimary(url)).thenThrow(new RuntimeException("bad JSON"));

        // Fallback returns 204 No Content.
        server.expect(requestToUrl)
            .andRespond(withStatus(HttpStatus.NO_CONTENT));

        final GeoServiceItem item = client.get(place);

        assertNotNull(item);
        assertEquals(place, item.getPlaceName());
        assertEquals(place, item.getModernPlaceName());
        assertNull(item.getResult());

        server.verify();
    }

    @Test
    void testGetReturnsDefaultItemWhenPrimaryBodyIsEmpty() {
        final String place = "Empty Primary";
        final String encoded = URLEncoder.encode(place, StandardCharsets.UTF_8);
        final String url = "http://localhost:8080/geocode?name=" + encoded;

        final RequestMatcher requestToUrl = request -> {
            assertEquals(url, request.getURI().toString());
            assertEquals("GET", request.getMethod().name());
        };

        // Primary fetch fails (e.g., null body), triggering fallback.
        when(resilientCaller.fetchPrimary(url)).thenThrow(new RuntimeException("empty body"));

        // Fallback raw JSON fetch also has no body.
        server.expect(requestToUrl)
            .andRespond(withStatus(HttpStatus.NO_CONTENT));

        final GeoServiceItem item = client.get(place);

        assertNotNull(item);
        assertEquals(place, item.getPlaceName());
        assertEquals(place, item.getModernPlaceName());
        assertNull(item.getResult());

        server.verify();
    }

    @Test
    void testGetReturnsDefaultItemWhenDebugLoggingIsEnabled() {
        final Level originalLevel = setGeoServiceClientLogLevel(Level.DEBUG);
        try {
            final String place = "Debug Payload";
            final String encoded = URLEncoder.encode(place, StandardCharsets.UTF_8);
            final String url = "http://localhost:8080/geocode?name=" + encoded;

            final RequestMatcher requestToUrl = request -> {
                assertEquals(url, request.getURI().toString());
                assertEquals("GET", request.getMethod().name());
            };

            // Primary fetch fails, triggering fallback.
            when(resilientCaller.fetchPrimary(url)).thenThrow(new RuntimeException("bad JSON"));

            // Fallback also returns malformed JSON.
            server.expect(requestToUrl)
                .andRespond(withSuccess("{still-not-json", MediaType.APPLICATION_JSON));

            final GeoServiceItem item = client.get(place);

            assertNotNull(item);
            assertEquals(place, item.getPlaceName());
            assertEquals(place, item.getModernPlaceName());
            assertNull(item.getResult());

            server.verify();
        } finally {
            setGeoServiceClientLogLevel(originalLevel);
        }
    }

    @Test
    void testBuildGeometrySkipsInvalidFeaturesAndKeepsFirstValidOne()
        throws ReflectiveOperationException {
        final JsonNode featuresNode = OBJECT_MAPPER.readTree(
            """
            [
              {
                "geometry": {
                  "coordinates": ["bad", 42.0]
                }
              },
              {
                "geometry": {
                  "coordinates": [-71.0, 42.0]
                }
              },
              {
                "geometry": {
                  "coordinates": [-72.0, 43.0]
                }
              }
            ]
            """);

        final FeatureCollection geometry = invokePrivate(
            "buildGeometry",
            JsonNode.class,
            featuresNode);

        assertNotNull(geometry);
        assertNotNull(geometry.getFeatures());
        assertEquals(1, geometry.getFeatures().size());

        final Feature feature = geometry.getFeatures().get(0);
        assertNotNull(feature);
        assertNull(feature.getId());
        assertNull(feature.getProperty("locationType"));

        final Point point = (Point) feature.getGeometry();
        assertEquals(EXPECTED_LATITUDE, point.getCoordinates().getLatitude());
        assertEquals(EXPECTED_LONGITUDE, point.getCoordinates().getLongitude());
    }

    @Test
    void testBuildGeometryReturnsEmptyCollectionForNonArrayFeatures()
            throws ReflectiveOperationException {
        final FeatureCollection geometry = invokePrivate(
            "buildGeometry",
            JsonNode.class,
            OBJECT_MAPPER.createObjectNode());

        assertNotNull(geometry);
        assertNotNull(geometry.getFeatures());
        assertTrue(geometry.getFeatures().isEmpty());
    }

    @Test
    void testBuildGeometryReturnsEmptyCollectionForEmptyArrays()
            throws ReflectiveOperationException {
        final FeatureCollection geometry = invokePrivate(
            "buildGeometry",
            JsonNode.class,
            OBJECT_MAPPER.createArrayNode());

        assertNotNull(geometry);
        assertNotNull(geometry.getFeatures());
        assertTrue(geometry.getFeatures().isEmpty());
    }

    @Test
    void testToLocationFeatureRejectsInvalidCoordinateShapes()
            throws ReflectiveOperationException {
        final JsonNode missingArrayNode = OBJECT_MAPPER.readTree(
            """
            {
              "geometry": {
                "coordinates": {}
              }
            }
            """);
        final JsonNode shortArrayNode = OBJECT_MAPPER.readTree(
            """
            {
              "geometry": {
                "coordinates": [-71.0]
              }
            }
            """);
        final ObjectNode nonFiniteNode = featureNode(Double.NaN, 42.0);

        assertNull(invokePrivate("toLocationFeature", JsonNode.class, missingArrayNode));
        assertNull(invokePrivate("toLocationFeature", JsonNode.class, shortArrayNode));
        assertNull(invokePrivate("toLocationFeature", JsonNode.class, nonFiniteNode));
    }

    @Test
    void testToLocationFeatureRejectsInvalidLatitudeValues()
        throws ReflectiveOperationException {
        final JsonNode nonNumericLatitudeNode = OBJECT_MAPPER.readTree(
            """
            {
              "geometry": {
              "coordinates": [-71.0, "bad"]
              }
            }
            """);
        final ObjectNode nonFiniteLatitudeNode = featureNode(EXPECTED_LONGITUDE, Double.NaN);

        assertNull(invokePrivate(
            "toLocationFeature",
            JsonNode.class,
            nonNumericLatitudeNode));
        assertNull(invokePrivate(
            "toLocationFeature",
            JsonNode.class,
            nonFiniteLatitudeNode));
    }

    @Test
    void testParseAddressTypesHandlesUnsupportedInputs()
        throws ReflectiveOperationException {
        final AddressType[] notArrayTypes = invokePrivate(
            "parseAddressTypes",
            JsonNode.class,
            OBJECT_MAPPER.createObjectNode());
        assertNotNull(notArrayTypes);
        assertEquals(0, notArrayTypes.length);

        final JsonNode invalidTypesNode = OBJECT_MAPPER.readTree(
            """
            [null, "   ", "not_a_real_type"]
            """);

        final AddressType[] addressTypes = invokePrivate(
            "parseAddressTypes",
            JsonNode.class,
            invalidTypesNode);

        assertNotNull(addressTypes);
        assertEquals(0, addressTypes.length);
    }

    @Test
    void testParseStringArrayAndTextValueHandleEmptyInputs()
        throws ReflectiveOperationException {
        final String[] notArrayStrings = invokePrivate(
            "parseStringArray",
            JsonNode.class,
            OBJECT_MAPPER.createObjectNode());
        assertNotNull(notArrayStrings);
        assertEquals(0, notArrayStrings.length);

        final JsonNode invalidStringsNode = OBJECT_MAPPER.readTree(
            """
            [null, "   "]
            """);

        final String[] invalidStrings = invokePrivate(
            "parseStringArray",
            JsonNode.class,
            invalidStringsNode);
        assertNotNull(invalidStrings);
        assertEquals(0, invalidStrings.length);

        assertNull(invokePrivate("textValue", JsonNode.class, null));
        assertNull(invokePrivate("textValue", JsonNode.class, OBJECT_MAPPER.nullNode()));
        assertNull(invokePrivate(
            "textValue",
            JsonNode.class,
            OBJECT_MAPPER.createObjectNode()));
        assertNull(invokePrivate(
            "textValue",
            JsonNode.class,
            OBJECT_MAPPER.readTree("\"   \"")));
    }

    @Test
    void testTextValueReturnsNullWhenStringValueIsNull()
        throws ReflectiveOperationException {
        final JsonNode nullStringNode = mock(JsonNode.class);

        when(nullStringNode.isNull()).thenReturn(false);
        when(nullStringNode.isValueNode()).thenReturn(true);
        when(nullStringNode.asString()).thenReturn(null);

        assertNull(invokePrivate(
            "textValue",
            JsonNode.class,
            nullStringNode));
    }

    @Test
    void testGetDoesNotCacheItemsWithoutResult() {
        final String place = "Uncached Place";
        final String encoded = URLEncoder.encode(place, StandardCharsets.UTF_8);
        final String url = "http://localhost:8080/geocode?name=" + encoded;

        final GeoServiceItem noResultItem =
            new GeoServiceItem("Uncached Place", "Uncached Modern Place", null);
        when(resilientCaller.fetchPrimary(url)).thenReturn(noResultItem);

        final GeoServiceItem first = client.get(place);
        final GeoServiceItem second = client.get(place);

        assertNotNull(first);
        assertNotNull(second);
        assertNull(first.getResult());
        assertNull(second.getResult());
        assertNotNull(geocodeCache());
        assertNull(geocodeCache().get(place, GeoServiceItem.class));
        server.verify();
    }

    private Cache geocodeCache() {
        return cacheManager.getCache(GeoServiceCacheConfig.GEOCODE_CACHE);
    }

    private ObjectNode featureNode(final double lng, final double lat) {
        final ObjectNode featureNode = OBJECT_MAPPER.createObjectNode();
        final ObjectNode geometryNode = featureNode.putObject("geometry");
        final ArrayNode coordinatesNode = geometryNode.putArray("coordinates");
        coordinatesNode.add(lng);
        coordinatesNode.add(lat);
        return featureNode;
    }

    @SuppressWarnings("unchecked")
    private <T> T invokePrivate(final String methodName,
        final Class<?> parameterType,
        final Object argument) throws ReflectiveOperationException {
        final Method method = GeoServiceClient.class.getDeclaredMethod(methodName, parameterType);
        method.setAccessible(true);
        return (T) method.invoke(client, argument);
    }

    private Level setGeoServiceClientLogLevel(final Level level) {
        final LoggerContext context = (LoggerContext) LogManager.getContext(false);
        final Configuration configuration = context.getConfiguration();
        final LoggerConfig loggerConfig =
            configuration.getLoggerConfig(GeoServiceClient.class.getName());
        final Level originalLevel = loggerConfig.getLevel();

        loggerConfig.setLevel(level);
        context.updateLoggers();
        return originalLevel;
    }
}
