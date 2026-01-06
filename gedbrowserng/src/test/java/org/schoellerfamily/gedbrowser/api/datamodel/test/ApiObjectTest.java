package org.schoellerfamily.gedbrowser.api.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author Dick Schoeller
 */
public class ApiObjectTest {
    /** */
    @Test
    public void testDefaultConstructorType() {
        final ApiObject o = ApiObject.builder()
            .type("")
            .string("")
            .attributes(java.util.List.of())
            .build();
        assertEquals("", o.getType(), "type mismatch");
    }

    /** */
    @Test
    public void testDefaultConstructorString() {
        final ApiObject o = ApiObject.builder()
            .type("")
            .string("")
            .attributes(java.util.List.of())
            .build();
        assertEquals("", o.getString(), "string mismatch");
    }

    /** */
    @Test
    public void testDefaultConstructorAttributes() {
        final ApiObject o = ApiObject.builder()
            .type("")
            .string("")
            .attributes(java.util.List.of())
            .build();
        assertTrue(o.getAttributes().isEmpty(), "attributes mismatch");
    }

    /** */
    @Test
    public void testConstructorType() {
        final ApiObject o = ApiObject.builder()
            .type("type")
            .string("string")
            .attributes(java.util.List.of())
            .build();
        assertEquals("type", o.getType(), "type mismatch");
    }

    /** */
    @Test
    public void testConstructorString() {
        final ApiObject o = ApiObject.builder()
            .type("type")
            .string("string")
            .attributes(java.util.List.of())
            .build();
        assertEquals("string", o.getString(), "string mismatch");
    }

    /** */
    @Test
    public void testConstructorNoAttributes() {
        final ApiObject o = ApiObject.builder()
            .type("type")
            .string("string")
            .attributes(java.util.List.of())
            .build();
        assertTrue(o.getAttributes().isEmpty(), "attributes mismatch");
    }

    /** */
    @Test
    public void testConstructorNullAttributes() {
        final ApiObject o = ApiObject.builder()
            .type("type")
            .string("string")
            .attributes(java.util.List.of())
            .build();
        assertTrue(o.getAttributes().isEmpty(), "attributes mismatch");
    }

    /** */
    @Test
    public void testConstructorWithAttributes() {
        final List<ApiAttribute> attributes = List.of(ApiAttribute.builder()
            .type("attribute")
            .string("a string")
            .tail("")
            .attributes(java.util.List.of())
            .build());
        final ApiObject o = ApiObject.builder()
            .type("type")
            .string("string")
            .attributes(attributes)
            .build();
        assertEquals(1, o.getAttributes().size(), "attributes mismatch");
    }

    /** */
    @Test
    public void testIsType() {
        final ApiObject o = ApiObject.builder()
            .type("type")
            .string("string")
            .attributes(java.util.List.of())
            .build();
        assertTrue(o.isType("type"), "isType mismatch");
    }

    /** */
    @Test
    public void testAccept() {
        final ApiObject o = ApiObject.builder()
            .type("type")
            .string("string")
            .attributes(java.util.List.of())
            .build();
        final ApiTestVisitor visitor = new ApiTestVisitor();
        o.accept(visitor);
        assertEquals("base", visitor.getMethodCalled(), "Method mismatch");
    }

    /** */
    @Test
    public void testHashAndEquals() {
        EqualsVerifier.forClass(ApiObject.class).suppress(Warning.STRICT_INHERITANCE).verify();
    }
}
