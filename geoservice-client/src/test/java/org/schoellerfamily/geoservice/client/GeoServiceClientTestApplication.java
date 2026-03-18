package org.schoellerfamily.geoservice.client;

import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Minimal Spring Boot application used to bootstrap the test context for
 * {@link GeoServiceClientTest}. Scope is explicitly limited to the client
 * package to avoid picking up Spring components from dependency modules.
 */
@SpringBootApplication(scanBasePackages = "org.schoellerfamily.geoservice.client")
class GeoServiceClientTestApplication {
}
