package org.schoellerfamily.gedbrowser.datamodel.util.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    @BeforeEach
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
        assertFalse(person.isSet(), "Should create empty person");
    }

    /** */
    @Test
    public void testPersonWithNulls() {
        final Person person = builder.createPerson(null,
                null);
        assertFalse(person.isSet(), "Should create empty person");
    }

    /** */
    @Test
    public void testPersonWithNullId() {
        final Person person = builder.createPerson(null,
                "Name/Me/");
        assertFalse(person.isSet(), "Should create empty person");
    }

    /** */
    @Test
    public void testPersonWithNullName() {
        final Person person = builder.createPerson("I1",
                null);
        assertFalse(person.isSet(), "Should create empty person");
    }

    /** */
    @Test
    public void testPerson() {
        final Person person = builder.createPerson("I1",
                "Name/Me/");
        assertTrue(person.isSet(), "Should create real person");
    }

    /** */
    @Test
    public void testPerson1() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        assertEquals("I1", person.getString(), "Should create real person");
    }

    /** */
    @Test
    public void testPerson2() {
        final Person person = builder.createPerson("I2",
                "Anonymous/Schoeller/");
        assertEquals("I2", person.getString(), "Should create real person");
    }

    /** */
    @Test
    public void testPerson3() {
        final Person person = builder.createPerson("I3",
                "Anonymous/Jones/");
        assertEquals("I3", person.getString(), "Should create real person");
    }

    /** */
    @Test
    public void testPerson4() {
        final Person person = builder.createPerson("I4",
                "Too Tall/Jones/");
        assertEquals("I4", person.getString(), "Should create real person");
    }

    /** */
    @Test
    public void testPerson5() {
        final Person person = builder.createPerson("I5",
                "Anonyma/Schoeller/");
        assertEquals("I5", person.getString(), "Should create real person");
    }

    /** */
    @Test
    public void testPerson1Name() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        assertEquals("J. Random/Schoeller/", person.getName().getString(),
                "Should create real person");
    }

    /** */
    @Test
    public void testPerson2Name() {
        final Person person = builder.createPerson("I2",
                "Anonymous/Schoeller/");
        assertEquals("Anonymous/Schoeller/", person.getName().getString(),
                "Should create real person");
    }

    /** */
    @Test
    public void testPerson3Name() {
        final Person person = builder.createPerson("I3",
                "Anonymous/Jones/");
        assertEquals("Anonymous/Jones/", person.getName().getString(),
                "Should create real person");
    }

    /** */
    @Test
    public void testPerson4Name() {
        final Person person = builder.createPerson("I4",
                "Too Tall/Jones/");
        assertEquals("Too Tall/Jones/", person.getName().getString(),
                "Should create real person");
    }

    /** */
    @Test
    public void testPerson5Name() {
        final Person person = builder.createPerson("I5",
                "Anonyma/Schoeller/");
        assertEquals("Anonyma/Schoeller/", person.getName().getString(),
                "Should create real person");
    }

    /** */
    @Test
    public void testPersonEventWithNulls() {
        final Attribute event = builder
                .createPersonEvent(null, null);
        assertFalse(event.isSet(), "Should create empty event");
    }

    /** */
    @Test
    public void testPersonEventWithNullPerson() {
        final Attribute event = builder
                .createPersonEvent(null, "Birth", "10 NOV 2000");
        assertFalse(event.isSet(), "Should create empty event");
    }

    /** */
    @Test
    public void testPersonEventWithNullType() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder
                .createPersonEvent(person, null, "10 NOV 2000");
        assertFalse(event.isSet(), "Should create empty event");
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
        assertTrue(visitor.getDate().isEmpty(), "Should create undated event");
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
        assertEquals("HUH?", visitor.getDate(),
                "Should create event with this date string");
    }

    /** */
    @Test
    public void testPersonEvent() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder
                .createPersonEvent(person, "Birth", "10 NOV 2000");
        assertTrue(event.isSet(), "Should create real event");
    }

    /** */
    @Test
    public void testFamilyWithNull() {
        final Family family = builder.createFamily(null);
        assertFalse(family.isSet(), "Should create empty family");
    }

    /** */
    @Test
    public void testFamilyWithId() {
        final Family family = builder.createFamily("F1");
        assertTrue(family.isSet() && "F1".equals(family.getString()),
                "Should create set family");
    }

    /** */
    @Test
    public void testFamilyWithIdFind() {
        final Root root = new Root();
        final GedObjectBuilder builder1 = new GedObjectBuilder(root);
        final Family family = builder1.createFamily("F1");
        assertEquals(family, root.find("F1", Family.class),
                "Should have found matching family");
    }

    /** */
    @Test
    public void testFamily1() {
        final Family family = builder.createFamily("F1");
        assertTrue(family.isSet() && "F1".equals(family.getString()),
                "Should create set family");
    }

    /** */
    @Test
    public void testFamily2() {
        final Family family = builder.createFamily("F2");
        assertTrue(family.isSet() && "F2".equals(family.getString()),
                "Should create set family");
    }

    /** */
    @Test
    public void testFamily3() {
        final Family family = builder.createFamily("F3");
        assertTrue(family.isSet() && "F3".equals(family.getString()),
                "Should create set family");
    }

    /** */
    @Test
    public void testFamilyEventWithNulls() {
        final Attribute event = builder.createFamilyEvent(
                null, null);
        assertFalse(event.isSet(), "Should create empty event");
    }

    /** */
    @Test
    public void testFamilyEventWithNullFamily() {
        final Attribute event = builder.createFamilyEvent(
                null, "Birth", "10 NOV 2000");
        assertFalse(event.isSet(), "Should create empty event");
    }

    /** */
    @Test
    public void testFamilyEventWithNullType() {
        final Family family = builder.createFamily("F1");
        final Attribute event = builder.createFamilyEvent(
                family, null, "10 NOV 2000");
        assertFalse(event.isSet(), "Should create empty event");
    }

    /** */
    @Test
    public void testFamilyEventWithNullDate() {
        final Family family = builder.createFamily("F1");
        final Attribute event = builder.createFamilyEvent(
                family, "Marriage");
        final GetDateVisitor visitor = new GetDateVisitor();
        event.accept(visitor);
        assertTrue(visitor.getDate().isEmpty(), "Should create undated event");
    }

    /** */
    @Test
    public void testFamilyEventWithBogusDate() {
        final Family family = builder.createFamily("F1");
        final Attribute event = builder.createFamilyEvent(
                family, "Marriage", "HUH?");
        final GetDateVisitor visitor = new GetDateVisitor();
        event.accept(visitor);
        assertEquals("HUH?", visitor.getDate(),
                "Should create event with this date string");
    }

    /** */
    @Test
    public void testFamilyEvent() {
        final Family family = builder.createFamily("F1");
        final Attribute event = builder.createFamilyEvent(
                family, "Marriage", "10 NOV 2000");
        assertTrue(event.isSet(), "Should create real event");
    }

    /** */
    @Test
    public void testAddHusbandToFamily() {
        final Family family = builder.createFamily("F1");
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Husband husband = builder.addHusbandToFamily(
                family, person);
        assertEquals(husband, family.getAttributes().get(0), "Should match");
    }

    /** */
    @Test
    public void testAddNullHusbandToFamilyGet() {
        final Family family = builder.createFamily("F1");
        final Husband husband =
                builder.addHusbandToFamily(family, null);
        assertTrue(!husband.isSet() && family.getAttributes().isEmpty(),
                "Empty husband should not be in family");
    }

    /** */
    @Test
    public void testAddNullHusbandToFamily() {
        final Family family = builder.createFamily("F1");
        final Husband husband =
                builder.addHusbandToFamily(family, null);
        assertFalse(husband.isSet(), "Should not be set");
    }

    /** */
    @Test
    public void testAddHusbandToNullFamily() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Husband husband =
                builder.addHusbandToFamily(null, person);
        assertFalse(husband.isSet(), "Should not be set");
    }

    /** */
    @Test
    public void testAddWifeToFamily() {
        final Family family = builder.createFamily("F1");
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Wife wife =
                builder.addWifeToFamily(family, person);
        assertEquals(wife, family.getAttributes().get(0), "Should match");
    }

    /** */
    @Test
    public void testAddNullWifeToFamilyGet() {
        final Family family = builder.createFamily("F1");
        final Wife wife =
                builder.addWifeToFamily(family, null);
        assertTrue(!wife.isSet() && family.getAttributes().isEmpty(),
                "Empty wife should not be in family");
    }

    /** */
    @Test
    public void testAddNullWifeToFamily() {
        final Family family = builder.createFamily("F1");
        final Wife wife =
                builder.addWifeToFamily(family, null);
        assertFalse(wife.isSet(), "Should not be set");
    }

    /** */
    @Test
    public void testAddWifeToNullFamily() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Wife wife =
                builder.addWifeToFamily(null, person);
        assertFalse(wife.isSet(), "Should not be set");
    }

    /** */
    @Test
    public void testAddChildToFamily() {
        final Family family = builder.createFamily("F1");
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Child child =
                builder.addChildToFamily(family, person);
        assertEquals(child, family.getAttributes().get(0), "Should match");
    }

    /** */
    @Test
    public void testAddNullChildToFamilyGet() {
        final Family family = builder.createFamily("F1");
        final Child child =
                builder.addChildToFamily(family, null);
        assertTrue(!child.isSet() && family.getAttributes().isEmpty(),
                "Empty child should not be in family");
    }

    /** */
    @Test
    public void testAddNullChildToFamily() {
        final Family family = builder.createFamily("F1");
        final Child child =
                builder.addChildToFamily(family, null);
        assertFalse(child.isSet(), "Should not be set");
    }

    /** */
    @Test
    public void testAddChildToNullFamily() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Child child =
                builder.addChildToFamily(null, person);
        assertFalse(child.isSet(), "Should not be set");
    }

    /** */
    @Test
    public void testSubmitterWithNulls() {
        final Submitter submitter = builder.createSubmitter(null, null);
        assertTrue(submitter.getString().isEmpty()
                        && submitter.getName().getString().isEmpty(),
                "Should create empty submitter");
    }

    /** */
    @Test
    public void testSubmitterWithNullId() {
        final Submitter submitter = builder.createSubmitter(null, "Name/Me/");
        assertTrue(submitter.getString().isEmpty()
                        && submitter.getName().getString().isEmpty(),
                "Should create empty submitter");
    }

    /** */
    @Test
    public void testSubmitterWithNullName() {
        final Submitter submitter = builder.createSubmitter("SUBM1", null);
        assertTrue(submitter.getString().isEmpty()
                        && submitter.getName().getString().isEmpty(),
                "Should create empty submitter");
    }

    /** */
    @Test
    public void testSubmitterWithBothGetString() {
        final Submitter submitter = builder.createSubmitter("SUBM1",
                "Name/Me/");
        assertEquals("SUBM1", submitter.getString(),
                "Should create real submitter");
    }

    /** */
    @Test
    public void testSubmitterWithBothGetName() {
        final Submitter submitter = builder.createSubmitter("SUBM1",
                "Name/Me/");
        assertEquals("Name/Me/", submitter.getName().getString(),
                "Should create real submitter");
    }

    /** */
    @Test
    public void testOneArgSubmitterGetString() {
        final Submitter submitter = builder.createSubmitter("SUBM1");
        assertEquals("SUBM1", submitter.getString(),
                "Should create submitter with ID");
    }

    /** */
    @Test
    public void testOneArgSubmitterGetName() {
        final Submitter submitter = builder.createSubmitter("SUBM1");
        assertTrue(submitter.getName().getString().isEmpty(),
                "Should create submitter without name");
    }

    /** */
    @Test
    public void testOneArgSubmitterNullGetString() {
        final Submitter submitter = builder.createSubmitter(null);
        assertTrue(submitter.getString().isEmpty(), "Should create empty submitter");
    }

    /** */
    @Test
    public void testOneArgSubmitterEmptyStringGetString() {
        final Submitter submitter = builder.createSubmitter(null);
        assertTrue(submitter.getString().isEmpty(), "Should create empty submitter");
    }

    /** */
    @Test
    public void testCreateHead() {
        final Head head = builder.createHead();
        assertEquals("Head", head.getString(), "String should be the default");
    }

    /** */
    @Test
    public void testCreateTrailer() {
        final Trailer trailer = builder.createTrailer();
        assertEquals("Trailer", trailer.getString(), "String should be the default");
    }

    /** */
    @Test
    public void testBeforeAddNameToPerson() {
        final Person person = builder.createPerson("I1");
        final Name name = person.getName();
        assertTrue(name.getString().isEmpty(), "Name should not be set");
    }

    /** */
    @Test
    public void testAddNameToPerson() {
        final Person person = builder.createPerson("I1");
        builder.addNameToPerson(person, "Foo/Bar/");
        final Name name = person.getName();
        assertEquals("Foo/Bar/", name.getString(), "Name should be set");
    }

    /** */
    @Test
    public void testAddNameToNullPerson() {
        final Name name = builder.addNameToPerson(null,
                "Foo/Bar/");
        assertFalse(name.isSet(), "Name should not be set");
    }

    /** */
    @Test
    public void testAddNullNameToPerson() {
        final Person person = builder.createPerson("I1");
        final Name name = builder.addNameToPerson(person,
                null);
        assertFalse(name.isSet(), "Name should not be set");
    }

    /** */
    @Test
    public void testAddNullNameToPersonNull() {
        final Person person = builder.createPerson("I1");
        builder.addNameToPerson(person, null);
        assertTrue(person.getName().getString().isEmpty(), "Name should not be set");
    }

    /** */
    @Test
    public void testAddNullNameToPersonNotSame() {
        final Person person = builder.createPerson("I1");
        final Name name = builder.addNameToPerson(person,
                null);
        assertNotSame(name, person.getName(), "Name should not be in person");
    }

    /** */
    @Test
    public void testNameToPersonWithNulls() {
        final Name name = builder.addNameToPerson(null,
                null);
        assertTrue(name.getString().isEmpty(), "Name should not be set");
    }

    /** */
    @Test
    public void testAddPlaceToEvent() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder
                .createPersonEvent(person, "Birth", "10 NOV 2000");
        final Place place = builder.addPlaceToEvent(event, "Boston, MA");
        assertEquals(place, getPlace(event), "Should have place");
    }

    /** */
    @Test
    public void testBeforeAddPlaceToEvent() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder
                .createPersonEvent(person, "Birth", "10 NOV 2000");
        assertNull(getPlace(event), "Should not have place");
    }

    /** */
    @Test
    public void testAddPlaceToEventCheckName() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder
                .createPersonEvent(person, "Birth", "10 NOV 2000");
        builder.addPlaceToEvent(event, "Boston, MA");
        assertEquals("Boston, MA", getPlace(event).getString(),
                "Should have place");
    }

    /** */
    @Test
    public void testMultimediaWithNulls() {
        final Multimedia mm =
                builder.addMultimediaToPerson(null, null);
        assertTrue(mm.getParent() == null && mm.getString().isEmpty(),
                "Should create empty mm");
    }

    /** */
    @Test
    public void testMultimediaWithNullPerson() {
        final Multimedia mm =
                builder.addMultimediaToPerson(null, "Foo");
        assertTrue(mm.getParent() == null && mm.getString().isEmpty(),
                "Should create empty mm");
    }

    /** */
    @Test
    public void testMultimediaWithNullType() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Multimedia mm =
                builder.addMultimediaToPerson(person, null);
        assertTrue(mm.getParent() == null && mm.getString().isEmpty(),
                "Should create empty mm");
    }

    /** */
    @Test
    public void testMultimediaSet() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Multimedia mm =
                builder.addMultimediaToPerson(
                        person, "Foo");
        assertTrue(mm.isSet(), "Should create real mm");
    }

    /** */
    @Test
    public void testMultimediaTail() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Multimedia mm =
                builder.addMultimediaToPerson(
                        person, "Foo");
        assertEquals("Foo", mm.getTail(), "Should create real mm");
    }

    /** */
    @Test
    public void testMultimedia() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Multimedia mm =
                builder.addMultimediaToPerson(
                        person, "Foo");
        assertEquals("Multimedia", mm.getString(), "Should create real mm");
    }

    /** */
    @Test
    public void testCreateSource1() {
        final Source source = builder.createSource("S1");
        assertEquals("S1", source.getString(), "Should be S1");
    }

    /** */
    @Test
    public void testBeforeAddDateToEvent() {
        final Source source = builder.createSource("S1");
        assertNull(getDate(source), "Should be undated");
    }

    /** */
    @Test
    public void testAddDateToEvent() {
        final Source source = builder.createSource("S1");
        builder.addDateToGedObject(source, "30 JAN 2017");
        assertEquals(new Date(source, "30 JAN 2017"), getDate(source),
                "Should match date");
    }

    /** */
    @Test
    public void testCreateSourceLinkWithNulls() {
        final SourceLink sl = builder.createSourceLink(null, null);
        assertTrue(sl.getParent() == null && sl.getString().isEmpty()
                        && sl.getFromString().isEmpty()
                        && sl.getToString().isEmpty(),
                "Should be empty");
    }

    /** */
    @Test
    public void testCreateSourceLinkWithNullGedObject() {
        final Source source = builder.createSource("S1");
        final SourceLink sl = builder.createSourceLink(null, source);
        assertTrue(sl.getParent() == null && sl.getString().isEmpty()
                        && sl.getFromString().isEmpty()
                        && sl.getToString().isEmpty(),
                "Should be empty");
    }

    /** */
    @Test
    public void testCreateSourceLinkWithNullSource() {
        final Person person = builder.createPerson("I1",
                "Who/Me/");
        final Attribute birth = builder
                .createPersonEvent(person, "Birth");
        final SourceLink sl = builder.createSourceLink(birth, null);
        assertTrue(sl.getParent() == null && sl.getString().isEmpty()
                        && sl.getFromString().isEmpty()
                        && sl.getToString().isEmpty(),
                "Should be empty");
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
        assertTrue(sl.getParent() == birth && "Source".equals(sl.getString())
                        && "S1".equals(sl.getToString())
                        && "Birth".equals(sl.getFromString()),
                "Should be filled in with Source, S1 and Birth as string, toString and fromString");
    }

    /** */
    @Test
    public void testCreateSubmitterLinkWithNulls() {
        final SubmitterLink sl = builder.createSubmitterLink(null, null);
        assertTrue(sl.getParent() == null && sl.getString().isEmpty()
                        && sl.getFromString().isEmpty()
                        && sl.getToString().isEmpty(),
                "Should be empty");
    }

    /** */
    @Test
    public void testCreateSubmitterLinkWithNullGedObject() {
        final Submitter submitter = builder.createSubmitter("SUBM1");
        final SubmitterLink sl = builder.createSubmitterLink(null, submitter);
        assertTrue(sl.getParent() == null && sl.getString().isEmpty()
                        && sl.getFromString().isEmpty()
                        && sl.getToString().isEmpty(),
                "Should be empty");
    }

    /** */
    @Test
    public void testCreateSubmitterLinkWithNullSubmitter() {
        final Head head = builder.createHead();
        final SubmitterLink sl = builder.createSubmitterLink(head, null);
        assertTrue(sl.getParent() == null && sl.getString().isEmpty()
                        && sl.getFromString().isEmpty()
                        && sl.getToString().isEmpty(),
                "Should be empty");
    }

    /** */
    @Test
    public void testCreateSubmitterLink() {
        final Head head = builder.createHead();
        final Submitter submitter = builder.createSubmitter("SUBM1");
        final SubmitterLink sl = builder.createSubmitterLink(head, submitter);
        assertTrue(sl.getParent() == head && "Submitter".equals(sl.getString())
                        && "SUBM1".equals(sl.getToString())
                        && "Head".equals(sl.getFromString()),
                "Should be filled in with Submitter, S1 and Head as string, toString and fromString");
    }

    /** */
    @Test
    public void testTwoArgCreateAttributeWithNulls() {
        final Attribute event = builder.createAttribute(null, null);
        assertFalse(event.isSet(), "Should create empty event");
    }

    /** */
    @Test
    public void testCreateAttributeWithNullParent() {
        final Attribute event = builder.createAttribute(null, "Birth");
        assertFalse(event.isSet(), "Should create empty event");
    }

    /** */
    @Test
    public void testCreateAttributeWithNullType() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder.createAttribute(person, null);
        assertFalse(event.isSet(), "Should create empty event");
    }

    /** */
    @Test
    public void testCreateAttribute() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder.createAttribute(person, "Birth");
        assertTrue(event.isSet(), "Should create real event");
    }

    /** */
    @Test
    public void testThreeArgCreateAttributeWithNulls() {
        final Attribute event = builder.createAttribute(null, null, null);
        assertFalse(event.isSet(), "Should create empty event");
    }

    /** */
    @Test
    public void testThreeArgCreateAttributeWithNullParent() {
        final Attribute event = builder.createAttribute(null, "Birth", "Tail");
        assertFalse(event.isSet(), "Should create empty event");
    }

    /** */
    @Test
    public void testThreeArgCreateAttributeWithNullParentAndType() {
        final Attribute event = builder.createAttribute(null, null, "Tail");
        assertFalse(event.isSet(), "Should create empty event");
    }

    /** */
    @Test
    public void testThreeArgCreateAttributeWithNullTypeAndTail() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder.createAttribute(person, null, null);
        assertFalse(event.isSet(), "Should create empty event");
    }

    /** */
    @Test
    public void testThreeArgCreateAttributeWithNullType() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder.createAttribute(person, null, "Tail");
        assertFalse(event.isSet(), "Should create empty event");
    }

    /** */
    @Test
    public void testThreeArgCreateAttributeWithNullTail() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder.createAttribute(person, "Birth", null);
        assertFalse(event.isSet(), "Should create empty event");
    }

    /** */
    @Test
    public void testThreeArgCreateAttribute() {
        final Person person = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Attribute event = builder.createAttribute(person, "Birth",
                "Tail");
        assertTrue(event.isSet() && person.equals(event.getParent())
                        && "Birth".equals(event.getString())
                        && "Tail".equals(event.getTail()),
                "Should create real event");
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
