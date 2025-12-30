package org.schoellerfamily.gedbrowser.api.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author Dick Schoeller
 */
public class ApiAttributeTest {
    /** */
    @Test
    public void testDefaultConstructorType() {
        final ApiAttribute o = ApiAttribute.builder()
                .type("")
                .string("")
                .build();
        assertEquals("", o.getType(), "type mismatch");
    }

    /** */
    @Test
    public void testDefaultConstructorString() {
        final ApiAttribute o = ApiAttribute.builder()
                .type("")
                .string("")
                .build();
        assertEquals("", o.getString(), "string mismatch");
    }

    /** */
    @Test
    public void testDefaultConstructorTail() {
        final ApiAttribute o = ApiAttribute.builder()
                .type("")
                .string("")
                .build();
        assertEquals("", o.getTail(), "tail mismatch");
    }

    /** */
    @Test
    public void testConstructorType() {
        final ApiAttribute o = ApiAttribute.builder()
                .type("type")
                .string("string")
                .tail("tail")
                .build();
        assertEquals("type", o.getType(), "type mismatch");
    }

    /** */
    @Test
    public void testConstructorString() {
        final ApiAttribute o = ApiAttribute.builder()
                .type("type")
                .string("string")
                .tail("tail")
                .build();
        assertEquals("string", o.getString(), "string mismatch");
    }

    /** */
    @Test
    public void testConstructorTail() {
        final ApiAttribute o = ApiAttribute.builder()
                .type("type")
                .string("string")
                .tail("tail")
                .build();
        assertEquals("tail", o.getTail(), "tail mismatch");
    }

    /** */
    @Test
    public void testConstructorNullAttributes() {
        final ApiAttribute o = ApiAttribute.builder()
                .type("type")
                .string("string")
                .tail("tail")
                .build();
        assertTrue(o.getAttributes().isEmpty(), "attributes mismatch");
    }

    /** */
    @Test
    public void testIsType() {
        final ApiAttribute o = ApiAttribute.builder()
                .type("type")
                .string("string")
                .tail("tail")
                .build();
        assertTrue(o.isType("type"), "isType mismatch");
    }

    /** */
    @Test
    public void testAccept() {
        final ApiAttribute o = ApiAttribute.builder()
                .type("type")
                .string("string")
                .tail("tail")
                .build();
        final ApiTestVisitor visitor = new ApiTestVisitor();
        o.accept(visitor);
        assertEquals("attribute", visitor.getMethodCalled(), "Method mismatch");
    }

    /** */
    @Test
    public void testHashAndEquals() {
    	EqualsVerifier
    	    .forClass(ApiAttribute.class)
			.suppress(Warning.STRICT_INHERITANCE)
    	    .verify();
    }
}