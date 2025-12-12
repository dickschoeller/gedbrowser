package org.schoellerfamily.gedbrowser.api.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiNote;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;

/**
 * @author Dick Schoeller
 */
public class ApiPersonTest {
    /** */
    @Test
    public void testDefaultConstructorType() {
        final ApiPerson o = new ApiPerson();
        assertEquals("", o.getType(), "type mismatch");
    }

    /** */
    @Test
    public void testDefaultConstructorString() {
        final ApiPerson o = new ApiPerson();
        assertEquals("", o.getString(), "string mismatch");
    }

    /** */
    @Test
    public void testDefaultConstructorAttributes() {
        final ApiPerson o = new ApiPerson();
        assertTrue(o.getAttributes().isEmpty(), "attributes mismatch");
    }

    /** */
    @Test
    public void testConstructorType() {
        final ApiPerson.Builder builder = new ApiPerson.Builder()
        .type("type")
        .id("string")
        .indexName("index name")
        .surname("surname")
        .build();
        final ApiPerson o = new ApiPerson(builder);
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
        final ApiPerson.Builder builder = new ApiPerson.Builder()
                .id("string")
                .add(new ApiAttribute("a string", "attribute", ""))
                .build();
        final ApiPerson o = new ApiPerson(builder);
        assertEquals(1, o.getAttributes().size(), "attributes mismatch");
    }

    /** */
    @Test
    public void testIsType() {
        final ApiPerson.Builder builder = new ApiPerson.Builder()
                .id("string")
                .add(new ApiAttribute("a string", "attribute", ""))
                .build();
        final ApiPerson o = new ApiPerson(builder);
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
    public void testEqualsSame() {
        final ApiPerson expected = createPerson();
        assertEquals(expected, expected, "Should match");
    }

    /** */
    @Test
    public void testEqualsSimple() {
        final ApiPerson expected = createPerson();
        final ApiPerson actual = createPerson();
        assertEquals(expected, actual, "Should match");
    }

    /** */
    @Test
    public void testNotEqualsClass() {
        final ApiPerson expected = createPerson();
        final ApiNote actual = new ApiNote("type", "string", "tail");
        assertNotEquals(expected, actual, "Should not match");
    }

    /** */
    @Test
    public void testEqualsNullIndexName() {
        final ApiPerson.Builder builder = new ApiPerson.Builder()
                .type("type")
                .id("string")
                .indexName(null)
                .surname("surname");
        final ApiPerson expected = new ApiPerson(builder);
        final ApiPerson actual = new ApiPerson(builder);
        assertEquals(expected, actual, "Should match");
    }

    /** */
    @Test
    public void testNotEqualsNullIndexName() {
        final ApiPerson.Builder builder = new ApiPerson.Builder()
                .type("type")
                .id("string")
                .indexName(null)
                .surname("surname");
        final ApiPerson expected = new ApiPerson(builder);
        final ApiPerson actual = createPerson();
        assertNotEquals(expected, actual, "Should not match");
    }

    /** */
    @Test
    public void testNotEqualsIndexNameNull() {
        final ApiPerson expected = createPerson();
        final ApiPerson.Builder builder = new ApiPerson.Builder()
                .type("type")
                .id("string")
                .indexName(null)
                .surname("surname");
        final ApiPerson actual = new ApiPerson(builder);
        assertNotEquals(expected, actual, "Should not match");
    }

    /** */
    @Test
    public void testEqualsNullSurname() {
        final ApiPerson.Builder builder = new ApiPerson.Builder()
                .type("type")
                .id("string")
                .indexName("index name")
                .surname(null);
        final ApiPerson expected = new ApiPerson(builder);
        final ApiPerson actual = new ApiPerson(builder);
        assertEquals(expected, actual, "Should match");
    }

    /** */
    @Test
    public void testNotEqualsNullSurname() {
        final ApiPerson.Builder builder = new ApiPerson.Builder()
                .type("type")
                .id("string")
                .indexName("index name")
                .surname(null);
        final ApiPerson expected = new ApiPerson(builder);
        final ApiPerson actual = createPerson();
        assertNotEquals(expected, actual, "Should not match");
    }

    /** */
    @Test
    public void testNotEqualsSurnameNull() {
        final ApiPerson expected = createPerson();
        final ApiPerson.Builder builder = new ApiPerson.Builder()
                .type("type")
                .id("string")
                .indexName("index name")
                .surname(null);
        final ApiPerson actual = new ApiPerson(builder);
        assertNotEquals(expected, actual, "Should not match");
    }

    /** */
    @Test
    public void testEqualsHash() {
        final ApiPerson expected = createPerson();
        final ApiPerson actual = createPerson();
        assertEquals(expected.hashCode(), actual.hashCode(), "Should match");
    }

    /** */
    @Test
    public void testEqualsNullIndexNameHash() {
        final ApiPerson.Builder builder = new ApiPerson.Builder()
                .type("type")
                .id("string")
                .indexName(null)
                .surname("surname");
        final ApiPerson expected = new ApiPerson(builder);
        final ApiPerson actual = new ApiPerson(builder);
        assertEquals(expected.hashCode(), actual.hashCode(), "Should match");
    }

    /** */
    @Test
    public void testEqualsNullSurnameHash() {
        final ApiPerson.Builder builder = new ApiPerson.Builder()
                .type("type")
                .id("string")
                .indexName("index name")
                .surname(null);
        final ApiPerson expected = new ApiPerson(builder);
        final ApiPerson actual = new ApiPerson(builder);
        assertEquals(expected.hashCode(), actual.hashCode(), "Should match");
    }

    /** */
    @Test
    public void testNotEqualsNullSurnameHash() {
        // Not calling build in order to allow the null in.
        final ApiPerson.Builder builder = new ApiPerson.Builder()
                .type("type")
                .id("string")
                .indexName("index name")
                .surname(null);
        final ApiPerson expected = new ApiPerson(builder);
        final ApiPerson actual = createPerson();
        assertNotEquals(expected.hashCode(), actual.hashCode(), "Should not match");
    }

    /** */
    @Test
    public void testNotEqualsSurnameNullHash() {
        final ApiPerson expected = createPerson();
        // Not calling build in order to allow the null in.
        final ApiPerson.Builder builder = new ApiPerson.Builder()
                .type("type")
                .id("string")
                .indexName("index name")
                .surname(null);
        final ApiPerson actual = new ApiPerson(builder);
        assertNotEquals(expected.hashCode(), actual.hashCode(), "Should not match");
    }

    /** */
    @Test
    public void testImage() {
        final ApiPerson o = createPerson();
        final ApiAttribute multimedia = new ApiAttribute(
                "multimedia", "Multimedia");
        final ApiAttribute file = new ApiAttribute(
                "attribute", "File", "foo.jpg");
        multimedia.getAttributes().add(file);
        o.addAttribute(multimedia);
        assertTrue(o.getImages().contains(multimedia), "Should contain multimedia");
    }

    /** */
    @Test
    public void testOneImage() {
        final ApiPerson o = createPerson();
        final ApiAttribute multimedia = new ApiAttribute(
                "multimedia", "Multimedia");
        final ApiAttribute file = new ApiAttribute(
                "attribute", "File", "foo.jpg");
        multimedia.getAttributes().add(file);
        o.addAttribute(multimedia);
        assertEquals(1, o.getImages().size(), "Should contain 1 image");
    }

    /** */
    @Test
    public void testNoImages() {
        final ApiPerson o = createPerson();
        assertEquals(0, o.getImages().size(), "Should contain 0 images");
    }

    /**
     * @return the new person
     */
    private ApiPerson createPerson() {
        final ApiPerson.Builder builder = new ApiPerson.Builder()
                .type("type")
                .id("string")
                .indexName("index name")
                .surname("surname")
                .build();
        return new ApiPerson(builder);
    }

    /** */
    @Test
    public void testHash() {
        final ApiPerson o = createPerson();
        final ApiAttribute multimedia = new ApiAttribute("multimedia", "Multimedia");
        final ApiAttribute file = new ApiAttribute("attribute", "File", "foo.jpg");
        multimedia.getAttributes().add(file);
        o.addAttribute(multimedia);
        final int expected = 33764385;
        assertEquals(expected, o.hashCode(), "Hash should be");
    }
}
