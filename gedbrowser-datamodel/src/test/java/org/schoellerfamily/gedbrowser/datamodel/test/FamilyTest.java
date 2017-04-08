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
import org.schoellerfamily.gedbrowser.datamodel.navigator.FamilyNavigator;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.CommentSize")
public final class FamilyTest {
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

    /** */
    @Before
    public void setUp() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        family1 = builder.getFamilyBuilder().createFamily("F1");
        person1 = builder.getPersonBuilder().createPerson(
                "I1", "J. Random/Schoeller/");
        person2 = builder.getPersonBuilder().createPerson(
                "I2", "Anonymous/Schoeller/");
        person3 = builder.getPersonBuilder().createPerson(
                "I3", "Anonymous/Jones/");
        final Family family = family1;
        final Person person = person1;
        builder.getFamilyBuilder().addHusbandToFamily(family, person);
        final Family family2 = family1;
        final Person person4 = person2;
        builder.getFamilyBuilder().addWifeToFamily(family2, person4);
        final Family family3 = family1;
        final Person person5 = person3;
        builder.getFamilyBuilder().addChildToFamily(family3, person5);
    }

    /** */
    @Test
    public void testGetSpouseFromHusband() {
        final FamilyNavigator navigator = new FamilyNavigator(family1);
        final Person spouse = navigator.getSpouse(person1);
        assertEquals("Spouse mismatch", person2, spouse);
    }

    /** */
    @Test
    public void testGetSpouseFromWife() {
        final FamilyNavigator navigator = new FamilyNavigator(family1);
        final Person spouse = navigator.getSpouse(person2);
        assertEquals("Spouse mismatch", person1, spouse);
    }

    /** */
    @Test
    public void testGetSpouseFromNonSpouse() {
        final FamilyNavigator navigator = new FamilyNavigator(family1);
        final Person spouse = navigator.getSpouse(person3);
        assertFalse("Expected spouse to be unset when search from child",
                spouse.isSet());
    }

    /** */
    @Test
    public void testGetSpousesContainsHusband() {
        final FamilyNavigator navigator = new FamilyNavigator(family1);
        final List<Person> spouses = navigator.getSpouses();
        assertTrue("Expected to find husband", spouses.contains(person1));
    }

    /** */
    @Test
    public void testGetSpousesContainsWife() {
        final FamilyNavigator navigator = new FamilyNavigator(family1);
        final List<Person> spouses = navigator.getSpouses();
        assertTrue("Expected to find wife", spouses.contains(person2));
    }

    /** */
    @Test
    public void testGetSpousesDoesNotContainNonSpouse() {
        final FamilyNavigator navigator = new FamilyNavigator(family1);
        final List<Person> spouses = navigator.getSpouses();
        assertFalse("Did not expect to find child in spouses list",
                spouses.contains(person3));
    }

    /** */
    @Test
    public void testFamilyUsualSetup() {
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

    /**
     * @param fam the family
     * @param gob what we found with find
     * @param chil the child
     * @param hus the husband
     * @param wif the wife
     * @param marr the marriage
     * @param sl the source link
     */
    private void assertMatchUnusual(final Family fam, final GedObject gob,
            final Child chil, final Husband hus, final Wife wif,
            final Attribute marr, final SourceLink sl) {
        assertEquals("Expected to find family", fam, gob);
        assertEquals("Attribute size mismatch",
                F1_ATTRS_LENGTH, fam.getAttributes().size());
        assertTrue("Expected to find Child in attributes",
                fam.getAttributes().contains(chil));
        assertTrue("Expected to find Husband in attributes",
                fam.getAttributes().contains(hus));
        assertTrue("Expected to find Wife in attributes",
                fam.getAttributes().contains(wif));
        assertTrue("Expected to find Marriage in attributes",
                fam.getAttributes().contains(marr));
        assertTrue("Expected to find SourceLink in attributes",
                marr.getAttributes().contains(sl));
    }

    /** */
    @Test
    public void testDefaultConstructor() {
        final Family family = new Family();
        assertMatch(null, family, "", false, false, 0);
    }

    /** */
    @Test
    public void testEmptyToString() {
        final Family family = new Family();
        assertEquals("Expected empty string", "", family.toString());
    }

    /** */
    @Test
    public void testConstructorRootId() {
        final Root localRoot = new Root(ROOT_NAME);
        final Family family = new Family(localRoot, new ObjectId("F1"));
        localRoot.insert(family);
        assertMatch(localRoot, family, "F1", false, false, 0);
    }

    /**
     * @param localRoot the root that this family is in
     * @param family the family
     * @param string the expected string
     * @param fatherSet whether father is expected to be set
     * @param motherSet whether mother is expected to be set
     * @param spousesSize the size of the spouses list
     */
    private void assertMatch(final Root localRoot, final Family family,
            final String string, final boolean fatherSet,
            final boolean motherSet, final int spousesSize) {
        assertEquals("String mismatch", string, family.getString());
        if (localRoot != null) {
            assertEquals("Did not find expected family",
                    family, localRoot.find("F1"));
        }
        final FamilyNavigator navigator = new FamilyNavigator(family);
        assertEquals("Father set mismatch",
                fatherSet, navigator.getFather().isSet());
        assertEquals("Mother set mismatch",
                motherSet, navigator.getMother().isSet());
        final List<Person> spouses = navigator.getSpouses();
        assertEquals("Spouse list size mismatch",
                spousesSize, spouses.size());
    }
}
