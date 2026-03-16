package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.navigator.FamilyNavigator;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * Contains tests for fam c.
 *
 * @author Richard Schoeller
 */
final class FamCTest {
    /** */
    private transient Person person2;
    /** */
    private transient Person person3;
    /** */
    private transient FamC famC;

    @BeforeEach
    void setUp() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson("I1", "J. Random/Schoeller/");
        person2 = builder.createPerson("I2", "Anonymous/Schoeller/");
        person3 = builder.createPerson("I3", "Anonymous/Jones/");
        final Family family = builder.createFamily("F1");
        buildChild(family, person1);
        final Person person = person2;
        builder.addHusbandToFamily(family, person);
        final Person person4 = person3;
        builder.addWifeToFamily(family, person4);
    }

    private Child buildChild(final Family family, final Person person1) {
        if (family == null || person1 == null) {
            return new Child();
        }
        // In-lined because we want to capture and look at the FamC object.
        // That prevents us from using the builder, which only expposes the
        // Child and not the FamC.
        famC = new FamC(person1, "FAMC", new ObjectId(family.getString()));
        final Child child = new Child(family, "Child", new ObjectId(person1.getString()));
        family.insert(child);
        person1.insert(famC);
        return child;
    }

    @Test
    void testGetFatherNotSet() {
        final FamilyNavigator navigator = new FamilyNavigator(new FamC());
        assertFalse(navigator.getFather().isSet(), "Father should be unset");
    }

    @Test
    void testGetFather() {
        final FamilyNavigator navigator = new FamilyNavigator(famC);
        assertEquals(person2, navigator.getFather(), "Person mismatch");
    }

    @Test
    void testGetMotherNotSet() {
        final FamilyNavigator navigator = new FamilyNavigator(new FamC());
        assertFalse(navigator.getMother().isSet(), "Mother should be unset");
    }

    @Test
    void testGetMother() {
        final FamilyNavigator navigator = new FamilyNavigator(famC);
        assertEquals(person3, navigator.getMother(), "Person mismatch");
    }
}
