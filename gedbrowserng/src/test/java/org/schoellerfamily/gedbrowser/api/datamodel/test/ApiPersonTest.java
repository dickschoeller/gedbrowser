package org.schoellerfamily.gedbrowser.api.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author Dick Schoeller
 */
public final class ApiPersonTest {
    /** */
    @Test
    void testDefaultConstructorType() {
        final ApiPerson o = ApiPerson.builder()
            .type("")
            .string("")
            .attributes(java.util.List.of())
            .surname("")
            .indexName("")
            .build();
        assertEquals("", o.getType(), "type mismatch");
    }

    /** */
    @Test
    void testDefaultConstructorString() {
        final ApiPerson o = ApiPerson.builder()
            .type("")
            .string("")
            .attributes(java.util.List.of())
            .surname("")
            .indexName("")
            .build();
        assertEquals("", o.getString(), "string mismatch");
    }

    /** */
    @Test
    void testDefaultConstructorAttributes() {
        final ApiPerson o = ApiPerson.builder()
            .type("")
            .string("")
            .attributes(java.util.List.of())
            .surname("")
            .indexName("")
            .build();
        assertTrue(o.getAttributes().isEmpty(), "attributes mismatch");
    }

    /** */
    @Test
    void testConstructorType() {
        final ApiPerson o = ApiPerson.builder()
            .type("type")
            .string("string")
            .indexName("index name")
            .surname("surname")
            .attributes(java.util.List.of())
            .build();
        assertEquals("type", o.getType(), "type mismatch");
    }

    /** */
    @Test
    void testConstructorString() {
        final ApiPerson o = createPerson();
        assertEquals("string", o.getString(), "string mismatch");
    }

    /** */
    @Test
    void testConstructorIndexName() {
        final ApiPerson o = createPerson();
        assertEquals("index name", o.getIndexName(), "indexname mismatch");
    }

    /** */
    @Test
    void testConstructorSurname() {
        final ApiPerson o = createPerson();
        assertEquals("surname", o.getSurname(), "surname mismatch");
    }

    /** */
    @Test
    void testConstructorNoAttributes() {
        final ApiPerson o = createPerson();
        assertTrue(o.getAttributes().isEmpty(), "attributes mismatch");
    }

    /** */
    @Test
    void testConstructorWithAttributes() {
        final ApiPerson o = ApiPerson.builder()
            .string("string")
            .attribute(ApiAttribute.builder()
                .type("attribute")
                .string("a string")
                .tail("")
                .attributes(java.util.List.of())
                .build())
            .surname("")
            .indexName("")
            .build();
        assertEquals(1, o.getAttributes().size(), "attributes mismatch");
    }

    /** */
    @Test
    void testIsType() {
        final ApiPerson o = ApiPerson.builder()
            .type("person")
            .string("string")
            .attribute(ApiAttribute.builder().type("attribute").string("a string").tail("").build())
            .surname("")
            .indexName("")
            .build();
        assertTrue(o.isType("person"), "type mismatch");
    }

    /** */
    @Test
    void testAccept() {
        final ApiPerson o = createPerson();
        final ApiTestVisitor visitor = new ApiTestVisitor();
        o.accept(visitor);
        assertEquals("person", visitor.getMethodCalled(), "Method mismatch");
    }

    /** */
    @Test
    void testImage() {
        final ApiAttribute multimedia = ApiAttribute.builder()
            .type("multimedia")
            .string("Multimedia")
            .attribute((ApiAttribute) ApiAttribute.builder()
                .type("attribute")
                .string("File")
                .tail("foo.jpg")
                .build())
            .build();
        final ApiPerson o = createPerson(multimedia);
        assertTrue(o.getImages().contains(multimedia), "Should contain multimedia");
    }

    /** */
    @Test
    void testOneImage() {
        final ApiAttribute multimedia = ApiAttribute.builder()
            .type("multimedia")
            .string("Multimedia")
            .attribute((ApiAttribute) ApiAttribute.builder()
                .type("attribute")
                .string("File")
                .tail("foo.jpg")
                .build())
            .build();
        final ApiPerson o = createPerson(multimedia);
        assertEquals(1, o.getImages().size(), "Should contain 1 image");
    }

    /** */
    @Test
    void testNoImages() {
        final ApiPerson o = createPerson();
        assertEquals(0, o.getImages().size(), "Should contain 0 images");
    }

    @Test
    void testHashAndEquals() {
        EqualsVerifier.forClass(ApiPerson.class).suppress(Warning.STRICT_INHERITANCE).verify();
    }

    private ApiPerson createPerson() {
        return ApiPerson.builder()
            .type("type")
            .string("string")
            .indexName("index name")
            .surname("surname")
            .build();
    }

    private ApiPerson createPerson(final ApiAttribute additional) {
        return ApiPerson.builder()
            .type("type")
            .string("string")
            .indexName("index name")
            .surname("surname")
            .attribute(additional)
            .build();
    }
}
