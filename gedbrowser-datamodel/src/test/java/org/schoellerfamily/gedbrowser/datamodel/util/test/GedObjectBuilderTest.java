package org.schoellerfamily.gedbrowser.datamodel.util.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.SubmitterLink;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.datamodel.Wife;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GetDateVisitor;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.ExcessivePublicCount", "PMD.GodClass",
        "PMD.TooManyStaticImports" })
public final class GedObjectBuilderTest {
    /** */
    private GedObjectBuilder builder;

    /** */
    @Before
    public void init() {
        builder = new GedObjectBuilder();
    }

    // TODO there might be more valid checks of the behaviors of the creators.
    // Check name and ID on person
    // Check type and date on events
    /** */
    @Test
    public void testPersonWithNull() {
        final Person person = builder.createPerson(null);
        assertFalse("Should create empty person", person.isSet());
    }

    /** */
    @Test
    public void testPersonWithNulls() {
        final Person person = builder.createPerson(null,
                null);
        assertFalse("Should create empty person", person.isSet());
    }

    /** */
    @Test
    public void testPersonWithNullId() {
        final Person person = builder.createPerson(null,
                "Name/Me/");
        assertFalse("Should create empty person", person.isSet());
    }

    /** */
    @Test
    public void testPersonWithNullName() {
        final Person person = builder.createPerson("I1",
                null);
        assertFalse("Should create empty person", person.isSet());
    }

    /** */
    @Test
    public void testPerson() {
        final Person person = builder.createPerson("I1",
                "Name/Me/");
        assertTrue("Should create real person", person.isSet());
    }

    /** */
    @Test
    public void testPerson1() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        assertEquals("Should create real person", "I1", person.getString());
    }

    /** */
    @Test
    public void testPerson2() {
        final Person person = builder.createPerson("I2",
                "Anonymous/Schoeller/");
        assertEquals("Should create real person", "I2", person.getString());
    }

    /** */
    @Test
    public void testPerson3() {
        final Person person = builder.createPerson("I3",
                "Anonymous/Jones/");
        assertEquals("Should create real person", "I3", person.getString());
    }

    /** */
    @Test
    public void testPerson4() {
        final Person person = builder.createPerson("I4",
                "Too Tall/Jones/");
        assertEquals("Should create real person", "I4", person.getString());
    }

    /** */
    @Test
    public void testPerson5() {
        final Person person = builder.createPerson("I5",
                "Anonyma/Schoeller/");
        assertEquals("Should create real person", "I5", person.getString());
    }

    /** */
    @Test
    public void testPerson1Name() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        assertEquals("Should create real person", "J. Random/Schoeller/",
                person.getName().getString());
    }

    /** */
    @Test
    public void testPerson2Name() {
        final Person person = builder.createPerson("I2",
                "Anonymous/Schoeller/");
        assertEquals("Should create real person", "Anonymous/Schoeller/",
                person.getName().getString());
    }

    /** */
    @Test
    public void testPerson3Name() {
        final Person person = builder.createPerson("I3",
                "Anonymous/Jones/");
        assertEquals("Should create real person", "Anonymous/Jones/",
                person.getName().getString());
    }

    /** */
    @Test
    public void testPerson4Name() {
        final Person person = builder.createPerson("I4",
                "Too Tall/Jones/");
        assertEquals("Should create real person", "Too Tall/Jones/",
                person.getName().getString());
    }

    /** */
    @Test
    public void testPerson5Name() {
        final Person person = builder.createPerson("I5",
                "Anonyma/Schoeller/");
        assertEquals("Should create real person", "Anonyma/Schoeller/",
                person.getName().getString());
    }

    /** */
    @Test
    public void testPersonEventWithNulls() {
        final Attribute event = builder
                .createPersonEvent(null, null);
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testPersonEventWithNullPerson() {
        final Attribute event = builder
                .createPersonEvent(null, "Birth", "10 NOV 2000");
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testPersonEventWithNullType() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder
                .createPersonEvent(person, null, "10 NOV 2000");
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testPersonEventWithNullDate() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder
                .createPersonEvent(person, "Birth");
        final GetDateVisitor visitor = new GetDateVisitor();
        event.accept(visitor);
        assertTrue("Should create undated event", visitor.getDate().isEmpty());
    }

    /** */
    @Test
    public void testPersonEventWithBogusDate() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder
                .createPersonEvent(person, "Birth", "HUH?");
        final GetDateVisitor visitor = new GetDateVisitor();
        event.accept(visitor);
        assertEquals("Should create event with this date string", "HUH?",
                visitor.getDate());
    }

    /** */
    @Test
    public void testPersonEvent() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder
                .createPersonEvent(person, "Birth", "10 NOV 2000");
        assertTrue("Should create real event", event.isSet());
    }

    /** */
    @Test
    public void testFamilyWithNull() {
        final Family family = builder.createFamily(null);
        assertFalse("Should create empty family", family.isSet());
    }

    /** */
    @Test
    public void testFamilyWithId() {
        final Family family = builder.createFamily("F1");
        assertTrue("Should create set family",
                family.isSet() && "F1".equals(family.getString()));
    }

    /** */
    @Test
    public void testFamilyWithIdFind() {
        final Root root = new Root();
        final GedObjectBuilder builder1 = new GedObjectBuilder(root);
        final Family family = builder1.createFamily("F1");
        assertEquals("Should have found matching family", family,
                root.find("F1", Family.class));
    }

    /** */
    @Test
    public void testFamily1() {
        final Family family = builder.createFamily("F1");
        assertTrue("Should create set family",
                family.isSet() && "F1".equals(family.getString()));
    }

    /** */
    @Test
    public void testFamily2() {
        final Family family = builder.createFamily("F2");
        assertTrue("Should create set family",
                family.isSet() && "F2".equals(family.getString()));
    }

    /** */
    @Test
    public void testFamily3() {
        final Family family = builder.createFamily("F3");
        assertTrue("Should create set family",
                family.isSet() && "F3".equals(family.getString()));
    }

    /** */
    @Test
    public void testFamilyEventWithNulls() {
        final Attribute event = builder.createFamilyEvent(
                null, null);
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testFamilyEventWithNullFamily() {
        final Attribute event = builder.createFamilyEvent(
                null, "Birth", "10 NOV 2000");
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testFamilyEventWithNullType() {
        final Family family = builder.createFamily("F1");
        final Attribute event = builder.createFamilyEvent(
                family, null, "10 NOV 2000");
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testFamilyEventWithNullDate() {
        final Family family = builder.createFamily("F1");
        final Attribute event = builder.createFamilyEvent(
                family, "Marriage");
        final GetDateVisitor visitor = new GetDateVisitor();
        event.accept(visitor);
        assertTrue("Should create undated event", visitor.getDate().isEmpty());
    }

    /** */
    @Test
    public void testFamilyEventWithBogusDate() {
        final Family family = builder.createFamily("F1");
        final Attribute event = builder.createFamilyEvent(
                family, "Marriage", "HUH?");
        final GetDateVisitor visitor = new GetDateVisitor();
        event.accept(visitor);
        assertEquals("Should create event with this date string", "HUH?",
                visitor.getDate());
    }

    /** */
    @Test
    public void testFamilyEvent() {
        final Family family = builder.createFamily("F1");
        final Attribute event = builder.createFamilyEvent(
                family, "Marriage", "10 NOV 2000");
        assertTrue("Should create real event", event.isSet());
    }

    /** */
    @Test
    public void testAddHusbandToFamily() {
        final Family family = builder.createFamily("F1");
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Husband husband = builder.addHusbandToFamily(
                family, person);
        assertEquals("Should match", husband, family.getAttributes().get(0));
    }

    /** */
    @Test
    public void testAddNullHusbandToFamilyGet() {
        final Family family = builder.createFamily("F1");
        final Husband husband =
                builder.addHusbandToFamily(family, null);
        assertTrue("Empty husband should not be in family",
                !husband.isSet() && family.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testAddNullHusbandToFamily() {
        final Family family = builder.createFamily("F1");
        final Husband husband =
                builder.addHusbandToFamily(family, null);
        assertFalse("Should not be set", husband.isSet());
    }

    /** */
    @Test
    public void testAddHusbandToNullFamily() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Husband husband =
                builder.addHusbandToFamily(null, person);
        assertFalse("Should not be set", husband.isSet());
    }

    /** */
    @Test
    public void testAddWifeToFamily() {
        final Family family = builder.createFamily("F1");
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Wife wife =
                builder.addWifeToFamily(family, person);
        assertEquals("Should match", wife, family.getAttributes().get(0));
    }

    /** */
    @Test
    public void testAddNullWifeToFamilyGet() {
        final Family family = builder.createFamily("F1");
        final Wife wife =
                builder.addWifeToFamily(family, null);
        assertTrue("Empty wife should not be in family",
                !wife.isSet() && family.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testAddNullWifeToFamily() {
        final Family family = builder.createFamily("F1");
        final Wife wife =
                builder.addWifeToFamily(family, null);
        assertFalse("Should not be set", wife.isSet());
    }

    /** */
    @Test
    public void testAddWifeToNullFamily() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Wife wife =
                builder.addWifeToFamily(null, person);
        assertFalse("Should not be set", wife.isSet());
    }

    /** */
    @Test
    public void testAddChildToFamily() {
        final Family family = builder.createFamily("F1");
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Child child =
                builder.addChildToFamily(family, person);
        assertEquals("Should match", child, family.getAttributes().get(0));
    }

    /** */
    @Test
    public void testAddNullChildToFamilyGet() {
        final Family family = builder.createFamily("F1");
        final Child child =
                builder.addChildToFamily(family, null);
        assertTrue("Empty child should not be in family",
                !child.isSet() && family.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testAddNullChildToFamily() {
        final Family family = builder.createFamily("F1");
        final Child child =
                builder.addChildToFamily(family, null);
        assertFalse("Should not be set", child.isSet());
    }

    /** */
    @Test
    public void testAddChildToNullFamily() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Child child =
                builder.addChildToFamily(null, person);
        assertFalse("Should not be set", child.isSet());
    }

    /** */
    @Test
    public void testSubmitterWithNulls() {
        final Submitter submitter = builder.createSubmitter(null, null);
        assertTrue("Should create empty submitter",
                submitter.getString().isEmpty()
                        && submitter.getName().getString().isEmpty());
    }

    /** */
    @Test
    public void testSubmitterWithNullId() {
        final Submitter submitter = builder.createSubmitter(null, "Name/Me/");
        assertTrue("Should create empty submitter",
                submitter.getString().isEmpty()
                        && submitter.getName().getString().isEmpty());
    }

    /** */
    @Test
    public void testSubmitterWithNullName() {
        final Submitter submitter = builder.createSubmitter("SUBM1", null);
        assertTrue("Should create empty submitter",
                submitter.getString().isEmpty()
                        && submitter.getName().getString().isEmpty());
    }

    /** */
    @Test
    public void testSubmitterWithBothGetString() {
        final Submitter submitter = builder.createSubmitter("SUBM1",
                "Name/Me/");
        assertEquals("Should create real submitter", "SUBM1",
                submitter.getString());
    }

    /** */
    @Test
    public void testSubmitterWithBothGetName() {
        final Submitter submitter = builder.createSubmitter("SUBM1",
                "Name/Me/");
        assertEquals("Should create real submitter", "Name/Me/",
                submitter.getName().getString());
    }

    /** */
    @Test
    public void testOneArgSubmitterGetString() {
        final Submitter submitter = builder.createSubmitter("SUBM1");
        assertEquals("Should create submitter with ID", "SUBM1",
                submitter.getString());
    }

    /** */
    @Test
    public void testOneArgSubmitterGetName() {
        final Submitter submitter = builder.createSubmitter("SUBM1");
        assertTrue("Should create submitter without name",
                submitter.getName().getString().isEmpty());
    }

    /** */
    @Test
    public void testOneArgSubmitterNullGetString() {
        final Submitter submitter = builder.createSubmitter(null);
        assertTrue("Should create empty submitter",
                submitter.getString().isEmpty());
    }

    /** */
    @Test
    public void testOneArgSubmitterEmptyStringGetString() {
        final Submitter submitter = builder.createSubmitter(null);
        assertTrue("Should create empty submitter",
                submitter.getString().isEmpty());
    }

    /** */
    @Test
    public void testCreateHead() {
        final Head head = builder.createHead();
        assertEquals("String should be the default", "Head", head.getString());
    }

    /** */
    @Test
    public void testCreateTrailer() {
        final Trailer trailer = builder.createTrailer();
        assertEquals("String should be the default", "Trailer",
                trailer.getString());
    }

    /** */
    @Test
    public void testBeforeAddNameToPerson() {
        final Person person = builder.createPerson("I1");
        final Name name = person.getName();
        assertTrue("Name should not be set", name.getString().isEmpty());
    }

    /** */
    @Test
    public void testAddNameToPerson() {
        final Person person = builder.createPerson("I1");
        builder.addNameToPerson(person, "Foo/Bar/");
        final Name name = person.getName();
        assertEquals("Name should be set", "Foo/Bar/", name.getString());
    }

    /** */
    @Test
    public void testAddNameToNullPerson() {
        final Name name = builder.addNameToPerson(null,
                "Foo/Bar/");
        assertFalse("Name should not be set", name.isSet());
    }

    /** */
    @Test
    public void testAddNullNameToPerson() {
        final Person person = builder.createPerson("I1");
        final Name name = builder.addNameToPerson(person,
                null);
        assertFalse("Name should not be set", name.isSet());
    }

    /** */
    @Test
    public void testAddNullNameToPersonNull() {
        final Person person = builder.createPerson("I1");
        builder.addNameToPerson(person, null);
        assertTrue("Name should not be set",
                person.getName().getString().isEmpty());
    }

    /** */
    @Test
    public void testAddNullNameToPersonNotSame() {
        final Person person = builder.createPerson("I1");
        final Name name = builder.addNameToPerson(person,
                null);
        assertNotSame("Name should not be in person", name, person.getName());
    }

    /** */
    @Test
    public void testNameToPersonWithNulls() {
        final Name name = builder.addNameToPerson(null,
                null);
        assertTrue("Name should not be set", name.getString().isEmpty());
    }

    /** */
    @Test
    public void testAddPlaceToEvent() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder
                .createPersonEvent(person, "Birth", "10 NOV 2000");
        final Place place = builder.addPlaceToEvent(event, "Boston, MA");
        assertEquals("Should have place", place, getPlace(event));
    }

    /** */
    @Test
    public void testBeforeAddPlaceToEvent() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder
                .createPersonEvent(person, "Birth", "10 NOV 2000");
        assertNull("Should not have place", getPlace(event));
    }

    /** */
    @Test
    public void testAddPlaceToEventCheckName() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder
                .createPersonEvent(person, "Birth", "10 NOV 2000");
        builder.addPlaceToEvent(event, "Boston, MA");
        assertEquals("Should have place", "Boston, MA",
                getPlace(event).getString());
    }

    /** */
    @Test
    public void testMultimediaWithNulls() {
        final Multimedia mm =
                builder.addMultimediaToPerson(null, null);
        assertTrue("Should create empty mm",
                mm.getParent() == null && mm.getString().isEmpty());
    }

    /** */
    @Test
    public void testMultimediaWithNullPerson() {
        final Multimedia mm =
                builder.addMultimediaToPerson(null, "Foo");
        assertTrue("Should create empty mm",
                mm.getParent() == null && mm.getString().isEmpty());
    }

    /** */
    @Test
    public void testMultimediaWithNullType() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Multimedia mm =
                builder.addMultimediaToPerson(person, null);
        assertTrue("Should create empty mm",
                mm.getParent() == null && mm.getString().isEmpty());
    }

    /** */
    @Test
    public void testMultimediaSet() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Multimedia mm =
                builder.addMultimediaToPerson(
                        person, "Foo");
        assertTrue("Should create real mm", mm.isSet());
    }

    /** */
    @Test
    public void testMultimediaTail() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Multimedia mm =
                builder.addMultimediaToPerson(
                        person, "Foo");
        assertEquals("Should create real mm", "Foo", mm.getTail());
    }

    /** */
    @Test
    public void testMultimedia() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Multimedia mm =
                builder.addMultimediaToPerson(
                        person, "Foo");
        assertEquals("Should create real mm", "Multimedia", mm.getString());
    }

    /** */
    @Test
    public void testCreateSource1() {
        final Source source = builder.createSource("S1");
        assertEquals("Should be S1", "S1", source.getString());
    }

    /** */
    @Test
    public void testBeforeAddDateToEvent() {
        final Source source = builder.createSource("S1");
        assertNull("Should be undated", getDate(source));
    }

    /** */
    @Test
    public void testAddDateToEvent() {
        final Source source = builder.createSource("S1");
        builder.addDateToGedObject(source, "30 JAN 2017");
        assertEquals("Should match date", new Date(source, "30 JAN 2017"),
                getDate(source));
    }

    /** */
    @Test
    public void testCreateSourceLinkWithNulls() {
        final SourceLink sl = builder.createSourceLink(null, null);
        assertTrue("Should be empty",
                sl.getParent() == null && sl.getString().isEmpty()
                        && sl.getFromString().isEmpty()
                        && sl.getToString().isEmpty());
    }

    /** */
    @Test
    public void testCreateSourceLinkWithNullGedObject() {
        final Source source = builder.createSource("S1");
        final SourceLink sl = builder.createSourceLink(null, source);
        assertTrue("Should be empty",
                sl.getParent() == null && sl.getString().isEmpty()
                        && sl.getFromString().isEmpty()
                        && sl.getToString().isEmpty());
    }

    /** */
    @Test
    public void testCreateSourceLinkWithNullSource() {
        final Person person = builder.createPerson("I1",
                "Who/Me/");
        final Attribute birth = builder
                .createPersonEvent(person, "Birth");
        final SourceLink sl = builder.createSourceLink(birth, null);
        assertTrue("Should be empty",
                sl.getParent() == null && sl.getString().isEmpty()
                        && sl.getFromString().isEmpty()
                        && sl.getToString().isEmpty());
    }

    /** */
    @Test
    public void testCreateSourceLink() {
        final Person person = builder.createPerson("I1",
                "Who/Me/");
        final Attribute birth = builder
                .createPersonEvent(person, "Birth");
        final Source source = builder.createSource("S1");
        final SourceLink sl = builder.createSourceLink(birth, source);
        assertTrue(
                "Should be filled in with Source, S1 and Birth as string,"
                        + " toString and fromString",
                sl.getParent() == birth && "Source".equals(sl.getString())
                        && "S1".equals(sl.getToString())
                        && "Birth".equals(sl.getFromString()));
    }

    /** */
    @Test
    public void testCreateSubmitterLinkWithNulls() {
        final SubmitterLink sl = builder.createSubmitterLink(null, null);
        assertTrue("Should be empty",
                sl.getParent() == null && sl.getString().isEmpty()
                        && sl.getFromString().isEmpty()
                        && sl.getToString().isEmpty());
    }

    /** */
    @Test
    public void testCreateSubmitterLinkWithNullGedObject() {
        final Submitter submitter = builder.createSubmitter("SUBM1");
        final SubmitterLink sl = builder.createSubmitterLink(null, submitter);
        assertTrue("Should be empty",
                sl.getParent() == null && sl.getString().isEmpty()
                        && sl.getFromString().isEmpty()
                        && sl.getToString().isEmpty());
    }

    /** */
    @Test
    public void testCreateSubmitterLinkWithNullSubmitter() {
        final Head head = builder.createHead();
        final SubmitterLink sl = builder.createSubmitterLink(head, null);
        assertTrue("Should be empty",
                sl.getParent() == null && sl.getString().isEmpty()
                        && sl.getFromString().isEmpty()
                        && sl.getToString().isEmpty());
    }

    /** */
    @Test
    public void testCreateSubmitterLink() {
        final Head head = builder.createHead();
        final Submitter submitter = builder.createSubmitter("SUBM1");
        final SubmitterLink sl = builder.createSubmitterLink(head, submitter);
        assertTrue(
                "Should be filled in with Submitter, S1 and Head as string,"
                        + " toString and fromString",
                sl.getParent() == head && "Submitter".equals(sl.getString())
                        && "SUBM1".equals(sl.getToString())
                        && "Head".equals(sl.getFromString()));
    }

    /** */
    @Test
    public void testTwoArgCreateAttributeWithNulls() {
        final Attribute event = builder.createAttribute(null, null);
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testCreateAttributeWithNullParent() {
        final Attribute event = builder.createAttribute(null, "Birth");
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testCreateAttributeWithNullType() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder.createAttribute(person, null);
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testCreateAttribute() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder.createAttribute(person, "Birth");
        assertTrue("Should create real event", event.isSet());
    }

    /** */
    @Test
    public void testThreeArgCreateAttributeWithNulls() {
        final Attribute event = builder.createAttribute(null, null, null);
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testThreeArgCreateAttributeWithNullParent() {
        final Attribute event = builder.createAttribute(null, "Birth", "Tail");
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testThreeArgCreateAttributeWithNullParentAndType() {
        final Attribute event = builder.createAttribute(null, null, "Tail");
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testThreeArgCreateAttributeWithNullTypeAndTail() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder.createAttribute(person, null, null);
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testThreeArgCreateAttributeWithNullType() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder.createAttribute(person, null, "Tail");
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testThreeArgCreateAttributeWithNullTail() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder.createAttribute(person, "Birth", null);
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testThreeArgCreateAttribute() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder.createAttribute(person, "Birth",
                "Tail");
        assertTrue("Should create real event",
                event.isSet() && person.equals(event.getParent())
                        && "Birth".equals(event.getString())
                        && "Tail".equals(event.getTail()));
    }

    // TODO methods left to test
    // createAttribute(ged, string)
    // createAttribute(ged, string, tail)

    /**
     * @param event the event to fish in
     * @return the place if found
     */
    private Place getPlace(final Attribute event) {
        for (final GedObject ged : event.getAttributes()) {
            if (ged instanceof Place) {
                return (Place) ged;
            }
        }
        return null;
    }

    /**
     * @param gob the object to fish in
     * @return the date if found
     */
    private Date getDate(final GedObject gob) {
        for (final GedObject ged : gob.getAttributes()) {
            if (ged instanceof Date) {
                return (Date) ged;
            }
        }
        return null;
    }
}
