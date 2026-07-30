package org.schoellerfamily.geoservice.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.geoservice.controller.ApplicationInfo;

/**
 * Contains tests for application info.
 *
 * @author Richard Schoeller
 */
public final class ApplicationInfoTest {
    /** Test target. */
    private final transient ApplicationInfo appInfo = new ApplicationInfo();

    @Test
    void testApplicationInfoURL() {
        assertEquals(
                "https://github.com/dickschoeller/gedbrowser",
                appInfo.getApplicationURL(),
                "Application URL mismatch");
    }

    @Test
    void testApplicationInfoMaintainerEmail() {
        assertEquals(
                "schoeller@comcast.net",
                appInfo.getMaintainerEmail(),
                "Maintainer email mismatch");
    }

    @Test
    void testApplicationInfoMaintainerName() {
        assertEquals(
                "Richard Schoeller",
                appInfo.getMaintainerName(),
                "Maintainer name mismatch");
    }

    @Test
    void testApplicationInfoName() {
        assertEquals(
                "gedbrowser-geoservice",
                appInfo.getName(),
                "Application name mismatch");
    }

    @Test
    void testApplicationInfoVersion() {
        assertEquals(
                "1.3.0-RC3-SNAPSHOT",
                appInfo.getVersion(),
                "Version mismatch");
    }
}
