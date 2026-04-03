package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import org.schoellerfamily.gedbrowser.datamodel.navigator.FamilyNavigator;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilderImpl;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;



/**
 * Contains tests for family.
 *
 * @author Richard Schoeller
 */
@SuppressWarnings("PMD.CommentSize")
final class FamilyTest {
    /** */
    private static final int F1_ATTRS_LENGTH = 4;
    /** */
    private static final String ROOT_NAME = "Root";
    /** */
    private transient Family family1;
    /** */
    private transient Person person1;
    /** */
    private transient Person person2;
    /** */
    private transient Person person3;

    @BeforeEach
    void setUp() {
        final GedObjectBuilder builder = new GedObjectBuilderImpl();
        family1 = builder.createFamily("F1");
        person1 = builder.createPerson(
                "I1", "J. Random/Schoeller/");
        person2 = builder.createPerson(
                "I2", "Anonymous/Schoeller/");
        person3 = builder.createPerson(
                "I3", "Anonymous/Jones/");
        final Family family = family1;
        final Person person = person1;
        builder.addHusbandToFamily(family, person);
        final Family family2 = family1;
        final Person person4 = person2;
        builder.addWifeToFamily(family2, person4);
        final Family family3 = family1;
        final Person person5 = person3;
        builder.addChildToFamily(family3, person5);
    }

    @Test
    void testGetSpouseFromHusband() {
        final FamilyNavigator navigator = new FamilyNavigator(family1);
        final Person spouse = navigator.getSpouse(person1);
        assertEquals(person2, spouse, "Spouse mismatch");
    }

    @Test
    void testGetSpouseFromWife() {
        final FamilyNavigator navigator = new FamilyNavigator(family1);
        final Person spouse = navigator.getSpouse(person2);
        assertEquals(person1, spouse, "Spouse mismatch");
    }

    @Test
    void testGetSpouseFromNonSpouse() {
        final FamilyNavigator navigator = new FamilyNavigator(family1);
        final Person spouse = navigator.getSpouse(person3);
        assertFalse(spouse.isSet(), "Expected spouse to be unset when search from child");
    }

    @Test
    void testGetSpousesContainsHusband() {
        final FamilyNavigator navigator = new FamilyNavigator(family1);
        final List<Person> spouses = navigator.getSpouses();
        assertTrue(spouses.contains(person1), "Expected to find husband");
    }

    @Test
    void testGetSpousesContainsWife() {
        final FamilyNavigator navigator = new FamilyNavigator(family1);
        final List<Person> spouses = navigator.getSpouses();
        assertTrue(spouses.contains(person2), "Expected to find wife");
    }

    @Test
    void testGetSpousesDoesNotContainNonSpouse() {
        final FamilyNavigator navigator = new FamilyNavigator(family1);
        final List<Person> spouses = navigator.getSpouses();
        assertFalse(spouses.contains(person3), "Did not expect to find child in spouses list");
    }

    @Test
    void testFamilyUsualSetup() {
        final Root localRoot = new Root(ROOT_NAME);
        final Family localFamily1 = new Family(localRoot, new ObjectId("F1"));
        localRoot.insert(localFamily1);
        final Person localPerson1 = new Person(localRoot, new ObjectId("I1"));
        localRoot.insert(localPerson1);
        final Person localPerson2 = new Person(localRoot, new ObjectId("I2"));
        localRoot.insert(localPerson2);
        final Husband localHusband = new Husband(localFamily1, "HUSB",
                new ObjectId("@I1@"));
        localFamily1.insert(localHusband);
        final Wife localWife =
                new Wife(localFamily1, "WIFE", new ObjectId("@I2@"));
        localFamily1.insert(localWife);
        final Person localPerson3 = new Person(localRoot, new ObjectId("I3"));
        localRoot.insert(localPerson3);
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
        localRoot.insert(source);

        final GedObject gob = localRoot.find("F1");
        assertMatchUnusual(localFamily1, gob, localChild, localHusband,
                localWife, marriage, sourceLink);
    }

    private void assertMatchUnusual(final Family fam, final GedObject gob,
            final Child chil, final Husband hus, final Wife wif,
            final Attribute marr, final SourceLink sl) {
        assertEquals(fam, gob, "Expected to find family");
        assertEquals(F1_ATTRS_LENGTH, fam.getAttributes().size(), "Attribute size mismatch");
        assertTrue(fam.getAttributes().contains(chil), "Expected to find Child in attributes");
        assertTrue(fam.getAttributes().contains(hus), "Expected to find Husband in attributes");
        assertTrue(fam.getAttributes().contains(wif), "Expected to find Wife in attributes");
        assertTrue(fam.getAttributes().contains(marr), "Expected to find Marriage in attributes");
        assertTrue(marr.getAttributes().contains(sl), "Expected to find SourceLink in attributes");
    }

    @Test
    void testDefaultConstructor() {
        final Family family = new Family();
        assertMatch(null, family, "", false, false, 0);
    }

    @Test
    void testEmptyToString() {
        final Family family = new Family();
        assertEquals("", family.toString(), "Expected empty string");
    }

    @Test
    void testConstructorRootId() {
        final Root localRoot = new Root(ROOT_NAME);
        final Family family = new Family(localRoot, new ObjectId("F1"));
        localRoot.insert(family);
        assertMatch(localRoot, family, "F1", false, false, 0);
    }

    private void assertMatch(final Root localRoot, final Family family,
            final String string, final boolean fatherSet,
            final boolean motherSet, final int spousesSize) {
        assertEquals(string, family.getString(), "String mismatch");
        if (localRoot != null) {
            assertEquals(family, localRoot.find("F1"), "Did not find expected family");
        }
        final FamilyNavigator navigator = new FamilyNavigator(family);
        assertEquals(fatherSet, navigator.getFather().isSet(), "Father set mismatch");
        assertEquals(motherSet, navigator.getMother().isSet(), "Mother set mismatch");
        final List<Person> spouses = navigator.getSpouses();
        assertEquals(spousesSize, spouses.size(), "Spouse list size mismatch");
    }
}
