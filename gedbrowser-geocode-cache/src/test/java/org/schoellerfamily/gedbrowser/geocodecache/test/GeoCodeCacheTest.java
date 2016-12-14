package org.schoellerfamily.gedbrowser.geocodecache.test;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.CommentSize" })
public final class GeoCodeCacheTest {
    /**
     * High bound for not founds.
     * Googles responses are sufficiently inconsistent that we can't
     * rely on an exact count.
     */
    private static final int HIGH_BOUND = 20;

    /**
     * Low bound for not founds.
     * Googles responses are sufficiently inconsistent that we can't
     * rely on an exact count.
     */
    private static final int LOW_BOUND = 10;

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
            "502 Canal Street, Lebanon, Lebanon County, Pennsylvania, USA"
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
            "21 N. Beaver St., York, York County, Pennsylvania, USA"
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
     * Table of values we know should be not found.
     */
    private final String[] expectedNotFound = {
        "1st United Methodist Church, Perkasie, Bucks County, Pennsylvania, US"
        + "A",
        "Abington Hospital, Abington, Montgomery County, Pennsylvania, USA",
        "Achenbach, Nassau-Siegen",
        "Adelo, Illinois, USA",
        "Albright College, Reading, Berks County, Pennsylvania, USA",
        "Altalaha Lutheran Cemetery, Rehrersburg, Berks County, Pennsylvania, "
        + "USA",
        "Altalaha, Rehrersburg, Berks County, Pennsylvania, USA",
        "Anamosa Hospital, Anamosa, Jones County, Iowa, USA",
        "Ash of Normandy, Sussex, England",
        "At Sea",
        "At home, 1.5 miles northwest of Sutliff, Solon, Cedar County, Iowa, U"
        + "SA",
        "At home, Cornwall Road, Lebanon, Lebanon County, Pennsylvania, USA",
        "At home, RD 2, Lebanon, Lebanon County, Pennsylvania, USA",
        "At home, Souderton, Montgomery County, Pennsylvania, USA",
        "At home, Tega Cay, York County, South Carolina, USA",
        "At home, Thanheim, Hohenzollern"
    };

    /**
     * Force debug logging during tests.
     */
    @BeforeClass
    public static void setUp() {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug");
    }

    /**
     */
    @Test
    public void testStupidNotFound() {
        logger.info("Entering testStupidNotFound");
        final GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        final GeoCodeCacheEntry entry = gcc.find("XYZZY");
        Assert.assertNull("Should not have found XYZZY",
                entry.getGeocodingResult());
    }

    /**
     */
    @Test
    public void testModernStupidNotFound() {
        logger.info("Entering testModernStupidNotFound");
        final GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        final GeoCodeCacheEntry entry = gcc.find("PLUGH", "XYZZY");
        Assert.assertNull("Should not have found modern XYZZY",
                entry.getGeocodingResult());
    }

    /**
     */
    @Test
    public void testOldHomeFound() {
        logger.info("Entering testOldHomeFound");
        final GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        final GeoCodeCacheEntry entry = gcc
                .find("3341 Chaucer Lane, Bethlehem, PA, USA");
        Assert.assertNotNull("Should have found 3341 Chaucer Lane",
                entry.getGeocodingResult());
    }

    /**
     */
    @Test
    public void testCacheStupid() {
        logger.info("Entering testCacheStupid");
        final GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        final GeoCodeCacheEntry entry1 = gcc.find("XYZZY");
        final GeoCodeCacheEntry entry2 = gcc.find("XYZZY");
        Assert.assertEquals("Should be equal", entry1, entry2);
    }

    /**
     */
    @Test
    public void testModernEmpty() {
        logger.info("Entering testModernEmpty");
        final GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        final GeoCodeCacheEntry entry1 = gcc.find("XYZZY");
        final GeoCodeCacheEntry entry2 = gcc.find("XYZZY", "");
        Assert.assertEquals("Should be equal", entry1, entry2);
    }

    /**
     */
    @Test
    public void testModernNull() {
        logger.info("Entering testModernNull");
        final GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        final GeoCodeCacheEntry entry1 = gcc.find("XYZZY");
        final GeoCodeCacheEntry entry2 = gcc.find("XYZZY", null);
        Assert.assertEquals("Should be equal", entry1, entry2);
    }

    /**
     */
    @Test
    public void testCacheFound() {
        logger.info("Entering testCacheFound");
        final GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        final GeoCodeCacheEntry entry1 = gcc
                .find("3341 Chaucer Lane, Bethlehem, Pennsylvania, USA");
        final GeoCodeCacheEntry entry2 = gcc
                .find("3341 Chaucer Lane, Bethlehem, Pennsylvania, USA");
        Assert.assertEquals("Should be equal", entry1, entry2);
    }

    /**
     */
    @Test
    public void testCacheReplace() {
        logger.info("Entering testCacheReplace");
        final GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        gcc.find("XYZZY");
        final GeoCodeCacheEntry entry2 = gcc.find("XYZZY",
                "3341 Chaucer Lane, Bethlehem, Pennsylvania, USA");
        final GeoCodeCacheEntry entry3 = gcc.find("XYZZY");
        Assert.assertEquals("Should be equal", entry2, entry3);
    }

    /**
     */
    @Test
    public void testCacheOldHome() {
        logger.info("Entering testCacheOldHome");
        final GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        final GeoCodeCacheEntry entry1 = gcc
                .find("3341 Chaucer Lane, Bethlehem, PA, USA");
        final GeoCodeCacheEntry entry2 = gcc
                .find("3341 Chaucer Lane, Bethlehem, PA, USA");
        Assert.assertEquals("Should be equal", entry1, entry2);
    }

    /**
     */
    @Test
    public void testCacheOldHomeModern() {
        logger.info("Entering testCacheOldHomeModern");
        final GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        final GeoCodeCacheEntry entry1 = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        final GeoCodeCacheEntry entry2 = gcc.find("Old Home");
        Assert.assertEquals("Should be equal", entry1, entry2);
    }

    /**
     */
    @Test
    public void testCacheOldHomeModernBoth() {
        logger.info("Entering testCacheOldHomeModernBoth");
        final GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        final GeoCodeCacheEntry entry1 = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        final GeoCodeCacheEntry entry2 = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        Assert.assertEquals("Should be equal", entry1, entry2);
    }

    /**
     */
    @Test
    public void testCacheOldHomeModernChange() {
        logger.info("Entering testCacheOldHomeModernChange");
        final GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        final GeoCodeCacheEntry entry1 = gcc.find("Old Home");
        final GeoCodeCacheEntry entry2 = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        Assert.assertNotEquals("Should NOT be equal", entry1, entry2);
    }

    /**
     */
    @Test
    public void testCacheOldHomeModernSet() {
        logger.info("Entering testCacheOldHomeModernSet");
        final GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        gcc.find("Old Home");
        final GeoCodeCacheEntry entry2 = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        final GeoCodeCacheEntry entry3 = gcc.find("Old Home");
        Assert.assertEquals("Should be equal", entry2, entry3);
    }

    /**
     */
    @Test
    public void testCacheOldHomeLocation() {
        logger.info("Entering testCacheOldHomeLocation");
        final GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        final GeoCodeCacheEntry entry = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        final LatLng expected = new LatLng(40.65800200, -75.40644300);
        final GeocodingResult geocodingResult = entry.getGeocodingResult();
        Assert.assertNotNull("geocoding result should not be null",
                geocodingResult);
        final LatLng actual = geocodingResult.geometry.location;
        Assert.assertEquals("there was a result, but the lat/long was wrong",
                expected.toString(), actual.toString());
    }

    /**
     */
    @Test
    public void testNotFounds() {
        logger.info("Entering testNotFounds");
        final GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        gcc.load(addressTable);
        final List<String> expectList = Arrays.asList(this.expectedNotFound);
        final Set<String> expected = new HashSet<>(expectList);
        final Set<String> actual = gcc.notFoundKeys();
        Assert.assertTrue("Some differences in not found sets",
                compareNotFound(expected, actual));
    }

    /**
     */
    @Test
    public void testNotFoundsFromFile() {
        logger.info("Entering testNotFounds");
        final GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        final InputStream fis = getTestFileAsStream();
        gcc.load(fis);
        final List<String> expectList = Arrays.asList(this.expectedNotFound);
        final Set<String> expected = new HashSet<>(expectList);
        final Set<String> actual = gcc.notFoundKeys();
        Assert.assertTrue("Some differences in not found sets",
                compareNotFound(expected, actual));
    }

    /**
     */
    @Test
    public void testCountNotFounds() {
        logger.info("Entering testCountNotFounds");
        final GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        gcc.load(addressTable);
        final int count = gcc.countNotFound();
        // Count does not seem to be deterministic with Google's APIs.
        Assert.assertTrue("Count too low at: " + count, count >= LOW_BOUND);
        Assert.assertTrue("Count too high at: " + count, count <= HIGH_BOUND);
    }

    /**
     */
    @Test
    public void testCountNotFoundsFromFile() {
        logger.info("Entering testCountNotFounds");
        final GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        final InputStream fis = getTestFileAsStream();
        gcc.load(fis);
        final int count = gcc.countNotFound();
        // Count does not seem to be deterministic with Google's APIs.
        Assert.assertTrue("Count too low at: " + count, count >= LOW_BOUND);
        Assert.assertTrue("Count too high at: " + count, count <= HIGH_BOUND);
    }

    /**
     * Check that all entries in expected are also in actual.
     * There may be more in actual than expected because Google
     * is inconsistent.
     *
     * @param expected set of keys we expect Google to not find
     * @param actual keys that Google didn't find
     * @return true if all keys in expected are present in actual
     */
    private boolean compareNotFound(final Set<String> expected,
            final Set<String> actual) {
        boolean retval = true;
        for (final String check : expected) {
            if (!actual.contains(check)) {
                logger.info("Missing not found: " + check);
                retval = false;
            }
        }
        return retval;
    }

    /**
     */
    @Test
    public void testSize() {
        logger.info("Entering testSize");
        final GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        gcc.load(addressTable);
        final int expected = 56;
        Assert.assertEquals("Should match known table size of 56",
                expected, gcc.size());
    }

    /**
     */
    @Test
    public void testSizeFromFile() {
        logger.info("Entering testSizeFromFile");
        final GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        final InputStream fis = getTestFileAsStream();
        gcc.load(fis);
        final int expected = 55;
        Assert.assertEquals("Should match known file size of 55",
                expected, gcc.size());
    }

    /**
     */
    @Test
    public void testDump() {
        logger.info("Entering testDump");
        final GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        gcc.load(addressTable);
        gcc.dump();
        Assert.assertTrue("Always pass", true);
    }

    /**
     */
    @Test
    public void testDumpFromFile() {
        logger.info("Entering testDumpFile");
        final GeoCodeCache gcc = GeoCodeCache.instance();
        gcc.clear();
        final InputStream fis = getTestFileAsStream();
        gcc.load(fis);
        gcc.dump();
        Assert.assertTrue("Always pass", true);
    }

    /**
     * @return test file opened in an input stream
     */
    private InputStream getTestFileAsStream() {
        return getClass().getResourceAsStream("/test.txt");
    }
}
