package org.schoellerfamily.gedbrowser.api.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiLifespan;
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
        assertEquals("type mismatch", "", o.getType());
    }

    /** */
    @Test
    public void testDefaultConstructorString() {
        final ApiPerson o = new ApiPerson();
        assertEquals("string mismatch", "", o.getString());
    }

    /** */
    @Test
    public void testDefaultConstructorAttributes() {
        final ApiPerson o = new ApiPerson();
        assertTrue("attributes mismatch", o.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testConstructorType() {
        final ApiPerson o = new ApiPerson("type", "string", "indexName",
                "surname", new ApiLifespan());
        assertEquals("type mismatch", "type", o.getType());
    }

    /** */
    @Test
    public void testConstructorString() {
        final ApiPerson o = new ApiPerson("type", "string", "indexName",
                "surname", new ApiLifespan());
        assertEquals("string mismatch", "string", o.getString());
    }

    /** */
    @Test
    public void testConstructorIndexName() {
        final ApiPerson o = new ApiPerson("type", "string", "indexName",
                "surname", new ApiLifespan());
        assertEquals("indexname mismatch", "indexName", o.getIndexName());
    }

    /** */
    @Test
    public void testConstructorSurname() {
        final ApiPerson o = new ApiPerson("type", "string", "indexName",
                "surname", new ApiLifespan());
        assertEquals("surname mismatch", "surname", o.getSurname());
    }

    /** */
    @Test
    public void testConstructorNoAttributes() {
        final ApiPerson o = new ApiPerson("type", "string", "indexName",
                "surname", new ApiLifespan());
        assertTrue("attributes mismatch", o.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testConstructorNullAttributes() {
        final ApiPerson o = new ApiPerson("type", "string", null, "indexName",
                "surname", new ApiLifespan());
        assertTrue("attributes mismatch", o.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testConstructorWithAttributes() {
        final List<ApiAttribute> attributes = new ArrayList<>();
        attributes.add(new ApiAttribute("a string", "attribute", ""));
        final ApiPerson o = new ApiPerson("type", "string", attributes,
                "indexName", "surname", new ApiLifespan());
        assertEquals("attributes mismatch", 1, o.getAttributes().size());
    }

    /** */
    @Test
    public void testIsType() {
        final ApiPerson o = new ApiPerson("type", "string", "indexName",
                "surname", new ApiLifespan());
        assertTrue("type mismatch", o.isType("type"));
    }

    /** */
    @Test
    public void testAccept() {
        final ApiPerson o = new ApiPerson("type", "string", "indexName",
                "surname", new ApiLifespan());
        final ApiTestVisitor visitor = new ApiTestVisitor();
        o.accept(visitor);
        assertEquals("Method mismatch", "person", visitor.getMethodCalled());
    }

    /** */
    @Test
    public void testEqualsSame() {
        final ApiPerson expected = new ApiPerson("type", "string", "index name",
                "surname", new ApiLifespan());
        assertEquals("Should match", expected, expected);
    }

    /** */
    @Test
    public void testEqualsSimple() {
        final ApiPerson expected = new ApiPerson("type", "string", "index name",
                "surname", new ApiLifespan());
        final ApiPerson actual = new ApiPerson("type", "string", "index name",
                "surname", new ApiLifespan());
        assertEquals("Should match", expected, actual);
    }

    /** */
    @Test
    public void testNotEqualsClass() {
        final ApiPerson expected = new ApiPerson("type", "string", "index name",
                "surname", new ApiLifespan());
        final ApiNote actual = new ApiNote("type", "string", "tail");
        assertNotEquals("Should not match", expected, actual);
    }

    /** */
    @Test
    public void testEqualsNullIndexName() {
        final ApiPerson expected = new ApiPerson("type", "string", null,
                "surname", new ApiLifespan());
        final ApiPerson actual = new ApiPerson("type", "string", null,
                "surname", new ApiLifespan());
        assertEquals("Should match", expected, actual);
    }

    /** */
    @Test
    public void testNotEqualsNullIndexName() {
        final ApiPerson expected = new ApiPerson("type", "string", null,
                "surname", new ApiLifespan());
        final ApiPerson actual = new ApiPerson("type", "string", "index name",
                "surname", new ApiLifespan());
        assertNotEquals("Should not match", expected, actual);
    }

    /** */
    @Test
    public void testNotEqualsIndexNameNull() {
        final ApiPerson expected = new ApiPerson("type", "string", "index name",
                "surname", new ApiLifespan());
        final ApiPerson actual = new ApiPerson("type", "string", null,
                "surname", new ApiLifespan());
        assertNotEquals("Should not match", expected, actual);
    }

    /** */
    @Test
    public void testEqualsNullSurname() {
        final ApiPerson expected = new ApiPerson("type", "string", "index name",
                null, new ApiLifespan());
        final ApiPerson actual = new ApiPerson("type", "string", "index name",
                null, new ApiLifespan());
        assertEquals("Should match", expected, actual);
    }

    /** */
    @Test
    public void testNotEqualsNullSurname() {
        final ApiPerson expected = new ApiPerson("type", "string", "index name",
                null, new ApiLifespan());
        final ApiPerson actual = new ApiPerson("type", "string", "index name",
                "surname", new ApiLifespan());
        assertNotEquals("Should not match", expected, actual);
    }

    /** */
    @Test
    public void testNotEqualsSurnameNull() {
        final ApiPerson expected = new ApiPerson("type", "string", "index name",
                "surname", new ApiLifespan());
        final ApiPerson actual = new ApiPerson("type", "string", "index name",
                null, new ApiLifespan());
        assertNotEquals("Should not match", expected, actual);
    }

    /** */
    @Test
    public void testEqualsHash() {
        final ApiPerson expected = new ApiPerson("type", "string", "index name",
                "surname", new ApiLifespan());
        final ApiPerson actual = new ApiPerson("type", "string", "index name",
                "surname", new ApiLifespan());
        assertEquals("Should match", expected.hashCode(), actual.hashCode());
    }

    /** */
    @Test
    public void testEqualsNullIndexNameHash() {
        final ApiPerson expected = new ApiPerson("type", "string", null,
                "surname", new ApiLifespan());
        final ApiPerson actual = new ApiPerson("type", "string", null,
                "surname", new ApiLifespan());
        assertEquals("Should match", expected.hashCode(), actual.hashCode());
    }

    /** */
    @Test
    public void testEqualsNullSurnameHash() {
        final ApiPerson expected = new ApiPerson("type", "string", "index name",
                null, new ApiLifespan());
        final ApiPerson actual = new ApiPerson("type", "string", "index name",
                null, new ApiLifespan());
        assertEquals("Should match", expected.hashCode(), actual.hashCode());
    }

    /** */
    @Test
    public void testNotEqualsNullSurnameHash() {
        final ApiPerson expected = new ApiPerson("type", "string", "index name",
                null, new ApiLifespan());
        final ApiPerson actual = new ApiPerson("type", "string", "index name",
                "surname", new ApiLifespan());
        assertNotEquals("Should not match", expected.hashCode(),
                actual.hashCode());
    }

    /** */
    @Test
    public void testNotEqualsSurnameNullHash() {
        final ApiPerson expected = new ApiPerson("type", "string", "index name",
                "surname", new ApiLifespan());
        final ApiPerson actual = new ApiPerson("type", "string", "index name",
                null, new ApiLifespan());
        assertNotEquals("Should not match", expected.hashCode(),
                actual.hashCode());
    }
}
