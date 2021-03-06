package org.schoellerfamily.gedbrowser.datamodel.navigator.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.navigator.PersonNavigator;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
public final class PersonNavigatorTest {
    /** */
    private transient Person person1;
    /** */
    private transient Person person3;
    /** */
    private transient Family family6;
    /** */
    private transient Person person6;
    /** */
    private transient Person person7;

    /** */
    private final GedObjectBuilder builder = new GedObjectBuilder();

    /** */
    @Before
    public void setUp() {
        person1 =
                builder.createPerson("I1", "Richard John/Schoeller/");
        final Attribute attr =
                new Attribute(person1, "Restriction", "confidential");
        person1.insert(attr);

        builder.createPerson("I2", "Lisa Hope/Robinson/");

        person3 =
                builder.createPerson(
                        "I3", "Karl Frederick/Schoeller/Jr.");

        final Person person4 =
                builder.createPerson("I4");
        builder.createPersonEvent(person4, "Birth");
        builder.createPersonEvent(person4, "Death");

        final Person person5 =
                builder.createPerson(
                        "I5", "Whosis/Schoeller/Jr./Huh?");
        builder.createPersonEvent(person5, "Birth", "1 JAN 1900");
        builder.createPersonEvent(person5, "Death", "1 JAN 1950");

        family6 = builder.createFamily("F6");
        final Family family2 = family6;
        final Person person12 = person3;
        builder.addChildToFamily(family2, person12);

        person6 = builder.createPerson("I6");
        person7 = builder.createPerson("I7");
        final Family family = family6;
        final Person person = person6;

        builder.addHusbandToFamily(family, person);
        final Family family1 = family6;
        final Person person2 = person7;
        builder.addWifeToFamily(family1, person2);

        final Person person8 =
                builder.createPerson("I8", "Same/Name/");
        builder.createPersonEvent(person8, "Birth", "1 JAN 1950");

        final Person person9 =
                builder.createPerson("I9", "Same/Name/");
        builder.createPersonEvent(person9, "Birth", "1 JAN 1940");

        final Person person10 =
                builder.createPerson("I10", "Same/Name/");
        builder.createPersonEvent(person10, "Birth", "1 JAN 1950");

        final Person person11 =
                builder.createPerson("I11", "Different/Name/");
        builder.createPersonEvent(person11, "Birth", "1 JAN 1930");
    }

    /** */
    @Test
    public void testPerson() {
        final PersonNavigator navigator = new PersonNavigator(person1);
        assertSame("Expected same person", person1, navigator.getPerson());
    }

    /** */
    @Test
    public void testGetFather() {
        final PersonNavigator navigator = new PersonNavigator(person3);
        assertEquals("Expected to find father", person6, navigator.getFather());
    }

    /** */
    @Test
    public void testGetMother() {
        final PersonNavigator navigator = new PersonNavigator(person3);
        assertEquals("Expected to find mother", person7, navigator.getMother());
    }

    /** */
    @Test
    public void testGetFatherUnset() {
        final PersonNavigator navigator = new PersonNavigator(person6);
        assertFalse("Expected not to find father",
                navigator.getFather().isSet());
    }

    /** */
    @Test
    public void testGetMotherUnset() {
        final PersonNavigator navigator = new PersonNavigator(person6);
        assertFalse("Expected not to find mother",
                navigator.getMother().isSet());
    }

    /** */
    @Test
    public void testGetHusbandsFamily() {
        final PersonNavigator navigator = new PersonNavigator(person6);
        final List<Family> list6 = navigator.getFamilies();
        assertTrue(
                "Should have found husband's family", list6.contains(family6));
    }

    /** */
    @Test
    public void testGetWifesFamily() {
        final PersonNavigator navigator = new PersonNavigator(person7);
        final List<Family> list7 = navigator.getFamilies();
        assertTrue("Should have found wife's family", list7.contains(family6));
    }
}
