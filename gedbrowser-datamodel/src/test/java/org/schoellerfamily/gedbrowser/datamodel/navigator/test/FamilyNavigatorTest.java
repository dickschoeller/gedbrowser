package org.schoellerfamily.gedbrowser.datamodel.navigator.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.navigator.FamilyNavigator;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilderImpl;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * Contains tests for family navigator.
 *
 * @author Richard Schoeller
 */
final class FamilyNavigatorTest {
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
        person1 = builder.createPerson("I1", "J. Random/Schoeller/");
        person2 = builder.createPerson("I2", "Anonymous/Schoeller/");
        person3 = builder.createPerson("I3", "Anonymous/Jones/");
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
    void testGetFather() {
        final FamilyNavigator navigator = new FamilyNavigator(family1);
        assertEquals(person1, navigator.getFather(), "Father mismatch");
    }

    @Test
    void testGetHusband() {
        final FamilyNavigator navigator = new FamilyNavigator(family1);
        assertEquals(person1.getString(), navigator.getHusband().getToString(), "Husband mismatch");
    }

    @Test
    void testGetMother() {
        final FamilyNavigator navigator = new FamilyNavigator(family1);
        assertEquals(person2, navigator.getMother(), "Mother mismatch");
    }

    @Test
    void testGetWife() {
        final FamilyNavigator navigator = new FamilyNavigator(family1);
        assertEquals(person2.getString(), navigator.getWife().getToString(), "Wife mismatch");
    }

    @Test
    void testGetChildren() {
        final FamilyNavigator navigator = new FamilyNavigator(family1);
        final List<Person> children = navigator.getChildren();
        assertTrue(children.contains(person3) && children.size() == 1,
            "Expected child to be in children");
    }

    @Test
    void testNullFamily() {
        final FamC famc = new FamC(null, "F8888", null);
        final FamilyNavigator navigator = new FamilyNavigator(famc);
        assertFalse(navigator.getFamily().isSet(), "Expected null");
    }
}
