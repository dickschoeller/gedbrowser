package org.schoellerfamily.gedbrowser.datamodel.util.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
public class GedObjectBuilderTest {
    // TODO there might be more valid checks of the behaviors of the creators.
    // Check name and ID on person
    // Check type and date on events
    /** */
    @Test
    public void testWithNulls() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson(null, null);
        assertFalse("Should create empty person", person.isSet());
    }

    /** */
    @Test
    public void testPersonWithNullId() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson(null, "Name/Me/");
        assertFalse("Should create empty person", person.isSet());
    }

    /** */
    @Test
    public void testPersonWithNullName() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson("I1", null);
        assertFalse("Should create empty person", person.isSet());
    }

    /** */
    @Test
    public void testPerson() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson("I1", "Name/Me/");
        assertTrue("Should create real person", person.isSet());
    }

    /** */
    @Test
    public void testPerson1() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        assertEquals("Should create real person",
                "I1", person.getString());
    }

    /** */
    @Test
    public void testPerson2() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson2();
        assertEquals("Should create real person",
                "I2", person.getString());
    }

    /** */
    @Test
    public void testPerson3() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson3();
        assertEquals("Should create real person",
                "I3", person.getString());
    }

    /** */
    @Test
    public void testPerson4() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson4();
        assertEquals("Should create real person",
                "I4", person.getString());
    }

    /** */
    @Test
    public void testPerson5() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson5();
        assertEquals("Should create real person",
                "I5", person.getString());
    }

    /** */
    @Test
    public void testPerson1Name() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        assertEquals("Should create real person",
                "J. Random/Schoeller/", person.getName().getString());
    }

    /** */
    @Test
    public void testPerson2Name() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson2();
        assertEquals("Should create real person",
                "Anonymous/Schoeller/", person.getName().getString());
    }

    /** */
    @Test
    public void testPerson3Name() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson3();
        assertEquals("Should create real person",
                "Anonymous/Jones/", person.getName().getString());
    }

    /** */
    @Test
    public void testPerson4Name() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson4();
        assertEquals("Should create real person",
                "Too Tall/Jones/", person.getName().getString());
    }

    /** */
    @Test
    public void testPerson5Name() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson5();
        assertEquals("Should create real person",
                "Anonyma/Schoeller/", person.getName().getString());
    }

    /** */
    @Test
    public void testPersonEventWithNulls() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Attribute event = builder.createPersonEvent(null, null);
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testPersonEventWithNullPerson() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Attribute event =
                builder.createPersonEvent(null, "Birth", "10 NOV 2000");
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testPersonEventWithNullType() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        final Attribute event =
                builder.createPersonEvent(person, null, "10 NOV 2000");
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testPersonEventWithNullDate() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        final Attribute event =
                builder.createPersonEvent(person, "Birth");
        assertTrue("Should create undated event", event.getDate().isEmpty());
    }

    /** */
    @Test
    public void testPersonEventWithBogusDate() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        final Attribute event =
                builder.createPersonEvent(person, "Birth",  "HUH?");
        assertEquals("Should create event with this date string",
                "HUH?", event.getDate());
    }

    /** */
    @Test
    public void testPersonEvent() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        final Attribute event =
                builder.createPersonEvent(person, "Birth",  "10 NOV 2000");
        assertTrue("Should create real event", event.isSet());
    }
}
