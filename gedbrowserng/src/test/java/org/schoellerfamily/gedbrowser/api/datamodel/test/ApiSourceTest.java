package org.schoellerfamily.gedbrowser.api.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSource;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author Dick Schoeller
 */
public class ApiSourceTest {
    /** */
    @Test
    public void testDefaultConstructorType() {
        final ApiSource o = ApiSource.builder().type("").string("").title("").build();
        assertEquals("", o.getType(), "type mismatch");
    }

    /** */
    @Test
    public void testDefaultConstructorString() {
        final ApiSource o = ApiSource.builder().type("").string("").title("").build();
        assertEquals("", o.getString(), "string mismatch");
    }

    /** */
    @Test
    public void testDefaultConstructorAttributes() {
        final ApiSource o = ApiSource.builder().type("").string("").title("").build();
        assertTrue(o.getAttributes().isEmpty(), "attributes mismatch");
    }

    /** */
    @Test
    public void testConstructorType() {
        final ApiSource o = ApiSource.builder()
            .type("type")
            .string("string")
            .title("Unknown")
            .build();
        assertEquals("type", o.getType(), "type mismatch");
    }

    /** */
    @Test
    public void testConstructorString() {
        final ApiSource o = ApiSource.builder()
            .type("type")
            .string("string")
            .title("Unknown")
            .build();
        assertEquals("string", o.getString(), "string mismatch");
    }

    /** */
    @Test
    public void testConstructorNoAttributes() {
        final ApiSource o = ApiSource.builder()
            .type("type")
            .string("string")
            .title("Unknown")
            .build();
        assertTrue(o.getAttributes().isEmpty(), "attributes empty mismatch");
    }

    /** */
    @Test
    public void testConstructorNullAttributes() {
        final ApiSource o = ApiSource.builder().type("type").string("string").title("").build();
        assertTrue(o.getAttributes().isEmpty(), "attributes empty mismatch");
    }

    /** */
    @Test
    public void testConstructorWithAttributes() {
        final ApiSource o = ApiSource.builder()
            .type("type")
            .string("string")
            .attributes(List
                .of(ApiAttribute.builder().type("attribute").string("a string").tail("").build()))
            .title("Unknown")
            .build();
        assertEquals(1, o.getAttributes().size(), "attributes size mismatch");
    }

    /** */
    @Test
    public void testIsType() {
        final ApiSource o = ApiSource.builder()
            .type("type")
            .string("string")
            .title("Unknown")
            .build();
        assertTrue(o.isType("type"), "isType mismatch");
    }

    /** */
    @Test
    public void testAccept() {
        final ApiSource o = ApiSource.builder()
            .type("type")
            .string("string")
            .title("Unknown")
            .build();
        final ApiTestVisitor visitor = new ApiTestVisitor();
        o.accept(visitor);
        assertEquals("source", visitor.getMethodCalled(), "Method mismatch");
    }

    /** */
    @Test
    public void testImage() {
        final ApiAttribute file = ApiAttribute.builder()
            .type("attribute")
            .string("File")
            .tail("foo.jpg")
            .attributes(List.of())
            .build();
        final ApiAttribute multimedia = ApiAttribute.builder()
            .type("multimedia")
            .string("Multimedia")
            .attributes(List.of(file))
            .build();
        final ApiSource o = ApiSource.builder()
            .type("type")
            .string("string")
            .title("title")
            // GET SPLIT ATTRIBUTES WORKING IN BUILDER
            .attributes(List.of(multimedia))
            .image(multimedia)
            .build();
        assertTrue(o.getImages().contains(multimedia), "Should contain multimedia");
    }

    /** */
    @Test
    public void testOneImage() {
        final ApiSource o = sourceWithMultimedia();
        assertEquals(1, o.getImages().size(), "Should contain 1 image");
    }

    /** */
    @Test
    public void testNoImages() {
        final ApiSource o = ApiSource.builder()
            .type("type")
            .string("string")
            .title("title")
            .attributes(List.of())
            .build();
        assertEquals(0, o.getImages().size(), "Should contain 0 images");
    }

    /** */
    @Test
    public void testHashAndEquals() {
        EqualsVerifier.forClass(ApiSource.class).suppress(Warning.STRICT_INHERITANCE).verify();
    }

    /**
     * @return a new source
     */
    private ApiSource sourceWithMultimedia() {
        final ApiAttribute multimedia = ApiAttribute.builder()
            .type("multimedia")
            .string("Multimedia")
            .attribute(
                ApiAttribute.builder().type("attribute").string("File").tail("foo.jpg").build())
            .build();
        return ApiSource.builder()
            .type("type")
            .string("string")
            .title("title")
            // GET SPLIT ATTRIBUTES WORKING IN BUILDER
            .attribute(multimedia)
            .image(multimedia)
            .build();
    }
}
