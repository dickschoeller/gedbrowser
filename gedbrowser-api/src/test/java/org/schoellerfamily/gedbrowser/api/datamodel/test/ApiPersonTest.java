package org.schoellerfamily.gedbrowser.api.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
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
        final ApiPerson o = createPerson();
        assertEquals("type mismatch", "type", o.getType());
    }

    /** */
    @Test
    public void testConstructorString() {
        final ApiPerson o = createPerson();
        assertEquals("string mismatch", "string", o.getString());
    }

    /** */
    @Test
    public void testConstructorIndexName() {
        final ApiPerson o = createPerson();
        assertEquals("indexname mismatch", "index name", o.getIndexName());
    }

    /** */
    @Test
    public void testConstructorSurname() {
        final ApiPerson o = createPerson();
        assertEquals("surname mismatch", "surname", o.getSurname());
    }

    /** */
    @Test
    public void testConstructorNoAttributes() {
        final ApiPerson o = createPerson();
        assertTrue("attributes mismatch", o.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testConstructorWithAttributes() {
        final ApiPerson.Builder builder = new ApiPerson.Builder()
                .id("string")
                .add(new ApiAttribute("a string", "attribute", ""))
                .build();
        final ApiPerson o = new ApiPerson(builder);
        assertEquals("attributes mismatch", 1, o.getAttributes().size());
    }

    /** */
    @Test
    public void testIsType() {
        final ApiPerson.Builder builder = new ApiPerson.Builder()
                .id("string")
                .add(new ApiAttribute("a string", "attribute", ""))
                .build();
        final ApiPerson o = new ApiPerson(builder);
        assertTrue("type mismatch", o.isType("person"));
    }

    /** */
    @Test
    public void testAccept() {
        final ApiPerson o = createPerson();
        final ApiTestVisitor visitor = new ApiTestVisitor();
        o.accept(visitor);
        assertEquals("Method mismatch", "person", visitor.getMethodCalled());
    }

    /** */
    @Test
    public void testEqualsSame() {
        final ApiPerson expected = createPerson();
        assertEquals("Should match", expected, expected);
    }

    /** */
    @Test
    public void testEqualsSimple() {
        final ApiPerson expected = createPerson();
        final ApiPerson actual = createPerson();
        assertEquals("Should match", expected, actual);
    }

    /** */
    @Test
    public void testNotEqualsClass() {
        final ApiPerson expected = createPerson();
        final ApiNote actual = new ApiNote("type", "string", "tail");
        assertNotEquals("Should not match", expected, actual);
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
        assertEquals("Should match", expected, actual);
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
        assertNotEquals("Should not match", expected, actual);
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
        assertNotEquals("Should not match", expected, actual);
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
        assertEquals("Should match", expected, actual);
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
        assertNotEquals("Should not match", expected, actual);
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
        assertNotEquals("Should not match", expected, actual);
    }

    /** */
    @Test
    public void testEqualsHash() {
        final ApiPerson expected = createPerson();
        final ApiPerson actual = createPerson();
        assertEquals("Should match", expected.hashCode(), actual.hashCode());
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
        assertEquals("Should match", expected.hashCode(), actual.hashCode());
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
        assertEquals("Should match", expected.hashCode(), actual.hashCode());
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
        assertNotEquals("Should not match", expected.hashCode(),
                actual.hashCode());
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
        assertNotEquals("Should not match", expected.hashCode(),
                actual.hashCode());
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
}
