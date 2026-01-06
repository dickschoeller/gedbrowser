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
public class ApiSubmitterTest {
    /** */
    @Test
    public void testDefaultConstructorType() {
        final ApiSubmitter o = ApiSubmitter.builder().type("").string("").name("").build();
        assertEquals("", o.getType(), "type mismatch");
    }

    /** */
    @Test
    public void testDefaultConstructorString() {
        final ApiSubmitter o = ApiSubmitter.builder().type("").string("").name("").build();
        assertEquals("", o.getString(), "string mismatch");
    }

    /** */
    @Test
    public void testDefaultConstructorAttributes() {
        final ApiSubmitter o = ApiSubmitter.builder().type("").string("").name("").build();
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
        final ApiSubmitter o = ApiSubmitter.builder()
            .type("type")
            .string("string")
            .name("? ?")
            .build();
        assertTrue(o.getAttributes().isEmpty(), "attributes empty mismatch");
    }

    /** */
    @Test
    public void testConstructorWithAttributes() {
        final ApiSubmitter o = ApiSubmitter.builder()
            .type("type")
            .string("string")
            .attribute(ApiAttribute.builder().type("attribute").string("a string").tail("").build())
            .name("? ?")
            .build();
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
    public void testEqualsAndHash() {
        EqualsVerifier.forClass(ApiSubmitter.class)
            .withNonnullFields("type", "string", "attributes", "name")
            .suppress(Warning.STRICT_INHERITANCE)
            .verify();
    }

    /**
     * @return a submitter
     */
    private ApiSubmitter basicSubmitter() {
        return ApiSubmitter.builder()
            .type("type")
            .string("string")
            .name("? ?")
            .attributes(java.util.List.of())
            .build();
    }
}
