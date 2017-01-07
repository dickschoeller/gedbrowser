package org.schoellerfamily.geoservice.persistence.mongo.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.schoellerfamily.geoservice.persistence.GeoCodeItem;
import org.schoellerfamily.geoservice.persistence.domain.GeoDocument;
import org.schoellerfamily.geoservice.persistence.mongo.domain.GeoDocumentMongo;
import org.schoellerfamily.geoservice.persistence.mongo.domain.GeoDocumentMongoFactory;

/**
 * Test of the factory that creates GeoDocumentMongo objects from GeoCodeItem
 * objects, and vice versa.
 *
 * @author Dick Schoeller
 */
public final class GeoDocumentMongoFactoryTest {
    /** */
    @Test
    public void testToGeoCodeItem() {
        final GeoDocument document = new GeoDocumentMongo();
        final GeoCodeItem actual = new GeoCodeItem("XYZZY", "PLUGH");
        document.loadGeoCodeItem(actual);
        final GeoCodeItem expected = GeoDocumentMongoFactory.getInstance()
                .createGeoCodeItem(document);
        assertEquals("Items should be the same", actual, expected);
    }

    /** */
    @Test
    public void testToGeoCodeItemNullName() {
        final GeoDocument document = new GeoDocumentMongo();
        final GeoCodeItem actual = new GeoCodeItem(null, "PLUGH");
        document.loadGeoCodeItem(actual);
        final GeoCodeItem expected = GeoDocumentMongoFactory.getInstance()
                .createGeoCodeItem(document);
        assertEquals("Items should be the same", actual, expected);
    }

    /** */
    @Test
    public void testToGeoCodeItemNullModern() {
        final GeoDocument document = new GeoDocumentMongo();
        final GeoCodeItem actual = new GeoCodeItem("XYZZY", (String) null);
        document.loadGeoCodeItem(actual);
        final GeoCodeItem expected = GeoDocumentMongoFactory.getInstance()
                .createGeoCodeItem(document);
        assertEquals("Items should be the same", actual, expected);
    }

    /** */
    @Test
    public void testToGeoCodeItemNull() {
        final GeoCodeItem expected = new GeoCodeItem();
        final GeoCodeItem actual = GeoDocumentMongoFactory.getInstance()
                .createGeoCodeItem(null);
        assertEquals("Items should be the same", expected, actual);
    }

    /** */
    @Test
    public void testToGeoDocument() {
        final GeoCodeItem item = new GeoCodeItem("XYZZY", "PLUGH");
        final GeoDocument actual = GeoDocumentMongoFactory.getInstance()
                .createGeoDocument(item);
        assertTrue(compare(item, actual));
    }

    /** */
    @Test
    public void testToGeoDocumentNull() {
        final GeoCodeItem item = new GeoCodeItem();
        final GeoDocument actual = GeoDocumentMongoFactory.getInstance()
                .createGeoDocument(null);
        assertTrue(compare(item, actual));
    }

    /**
     * @param item a geocode item
     * @param doc a geo document
     * @return true if their fields match
     */
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

    /**
     * Compare 2 strings. Both null is considered to be a match.
     *
     * @param arg0 a string
     * @param arg1 another string
     * @return true if they match
     */
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
