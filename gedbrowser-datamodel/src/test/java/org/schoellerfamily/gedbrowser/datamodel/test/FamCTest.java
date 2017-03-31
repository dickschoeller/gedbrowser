package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.navigator.FamilyNavigator;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
public final class FamCTest {
    /** */
    private transient Person person2;
    /** */
    private transient Person person3;
    /** */
    private transient FamC famC;

    /** */
    @Before
    public void setUp() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        person2 = builder.createPerson2();
        person3 = builder.createPerson3();
        final Family family = builder.createFamily("F1");
        buildChild(family, person1);
        builder.addHusbandToFamily(family, person2);
        builder.addWifeToFamily(family, person3);
    }

    /**
     * @param family the family to put the child in
     * @param person1 the person to be the child
     * @return the child object
     */
    private Child buildChild(final Family family, final Person person1) {
        if (family == null || person1 == null) {
            return new Child();
        }
        // In-lined because we want to capture and look at the FamC object.
        // That prevents us from using the builder, which only expposes the
        // Child and not the FamC.
        famC = new FamC(person1, "FAMC",
                new ObjectId(family.getString()));
        final Child child = new Child(family, "Child",
                new ObjectId(person1.getString()));
        family.insert(child);
        person1.insert(famC);
        return child;
    }

    /** */
    @Test
    public void testGetFatherNotSet() {
        final FamilyNavigator navigator = new FamilyNavigator(new FamC());
        assertFalse("Father should be unset", navigator.getFather().isSet());
    }

    /** */
    @Test
    public void testGetFather() {
        final FamilyNavigator navigator = new FamilyNavigator(famC);
        assertEquals("Person mismatch", person2, navigator.getFather());
    }

    /** */
    @Test
    public void testGetMotherNotSet() {
        final FamilyNavigator navigator = new FamilyNavigator(new FamC());
        assertFalse("Mother should be unset", navigator.getMother().isSet());
    }

    /** */
    @Test
    public void testGetMother() {
        final FamilyNavigator navigator = new FamilyNavigator(famC);
        assertEquals("Person mismatch", person3, navigator.getMother());
    }
}
