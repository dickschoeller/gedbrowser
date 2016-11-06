package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Wife;

/**
 * @author Dick Schoeller
 */
public final class FamilyTest {
    /** */
    private static final int F1_ATTRS_LENGTH = 4;
    /** */
    private static final String ROOT_NAME = "Root";
    /** */
    private final transient Root root = new Root(null, ROOT_NAME);
    /** */
    private final transient Family family1 = new Family(root,
            new ObjectId("F1"));
    /** */
    private final transient Person person1 = new Person(root,
            new ObjectId("I1"));
    /** */
    private final transient Person person2 = new Person(root,
            new ObjectId("I2"));
    /** */
    private final transient Husband husband = new Husband(family1, "HUSB",
            new ObjectId("@I1@"));
    /** */
    private final transient Wife wife = new Wife(family1, "WIFE",
            new ObjectId("@I2@"));
    /** */
    private final transient Person person3 = new Person(root,
            new ObjectId("I3"));
    /** */
    private final transient Child child = new Child(family1, "CHIL",
            new ObjectId("@I3@"));

    /** */
    @Before
    public void setUp() {
        root.insert("F1", family1);
        root.insert("I1", person1);
        root.insert("I2", person2);
        family1.insert(husband);
        family1.insert(wife);
        root.insert("I3", person3);
        family1.insert(child);
    }

    /** */
    @Test
    public void testGetFather() {
        assertEquals(person1, family1.getFather());
    }

    /** */
    @Test
    public void testGetMother() {
        assertEquals(person2, family1.getMother());
    }

    /** */
    @Test
    public void testGetSpouse() {
        assertEquals(person2, family1.getSpouse(person1));
        assertEquals(person1, family1.getSpouse(person2));
        assertFalse(family1.getSpouse(person3).isSet());
    }

    /** */
    @Test
    public void testGetSpouses() {
        final List<Person> spouses = family1.getSpouses();
        assertTrue(spouses.contains(person1));
        assertTrue(spouses.contains(person2));
        assertFalse(spouses.contains(person3));
    }

    /** */
    @Test
    public void testGetChildren() {
        // fail("Not yet implemented");
    }

    /** */
    @Test
    public void testFamilyUsualSetup() {
        final Root localRoot = new Root(null, ROOT_NAME);
        final Family localFamily1 = new Family(localRoot, new ObjectId("F1"));
        localRoot.insert("F1", localFamily1);
        final Person localPerson1 = new Person(localRoot, new ObjectId("I1"));
        localRoot.insert("I1", localPerson1);
        final Person localPerson2 = new Person(localRoot, new ObjectId("I2"));
        localRoot.insert("I2", localPerson2);
        final Husband localHusband = new Husband(localFamily1, "HUSB",
                new ObjectId("@I1@"));
        localFamily1.insert(localHusband);
        final Wife localWife =
                new Wife(localFamily1, "WIFE", new ObjectId("@I2@"));
        localFamily1.insert(localWife);
        final Person localPerson3 = new Person(localRoot, new ObjectId("I3"));
        localRoot.insert("I3", localPerson3);
        final Child localChild = new Child(localFamily1, "CHIL",
                new ObjectId("@I3@"));
        localFamily1.insert(localChild);
        final Attribute marriage = new Attribute(localFamily1, "MARR");
        localFamily1.insert(marriage);
        final Date marriageDate = new Date(marriage, "27 MAY 1984");
        marriage.insert(marriageDate);
        final Place marriagePlace = new Place(marriage,
                "Temple Emanu-el, Providence, "
                        + "Providence County, Rhode Island, USA");
        marriage.insert(marriagePlace);
        final Attribute marriageNote = new Attribute(localFamily1, "NOTE",
                "The ceremony performed by Rabbi Wayne Franklin and "
                        + "Cantor Ivan Perlman");
        marriage.insert(marriageNote);
        marriageNote.appendString("Perlman.  The best man and matron of "
                + "honor were Dale Matcovitch");
        marriageNote.appendString("and Carol Robinson Sacerdote.");
        final SourceLink sourceLink = new SourceLink(marriage, "SOUR",
                new ObjectId("@S4@"));
        marriage.insert(sourceLink);
        final Source source = new Source(localRoot, new ObjectId("S4"));
        localRoot.insert("S4", source);

        final GedObject gob = localRoot.find("F1");
        assertEquals(localFamily1, gob);
        assertEquals(F1_ATTRS_LENGTH, localFamily1.getAttributes().size());
        assertTrue(localFamily1.getAttributes().contains(localChild));
        assertTrue(localFamily1.getAttributes().contains(localHusband));
        assertTrue(localFamily1.getAttributes().contains(localWife));
        assertTrue(localFamily1.getAttributes().contains(marriage));
        assertTrue(marriage.getAttributes().contains(sourceLink));
    }

    /** */
    @Test
    public void testFamily() {
        final Family family = new Family();
        assertEquals("", family.toString());
        assertEquals("", family.getString());
        assertFalse(family.getFather().isSet());
        assertFalse(family.getMother().isSet());
        assertEquals(0, family.getSpouses().size());
    }

    /** */
    @Test
    public void testFamilyGedObject() {
        final Root localRoot = new Root(null, ROOT_NAME);
        final Family family = new Family(localRoot);
        localRoot.insert("F1", family);
        assertTrue("Family string should be empty", family.getString()
                .isEmpty());
        assertEquals(family, localRoot.find("F1"));
        assertFalse(family.getFather().isSet());
        assertFalse(family.getMother().isSet());
        assertEquals(0, family.getSpouses().size());
    }

    /** */
    @Test
    public void testFamilyGedObjectString() {
        final Root localRoot = new Root(null, ROOT_NAME);
        final Family family = new Family(localRoot, new ObjectId("F1"));
        localRoot.insert("F1", family);
        assertEquals("F1", family.getString());
        assertEquals(family, localRoot.find("F1"));
        assertFalse(family.getFather().isSet());
        assertFalse(family.getMother().isSet());
        assertEquals(0, family.getSpouses().size());
    }
}
