package org.schoellerfamily.geoservice.persistence.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.geoservice.geocoder.GeoCoder;
import org.schoellerfamily.geoservice.geocoder.StubGeoCoder;
import org.schoellerfamily.geoservice.persistence.GeoCode;
import org.schoellerfamily.geoservice.persistence.GeoCodeItem;
import org.schoellerfamily.geoservice.persistence.GeoCodeLoader;
import org.schoellerfamily.geoservice.persistence.fixture.GeoCodeStub;
import org.schoellerfamily.geoservice.persistence.fixture.GeoCodeTestFixture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@SuppressWarnings({ "PMD.ExcessiveImports", "PMD.TooManyStaticImports" })
public class GeoCodeTest {
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
    private GeoCodeTestFixture testFixture;

    /** */
    @Autowired
    private GeoCodeLoader loader;

    /**
     * Manage the configuration for testing the cache.
     *
     * @author Dick Schoeller
     */
    @Configuration
    static class ContextConfiguration {
        /**
         * Get the fixture that helps setup tests.
         *
         * @return the fixture
         */
        @Bean
        public GeoCodeTestFixture testFixture() {
            return new GeoCodeTestFixture();
        }

        /**
         * @return the geocode cache
         */
        @Bean
        public GeoCode geoCode() {
            return new GeoCodeStub();
        }

        /**
         * @return the geocoder
         */
        // We turn off checkstyle because bean methods must not be final
        // CHECKSTYLE:OFF
        @Bean
        public GeoCoder geoCoder() {
            return new StubGeoCoder(testFixture().expectedNotFound());
        }

        /**
         * @return the geocodeloader
         */
        // We turn off checkstyle because bean methods must not be final
        // CHECKSTYLE:OFF
        @Bean
        public GeoCodeLoader loader() {
            return new GeoCodeLoader();
        }
    }

    /**
     * Force debug logging during tests.
     */
    @BeforeClass
    public static void setUp() {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug");
    }

    /** */
    @Test
    public void testStupidNotFound() {
        logger.info("Entering testStupidNotFound");
        gcc.clear();
        final GeoCodeItem entry = gcc.find("XYZZY");
        assertNull("Should not have found XYZZY",
                entry.getGeocodingResult());
    }

    /** */
    @Test
    public void testModernStupidNotFound() {
        logger.info("Entering testModernStupidNotFound");
        gcc.clear();
        final GeoCodeItem entry = gcc.find("PLUGH", "XYZZY");
        assertNull("Should not have found modern XYZZY",
                entry.getGeocodingResult());
    }

    /** */
    @Test
    public void testOldHomeFound() {
        logger.info("Entering testOldHomeFound");
        gcc.clear();
        final GeoCodeItem entry = gcc
                .find("3341 Chaucer Lane, Bethlehem, PA, USA");
        assertNotNull("Should have found 3341 Chaucer Lane",
                entry.getGeocodingResult());
    }

    /** */
    @Test
    public void testCacheStupid() {
        logger.info("Entering testCacheStupid");
        gcc.clear();
        final GeoCodeItem entry1 = gcc.find("XYZZY");
        final GeoCodeItem entry2 = gcc.find("XYZZY");
        assertEquals("Should be equal", entry1, entry2);
    }

    /** */
    @Test
    public void testModernEmpty() {
        logger.info("Entering testModernEmpty");
        gcc.clear();
        final GeoCodeItem entry1 = gcc.find("XYZZY");
        final GeoCodeItem entry2 = gcc.find("XYZZY", "");
        assertEquals("Should be equal", entry1, entry2);
    }

    /** */
    @Test
    public void testModernNull() {
        logger.info("Entering testModernNull");
        gcc.clear();
        final GeoCodeItem entry1 = gcc.find("XYZZY");
        final GeoCodeItem entry2 = gcc.find("XYZZY", null);
        assertEquals("Should be equal", entry1, entry2);
    }

    /** */
    @Test
    public void testCacheFound() {
        logger.info("Entering testCacheFound");
        gcc.clear();
        final GeoCodeItem entry1 = gcc
                .find("3341 Chaucer Lane, Bethlehem, Pennsylvania, USA");
        final GeoCodeItem entry2 = gcc
                .find("3341 Chaucer Lane, Bethlehem, Pennsylvania, USA");
        assertEquals("Should be equal", entry1, entry2);
    }

    /** */
    @Test
    public void testCacheRefind() {
        logger.info("Entering testCacheReplace");
        gcc.clear();
        gcc.find("XYZZY");
        final GeoCodeItem entry2 = gcc.find("XYZZY",
                "3341 Chaucer Lane, Bethlehem, Pennsylvania, USA");
        final GeoCodeItem entry3 = gcc.find("XYZZY");
        assertEquals("Should be equal", entry2, entry3);
    }

    /** */
    @Test
    public void testCacheOldHome() {
        logger.info("Entering testCacheOldHome");
        gcc.clear();
        final GeoCodeItem entry1 = gcc
                .find("3341 Chaucer Lane, Bethlehem, PA, USA");
        final GeoCodeItem entry2 = gcc
                .find("3341 Chaucer Lane, Bethlehem, PA, USA");
        assertEquals("Should be equal", entry1, entry2);
    }

    /** */
    @Test
    public void testCacheOldHomeModern() {
        logger.info("Entering testCacheOldHomeModern");
        gcc.clear();
        final GeoCodeItem entry1 = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        final GeoCodeItem entry2 = gcc.find("Old Home");
        assertEquals("Should be equal", entry1, entry2);
    }

    /** */
    @Test
    public void testCacheOldHomeModernBoth() {
        logger.info("Entering testCacheOldHomeModernBoth");
        gcc.clear();
        final GeoCodeItem entry1 = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        final GeoCodeItem entry2 = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        assertEquals("Should be equal", entry1, entry2);
    }

    /** */
    @Test
    public void testCacheOldHomeModernChange() {
        logger.info("Entering testCacheOldHomeModernChange");
        gcc.clear();
        final GeoCodeItem entry1 = gcc.find("Old Home");
        final GeoCodeItem entry2 = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        assertNotEquals("Should NOT be equal", entry1, entry2);
    }

    /** */
    @Test
    public void testCacheOldHomeModernToBroken() {
        logger.info("Entering testCacheOldHomeModernChange");
        gcc.clear();
        final GeoCodeItem entry1 = gcc.find("Old Home");
        final GeoCodeItem entry2 = gcc.find("Old Home", "At Sea");
        assertNotEquals("Should NOT be equal", entry1, entry2);
    }

    /** */
    @Test
    public void testCacheOldHomeBroken() {
        logger.info("Entering testCacheOldHomeModernChange");
        gcc.clear();
        final GeoCodeItem entry1 = gcc.add(new GeoCodeItem("Old Home", "At Sea"));
        final GeoCodeItem entry2 = gcc.find("Old Home", "At Sea");
        assertEquals("Should be equal", entry1, entry2);
    }

    /** */
    @Test
    public void testCacheOldHomeAddThenFind() {
        logger.info("Entering testCacheOldHomeModernChange");
        gcc.clear();
        final GeoCodeItem entry1 = gcc.add(new GeoCodeItem("Old Home", "3341 Chaucer Lane, Bethlehem, PA"));
        final GeoCodeItem entry2 = gcc.find("Old Home", "3341 Chaucer Lane, Bethlehem, PA");
        assertNotEquals("Should NOT be equal", entry1, entry2);
    }

    /** */
    @Test
    public void testCacheAddThenFind() {
        logger.info("Entering testCacheOldHomeModernChange");
        gcc.clear();
        final GeoCodeItem entry1 = gcc.add(new GeoCodeItem("3341 Chaucer Lane, Bethlehem, PA"));
        final GeoCodeItem entry2 = gcc.find("3341 Chaucer Lane, Bethlehem, PA");
        assertNotEquals("Should NOT be equal", entry1, entry2);
    }

    /** */
    @Test
    public void testCacheAllKeysSize() {
        logger.info("Entering testCacheOldHomeModernChange");
        gcc.clear();
        gcc.add(new GeoCodeItem("Old Home"));
        gcc.add(new GeoCodeItem("At Sea"));
        assertEquals("Should be 2 items", 2, gcc.allKeys().size());
    }

    /** */
    @Test
    public void testCacheAllKeysContains() {
        logger.info("Entering testCacheOldHomeModernChange");
        gcc.clear();
        gcc.add(new GeoCodeItem("Old Home"));
        gcc.add(new GeoCodeItem("At Sea"));
        final Collection<String> keys = gcc.allKeys();
        assertTrue("Should contain both items",
                keys.contains("Old Home") && keys.contains("At Sea"));
    }

    /** */
    @Test
    public void testCacheReplaceEquals() {
        logger.info("Entering testCacheReplace");
        gcc.clear();
        gcc.find("XYZZY");
        final GeoCodeItem entry2 = gcc.find("XYZZY", "XYZZY");
        final GeoCodeItem entry3 = gcc.find("XYZZY", "XYZZY");
        assertEquals("Should be equal", entry2, entry3);
    }

    /** */
    @Test
    public void testCacheReplaceEmptyResult() {
        logger.info("Entering testCacheReplace");
        gcc.clear();
        gcc.find("XYZZY");
        gcc.find("XYZZY", "XYZZY");
        final GeoCodeItem entry3 = gcc.find("XYZZY", "XYZZY");
        assertNull("Geocoding result should still be null",
                entry3.getGeocodingResult());
    }

    /** */
    @Test
    public void testCacheOldHomeModernSet() {
        logger.info("Entering testCacheOldHomeModernSet");
        gcc.clear();
        gcc.find("Old Home");
        final GeoCodeItem entry2 = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        final GeoCodeItem entry3 = gcc.find("Old Home");
        assertEquals("Should be equal", entry2, entry3);
    }

    /** */
    @Test
    public void testCacheOldHomeLocationResultNotNull() {
        logger.info("Entering testCacheOldHomeLocation");
        gcc.clear();
        final GeoCodeItem entry = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        final GeocodingResult geocodingResult = entry.getGeocodingResult();
        assertNotNull("geocoding result should not be null", geocodingResult);
    }

    /** */
    @Test
    public void testCacheOldHomeLocationResultMatch() {
        logger.info("Entering testCacheOldHomeLocation");
        gcc.clear();
        final GeoCodeItem entry = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        final LatLng expected = new LatLng(40.65800200, -75.40644300);
        final GeocodingResult geocodingResult = entry.getGeocodingResult();
        final LatLng actual = geocodingResult.geometry.location;
        assertEquals("there was a result, but the lat/long was wrong",
                expected.toString(), actual.toString());
    }

    /** */
    @Test
    public void testNotFounds() {
        logger.info("Entering testNotFounds");
        gcc.clear();
        testFixture.loadTestAddresses();
        final List<String> expectList = Arrays
                .asList(testFixture.expectedNotFound());
        final Collection<String> expected = new HashSet<>(expectList);
        final Collection<String> actual = gcc.notFoundKeys();
        assertTrue("Some differences in not found sets",
                compareNotFound(expected, actual));
    }

    /** */
    @Test
    public void testNotFoundsFromFile() {
        logger.info("Entering testNotFounds");
        gcc.clear();
        final InputStream fis = getTestFileAsStream();
        loader.load(fis);
        final List<String> expectList = Arrays
                .asList(testFixture.expectedNotFound());
        final Collection<String> expected = new HashSet<>(expectList);
        final Collection<String> actual = gcc.notFoundKeys();
        assertTrue("Some differences in not found sets",
                compareNotFound(expected, actual));
    }

    /** */
    @Test
    public void testCountNotFoundsLowBound() {
        logger.info("Entering testCountNotFounds");
        gcc.clear();
        testFixture.loadTestAddresses();
        final int count = gcc.countNotFound();
        // Count does not seem to be deterministic with Google's APIs.
        assertTrue("Count too low at: " + count,
                count >= testFixture.expectedLowBound());
    }

    /** */
    @Test
    public void testCountNotFoundsHighBound() {
        logger.info("Entering testCountNotFounds");
        gcc.clear();
        testFixture.loadTestAddresses();
        final int count = gcc.countNotFound();
        // Count does not seem to be deterministic with Google's APIs.
        assertTrue("Count too high at: " + count,
                count <= testFixture.expectedHighBound());
    }

    /** */
    @Test
    public void testCountNotFoundsFromFileLowBound() {
        logger.info("Entering testCountNotFounds");
        gcc.clear();
        final InputStream fis = getTestFileAsStream();
        loader.load(fis);
        final int count = gcc.countNotFound();
        // Count does not seem to be deterministic with Google's APIs.
        assertTrue("Count too low at: " + count,
                count >= testFixture.expectedLowBound());
    }

    /** */
    @Test
    public void testCountNotFoundsFromFileHighBound() {
        logger.info("Entering testCountNotFounds");
        gcc.clear();
        final InputStream fis = getTestFileAsStream();
        loader.load(fis);
        final int count = gcc.countNotFound();
        // Count does not seem to be deterministic with Google's APIs.
        assertTrue("Count too high at: " + count,
                count <= testFixture.expectedHighBound());
    }

    /** */
    @Test
    public void testSize() {
        logger.info("Entering testSize");
        gcc.clear();
        testFixture.loadTestAddresses();
        final int expected = 19;
        assertEquals("Should match known table size of 19",
                expected, gcc.size());
    }

    /** */
    @Test
    public void testSizeFromResource() {
        logger.info("Entering testSizeFromFile");
        gcc.clear();
        final InputStream fis = getTestFileAsStream();
        loader.load(fis);
        final int expected = 19;
        assertEquals("Should match known file size of 19",
                expected, gcc.size());
    }

    /** */
    @Test
    public void testSizeLoadFileError() {
        logger.info("Entering testSizeFromFile");
        gcc.clear();
        loader.load(dummyFileName);
        final int expected = 0;
        assertEquals(
                "Should be 0 because of file not found, was: " + gcc.size(),
                expected, gcc.size());
    }

    /** */
    @Test
    public void testSizeLoadAndFindFileError() {
        logger.info("Entering testSizeFromFile");
        gcc.clear();
        loader.loadAndFind(dummyFileName);
        final int expected = 0;
        assertEquals(
                "Should be 0 because of file not found, was: " + gcc.size(),
                expected, gcc.size());
    }

    /** */
    @Test
    public void testDump() {
        logger.info("Entering testDump");
        gcc.clear();
        testFixture.loadTestAddresses();
        gcc.dump();
        final int expected = 19;
        assertEquals("Should match known table size of 19",
                expected, gcc.size());
    }

    /** */
    @Test
    public void testDumpFromFile() {
        logger.info("Entering testDumpFile");
        gcc.clear();
        final InputStream fis = getTestFileAsStream();
        loader.load(fis);
        gcc.dump();
        final int expected = 19;
        assertEquals("Should match known file size of 19",
                expected, gcc.size());
    }

    /** */
    @Test
    public void testDumpFromFileAfterFind() {
        logger.info("Entering testDumpFile");
        gcc.clear();
        final InputStream fis = getTestFileAsStream();
        loader.loadAndFind(fis);
        gcc.dump();
        final int expected = 19;
        assertEquals("Should match known file size of 19",
                expected, gcc.size());
    }

    /**
     * Check that all entries in expected are also in actual. There may be more
     * in actual than expected because Google is inconsistent.
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
     * @return test file opened in an input stream
     */
    private InputStream getTestFileAsStream() {
        return getClass().getResourceAsStream("/test.txt");
    }
}
