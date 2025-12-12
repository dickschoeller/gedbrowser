package org.schoellerfamily.gedbrowser.api.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSource;

/**
 * @author Dick Schoeller
 */
public class ApiSourceTest {
    /** */
    @Test
    public void testDefaultConstructorType() {
        final ApiSource o = new ApiSource();
        assertEquals("", o.getType(), "type mismatch");
    }

    /** */
    @Test
    public void testDefaultConstructorString() {
        final ApiSource o = new ApiSource();
        assertEquals("", o.getString(), "string mismatch");
    }

    /** */
    @Test
    public void testDefaultConstructorAttributes() {
        final ApiSource o = new ApiSource();
        assertTrue(o.getAttributes().isEmpty(), "attributes mismatch");
    }

    /** */
    @Test
    public void testConstructorType() {
        final ApiSource o = new ApiSource("type", "string", "Unknown");
        assertEquals("type", o.getType(), "type mismatch");
    }

    /** */
    @Test
    public void testConstructorString() {
        final ApiSource o = new ApiSource("type", "string", "Unknown");
        assertEquals("string", o.getString(), "string mismatch");
    }

    /** */
    @Test
    public void testConstructorNoAttributes() {
        final ApiSource o = new ApiSource("type", "string", "Unknown");
        assertTrue(o.getAttributes().isEmpty(), "attributes empty mismatch");
    }

    /** */
    @Test
    public void testConstructorNullAttributes() {
        final ApiSource o = new ApiSource("type", "string", null);
        assertTrue(o.getAttributes().isEmpty(), "attributes empty mismatch");
    }

    /** */
    @Test
    public void testConstructorWithAttributes() {
        final List<ApiAttribute> attributes =
            List.of(new ApiAttribute("a string", "attribute", ""));
        final ApiSource o = new ApiSource("type", "string", attributes, "Unknown");
        assertEquals(1, o.getAttributes().size(), "attributes size mismatch");
    }

    /** */
    @Test
    public void testIsType() {
        final ApiSource o = new ApiSource("type", "string", "Unknown");
        assertTrue(o.isType("type"), "isType mismatch");
    }

    /** */
    @Test
    public void testAccept() {
        final ApiSource o = new ApiSource("type", "string", "Unknown");
        final ApiTestVisitor visitor = new ApiTestVisitor();
        o.accept(visitor);
        assertEquals("source", visitor.getMethodCalled(), "Method mismatch");
    }

    /** */
    @Test
    public void testImage() {
        final ApiSource o = new ApiSource("type", "string", "title");
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
        final ApiSource o = sourceWithMultimedia();
        assertEquals(1, o.getImages().size(), "Should contain 1 image");
    }

    /** */
    @Test
    public void testNoImages() {
        final ApiSource o = new ApiSource("type", "string", "title");
        assertEquals(0, o.getImages().size(), "Should contain 0 images");
    }

    /** */
    @Test
    public void testHash() {
        final ApiSource o = sourceWithMultimedia();
        final int expected = 1050090841;
        assertEquals(expected, o.hashCode(), "Hash should be");
    }

    /** */
    @Test
    public void testEquals() {
        final ApiSource o1 = sourceWithMultimedia();
        final ApiSource o2 = sourceWithMultimedia();
        assertEquals(o1, o2, "Objects should be equal");
    }

    /** */
    @Test
    public void testSame() {
        final ApiSource o1 = sourceWithMultimedia();
        assertEquals(o1, o1, "Objects should be equal");
    }

    /** */
    @Test
    public void testNotEquals() {
        final ApiSource o1 = sourceWithMultimedia();
        final ApiSource o2 = sourceWithMultimedia();
        final ApiAttribute a = new ApiAttribute(
                "attribute", "Repository", "Needham Public Library");
        o2.addAttribute(a);
        assertNotEquals(o1, o2, "Objects should be unequal");
    }

    /**
     * @return a new source
     */
    private ApiSource sourceWithMultimedia() {
        final ApiSource o = new ApiSource("type", "string", "title");
        final ApiAttribute multimedia = new ApiAttribute(
                "multimedia", "Multimedia");
        final ApiAttribute file = new ApiAttribute(
                "attribute", "File", "foo.jpg");
        multimedia.getAttributes().add(file);
        o.addAttribute(multimedia);
        return o;
    }
}