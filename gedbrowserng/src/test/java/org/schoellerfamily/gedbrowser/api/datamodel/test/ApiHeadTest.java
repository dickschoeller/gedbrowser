package org.schoellerfamily.gedbrowser.api.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiHead;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author Dick Schoeller
 */
public class ApiHeadTest {
    /** */
    @Test
    public void testDefaultConstructorType() {
        final ApiHead o = ApiHead.builder().type("").string("").attributes(java.util.List.of()).build();
        assertEquals("", o.getType(), "type mismatch");
    }

    /** */
    @Test
    public void testDefaultConstructorString() {
        final ApiHead o = ApiHead.builder().type("").string("").attributes(java.util.List.of()).build();
        assertEquals("", o.getString(), "string mismatch");
    }

    /** */
    @Test
    public void testDefaultConstructorAttributes() {
        final ApiHead o = ApiHead.builder().type("").string("").attributes(java.util.List.of()).build();
        assertTrue(o.getAttributes().isEmpty(), "attributes mismatch");
    }

    /** */
    @Test
    public void testConstructorType() {
        final ApiHead o = ApiHead.builder().type("type").string("string").build();
        assertEquals("type", o.getType(), "type mismatch");
    }

    /** */
    @Test
    public void testConstructorString() {
        final ApiHead o = ApiHead.builder().type("type").string("string").build();
        assertEquals("string", o.getString(), "string mismatch");
    }

    /** */
    @Test
    public void testConstructorNoAttributes() {
        final ApiHead o = ApiHead.builder().type("type").string("string").build();
        assertTrue(o.getAttributes().isEmpty(), "attributes mismatch");
    }

    /** */
    @Test
    public void testConstructorNullAttributes() {
        final ApiHead o = ApiHead.builder().type("type").string("string").attributes(java.util.List.of()).build();
        assertTrue(o.getAttributes().isEmpty(), "attributes mismatch");
    }

    /** */
    @Test
    public void testConstructorWithAttributes() {
        final List<ApiAttribute> attributes = List.of(
            ApiAttribute.builder().type("attribute").string("a string").tail("").attributes(java.util.List.of()).build());
        final ApiHead o = ApiHead.builder().type("type").string("string").attributes(attributes).build();
        assertEquals(1, o.getAttributes().size(), "attributes mismatch");
    }

    /** */
    @Test
    public void testIsType() {
        final ApiHead o = ApiHead.builder().type("type").string("string").build();
        assertTrue(o.isType("type"), "isType mismatch");
    }

    /** */
    @Test
    public void testAccept() {
        final ApiHead o = ApiHead.builder().type("type").string("string").build();
        final ApiTestVisitor visitor = new ApiTestVisitor();
        o.accept(visitor);
        assertEquals("head", visitor.getMethodCalled(), "Method mismatch");
    }

    /** */
    @Test
    public void testHashAndEquals() {
    	EqualsVerifier
    	    .forClass(ApiHead.class)
			.suppress(Warning.STRICT_INHERITANCE)
    	    .verify();
    }
}