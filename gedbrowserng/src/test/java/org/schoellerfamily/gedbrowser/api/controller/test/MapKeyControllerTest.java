package org.schoellerfamily.gedbrowser.api.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.controller.MapKeyController;
import org.schoellerfamily.geoservice.keys.KeyManager;
import org.springframework.http.ResponseEntity;

final class MapKeyControllerTest {

    @Test
    void testReadMapKeyReturnsKeyWhenManagerSucceeds() {
        final KeyManager keyManager = new KeyManager() {
            @Override
            public String getGeocodingKey() {
                return "GEO";
            }

            @Override
            public String getMapsKey() {
                return "MAP-KEY";
            }
        };

        final MapKeyController controller = new MapKeyController(keyManager);

        final ResponseEntity<Map<String, String>> response = controller.readMapKey();

        assertEquals(200, response.getStatusCode().value());
        assertEquals("MAP-KEY", response.getBody().get("key"));
    }

    @Test
    void testReadMapKeyReturns500WhenManagerThrows() {
        final KeyManager keyManager = new KeyManager() {
            @Override
            public String getGeocodingKey() {
                return "GEO";
            }

            @Override
            public String getMapsKey() {
                throw new IllegalStateException("missing key");
            }
        };

        final MapKeyController controller = new MapKeyController(keyManager);

        final ResponseEntity<Map<String, String>> response = controller.readMapKey();

        assertEquals(500, response.getStatusCode().value());
        assertNull(response.getBody());
    }
}
