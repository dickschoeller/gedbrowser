package org.schoellerfamily.gedbrowser.api.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;

/**
 * @author Dick Schoeller
 */
public class ApiAttributeTest {
    /** */
    @Test
    public void testDefaultConstructorType() {
        final ApiAttribute o = new ApiAttribute();
        assertEquals("type mismatch", "", o.getType());
    }

    /** */
    @Test
    public void testDefaultConstructorString() {
        final ApiAttribute o = new ApiAttribute();
        assertEquals("string mismatch", "", o.getString());
    }

    /** */
    @Test
    public void testDefaultConstructorTail() {
        final ApiAttribute o = new ApiAttribute();
        assertNull("tail mismatch", o.getTail());
    }

    /** */
    @Test
    public void testConstructorType() {
        final ApiAttribute o = new ApiAttribute("string", "type", "tail");
        assertEquals("type mismatch", "type", o.getType());
    }

    /** */
    @Test
    public void testConstructorString() {
        final ApiAttribute o = new ApiAttribute("string", "type", "tail");
        assertEquals("string mismatch", "string", o.getString());
    }

    /** */
    @Test
    public void testConstructorTail() {
        final ApiAttribute o = new ApiAttribute("string", "type", "tail");
        assertEquals("tail mismatch", "tail", o.getTail());
    }

    /** */
    @Test
    public void testConstructorNullAttributes() {
        final ApiAttribute o = new ApiAttribute("string", "type", null, "tail");
        assertTrue("attributes mismatch", o.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testIsType() {
        final ApiAttribute o = new ApiAttribute("string", "type", "tail");
        assertTrue("isType mismatch", o.isType("type"));
    }

    /** */
    @Test
    public void testAccept() {
        final ApiAttribute o = new ApiAttribute("string", "type", "tail");
        final ApiTestVisitor visitor = new ApiTestVisitor();
        o.accept(visitor);
        assertEquals("Method mismatch", "attribute", visitor.getMethodCalled());
    }

}
