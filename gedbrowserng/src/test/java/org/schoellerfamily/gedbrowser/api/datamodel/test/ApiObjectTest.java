package org.schoellerfamily.gedbrowser.api.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
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
        assertEquals("", o.getType(), "type mismatch");
    }

    /** */
    @Test
    public void testDefaultConstructorString() {
        final ApiObject o = new ApiObject();
        assertEquals("", o.getString(), "string mismatch");
    }

    /** */
    @Test
    public void testDefaultConstructorAttributes() {
        final ApiObject o = new ApiObject();
        assertTrue(o.getAttributes().isEmpty(), "attributes mismatch");
    }

    /** */
    @Test
    public void testConstructorType() {
        final ApiObject o = new ApiObject("type", "string");
        assertEquals("type", o.getType(), "type mismatch");
    }

    /** */
    @Test
    public void testConstructorString() {
        final ApiObject o = new ApiObject("type", "string");
        assertEquals("string", o.getString(), "string mismatch");
    }

    /** */
    @Test
    public void testConstructorNoAttributes() {
        final ApiObject o = new ApiObject("type", "string");
        assertTrue(o.getAttributes().isEmpty(), "attributes mismatch");
    }

    /** */
    @Test
    public void testConstructorNullAttributes() {
        final ApiObject o = new ApiObject("type", "string", null);
        assertTrue(o.getAttributes().isEmpty(), "attributes mismatch");
    }

    /** */
    @Test
    public void testConstructorWithAttributes() {
        final List<ApiAttribute> attributes = List.of(new ApiAttribute("a string", "attribute", ""));
        final ApiObject o = new ApiObject("type", "string", attributes);
        assertEquals(1, o.getAttributes().size(), "attributes mismatch");
    }

    /** */
    @Test
    public void testIsType() {
        final ApiObject o = new ApiObject("type", "string");
        assertTrue(o.isType("type"), "isType mismatch");
    }

    /** */
    @Test
    public void testAccept() {
        final ApiObject o = new ApiObject("type", "string");
        final ApiTestVisitor visitor = new ApiTestVisitor();
        o.accept(visitor);
        assertEquals("base", visitor.getMethodCalled(), "Method mismatch");
    }

    /** */
    @Test
    public void testEqualsSame() {
        final ApiObject expected = new ApiObject("type", "string");
        assertEquals(expected, expected, "Should match");
    }

    /** */
    @Test
    public void testEqualsSimple() {
        final ApiObject expected = new ApiObject("type", "string");
        final ApiObject actual = new ApiObject("type", "string");
        assertEquals(expected, actual, "Should match");
    }

    /** */
    @Test
    public void testNotEqualsType() {
        final ApiObject expected = new ApiObject("type", "string");
        final ApiObject actual = new ApiObject("type1", "string");
        assertNotEquals(expected, actual, "Should not match");
    }

    /** */
    @Test
    public void testNotEqualsNull() {
        final ApiObject expected = new ApiObject("type", "string");
        assertNotEquals(expected, (ApiObject) null, "Should not match");
    }

    /** */
    @Test
    public void testNotEqualsString() {
        final ApiObject expected = new ApiObject("type", "string");
        final ApiObject actual = new ApiObject("type", "string1");
        assertNotEquals(expected, actual, "Should not match");
    }

    /** */
    @Test
    public void testNotEqualsNullType() {
        final ApiObject expected = new ApiObject("type", "string");
        final ApiObject actual = new ApiObject(null, "string");
        assertNotEquals(expected, actual, "Should not match");
    }

    /** */
    @Test
    public void testNotEqualsNullString() {
        final ApiObject expected = new ApiObject("type", "string");
        final ApiObject actual = new ApiObject("type", null);
        assertNotEquals(expected, actual, "Should not match");
    }

    /** */
    @Test
    public void testNotEqualsTypeNull() {
        final ApiObject expected = new ApiObject(null, "string");
        final ApiObject actual = new ApiObject("type1", "string");
        assertNotEquals(expected, actual, "Should not match");
    }

    /** */
    @Test
    public void testNotEqualsStringNull() {
        final ApiObject expected = new ApiObject("type", null);
        final ApiObject actual = new ApiObject("type", "string1");
        assertNotEquals(expected, actual, "Should not match");
    }

    /** */
    @Test
    public void testEqualsNullType() {
        final ApiObject expected = new ApiObject(null, "string");
        final ApiObject actual = new ApiObject(null, "string");
        assertEquals(expected, actual, "Should match");
    }

    /** */
    @Test
    public void testEqualsNullString() {
        final ApiObject expected = new ApiObject("type", null);
        final ApiObject actual = new ApiObject("type", null);
        assertEquals(expected, actual, "Should match");
    }

    /** */
    @Test
    public void testNotEqualsClass() {
        final ApiObject expected = new ApiObject("type", "string");
        final ApiFamily actual = new ApiFamily("type", "string");
        assertNotEquals(expected, actual, "Should not match");
    }

    /** */
    @Test
    public void testEqualsWithAttributes() {
        final List<ApiAttribute> expectedAttributes = List.of(new ApiAttribute("atype", "string", "tail"));
        final ApiObject expected =
                new ApiObject("type", "string", expectedAttributes);
        final List<ApiAttribute> actualAttributes = List.of(new ApiAttribute("atype", "string", "tail"));
        final ApiObject actual =
                new ApiObject("type", "string", actualAttributes);
        assertEquals(expected, actual, "Should match");
    }

    /** */
    @Test
    public void testNotEqualsBecauseOfAttributes() {
        final List<ApiAttribute> expectedAttributes = List.of(new ApiAttribute("atype", "string", "tail"));
        final ApiObject expected =
                new ApiObject("type", "string", expectedAttributes);
        final List<ApiAttribute> actualAttributes = List.of(new ApiAttribute("atype1", "string", "tail"));
        final ApiObject actual =
                new ApiObject("type", "string", actualAttributes);
        assertNotEquals(expected, actual, "Should not match");
    }

    /** */
    @Test
    public void testNotEqualsBecauseOfAttributeTail() {
        final List<ApiAttribute> expectedAttributes = List.of(new ApiAttribute("atype", "string", "tail"));
        final ApiObject expected =
                new ApiObject("type", "string", expectedAttributes);
        final List<ApiAttribute> actualAttributes = List.of(new ApiAttribute("atype", "string", "tail1"));
        final ApiObject actual =
                new ApiObject("type", "string", actualAttributes);
        assertNotEquals(expected, actual, "Should not match");
    }

    /** */
    @Test
    public void testNotEqualsBecauseOfAttributeNullTail() {
        final List<ApiAttribute> expectedAttributes = List.of(new ApiAttribute("atype", "string", "tail"));
        final ApiObject expected =
                new ApiObject("type", "string", expectedAttributes);
        final List<ApiAttribute> actualAttributes = List.of(new ApiAttribute("atype", "string", null));
        final ApiObject actual =
                new ApiObject("type", "string", actualAttributes);
        assertNotEquals(expected, actual, "Should not match");
    }

    /** */
    @Test public void testHashEquals() {
        final List<ApiAttribute> expectedAttributes = List.of(new ApiAttribute("atype", "string", "tail"));
        final ApiObject expected =
                new ApiObject("type", "string", expectedAttributes);
        final List<ApiAttribute> actualAttributes = List.of(new ApiAttribute("atype", "string", "tail"));
        final ApiObject actual =
                new ApiObject("type", "string", actualAttributes);
        assertEquals(expected.hashCode(), actual.hashCode(), "Should match");
    }

    /** */
    @Test public void testHashNotEquals() {
        final List<ApiAttribute> expectedAttributes = List.of(new ApiAttribute("atype", "string", "tail1"));
        final ApiObject expected =
                new ApiObject("type", "string", expectedAttributes);
        final List<ApiAttribute> actualAttributes = List.of(new ApiAttribute("atype", "string", "tail"));
        final ApiObject actual =
                new ApiObject("type", "string", actualAttributes);
        assertNotEquals(expected.hashCode(), actual.hashCode(), "Should not match");
    }

    /** */
    @Test public void testHashNotEqualsNullType() {
        final List<ApiAttribute> expectedAttributes = List.of(new ApiAttribute("atype", "string", "tail"));
        final ApiObject expected =
                new ApiObject("type", "string", expectedAttributes);
        final List<ApiAttribute> actualAttributes = List.of(new ApiAttribute(null, "string", "tail"));
        final ApiObject actual =
                new ApiObject("type", "string", actualAttributes);
        assertNotEquals(expected.hashCode(), actual.hashCode(), "Should not match");
    }

    /** */
    @Test public void testHashNotEqualsNullString() {
        final List<ApiAttribute> expectedAttributes = List.of(new ApiAttribute("atype", "string", "tail"));
        final ApiObject expected =
                new ApiObject("type", "string", expectedAttributes);
        final List<ApiAttribute> actualAttributes = List.of(new ApiAttribute("atype", null, "tail"));
        final ApiObject actual =
                new ApiObject("type", "string", actualAttributes);
        assertNotEquals(expected.hashCode(), actual.hashCode(), "Should not match");
    }

    /** */
    @Test
    public void testHashNotEqualsBecauseOfAttributeNullTail() {
        final List<ApiAttribute> expectedAttributes = List.of(new ApiAttribute("atype", "string", "tail"));
        final ApiObject expected =
                new ApiObject("type", "string", expectedAttributes);
        final List<ApiAttribute> actualAttributes = List.of(new ApiAttribute("atype", "string", null));
        final ApiObject actual =
                new ApiObject("type", "string", actualAttributes);
        assertNotEquals(expected.hashCode(), actual.hashCode(), "Should not match");
    }
}