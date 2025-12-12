package org.schoellerfamily.gedbrowser.api.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
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
        assertEquals("", o.getType(), "type mismatch");
    }

    /** */
    @Test
    public void testDefaultConstructorString() {
        final ApiSubmission o = new ApiSubmission();
        assertEquals("", o.getString(), "string mismatch");
    }

    /** */
    @Test
    public void testDefaultConstructorAttributes() {
        final ApiSubmission o = new ApiSubmission();
        assertTrue(o.getAttributes().isEmpty(), "attributes mismatch");
    }

    /** */
    @Test
    public void testConstructorType() {
        final ApiSubmission o = new ApiSubmission("type", "string");
        assertEquals("type", o.getType(), "type mismatch");
    }

    /** */
    @Test
    public void testConstructorString() {
        final ApiSubmission o = new ApiSubmission("type", "string");
        assertEquals("string", o.getString(), "string mismatch");
    }

    /** */
    @Test
    public void testConstructorNoAttributes() {
        final ApiSubmission o = new ApiSubmission("type", "string");
        assertTrue(o.getAttributes().isEmpty(), "attributes empty mismatch");
    }

    /** */
    @Test
    public void testConstructorNullAttributes() {
        final ApiSubmission o = new ApiSubmission("type", "string", null);
        assertTrue(o.getAttributes().isEmpty(), "attributes empty mismatch");
    }

    /** */
    @Test
    public void testConstructorWithAttributes() {
        final List<ApiAttribute> attributes = List.of(new ApiAttribute("a string", "attribute", ""));
        final ApiSubmission o = new ApiSubmission("type", "string", attributes);
        assertEquals(1, o.getAttributes().size(), "attributes size mismatch");
    }

    /** */
    @Test
    public void testIsType() {
        final ApiSubmission o = new ApiSubmission("type", "string");
        assertTrue(o.isType("type"), "isType mismatch");
    }

    /** */
    @Test
    public void testAccept() {
        final ApiSubmission o = new ApiSubmission("type", "string");
        final ApiTestVisitor visitor = new ApiTestVisitor();
        o.accept(visitor);
        assertEquals("submission", visitor.getMethodCalled(), "Method mismatch");
    }

}