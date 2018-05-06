package org.schoellerfamily.gedbrowser.api.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;

/**
 * @author Dick Schoeller
 */
public class ApiObjectTest {
    /** */
    @Test
    public void testDefaultConstructorType() {
        final ApiObject o = new ApiObject();
        assertEquals("type mismatch", "", o.getType());
    }

    /** */
    @Test
    public void testDefaultConstructorString() {
        final ApiObject o = new ApiObject();
        assertEquals("string mismatch", "", o.getString());
    }

    /** */
    @Test
    public void testDefaultConstructorAttributes() {
        final ApiObject o = new ApiObject();
        assertTrue("attributes mismatch", o.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testConstructorType() {
        final ApiObject o = new ApiObject("type", "string");
        assertEquals("type mismatch", "type", o.getType());
    }

    /** */
    @Test
    public void testConstructorString() {
        final ApiObject o = new ApiObject("type", "string");
        assertEquals("string mismatch", "string", o.getString());
    }

    /** */
    @Test
    public void testConstructorNoAttributes() {
        final ApiObject o = new ApiObject("type", "string");
        assertTrue("attributes mismatch", o.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testConstructorNullAttributes() {
        final ApiObject o = new ApiObject("type", "string", null);
        assertTrue("attributes mismatch", o.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testConstructorWithAttributes() {
        final List<ApiAttribute> attributes = new ArrayList<>();
        attributes.add(new ApiAttribute("a string", "attribute", ""));
        final ApiObject o = new ApiObject("type", "string", attributes);
        assertEquals("attributes mismatch", 1, o.getAttributes().size());
    }

    /** */
    @Test
    public void testIsType() {
        final ApiObject o = new ApiObject("type", "string");
        assertTrue("isType mismatch", o.isType("type"));
    }

    /** */
    @Test
    public void testAccept() {
        final ApiObject o = new ApiObject("type", "string");
        final ApiTestVisitor visitor = new ApiTestVisitor();
        o.accept(visitor);
        assertEquals("Method mismatch", "base", visitor.getMethodCalled());
    }

    /** */
    @Test
    public void testEqualsSame() {
        final ApiObject expected = new ApiObject("type", "string");
        assertEquals("Should match", expected, expected);
    }

    /** */
    @Test
    public void testEqualsSimple() {
        final ApiObject expected = new ApiObject("type", "string");
        final ApiObject actual = new ApiObject("type", "string");
        assertEquals("Should match", expected, actual);
    }

    /** */
    @Test
    public void testNotEqualsType() {
        final ApiObject expected = new ApiObject("type", "string");
        final ApiObject actual = new ApiObject("type1", "string");
        assertNotEquals("Should not match", expected, actual);
    }

    /** */
    @Test
    public void testNotEqualsNull() {
        final ApiObject expected = new ApiObject("type", "string");
        assertNotEquals("Should not match", expected, (ApiObject) null);
    }

    /** */
    @Test
    public void testNotEqualsString() {
        final ApiObject expected = new ApiObject("type", "string");
        final ApiObject actual = new ApiObject("type", "string1");
        assertNotEquals("Should not match", expected, actual);
    }

    /** */
    @Test
    public void testNotEqualsNullType() {
        final ApiObject expected = new ApiObject("type", "string");
        final ApiObject actual = new ApiObject(null, "string");
        assertNotEquals("Should not match", expected, actual);
    }

    /** */
    @Test
    public void testNotEqualsNullString() {
        final ApiObject expected = new ApiObject("type", "string");
        final ApiObject actual = new ApiObject("type", null);
        assertNotEquals("Should not match", expected, actual);
    }

    /** */
    @Test
    public void testNotEqualsTypeNull() {
        final ApiObject expected = new ApiObject(null, "string");
        final ApiObject actual = new ApiObject("type1", "string");
        assertNotEquals("Should not match", expected, actual);
    }

    /** */
    @Test
    public void testNotEqualsStringNull() {
        final ApiObject expected = new ApiObject("type", null);
        final ApiObject actual = new ApiObject("type", "string1");
        assertNotEquals("Should not match", expected, actual);
    }

    /** */
    @Test
    public void testEqualsNullType() {
        final ApiObject expected = new ApiObject(null, "string");
        final ApiObject actual = new ApiObject(null, "string");
        assertEquals("Should match", expected, actual);
    }

    /** */
    @Test
    public void testEqualsNullString() {
        final ApiObject expected = new ApiObject("type", null);
        final ApiObject actual = new ApiObject("type", null);
        assertEquals("Should match", expected, actual);
    }

    /** */
    @Test
    public void testNotEqualsClass() {
        final ApiObject expected = new ApiObject("type", "string");
        final ApiFamily actual = new ApiFamily("type", "string");
        assertNotEquals("Should not match", expected, actual);
    }

    /** */
    @Test
    public void testEqualsWithAttributes() {
        final List<ApiAttribute> expectedAttributes = new ArrayList<>();
        expectedAttributes.add(new ApiAttribute("atype", "string", "tail"));
        final ApiObject expected =
                new ApiObject("type", "string", expectedAttributes);
        final List<ApiAttribute> actualAttributes = new ArrayList<>();
        actualAttributes.add(new ApiAttribute("atype", "string", "tail"));
        final ApiObject actual =
                new ApiObject("type", "string", actualAttributes);
        assertEquals("Should match", expected, actual);
    }

    /** */
    @Test
    public void testNotEqualsBecauseOfAttributes() {
        final List<ApiAttribute> expectedAttributes = new ArrayList<>();
        expectedAttributes.add(new ApiAttribute("atype", "string", "tail"));
        final ApiObject expected =
                new ApiObject("type", "string", expectedAttributes);
        final List<ApiAttribute> actualAttributes = new ArrayList<>();
        actualAttributes.add(new ApiAttribute("atype1", "string", "tail"));
        final ApiObject actual =
                new ApiObject("type", "string", actualAttributes);
        assertNotEquals("Should not match", expected, actual);
    }

    /** */
    @Test
    public void testNotEqualsBecauseOfAttributeTail() {
        final List<ApiAttribute> expectedAttributes = new ArrayList<>();
        expectedAttributes.add(new ApiAttribute("atype", "string", "tail"));
        final ApiObject expected =
                new ApiObject("type", "string", expectedAttributes);
        final List<ApiAttribute> actualAttributes = new ArrayList<>();
        actualAttributes.add(new ApiAttribute("atype", "string", "tail1"));
        final ApiObject actual =
                new ApiObject("type", "string", actualAttributes);
        assertNotEquals("Should not match", expected, actual);
    }

    /** */
    @Test
    public void testNotEqualsBecauseOfAttributeNullTail() {
        final List<ApiAttribute> expectedAttributes = new ArrayList<>();
        expectedAttributes.add(new ApiAttribute("atype", "string", "tail"));
        final ApiObject expected =
                new ApiObject("type", "string", expectedAttributes);
        final List<ApiAttribute> actualAttributes = new ArrayList<>();
        actualAttributes.add(new ApiAttribute("atype", "string", null));
        final ApiObject actual =
                new ApiObject("type", "string", actualAttributes);
        assertNotEquals("Should not match", expected, actual);
    }

    /** */
    @Test public void testHashEquals() {
        final List<ApiAttribute> expectedAttributes = new ArrayList<>();
        expectedAttributes.add(new ApiAttribute("atype", "string", "tail"));
        final ApiObject expected =
                new ApiObject("type", "string", expectedAttributes);
        final List<ApiAttribute> actualAttributes = new ArrayList<>();
        actualAttributes.add(new ApiAttribute("atype", "string", "tail"));
        final ApiObject actual =
                new ApiObject("type", "string", actualAttributes);
        assertEquals("Should match", expected.hashCode(), actual.hashCode());
    }

    /** */
    @Test public void testHashNotEquals() {
        final List<ApiAttribute> expectedAttributes = new ArrayList<>();
        expectedAttributes.add(new ApiAttribute("atype", "string", "tail1"));
        final ApiObject expected =
                new ApiObject("type", "string", expectedAttributes);
        final List<ApiAttribute> actualAttributes = new ArrayList<>();
        actualAttributes.add(new ApiAttribute("atype", "string", "tail"));
        final ApiObject actual =
                new ApiObject("type", "string", actualAttributes);
        assertNotEquals("Should not match",
                expected.hashCode(), actual.hashCode());
    }

    /** */
    @Test public void testHashNotEqualsNullType() {
        final List<ApiAttribute> expectedAttributes = new ArrayList<>();
        expectedAttributes.add(new ApiAttribute("atype", "string", "tail"));
        final ApiObject expected =
                new ApiObject("type", "string", expectedAttributes);
        final List<ApiAttribute> actualAttributes = new ArrayList<>();
        actualAttributes.add(new ApiAttribute(null, "string", "tail"));
        final ApiObject actual =
                new ApiObject("type", "string", actualAttributes);
        assertNotEquals("Should not match",
                expected.hashCode(), actual.hashCode());
    }

    /** */
    @Test public void testHashNotEqualsNullString() {
        final List<ApiAttribute> expectedAttributes = new ArrayList<>();
        expectedAttributes.add(new ApiAttribute("atype", "string", "tail"));
        final ApiObject expected =
                new ApiObject("type", "string", expectedAttributes);
        final List<ApiAttribute> actualAttributes = new ArrayList<>();
        actualAttributes.add(new ApiAttribute("atype", null, "tail"));
        final ApiObject actual =
                new ApiObject("type", "string", actualAttributes);
        assertNotEquals("Should not match",
                expected.hashCode(), actual.hashCode());
    }

    /** */
    @Test
    public void testHashNotEqualsBecauseOfAttributeNullTail() {
        final List<ApiAttribute> expectedAttributes = new ArrayList<>();
        expectedAttributes.add(new ApiAttribute("atype", "string", "tail"));
        final ApiObject expected =
                new ApiObject("type", "string", expectedAttributes);
        final List<ApiAttribute> actualAttributes = new ArrayList<>();
        actualAttributes.add(new ApiAttribute("atype", "string", null));
        final ApiObject actual =
                new ApiObject("type", "string", actualAttributes);
        assertNotEquals("Should not match",
                expected.hashCode(), actual.hashCode());
    }
}
