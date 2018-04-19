package org.schoellerfamily.gedbrowser.api.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
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
        assertEquals("type mismatch", "", o.getType());
    }

    /** */
    @Test
    public void testDefaultConstructorString() {
        final ApiSource o = new ApiSource();
        assertEquals("string mismatch", "", o.getString());
    }

    /** */
    @Test
    public void testDefaultConstructorAttributes() {
        final ApiSource o = new ApiSource();
        assertTrue("attributes mismatch", o.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testConstructorType() {
        final ApiSource o = new ApiSource("type", "string", "Unknown");
        assertEquals("type mismatch", "type", o.getType());
    }

    /** */
    @Test
    public void testConstructorString() {
        final ApiSource o = new ApiSource("type", "string", "Unknown");
        assertEquals("string mismatch", "string", o.getString());
    }

    /** */
    @Test
    public void testConstructorNoAttributes() {
        final ApiSource o = new ApiSource("type", "string", "Unknown");
        assertTrue("attributes empty mismatch", o.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testConstructorNullAttributes() {
        final ApiSource o = new ApiSource("type", "string", null);
        assertTrue("attributes empty mismatch", o.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testConstructorWithAttributes() {
        final List<ApiAttribute> attributes = new ArrayList<>();
        attributes.add(new ApiAttribute("a string", "attribute", ""));
        final ApiSource o = new ApiSource("type", "string", attributes,
                "Unknown");
        assertEquals("attributes size mismatch", 1, o.getAttributes().size());
    }

    /** */
    @Test
    public void testIsType() {
        final ApiSource o = new ApiSource("type", "string", "Unknown");
        assertTrue("isType mismatch", o.isType("type"));
    }

    /** */
    @Test
    public void testAccept() {
        final ApiSource o = new ApiSource("type", "string", "Unknown");
        final ApiTestVisitor visitor = new ApiTestVisitor();
        o.accept(visitor);
        assertEquals("Method mismatch", "source", visitor.getMethodCalled());
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
        assertTrue("Should contain multimedia",
                o.getImages().contains(multimedia));
    }

    /** */
    @Test
    public void testOneImage() {
        final ApiSource o = sourceWithMultimedia();
        assertEquals("Should contain 1 image", 1, o.getImages().size());
    }

    /** */
    @Test
    public void testNoImages() {
        final ApiSource o = new ApiSource("type", "string", "title");
        assertEquals("Should contain 0 images", 0, o.getImages().size());
    }

    /** */
    @Test
    public void testHash() {
        final ApiSource o = sourceWithMultimedia();
        final int expected = 1050090841;
        assertEquals("Hash should be", expected, o.hashCode());
    }

    /** */
    @Test
    public void testEquals() {
        final ApiSource o1 = sourceWithMultimedia();
        final ApiSource o2 = sourceWithMultimedia();
        assertEquals("Objects should be equal", o1, o2);
    }

    /** */
    @Test
    public void testSame() {
        final ApiSource o1 = sourceWithMultimedia();
        assertEquals("Objects should be equal", o1, o1);
    }

    /** */
    @Test
    public void testNotEquals() {
        final ApiSource o1 = sourceWithMultimedia();
        final ApiSource o2 = sourceWithMultimedia();
        final ApiAttribute a = new ApiAttribute(
                "attribute", "Repository", "Needham Public Library");
        o2.addAttribute(a);
        assertNotEquals("Objects should be unequal", o1, o2);
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
