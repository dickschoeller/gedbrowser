package org.schoellerfamily.geoservice.persistence.mongo.test;

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
import org.junit.runner.RunWith;
import org.schoellerfamily.geoservice.persistence.GeoCodeDao;
import org.schoellerfamily.geoservice.persistence.GeoCodeItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.CommentSize" })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MongoTestConfiguration.class)
public final class GeoCodeMongoTest {
    /** */
    @Autowired
    private GeoCodeDao gcc;

    /** */
    @Autowired
    private GeoRepositoryFixture fixture;

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
            "1st United Methodist Church, Perkasie, Bucks County, Pennsylvania"
            + ", USA",
            ""
        },
        {
            "4 miles northeast of Pine Grove, Pine Grove Township, Schuylkill "
            + "County, Pennsylvania, USA",
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
            "Alsace, France",
            ""
        },
        {
            "Altalaha Lutheran Cemetery, Rehrersburg, Berks County, Pennsylvan"
            + "ia, USA",
            ""
        },
        {
            "America",
            ""
        },
        {
            "Anamosa Hospital, Anamosa, Jones County, Iowa, USA",
            ""
        },
        {
            "Arizona, USA",
            ""
        },
        {
            "Ash of Normandy, Sussex, England",
        },
        {
            "At Sea",
            ""
        },
        {
            "At home, 1.5 miles northwest of Sutliff, Solon, Cedar County, Iow"
            + "a, USA",
            ""
        },
        {
            "At home, Cornwall Road, Lebanon, Lebanon County, Pennsylvania, U"
            + "SA",
            ""
        },
        {
            "At home, RD 2, Lebanon, Lebanon County, Pennsylvania, USA",
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
    };

    /**
     * Table of values we know should be not found.
     */
    private final String[] expectedNotFound = {
        "1st United Methodist Church, Perkasie, Bucks County, Pennsylvania, US"
        + "A",
        "Adelo, Illinois, USA",
        "Albright College, Reading, Berks County, Pennsylvania, USA",
        "Altalaha Lutheran Cemetery, Rehrersburg, Berks County, Pennsylvania, "
        + "USA",
        "Anamosa Hospital, Anamosa, Jones County, Iowa, USA",
        "Ash of Normandy, Sussex, England",
        "At Sea",
        "At home, 1.5 miles northwest of Sutliff, Solon, Cedar County, Iowa, U"
        + "SA",
        "At home, Cornwall Road, Lebanon, Lebanon County, Pennsylvania, USA",
        "At home, RD 2, Lebanon, Lebanon County, Pennsylvania, USA",
        "At home, Souderton, Montgomery County, Pennsylvania, USA",
        "At home, Tega Cay, York County, South Carolina, USA",
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
        fixture.clearRepository();
        final GeoCodeItem entry = gcc.find("XYZZY");
        Assert.assertNull("Should not have found XYZZY",
                entry.getGeocodingResult());
    }

    /**
     */
    @Test
    public void testModernStupidNotFound() {
        logger.info("Entering testModernStupidNotFound");
        fixture.clearRepository();
        final GeoCodeItem entry = gcc.find("PLUGH", "XYZZY");
        Assert.assertNull("Should not have found modern XYZZY",
                entry.getGeocodingResult());
    }

    /**
     */
    @Test
    public void testOldHomeFound() {
        logger.info("Entering testOldHomeFound");
        fixture.clearRepository();
        final GeoCodeItem entry = gcc
                .find("3341 Chaucer Lane, Bethlehem, PA, USA");
        final GeocodingResult geocodingResult = entry.getGeocodingResult();
        Assert.assertNotNull("Should have found 3341 Chaucer Lane",
                geocodingResult);
    }

    /**
     */
    @Test
    public void testCacheStupid() {
        logger.info("Entering testCacheStupid");
        fixture.clearRepository();
        final GeoCodeItem entry1 = gcc.find("XYZZY");
        final GeoCodeItem entry2 = gcc.find("XYZZY");
        Assert.assertEquals("Should be equal", entry1, entry2);
    }

    /**
     */
    @Test
    public void testModernEmpty() {
        logger.info("Entering testModernEmpty");
        fixture.clearRepository();
        final GeoCodeItem entry1 = gcc.find("XYZZY");
        final GeoCodeItem entry2 = gcc.find("XYZZY", "");
        Assert.assertEquals("Should be equal", entry1, entry2);
    }

    /**
     */
    @Test
    public void testModernNull() {
        logger.info("Entering testModernNull");
        fixture.clearRepository();
        final GeoCodeItem entry1 = gcc.find("XYZZY");
        final GeoCodeItem entry2 = gcc.find("XYZZY", null);
        Assert.assertEquals("Should be equal", entry1, entry2);
    }

    /**
     */
    @Test
    public void testCacheFound() {
        logger.info("Entering testCacheFound");
        fixture.clearRepository();
        final GeoCodeItem entry1 = gcc
                .find("3341 Chaucer Lane, Bethlehem, Pennsylvania, USA");
        final GeoCodeItem entry2 = gcc
                .find("3341 Chaucer Lane, Bethlehem, Pennsylvania, USA");
        Assert.assertEquals("Should be equal", entry1, entry2);
    }

    /**
     */
    @Test
    public void testCacheRefind() {
        logger.info("Entering testCacheReplace");
        fixture.clearRepository();
        gcc.find("XYZZY");
        final GeoCodeItem entry2 = gcc.find("XYZZY",
                "3341 Chaucer Lane, Bethlehem, Pennsylvania, USA");
        final GeoCodeItem entry3 = gcc.find("XYZZY");
        Assert.assertEquals("Should be equal", entry2, entry3);
    }

    /**
     */
    @Test
    public void testCacheOldHome() {
        logger.info("Entering testCacheOldHome");
        fixture.clearRepository();
        final GeoCodeItem entry1 = gcc
                .find("3341 Chaucer Lane, Bethlehem, PA, USA");
        final GeoCodeItem entry2 = gcc
                .find("3341 Chaucer Lane, Bethlehem, PA, USA");
        Assert.assertEquals("Should be equal", entry1, entry2);
    }

    /**
     */
    @Test
    public void testCacheOldHomeModern() {
        logger.info("Entering testCacheOldHomeModern");
        fixture.clearRepository();
        final GeoCodeItem entry1 = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        final GeoCodeItem entry2 = gcc.find("Old Home");
        Assert.assertEquals("Should be equal", entry1, entry2);
    }

    /**
     */
    @Test
    public void testCacheOldHomeModernBoth() {
        logger.info("Entering testCacheOldHomeModernBoth");
        fixture.clearRepository();
        final GeoCodeItem entry1 = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        final GeoCodeItem entry2 = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        Assert.assertEquals("Should be equal", entry1, entry2);
    }

    /**
     */
    @Test
    public void testCacheOldHomeModernChange() {
        logger.info("Entering testCacheOldHomeModernChange");
        fixture.clearRepository();
        final GeoCodeItem entry1 = gcc.find("Old Home");
        final GeoCodeItem entry2 = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        Assert.assertNotEquals("Should NOT be equal", entry1, entry2);
    }

    /**
     */
    @Test
    public void testCacheReplace() {
        logger.info("Entering testCacheReplace");
        fixture.clearRepository();
        gcc.find("XYZZY");
        final GeoCodeItem entry2 = gcc.find("XYZZY", "XYZZY");
        final GeoCodeItem entry3 = gcc.find("XYZZY", "XYZZY");
        Assert.assertEquals("Should be equal", entry2, entry3);
        Assert.assertNull("Geocoding result should be null",
                entry2.getGeocodingResult());
    }

    /**
     */
    @Test
    public void testCacheOldHomeModernSet() {
        logger.info("Entering testCacheOldHomeModernSet");
        fixture.clearRepository();
        gcc.find("Old Home");
        final GeoCodeItem entry2 = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        final GeoCodeItem entry3 = gcc.find("Old Home");
        Assert.assertEquals("Should be equal", entry2, entry3);
    }

    /**
     */
    @Test
    public void testCacheOldHomeLocation() {
        logger.info("Entering testCacheOldHomeLocation");
        fixture.clearRepository();
        final GeoCodeItem entry = gcc.find("Old Home",
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
        fixture.clearRepository();
        load(addressTable);
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
        fixture.clearRepository();
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
        fixture.clearRepository();
        load(addressTable);
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
        fixture.clearRepository();
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
        fixture.clearRepository();
        load(addressTable);
        final int expected = 19;
        Assert.assertEquals("Should match known table size of 19",
                expected, gcc.size());
    }

    /**
     */
    @Test
    public void testSizeFromResource() {
        logger.info("Entering testSizeFromFile");
        fixture.clearRepository();
        final InputStream fis = getTestFileAsStream();
        gcc.load(fis);
        final int expected = 19;
        Assert.assertEquals("Should match known file size of 19",
                expected, gcc.size());
    }

    /**
     */
    @Test
    public void testSizeLoadFileError() {
        logger.info("Entering testSizeFromFile");
        fixture.clearRepository();
        gcc.load("/foo");
        final int expected = 0;
        Assert.assertEquals(
                "Should be 0 because of file not found, was: " + gcc.size(),
                expected, gcc.size());
    }

    /**
     */
    @Test
    public void testSizeFromFile() {
        logger.info("Entering testSizeFromFile");
        fixture.clearRepository();
        gcc.load(getTestFilePath());
        final int expected = 19;
        Assert.assertEquals("Should match known file size of 19",
                expected, gcc.size());
    }

    /**
     */
    @Test
    public void testDump() {
        logger.info("Entering testDump");
        fixture.clearRepository();
        load(addressTable);
        gcc.dump();
        final int expected = 19;
        Assert.assertEquals("Should match known table size of 19",
                expected, gcc.size());
    }

    /**
     */
    @Test
    public void testDumpFromFile() {
        logger.info("Entering testDumpFile");
        fixture.clearRepository();
        final InputStream fis = getTestFileAsStream();
        gcc.load(fis);
        gcc.dump();
        final int expected = 19;
        Assert.assertEquals("Should match known file size of 19",
                expected, gcc.size());
    }

    /**
     */
    @Test
    public void testAllKeysSize() {
        logger.info("Entering testAllKeysSize");
        fixture.clearRepository();
        load(addressTable);
        final int expected = 19;
        Assert.assertEquals("Should match known table size of 19",
                expected, gcc.allKeys().size());
    }

    /**
     */
    @Test
    public void testAllKeys() {
        logger.info("Entering testAllKeys");
        fixture.clearRepository();
        load(addressTable);
        Assert.assertTrue("Should contain search string",
                gcc.allKeys().contains("America"));
    }

    /**
     */
    @Test
    public void testDelete() {
        logger.info("Entering testDelete");
        fixture.clearRepository();
        load(addressTable);
        final GeoCodeItem gci = gcc.find("America");
        gcc.delete(gci);
        Assert.assertFalse("Should not contain search string",
                gcc.allKeys().contains("America"));
    }

    /** */
    @Test
    public void testAddGet() {
        fixture.clearRepository();
        final GeoCodeItem input = new GeoCodeItem("America");
        gcc.add(input);
        final GeoCodeItem output = gcc.get("America");
        Assert.assertEquals("Items should match", input, output);
    }

    /** */
    @Test
    public void testAddFind() {
        fixture.clearRepository();
        final GeoCodeItem input = new GeoCodeItem("America");
        gcc.add(input);
        final GeoCodeItem output = gcc.find("America");
        Assert.assertNotEquals("Items should not match", input, output);
    }

    /** */
    @Test
    public void testAddFindWithModern() {
        fixture.clearRepository();
        final GeoCodeItem input = new GeoCodeItem("America", "America");
        gcc.add(input);
        final GeoCodeItem output = gcc.find("America", "America");
        Assert.assertNotEquals("Items should not match", input, output);
    }

    /** */
    @Test
    public void testAddFindChangeModern() {
        fixture.clearRepository();
        final GeoCodeItem input = new GeoCodeItem("XYZZY", "XYZZY");
        gcc.add(input);
        final GeoCodeItem output = gcc.find("XYZZY", "America");
        Assert.assertTrue("Output should have result",
                input.getGeocodingResult() == null
                        && output.getGeocodingResult() != null);
        Assert.assertNotEquals("Items should not match",
                input.getModernPlaceName(), output.getModernPlaceName());
    }

    /** */
    @Test
    public void testAddFindChangeModernToBogus() {
        fixture.clearRepository();
        final GeoCodeItem input = new GeoCodeItem("XYZZY", "XYZZY");
        gcc.add(input);
        final GeoCodeItem output = gcc.find("XYZZY", "PLUGH");
        Assert.assertTrue("Output should not have result",
                input.getGeocodingResult() == null
                        && output.getGeocodingResult() == null);
        Assert.assertNotEquals("Items should not match",
                input.getModernPlaceName(), output.getModernPlaceName());
    }

    /**
     * @return test file opened in an input stream
     */
    private InputStream getTestFileAsStream() {
        return getClass().getResourceAsStream("/test.txt");
    }

    /**
     * Load the cache from an array of strings. Particularly valuable for
     * testing. Each string has planeName|modernPlaceName. The second part can
     * be empty.
     *
     * @param strings the array of strings
     */
    @SuppressWarnings("PMD.UseVarargs")
    private void load(final String[][] strings) {
        for (final String[] line : strings) {
            if (line.length < 2 || line[1] == null || line[1].isEmpty()) {
                gcc.find(line[0]);
            } else {
                gcc.find(line[0], line[1]);
            }
        }
    }


    /**
     * @return path to the standard location for a test file, shorter than
     *         standard places.
     */
    private String getTestFilePath() {
        return "/var/lib/gedbrowser/test.txt";
    }
}
