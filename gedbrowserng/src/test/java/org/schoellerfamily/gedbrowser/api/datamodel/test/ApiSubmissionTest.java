package org.schoellerfamily.gedbrowser.api.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmission;

/**
 * @author Dick Schoeller
 */
public class ApiSubmissionTest {
    /** */
    @Test
    public void testDefaultConstructorType() {
        final ApiSubmission o = new ApiSubmission();
        assertEquals("type mismatch", "", o.getType());
    }

    /** */
    @Test
    public void testDefaultConstructorString() {
        final ApiSubmission o = new ApiSubmission();
        assertEquals("string mismatch", "", o.getString());
    }

    /** */
    @Test
    public void testDefaultConstructorAttributes() {
        final ApiSubmission o = new ApiSubmission();
        assertTrue("attributes mismatch", o.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testConstructorType() {
        final ApiSubmission o = new ApiSubmission("type", "string");
        assertEquals("type mismatch", "type", o.getType());
    }

    /** */
    @Test
    public void testConstructorString() {
        final ApiSubmission o = new ApiSubmission("type", "string");
        assertEquals("string mismatch", "string", o.getString());
    }

    /** */
    @Test
    public void testConstructorNoAttributes() {
        final ApiSubmission o = new ApiSubmission("type", "string");
        assertTrue("attributes empty mismatch", o.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testConstructorNullAttributes() {
        final ApiSubmission o = new ApiSubmission("type", "string", null);
        assertTrue("attributes empty mismatch", o.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testConstructorWithAttributes() {
        final List<ApiAttribute> attributes = new ArrayList<>();
        attributes.add(new ApiAttribute("a string", "attribute", ""));
        final ApiSubmission o = new ApiSubmission("type", "string", attributes);
        assertEquals("attributes size mismatch", 1, o.getAttributes().size());
    }

    /** */
    @Test
    public void testIsType() {
        final ApiSubmission o = new ApiSubmission("type", "string");
        assertTrue("isType mismatch", o.isType("type"));
    }

    /** */
    @Test
    public void testAccept() {
        final ApiSubmission o = new ApiSubmission("type", "string");
        final ApiTestVisitor visitor = new ApiTestVisitor();
        o.accept(visitor);
        assertEquals("Method mismatch",
                "submission", visitor.getMethodCalled());
    }

}
