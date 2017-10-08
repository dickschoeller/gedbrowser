package org.schoellerfamily.gedbrowser.api.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSource;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;

/**
 * @author Dick Schoeller
 */
public class ApiSourceTest {
    /** */
    @Test
    public void testDefaultConstructorType() {
        final ApiSource o = new ApiSource();
        assertEquals("type mismatch", "", o.getType());
    }

    /** */
    @Test
    public void testDefaultConstructorString() {
        final ApiSource o = new ApiSource();
        assertEquals("string mismatch", "", o.getString());
    }

    /** */
    @Test
    public void testDefaultConstructorAttributes() {
        final ApiSource o = new ApiSource();
        assertTrue("attributes mismatch", o.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testConstructorType() {
        final ApiSource o = new ApiSource("type", "string");
        assertEquals("type mismatch", "type", o.getType());
    }

    /** */
    @Test
    public void testConstructorString() {
        final ApiSource o = new ApiSource("type", "string");
        assertEquals("string mismatch", "string", o.getString());
    }

    /** */
    @Test
    public void testConstructorNoAttributes() {
        final ApiSource o = new ApiSource("type", "string");
        assertTrue("attributes empty mismatch", o.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testConstructorNullAttributes() {
        final ApiSource o = new ApiSource("type", "string", null);
        assertTrue("attributes empty mismatch", o.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testConstructorWithAttributes() {
        final List<ApiObject> attributes = new ArrayList<>();
        attributes.add(new ApiObject("attribute", "a string"));
        final ApiSource o = new ApiSource("type", "string", attributes);
        assertEquals("attributes size mismatch", 1, o.getAttributes().size());
    }

    /** */
    @Test
    public void testIsType() {
        final ApiSource o = new ApiSource("type", "string");
        assertTrue("isType mismatch", o.isType("type"));
    }

    /** */
    @Test
    public void testAccept() {
        final ApiSource o = new ApiSource("type", "string");
        final ApiTestVisitor visitor = new ApiTestVisitor();
        o.accept(visitor);
        assertEquals("Method mismatch", "source", visitor.getMethodCalled());
    }
}
