package org.schoellerfamily.gedbrowser.api.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
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
        final ApiAttribute o = new ApiAttribute("type", "string", "tail");
        assertEquals("type mismatch", "type", o.getType());
    }

    /** */
    @Test
    public void testConstructorString() {
        final ApiAttribute o = new ApiAttribute("type", "string", "tail");
        assertEquals("string mismatch", "string", o.getString());
    }

    /** */
    @Test
    public void testConstructorTail() {
        final ApiAttribute o = new ApiAttribute("type", "string", "tail");
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
        final ApiAttribute o = new ApiAttribute("type", "string", "tail");
        assertTrue("isType mismatch", o.isType("type"));
    }

    /** */
    @Test
    public void testAccept() {
        final ApiAttribute o = new ApiAttribute("type", "string", "tail");
        final ApiTestVisitor visitor = new ApiTestVisitor();
        o.accept(visitor);
        assertEquals("Method mismatch", "attribute", visitor.getMethodCalled());
    }

    /** */
    @Test
    public void testEqualsSame() {
        final ApiAttribute expected =
                new ApiAttribute("type", "string", "tail");
        assertEquals("Should match", expected, expected);
    }

    /** */
    @Test
    public void testEqualsSimple() {
        final ApiAttribute expected =
                new ApiAttribute("type", "string", "tail");
        final ApiAttribute actual = new ApiAttribute("type", "string", "tail");
        assertEquals("Should match", expected, actual);
    }

    /** */
    @Test
    public void testNotEqualsClass() {
        final ApiAttribute expected =
                new ApiAttribute("type", "string", "tail");
        final ApiNote actual = new ApiNote("type", "string", "tail");
        assertNotEquals("Should not match", expected, actual);
    }

    /** */
    @Test
    public void testEqualsNullTail() {
        final ApiAttribute expected =
                new ApiAttribute("type", "string", null);
        final ApiAttribute actual = new ApiAttribute("type", "string", null);
        assertEquals("Should match", expected, actual);
    }

    /** */
    @Test
    public void testNotEqualsNullTail() {
        final ApiAttribute expected =
                new ApiAttribute("type", "string", null);
        final ApiAttribute actual = new ApiAttribute("type", "string", "tail");
        assertNotEquals("Should not match", expected, actual);
    }

    /** */
    @Test
    public void testNotEqualsTailNull() {
        final ApiAttribute expected =
                new ApiAttribute("type", "string", "tail");
        final ApiAttribute actual = new ApiAttribute("type", "string", null);
        assertNotEquals("Should not match", expected, actual);
    }
}
