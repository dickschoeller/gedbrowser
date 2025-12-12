package org.schoellerfamily.gedbrowser.api.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiNote;

/**
 * @author Dick Schoeller
 */
public class ApiAttributeTest {
    /** */
    @Test
    public void testDefaultConstructorType() {
        final ApiAttribute o = new ApiAttribute();
        assertEquals("", o.getType(), "type mismatch");
    }

    /** */
    @Test
    public void testDefaultConstructorString() {
        final ApiAttribute o = new ApiAttribute();
        assertEquals("", o.getString(), "string mismatch");
    }

    /** */
    @Test
    public void testDefaultConstructorTail() {
        final ApiAttribute o = new ApiAttribute();
        assertNull(o.getTail(), "tail mismatch");
    }

    /** */
    @Test
    public void testConstructorType() {
        final ApiAttribute o = new ApiAttribute("type", "string", "tail");
        assertEquals("type", o.getType(), "type mismatch");
    }

    /** */
    @Test
    public void testConstructorString() {
        final ApiAttribute o = new ApiAttribute("type", "string", "tail");
        assertEquals("string", o.getString(), "string mismatch");
    }

    /** */
    @Test
    public void testConstructorTail() {
        final ApiAttribute o = new ApiAttribute("type", "string", "tail");
        assertEquals("tail", o.getTail(), "tail mismatch");
    }

    /** */
    @Test
    public void testConstructorNullAttributes() {
        final ApiAttribute o = new ApiAttribute("string", "type", null, "tail");
        assertTrue(o.getAttributes().isEmpty(), "attributes mismatch");
    }

    /** */
    @Test
    public void testIsType() {
        final ApiAttribute o = new ApiAttribute("type", "string", "tail");
        assertTrue(o.isType("type"), "isType mismatch");
    }

    /** */
    @Test
    public void testAccept() {
        final ApiAttribute o = new ApiAttribute("type", "string", "tail");
        final ApiTestVisitor visitor = new ApiTestVisitor();
        o.accept(visitor);
        assertEquals("attribute", visitor.getMethodCalled(), "Method mismatch");
    }

    /** */
    @Test
    public void testEqualsSame() {
        final ApiAttribute expected =
                new ApiAttribute("type", "string", "tail");
        assertEquals(expected, expected, "Should match");
    }

    /** */
    @Test
    public void testEqualsSimple() {
        final ApiAttribute expected =
                new ApiAttribute("type", "string", "tail");
        final ApiAttribute actual = new ApiAttribute("type", "string", "tail");
        assertEquals(expected, actual, "Should match");
    }

    /** */
    @Test
    public void testNotEqualsClass() {
        final ApiAttribute expected =
                new ApiAttribute("type", "string", "tail");
        final ApiNote actual = new ApiNote("type", "string", "tail");
        assertNotEquals(expected, actual, "Should not match");
    }

    /** */
    @Test
    public void testEqualsNullTail() {
        final ApiAttribute expected =
                new ApiAttribute("type", "string", null);
        final ApiAttribute actual = new ApiAttribute("type", "string", null);
        assertEquals(expected, actual, "Should match");
    }

    /** */
    @Test
    public void testNotEqualsNullTail() {
        final ApiAttribute expected =
                new ApiAttribute("type", "string", null);
        final ApiAttribute actual = new ApiAttribute("type", "string", "tail");
        assertNotEquals(expected, actual, "Should not match");
    }

    /** */
    @Test
    public void testNotEqualsTailNull() {
        final ApiAttribute expected =
                new ApiAttribute("type", "string", "tail");
        final ApiAttribute actual = new ApiAttribute("type", "string", null);
        assertNotEquals(expected, actual, "Should not match");
    }
}