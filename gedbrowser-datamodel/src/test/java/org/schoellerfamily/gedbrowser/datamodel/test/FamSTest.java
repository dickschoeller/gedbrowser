package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Wife;
import org.schoellerfamily.gedbrowser.datamodel.navigator.FamilyNavigator;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
public final class FamSTest {
    /** */
    private transient Person person1;
    /** */
    private transient Person person2;
    /** */
    private transient Person person3;
    /** */
    private transient Family family;
    /** */
    private transient FamS famS2;
    /** */
    private transient FamS famS3;

    /** */
    @BeforeEach
    public void setUp() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        person1 = builder.createPerson("I1", "J. Random/Schoeller/");
        person2 = builder.createPerson("I2", "Anonymous/Schoeller/");
        person3 = builder.createPerson("I3", "Anonymous/Jones/");
        family = builder.createFamily("F1");
        final Family family1 = family;
        final Person person = person1;
        builder.addChildToFamily(family1, person);

        // Because we are working directly with FamS can't use builder.
        famS2 = new FamS(person2, "FAMS", new ObjectId("F1"));
        person2.insert(famS2);
        final Husband husband = new Husband(family, "Husband", new ObjectId("I2"));
        family.insert(husband);

        famS3 = new FamS(person3, "FAMS", new ObjectId("F1"));
        person3.insert(famS3);
        final Wife wife = new Wife(family, "Wife", new ObjectId("I3"));
        family.insert(wife);
    }

    /** */
    @Test
    public void testGetSpouseFromSpouse() {
        final FamilyNavigator navigator = new FamilyNavigator(famS2);
        assertEquals(person3, navigator.getSpouse(person2), "Person mismatch");
    }

    /** */
    @Test
    public void testGetSpouseFromParent() {
        final FamilyNavigator navigator = new FamilyNavigator(famS2);
        assertEquals(person2, navigator.getSpouse(person3), "Person mismatch");
    }

    /** */
    @Test
    public void testGetSpouseNotSetFromUnrelated() {
        final FamilyNavigator navigator = new FamilyNavigator(famS2);
        assertFalse(navigator.getSpouse(person1).isSet(),
            "Should be unset person when not from one of the spouses");
    }

    /** */
    @Test
    public void testGetSpouseIsParentFromNull() {
        final FamilyNavigator navigator = new FamilyNavigator(famS2);
        assertEquals(person2, navigator.getSpouse(null), "Person mismatch");
    }

    /** */
    @Test
    public void testGetFamilies() {
        final FamilyNavigator navigator = new FamilyNavigator(famS2);
        final Family gottenFamily = navigator.getFamily();
        assertSame(family, gottenFamily, "Mismatched family");
    }

    /** */
    @Test
    public void testGetFamiliesUnsetWhenUnattached() {
        final FamS fams = new FamS(null, "F73", null);
        final FamilyNavigator navigator = new FamilyNavigator(fams);
        assertFalse(navigator.getFamily().isSet(), "Family should not be set");
    }

    /** */
    @Test
    public void testGetChildrenFromHusband() {
        final FamilyNavigator navigator = new FamilyNavigator(famS2);
        final List<Person> newList = navigator.getChildren();
        assertTrue(newList.contains(person1), "List should contain person1");
    }

    /** */
    @Test
    public void testGetChildrenFromWife() {
        final FamilyNavigator navigator = new FamilyNavigator(famS3);
        final List<Person> newList = navigator.getChildren();
        assertTrue(newList.contains(person1), "List should contain person1");
    }

    // TODO we don't have a test where FamS.getSpouse returns a set person.
}
