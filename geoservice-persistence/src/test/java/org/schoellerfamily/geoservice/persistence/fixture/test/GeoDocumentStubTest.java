package org.schoellerfamily.geoservice.persistence.fixture.test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;
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
        assertNull(doc.getGeoItem(), "Expected null item");
    }

    /** */
    @Test
    public void testNoArgHasNullName() {
        final GeoDocument doc = new GeoDocumentStub();
        assertNull(doc.getName(), "Expected null name");
    }

    /** */
    @Test
    public void testNoArgHasNullModernName() {
        final GeoDocument doc = new GeoDocumentStub();
        assertNull(doc.getModernName(), "Expected null modern name");
    }

    /** */
    @Test
    public void testNoArgHasNullResult() {
        final GeoDocument doc = new GeoDocumentStub();
        assertNull(doc.getResult(), "Expected null result");
    }

    /** */
    @Test
    public void testNullArgHasNullItem() {
        final GeoDocument doc = new GeoDocumentStub(null);
        assertNull(doc.getGeoItem(), "Expected null item");
    }

    /** */
    @Test
    public void testNullArgHasNullName() {
        final GeoDocument doc = new GeoDocumentStub(null);
        assertNull(doc.getName(), "Expected null name");
    }

    /** */
    @Test
    public void testNullArgHasNullModernName() {
        final GeoDocument doc = new GeoDocumentStub(null);
        assertNull(doc.getModernName(), "Expected null modern name");
    }

    /** */
    @Test
    public void testNullArgHasNullResult() {
        final GeoDocument doc = new GeoDocumentStub(null);
        assertNull(doc.getResult(), "Expected null result");
    }

    /** */
    @Test
    public void testItemHasItem() {
        final GeoCodeItem item = new GeoCodeItem();
        final GeoDocument doc = new GeoDocumentStub(item);
        assertSame(item, doc.getGeoItem(), "Expected same item");
    }

    /** */
    @Test
    public void testSetItemHasItem() {
        final GeoCodeItem item = new GeoCodeItem();
        final GeoDocument doc = new GeoDocumentStub(null);
        doc.setGeoItem(item);
        assertSame(item, doc.getGeoItem(), "Expected same item");
    }

    /** */
    @Test
    public void testLoadItemHasItem() {
        final GeoCodeItem item = new GeoCodeItem();
        final GeoDocument doc = new GeoDocumentStub(null);
        doc.loadGeoCodeItem(item);
        assertSame(item, doc.getGeoItem(), "Expected same item");
    }

    /** */
    @Test
    public void testNullItemHasNullName() {
        final GeoCodeItem item = new GeoCodeItem();
        final GeoDocument doc = new GeoDocumentStub(item);
        assertNull(doc.getName(), "Expected null name");
    }

    /** */
    @Test
    public void testNullItemHasNullModernName() {
        final GeoCodeItem item = new GeoCodeItem();
        final GeoDocument doc = new GeoDocumentStub(item);
        assertNull(doc.getModernName(), "Expected null modern name");
    }

    /** */
    @Test
    public void testNullItemHasNullResult() {
        final GeoCodeItem item = new GeoCodeItem();
        final GeoDocument doc = new GeoDocumentStub(item);
        assertNull(doc.getResult(), "Expected null result");
    }
}