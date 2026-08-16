package org.schoellerfamily.geoservice.persistence.mongo.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.schoellerfamily.geoservice.geocoder.GeoCoder;
import org.schoellerfamily.geoservice.geocoder.StubGeoCoder;
import org.schoellerfamily.geoservice.persistence.GeoCode;
import org.schoellerfamily.geoservice.persistence.GeoCodeItem;
import org.schoellerfamily.geoservice.persistence.GeoCodeLoader;
import org.schoellerfamily.geoservice.persistence.fixture.GeoCodeTestFixture;
import org.schoellerfamily.geoservice.persistence.mongo.GeoCodeMongo;
import org.schoellerfamily.geoservice.persistence.mongo.repository.GeoDocumentRepositoryMongo;

import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import lombok.extern.slf4j.Slf4j;

/**
 * Contains integration tests for geo code mongo.
 *
 * @author Richard Schoeller
 */
@SuppressWarnings({ "PMD.CommentSize", "PMD.ExcessiveImports", "PMD.GodClass",
    "PMD.TooManyStaticImports",
    "PMD.TooManyMethods" })
@Slf4j
final class GeoCodeMongoIT {
    /** */
    private transient String dummyFileName;

    /** */
    private GeoCode gcc;

    /** */
    private GeoRepositoryFixture testFixture;

    /** */
    private GeoCodeLoader loader;

    /** */
    private MongoClient mongoClient;

    /**
     * Force debug logging during tests.
     */
    @BeforeAll
    public static void init() {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug");
    }

    @BeforeEach
    void setUp() {
        dummyFileName = System.getProperty("geoservice.dummyfile", "/foo");
        final String host = System.getProperty("spring.data.mongodb.host", "localhost");
        final String port = System.getProperty("spring.data.mongodb.port", "27017");
        final String databaseName = "geoserviceTest_" + UUID.randomUUID();
        final String connectionString = "mongodb://" + host + ":" + port;
        mongoClient = MongoClients.create(connectionString);
        final MongoDatabase mongoDatabase = mongoClient.getDatabase(databaseName);

        final GeoDocumentRepositoryMongo repository = new GeoDocumentRepositoryMongo(mongoDatabase);
        final GeoCoder geoCoder = new StubGeoCoder(new GeoCodeTestFixture().expectedNotFound());
        gcc = new GeoCodeMongo(geoCoder, repository);
        testFixture = new GeoRepositoryFixture(repository, mongoDatabase);
        loader = new GeoCodeLoader(gcc);
        testFixture.loadRepository();
    }

    @AfterEach
    void tearDown() {
        testFixture.clearRepository();
        mongoClient.close();
    }

    @Test
    void testStupidNotFound() {
        log.info("Entering testStupidNotFound");
        final GeoCodeItem entry = gcc.find("XYZZY");
        assertNull(entry.getGeocodingResult(), "Should not have found XYZZY");
    }

    @Test
    void testModernStupidNotFound() {
        log.info("Entering testModernStupidNotFound");
        final GeoCodeItem entry = gcc.find("PLUGH", "XYZZY");
        assertNull(entry.getGeocodingResult(), "Should not have found modern XYZZY");
    }

    @Test
    void testOldHomeFound() {
        log.info("Entering testOldHomeFound");
        final GeoCodeItem entry = gcc.find("3341 Chaucer Lane, Bethlehem, PA, USA");
        final GeocodingResult geocodingResult = entry.getGeocodingResult();
        assertNotNull(geocodingResult, "Should have found 3341 Chaucer Lane");
    }

    private static Stream<Arguments> cacheConsistencyProvider() {
        return Stream.of(
            Arguments.of("XYZZY"),
            Arguments.of("3341 Chaucer Lane, Bethlehem, Pennsylvania, USA"),
            Arguments.of("3341 Chaucer Lane, Bethlehem, PA, USA")
        );
    }

    @ParameterizedTest
    @MethodSource("cacheConsistencyProvider")
    void testCacheConsistency(final String location) {
        log.info("Entering testCacheConsistency with {}", location);
        final GeoCodeItem entry1 = gcc.find(location);
        final GeoCodeItem entry2 = gcc.find(location);
        assertEquals(entry1, entry2, "Should be equal");
    }

    @Test
    void testModernEmpty() {
        log.info("Entering testModernEmpty");
        final GeoCodeItem entry1 = gcc.find("XYZZY");
        final GeoCodeItem entry2 = gcc.find("XYZZY", "");
        assertEquals(entry1, entry2, "Should be equal");
    }

    @Test
    void testModernNull() {
        log.info("Entering testModernNull");
        final GeoCodeItem entry1 = gcc.find("XYZZY");
        final GeoCodeItem entry2 = gcc.find("XYZZY", null);
        assertEquals(entry1, entry2, "Should be equal");
    }

    @Test
    void testCacheRefind() {
        log.info("Entering testCacheReplace");
        gcc.find("XYZZY");
        final GeoCodeItem entry2 = gcc
            .find("XYZZY", "3341 Chaucer Lane, Bethlehem, Pennsylvania, USA");
        final GeoCodeItem entry3 = gcc.find("XYZZY");
        assertEquals(entry2, entry3, "Should be equal");
    }

    @Test
    void testCacheOldHomeModern() {
        log.info("Entering testCacheOldHomeModern");
        final GeoCodeItem entry1 = gcc.find("Old Home", "3341 Chaucer Lane, Bethlehem, PA, USA");
        final GeoCodeItem entry2 = gcc.find("Old Home");
        assertEquals(entry1, entry2, "Should be equal");
    }

    @Test
    void testCacheOldHomeModernBoth() {
        log.info("Entering testCacheOldHomeModernBoth");
        final GeoCodeItem entry1 = gcc.find("Old Home", "3341 Chaucer Lane, Bethlehem, PA, USA");
        final GeoCodeItem entry2 = gcc.find("Old Home", "3341 Chaucer Lane, Bethlehem, PA, USA");
        assertEquals(entry1, entry2, "Should be equal");
    }

    @Test
    void testCacheOldHomeModernChange() {
        log.info("Entering testCacheOldHomeModernChange");
        final GeoCodeItem entry1 = gcc.find("Old Home");
        final GeoCodeItem entry2 = gcc.find("Old Home", "3341 Chaucer Lane, Bethlehem, PA, USA");
        assertNotEquals(entry1, entry2, "Should NOT be equal");
    }

    @Test
    void testCacheReplaceCheckEquals() {
        log.info("Entering testCacheReplace");
        gcc.find("XYZZY");
        final GeoCodeItem entry2 = gcc.find("XYZZY", "XYZZY");
        final GeoCodeItem entry3 = gcc.find("XYZZY", "XYZZY");
        assertEquals(entry2, entry3, "Should be equal");
    }

    @Test
    void testCacheReplaceCheckNullGeocodingResult() {
        log.info("Entering testCacheReplace");
        gcc.find("XYZZY");
        gcc.find("XYZZY", "XYZZY");
        final GeoCodeItem entry3 = gcc.find("XYZZY", "XYZZY");
        assertNull(entry3.getGeocodingResult(), "Geocoding result should be null");
    }

    @Test
    void testCacheOldHomeModernSet() {
        log.info("Entering testCacheOldHomeModernSet");
        gcc.find("Old Home");
        final GeoCodeItem entry2 = gcc.find("Old Home", "3341 Chaucer Lane, Bethlehem, PA, USA");
        final GeoCodeItem entry3 = gcc.find("Old Home");
        assertEquals(entry2, entry3, "Should be equal");
    }

    @Test
    void testCacheOldHomeLocationResultNotNull() {
        log.info("Entering testCacheOldHomeLocation");
        final GeoCodeItem entry = gcc.find("Old Home", "3341 Chaucer Lane, Bethlehem, PA, USA");
        final GeocodingResult geocodingResult = entry.getGeocodingResult();
        assertNotNull(geocodingResult, "geocoding result should not be null");
    }

    @Test
    void testCacheOldHomeLocationLatLngMatch() {
        log.info("Entering testCacheOldHomeLocation");
        final GeoCodeItem entry = gcc.find("Old Home", "3341 Chaucer Lane, Bethlehem, PA, USA");
        final LatLng expected = new LatLng(40.65800200, -75.40644300);
        final GeocodingResult geocodingResult = entry.getGeocodingResult();
        final LatLng actual = geocodingResult.geometry.location;
        assertEquals(expected.toString(), actual.toString(),
                "there was a result, but the lat/long was wrong");
    }

    @Test
    void testNotFounds() {
        log.info("Entering testNotFounds");
        testFixture.loadTestAddresses(gcc);
        final List<String> expectList = Arrays.asList(testFixture.expectedNotFound());
        final Collection<String> expected = new HashSet<>(expectList);
        final Collection<String> actual = gcc.notFoundKeys();
        assertTrue(compareNotFound(expected, actual), "Some differences in not found sets");
    }

    @Test
    void testNotFoundsFromFile() throws IOException {
        log.info("Entering testNotFounds");
        try (InputStream fis = getTestFileAsStream()) {
            loader.load(fis);
            final List<String> expectList = Arrays.asList(testFixture.expectedNotFound());
            final Collection<String> expected = new HashSet<>(expectList);
            final Collection<String> actual = gcc.notFoundKeys();
            assertTrue(compareNotFound(expected, actual), "Some differences in not found sets");
        }
    }

    @Test
    void testCountNotFoundsLowBound() {
        log.info("Entering testCountNotFounds");
        testFixture.loadTestAddresses(gcc);
        final int count = gcc.countNotFound();
        // Count does not seem to be deterministic with Google's APIs.
        assertTrue(count >= testFixture.expectedLowBound(), "Count too low at: " + count);
    }

    @Test
    void testCountNotFoundsHighBound() {
        log.info("Entering testCountNotFounds");
        testFixture.loadTestAddresses(gcc);
        final int count = gcc.countNotFound();
        // Count does not seem to be deterministic with Google's APIs.
        assertTrue(count <= testFixture.expectedHighBound(), "Count too high at: " + count);
    }

    @Test
    void testCountNotFoundsFromFileLowBound() throws IOException {
        log.info("Entering testCountNotFounds");
        try (InputStream fis = getTestFileAsStream()) {
            loader.load(fis);
            final int count = gcc.countNotFound();
            // Count does not seem to be deterministic with Google's APIs.
            assertTrue(count >= testFixture.expectedLowBound(), "Count too low at: " + count);
        }
    }

    @Test
    void testCountNotFoundsFromFileHighBound() throws IOException {
        log.info("Entering testCountNotFounds");
        try (InputStream fis = getTestFileAsStream()) {
            loader.load(fis);
            // Count does not seem to be deterministic with Google's APIs.
            final int count = gcc.countNotFound();
            assertTrue(count <= testFixture.expectedHighBound(), "Count too high at: " + count);
        }
    }

    private boolean compareNotFound(final Collection<String> expected,
            final Collection<String> actual) {
        for (final String check : expected) {
            if (!actual.contains(check)) {
                log.info("Missing not found: {}", check);
                return false;
            }
        }
        return true;
    }

    @Test
    void testSize() {
        log.info("Entering testSize");
        testFixture.loadTestAddresses(gcc);
        final int expected = 19;
        assertEquals(expected, gcc.size(), "Should match known table size of 19");
    }

    @Test
    void testSizeFromResource() throws IOException {
        log.info("Entering testSizeFromFile");
        try (InputStream fis = getTestFileAsStream()) {
            loader.load(fis);
            final int expected = 19;
            assertEquals(expected, gcc.size(), "Should match known file size of 19");
        }
    }

    @Test
    void testSizeLoadFileError() {
        log.info("Entering testSizeFromFile");
        loader.load(dummyFileName);
        final int expected = 0;
        assertEquals(expected, gcc.size(),
            "Should be 0 because of file not found, was: " + gcc.size());
    }

    @Test
    void testDump() {
        log.info("Entering testDump");
        testFixture.loadTestAddresses(gcc);
        gcc.dump();
        final int expected = 19;
        assertEquals(expected, gcc.size(), "Should match known table size of 19");
    }

    @Test
    void testDumpFromFile() throws IOException {
        log.info("Entering testDumpFile");
        try (InputStream fis = getTestFileAsStream()) {
            loader.load(fis);
            gcc.dump();
            final int expected = 19;
            assertEquals(expected, gcc.size(), "Should match known file size of 19");
        }
    }

    @Test
    void testAllKeysSize() {
        log.info("Entering testAllKeysSize");
        testFixture.loadTestAddresses(gcc);
        final int expected = 19;
        assertEquals(expected, gcc.allKeys().size(), "Should match known table size of 19");
    }

    @Test
    void testAllKeys() {
        log.info("Entering testAllKeys");
        testFixture.loadTestAddresses(gcc);
        assertTrue(gcc.allKeys().contains("America"), "Should contain search string");
    }

    @Test
    void testDelete() {
        log.info("Entering testDelete");
        testFixture.loadTestAddresses(gcc);
        final GeoCodeItem gci = gcc.find("America");
        gcc.delete(gci);
        assertFalse(gcc.allKeys().contains("America"), "Should not contain search string");
    }

    @Test
    void testAddGet() {
        final GeoCodeItem input = new GeoCodeItem("America");
        gcc.add(input);
        final GeoCodeItem output = gcc.get("America");
        assertEquals(input, output, "Items should match");
    }

    @Test
    void testAddFind() {
        final GeoCodeItem input = new GeoCodeItem("America");
        gcc.add(input);
        final GeoCodeItem output = gcc.find("America");
        assertNotEquals(input, output, "Items should not match");
    }

    @Test
    void testAddFindWithModern() {
        final GeoCodeItem input = new GeoCodeItem("America", "America");
        gcc.add(input);
        final GeoCodeItem output = gcc.find("America", "America");
        assertNotEquals(input, output, "Items should not match");
    }

    @Test
    void testAddFindChangeModernResultsPresent() {
        final GeoCodeItem input = new GeoCodeItem("XYZZY", "XYZZY");
        gcc.add(input);
        final GeoCodeItem output = gcc.find("XYZZY", "America");
        assertTrue(input.getGeocodingResult() == null && output.getGeocodingResult() != null,
            "Output should have result");
    }

    @Test
    void testAddFindChangeModernNamesMatch() {
        final GeoCodeItem input = new GeoCodeItem("XYZZY", "XYZZY");
        gcc.add(input);
        final GeoCodeItem output = gcc.find("XYZZY", "America");
        assertNotEquals(input.getModernPlaceName(), output.getModernPlaceName(),
            "Items should not match");
    }

    @Test
    void testAddFindChangeModernToBogusResultsPresent() {
        final GeoCodeItem input = new GeoCodeItem("XYZZY", "XYZZY");
        gcc.add(input);
        final GeoCodeItem output = gcc.find("XYZZY", "PLUGH");
        assertTrue(input.getGeocodingResult() == null && output.getGeocodingResult() == null,
            "Output should not have result");
    }

    @Test
    void testAddFindChangeModernToBogusNamesMatch() {
        final GeoCodeItem input = new GeoCodeItem("XYZZY", "XYZZY");
        gcc.add(input);
        final GeoCodeItem output = gcc.find("XYZZY", "PLUGH");
        assertNotEquals(input.getModernPlaceName(), output.getModernPlaceName(),
            "Items should not match");
    }

    private InputStream getTestFileAsStream() {
        return getClass().getResourceAsStream("/test.txt");
    }
}
