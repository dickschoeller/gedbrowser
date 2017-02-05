package org.schoellerfamily.geoservice.persistence.fixture.test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.schoellerfamily.geoservice.persistence.GeoCodeItem;
import org.schoellerfamily.geoservice.persistence.domain.GeoDocument;
import org.schoellerfamily.geoservice.persistence.fixture.GeoDocumentStub;

/**
 * @author Dick Schoeller
 */
public final class GeoDocumentStubTest {
    /** */
    @Test
    public void testNoArgHasNullItem() {
        final GeoDocument doc = new GeoDocumentStub();
        assertNull("Expected null item", doc.getGeoItem());
    }

    /** */
    @Test
    public void testNoArgHasNullName() {
        final GeoDocument doc = new GeoDocumentStub();
        assertNull("Expected null name", doc.getName());
    }

    /** */
    @Test
    public void testNoArgHasNullModernName() {
        final GeoDocument doc = new GeoDocumentStub();
        assertNull("Expected null modern name", doc.getModernName());
    }

    /** */
    @Test
    public void testNoArgHasNullResult() {
        final GeoDocument doc = new GeoDocumentStub();
        assertNull("Expected null result", doc.getResult());
    }

    /** */
    @Test
    public void testNullArgHasNullItem() {
        final GeoDocument doc = new GeoDocumentStub(null);
        assertNull("Expected null item", doc.getGeoItem());
    }

    /** */
    @Test
    public void testNullArgHasNullName() {
        final GeoDocument doc = new GeoDocumentStub(null);
        assertNull("Expected null name", doc.getName());
    }

    /** */
    @Test
    public void testNullArgHasNullModernName() {
        final GeoDocument doc = new GeoDocumentStub(null);
        assertNull("Expected null modern name", doc.getModernName());
    }

    /** */
    @Test
    public void testNullArgHasNullResult() {
        final GeoDocument doc = new GeoDocumentStub(null);
        assertNull("Expected null result", doc.getResult());
    }

    /** */
    @Test
    public void testItemHasItem() {
        final GeoCodeItem item = new GeoCodeItem();
        final GeoDocument doc = new GeoDocumentStub(item);
        assertSame("Expected same item", item, doc.getGeoItem());
    }

    /** */
    @Test
    public void testSetItemHasItem() {
        final GeoCodeItem item = new GeoCodeItem();
        final GeoDocument doc = new GeoDocumentStub(null);
        doc.setGeoItem(item);
        assertSame("Expected same item", item, doc.getGeoItem());
    }

    /** */
    @Test
    public void testLoadItemHasItem() {
        final GeoCodeItem item = new GeoCodeItem();
        final GeoDocument doc = new GeoDocumentStub(null);
        doc.loadGeoCodeItem(item);
        assertSame("Expected same item", item, doc.getGeoItem());
    }

    /** */
    @Test
    public void testNullItemHasNullName() {
        final GeoCodeItem item = new GeoCodeItem();
        final GeoDocument doc = new GeoDocumentStub(item);
        assertNull("Expected null name", doc.getName());
    }

    /** */
    @Test
    public void testNullItemHasNullModernName() {
        final GeoCodeItem item = new GeoCodeItem();
        final GeoDocument doc = new GeoDocumentStub(item);
        assertNull("Expected null modern name", doc.getModernName());
    }

    /** */
    @Test
    public void testNullItemHasNullResult() {
        final GeoCodeItem item = new GeoCodeItem();
        final GeoDocument doc = new GeoDocumentStub(item);
        assertNull("Expected null result", doc.getResult());
    }
}
