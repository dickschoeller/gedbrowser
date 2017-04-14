package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.renderer.ApplicationInfo;

/**
 * @author Dick Schoeller
 */
public class ApplicationInfoTest {
    /** */
    private ApplicationInfo appInfo;

    /** */
    @Before
    public void init() {
        appInfo = new ApplicationInfo() {
            /**
             * {@inheritDoc}
             */
            @Override
            public String getName() {
                return "renderer";
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public String getVersion() {
                return "1";
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public String getMaintainerEmail() {
                return "schoeller@comcast.net";
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public String getMaintainerName() {
                return "Richard Schoeller";
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public String getApplicationURL() {
                return "https://www.schoellerfamily.org/";
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public String getHomeURL() {
                return "https://www.schoellerfamily.org/";
            }
        };
    }

    /** */
    @Test
    public void testGetInfoMapName() {
        Map<String, Object> infoMap = appInfo.getInfoMap();
        assertEquals("Name mismatch", infoMap.get("name"), "renderer");
    }

    /** */
    @Test
    public void testGetInfoMapVersion() {
        Map<String, Object> infoMap = appInfo.getInfoMap();
        assertEquals("Version mismatch", infoMap.get("version"), "1");
    }

    /** */
    @Test
    public void testGetInfoMapURL() {
        Map<String, Object> infoMap = appInfo.getInfoMap();
        assertEquals("URL mismatch",
                infoMap.get("URL"), "https://www.schoellerfamily.org/");
    }

    /** */
    @Test
    public void testGetInfoMapMaintainerName() {
        Map<String, Object> infoMap = appInfo.getInfoMap();
        Map<?, ?> maintainerMap = (Map<?, ?>) infoMap.get("maintainer");
        assertEquals("Maintainer name mismatch",
                maintainerMap.get("name"), "Richard Schoeller");
    }

    /** */
    @Test
    public void testGetInfoMap() {
        Map<String, Object> infoMap = appInfo.getInfoMap();
        Map<?, ?> maintainerMap = (Map<?, ?>) infoMap.get("maintainer");
        assertEquals("Maintainer email mismatch",
                maintainerMap.get("email"), "schoeller@comcast.net");
    }
}
