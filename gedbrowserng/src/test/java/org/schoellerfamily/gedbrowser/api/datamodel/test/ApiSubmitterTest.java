package org.schoellerfamily.gedbrowser.api.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmitter;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author Dick Schoeller
 */
class ApiSubmitterTest {
    @Test
    void testDefaultConstructorType() {
        final ApiSubmitter o = ApiSubmitter.builder().type("").string("").name("").build();
        assertEquals("", o.getType(), "type mismatch");
    }

    @Test
    void testDefaultConstructorString() {
        final ApiSubmitter o = ApiSubmitter.builder().type("").string("").name("").build();
        assertEquals("", o.getString(), "string mismatch");
    }

    @Test
    void testDefaultConstructorAttributes() {
        final ApiSubmitter o = ApiSubmitter.builder().type("").string("").name("").build();
        assertTrue(o.getAttributes().isEmpty(), "attributes mismatch");
    }

    @Test
    void testConstructorType() {
        final ApiSubmitter o = basicSubmitter();
        assertEquals("type", o.getType(), "type mismatch");
    }

    @Test
    void testConstructorString() {
        final ApiSubmitter o = basicSubmitter();
        assertEquals("string", o.getString(), "string mismatch");
    }

    @Test
    void testConstructorNoAttributes() {
        final ApiSubmitter o = basicSubmitter();
        assertTrue(o.getAttributes().isEmpty(), "attributes empty mismatch");
    }

    @Test
    void testConstructorNullAttributes() {
        final ApiSubmitter o = ApiSubmitter.builder()
            .type("type")
            .string("string")
            .name("? ?")
            .build();
        assertTrue(o.getAttributes().isEmpty(), "attributes empty mismatch");
    }

    @Test
    void testConstructorWithAttributes() {
        final ApiSubmitter o = ApiSubmitter.builder()
            .type("type")
            .string("string")
            .attribute(ApiAttribute.builder().type("attribute").string("a string").tail("").build())
            .name("? ?")
            .build();
        assertEquals(1, o.getAttributes().size(), "attributes size mismatch");
    }

    @Test
    void testIsType() {
        final ApiSubmitter o = basicSubmitter();
        assertTrue(o.isType("type"), "isType mismatch");
    }

    @Test
    void testAccept() {
        final ApiSubmitter o = basicSubmitter();
        final ApiTestVisitor visitor = new ApiTestVisitor();
        o.accept(visitor);
        assertEquals("submitter", visitor.getMethodCalled(), "Method mismatch");
    }

    @Test
    void testEqualsAndHash() {
        EqualsVerifier.forClass(ApiSubmitter.class)
            .withNonnullFields("type", "string", "attributes", "name")
            .suppress(Warning.STRICT_INHERITANCE)
            .verify();
    }

    private ApiSubmitter basicSubmitter() {
        return ApiSubmitter.builder()
            .type("type")
            .string("string")
            .name("? ?")
            .attributes(java.util.List.of())
            .build();
    }
}
