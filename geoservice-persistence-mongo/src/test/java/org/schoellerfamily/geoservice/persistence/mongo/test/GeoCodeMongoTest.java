package org.schoellerfamily.geoservice.persistence.mongo.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.geoservice.persistence.GeoCode;
import org.schoellerfamily.geoservice.persistence.GeoCodeItem;
import org.schoellerfamily.geoservice.persistence.GeoCodeLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

/**
 * Tests of the geocoder using MongoDB persistence for the cache.
 *
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.CommentSize", "PMD.TooManyStaticImports" })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MongoTestConfiguration.class)
public final class GeoCodeMongoTest {
    // TODO reduce this to tests of only the local methods.
    // This is too much of an integration test.
    // Instead leave testing of the base class geocode methods to the tests
    // of that class.

    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Value("${geoservice.dummyfile:/foo}")
    private transient String dummyFileName;

    /** */
    @Autowired
    private GeoCode gcc;

    /** */
    @Autowired
    private GeoRepositoryFixture testFixture;

    /** */
    @Autowired
    private GeoCodeLoader loader;

    /**
     * Force debug logging during tests.
     */
    @BeforeClass
    public static void setUp() {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug");
    }

    /**
     * @throws IOException if there is a problem loading data
     */
    @Before
    public void init() throws IOException {
        testFixture.loadRepository();
    }

    /** */
    @After
    public void tearDown() {
        testFixture.clearRepository();
    }

    /**
     */
    @Test
    public void testStupidNotFound() {
        logger.info("Entering testStupidNotFound");
        final GeoCodeItem entry = gcc.find("XYZZY");
        assertNull("Should not have found XYZZY",
                entry.getGeocodingResult());
    }

    /**
     */
    @Test
    public void testModernStupidNotFound() {
        logger.info("Entering testModernStupidNotFound");
        final GeoCodeItem entry = gcc.find("PLUGH", "XYZZY");
        assertNull("Should not have found modern XYZZY",
                entry.getGeocodingResult());
    }

    /**
     */
    @Test
    public void testOldHomeFound() {
        logger.info("Entering testOldHomeFound");
        final GeoCodeItem entry = gcc
                .find("3341 Chaucer Lane, Bethlehem, PA, USA");
        final GeocodingResult geocodingResult = entry.getGeocodingResult();
        assertNotNull("Should have found 3341 Chaucer Lane",
                geocodingResult);
    }

    /**
     */
    @Test
    public void testCacheStupid() {
        logger.info("Entering testCacheStupid");
        final GeoCodeItem entry1 = gcc.find("XYZZY");
        final GeoCodeItem entry2 = gcc.find("XYZZY");
        assertEquals("Should be equal", entry1, entry2);
    }

    /**
     */
    @Test
    public void testModernEmpty() {
        logger.info("Entering testModernEmpty");
        final GeoCodeItem entry1 = gcc.find("XYZZY");
        final GeoCodeItem entry2 = gcc.find("XYZZY", "");
        assertEquals("Should be equal", entry1, entry2);
    }

    /**
     */
    @Test
    public void testModernNull() {
        logger.info("Entering testModernNull");
        final GeoCodeItem entry1 = gcc.find("XYZZY");
        final GeoCodeItem entry2 = gcc.find("XYZZY", null);
        assertEquals("Should be equal", entry1, entry2);
    }

    /**
     */
    @Test
    public void testCacheFound() {
        logger.info("Entering testCacheFound");
        final GeoCodeItem entry1 = gcc
                .find("3341 Chaucer Lane, Bethlehem, Pennsylvania, USA");
        final GeoCodeItem entry2 = gcc
                .find("3341 Chaucer Lane, Bethlehem, Pennsylvania, USA");
        assertEquals("Should be equal", entry1, entry2);
    }

    /**
     */
    @Test
    public void testCacheRefind() {
        logger.info("Entering testCacheReplace");
        gcc.find("XYZZY");
        final GeoCodeItem entry2 = gcc.find("XYZZY",
                "3341 Chaucer Lane, Bethlehem, Pennsylvania, USA");
        final GeoCodeItem entry3 = gcc.find("XYZZY");
        assertEquals("Should be equal", entry2, entry3);
    }

    /**
     */
    @Test
    public void testCacheOldHome() {
        logger.info("Entering testCacheOldHome");
        final GeoCodeItem entry1 = gcc
                .find("3341 Chaucer Lane, Bethlehem, PA, USA");
        final GeoCodeItem entry2 = gcc
                .find("3341 Chaucer Lane, Bethlehem, PA, USA");
        assertEquals("Should be equal", entry1, entry2);
    }

    /**
     */
    @Test
    public void testCacheOldHomeModern() {
        logger.info("Entering testCacheOldHomeModern");
        final GeoCodeItem entry1 = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        final GeoCodeItem entry2 = gcc.find("Old Home");
        assertEquals("Should be equal", entry1, entry2);
    }

    /**
     */
    @Test
    public void testCacheOldHomeModernBoth() {
        logger.info("Entering testCacheOldHomeModernBoth");
        final GeoCodeItem entry1 = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        final GeoCodeItem entry2 = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        assertEquals("Should be equal", entry1, entry2);
    }

    /**
     */
    @Test
    public void testCacheOldHomeModernChange() {
        logger.info("Entering testCacheOldHomeModernChange");
        final GeoCodeItem entry1 = gcc.find("Old Home");
        final GeoCodeItem entry2 = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        assertNotEquals("Should NOT be equal", entry1, entry2);
    }

    /**
     */
    @Test
    public void testCacheReplaceCheckEquals() {
        logger.info("Entering testCacheReplace");
        gcc.find("XYZZY");
        final GeoCodeItem entry2 = gcc.find("XYZZY", "XYZZY");
        final GeoCodeItem entry3 = gcc.find("XYZZY", "XYZZY");
        assertEquals("Should be equal", entry2, entry3);
    }

    /**
     */
    @Test
    public void testCacheReplaceCheckNullGeocodingResult() {
        logger.info("Entering testCacheReplace");
        gcc.find("XYZZY");
        gcc.find("XYZZY", "XYZZY");
        final GeoCodeItem entry3 = gcc.find("XYZZY", "XYZZY");
        assertNull("Geocoding result should be null",
                entry3.getGeocodingResult());
    }

    /**
     */
    @Test
    public void testCacheOldHomeModernSet() {
        logger.info("Entering testCacheOldHomeModernSet");
        gcc.find("Old Home");
        final GeoCodeItem entry2 = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        final GeoCodeItem entry3 = gcc.find("Old Home");
        assertEquals("Should be equal", entry2, entry3);
    }

    /**
     */
    @Test
    public void testCacheOldHomeLocationResultNotNull() {
        logger.info("Entering testCacheOldHomeLocation");
        final GeoCodeItem entry = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        final GeocodingResult geocodingResult = entry.getGeocodingResult();
        assertNotNull("geocoding result should not be null",
                geocodingResult);
    }

    /**
     */
    @Test
    public void testCacheOldHomeLocationLatLngMatch() {
        logger.info("Entering testCacheOldHomeLocation");
        final GeoCodeItem entry = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        final LatLng expected = new LatLng(40.65800200, -75.40644300);
        final GeocodingResult geocodingResult = entry.getGeocodingResult();
        final LatLng actual = geocodingResult.geometry.location;
        assertEquals("there was a result, but the lat/long was wrong",
                expected.toString(), actual.toString());
    }

    /**
     */
    @Test
    public void testNotFounds() {
        logger.info("Entering testNotFounds");
        testFixture.loadTestAddresses();
        final List<String> expectList = Arrays
                .asList(testFixture.expectedNotFound());
        final Collection<String> expected = new HashSet<>(expectList);
        final Collection<String> actual = gcc.notFoundKeys();
        assertTrue("Some differences in not found sets",
                compareNotFound(expected, actual));
    }

    /**
     */
    @Test
    public void testNotFoundsFromFile() {
        logger.info("Entering testNotFounds");
        final InputStream fis = getTestFileAsStream();
        loader.load(fis);
        final List<String> expectList = Arrays
                .asList(testFixture.expectedNotFound());
        final Collection<String> expected = new HashSet<>(expectList);
        final Collection<String> actual = gcc.notFoundKeys();
        assertTrue("Some differences in not found sets",
                compareNotFound(expected, actual));
    }

    /**
     */
    @Test
    public void testCountNotFoundsLowBound() {
        logger.info("Entering testCountNotFounds");
        testFixture.loadTestAddresses();
        final int count = gcc.countNotFound();
        // Count does not seem to be deterministic with Google's APIs.
        assertTrue("Count too low at: " + count,
                count >= testFixture.expectedLowBound());
    }

    /**
     */
    @Test
    public void testCountNotFoundsHighBound() {
        logger.info("Entering testCountNotFounds");
        testFixture.loadTestAddresses();
        final int count = gcc.countNotFound();
        // Count does not seem to be deterministic with Google's APIs.
        assertTrue("Count too high at: " + count,
                count <= testFixture.expectedHighBound());
    }

    /**
     */
    @Test
    public void testCountNotFoundsFromFileLowBound() {
        logger.info("Entering testCountNotFounds");
        final InputStream fis = getTestFileAsStream();
        loader.load(fis);
        final int count = gcc.countNotFound();
        // Count does not seem to be deterministic with Google's APIs.
        assertTrue("Count too low at: " + count,
                count >= testFixture.expectedLowBound());
    }

    /**
     */
    @Test
    public void testCountNotFoundsFromFileHighBound() {
        logger.info("Entering testCountNotFounds");
        final InputStream fis = getTestFileAsStream();
        loader.load(fis);
        final int count = gcc.countNotFound();
        // Count does not seem to be deterministic with Google's APIs.
        assertTrue("Count too high at: " + count,
                count <= testFixture.expectedHighBound());
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
    private boolean compareNotFound(final Collection<String> expected,
            final Collection<String> actual) {
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
        testFixture.loadTestAddresses();
        final int expected = 19;
        assertEquals("Should match known table size of 19",
                expected, gcc.size());
    }

    /**
     */
    @Test
    public void testSizeFromResource() {
        logger.info("Entering testSizeFromFile");
        final InputStream fis = getTestFileAsStream();
        loader.load(fis);
        final int expected = 19;
        assertEquals("Should match known file size of 19",
                expected, gcc.size());
    }

    /**
     */
    @Test
    public void testSizeLoadFileError() {
        logger.info("Entering testSizeFromFile");
        loader.load(dummyFileName);
        final int expected = 0;
        assertEquals(
                "Should be 0 because of file not found, was: " + gcc.size(),
                expected, gcc.size());
    }

    /**
     */
    @Test
    public void testDump() {
        logger.info("Entering testDump");
        testFixture.loadTestAddresses();
        gcc.dump();
        final int expected = 19;
        assertEquals("Should match known table size of 19",
                expected, gcc.size());
    }

    /**
     */
    @Test
    public void testDumpFromFile() {
        logger.info("Entering testDumpFile");
        final InputStream fis = getTestFileAsStream();
        loader.load(fis);
        gcc.dump();
        final int expected = 19;
        assertEquals("Should match known file size of 19",
                expected, gcc.size());
    }

    /**
     */
    @Test
    public void testAllKeysSize() {
        logger.info("Entering testAllKeysSize");
        testFixture.loadTestAddresses();
        final int expected = 19;
        assertEquals("Should match known table size of 19",
                expected, gcc.allKeys().size());
    }

    /**
     */
    @Test
    public void testAllKeys() {
        logger.info("Entering testAllKeys");
        testFixture.loadTestAddresses();
        assertTrue("Should contain search string",
                gcc.allKeys().contains("America"));
    }

    /**
     */
    @Test
    public void testDelete() {
        logger.info("Entering testDelete");
        testFixture.loadTestAddresses();
        final GeoCodeItem gci = gcc.find("America");
        gcc.delete(gci);
        assertFalse("Should not contain search string",
                gcc.allKeys().contains("America"));
    }

    /** */
    @Test
    public void testAddGet() {
        final GeoCodeItem input = new GeoCodeItem("America");
        gcc.add(input);
        final GeoCodeItem output = gcc.get("America");
        assertEquals("Items should match", input, output);
    }

    /** */
    @Test
    public void testAddFind() {
        final GeoCodeItem input = new GeoCodeItem("America");
        gcc.add(input);
        final GeoCodeItem output = gcc.find("America");
        assertNotEquals("Items should not match", input, output);
    }

    /** */
    @Test
    public void testAddFindWithModern() {
        final GeoCodeItem input = new GeoCodeItem("America", "America");
        gcc.add(input);
        final GeoCodeItem output = gcc.find("America", "America");
        assertNotEquals("Items should not match", input, output);
    }

    /** */
    @Test
    public void testAddFindChangeModernResultsPresent() {
        final GeoCodeItem input = new GeoCodeItem("XYZZY", "XYZZY");
        gcc.add(input);
        final GeoCodeItem output = gcc.find("XYZZY", "America");
        assertTrue("Output should have result",
                input.getGeocodingResult() == null
                        && output.getGeocodingResult() != null);
    }

    /** */
    @Test
    public void testAddFindChangeModernNamesMatch() {
        final GeoCodeItem input = new GeoCodeItem("XYZZY", "XYZZY");
        gcc.add(input);
        final GeoCodeItem output = gcc.find("XYZZY", "America");
        assertNotEquals("Items should not match",
                input.getModernPlaceName(), output.getModernPlaceName());
    }

    /** */
    @Test
    public void testAddFindChangeModernToBogusResultsPresent() {
        final GeoCodeItem input = new GeoCodeItem("XYZZY", "XYZZY");
        gcc.add(input);
        final GeoCodeItem output = gcc.find("XYZZY", "PLUGH");
        assertTrue("Output should not have result",
                input.getGeocodingResult() == null
                        && output.getGeocodingResult() == null);
    }

    /** */
    @Test
    public void testAddFindChangeModernToBogusNamesMatch() {
        final GeoCodeItem input = new GeoCodeItem("XYZZY", "XYZZY");
        gcc.add(input);
        final GeoCodeItem output = gcc.find("XYZZY", "PLUGH");
        assertNotEquals("Items should not match",
                input.getModernPlaceName(), output.getModernPlaceName());
    }

    /**
     * @return test file opened in an input stream
     */
    private InputStream getTestFileAsStream() {
        return getClass().getResourceAsStream("/test.txt");
    }
}
