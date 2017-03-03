package org.schoellerfamily.gedbrowser.datamodel.util.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
    // TODO there might be more valid checks of the behaviors of the creators.
    // Check name and ID on person
    // Check type and date on events
    /** */
    @Test
    public void testPersonWithNull() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson(null);
        assertFalse("Should create empty person", person.isSet());
    }

    /** */
    @Test
    public void testPersonWithNulls() {
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
        final GetDateVisitor visitor = new GetDateVisitor();
        event.accept(visitor);
        assertTrue("Should create undated event", visitor.getDate().isEmpty());
    }

    /** */
    @Test
    public void testPersonEventWithBogusDate() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        final Attribute event =
                builder.createPersonEvent(person, "Birth",  "HUH?");
        final GetDateVisitor visitor = new GetDateVisitor();
        event.accept(visitor);
        assertEquals("Should create event with this date string",
                "HUH?", visitor.getDate());
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

    /** */
    @Test
    public void testFamilyWithNull() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily(null);
        assertFalse("Should create empty family", family.isSet());
    }

    /** */
    @Test
    public void testFamilyWithId() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily("F1");
        assertTrue("Should create set family",
                family.isSet() && "F1".equals(family.getString()));
    }

    /** */
    @Test
    public void testFamilyWithIdFind() {
        final Root root = new Root();
        final GedObjectBuilder builder = new GedObjectBuilder(root);
        final Family family = builder.createFamily("F1");
        assertEquals("Should have found matching family",
                family, root.find("F1", Family.class));
    }

    /** */
    @Test
    public void testFamily1() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        assertTrue("Should create set family",
                family.isSet() && "F1".equals(family.getString()));
    }

    /** */
    @Test
    public void testFamily2() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily2();
        assertTrue("Should create set family",
                family.isSet() && "F2".equals(family.getString()));
    }

    /** */
    @Test
    public void testFamily3() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily3();
        assertTrue("Should create set family",
                family.isSet() && "F3".equals(family.getString()));
    }

    /** */
    @Test
    public void testFamilyEventWithNulls() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Attribute event = builder.createFamilyEvent(null, null);
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testFamilyEventWithNullFamily() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Attribute event =
                builder.createFamilyEvent(null, "Birth", "10 NOV 2000");
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testFamilyEventWithNullType() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Attribute event =
                builder.createFamilyEvent(family, null, "10 NOV 2000");
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testFamilyEventWithNullDate() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Attribute event =
                builder.createFamilyEvent(family, "Marriage");
        final GetDateVisitor visitor = new GetDateVisitor();
        event.accept(visitor);
        assertTrue("Should create undated event", visitor.getDate().isEmpty());
    }

    /** */
    @Test
    public void testFamilyEventWithBogusDate() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Attribute event =
                builder.createFamilyEvent(family, "Marriage",  "HUH?");
        final GetDateVisitor visitor = new GetDateVisitor();
        event.accept(visitor);
        assertEquals("Should create event with this date string",
                "HUH?", visitor.getDate());
    }

    /** */
    @Test
    public void testFamilyEvent() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Attribute event =
                builder.createFamilyEvent(family, "Marriage",  "10 NOV 2000");
        assertTrue("Should create real event", event.isSet());
    }

    /** */
    @Test
    public void testAddHusbandToFamily() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Person person = builder.createPerson1();
        final Husband husband = builder.addHusbandToFamily(family, person);
        assertEquals("Should match", husband, family.getAttributes().get(0));
    }

    /** */
    @Test
    public void testAddNullHusbandToFamilyGet() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Husband husband = builder.addHusbandToFamily(family, null);
        assertTrue("Empty husband should not be in family",
                !husband.isSet() && family.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testAddNullHusbandToFamily() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Husband husband = builder.addHusbandToFamily(family, null);
        assertFalse("Should not be set", husband.isSet());
    }

    /** */
    @Test
    public void testAddHusbandToNullFamily() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        final Husband husband = builder.addHusbandToFamily(null, person);
        assertFalse("Should not be set", husband.isSet());
    }

    /** */
    @Test
    public void testAddWifeToFamily() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Person person = builder.createPerson1();
        final Wife wife = builder.addWifeToFamily(family, person);
        assertEquals("Should match", wife, family.getAttributes().get(0));
    }

    /** */
    @Test
    public void testAddNullWifeToFamilyGet() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Wife wife = builder.addWifeToFamily(family, null);
        assertTrue("Empty wife should not be in family",
                !wife.isSet() && family.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testAddNullWifeToFamily() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Wife wife = builder.addWifeToFamily(family, null);
        assertFalse("Should not be set", wife.isSet());
    }

    /** */
    @Test
    public void testAddWifeToNullFamily() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        final Wife wife = builder.addWifeToFamily(null, person);
        assertFalse("Should not be set", wife.isSet());
    }

    /** */
    @Test
    public void testAddChildToFamily() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Person person = builder.createPerson1();
        final Child child = builder.addChildToFamily(family, person);
        assertEquals("Should match", child, family.getAttributes().get(0));
    }

    /** */
    @Test
    public void testAddNullChildToFamilyGet() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Child child = builder.addChildToFamily(family, null);
        assertTrue("Empty child should not be in family",
                !child.isSet() && family.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testAddNullChildToFamily() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Child child = builder.addChildToFamily(family, null);
        assertFalse("Should not be set", child.isSet());
    }

    /** */
    @Test
    public void testAddChildToNullFamily() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        final Child child = builder.addChildToFamily(null, person);
        assertFalse("Should not be set", child.isSet());
    }

    /** */
    @Test
    public void testSubmittorWithNulls() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submittor submittor = builder.createSubmittor(null, null);
        assertTrue("Should create empty submittor",
                submittor.getString().isEmpty()
                && submittor.getName().getString().isEmpty());
    }

    /** */
    @Test
    public void testSubmittorWithNullId() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submittor submittor = builder.createSubmittor(null, "Name/Me/");
        assertTrue("Should create empty submittor",
                submittor.getString().isEmpty()
                && submittor.getName().getString().isEmpty());
    }

    /** */
    @Test
    public void testSubmittorWithNullName() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submittor submittor = builder.createSubmittor("SUBM1", null);
        assertTrue("Should create empty submittor",
                submittor.getString().isEmpty()
                && submittor.getName().getString().isEmpty());
    }

    /** */
    @Test
    public void testSubmittorWithBothGetString() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submittor submittor = builder.createSubmittor("SUBM1",
                "Name/Me/");
        assertEquals("Should create real submittor", "SUBM1",
                submittor.getString());
    }

    /** */
    @Test
    public void testSubmittorWithBothGetName() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submittor submittor = builder.createSubmittor("SUBM1",
                "Name/Me/");
        assertEquals("Should create real submittor", "Name/Me/",
                submittor.getName().getString());
    }

    /** */
    @Test
    public void testOneArgSubmittorGetString() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submittor submittor = builder.createSubmittor("SUBM1");
        assertEquals("Should create submittor with ID", "SUBM1",
                submittor.getString());
    }

    /** */
    @Test
    public void testOneArgSubmittorGetName() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submittor submittor = builder.createSubmittor("SUBM1");
        assertTrue("Should create submittor without name",
                submittor.getName().getString().isEmpty());
    }

    /** */
    @Test
    public void testOneArgSubmittorNullGetString() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submittor submittor = builder.createSubmittor(null);
        assertTrue("Should create empty submittor",
                submittor.getString().isEmpty());
    }

    /** */
    @Test
    public void testOneArgSubmittorEmptyStringGetString() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submittor submittor = builder.createSubmittor(null);
        assertTrue("Should create empty submittor",
                submittor.getString().isEmpty());
    }

    /** */
    @Test
    public void testCreateHead() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Head head = builder.createHead();
        assertEquals("String should be the default",
                "Head", head.getString());
    }

    /** */
    @Test
    public void testCreateTrailer() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Trailer trailer = builder.createTrailer();
        assertEquals("String should be the default",
                "Trailer", trailer.getString());
    }

    /** */
    @Test
    public void testBeforeAddNameToPerson() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson("I1");
        final Name name = person.getName();
        assertTrue("Name should not be set", name.getString().isEmpty());
    }

    /** */
    @Test
    public void testAddNameToPerson() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson("I1");
        builder.addNameToPerson(person, "Foo/Bar/");
        final Name name = person.getName();
        assertEquals("Name should be set", "Foo/Bar/", name.getString());
    }

    /** */
    @Test
    public void testAddNameToNullPerson() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Name name = builder.addNameToPerson(null, "Foo/Bar/");
        assertFalse("Name should not be set", name.isSet());
    }

    /** */
    @Test
    public void testAddNullNameToPerson() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson("I1");
        final Name name = builder.addNameToPerson(person, null);
        assertFalse("Name should not be set", name.isSet());
    }

    /** */
    @Test
    public void testAddNullNameToPersonNull() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson("I1");
        builder.addNameToPerson(person, null);
        assertTrue("Name should not be set",
                person.getName().getString().isEmpty());
    }

    /** */
    @Test
    public void testAddNullNameToPersonNotSame() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson("I1");
        final Name name = builder.addNameToPerson(person, null);
        assertNotSame("Name should not be in person", name, person.getName());
    }

    /** */
    @Test
    public void testNameToPersonWithNulls() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Name name = builder.addNameToPerson(null, null);
        assertTrue("Name should not be set", name.getString().isEmpty());
    }

    /** */
    @Test
    public void testAddPlaceToEvent() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        final Attribute event =
                builder.createPersonEvent(person, "Birth",  "10 NOV 2000");
        final Place place = builder.addPlaceToEvent(event, "Boston, MA");
        assertEquals("Should have place", place, getPlace(event));
    }

    /** */
    @Test
    public void testBeforeAddPlaceToEvent() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        final Attribute event =
                builder.createPersonEvent(person, "Birth",  "10 NOV 2000");
        assertNull("Should not have place", getPlace(event));
    }

    /** */
    @Test
    public void testAddPlaceToEventCheckName() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        final Attribute event =
                builder.createPersonEvent(person, "Birth",  "10 NOV 2000");
        builder.addPlaceToEvent(event, "Boston, MA");
        assertEquals("Should have place", "Boston, MA",
                getPlace(event).getString());
    }

    /** */
    @Test
    public void testMultimediaWithNulls() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Multimedia mm = builder.addMultimediaToPerson(null, null);
        assertTrue("Should create empty mm",
                mm.getParent() == null && mm.getString().isEmpty());
    }

    /** */
    @Test
    public void testMultimediaWithNullPerson() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Multimedia mm =
                builder.addMultimediaToPerson(null, "Foo");
        assertTrue("Should create empty mm",
                mm.getParent() == null && mm.getString().isEmpty());
    }

    /** */
    @Test
    public void testMultimediaWithNullType() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        final Multimedia mm =
                builder.addMultimediaToPerson(person, null);
        assertTrue("Should create empty mm",
                mm.getParent() == null && mm.getString().isEmpty());
    }

    /** */
    @Test
    public void testMultimediaSet() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        final Multimedia mm =
                builder.addMultimediaToPerson(person, "Foo");
        assertTrue("Should create real mm", mm.isSet());
    }

    /** */
    @Test
    public void testMultimediaTail() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        final Multimedia mm =
                builder.addMultimediaToPerson(person, "Foo");
        assertEquals("Should create real mm", "Foo", mm.getTail());
    }

    /** */
    @Test
    public void testMultimedia() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        final Multimedia mm =
                builder.addMultimediaToPerson(person, "Foo");
        assertEquals("Should create real mm", "Multimedia", mm.getString());
    }

    /** */
    @Test
    public void testCreateSource1() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Source source = builder.createSource1();
        assertEquals("Should be S1", "S1", source.getString());
    }

    /** */
    @Test
    public void testBeforeAddDateToEvent() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Source source = builder.createSource1();
        assertNull("Should be undated", getDate(source));
    }

    /** */
    @Test
    public void testAddDateToEvent() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Source source = builder.createSource1();
        builder.addDateToGedObject(source, "30 JAN 2017");
        assertEquals("Should match date", new Date(source, "30 JAN 2017"),
                getDate(source));
    }

    /** */
    @Test
    public void testCreateSourceLinkWithNulls() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final SourceLink sl = builder.createSourceLink(null, null);
        assertTrue("Should be empty",
                sl.getParent() == null
                && sl.getString().isEmpty()
                && sl.getFromString().isEmpty()
                && sl.getToString().isEmpty());
    }

    /** */
    @Test
    public void testCreateSourceLinkWithNullGedObject() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Source source = builder.createSource("S1");
        final SourceLink sl = builder.createSourceLink(null, source);
        assertTrue("Should be empty",
                sl.getParent() == null
                && sl.getString().isEmpty()
                && sl.getFromString().isEmpty()
                && sl.getToString().isEmpty());
    }

    /** */
    @Test
    public void testCreateSourceLinkWithNullSource() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson("I1", "Who/Me/");
        final Attribute birth = builder.createPersonEvent(person, "Birth");
        final SourceLink sl = builder.createSourceLink(birth, null);
        assertTrue("Should be empty",
                sl.getParent() == null
                && sl.getString().isEmpty()
                && sl.getFromString().isEmpty()
                && sl.getToString().isEmpty());
    }

    /** */
    @Test
    public void testCreateSourceLink() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson("I1", "Who/Me/");
        final Attribute birth = builder.createPersonEvent(person, "Birth");
        final Source source = builder.createSource("S1");
        final SourceLink sl = builder.createSourceLink(birth, source);
        assertTrue("Should be filled in with Source, S1 and Birth as string,"
                + " toString and fromString",
                sl.getParent() == birth
                && "Source".equals(sl.getString())
                && "S1".equals(sl.getToString())
                && "Birth".equals(sl.getFromString()));
    }

    /** */
    @Test
    public void testCreateSubmittorLinkWithNulls() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final SubmittorLink sl = builder.createSubmittorLink(null, null);
        assertTrue("Should be empty",
                sl.getParent() == null
                && sl.getString().isEmpty()
                && sl.getFromString().isEmpty()
                && sl.getToString().isEmpty());
    }

    /** */
    @Test
    public void testCreateSubmittorLinkWithNullGedObject() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submittor submittor = builder.createSubmittor("SUBM1");
        final SubmittorLink sl = builder.createSubmittorLink(null, submittor);
        assertTrue("Should be empty",
                sl.getParent() == null
                && sl.getString().isEmpty()
                && sl.getFromString().isEmpty()
                && sl.getToString().isEmpty());
    }

    /** */
    @Test
    public void testCreateSubmittorLinkWithNullSubmittor() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Head head = builder.createHead();
        final SubmittorLink sl = builder.createSubmittorLink(head, null);
        assertTrue("Should be empty",
                sl.getParent() == null
                && sl.getString().isEmpty()
                && sl.getFromString().isEmpty()
                && sl.getToString().isEmpty());
    }

    /** */
    @Test
    public void testCreateSubmittorLink() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Head head = builder.createHead();
        final Submittor submittor = builder.createSubmittor("SUBM1");
        final SubmittorLink sl = builder.createSubmittorLink(head, submittor);
        assertTrue("Should be filled in with Submittor, S1 and Head as string,"
                + " toString and fromString",
                sl.getParent() == head
                && "Submittor".equals(sl.getString())
                && "SUBM1".equals(sl.getToString())
                && "Head".equals(sl.getFromString()));
    }

    /** */
    @Test
    public void testTwoArgCreateAttributeWithNulls() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Attribute event = builder.createAttribute(null, null);
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testCreateAttributeWithNullParent() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Attribute event = builder.createAttribute(null, "Birth");
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testCreateAttributeWithNullType() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        final Attribute event = builder.createAttribute(person, null);
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testCreateAttribute() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        final Attribute event = builder.createAttribute(person, "Birth");
        assertTrue("Should create real event", event.isSet());
    }

    /** */
    @Test
    public void testThreeArgCreateAttributeWithNulls() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Attribute event = builder.createAttribute(null, null, null);
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testThreeArgCreateAttributeWithNullParent() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Attribute event = builder.createAttribute(null, "Birth", "Tail");
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testThreeArgCreateAttributeWithNullParentAndType() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Attribute event = builder.createAttribute(null, null, "Tail");
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testThreeArgCreateAttributeWithNullTypeAndTail() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        final Attribute event = builder.createAttribute(person, null, null);
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testThreeArgCreateAttributeWithNullType() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        final Attribute event = builder.createAttribute(person, null, "Tail");
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testThreeArgCreateAttributeWithNullTail() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        final Attribute event = builder.createAttribute(person, "Birth", null);
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testThreeArgCreateAttribute() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        final Attribute event =
                builder.createAttribute(person, "Birth", "Tail");
        assertTrue("Should create real event",
                event.isSet()
                && person.equals(event.getParent())
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
