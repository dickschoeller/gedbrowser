package org.schoellerfamily.gedbrowser.api.controller;

import java.util.Map;

import org.schoellerfamily.geoservice.keys.KeyManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Exposes Google Maps key for frontend map rendering.
 */
@CrossOrigin(origins = {
        "http://largo.schoellerfamily.org:4200", "http://localhost:4200" })
@RestController
@RequiredArgsConstructor
@Slf4j
public class MapKeyController {
    /** */
    private final KeyManager keyManager;

    /**
     * Return the maps key used by the frontend Google Maps script.
     *
     * @return a map containing the key string, or 500 on failure
     */
    @GetMapping(value = "/v1/map-key")
    public ResponseEntity<Map<String, String>> readMapKey() {
        try {
            return ResponseEntity.ok(Map.of("key", keyManager.getMapsKey()));
        } catch (RuntimeException e) {
            log.error("Couldn't get maps key", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
