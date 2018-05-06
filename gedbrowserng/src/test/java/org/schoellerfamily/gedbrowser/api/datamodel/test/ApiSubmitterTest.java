package org.schoellerfamily.gedbrowser.api.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
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
        assertEquals("type mismatch", "", o.getType());
    }

    /** */
    @Test
    public void testDefaultConstructorString() {
        final ApiSubmitter o = new ApiSubmitter();
        assertEquals("string mismatch", "", o.getString());
    }

    /** */
    @Test
    public void testDefaultConstructorAttributes() {
        final ApiSubmitter o = new ApiSubmitter();
        assertTrue("attributes mismatch", o.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testConstructorType() {
        final ApiSubmitter o = basicSubmitter();
        assertEquals("type mismatch", "type", o.getType());
    }

    /** */
    @Test
    public void testConstructorString() {
        final ApiSubmitter o = basicSubmitter();
        assertEquals("string mismatch", "string", o.getString());
    }

    /** */
    @Test
    public void testConstructorNoAttributes() {
        final ApiSubmitter o = basicSubmitter();
        assertTrue("attributes empty mismatch", o.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testConstructorNullAttributes() {
        final ApiSubmitter o = new ApiSubmitter("type", "string", null, "? ?");
        assertTrue("attributes empty mismatch", o.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testConstructorWithAttributes() {
        final List<ApiAttribute> attributes = new ArrayList<>();
        attributes.add(new ApiAttribute("a string", "attribute", ""));
        final ApiSubmitter o =
                new ApiSubmitter("type", "string", attributes, "? ?");
        assertEquals("attributes size mismatch", 1, o.getAttributes().size());
    }

    /** */
    @Test
    public void testIsType() {
        final ApiSubmitter o = basicSubmitter();
        assertTrue("isType mismatch", o.isType("type"));
    }

    /** */
    @Test
    public void testAccept() {
        final ApiSubmitter o = basicSubmitter();
        final ApiTestVisitor visitor = new ApiTestVisitor();
        o.accept(visitor);
        assertEquals("Method mismatch", "submitter", visitor.getMethodCalled());
    }

    /** */
    @Test
    public void testHash() {
        final ApiSubmitter o = basicSubmitter();
        final int expected = 1906865237;
        assertEquals("Hash should be", expected, o.hashCode());
    }

    /** */
    @Test
    public void testEquals() {
        final ApiSubmitter o1 = basicSubmitter();
        final ApiSubmitter o2 = basicSubmitter();
        assertEquals("Objects should be equal", o1, o2);
    }

    /** */
    @Test
    public void testSame() {
        final ApiSubmitter o1 = basicSubmitter();
        assertEquals("Objects should be equal", o1, o1);
    }

    /** */
    @Test
    public void testNotEquals() {
        final ApiSubmitter o1 = basicSubmitter();
        final ApiSubmitter o2 = basicSubmitter();
        final ApiAttribute a = new ApiAttribute(
                "attribute", "Repository", "Needham Public Library");
        o2.getAttributes().add(a);
        assertNotEquals("Objects should be unequal", o1, o2);
    }

    /**
     * @return a submitter
     */
    private ApiSubmitter basicSubmitter() {
        return new ApiSubmitter("type", "string", "? ?");
    }
}
