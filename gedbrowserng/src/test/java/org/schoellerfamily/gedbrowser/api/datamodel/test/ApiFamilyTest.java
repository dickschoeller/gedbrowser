package org.schoellerfamily.gedbrowser.api.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author Dick Schoeller
 */
final class ApiFamilyTest {
    @Test
    void testDefaultConstructorType() {
        final ApiFamily o = ApiFamily.builder().type("").string("").build();
        assertEquals("", o.getType(), "type mismatch");
    }

    @Test
    void testDefaultConstructorString() {
        final ApiFamily o = ApiFamily.builder().type("").string("").build();
        assertEquals("", o.getString(), "string mismatch");
    }

    @Test
    void testDefaultConstructorAttributes() {
        final ApiFamily o = ApiFamily.builder().type("").string("").build();
        assertTrue(o.getAttributes().isEmpty(), "attributes mismatch");
    }

    @Test
    void testConstructorType() {
        final ApiFamily o = ApiFamily.builder().type("type").string("string").build();
        assertEquals("type", o.getType(), "type mismatch");
    }

    @Test
    void testConstructorString() {
        final ApiFamily o = ApiFamily.builder().type("type").string("string").build();
        assertEquals("string", o.getString(), "string mismatch");
    }

    @Test
    void testConstructorNoAttributes() {
        final ApiFamily o = ApiFamily.builder().type("type").string("string").build();
        assertTrue(o.getAttributes().isEmpty(), "attributes empty mismatch");
    }

    @Test
    void testConstructorWithAttributes() {
        final ApiFamily o = ApiFamily.builder()
            .type("type")
            .string("string")
            .attribute(ApiAttribute.builder().type("attribute").string("a string").tail("").build())
            .build();
        assertEquals(1, o.getAttributes().size(), "attributes size mismatch");
    }

    @Test
    void testIsType() {
        final ApiFamily o = ApiFamily.builder().type("type").string("string").build();
        assertTrue(o.isType("type"), "isType mismatch");
    }

    @Test
    void testAccept() {
        final ApiFamily o = ApiFamily.builder().type("type").string("string").build();
        final ApiTestVisitor visitor = new ApiTestVisitor();
        o.accept(visitor);
        assertEquals("family", visitor.getMethodCalled(), "Method mismatch");
    }

    @Test
    void testImage() {
        final ApiAttribute file = ApiAttribute.builder()
            .type("attribute")
            .string("File")
            .tail("foo.jpg")
            .build();
        final ApiAttribute multimedia = ApiAttribute.builder()
            .type("multimedia")
            .string("Multimedia")
            .attribute(file)
            .build();
        final ApiFamily o = ApiFamily.builder()
            .type("type")
            .string("string")
            .attribute(multimedia)
            .build();
        assertTrue(o.getImages().contains(multimedia), "Should contain multimedia");
    }

    @Test
    void testOneImage() {
        final ApiFamily o = familyWithMultimedia();
        assertEquals(1, o.getImages().size(), "Should contain 1 image");
    }

    @Test
    void testNoImages() {
        final ApiFamily o = ApiFamily.builder().type("type").string("string").build();
        assertEquals(0, o.getImages().size(), "Should contain 0 images");
    }

    @Test
    void testHusband() {
        final ApiAttribute husband = ApiAttribute.builder().type("husband").string("I2").build();
        final ApiFamily o = familyWithMultimedia(husband);
        assertEquals(1, o.getSpouses().size(), "Should contain 1 spouses");
    }

    @Test
    void testWife() {
        final ApiAttribute wife = ApiAttribute.builder().type("wife").string("I2").build();
        final ApiFamily o = familyWithMultimedia(wife);
        assertEquals(1, o.getSpouses().size(), "Should contain 1 spouses");
    }

    @Test
    void testSpouse() {
        final ApiAttribute spouse = ApiAttribute.builder().type("spouse").string("I2").build();
        final ApiFamily o = familyWithMultimedia(spouse);
        assertTrue(o.getSpouses().contains(spouse), "Should contain spouse");
    }

    @Test
    void testNoSpouse() {
        final ApiFamily o = familyWithMultimedia();
        assertEquals(0, o.getSpouses().size(), "Should contain 0 spouses");
    }

    @Test
    void testChild() {
        final ApiAttribute child = ApiAttribute.builder().type("child").string("I2").build();
        final ApiFamily o = familyWithMultimedia(child);
        assertTrue(o.getChildren().contains(child) && o.getChildren().size() == 1,
            "Should contain child");
    }

    @Test
    void testNoChildren() {
        final ApiFamily o = familyWithMultimedia();
        assertEquals(0, o.getChildren().size(), "Should contain 0 children");
    }

    @Test
    void testHashAndEquals() {
        EqualsVerifier.forClass(ApiFamily.class).suppress(Warning.STRICT_INHERITANCE).verify();
    }

    private ApiFamily familyWithMultimedia() {
        return ApiFamily.builder()
            .type("type")
            .string("string")
            .attribute(ApiAttribute.builder()
                .type("multimedia")
                .string("Multimedia")
                .attribute(
                    ApiAttribute.builder().type("attribute").string("File").tail("foo.jpg").build())
                .build())
            .build();
    }

    private ApiFamily familyWithMultimedia(final ApiAttribute additional) {
        return ApiFamily.builder()
            .type("type")
            .string("string")
            .attribute(additional)
            .attribute(ApiAttribute.builder()
                .type("multimedia")
                .string("Multimedia")
                .attribute(
                    ApiAttribute.builder().type("attribute").string("File").tail("foo.jpg").build())
                .build())
            .build();
    }
}
