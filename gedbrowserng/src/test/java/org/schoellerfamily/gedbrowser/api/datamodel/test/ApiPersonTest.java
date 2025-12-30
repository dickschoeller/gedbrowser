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
public class ApiPersonTest {
    /** */
    @Test
    public void testDefaultConstructorType() {
        final ApiPerson o = ApiPerson.builder().type("").string("").attributes(java.util.List.of()).surname("").indexName("").build();
        assertEquals("", o.getType(), "type mismatch");
    }

    /** */
    @Test
    public void testDefaultConstructorString() {
        final ApiPerson o = ApiPerson.builder().type("").string("").attributes(java.util.List.of()).surname("").indexName("").build();
        assertEquals("", o.getString(), "string mismatch");
    }

    /** */
    @Test
    public void testDefaultConstructorAttributes() {
        final ApiPerson o = ApiPerson.builder().type("").string("").attributes(java.util.List.of()).surname("").indexName("").build();
        assertTrue(o.getAttributes().isEmpty(), "attributes mismatch");
    }

    /** */
    @Test
    public void testConstructorType() {
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
    public void testConstructorString() {
        final ApiPerson o = createPerson();
        assertEquals("string", o.getString(), "string mismatch");
    }

    /** */
    @Test
    public void testConstructorIndexName() {
        final ApiPerson o = createPerson();
        assertEquals("index name", o.getIndexName(), "indexname mismatch");
    }

    /** */
    @Test
    public void testConstructorSurname() {
        final ApiPerson o = createPerson();
        assertEquals("surname", o.getSurname(), "surname mismatch");
    }

    /** */
    @Test
    public void testConstructorNoAttributes() {
        final ApiPerson o = createPerson();
        assertTrue(o.getAttributes().isEmpty(), "attributes mismatch");
    }

    /** */
    @Test
    public void testConstructorWithAttributes() {
        final ApiPerson o = ApiPerson.builder()
                .string("string")
                .attribute(ApiAttribute.builder().type("attribute").string("a string").tail("").attributes(java.util.List.of()).build())
                .surname("")
                .indexName("")
                .build();
        assertEquals(1, o.getAttributes().size(), "attributes mismatch");
    }

    /** */
    @Test
    public void testIsType() {
        final ApiPerson o = ApiPerson.builder()
        		.type("person")
                .string("string")
                .attribute(ApiAttribute.builder()
                    .type("attribute")
                    .string("a string")
                    .tail("")
                    .build())
                .surname("")
                .indexName("")
                .build();
        assertTrue(o.isType("person"), "type mismatch");
    }

    /** */
    @Test
    public void testAccept() {
        final ApiPerson o = createPerson();
        final ApiTestVisitor visitor = new ApiTestVisitor();
        o.accept(visitor);
        assertEquals("person", visitor.getMethodCalled(), "Method mismatch");
    }

    /** */
    @Test
    public void testImage() {
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
    public void testOneImage() {
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
    public void testNoImages() {
        final ApiPerson o = createPerson();
        assertEquals(0, o.getImages().size(), "Should contain 0 images");
    }

    /** */
    @Test
    public void testHashAndEquals() {
    	EqualsVerifier
    	    .forClass(ApiPerson.class)
			.suppress(Warning.STRICT_INHERITANCE)
    	    .verify();
    }

    /**
     * @return the new person
     */
    private ApiPerson createPerson() {
        return ApiPerson.builder()
                .type("type")
                .string("string")
                .indexName("index name")
                .surname("surname")
                .build();
    }

    /**
     * @return the new person
     */
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
