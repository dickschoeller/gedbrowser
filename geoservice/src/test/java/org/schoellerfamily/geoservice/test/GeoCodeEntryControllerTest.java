package org.schoellerfamily.geoservice.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.geoservice.controller.GeoCodeEntryController;
import org.schoellerfamily.geoservice.model.GeoServiceItem;
import org.schoellerfamily.geoservice.model.GeoServiceGeocodingResult;
import org.schoellerfamily.geoservice.persistence.GeoCode;
import org.schoellerfamily.geoservice.persistence.GeoCodeItem;
import org.schoellerfamily.geoservice.persistence.domain.GeoDocument;
import org.geojson.Feature;
import org.geojson.FeatureCollection;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.exceptions.HttpStatusException;

/**
 * Unit tests for {@link GeoCodeEntryController}.
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class GeoCodeEntryControllerTest {

    /** In-memory persistence used by tests. */
    private InMemoryGeoCode geoCode;

    /** Controller under test. */
    private GeoCodeEntryController controller;

    @BeforeEach
    void setUp() {
        geoCode = new InMemoryGeoCode();
        controller = new GeoCodeEntryController(geoCode);
    }

    @Test
    void testCreateReturnsBadRequestWhenItemIsNull() {
        final HttpResponse<GeoServiceItem> response = controller.create(null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
    }

    @Test
    void testCreateReturnsConflictWhenPlaceAlreadyExists() {
        geoCode.add(new GeoCodeItem("Existing Place", "Existing Modern"));

        final HttpResponse<GeoServiceItem> response = controller.create(
            new GeoServiceItem("Existing Place", "Any Modern", null));

        assertEquals(HttpStatus.CONFLICT, response.getStatus());
    }

    @Test
    void testCreateNormalizesBlankModernName() {
        controller.create(new GeoServiceItem("Create Normalize", "", null));
        assertEquals("Create Normalize", geoCode.get("Create Normalize").getModernPlaceName());
    }

    @Test
    void testCreateReturnsBadRequestWhenResultMalformed() {
        final HttpResponse<GeoServiceItem> response = controller.create(
            new GeoServiceItem("Create Broken", "Create Broken Modern", malformedItem().getResult()));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
    }

    @Test
    void testCreateReturnsCreatedForNewPlace() {
        final HttpResponse<GeoServiceItem> response = controller.create(
            new GeoServiceItem("Create Success", "Create Modern", null));

        assertEquals(HttpStatus.CREATED, response.getStatus());
    }

    @Test
    void testUpdateReturnsBadRequestWhenItemIsNull() {
        final HttpResponse<GeoServiceItem> response = controller.update(null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
    }

    @Test
    void testUpdateReturnsBadRequestWhenNameBlank() {
        final HttpResponse<GeoServiceItem> response = controller.update(
            new GeoServiceItem("   ", "Modern", null));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
    }

    @Test
    void testUpdateReturnsNotFoundWhenPlaceMissing() {
        final HttpResponse<GeoServiceItem> response = controller.update(
            new GeoServiceItem("Missing Place", "Modern", null));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
    }

    @Test
    void testUpdateNormalizesBlankModernName() {
        geoCode.add(new GeoCodeItem("Update Normalize", "Before"));

        controller.update(new GeoServiceItem("Update Normalize", "", null));
        assertEquals("Update Normalize", geoCode.get("Update Normalize").getModernPlaceName());
    }

    @Test
    void testUpdateReturnsBadRequestWhenResultMalformed() {
        geoCode.add(new GeoCodeItem("Update Broken", "Before"));

        final HttpResponse<GeoServiceItem> response = controller.update(
            new GeoServiceItem("Update Broken", "Update Broken Modern", malformedItem().getResult()));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
    }

    @Test
    void testUpdateReturnsOkForExistingPlace() {
        geoCode.add(new GeoCodeItem("Update Success", "Before"));

        final HttpResponse<GeoServiceItem> response = controller.update(
            new GeoServiceItem("Update Success", "After", null));

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("After", geoCode.get("Update Success").getModernPlaceName());
    }

    @Test
    void testDeleteReturnsBadRequestWhenNameBlank() {
        final HttpResponse<GeoServiceItem> response = controller.delete("   ");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
    }

    @Test
    void testDeleteTreatsPercentLiteralAsLiteral() {
        final HttpResponse<GeoServiceItem> response = controller.delete("Bad%2");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
    }

    @Test
    void testDeleteReturnsNotFoundWhenPlaceMissing() {
        final HttpResponse<GeoServiceItem> response = controller.delete("Missing");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
    }

    @Test
    void testDeleteUsesDecodedNameAndRemovesEntry() {
        geoCode.add(new GeoCodeItem("Alpha Beta", "Alpha Beta"));

        controller.delete("Alpha Beta");
        assertNull(geoCode.get("Alpha Beta"));
    }

    @Test
    void testDeleteReturnsDeletedBody() {
        geoCode.add(new GeoCodeItem("Delete Body", "Delete Modern"));

        final HttpResponse<GeoServiceItem> response = controller.delete("Delete Body");
        assertNotNull(response.body());
    }

    @Test
    void testFindWithoutModernUsesSingleArgFind() {
        controller.find("FindOne", "");
        assertEquals("FindOne", geoCode.lastFindName);
        assertEquals(Boolean.TRUE, geoCode.singleArgFindUsed);
    }

    @Test
    void testFindWithModernUsesTwoArgFind() {
        controller.find("FindTwo", "ModernTwo");
        assertEquals("ModernTwo", geoCode.lastFindModernName);
        assertEquals(Boolean.TRUE, geoCode.twoArgFindUsed);
    }

    @Test
    void testFindAcceptsPercentLiteralName() {
        final GeoServiceItem item = controller.find("Bad%2", "");

        assertEquals("Bad%2", item.getPlaceName());
    }

    @Test
    void testFindReturnsBadRequestWhenNameBlank() {
        final HttpStatusException ex = assertThrows(HttpStatusException.class,
            () -> controller.find("   ", ""));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }

    @Test
    void testFindReturnsBadRequestWhenNameEmpty() {
        final HttpStatusException ex = assertThrows(HttpStatusException.class,
            () -> controller.find("", ""));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }

    @Test
    void testFindTreatsDecodedBlankModernNameAsAbsent() {
        controller.find("FindBlankModern", " ");
        assertEquals("FindBlankModern", geoCode.lastFindName);
        assertEquals(Boolean.TRUE, geoCode.singleArgFindUsed);
    }

    private GeoServiceItem malformedItem() {
        final FeatureCollection geometry = new FeatureCollection();
        geometry.add(new Feature());
        geometry.add(new Feature());
        final GeoServiceGeocodingResult result = new GeoServiceGeocodingResult(
            null, "Malformed", null, geometry, null, false, "placeId");
        return new GeoServiceItem("Malformed", "Malformed Modern", result);
    }

    /**
     * Minimal in-memory GeoCode implementation for unit tests.
     */
    private static final class InMemoryGeoCode implements GeoCode {
        /** In-memory entries keyed by place name. */
        private final Map<String, GeoCodeItem> store = new HashMap<>();

        /** Last place name passed to find. */
        private String lastFindName;

        /** Last modern name passed to two-arg find. */
        private String lastFindModernName;

        /** Whether the single-arg `find` overload was used. */
        private Boolean singleArgFindUsed;

        /** Whether the two-arg `find` overload was used. */
        private Boolean twoArgFindUsed;

        @Override
        public void clear() {
            store.clear();
        }

        @Override
        public GeoCodeItem find(final String placeName) {
            lastFindName = placeName;
            lastFindModernName = null;
            singleArgFindUsed = Boolean.TRUE;
            twoArgFindUsed = Boolean.FALSE;
            final GeoCodeItem existing = store.get(placeName);
            return existing == null ? new GeoCodeItem(placeName) : existing;
        }

        @Override
        public GeoCodeItem find(final String placeName, final String modernPlaceName) {
            lastFindName = placeName;
            lastFindModernName = modernPlaceName;
            singleArgFindUsed = Boolean.FALSE;
            twoArgFindUsed = Boolean.TRUE;
            final GeoCodeItem existing = store.get(placeName);
            if (existing == null) {
                return new GeoCodeItem(placeName, modernPlaceName);
            }
            return existing;
        }

        @Override
        public Collection<String> allKeys() {
            return Collections.unmodifiableSet(store.keySet());
        }

        @Override
        public void dump() {
            // no-op
        }

        @Override
        public int countNotFound() {
            return 0;
        }

        @Override
        public Collection<String> notFoundKeys() {
            return Collections.emptySet();
        }

        @Override
        public long size() {
            return store.size();
        }

        @Override
        public GeoCodeItem add(final GeoCodeItem item) {
            store.put(item.getPlaceName(), item);
            return item;
        }

        @Override
        public GeoCodeItem update(final GeoCodeItem item) {
            return add(item);
        }

        @Override
        public GeoCodeItem delete(final GeoCodeItem item) {
            store.remove(item.getPlaceName());
            return item;
        }

        @Override
        public GeoCodeItem get(final String placeName) {
            return store.get(placeName);
        }

        @Override
        public GeoDocument create(final GeoCodeItem item) {
            return null;
        }

        @Override
        public Iterable<? extends GeoDocument> findAllDocuments() {
            return Collections.emptyList();
        }

        @Override
        public GeoDocument addDocument(final GeoDocument document) {
            return document;
        }

        @Override
        public GeoDocument getDocument(final String placeName) {
            return null;
        }

        @Override
        public GeoDocument deleteDocument(final String placeName) {
            return null;
        }
    }
}
