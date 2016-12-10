package org.schoellerfamily.gedbrowser.geocodecache.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.geocodecache.GeoCodeCache;
import org.schoellerfamily.gedbrowser.geocodecache.GeoCodeCacheEntry;

import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

/**
 * @author Dick Schoeller
 */
public final class GeoCodeCacheTest {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /**
     * Table of old and modern addresses for some bigger tests.
     */
    private final String[][] addressTable = {
        {
            "1 Glendale Street, Randolph, Norfolk County, Massachusetts, USA",
            ""
        },
        {
            "13 Highland Street, Revere, Suffolk County, Massachusetts, USA",
            ""
        },
        {
            "1st United Methodist Church, Perkasie, Bucks County, Pennsylvan"
            + "ia, USA",
            ""
        },
        {
            "21 N. Beaver St., York, York County, Pennsylvania, USA",
            ""
        },
        {
            "26 North Beaver Street, York, York County, Pennsylvania, USA",
            ""
        },
        {
            "26 Thornton Street, Revere, Suffolk County, Massachusetts, USA",
            ""
        },
        {
            "354 West Philadelphia Street, York, York County, Pennsylvania, "
            + "USA",
            ""
        },
        {
            "376 West Brundage, Sheridan, Sheridan County, Wyoming, USA",
            ""
        },
        {
            "4 miles northeast of Pine Grove, Pine Grove Township, Schuylkil"
            + "l County, Pennsylvania, USA",
            ""
        },
        {
            "417 West King St., York, York County, Pennsylvania, USA",
            ""
        },
        {
            "460 North 4th St., Lebanon, Lebanon County, Pennsylvania, USA",
            ""
        },
        {
            "481 Madison Avenue, York, York County, Pennsylvania, USA",
            ""
        },
        {
            "514 Maple Street, Lebanon, Lebanon County, Pennsylvania, USA",
            ""
        },
        {
            "7105 Penarth Avenue, Bywood, Upper Darby Township, Delaware Cou"
            + "nty, Pennsylvania, USA",
            ""
        },
        {
            "7105 Pennock Avenue, Bywood, Upper Darby Township, Delaware Cou"
            + "nty, Pennsylvania, USA", ""
        },
        {
            "741 West Philadelphia St., York, York County, Pennsylvania, USA",
            ""
        },
        {
             "757 Kohn Street, Norristown, Montgomery County, Pennsylvania, "
             + "USA",
             ""
        },
        {
             "830 South Davis Boulevard, Tampa, Hillsborough County, Florida"
             + ", USA",
             ""
        },
        {
            "Abington Hospital, Abington, Montgomery County, Pennsylvania, U"
            + "SA",
            ""
        },
        {
            "Abington, Montgomery County, Pennsylvania, USA",
            ""
        },
        {
            "Abtweiler, Pfalz",
            ""
        },
        {
            "Achenbach, Nassau-Siegen",
            ""
        },
        {
            "Adams County, Pennsylvania, USA",
            ""
        },
        {
            "Adelo, Illinois, USA",
            ""
        },
        {
            "Aistaig, Württemberg",
            ""
        },
        {
            "Albright College, Reading, Berks County, Pennsylvania, USA",
            ""
        },
        {
            "Albrightsville, Carbon County, Pennsylvania, USA",
            ""
        },
        {
            "Allentown, Lehigh County, Pennsylvania, USA", ""
        },
        {
            "Alpirsbach, Württemberg", ""
        },
        {
            "Alsace, France", ""
        },
        {
            "Altalaha Lutheran Cemetery, Rehrersburg, Berks County, Pennsylv"
            + "ania, USA",
            ""
        },
        {
            "Altalaha, Rehrersburg, Berks County, Pennsylvania, USA",
            ""
        },
        {
            "America",
            ""
        },
        {
            "Amityville, Nassau County, New York, USA",
            ""
        },
        {
            "Amsterdam, Netherlands",
            ""
        },
        {
            "Anaheim, California, USA",
            ""
        },
        {
            "Anamosa Hospital, Anamosa, Jones County, Iowa, USA",
            ""
        },
        {
            "Anamosa, Jones County, Iowa, USA",
            ""
        },
        {
            "Antes Fort, Lycoming County, Pennsylvania, USA",
            ""
        },
        {
            "Argyle, Nova Scotia, Canada",
            ""
        },
        {
            "Arizona, USA",
            ""
        },
        {
            "Arnold, Nottinghamshire, England",
            ""
        },
        {
            "Ash of Normandy, Sussex, England",
            ""
        },
        {
            "Ashland, Jackson County, Oregon, USA",
            ""
        },
        {
            "Ashville, Buncombe County, North Carolina, USA",
            ""
        },
        {
            "At Home, 502 Canal Street, Lebanon, Lebanon County, Pennsylvani"
            + "a, USA",
            ""
        },
        {
            "At Sea",
            ""
        },
        {
            "At home, 1.5 miles northwest of Sutliff, Solon, Cedar County, I"
            + "owa, USA",
            ""
        },
        {
            "At home, 21 N. Beaver St., York, York County, Pennsylvania, USA",
            ""
        },
        {
            "At home, Cornwall Road, Lebanon, Lebanon County, Pennsylvania, "
            + "USA",
            ""
        },
        {
            "At home, RD 2, Lebanon, Lebanon County, Pennsylvania, USA",
            ""
        },
        {
            "At home, Sheridan, Sheridan County, Wyoming, USA",
            ""
        },
        {
            "At home, Solon, Cedar County, Iowa, USA",
            ""
        },
        {
            "At home, Souderton, Montgomery County, Pennsylvania, USA",
            ""
        },
        {
            "At home, Tega Cay, York County, South Carolina, USA",
            ""
        },
        {
            "At home, Thanheim, Hohenzollern",
            ""
        },
    };

    /**
     * Force debug logging during tests.
     */
    @BeforeClass
    public static void setup() {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug");
    }

    /**
     */
    @Test
    public void testStupidNotFound() {
        logger.info("Entering testStupidNotFound");
        GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        GeoCodeCacheEntry entry = gcc.find("XYZZY");
        Assert.assertNull(entry.getGeocodingResult());
    }

    /**
     */
    @Test
    public void testOldHomeFound() {
        logger.info("Entering testOldHomeFound");
        GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        GeoCodeCacheEntry entry = gcc
                .find("3341 Chaucer Lane, Bethlehem, PA, USA");
        Assert.assertNotNull("geocoding result should not be null",
                entry.getGeocodingResult());
    }

    /**
     */
    @Test
    public void testCacheStupid() {
        logger.info("Entering testCacheStupid");
        GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        GeoCodeCacheEntry entry1 = gcc.find("XYZZY");
        GeoCodeCacheEntry entry2 = gcc.find("XYZZY");
        Assert.assertSame(entry1, entry2);
    }

    /**
     */
    @Test
    public void testCacheOldHome() {
        logger.info("Entering testCacheOldHome");
        GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        GeoCodeCacheEntry entry1 = gcc
                .find("3341 Chaucer Lane, Bethlehem, PA, USA");
        GeoCodeCacheEntry entry2 = gcc
                .find("3341 Chaucer Lane, Bethlehem, PA, USA");
        Assert.assertSame(entry1, entry2);
    }

    /**
     */
    @Test
    public void testCacheOldHomeModern() {
        logger.info("Entering testCacheOldHomeModern");
        GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        GeoCodeCacheEntry entry1 = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        GeoCodeCacheEntry entry2 = gcc.find("Old Home");
        Assert.assertSame(entry1, entry2);
    }

    /**
     */
    @Test
    public void testCacheOldHomeModernBoth() {
        logger.info("Entering testCacheOldHomeModernBoth");
        GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        GeoCodeCacheEntry entry1 = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        GeoCodeCacheEntry entry2 = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        Assert.assertSame(entry1, entry2);
    }

    /**
     */
    @Test
    public void testCacheOldHomeModernChange() {
        logger.info("Entering testCacheOldHomeModernChange");
        GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        GeoCodeCacheEntry entry1 = gcc.find("Old Home");
        GeoCodeCacheEntry entry2 = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        Assert.assertNotSame(entry1, entry2);
    }

    /**
     */
    @Test
    public void testCacheOldHomeModernSet() {
        logger.info("Entering testCacheOldHomeModernSet");
        GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        GeoCodeCacheEntry entry1 = gcc.find("Old Home");
        GeoCodeCacheEntry entry2 = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        Assert.assertNotSame(entry1, entry2);
        GeoCodeCacheEntry entry3 = gcc.find("Old Home");
        Assert.assertSame(entry2, entry3);
    }

    /**
     */
    @Test
    public void testCacheOldHomeLocation() {
        logger.info("Entering testCacheOldHomeLocation");
        GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        GeoCodeCacheEntry entry = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        final LatLng expected = new LatLng(40.65800200, -75.40644300);
        final GeocodingResult geocodingResult = entry.getGeocodingResult();
        Assert.assertNotNull("geocoding result should not be null",
                geocodingResult);
        final LatLng actual = geocodingResult.geometry.location;
        Assert.assertEquals(expected.toString(), actual.toString());
    }

    /**
     */
    @Test
    public void testNotFounds() {
        logger.info("Entering testNotFounds");
        GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        gcc.load(addressTable);
        final int expected = 19;
        Assert.assertEquals(expected, gcc.countNotFound());
    }

    /**
     */
    @Test
    public void testSize() {
        logger.info("Entering testSize");
        GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        gcc.load(addressTable);
        final int expected = 56;
        Assert.assertEquals(expected, gcc.size());
    }

    /**
     */
    @Test
    public void testDump() {
        logger.info("Entering testDump");
        GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        gcc.load(addressTable);
        gcc.dump();
    }

//    /**
//     */
//    @Test
//    public void testLoadDump() {
//        logger.info("Entering testLoadDump");
//        GeoCodeCache gcc = GeoCodeCache.instance();
//        gcc.clear();
//        gcc.load();
//        Assert.assertEquals(962, gcc.size());
//        gcc.dump();
//    }
}
