package org.schoellerfamily.geoservice.persistence.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@SuppressWarnings({ "PMD.ExcessiveImports", "PMD.ExcessivePublicCount", "PMD.GodClass",
    "PMD.TooManyStaticImports" })
@Slf4j
public final class GeoCodeTest {

    /** */
    @Value("${geoservice.dummyfile:/foo}")
    private transient String dummyFileName;

    /** */
    @Value("${gedbrowser.home:#{ systemProperties['user.dir'] }/src/test/resources}")
    private transient String gedbrowserHome;

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
        @Bean
        public GeoCoder geoCoder() {
            return new StubGeoCoder(testFixture().expectedNotFound());
        }

        /**
         * @return the geocodeloader
         */
        @Bean
        public GeoCodeLoader loader() {
            return new GeoCodeLoader();
        }
    }

    /**
     * Force debug logging during tests.
     */
    @BeforeAll
    public static void setUp() {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug");
    }

    /** */
    @Test
    void testStupidNotFound() {
        log.info("Entering testStupidNotFound");
        gcc.clear();
        final GeoCodeItem entry = gcc.find("XYZZY");
        assertNull(entry.getGeocodingResult(), "Should not have found XYZZY");
    }

    /** */
    @Test
    void testModernStupidNotFound() {
        log.info("Entering testModernStupidNotFound");
        gcc.clear();
        final GeoCodeItem entry = gcc.find("PLUGH", "XYZZY");
        assertNull(entry.getGeocodingResult(), "Should not have found modern XYZZY");
    }

    /** */
    @Test
    void testOldHomeFound() {
        log.info("Entering testOldHomeFound");
        gcc.clear();
        final GeoCodeItem entry = gcc.find("3341 Chaucer Lane, Bethlehem, PA, USA");
        assertNotNull(entry.getGeocodingResult(), "Should have found 3341 Chaucer Lane");
    }

    /** */
    @Test
    void testCacheStupid() {
        log.info("Entering testCacheStupid");
        gcc.clear();
        final GeoCodeItem entry1 = gcc.find("XYZZY");
        final GeoCodeItem entry2 = gcc.find("XYZZY");
        assertEquals(entry1, entry2, "Should be equal");
    }

    /** */
    @Test
    void testModernEmpty() {
        log.info("Entering testModernEmpty");
        gcc.clear();
        final GeoCodeItem entry1 = gcc.find("XYZZY");
        final GeoCodeItem entry2 = gcc.find("XYZZY", "");
        assertEquals(entry1, entry2, "Should be equal");
    }

    /** */
    @Test
    void testModernNull() {
        log.info("Entering testModernNull");
        gcc.clear();
        final GeoCodeItem entry1 = gcc.find("XYZZY");
        final GeoCodeItem entry2 = gcc.find("XYZZY", null);
        assertEquals(entry1, entry2, "Should be equal");
    }

    /** */
    @Test
    void testCacheFound() {
        log.info("Entering testCacheFound");
        gcc.clear();
        final GeoCodeItem entry1 = gcc.find("3341 Chaucer Lane, Bethlehem, Pennsylvania, USA");
        final GeoCodeItem entry2 = gcc.find("3341 Chaucer Lane, Bethlehem, Pennsylvania, USA");
        assertEquals(entry1, entry2, "Should be equal");
    }

    /** */
    @Test
    void testCacheRefind() {
        log.info("Entering testCacheReplace");
        gcc.clear();
        gcc.find("XYZZY");
        final GeoCodeItem entry2 = gcc.find("XYZZY",
            "3341 Chaucer Lane, Bethlehem, Pennsylvania, USA");
        final GeoCodeItem entry3 = gcc.find("XYZZY");
        assertEquals(entry2, entry3, "Should be equal");
    }

    /** */
    @Test
    void testCacheOldHome() {
        log.info("Entering testCacheOldHome");
        gcc.clear();
        final GeoCodeItem entry1 = gcc.find("3341 Chaucer Lane, Bethlehem, PA, USA");
        final GeoCodeItem entry2 = gcc.find("3341 Chaucer Lane, Bethlehem, PA, USA");
        assertEquals(entry1, entry2, "Should be equal");
    }

    /** */
    @Test
    void testCacheOldHomeModern() {
        log.info("Entering testCacheOldHomeModern");
        gcc.clear();
        final GeoCodeItem entry1 = gcc.find("Old Home", "3341 Chaucer Lane, Bethlehem, PA, USA");
        final GeoCodeItem entry2 = gcc.find("Old Home");
        assertEquals(entry1, entry2, "Should be equal");
    }

    /** */
    @Test
    void testCacheOldHomeModernBoth() {
        log.info("Entering testCacheOldHomeModernBoth");
        gcc.clear();
        final GeoCodeItem entry1 = gcc.find("Old Home", "3341 Chaucer Lane, Bethlehem, PA, USA");
        final GeoCodeItem entry2 = gcc.find("Old Home", "3341 Chaucer Lane, Bethlehem, PA, USA");
        assertEquals(entry1, entry2, "Should be equal");
    }

    /** */
    @Test
    void testCacheOldHomeModernChange() {
        log.info("Entering testCacheOldHomeModernChange");
        gcc.clear();
        final GeoCodeItem entry1 = gcc.find("Old Home");
        final GeoCodeItem entry2 = gcc.find("Old Home", "3341 Chaucer Lane, Bethlehem, PA, USA");
        assertNotEquals(entry1, entry2, "Should NOT be equal");
    }

    /** */
    @Test
    void testCacheOldHomeModernToBroken() {
        log.info("Entering testCacheOldHomeModernChange");
        gcc.clear();
        final GeoCodeItem entry1 = gcc.find("Old Home");
        final GeoCodeItem entry2 = gcc.find("Old Home", "Blah Blah");
        assertNotEquals(entry1, entry2, "Should NOT be equal");
    }

    /** */
    @Test
    void testCacheOldHomeBroken() {
        log.info("Entering testCacheOldHomeModernChange");
        gcc.clear();
        final GeoCodeItem entry1 = gcc.add(new GeoCodeItem("Old Home", "Blah Blah"));
        final GeoCodeItem entry2 = gcc.find("Old Home", "Blah Blah");
        assertEquals(entry1, entry2, "Should be equal");
    }

    /** */
    @Test
    void testCacheOldHomeAddThenFind() {
        log.info("Entering testCacheOldHomeModernChange");
        gcc.clear();
        final GeoCodeItem entry1 = gcc
            .add(new GeoCodeItem("Old Home", "3341 Chaucer Lane, Bethlehem, PA"));
        final GeoCodeItem entry2 = gcc.find("Old Home", "3341 Chaucer Lane, Bethlehem, PA");
        assertNotEquals(entry1, entry2, "Should NOT be equal");
    }

    /** */
    @Test
    void testCacheAddThenFind() {
        log.info("Entering testCacheOldHomeModernChange");
        gcc.clear();
        final GeoCodeItem entry1 = gcc.add(new GeoCodeItem("3341 Chaucer Lane, Bethlehem, PA"));
        final GeoCodeItem entry2 = gcc.find("3341 Chaucer Lane, Bethlehem, PA");
        assertNotEquals(entry1, entry2, "Should NOT be equal");
    }

    /** */
    @Test
    void testCacheAllKeysSize() {
        log.info("Entering testCacheOldHomeModernChange");
        gcc.clear();
        gcc.add(new GeoCodeItem("Old Home"));
        gcc.add(new GeoCodeItem("At Sea"));
        assertEquals(2, gcc.allKeys().size(), "Should be 2 items");
    }

    /** */
    @Test
    void testCacheAllKeysContains() {
        log.info("Entering testCacheOldHomeModernChange");
        gcc.clear();
        gcc.add(new GeoCodeItem("Old Home"));
        gcc.add(new GeoCodeItem("At Sea"));
        final Collection<String> keys = gcc.allKeys();
        assertTrue(keys.contains("Old Home") && keys.contains("At Sea"),
            "Should contain both items");
    }

    /** */
    @Test
    void testCacheReplaceEquals() {
        log.info("Entering testCacheReplace");
        gcc.clear();
        gcc.find("XYZZY");
        final GeoCodeItem entry2 = gcc.find("XYZZY", "XYZZY");
        final GeoCodeItem entry3 = gcc.find("XYZZY", "XYZZY");
        assertEquals(entry2, entry3, "Should be equal");
    }

    /** */
    @Test
    void testCacheReplaceEmptyResult() {
        log.info("Entering testCacheReplace");
        gcc.clear();
        gcc.find("XYZZY");
        gcc.find("XYZZY", "XYZZY");
        final GeoCodeItem entry3 = gcc.find("XYZZY", "XYZZY");
        assertNull(entry3.getGeocodingResult(), "Geocoding result should still be null");
    }

    /** */
    @Test
    void testCacheOldHomeModernSet() {
        log.info("Entering testCacheOldHomeModernSet");
        gcc.clear();
        gcc.find("Old Home");
        final GeoCodeItem entry2 = gcc.find("Old Home", "3341 Chaucer Lane, Bethlehem, PA, USA");
        final GeoCodeItem entry3 = gcc.find("Old Home");
        assertEquals(entry2, entry3, "Should be equal");
    }

    /** */
    @Test
    void testCacheOldHomeLocationResultNotNull() {
        log.info("Entering testCacheOldHomeLocation");
        gcc.clear();
        final GeoCodeItem entry = gcc.find("Old Home", "3341 Chaucer Lane, Bethlehem, PA, USA");
        final GeocodingResult geocodingResult = entry.getGeocodingResult();
        assertNotNull(geocodingResult, "geocoding result should not be null");
    }

    /** */
    @Test
    void testCacheOldHomeLocationResultMatch() {
        log.info("Entering testCacheOldHomeLocation");
        gcc.clear();
        final GeoCodeItem entry = gcc.find("Old Home", "3341 Chaucer Lane, Bethlehem, PA, USA");
        final LatLng expected = new LatLng(40.65800200, -75.40644300);
        final GeocodingResult geocodingResult = entry.getGeocodingResult();
        final LatLng actual = geocodingResult.geometry.location;
        assertEquals(expected.toString(), actual.toString(),
            "there was a result, but the lat/long was wrong");
    }

    /** */
    @Test
    void testNotFounds() {
        log.info("Entering testNotFounds");
        gcc.clear();
        testFixture.loadTestAddresses();
        final List<String> expectList = Arrays.asList(testFixture.expectedNotFound());
        final Collection<String> expected = new HashSet<>(expectList);
        final Collection<String> actual = gcc.notFoundKeys();
        assertTrue(compareNotFound(expected, actual), "Some differences in not found sets");
    }

    /** */
    @Test
    void testNotFoundsFromStream() {
        log.info("Entering testNotFounds");
        gcc.clear();
        loader.load(getTestFileAsStream());
        final List<String> expectList = Arrays.asList(testFixture.expectedNotFound());
        final Collection<String> expected = new HashSet<>(expectList);
        final Collection<String> actual = gcc.notFoundKeys();
        assertTrue(compareNotFound(expected, actual), "Some differences in not found sets");
    }

    /** */
    @Test
    void testNotFoundsFromFile() {
        log.info("Entering testNotFounds");
        gcc.clear();
        loader.load(getTestFileName());
        final List<String> expectList = Arrays.asList(testFixture.expectedNotFound());
        final Collection<String> expected = new HashSet<>(expectList);
        final Collection<String> actual = gcc.notFoundKeys();
        assertTrue(compareNotFound(expected, actual), "Some differences in not found sets");
    }

    /** */
    @Test
    void testNotParsingFromStream() {
        log.info("Entering testNotFounds");
        gcc.clear();
        loader.load(getTestFileAsStream());
        final GeoCodeItem item = gcc.get("At Sea");
        assertEquals("Atlantic Ocean", item.getModernPlaceName(),
            "Failure indicates a parsing problem");
    }

    /** */
    @Test
    void testNotParsingFromFile() {
        log.info("Entering testNotFounds");
        gcc.clear();
        loader.load(getTestFileName());
        final GeoCodeItem item = gcc.get("At Sea");
        assertEquals("Atlantic Ocean", item.getModernPlaceName(),
            "Failure indicates a parsing problem");
    }

    /** */
    @Test
    void testCountNotFoundsLowBound() {
        log.info("Entering testCountNotFounds");
        gcc.clear();
        testFixture.loadTestAddresses();
        final int count = gcc.countNotFound();
        // Count does not seem to be deterministic with Google's APIs.
        assertTrue(count >= testFixture.expectedLowBound(), "Count too low at: " + count);
    }

    /** */
    @Test
    void testCountNotFoundsHighBound() {
        log.info("Entering testCountNotFounds");
        gcc.clear();
        testFixture.loadTestAddresses();
        final int count = gcc.countNotFound();
        // Count does not seem to be deterministic with Google's APIs.
        assertTrue(count <= testFixture.expectedHighBound(), "Count too high at: " + count);
    }

    /** */
    @Test
    void testCountNotFoundsFromStreamLowBound() {
        log.info("Entering testCountNotFounds");
        gcc.clear();
        loader.load(getTestFileAsStream());
        final int count = gcc.countNotFound();
        // Count does not seem to be deterministic with Google's APIs.
        assertTrue(count >= testFixture.expectedLowBound(), "Count too low at: " + count);
    }

    /** */
    @Test
    void testCountNotFoundsFromFileLowBound() {
        log.info("Entering testCountNotFounds");
        gcc.clear();
        loader.load(getTestFileName());
        final int count = gcc.countNotFound();
        // Count does not seem to be deterministic with Google's APIs.
        assertTrue(count >= testFixture.expectedLowBound(), "Count too low at: " + count);
    }

    /** */
    @Test
    void testCountNotFoundsFromStreamHighBound() {
        log.info("Entering testCountNotFounds");
        gcc.clear();
        loader.load(getTestFileAsStream());
        final int count = gcc.countNotFound();
        // Count does not seem to be deterministic with Google's APIs.
        assertTrue(count <= testFixture.expectedHighBound(), "Count too high at: " + count);
    }

    /** */
    @Test
    void testCountNotFoundsFromFileHighBound() {
        log.info("Entering testCountNotFounds");
        gcc.clear();
        loader.load(getTestFileName());
        final int count = gcc.countNotFound();
        // Count does not seem to be deterministic with Google's APIs.
        assertTrue(count <= testFixture.expectedHighBound(), "Count too high at: " + count);
    }

    /** */
    @Test
    void testSize() {
        log.info("Entering testSize");
        gcc.clear();
        testFixture.loadTestAddresses();
        final int expected = 19;
        assertEquals(expected, gcc.size(), "Should match known table size of 19");
    }

    /** */
    @Test
    void testSizeFromStream() {
        log.info("Entering testSizeFromFile");
        gcc.clear();
        loader.load(getTestFileAsStream());
        final int expected = 19;
        assertEquals(expected, gcc.size(), "Should match known file size of 19");
    }

    /** */
    @Test
    void testSizeFromFile() {
        log.info("Entering testSizeFromFile");
        gcc.clear();
        loader.load(getTestFileName());
        final int expected = 19;
        assertEquals(expected, gcc.size(), "Should match known file size of 19");
    }

    /** */
    @Test
    void testSizeLoadFileError() {
        log.info("Entering testSizeFromFile");
        gcc.clear();
        loader.load(dummyFileName);
        final int expected = 0;
        assertEquals(expected, gcc.size(),
            "Should be 0 because of file not found, was: " + gcc.size());
    }

    /** */
    @Test
    void testSizeLoadAndFindFileError() {
        log.info("Entering testSizeFromFile");
        gcc.clear();
        loader.loadAndFind(dummyFileName);
        final int expected = 0;
        assertEquals(expected, gcc.size(),
            "Should be 0 because of file not found, was: " + gcc.size());
    }

    /** */
    @Test
    void testDump() {
        log.info("Entering testDump");
        gcc.clear();
        testFixture.loadTestAddresses();
        gcc.dump();
        final int expected = 19;
        assertEquals(expected, gcc.size(), "Should match known table size of 19");
    }

    /** */
    @Test
    void testDumpFromStream() {
        log.info("Entering testDumpFile");
        gcc.clear();
        loader.load(getTestFileAsStream());
        gcc.dump();
        final int expected = 19;
        assertEquals(expected, gcc.size(), "Should match known file size of 19");
    }

    /** */
    @Test
    void testDumpFromFile() {
        log.info("Entering testDumpFile");
        gcc.clear();
        loader.load(getTestFileName());
        gcc.dump();
        final int expected = 19;
        assertEquals(expected, gcc.size(), "Should match known file size of 19");
    }

    /** */
    @Test
    void testDumpFromStreamAfterFind() {
        log.info("Entering testDumpFile");
        gcc.clear();
        loader.loadAndFind(getTestFileAsStream());
        gcc.dump();
        final int expected = 19;
        assertEquals(expected, gcc.size(), "Should match known file size of 19");
    }

    /** */
    @Test
    void testDumpFromFileAfterFind() {
        log.info("Entering testDumpFile");
        gcc.clear();
        loader.loadAndFind(getTestFileName());
        gcc.dump();
        final int expected = 19;
        assertEquals(expected, gcc.size(), "Should match known file size of 19");
    }

    /**
     * Check that all entries in expected are also in actual. There may be more in
     * actual than expected because Google is inconsistent.
     *
     * @param expected set of keys we expect Google to not find
     * @param actual   keys that Google didn't find
     * @return true if all keys in expected are present in actual
     */
    private boolean compareNotFound(final Collection<String> expected,
        final Collection<String> actual) {
        boolean retval = true;
        for (final String check : expected) {
            if (!actual.contains(check)) {
                log.info("Missing not found: {}", check);
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

    /**
     * @return test file opened in an input stream
     */
    private String getTestFileName() {
        return gedbrowserHome + "/test.txt";
    }
}
