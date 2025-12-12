package org.schoellerfamily.gedbrowser.api.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmitter;

/**
 * @author Dick Schoeller
 */
public class ApiSubmitterTest {
    /** */
    @Test
    public void testDefaultConstructorType() {
        final ApiSubmitter o = new ApiSubmitter();
        assertEquals("", o.getType(), "type mismatch");
    }

    /** */
    @Test
    public void testDefaultConstructorString() {
        final ApiSubmitter o = new ApiSubmitter();
        assertEquals("", o.getString(), "string mismatch");
    }

    /** */
    @Test
    public void testDefaultConstructorAttributes() {
        final ApiSubmitter o = new ApiSubmitter();
        assertTrue(o.getAttributes().isEmpty(), "attributes mismatch");
    }

    /** */
    @Test
    public void testConstructorType() {
        final ApiSubmitter o = basicSubmitter();
        assertEquals("type", o.getType(), "type mismatch");
    }

    /** */
    @Test
    public void testConstructorString() {
        final ApiSubmitter o = basicSubmitter();
        assertEquals("string", o.getString(), "string mismatch");
    }

    /** */
    @Test
    public void testConstructorNoAttributes() {
        final ApiSubmitter o = basicSubmitter();
        assertTrue(o.getAttributes().isEmpty(), "attributes empty mismatch");
    }

    /** */
    @Test
    public void testConstructorNullAttributes() {
        final ApiSubmitter o = new ApiSubmitter("type", "string", null, "? ?");
        assertTrue(o.getAttributes().isEmpty(), "attributes empty mismatch");
    }

    /** */
    @Test
    public void testConstructorWithAttributes() {
        final List<ApiAttribute> attributes = List.of(
                new ApiAttribute("a string", "attribute", "")
        );
        final ApiSubmitter o = new ApiSubmitter("type", "string", attributes, "? ?");
        assertEquals(1, o.getAttributes().size(), "attributes size mismatch");
    }

    /** */
    @Test
    public void testIsType() {
        final ApiSubmitter o = basicSubmitter();
        assertTrue(o.isType("type"), "isType mismatch");
    }

    /** */
    @Test
    public void testAccept() {
        final ApiSubmitter o = basicSubmitter();
        final ApiTestVisitor visitor = new ApiTestVisitor();
        o.accept(visitor);
        assertEquals("submitter", visitor.getMethodCalled(), "Method mismatch");
    }

    /** */
    @Test
    public void testHash() {
        final ApiSubmitter o = basicSubmitter();
        final int expected = 1906865237;
        assertEquals(expected, o.hashCode(), "Hash should be");
    }

    /** */
    @Test
    public void testEquals() {
        final ApiSubmitter o1 = basicSubmitter();
        final ApiSubmitter o2 = basicSubmitter();
        assertEquals(o1, o2, "Objects should be equal");
    }

    /** */
    @Test
    public void testSame() {
        final ApiSubmitter o1 = basicSubmitter();
        assertEquals(o1, o1, "Objects should be equal");
    }

    /** */
    @Test
    public void testNotEquals() {
        final ApiSubmitter o1 = basicSubmitter();
        final ApiSubmitter o2 = basicSubmitter();
        final ApiAttribute a = new ApiAttribute(
                "attribute", "Repository", "Needham Public Library");
        o2.getAttributes().add(a);
        assertNotEquals(o1, o2, "Objects should be unequal");
    }

    /**
     * @return a submitter
     */
    private ApiSubmitter basicSubmitter() {
        return new ApiSubmitter("type", "string", "? ?");
    }
}