package org.schoellerfamily.gedbrowser.api.datamodel.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;

/**
 * @author Dick Schoeller
 */
public class ApiFamilyTest {
    /** */
    @Test
    public void testDefaultConstructorType() {
        final ApiFamily o = new ApiFamily();
        assertEquals("", o.getType(), "type mismatch");
    }

    /** */
    @Test
    public void testDefaultConstructorString() {
        final ApiFamily o = new ApiFamily();
        assertEquals("", o.getString(), "string mismatch");
    }

    /** */
    @Test
    public void testDefaultConstructorAttributes() {
        final ApiFamily o = new ApiFamily();
        assertTrue(o.getAttributes().isEmpty(), "attributes mismatch");
    }

    /** */
    @Test
    public void testConstructorType() {
        final ApiFamily o = new ApiFamily("type", "string");
        assertEquals("type", o.getType(), "type mismatch");
    }

    /** */
    @Test
    public void testConstructorString() {
        final ApiFamily o = new ApiFamily("type", "string");
        assertEquals("string", o.getString(), "string mismatch");
    }

    /** */
    @Test
    public void testConstructorNoAttributes() {
        final ApiFamily o = new ApiFamily("type", "string");
        assertTrue(o.getAttributes().isEmpty(), "attributes empty mismatch");
    }

    /** */
    @Test
    public void testConstructorNullAttributes() {
        final ApiFamily o = new ApiFamily("type", "string", null);
        assertTrue(o.getAttributes().isEmpty(), "attributes empty mismatch");
    }

    /** */
    @Test
    public void testConstructorWithAttributes() {
        final List<ApiAttribute> attributes = List.of(
            new ApiAttribute("a string", "attribute", ""));
        final ApiFamily o = new ApiFamily("type", "string", attributes);
        assertEquals(1, o.getAttributes().size(), "attributes size mismatch");
    }

    /** */
    @Test
    public void testIsType() {
        final ApiFamily o = new ApiFamily("type", "string");
        assertTrue(o.isType("type"), "isType mismatch");
    }

    /** */
    @Test
    public void testAccept() {
        final ApiFamily o = new ApiFamily("type", "string");
        final ApiTestVisitor visitor = new ApiTestVisitor();
        o.accept(visitor);
        assertEquals("family", visitor.getMethodCalled(), "Method mismatch");
    }

    /** */
    @Test
    public void testImage() {
        final ApiFamily o = new ApiFamily("type", "string");
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
        final ApiFamily o = familyWithMultimedia();
        assertEquals(1, o.getImages().size(), "Should contain 1 image");
    }

    /** */
    @Test
    public void testNoImages() {
        final ApiFamily o = new ApiFamily("type", "string");
        assertEquals(0, o.getImages().size(), "Should contain 0 images");
    }

    /** */
    @Test public void testHusband() {
        final ApiFamily o = familyWithMultimedia();
        final ApiAttribute husband = new ApiAttribute("husband", "I2");
        o.addAttribute(husband);
        assertEquals(1, o.getSpouses().size(), "Should contain 1 spouses");
    }

    /** */
    @Test public void testWife() {
        final ApiFamily o = familyWithMultimedia();
        final ApiAttribute wife = new ApiAttribute("wife", "I2");
        o.addAttribute(wife);
        assertEquals(1, o.getSpouses().size(), "Should contain 1 spouses");
    }

    /** */
    @Test public void testSpouse() {
        final ApiFamily o = familyWithMultimedia();
        final ApiAttribute spouse = new ApiAttribute("spouse", "I2");
        o.addAttribute(spouse);
        assertTrue(o.getSpouses().contains(spouse), "Should contain spouse");
    }

    /** */
    @Test
    public void testNoSpouse() {
        final ApiFamily o = familyWithMultimedia();
        assertEquals(0, o.getSpouses().size(), "Should contain 0 spouses");
    }

    /** */
    @Test public void testChild() {
        final ApiFamily o = familyWithMultimedia();
        final ApiAttribute spouse = new ApiAttribute("child", "I2");
        o.addAttribute(spouse);
        assertTrue(o.getChildren().contains(spouse)
                && o.getChildren().size() == 1, "Should contain chould");
    }

    /** */
    @Test
    public void testNoChildren() {
        final ApiFamily o = familyWithMultimedia();
        assertEquals(0, o.getChildren().size(), "Should contain 0 children");
    }

    /** */
    @Test
    public void testHash() {
        final ApiFamily o = familyWithMultimedia();
        final int expected = -933468865;
        assertEquals(expected, o.hashCode(), "Hash should be");
    }

    /** */
    @Test
    public void testEquals() {
        final ApiFamily o1 = familyWithMultimedia();
        final ApiFamily o2 = familyWithMultimedia();
        assertEquals(o1, o2, "Objects should be equal");
    }

    /** */
    @Test
    public void testSame() {
        final ApiFamily o1 = familyWithMultimedia();
        assertEquals(o1, o1, "Objects should be equal");
    }

    /** */
    @Test
    public void testNotEquals() {
        final ApiFamily o1 = familyWithMultimedia();
        final ApiFamily o2 = familyWithMultimedia();
        final ApiAttribute a = new ApiAttribute(
                "attribute", "Occupation", "farmer");
        o2.addAttribute(a);
        assertNotEquals(o1, o2, "Objects should be unequal");
    }

    /** */
    @Test
    public void testNotEqualsChildren() {
        final ApiFamily o1 = familyWithMultimedia();
        final ApiFamily o2 = familyWithMultimedia();
        final ApiAttribute a = new ApiAttribute("child", "I1");
        o2.addAttribute(a);
        assertNotEquals(o1, o2, "Objects should be unequal");
    }

    /**
     * @return a new family
     */
    private ApiFamily familyWithMultimedia() {
        final ApiFamily o = new ApiFamily("type", "string");
        final ApiAttribute multimedia = new ApiAttribute(
                "multimedia", "Multimedia");
        final ApiAttribute file = new ApiAttribute(
                "attribute", "File", "foo.jpg");
        multimedia.getAttributes().add(file);
        o.addAttribute(multimedia);
        return o;
    }
}