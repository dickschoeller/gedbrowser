package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;

/**
 * @author Dick Schoeller
 */
public class ApplicationInfoTest {
    /** */
    private ApplicationInfo appInfo;

    /** */
    @BeforeEach
    public void setUp() {
        appInfo = new ApplicationInfo() {
            @Override
            public String getApplicationName() {
                return "renderer";
            }

            @Override
            public String getVersion() {
                return "1";
            }

            @Override
            public String getMaintainerEmail() {
                return "schoeller@comcast.net";
            }

            @Override
            public String getMaintainerName() {
                return "Richard Schoeller";
            }

            @Override
            public String getApplicationURL() {
                return "https://www.schoellerfamily.org/";
            }

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
