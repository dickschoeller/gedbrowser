package org.schoellerfamily.gedbrowser.datamodel.navigator.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.navigator.FamilyNavigator;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
public class FamilyNavigatorTest {
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
        family1 = builder.createFamily1();
        person1 = builder.createPerson1();
        person2 = builder.createPerson2();
        person3 = builder.createPerson3();
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        builder.addChildToFamily(family1, person3);
    }

    /** */
    @Test
    public void testGetFather() {
        final FamilyNavigator navigator = new FamilyNavigator(family1);
        assertEquals("Father mismatch", person1, navigator.getFather());
    }

    /** */
    @Test
    public void testGetHusband() {
        final FamilyNavigator navigator = new FamilyNavigator(family1);
        assertEquals("Husband mismatch",
                person1.getString(), navigator.getHusband().getToString());
    }

    /** */
    @Test
    public void testGetMother() {
        final FamilyNavigator navigator = new FamilyNavigator(family1);
        assertEquals("Mother mismatch", person2, navigator.getMother());
    }

    /** */
    @Test
    public void testGetWife() {
        final FamilyNavigator navigator = new FamilyNavigator(family1);
        assertEquals("Wife mismatch",
                person2.getString(), navigator.getWife().getToString());
    }

    /** */
    @Test
    public void testGetChildren() {
        final FamilyNavigator navigator = new FamilyNavigator(family1);
        final List<Person> children = navigator.getChildren();
        assertTrue("Expected child to be in children",
                children.contains(person3) && children.size() == 1);
    }

    /** */
    @Test
    public void testNullFamily() {
        final FamC famc = new FamC(null, "F8888");
        final FamilyNavigator navigator = new FamilyNavigator(famc);
        assertFalse("Expected null", navigator.getFamily().isSet());
    }
}
