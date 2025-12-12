package org.schoellerfamily.geoservice.persistence.test;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

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

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@SuppressWarnings({ "PMD.ExcessiveImports", "PMD.ExcessivePublicCount",
        "PMD.GodClass", "PMD.TooManyStaticImports" })
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
    @BeforeClass
    public static void setUp() {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug");
    }

    /** */
    @Test
    public void testStupidNotFound() {
        log.info("Entering testStupidNotFound");
        gcc.clear();
        final GeoCodeItem entry = gcc.find("XYZZY");
        assertNull("Should not have found XYZZY",
                entry.getGeocodingResult());
    }

    /** */
    @Test
    public void testModernStupidNotFound() {
        log.info("Entering testModernStupidNotFound");
        gcc.clear();
        final GeoCodeItem entry = gcc.find("PLUGH", "XYZZY");
        assertNull("Should not have found modern XYZZY",
                entry.getGeocodingResult());
    }

    /** */
    @Test
    public void testOldHomeFound() {
        log.info("Entering testOldHomeFound");
        gcc.clear();
        final GeoCodeItem entry = gcc
                .find("3341 Chaucer Lane, Bethlehem, PA, USA");
        assertNotNull("Should have found 3341 Chaucer Lane",
                entry.getGeocodingResult());
    }

    /** */
    @Test
    public void testCacheStupid() {
        log.info("Entering testCacheStupid");
        gcc.clear();
        final GeoCodeItem entry1 = gcc.find("XYZZY");
        final GeoCodeItem entry2 = gcc.find("XYZZY");
        assertEquals("Should be equal", entry1, entry2);
    }

    /** */
    @Test
    public void testModernEmpty() {
        log.info("Entering testModernEmpty");
        gcc.clear();
        final GeoCodeItem entry1 = gcc.find("XYZZY");
        final GeoCodeItem entry2 = gcc.find("XYZZY", "");
        assertEquals("Should be equal", entry1, entry2);
    }

    /** */
    @Test
    public void testModernNull() {
        log.info("Entering testModernNull");
        gcc.clear();
        final GeoCodeItem entry1 = gcc.find("XYZZY");
        final GeoCodeItem entry2 = gcc.find("XYZZY", null);
        assertEquals("Should be equal", entry1, entry2);
    }

    /** */
    @Test
    public void testCacheFound() {
        log.info("Entering testCacheFound");
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
        log.info("Entering testCacheReplace");
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
        log.info("Entering testCacheOldHome");
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
        log.info("Entering testCacheOldHomeModern");
        gcc.clear();
        final GeoCodeItem entry1 = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        final GeoCodeItem entry2 = gcc.find("Old Home");
        assertEquals("Should be equal", entry1, entry2);
    }

    /** */
    @Test
    public void testCacheOldHomeModernBoth() {
        log.info("Entering testCacheOldHomeModernBoth");
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
        log.info("Entering testCacheOldHomeModernChange");
        gcc.clear();
        final GeoCodeItem entry1 = gcc.find("Old Home");
        final GeoCodeItem entry2 = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        assertNotEquals("Should NOT be equal", entry1, entry2);
    }

    /** */
    @Test
    public void testCacheOldHomeModernToBroken() {
        log.info("Entering testCacheOldHomeModernChange");
        gcc.clear();
        final GeoCodeItem entry1 = gcc.find("Old Home");
        final GeoCodeItem entry2 = gcc.find("Old Home", "Blah Blah");
        assertNotEquals("Should NOT be equal", entry1, entry2);
    }

    /** */
    @Test
    public void testCacheOldHomeBroken() {
        log.info("Entering testCacheOldHomeModernChange");
        gcc.clear();
        final GeoCodeItem entry1 = gcc
                .add(new GeoCodeItem("Old Home", "Blah Blah"));
        final GeoCodeItem entry2 = gcc.find("Old Home", "Blah Blah");
        assertEquals("Should be equal", entry1, entry2);
    }

    /** */
    @Test
    public void testCacheOldHomeAddThenFind() {
        log.info("Entering testCacheOldHomeModernChange");
        gcc.clear();
        final GeoCodeItem entry1 = gcc.add(new GeoCodeItem("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA"));
        final GeoCodeItem entry2 = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA");
        assertNotEquals("Should NOT be equal", entry1, entry2);
    }

    /** */
    @Test
    public void testCacheAddThenFind() {
        log.info("Entering testCacheOldHomeModernChange");
        gcc.clear();
        final GeoCodeItem entry1 = gcc
                .add(new GeoCodeItem("3341 Chaucer Lane, Bethlehem, PA"));
        final GeoCodeItem entry2 = gcc.find("3341 Chaucer Lane, Bethlehem, PA");
        assertNotEquals("Should NOT be equal", entry1, entry2);
    }

    /** */
    @Test
    public void testCacheAllKeysSize() {
        log.info("Entering testCacheOldHomeModernChange");
        gcc.clear();
        gcc.add(new GeoCodeItem("Old Home"));
        gcc.add(new GeoCodeItem("At Sea"));
        assertEquals("Should be 2 items", 2, gcc.allKeys().size());
    }

    /** */
    @Test
    public void testCacheAllKeysContains() {
        log.info("Entering testCacheOldHomeModernChange");
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
        log.info("Entering testCacheReplace");
        gcc.clear();
        gcc.find("XYZZY");
        final GeoCodeItem entry2 = gcc.find("XYZZY", "XYZZY");
        final GeoCodeItem entry3 = gcc.find("XYZZY", "XYZZY");
        assertEquals("Should be equal", entry2, entry3);
    }

    /** */
    @Test
    public void testCacheReplaceEmptyResult() {
        log.info("Entering testCacheReplace");
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
        log.info("Entering testCacheOldHomeModernSet");
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
        log.info("Entering testCacheOldHomeLocation");
        gcc.clear();
        final GeoCodeItem entry = gcc.find("Old Home",
                "3341 Chaucer Lane, Bethlehem, PA, USA");
        final GeocodingResult geocodingResult = entry.getGeocodingResult();
        assertNotNull("geocoding result should not be null", geocodingResult);
    }

    /** */
    @Test
    public void testCacheOldHomeLocationResultMatch() {
        log.info("Entering testCacheOldHomeLocation");
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
        log.info("Entering testNotFounds");
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
    public void testNotFoundsFromStream() {
        log.info("Entering testNotFounds");
        gcc.clear();
        loader.load(getTestFileAsStream());
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
        log.info("Entering testNotFounds");
        gcc.clear();
        loader.load(getTestFileName());
        final List<String> expectList = Arrays
                .asList(testFixture.expectedNotFound());
        final Collection<String> expected = new HashSet<>(expectList);
        final Collection<String> actual = gcc.notFoundKeys();
        assertTrue("Some differences in not found sets",
                compareNotFound(expected, actual));
    }

    /** */
    @Test
    public void testNotParsingFromStream() {
        log.info("Entering testNotFounds");
        gcc.clear();
        loader.load(getTestFileAsStream());
        final GeoCodeItem item = gcc.get("At Sea");
        assertEquals("Failure indicates a parsing problem",
                "Atlantic Ocean", item.getModernPlaceName());
    }

    /** */
    @Test
    public void testNotParsingFromFile() {
        log.info("Entering testNotFounds");
        gcc.clear();
        loader.load(getTestFileName());
        final GeoCodeItem item = gcc.get("At Sea");
        assertEquals("Failure indicates a parsing problem",
                "Atlantic Ocean", item.getModernPlaceName());
    }

    /** */
    @Test
    public void testCountNotFoundsLowBound() {
        log.info("Entering testCountNotFounds");
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
        log.info("Entering testCountNotFounds");
        gcc.clear();
        testFixture.loadTestAddresses();
        final int count = gcc.countNotFound();
        // Count does not seem to be deterministic with Google's APIs.
        assertTrue("Count too high at: " + count,
                count <= testFixture.expectedHighBound());
    }

    /** */
    @Test
    public void testCountNotFoundsFromStreamLowBound() {
        log.info("Entering testCountNotFounds");
        gcc.clear();
        loader.load(getTestFileAsStream());
        final int count = gcc.countNotFound();
        // Count does not seem to be deterministic with Google's APIs.
        assertTrue("Count too low at: " + count,
                count >= testFixture.expectedLowBound());
    }

    /** */
    @Test
    public void testCountNotFoundsFromFileLowBound() {
        log.info("Entering testCountNotFounds");
        gcc.clear();
        loader.load(getTestFileName());
        final int count = gcc.countNotFound();
        // Count does not seem to be deterministic with Google's APIs.
        assertTrue("Count too low at: " + count,
                count >= testFixture.expectedLowBound());
    }

    /** */
    @Test
    public void testCountNotFoundsFromStreamHighBound() {
        log.info("Entering testCountNotFounds");
        gcc.clear();
        loader.load(getTestFileAsStream());
        final int count = gcc.countNotFound();
        // Count does not seem to be deterministic with Google's APIs.
        assertTrue("Count too high at: " + count,
                count <= testFixture.expectedHighBound());
    }

    /** */
    @Test
    public void testCountNotFoundsFromFileHighBound() {
        log.info("Entering testCountNotFounds");
        gcc.clear();
        loader.load(getTestFileName());
        final int count = gcc.countNotFound();
        // Count does not seem to be deterministic with Google's APIs.
        assertTrue("Count too high at: " + count,
                count <= testFixture.expectedHighBound());
    }

    /** */
    @Test
    public void testSize() {
        log.info("Entering testSize");
        gcc.clear();
        testFixture.loadTestAddresses();
        final int expected = 19;
        assertEquals("Should match known table size of 19",
                expected, gcc.size());
    }

    /** */
    @Test
    public void testSizeFromStream() {
        log.info("Entering testSizeFromFile");
        gcc.clear();
        loader.load(getTestFileAsStream());
        final int expected = 19;
        assertEquals("Should match known file size of 19",
                expected, gcc.size());
    }

    /** */
    @Test
    public void testSizeFromFile() {
        log.info("Entering testSizeFromFile");
        gcc.clear();
        loader.load(getTestFileName());
        final int expected = 19;
        assertEquals("Should match known file size of 19",
                expected, gcc.size());
    }

    /** */
    @Test
    public void testSizeLoadFileError() {
        log.info("Entering testSizeFromFile");
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
        log.info("Entering testSizeFromFile");
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
        log.info("Entering testDump");
        gcc.clear();
        testFixture.loadTestAddresses();
        gcc.dump();
        final int expected = 19;
        assertEquals("Should match known table size of 19",
                expected, gcc.size());
    }

    /** */
    @Test
    public void testDumpFromStream() {
        log.info("Entering testDumpFile");
        gcc.clear();
        loader.load(getTestFileAsStream());
        gcc.dump();
        final int expected = 19;
        assertEquals("Should match known file size of 19",
                expected, gcc.size());
    }

    /** */
    @Test
    public void testDumpFromFile() {
        log.info("Entering testDumpFile");
        gcc.clear();
        loader.load(getTestFileName());
        gcc.dump();
        final int expected = 19;
        assertEquals("Should match known file size of 19",
                expected, gcc.size());
    }

    /** */
    @Test
    public void testDumpFromStreamAfterFind() {
        log.info("Entering testDumpFile");
        gcc.clear();
        loader.loadAndFind(getTestFileAsStream());
        gcc.dump();
        final int expected = 19;
        assertEquals("Should match known file size of 19",
                expected, gcc.size());
    }

    /** */
    @Test
    public void testDumpFromFileAfterFind() {
        log.info("Entering testDumpFile");
        gcc.clear();
        loader.loadAndFind(getTestFileName());
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
