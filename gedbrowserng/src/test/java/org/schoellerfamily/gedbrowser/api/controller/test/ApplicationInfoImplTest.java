package org.schoellerfamily.gedbrowser.api.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.controller.ApplicationInfoImpl;

/**
 * Contains tests for application info impl.
 */
final class ApplicationInfoImplTest {

    @Test
    void testApplicationInfoName() {
        final ApplicationInfoImpl info = new ApplicationInfoImpl(
            "dev@example.com",
            "Developer",
            "https://example.com/app",
            "https://example.com/home");

        assertEquals("gedbrowserng", info.getApplicationName());
    }

    @Test
    void testMaintainerEmail() {
        final ApplicationInfoImpl info = new ApplicationInfoImpl(
            "dev@example.com",
            "Developer",
            "https://example.com/app",
            "https://example.com/home");

        assertEquals("dev@example.com", info.getMaintainerEmail());
    }

    @Test
    void testMaintainerName() {
        final ApplicationInfoImpl info = new ApplicationInfoImpl(
            "dev@example.com",
            "Developer",
            "https://example.com/app",
            "https://example.com/home");

        assertEquals("Developer", info.getMaintainerName());
    }

    @Test
    void testApplicationUrl() {
        final ApplicationInfoImpl info = new ApplicationInfoImpl(
            "dev@example.com",
            "Developer",
            "https://example.com/app",
            "https://example.com/home");

        assertEquals("https://example.com/app", info.getApplicationURL());
    }

    @Test
    void testHomeUrl() {
        final ApplicationInfoImpl info = new ApplicationInfoImpl(
            "dev@example.com",
            "Developer",
            "https://example.com/app",
            "https://example.com/home");

        assertEquals("https://example.com/home", info.getHomeURL());
    }
}
