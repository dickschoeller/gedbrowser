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
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.datamodel.SubmittorLink;
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
        final Person person = builder.getPersonBuilder().createPerson(null);
        assertFalse("Should create empty person", person.isSet());
    }

    /** */
    @Test
    public void testPersonWithNulls() {
        final Person person = builder.getPersonBuilder().createPerson(null,
                null);
        assertFalse("Should create empty person", person.isSet());
    }

    /** */
    @Test
    public void testPersonWithNullId() {
        final Person person = builder.getPersonBuilder().createPerson(null,
                "Name/Me/");
        assertFalse("Should create empty person", person.isSet());
    }

    /** */
    @Test
    public void testPersonWithNullName() {
        final Person person = builder.getPersonBuilder().createPerson("I1",
                null);
        assertFalse("Should create empty person", person.isSet());
    }

    /** */
    @Test
    public void testPerson() {
        final Person person = builder.getPersonBuilder().createPerson("I1",
                "Name/Me/");
        assertTrue("Should create real person", person.isSet());
    }

    /** */
    @Test
    public void testPerson1() {
        final Person person = builder.getPersonBuilder().createPerson("I1",
                "J. Random/Schoeller/");
        assertEquals("Should create real person", "I1", person.getString());
    }

    /** */
    @Test
    public void testPerson2() {
        final Person person = builder.getPersonBuilder().createPerson("I2",
                "Anonymous/Schoeller/");
        assertEquals("Should create real person", "I2", person.getString());
    }

    /** */
    @Test
    public void testPerson3() {
        final Person person = builder.getPersonBuilder().createPerson("I3",
                "Anonymous/Jones/");
        assertEquals("Should create real person", "I3", person.getString());
    }

    /** */
    @Test
    public void testPerson4() {
        final Person person = builder.getPersonBuilder().createPerson("I4",
                "Too Tall/Jones/");
        assertEquals("Should create real person", "I4", person.getString());
    }

    /** */
    @Test
    public void testPerson5() {
        final Person person = builder.getPersonBuilder().createPerson("I5",
                "Anonyma/Schoeller/");
        assertEquals("Should create real person", "I5", person.getString());
    }

    /** */
    @Test
    public void testPerson1Name() {
        final Person person = builder.getPersonBuilder().createPerson("I1",
                "J. Random/Schoeller/");
        assertEquals("Should create real person", "J. Random/Schoeller/",
                person.getName().getString());
    }

    /** */
    @Test
    public void testPerson2Name() {
        final Person person = builder.getPersonBuilder().createPerson("I2",
                "Anonymous/Schoeller/");
        assertEquals("Should create real person", "Anonymous/Schoeller/",
                person.getName().getString());
    }

    /** */
    @Test
    public void testPerson3Name() {
        final Person person = builder.getPersonBuilder().createPerson("I3",
                "Anonymous/Jones/");
        assertEquals("Should create real person", "Anonymous/Jones/",
                person.getName().getString());
    }

    /** */
    @Test
    public void testPerson4Name() {
        final Person person = builder.getPersonBuilder().createPerson("I4",
                "Too Tall/Jones/");
        assertEquals("Should create real person", "Too Tall/Jones/",
                person.getName().getString());
    }

    /** */
    @Test
    public void testPerson5Name() {
        final Person person = builder.getPersonBuilder().createPerson("I5",
                "Anonyma/Schoeller/");
        assertEquals("Should create real person", "Anonyma/Schoeller/",
                person.getName().getString());
    }

    /** */
    @Test
    public void testPersonEventWithNulls() {
        final Attribute event = builder.getPersonBuilder()
                .createPersonEvent(null, null);
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testPersonEventWithNullPerson() {
        final Attribute event = builder.getPersonBuilder()
                .createPersonEvent(null, "Birth", "10 NOV 2000");
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testPersonEventWithNullType() {
        final Person person = builder.getPersonBuilder().createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder.getPersonBuilder()
                .createPersonEvent(person, null, "10 NOV 2000");
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testPersonEventWithNullDate() {
        final Person person = builder.getPersonBuilder().createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder.getPersonBuilder()
                .createPersonEvent(person, "Birth");
        final GetDateVisitor visitor = new GetDateVisitor();
        event.accept(visitor);
        assertTrue("Should create undated event", visitor.getDate().isEmpty());
    }

    /** */
    @Test
    public void testPersonEventWithBogusDate() {
        final Person person = builder.getPersonBuilder().createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder.getPersonBuilder()
                .createPersonEvent(person, "Birth", "HUH?");
        final GetDateVisitor visitor = new GetDateVisitor();
        event.accept(visitor);
        assertEquals("Should create event with this date string", "HUH?",
                visitor.getDate());
    }

    /** */
    @Test
    public void testPersonEvent() {
        final Person person = builder.getPersonBuilder().createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder.getPersonBuilder()
                .createPersonEvent(person, "Birth", "10 NOV 2000");
        assertTrue("Should create real event", event.isSet());
    }

    /** */
    @Test
    public void testFamilyWithNull() {
        final Family family = builder.getFamilyBuilder().createFamily(null);
        assertFalse("Should create empty family", family.isSet());
    }

    /** */
    @Test
    public void testFamilyWithId() {
        final Family family = builder.getFamilyBuilder().createFamily("F1");
        assertTrue("Should create set family",
                family.isSet() && "F1".equals(family.getString()));
    }

    /** */
    @Test
    public void testFamilyWithIdFind() {
        final Root root = new Root();
        final GedObjectBuilder builder1 = new GedObjectBuilder(root);
        final Family family = builder1.getFamilyBuilder().createFamily("F1");
        assertEquals("Should have found matching family", family,
                root.find("F1", Family.class));
    }

    /** */
    @Test
    public void testFamily1() {
        final Family family = builder.getFamilyBuilder().createFamily("F1");
        assertTrue("Should create set family",
                family.isSet() && "F1".equals(family.getString()));
    }

    /** */
    @Test
    public void testFamily2() {
        final Family family = builder.getFamilyBuilder().createFamily("F2");
        assertTrue("Should create set family",
                family.isSet() && "F2".equals(family.getString()));
    }

    /** */
    @Test
    public void testFamily3() {
        final Family family = builder.getFamilyBuilder().createFamily("F3");
        assertTrue("Should create set family",
                family.isSet() && "F3".equals(family.getString()));
    }

    /** */
    @Test
    public void testFamilyEventWithNulls() {
        final Attribute event = builder.getFamilyBuilder().createFamilyEvent(
                null, null);
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testFamilyEventWithNullFamily() {
        final Attribute event = builder.getFamilyBuilder().createFamilyEvent(
                null, "Birth", "10 NOV 2000");
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testFamilyEventWithNullType() {
        final Family family = builder.getFamilyBuilder().createFamily("F1");
        final Attribute event = builder.getFamilyBuilder().createFamilyEvent(
                family, null, "10 NOV 2000");
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testFamilyEventWithNullDate() {
        final Family family = builder.getFamilyBuilder().createFamily("F1");
        final Attribute event = builder.getFamilyBuilder().createFamilyEvent(
                family, "Marriage");
        final GetDateVisitor visitor = new GetDateVisitor();
        event.accept(visitor);
        assertTrue("Should create undated event", visitor.getDate().isEmpty());
    }

    /** */
    @Test
    public void testFamilyEventWithBogusDate() {
        final Family family = builder.getFamilyBuilder().createFamily("F1");
        final Attribute event = builder.getFamilyBuilder().createFamilyEvent(
                family, "Marriage", "HUH?");
        final GetDateVisitor visitor = new GetDateVisitor();
        event.accept(visitor);
        assertEquals("Should create event with this date string", "HUH?",
                visitor.getDate());
    }

    /** */
    @Test
    public void testFamilyEvent() {
        final Family family = builder.getFamilyBuilder().createFamily("F1");
        final Attribute event = builder.getFamilyBuilder().createFamilyEvent(
                family, "Marriage", "10 NOV 2000");
        assertTrue("Should create real event", event.isSet());
    }

    /** */
    @Test
    public void testAddHusbandToFamily() {
        final Family family = builder.getFamilyBuilder().createFamily("F1");
        final Person person = builder.getPersonBuilder().createPerson("I1",
                "J. Random/Schoeller/");
        final Husband husband = builder.getFamilyBuilder().addHusbandToFamily(
                family, person);
        assertEquals("Should match", husband, family.getAttributes().get(0));
    }

    /** */
    @Test
    public void testAddNullHusbandToFamilyGet() {
        final Family family = builder.getFamilyBuilder().createFamily("F1");
        final Husband husband =
                builder.getFamilyBuilder().addHusbandToFamily(family, null);
        assertTrue("Empty husband should not be in family",
                !husband.isSet() && family.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testAddNullHusbandToFamily() {
        final Family family = builder.getFamilyBuilder().createFamily("F1");
        final Husband husband =
                builder.getFamilyBuilder().addHusbandToFamily(family, null);
        assertFalse("Should not be set", husband.isSet());
    }

    /** */
    @Test
    public void testAddHusbandToNullFamily() {
        final Person person = builder.getPersonBuilder().createPerson("I1",
                "J. Random/Schoeller/");
        final Husband husband =
                builder.getFamilyBuilder().addHusbandToFamily(null, person);
        assertFalse("Should not be set", husband.isSet());
    }

    /** */
    @Test
    public void testAddWifeToFamily() {
        final Family family = builder.getFamilyBuilder().createFamily("F1");
        final Person person = builder.getPersonBuilder().createPerson("I1",
                "J. Random/Schoeller/");
        final Wife wife =
                builder.getFamilyBuilder().addWifeToFamily(family, person);
        assertEquals("Should match", wife, family.getAttributes().get(0));
    }

    /** */
    @Test
    public void testAddNullWifeToFamilyGet() {
        final Family family = builder.getFamilyBuilder().createFamily("F1");
        final Wife wife =
                builder.getFamilyBuilder().addWifeToFamily(family, null);
        assertTrue("Empty wife should not be in family",
                !wife.isSet() && family.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testAddNullWifeToFamily() {
        final Family family = builder.getFamilyBuilder().createFamily("F1");
        final Wife wife =
                builder.getFamilyBuilder().addWifeToFamily(family, null);
        assertFalse("Should not be set", wife.isSet());
    }

    /** */
    @Test
    public void testAddWifeToNullFamily() {
        final Person person = builder.getPersonBuilder().createPerson("I1",
                "J. Random/Schoeller/");
        final Wife wife =
                builder.getFamilyBuilder().addWifeToFamily(null, person);
        assertFalse("Should not be set", wife.isSet());
    }

    /** */
    @Test
    public void testAddChildToFamily() {
        final Family family = builder.getFamilyBuilder().createFamily("F1");
        final Person person = builder.getPersonBuilder().createPerson("I1",
                "J. Random/Schoeller/");
        final Child child =
                builder.getFamilyBuilder().addChildToFamily(family, person);
        assertEquals("Should match", child, family.getAttributes().get(0));
    }

    /** */
    @Test
    public void testAddNullChildToFamilyGet() {
        final Family family = builder.getFamilyBuilder().createFamily("F1");
        final Child child =
                builder.getFamilyBuilder().addChildToFamily(family, null);
        assertTrue("Empty child should not be in family",
                !child.isSet() && family.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testAddNullChildToFamily() {
        final Family family = builder.getFamilyBuilder().createFamily("F1");
        final Child child =
                builder.getFamilyBuilder().addChildToFamily(family, null);
        assertFalse("Should not be set", child.isSet());
    }

    /** */
    @Test
    public void testAddChildToNullFamily() {
        final Person person = builder.getPersonBuilder().createPerson("I1",
                "J. Random/Schoeller/");
        final Child child =
                builder.getFamilyBuilder().addChildToFamily(null, person);
        assertFalse("Should not be set", child.isSet());
    }

    /** */
    @Test
    public void testSubmittorWithNulls() {
        final Submittor submittor = builder.createSubmittor(null, null);
        assertTrue("Should create empty submittor",
                submittor.getString().isEmpty()
                        && submittor.getName().getString().isEmpty());
    }

    /** */
    @Test
    public void testSubmittorWithNullId() {
        final Submittor submittor = builder.createSubmittor(null, "Name/Me/");
        assertTrue("Should create empty submittor",
                submittor.getString().isEmpty()
                        && submittor.getName().getString().isEmpty());
    }

    /** */
    @Test
    public void testSubmittorWithNullName() {
        final Submittor submittor = builder.createSubmittor("SUBM1", null);
        assertTrue("Should create empty submittor",
                submittor.getString().isEmpty()
                        && submittor.getName().getString().isEmpty());
    }

    /** */
    @Test
    public void testSubmittorWithBothGetString() {
        final Submittor submittor = builder.createSubmittor("SUBM1",
                "Name/Me/");
        assertEquals("Should create real submittor", "SUBM1",
                submittor.getString());
    }

    /** */
    @Test
    public void testSubmittorWithBothGetName() {
        final Submittor submittor = builder.createSubmittor("SUBM1",
                "Name/Me/");
        assertEquals("Should create real submittor", "Name/Me/",
                submittor.getName().getString());
    }

    /** */
    @Test
    public void testOneArgSubmittorGetString() {
        final Submittor submittor = builder.createSubmittor("SUBM1");
        assertEquals("Should create submittor with ID", "SUBM1",
                submittor.getString());
    }

    /** */
    @Test
    public void testOneArgSubmittorGetName() {
        final Submittor submittor = builder.createSubmittor("SUBM1");
        assertTrue("Should create submittor without name",
                submittor.getName().getString().isEmpty());
    }

    /** */
    @Test
    public void testOneArgSubmittorNullGetString() {
        final Submittor submittor = builder.createSubmittor(null);
        assertTrue("Should create empty submittor",
                submittor.getString().isEmpty());
    }

    /** */
    @Test
    public void testOneArgSubmittorEmptyStringGetString() {
        final Submittor submittor = builder.createSubmittor(null);
        assertTrue("Should create empty submittor",
                submittor.getString().isEmpty());
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
        final Person person = builder.getPersonBuilder().createPerson("I1");
        final Name name = person.getName();
        assertTrue("Name should not be set", name.getString().isEmpty());
    }

    /** */
    @Test
    public void testAddNameToPerson() {
        final Person person = builder.getPersonBuilder().createPerson("I1");
        builder.getPersonBuilder().addNameToPerson(person, "Foo/Bar/");
        final Name name = person.getName();
        assertEquals("Name should be set", "Foo/Bar/", name.getString());
    }

    /** */
    @Test
    public void testAddNameToNullPerson() {
        final Name name = builder.getPersonBuilder().addNameToPerson(null,
                "Foo/Bar/");
        assertFalse("Name should not be set", name.isSet());
    }

    /** */
    @Test
    public void testAddNullNameToPerson() {
        final Person person = builder.getPersonBuilder().createPerson("I1");
        final Name name = builder.getPersonBuilder().addNameToPerson(person,
                null);
        assertFalse("Name should not be set", name.isSet());
    }

    /** */
    @Test
    public void testAddNullNameToPersonNull() {
        final Person person = builder.getPersonBuilder().createPerson("I1");
        builder.getPersonBuilder().addNameToPerson(person, null);
        assertTrue("Name should not be set",
                person.getName().getString().isEmpty());
    }

    /** */
    @Test
    public void testAddNullNameToPersonNotSame() {
        final Person person = builder.getPersonBuilder().createPerson("I1");
        final Name name = builder.getPersonBuilder().addNameToPerson(person,
                null);
        assertNotSame("Name should not be in person", name, person.getName());
    }

    /** */
    @Test
    public void testNameToPersonWithNulls() {
        final Name name = builder.getPersonBuilder().addNameToPerson(null,
                null);
        assertTrue("Name should not be set", name.getString().isEmpty());
    }

    /** */
    @Test
    public void testAddPlaceToEvent() {
        final Person person = builder.getPersonBuilder().createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder.getPersonBuilder()
                .createPersonEvent(person, "Birth", "10 NOV 2000");
        final Place place = builder.addPlaceToEvent(event, "Boston, MA");
        assertEquals("Should have place", place, getPlace(event));
    }

    /** */
    @Test
    public void testBeforeAddPlaceToEvent() {
        final Person person = builder.getPersonBuilder().createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder.getPersonBuilder()
                .createPersonEvent(person, "Birth", "10 NOV 2000");
        assertNull("Should not have place", getPlace(event));
    }

    /** */
    @Test
    public void testAddPlaceToEventCheckName() {
        final Person person = builder.getPersonBuilder().createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder.getPersonBuilder()
                .createPersonEvent(person, "Birth", "10 NOV 2000");
        builder.addPlaceToEvent(event, "Boston, MA");
        assertEquals("Should have place", "Boston, MA",
                getPlace(event).getString());
    }

    /** */
    @Test
    public void testMultimediaWithNulls() {
        final Multimedia mm =
                builder.getPersonBuilder().addMultimediaToPerson(null, null);
        assertTrue("Should create empty mm",
                mm.getParent() == null && mm.getString().isEmpty());
    }

    /** */
    @Test
    public void testMultimediaWithNullPerson() {
        final Multimedia mm =
                builder.getPersonBuilder().addMultimediaToPerson(null, "Foo");
        assertTrue("Should create empty mm",
                mm.getParent() == null && mm.getString().isEmpty());
    }

    /** */
    @Test
    public void testMultimediaWithNullType() {
        final Person person = builder.getPersonBuilder().createPerson("I1",
                "J. Random/Schoeller/");
        final Multimedia mm =
                builder.getPersonBuilder().addMultimediaToPerson(person, null);
        assertTrue("Should create empty mm",
                mm.getParent() == null && mm.getString().isEmpty());
    }

    /** */
    @Test
    public void testMultimediaSet() {
        final Person person = builder.getPersonBuilder().createPerson("I1",
                "J. Random/Schoeller/");
        final Multimedia mm =
                builder.getPersonBuilder().addMultimediaToPerson(
                        person, "Foo");
        assertTrue("Should create real mm", mm.isSet());
    }

    /** */
    @Test
    public void testMultimediaTail() {
        final Person person = builder.getPersonBuilder().createPerson("I1",
                "J. Random/Schoeller/");
        final Multimedia mm =
                builder.getPersonBuilder().addMultimediaToPerson(
                        person, "Foo");
        assertEquals("Should create real mm", "Foo", mm.getTail());
    }

    /** */
    @Test
    public void testMultimedia() {
        final Person person = builder.getPersonBuilder().createPerson("I1",
                "J. Random/Schoeller/");
        final Multimedia mm =
                builder.getPersonBuilder().addMultimediaToPerson(
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
        final Person person = builder.getPersonBuilder().createPerson("I1",
                "Who/Me/");
        final Attribute birth = builder.getPersonBuilder()
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
        final Person person = builder.getPersonBuilder().createPerson("I1",
                "Who/Me/");
        final Attribute birth = builder.getPersonBuilder()
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
    public void testCreateSubmittorLinkWithNulls() {
        final SubmittorLink sl = builder.createSubmittorLink(null, null);
        assertTrue("Should be empty",
                sl.getParent() == null && sl.getString().isEmpty()
                        && sl.getFromString().isEmpty()
                        && sl.getToString().isEmpty());
    }

    /** */
    @Test
    public void testCreateSubmittorLinkWithNullGedObject() {
        final Submittor submittor = builder.createSubmittor("SUBM1");
        final SubmittorLink sl = builder.createSubmittorLink(null, submittor);
        assertTrue("Should be empty",
                sl.getParent() == null && sl.getString().isEmpty()
                        && sl.getFromString().isEmpty()
                        && sl.getToString().isEmpty());
    }

    /** */
    @Test
    public void testCreateSubmittorLinkWithNullSubmittor() {
        final Head head = builder.createHead();
        final SubmittorLink sl = builder.createSubmittorLink(head, null);
        assertTrue("Should be empty",
                sl.getParent() == null && sl.getString().isEmpty()
                        && sl.getFromString().isEmpty()
                        && sl.getToString().isEmpty());
    }

    /** */
    @Test
    public void testCreateSubmittorLink() {
        final Head head = builder.createHead();
        final Submittor submittor = builder.createSubmittor("SUBM1");
        final SubmittorLink sl = builder.createSubmittorLink(head, submittor);
        assertTrue(
                "Should be filled in with Submittor, S1 and Head as string,"
                        + " toString and fromString",
                sl.getParent() == head && "Submittor".equals(sl.getString())
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
        final Person person = builder.getPersonBuilder().createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder.createAttribute(person, null);
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testCreateAttribute() {
        final Person person = builder.getPersonBuilder().createPerson("I1",
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
        final Person person = builder.getPersonBuilder().createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder.createAttribute(person, null, null);
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testThreeArgCreateAttributeWithNullType() {
        final Person person = builder.getPersonBuilder().createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder.createAttribute(person, null, "Tail");
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testThreeArgCreateAttributeWithNullTail() {
        final Person person = builder.getPersonBuilder().createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder.createAttribute(person, "Birth", null);
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testThreeArgCreateAttribute() {
        final Person person = builder.getPersonBuilder().createPerson("I1",
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
