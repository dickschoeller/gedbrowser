package org.schoellerfamily.gedbrowser.api.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmission;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author Dick Schoeller
 */
public class ApiSubmissionTest {
    /** */
    @Test
    void testDefaultConstructorType() {
        final ApiSubmission o = ApiSubmission.builder()
            .type("")
            .string("")
            .attributes(java.util.List.of())
            .build();
        assertEquals("", o.getType(), "type mismatch");
    }

    /** */
    @Test
    void testDefaultConstructorString() {
        final ApiSubmission o = ApiSubmission.builder()
            .type("")
            .string("")
            .attributes(java.util.List.of())
            .build();
        assertEquals("", o.getString(), "string mismatch");
    }

    /** */
    @Test
    void testDefaultConstructorAttributes() {
        final ApiSubmission o = ApiSubmission.builder()
            .type("")
            .string("")
            .attributes(java.util.List.of())
            .build();
        assertTrue(o.getAttributes().isEmpty(), "attributes mismatch");
    }

    /** */
    @Test
    void testConstructorType() {
        final ApiSubmission o = ApiSubmission.builder()
            .type("type")
            .string("string")
            .attributes(java.util.List.of())
            .build();
        assertEquals("type", o.getType(), "type mismatch");
    }

    /** */
    @Test
    void testConstructorString() {
        final ApiSubmission o = ApiSubmission.builder()
            .type("type")
            .string("string")
            .attributes(java.util.List.of())
            .build();
        assertEquals("string", o.getString(), "string mismatch");
    }

    /** */
    @Test
    void testConstructorNoAttributes() {
        final ApiSubmission o = ApiSubmission.builder()
            .type("type")
            .string("string")
            .attributes(java.util.List.of())
            .build();
        assertTrue(o.getAttributes().isEmpty(), "attributes empty mismatch");
    }

    /** */
    @Test
    void testConstructorNullAttributes() {
        final ApiSubmission o = ApiSubmission.builder()
            .type("type")
            .string("string")
            .attributes(java.util.List.of())
            .build();
        assertTrue(o.getAttributes().isEmpty(), "attributes empty mismatch");
    }

    /** */
    @Test
    void testConstructorWithAttributes() {
        final List<ApiAttribute> attributes = List.of(ApiAttribute.builder()
            .type("attribute")
            .string("a string")
            .tail("")
            .attributes(java.util.List.of())
            .build());
        final ApiSubmission o = ApiSubmission.builder()
            .type("type")
            .string("string")
            .attributes(attributes)
            .build();
        assertEquals(1, o.getAttributes().size(), "attributes size mismatch");
    }

    /** */
    @Test
    void testIsType() {
        final ApiSubmission o = ApiSubmission.builder()
            .type("type")
            .string("string")
            .attributes(java.util.List.of())
            .build();
        assertTrue(o.isType("type"), "isType mismatch");
    }

    /** */
    @Test
    void testAccept() {
        final ApiSubmission o = ApiSubmission.builder()
            .type("type")
            .string("string")
            .attributes(java.util.List.of())
            .build();
        final ApiTestVisitor visitor = new ApiTestVisitor();
        o.accept(visitor);
        assertEquals("submission", visitor.getMethodCalled(), "Method mismatch");
    }

    /** */
    @Test
    void testHashAndEquals() {
        EqualsVerifier.forClass(ApiSubmission.class).suppress(Warning.STRICT_INHERITANCE).verify();
    }
}
