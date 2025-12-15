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
    public void init() {
        appInfo = new ApplicationInfo() {
            /** {@inheritDoc} */
            @Override
            public String getApplicationName() {
                return "renderer";
            }

            /** {@inheritDoc} */
            @Override
            public String getVersion() {
                return "1";
            }

            /** {@inheritDoc} */
            @Override
            public String getMaintainerEmail() {
                return "schoeller@comcast.net";
            }

            /** {@inheritDoc} */
            @Override
            public String getMaintainerName() {
                return "Richard Schoeller";
            }

            /** {@inheritDoc} */
            @Override
            public String getApplicationURL() {
                return "https://www.schoellerfamily.org/";
            }

            /** {@inheritDoc} */
            @Override
            public String getHomeURL() {
                return "https://www.schoellerfamily.org/";
            }
        };
    }

    /** */
    @Test
    public void testGetInfoMapName() {
        final Map<String, Object> infoMap = appInfo.getInfoMap();
        assertEquals("renderer", infoMap.get("name"), "Name mismatch");
    }

    /** */
    @Test
    public void testGetInfoMapVersion() {
        final Map<String, Object> infoMap = appInfo.getInfoMap();
        assertEquals("1", infoMap.get("version"), "Version mismatch");
    }

    /** */
    @Test
    public void testGetInfoMapURL() {
        final Map<String, Object> infoMap = appInfo.getInfoMap();
        assertEquals("https://www.schoellerfamily.org/",
                infoMap.get("URL"), "URL mismatch");
    }

    /** */
    @Test
    public void testGetInfoMapMaintainerName() {
        final Map<String, Object> infoMap = appInfo.getInfoMap();
        final Map<?, ?> maintainerMap = (Map<?, ?>) infoMap.get("maintainer");
        assertEquals("Richard Schoeller",
                maintainerMap.get("name"), "Maintainer name mismatch");
    }

    /** */
    @Test
    public void testGetInfoMap() {
        final Map<String, Object> infoMap = appInfo.getInfoMap();
        final Map<?, ?> maintainerMap = (Map<?, ?>) infoMap.get("maintainer");
        assertEquals("schoeller@comcast.net",
                maintainerMap.get("email"), "Maintainer email mismatch");
    }
}