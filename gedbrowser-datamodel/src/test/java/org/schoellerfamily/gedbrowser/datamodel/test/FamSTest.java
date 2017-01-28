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
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.TooManyStaticImports", "PMD.CommentSize" })
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
        family = builder.createFamily1();
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
        assertEquals("Person mismatch", person3, famS2.getSpouse(person2));
    }

    /** */
    @Test
    public void testGetSpouseFromParent() {
        assertEquals("Person mismatch", person2, famS2.getSpouse(person3));
    }

    /** */
    @Test
    public void testGetSpouseNotSetFromUnrelated() {
        assertFalse("Should be unset person when not from one of the spouses",
                famS2.getSpouse(person1).isSet());
    }

    /** */
    @Test
    public void testGetSpouseIsParentFromNull() {
        assertEquals("Person mismatch", person2, famS2.getSpouse(null));
    }

    /** */
    @Test
    public void testGetFamilies() {
        final Family gottenFamily = famS2.getFamily();
        assertSame("Mismatched family", family, gottenFamily);
    }

    /** */
    @Test
    public void testGetFamiliesUnsetWhenUnattached() {
        final FamS fams = new FamS(null, "F73");
        assertFalse("Family should not be set", fams.getFamily().isSet());
    }

    /** */
    @Test
    public void testGetChildrenFromHusband() {
        final List<Person> newList = famS2.getChildren();
        assertTrue("List should contain person1", newList.contains(person1));
    }

    /** */
    @Test
    public void testGetChildrenFromWife() {
        final List<Person> newList = famS3.getChildren();
        assertTrue("List should contain person1", newList.contains(person1));
    }

    /** */
    @Test
    public void testFamSConstructNull() {
        final FamS fams = new FamS(null);
        assertMatch(fams, null, "", "", "", false, 0, false);
    }

    /** */
    @Test
    public void testFamSConstructPerson() {
        final FamS fams = new FamS(person1);
        assertMatch(fams, person1, "", "", "I1", false, 0, false);
    }

    /** */
    @Test
    public void testFamSConstructNullNull() {
        final FamS fams = new FamS(null, null);
        assertMatch(fams, null, "", "", "", false, 0, false);
    }

    /** */
    @Test
    public void testFamSConstructPersonNull() {
        final FamS fams = new FamS(person1, null);
        assertMatch(fams, person1, "", "", "I1", false, 0, false);
    }

    /** */
    @Test
    public void testFamSConstructNullBlank() {
        final FamS fams = new FamS(null, "");
        assertMatch(fams, null, "", "", "", false, 0, false);
    }

    /** */
    @Test
    public void testFamSConstructPersonBlank() {
        final FamS fams = new FamS(person1, "");
        assertMatch(fams, person1, "", "", "I1", false, 0, false);
    }

    /** */
    @Test
    public void testFamSConstructNullString() {
        final FamS fams = new FamS(null, "FamS");
        assertMatch(fams, null, "FamS", "", "", false, 0, false);
    }

    /** */
    @Test
    public void testFamSConstructPersonString() {
        final FamS fams = new FamS(person1, "Lunk");
        assertMatch(fams, person1, "Lunk", "", "I1", false, 0, false);
    }

    /** */
    @Test
    public void testFamSConstructorNullNullNull() {
        final FamS fams = new FamS(null, null, null);
        assertMatch(fams, null, "", "", "", false, 0, false);
    }

    /** */
    @Test
    public void testFamSConstructorPersonNullNull() {
        final FamS fams = new FamS(person1, null, null);
        assertMatch(fams, person1, "", "", "I1", false, 0, false);
    }

    /** */
    @Test
    public void testFamSConstructorNullBlankNull() {
        final FamS fams = new FamS(null, "", null);
        assertMatch(fams, null, "", "", "", false, 0, false);
    }

    /** */
    @Test
    public void testFamSConstructorPersonBlankNull() {
        final FamS fams = new FamS(person1, "", null);
        assertMatch(fams, person1, "", "", "I1", false, 0, false);
    }

    /** */
    @Test
    public void testFamSConstructorNullStringNull() {
        final FamS fams = new FamS(null, "FamS", null);
        assertMatch(fams, null, "FamS", "", "", false, 0, false);
    }

    /** */
    @Test
    public void testFamSConstructorPersonStringNull() {
        final FamS fams = new FamS(person1, "Lunk", null);
        assertMatch(fams, person1, "Lunk", "", "I1", false, 0, false);
    }

    /** */
    @Test
    public void testFamSConstructorNullNullBlankId() {
        final FamS fams = new FamS(null, null, new ObjectId(""));
        assertMatch(fams, null, "", "", "", false, 0, false);
    }

    /** */
    @Test
    public void testFamSConstructorPersonNullBlankId() {
        final FamS fams = new FamS(person1, null, new ObjectId(""));
        assertMatch(fams, person1, "", "", "I1", false, 0, false);
    }

    /** */
    @Test
    public void testFamSConstructorNullBlankBlankId() {
        final FamS fams = new FamS(null, "", new ObjectId(""));
        assertMatch(fams, null, "", "", "", false, 0, false);
    }

    /** */
    @Test
    public void testFamSConstructorPersonBlankBlankId() {
        final FamS fams = new FamS(person1, "", new ObjectId(""));
        assertMatch(fams, person1, "", "", "I1", false, 0, false);
    }

    /** */
    @Test
    public void testFamSConstructorNullStringBlankId() {
        final FamS fams = new FamS(null, "FamS", new ObjectId(""));
        assertMatch(fams, null, "FamS", "", "", false, 0, false);
    }

    /** */
    @Test
    public void testFamSConstructorPersonStringBlankId() {
        final FamS fams = new FamS(person1, "Lunk", new ObjectId(""));
        assertMatch(fams, person1, "Lunk", "", "I1", false, 0, false);
    }

    /** */
    @Test
    public void testFamSConstructorNullNullId() {
        final FamS fams = new FamS(null, null, new ObjectId("F2"));
        assertMatch(fams, null, "", "F2", "", false, 0, false);
    }

    /** */
    @Test
    public void testFamSConstructorPersonNullId() {
        final FamS fams = new FamS(person1, null, new ObjectId("F3"));
        assertMatch(fams, person1, "", "F3", "I1", false, 0, false);
    }

    /** */
    @Test
    public void testFamSConstructorNullBlankId() {
        final FamS fams = new FamS(null, "", new ObjectId("F1"));
        assertMatch(fams, null, "", "F1", "", false, 0, false);
    }

    /** */
    @Test
    public void testFamSConstructorPersonBlankId() {
        final FamS fams = new FamS(person1, "", new ObjectId("F2"));
        assertMatch(fams, person1, "", "F2", "I1", false, 0, false);
    }

    /** */
    @Test
    public void testFamSConstructorNullStringId() {
        final FamS fams = new FamS(null, "FamS", new ObjectId("F3"));
        assertMatch(fams, null, "FamS", "F3", "", false, 0, false);
    }

    /** */
    @Test
    public void testFamSConstructorPersonStringId() {
        final FamS fams =
                new FamS(person1, "Lunk", new ObjectId("F1"));
        assertMatch(fams, person1, "Lunk", "F1", "I1", false, 1, true);
    }

    /**
     * @param fams the FamS to check
     * @param parent the expected parent person of the fams
     * @param string the expected main string
     * @param toString the expected to string
     * @param fromString the expected from string
     * @param spouseSet whether spouse is expected to be set
     * @param childrenSize expected number of children
     * @param spouseNullSet whether spouse is expected to be set in null search
     */
    // TODO I know it's a lot of strings.
    // CHECKSTYLE:OFF
    private void assertMatch(final FamS fams,
            final Person parent,  final String string,
            final String toString, final String fromString,
            final boolean spouseSet, final int childrenSize,
            final boolean spouseNullSet) {
        // CHECKSTYLE:ON
        assertEquals("Parent mismatch", parent, fams.getParent());
        assertEquals("String mismatch", string, fams.getString());
        assertEquals("To string mismatch", toString, fams.getToString());
        assertEquals("From string mismatch", fromString, fams.getFromString());
        assertEquals("Spouse set mismatch",
                spouseSet, fams.getSpouse(parent).isSet());
        assertEquals("Expected " + childrenSize + " children",
                childrenSize, fams.getChildren().size());
        assertEquals("Spouse set mismatch",
                spouseNullSet, fams.getSpouse(null).isSet());
    }

    // TODO we don't have a test where FamS.getSpouse returns a set person.
}
