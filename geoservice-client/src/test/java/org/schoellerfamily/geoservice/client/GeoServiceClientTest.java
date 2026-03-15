package org.schoellerfamily.geoservice.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.schoellerfamily.geoservice.model.GeoServiceItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.RequestMatcher;
import org.springframework.web.client.RestClient;

import com.google.maps.model.AddressType;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.ObjectNode;

/**
 * Tests for {@link GeoServiceClient}.
 */
@SuppressWarnings({ "PMD.UnitTestContainsTooManyAsserts", "PMD.TooManyMethods" })
final class GeoServiceClientTest {

  /** */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  /** */
  private static final String HOST = "localhost";

  /** */
  private static final int PORT = 8080;

  /** */
  private static final String PROTOCOL = "http";

  /** */
  private static final double EXPECTED_LATITUDE = 42.0;

  /** */
  private static final double EXPECTED_LONGITUDE = -71.0;

    @Test
    void testGetReturnsPrimaryResponseWhenDeserializationSucceeds() {
        final RestClient.Builder builder = RestClient.builder();
        final MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
      final GeoServiceClient client = new GeoServiceClient(
        builder.build(),
        HOST,
        PORT,
        PROTOCOL);
  client.initCache();

        final String place = "Primary Place";
        final String encoded = URLEncoder.encode(place, StandardCharsets.UTF_8);
        final String url = "http://localhost:8080/geocode?name=" + encoded;

        final RequestMatcher requestToUrl = request -> {
          assertEquals(url, request.getURI().toString());
          assertEquals("GET", request.getMethod().name());
        };

        server.expect(requestToUrl)
            .andRespond(withSuccess(
                """
                {
                  "placeName":"Primary Place",
                  "modernPlaceName":"Primary Modern Place",
                  "result": null
                }
                """,
                MediaType.APPLICATION_JSON));

        final GeoServiceItem item = client.get(place);

        assertNotNull(item);
        assertEquals("Primary Place", item.getPlaceName());
        assertEquals("Primary Modern Place", item.getModernPlaceName());
        assertNull(item.getResult());

        server.verify();
    }

      @Test
      void testGetReturnsCachedItemWithoutCallingBackend()
          throws ReflectiveOperationException {
        final GeoServiceClient client = newClient();
        final PlaceCache cache = Mockito.mock(PlaceCache.class);
        final GeoServiceItem cached =
          new GeoServiceItem("Cached Place", "Cached Modern Place", null);
        Mockito.when(cache.get("Cached Place")).thenReturn(cached);

        setPrivateField(client, "geocodeCache", cache);

        final GeoServiceItem item = client.get("Cached Place");

        assertEquals(cached, item);
        Mockito.verify(cache).get("Cached Place");
        Mockito.verify(cache, Mockito.never()).put(Mockito.anyString(), Mockito.any());
      }

      @Test
      void testGetUsesDirectFetchWhenCallExecutorIsNull() throws ReflectiveOperationException {
        final RestClient.Builder builder = RestClient.builder();
        final MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
        final GeoServiceClient client = new GeoServiceClient(
          builder.build(),
          HOST,
          PORT,
          PROTOCOL);
        client.initCache();
        setPrivateField(client, "callExecutor", null);

        final String place = "Direct Place";
        final String encoded = URLEncoder.encode(place, StandardCharsets.UTF_8);
        final String url = "http://localhost:8080/geocode?name=" + encoded;

        final RequestMatcher requestToUrl = request -> {
          assertEquals(url, request.getURI().toString());
          assertEquals("GET", request.getMethod().name());
        };

        server.expect(requestToUrl)
          .andRespond(withSuccess(
            """
            {
              "placeName":"Direct Place",
              "modernPlaceName":"Direct Modern Place",
              "result": null
            }
            """,
            MediaType.APPLICATION_JSON));

        final GeoServiceItem item = client.get(place);

        assertNotNull(item);
        assertEquals("Direct Place", item.getPlaceName());
        assertEquals("Direct Modern Place", item.getModernPlaceName());
        assertNull(item.getResult());
        server.verify();
      }

    @Test
    void testGetFallsBackWhenPrimaryDeserializationFails() {
        final RestClient.Builder builder = RestClient.builder();
        final MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
      final GeoServiceClient client = new GeoServiceClient(
        builder.build(),
        HOST,
        PORT,
        PROTOCOL);
        client.initCache();

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

        // First request fails primary mapping and triggers fallback parser.
        final RequestMatcher requestToUrl = request -> {
          assertEquals(url, request.getURI().toString());
          assertEquals("GET", request.getMethod().name());
        };

        server.expect(requestToUrl)
            .andRespond(withSuccess("{not-json", MediaType.APPLICATION_JSON));

        // Second request is used by fallback parser as raw JSON.
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
        final RestClient.Builder builder = RestClient.builder();
        final MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
      final GeoServiceClient client = new GeoServiceClient(
        builder.build(),
        HOST,
        PORT,
        PROTOCOL);
        client.initCache();

        final String place = "Nowhere";
        final String encoded = URLEncoder.encode(place, StandardCharsets.UTF_8);
        final String url = "http://localhost:8080/geocode?name=" + encoded;

        final RequestMatcher requestToUrl = request -> {
          assertEquals(url, request.getURI().toString());
          assertEquals("GET", request.getMethod().name());
        };

        server.expect(requestToUrl)
            .andRespond(withSuccess("{not-json", MediaType.APPLICATION_JSON));

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
    final RestClient.Builder builder = RestClient.builder();
    final MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
    final GeoServiceClient client = new GeoServiceClient(
      builder.build(),
      HOST,
      PORT,
      PROTOCOL);
    client.initCache();

    final String place = "Broken Payload";
    final String encoded = URLEncoder.encode(place, StandardCharsets.UTF_8);
    final String url = "http://localhost:8080/geocode?name=" + encoded;

    final RequestMatcher requestToUrl = request -> {
      assertEquals(url, request.getURI().toString());
      assertEquals("GET", request.getMethod().name());
    };

    server.expect(requestToUrl)
      .andRespond(withSuccess("{not-json", MediaType.APPLICATION_JSON));

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
    final RestClient.Builder builder = RestClient.builder();
    final MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
    final GeoServiceClient client = new GeoServiceClient(
      builder.build(),
      HOST,
      PORT,
      PROTOCOL);
    client.initCache();

    final String place = "Missing Payload";
    final String encoded = URLEncoder.encode(place, StandardCharsets.UTF_8);
    final String url = "http://localhost:8080/geocode?name=" + encoded;

    final RequestMatcher requestToUrl = request -> {
      assertEquals(url, request.getURI().toString());
      assertEquals("GET", request.getMethod().name());
    };

    server.expect(requestToUrl)
      .andRespond(withSuccess("{not-json", MediaType.APPLICATION_JSON));

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
    final RestClient.Builder builder = RestClient.builder();
    final MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
    final GeoServiceClient client = new GeoServiceClient(
      builder.build(),
      HOST,
      PORT,
      PROTOCOL);
    client.initCache();

    final String place = "Empty Primary";
    final String encoded = URLEncoder.encode(place, StandardCharsets.UTF_8);
    final String url = "http://localhost:8080/geocode?name=" + encoded;

    final RequestMatcher requestToUrl = request -> {
      assertEquals(url, request.getURI().toString());
      assertEquals("GET", request.getMethod().name());
    };

    // Primary fetch returns HTTP 204, which yields a null body.
    server.expect(requestToUrl)
      .andRespond(withStatus(HttpStatus.NO_CONTENT));

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
      final RestClient.Builder builder = RestClient.builder();
      final MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
      final GeoServiceClient client = new GeoServiceClient(
        builder.build(),
        HOST,
        PORT,
        PROTOCOL);
      client.initCache();

      final String place = "Debug Payload";
      final String encoded = URLEncoder.encode(place, StandardCharsets.UTF_8);
      final String url = "http://localhost:8080/geocode?name=" + encoded;

      final RequestMatcher requestToUrl = request -> {
        assertEquals(url, request.getURI().toString());
        assertEquals("GET", request.getMethod().name());
      };

      server.expect(requestToUrl)
        .andRespond(withSuccess("{not-json", MediaType.APPLICATION_JSON));

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
        final GeoServiceClient client = newClient();
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
            client,
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
        final GeoServiceClient client = newClient();
        final FeatureCollection geometry = invokePrivate(
            client,
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
        final GeoServiceClient client = newClient();
        final FeatureCollection geometry = invokePrivate(
          client,
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
        final GeoServiceClient client = newClient();

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

        assertNull(invokePrivate(client, "toLocationFeature", JsonNode.class, missingArrayNode));
        assertNull(invokePrivate(client, "toLocationFeature", JsonNode.class, shortArrayNode));
        assertNull(invokePrivate(client, "toLocationFeature", JsonNode.class, nonFiniteNode));
    }

      @Test
      void testToLocationFeatureRejectsInvalidLatitudeValues()
          throws ReflectiveOperationException {
        final GeoServiceClient client = newClient();
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
          client,
          "toLocationFeature",
          JsonNode.class,
          nonNumericLatitudeNode));
        assertNull(invokePrivate(
          client,
          "toLocationFeature",
          JsonNode.class,
          nonFiniteLatitudeNode));
      }

    @Test
    void testParseAddressTypesHandlesUnsupportedInputs()
            throws ReflectiveOperationException {
        final GeoServiceClient client = newClient();

        final AddressType[] notArrayTypes = invokePrivate(
          client,
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
            client,
            "parseAddressTypes",
            JsonNode.class,
            invalidTypesNode);

        assertNotNull(addressTypes);
        assertEquals(0, addressTypes.length);
    }

    @Test
    void testParseStringArrayAndTextValueHandleEmptyInputs()
            throws ReflectiveOperationException {
        final GeoServiceClient client = newClient();

        final String[] notArrayStrings = invokePrivate(
          client,
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
          client,
          "parseStringArray",
          JsonNode.class,
          invalidStringsNode);
        assertNotNull(invalidStrings);
        assertEquals(0, invalidStrings.length);

        assertNull(invokePrivate(client, "textValue", JsonNode.class, null));
        assertNull(invokePrivate(client, "textValue", JsonNode.class, OBJECT_MAPPER.nullNode()));
        assertNull(invokePrivate(
            client,
            "textValue",
            JsonNode.class,
            OBJECT_MAPPER.createObjectNode()));
        assertNull(invokePrivate(
            client,
            "textValue",
            JsonNode.class,
            OBJECT_MAPPER.readTree("\"   \"")));
    }

    @Test
    void testTextValueReturnsNullWhenStringValueIsNull()
            throws ReflectiveOperationException {
        final GeoServiceClient client = newClient();
        final JsonNode nullStringNode = Mockito.mock(JsonNode.class);

        Mockito.when(nullStringNode.isNull()).thenReturn(false);
        Mockito.when(nullStringNode.isValueNode()).thenReturn(true);
        Mockito.when(nullStringNode.asString()).thenReturn(null);

        assertNull(invokePrivate(
            client,
            "textValue",
            JsonNode.class,
            nullStringNode));
    }

    private GeoServiceClient newClient() {
        final GeoServiceClient client =
          new GeoServiceClient(RestClient.builder().build(), HOST, PORT, PROTOCOL);
        client.initCache();
        return client;
    }

    @Test
    void testDestroyCacheClosesUnderlyingCacheWithoutThrowing() {
        final GeoServiceClient client = newClient();
        // Should close the EhcachePlaceCache without throwing.
      assertDoesNotThrow(client::destroyCache);
    }

    @Test
    void testDestroyCacheInvokesCloseOnMockedGeocodeCache() throws ReflectiveOperationException {
        final GeoServiceClient client =
            new GeoServiceClient(RestClient.builder().build(), HOST, PORT, PROTOCOL);

        final PlaceCache mockCache = Mockito.mock(PlaceCache.class);
        final java.lang.reflect.Field field =
            GeoServiceClient.class.getDeclaredField("geocodeCache");
        field.setAccessible(true);
        field.set(client, mockCache);

        client.destroyCache();

        Mockito.verify(mockCache).close();
    }

    @Test
    void testDestroyCacheDoesNotThrowWhenCacheIsNull() {
        final GeoServiceClient client =
            new GeoServiceClient(RestClient.builder().build(), HOST, PORT, PROTOCOL);
        // geocodeCache remains null (initCache not called); destroyCache should be safe.
      assertDoesNotThrow(client::destroyCache);
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
    private <T> T invokePrivate(final GeoServiceClient client,
            final String methodName,
            final Class<?> parameterType,
            final Object argument) throws ReflectiveOperationException {
        final Method method = GeoServiceClient.class.getDeclaredMethod(methodName, parameterType);
        method.setAccessible(true);
        return (T) method.invoke(client, argument);
    }

      private void setPrivateField(final GeoServiceClient client,
          final String fieldName,
          final Object value) throws ReflectiveOperationException {
        final java.lang.reflect.Field field =
          GeoServiceClient.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(client, value);
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
