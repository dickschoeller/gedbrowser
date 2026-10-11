package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;

/**
 * Contains tests for application info.
 *
 * @author Richard Schoeller
 */
class ApplicationInfoTest {
    /** */
    private ApplicationInfo appInfo;

    @BeforeEach
    void setUp() {
        appInfo = new ApplicationInfo() {
            /**
             * Gets the application name.
             *
             * @return the application name
             */
            @Override
            public String getApplicationName() {
                return "renderer";
            }

            /**
             * Gets the version.
             *
             * @return the version
             */
            @Override
            public String getVersion() {
                return "1";
            }

            /**
             * Gets the maintainer email.
             *
             * @return the maintainer email
             */
            @Override
            public String getMaintainerEmail() {
                return "schoeller@comcast.net";
            }

            /**
             * Gets the maintainer name.
             *
             * @return the maintainer name
             */
            @Override
            public String getMaintainerName() {
                return "Richard Schoeller";
            }

            /**
             * Gets the application u r l.
             *
             * @return the application u r l
             */
            @Override
            public String getApplicationURL() {
                return "https://www.schoellerfamily.org/";
            }

            /**
             * Gets the home u r l.
             *
             * @return the home u r l
             */
            @Override
            public String getHomeURL() {
                return "https://www.schoellerfamily.org/";
            }
        };
    }

    @Test
    void testGetInfoMapName() {
        final Map<String, Object> infoMap = appInfo.getInfoMap();
        assertEquals("renderer", infoMap.get("name"), "Name mismatch");
    }

    @Test
    void testGetInfoMapVersion() {
        final Map<String, Object> infoMap = appInfo.getInfoMap();
        assertEquals("1", infoMap.get("version"), "Version mismatch");
    }

    @Test
    void testGetInfoMapURL() {
        final Map<String, Object> infoMap = appInfo.getInfoMap();
        assertEquals("https://www.schoellerfamily.org/", infoMap.get("URL"), "URL mismatch");
    }

    @Test
    void testGetInfoMapMaintainerName() {
        final Map<String, Object> infoMap = appInfo.getInfoMap();
        final Map<?, ?> maintainerMap = (Map<?, ?>) infoMap.get("maintainer");
        assertEquals("Richard Schoeller", maintainerMap.get("name"), "Maintainer name mismatch");
    }

    @Test
    void testGetInfoMap() {
        final Map<String, Object> infoMap = appInfo.getInfoMap();
        final Map<?, ?> maintainerMap = (Map<?, ?>) infoMap.get("maintainer");
        assertEquals("schoeller@comcast.net", maintainerMap.get("email"),
            "Maintainer email mismatch");
    }
}
