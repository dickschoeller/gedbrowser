package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
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
    @Before
    public void setUp() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        person1 = builder.createPerson1();
        person2 = builder.createPerson2();
        person3 = builder.createPerson3();
        family = builder.createFamily("F1");
        builder.addChildToFamily(family, person1);

        // Because we are working directly with FamS can't use builder.
        famS2 = new FamS(person2, "FAMS", new ObjectId("F1"));
        person2.insert(famS2);
        final Husband husband = new Husband(family, "Husband",
                new ObjectId("I2"));
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
        assertEquals("Person mismatch", person3, navigator.getSpouse(person2));
    }

    /** */
    @Test
    public void testGetSpouseFromParent() {
        final FamilyNavigator navigator = new FamilyNavigator(famS2);
        assertEquals("Person mismatch", person2, navigator.getSpouse(person3));
    }

    /** */
    @Test
    public void testGetSpouseNotSetFromUnrelated() {
        final FamilyNavigator navigator = new FamilyNavigator(famS2);
        assertFalse("Should be unset person when not from one of the spouses",
                navigator.getSpouse(person1).isSet());
    }

    /** */
    @Test
    public void testGetSpouseIsParentFromNull() {
        final FamilyNavigator navigator = new FamilyNavigator(famS2);
        assertEquals("Person mismatch", person2, navigator.getSpouse(null));
    }

    /** */
    @Test
    public void testGetFamilies() {
        final FamilyNavigator navigator = new FamilyNavigator(famS2);
        final Family gottenFamily = navigator.getFamily();
        assertSame("Mismatched family", family, gottenFamily);
    }

    /** */
    @Test
    public void testGetFamiliesUnsetWhenUnattached() {
        final FamS fams = new FamS(null, "F73", null);
        final FamilyNavigator navigator = new FamilyNavigator(fams);
        assertFalse("Family should not be set", navigator.getFamily().isSet());
    }

    /** */
    @Test
    public void testGetChildrenFromHusband() {
        final FamilyNavigator navigator = new FamilyNavigator(famS2);
        final List<Person> newList = navigator.getChildren();
        assertTrue("List should contain person1", newList.contains(person1));
    }

    /** */
    @Test
    public void testGetChildrenFromWife() {
        final FamilyNavigator navigator = new FamilyNavigator(famS3);
        final List<Person> newList = navigator.getChildren();
        assertTrue("List should contain person1", newList.contains(person1));
    }

    // TODO we don't have a test where FamS.getSpouse returns a set person.
}
