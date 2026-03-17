package org.schoellerfamily.geoservice.persistence.mongo.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.geoservice.persistence.GeoCodeItem;
import org.schoellerfamily.geoservice.persistence.domain.GeoDocument;
import org.schoellerfamily.geoservice.persistence.mongo.domain.GeoDocumentMongo;
import org.schoellerfamily.geoservice.persistence.mongo.domain.GeoDocumentMongoFactory;

/**
 * Contains integration tests for geo document mongo factory.
 *
 * @author Richard Schoeller
 */
final class GeoDocumentMongoFactoryIT {
    @Test
    void testToGeoCodeItem() {
        final GeoDocument document = new GeoDocumentMongo();
        final GeoCodeItem actual = new GeoCodeItem("XYZZY", "PLUGH");
        document.loadGeoCodeItem(actual);
        final GeoCodeItem expected = GeoDocumentMongoFactory.getInstance()
                .createGeoCodeItem(document);
        assertEquals(expected, actual, "Items should be the same");
    }

    @Test
    void testToGeoCodeItemNullName() {
        final GeoDocument document = new GeoDocumentMongo();
        final GeoCodeItem actual = new GeoCodeItem(null, "PLUGH");
        document.loadGeoCodeItem(actual);
        final GeoCodeItem expected = GeoDocumentMongoFactory.getInstance()
                .createGeoCodeItem(document);
        assertEquals(expected, actual, "Items should be the same");
    }

    @Test
    void testToGeoCodeItemNullModern() {
        final GeoDocument document = new GeoDocumentMongo();
        final GeoCodeItem actual = new GeoCodeItem("XYZZY", (String) null);
        document.loadGeoCodeItem(actual);
        final GeoCodeItem expected = GeoDocumentMongoFactory.getInstance()
                .createGeoCodeItem(document);
        assertEquals(expected, actual, "Items should be the same");
    }

    @Test
    void testToGeoCodeItemNull() {
        final GeoCodeItem expected = new GeoCodeItem();
        final GeoCodeItem actual = GeoDocumentMongoFactory.getInstance()
                .createGeoCodeItem(null);
        assertEquals(expected, actual, "Items should be the same");
    }

    @Test
    void testToGeoDocument() {
        final GeoCodeItem item = new GeoCodeItem("XYZZY", "PLUGH");
        final GeoDocument actual = GeoDocumentMongoFactory.getInstance()
                .createGeoDocument(item);
        assertTrue(compare(item, actual), "GeoDocument and GeoCodeItem should match");
    }

    @Test
    void testToGeoDocumentNull() {
        final GeoCodeItem item = new GeoCodeItem();
        final GeoDocument actual = GeoDocumentMongoFactory.getInstance()
                .createGeoDocument(null);
        assertTrue(compare(item, actual), "GeoDocument and GeoCodeItem should match");
    }

    private boolean compare(final GeoCodeItem item, final GeoDocument doc) {
        // TODO move this method into a utility class somewhere
        // where it can be more broadly used
        if (!compare(item.getPlaceName(), doc.getName())) {
            return false;
        }
        if (!compare(item.getModernPlaceName(), doc.getModernName())) {
            return false;
        }
        if (item.getGeocodingResult() == null && doc.getResult() == null) {
            return true;
        }
        if (item.getGeocodingResult() == null || doc.getResult() == null) {
            return false;
        }
        return (item.getGeocodingResult().equals(doc.getResult()));
    }

    private boolean compare(final String arg0, final String arg1) {
        // TODO move this method into a utility class somewhere
        // where it can be more broadly used
        if (arg0 == null && arg1 == null) {
            return true;
        }
        if (arg0 == null || arg1 == null) {
            return false;
        }
        return (arg0.equals(arg1));
    }
}
