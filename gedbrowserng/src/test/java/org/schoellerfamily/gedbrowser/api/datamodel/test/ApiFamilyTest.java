package org.schoellerfamily.gedbrowser.api.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
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
        assertEquals("type mismatch", "", o.getType());
    }

    /** */
    @Test
    public void testDefaultConstructorString() {
        final ApiFamily o = new ApiFamily();
        assertEquals("string mismatch", "", o.getString());
    }

    /** */
    @Test
    public void testDefaultConstructorAttributes() {
        final ApiFamily o = new ApiFamily();
        assertTrue("attributes mismatch", o.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testConstructorType() {
        final ApiFamily o = new ApiFamily("type", "string");
        assertEquals("type mismatch", "type", o.getType());
    }

    /** */
    @Test
    public void testConstructorString() {
        final ApiFamily o = new ApiFamily("type", "string");
        assertEquals("string mismatch", "string", o.getString());
    }

    /** */
    @Test
    public void testConstructorNoAttributes() {
        final ApiFamily o = new ApiFamily("type", "string");
        assertTrue("attributes empty mismatch", o.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testConstructorNullAttributes() {
        final ApiFamily o = new ApiFamily("type", "string", null);
        assertTrue("attributes empty mismatch", o.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testConstructorWithAttributes() {
        final List<ApiAttribute> attributes = new ArrayList<>();
        attributes.add(new ApiAttribute("a string", "attribute", ""));
        final ApiFamily o = new ApiFamily("type", "string", attributes);
        assertEquals("attributes size mismatch", 1, o.getAttributes().size());
    }

    /** */
    @Test
    public void testIsType() {
        final ApiFamily o = new ApiFamily("type", "string");
        assertTrue("isType mismatch", o.isType("type"));
    }

    /** */
    @Test
    public void testAccept() {
        final ApiFamily o = new ApiFamily("type", "string");
        final ApiTestVisitor visitor = new ApiTestVisitor();
        o.accept(visitor);
        assertEquals("Method mismatch", "family", visitor.getMethodCalled());
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
        assertTrue("Should contain multimedia",
                o.getImages().contains(multimedia));
    }

    /** */
    @Test
    public void testOneImage() {
        final ApiFamily o = familyWithMultimedia();
        assertEquals("Should contain 1 image", 1, o.getImages().size());
    }

    /** */
    @Test
    public void testNoImages() {
        final ApiFamily o = new ApiFamily("type", "string");
        assertEquals("Should contain 0 images", 0, o.getImages().size());
    }

    /** */
    @Test public void testHusband() {
        final ApiFamily o = familyWithMultimedia();
        final ApiAttribute husband = new ApiAttribute("husband", "I2");
        o.addAttribute(husband);
        assertEquals("Should contain 1 spouses", 1, o.getSpouses().size());
    }

    /** */
    @Test public void testWife() {
        final ApiFamily o = familyWithMultimedia();
        final ApiAttribute wife = new ApiAttribute("wife", "I2");
        o.addAttribute(wife);
        assertEquals("Should contain 1 spouses", 1, o.getSpouses().size());
    }

    /** */
    @Test public void testSpouse() {
        final ApiFamily o = familyWithMultimedia();
        final ApiAttribute spouse = new ApiAttribute("spouse", "I2");
        o.addAttribute(spouse);
        assertTrue("Should contain spouse", o.getSpouses().contains(spouse));
    }

    /** */
    @Test
    public void testNoSpouse() {
        final ApiFamily o = familyWithMultimedia();
        assertEquals("Should contain 0 spouses", 0, o.getSpouses().size());
    }

    /** */
    @Test public void testChild() {
        final ApiFamily o = familyWithMultimedia();
        final ApiAttribute spouse = new ApiAttribute("child", "I2");
        o.addAttribute(spouse);
        assertTrue("Should contain chould",
                o.getChildren().contains(spouse)
                && o.getChildren().size() == 1);
    }

    /** */
    @Test
    public void testNoChildren() {
        final ApiFamily o = familyWithMultimedia();
        assertEquals("Should contain 0 children", 0, o.getChildren().size());
    }

    /** */
    @Test
    public void testHash() {
        final ApiFamily o = familyWithMultimedia();
        final int expected = -933468865;
        assertEquals("Hash should be", expected, o.hashCode());
    }

    /** */
    @Test
    public void testEquals() {
        final ApiFamily o1 = familyWithMultimedia();
        final ApiFamily o2 = familyWithMultimedia();
        assertEquals("Objects should be equal", o1, o2);
    }

    /** */
    @Test
    public void testSame() {
        final ApiFamily o1 = familyWithMultimedia();
        assertEquals("Objects should be equal", o1, o1);
    }

    /** */
    @Test
    public void testNotEquals() {
        final ApiFamily o1 = familyWithMultimedia();
        final ApiFamily o2 = familyWithMultimedia();
        final ApiAttribute a = new ApiAttribute(
                "attribute", "Occupation", "farmer");
        o2.addAttribute(a);
        assertNotEquals("Objects should be unequal", o1, o2);
    }

    /** */
    @Test
    public void testNotEqualsChildren() {
        final ApiFamily o1 = familyWithMultimedia();
        final ApiFamily o2 = familyWithMultimedia();
        final ApiAttribute a = new ApiAttribute("child", "I1");
        o2.addAttribute(a);
        assertNotEquals("Objects should be unequal", o1, o2);
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
