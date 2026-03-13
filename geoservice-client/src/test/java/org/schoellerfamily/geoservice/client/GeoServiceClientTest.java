package org.schoellerfamily.geoservice.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.Point;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.geoservice.model.GeoServiceItem;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.RequestMatcher;
import org.springframework.web.client.RestClient;

final class GeoServiceClientTest {

    @Test
    void testGetFallsBackWhenPrimaryDeserializationFails() {
        final RestClient.Builder builder = RestClient.builder();
        final MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
        final GeoServiceClient client = new GeoServiceClient(builder.build(), "localhost", 8080, "http");

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
        assertEquals(42.0, point.getCoordinates().getLatitude());
        assertEquals(-71.0, point.getCoordinates().getLongitude());

        server.verify();
    }

    @Test
    void testGetReturnsDefaultItemWhenFallbackAlsoFails() {
        final RestClient.Builder builder = RestClient.builder();
        final MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
        final GeoServiceClient client = new GeoServiceClient(builder.build(), "localhost", 8080, "http");

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
}
