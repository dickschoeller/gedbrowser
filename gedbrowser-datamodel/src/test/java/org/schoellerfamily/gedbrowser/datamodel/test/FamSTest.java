package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Wife;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.TooManyStaticImports")
public final class FamSTest {
    /** */
    private static final String TEST_STRING_LUNK = "Lunk";
    /** */
    private static final String FAMS_TAG = "FamS";
    /** */
    private final transient Root root = new Root(null, "Root");
    /** */
    private final transient Person person1 = new Person(root,
            new ObjectId("I1"));
    /** */
    private final transient Person person2 = new Person(root,
            new ObjectId("I2"));
    /** */
    private final transient Person person3 = new Person(root,
            new ObjectId("I3"));
    /** */
    private final transient Family family = new Family(root,
            new ObjectId("F1"));
    /** */
    private final transient FamC famC = new FamC(person1, "FAMC",
            new ObjectId("F1"));
    /** */
    private final transient FamS famS2 = new FamS(person2, "FAMS",
            new ObjectId("F1"));
    /** */
    private final transient FamS famS3 = new FamS(person3, "FAMS",
            new ObjectId("F1"));
    /** */
    private final transient Child child = new Child(family, "Child",
            new ObjectId("I1"));
    /** */
    private final transient Husband husband = new Husband(family, "Husband",
            new ObjectId("I2"));
    /** */
    private final transient Wife wife = new Wife(family, "Wife",
            new ObjectId("I3"));

    /** */
    @Before
    public void setUp() {
        root.insert(null, person1);
        root.insert(null, person2);
        root.insert(null, person3);
        root.insert(null, family);

        family.insert(child);
        family.insert(husband);
        family.insert(wife);

        person1.insert(famC);
        person2.insert(famS2);
        person3.insert(famS3);
    }

    /** */
    @Test
    public void testGetSpouse() {
        assertEquals(person3, famS2.getSpouse(person2));
        assertEquals(person2, famS2.getSpouse(person3));
        assertFalse(famS2.getSpouse(person1).isSet());
        assertEquals(person2, famS2.getSpouse(null));
    }

    /** */
    @Test
    public void testGetFamilies() {
        final Family gottenFamily = famS2.getFamily();
        assertSame(family, gottenFamily);

        final FamS fams = new FamS(null, "F73");
        assertFalse(fams.getFamily().isSet());
    }

    /** */
    @Test
    public void testGetChildren() {
        List<Person> newList = famS2.getChildren();
        assertTrue(newList.contains(person1));
        newList = famS3.getChildren();
        assertTrue(newList.contains(person1));
    }

    /** */
    @Test
    public void testFamSGedObject() {
        FamS fams;
        fams = new FamS(null);
        assertNull(fams.getParent());
        assertEquals("", fams.getString());
        assertEquals("", fams.getToString());
        assertEquals("", fams.getFromString());
        assertFalse(fams.getSpouse(null).isSet());
        assertEquals(0, fams.getChildren().size());
        assertFalse(fams.getSpouse(null).isSet());

        fams = new FamS(person1);
        assertEquals(person1, fams.getParent());
        assertEquals("", fams.getString());
        assertEquals("", fams.getToString());
        assertEquals("I1", fams.getFromString());
        assertFalse(fams.getSpouse(person1).isSet());
        assertEquals(0, fams.getChildren().size());
        assertFalse(fams.getSpouse(null).isSet());
    }

    /** */
    @Test
    public void testFamSGedObjectString() {
        FamS fams;
        fams = new FamS(null, null);
        assertNull(fams.getParent());
        assertEquals("", fams.getString());
        assertEquals("", fams.getToString());
        assertEquals("", fams.getFromString());
        assertFalse(fams.getSpouse(null).isSet());
        assertEquals(0, fams.getChildren().size());
        assertFalse(fams.getSpouse(null).isSet());

        fams = new FamS(person1, null);
        assertEquals(person1, fams.getParent());
        assertEquals("", fams.getString());
        assertEquals("", fams.getToString());
        assertEquals("I1", fams.getFromString());
        assertFalse(fams.getSpouse(person1).isSet());
        assertEquals(0, fams.getChildren().size());
        assertFalse(fams.getSpouse(null).isSet());

        fams = new FamS(null, "");
        assertNull(fams.getParent());
        assertEquals("", fams.getString());
        assertEquals("", fams.getToString());
        assertEquals("", fams.getFromString());
        assertFalse(fams.getSpouse(null).isSet());
        assertEquals(0, fams.getChildren().size());
        assertFalse(fams.getSpouse(null).isSet());

        fams = new FamS(person1, "");
        assertEquals(person1, fams.getParent());
        assertEquals("", fams.getString());
        assertEquals("", fams.getToString());
        assertEquals("I1", fams.getFromString());
        assertFalse(fams.getSpouse(person1).isSet());
        assertEquals(0, fams.getChildren().size());
        assertFalse(fams.getSpouse(null).isSet());

        fams = new FamS(null, FAMS_TAG);
        assertNull(fams.getParent());
        assertEquals(FAMS_TAG, fams.getString());
        assertEquals("", fams.getToString());
        assertEquals("", fams.getFromString());
        assertFalse(fams.getSpouse(null).isSet());
        assertEquals(0, fams.getChildren().size());
        assertFalse(fams.getSpouse(null).isSet());

        fams = new FamS(person1, TEST_STRING_LUNK);
        assertEquals(person1, fams.getParent());
        assertEquals(TEST_STRING_LUNK, fams.getString());
        assertEquals("", fams.getToString());
        assertEquals("I1", fams.getFromString());
        assertFalse(fams.getSpouse(person1).isSet());
        assertEquals(0, fams.getChildren().size());
        assertFalse(fams.getSpouse(null).isSet());
    }

    /** */
    @Test
    public void testFamSGedObjectStringNull() {
        FamS fams;
        fams = new FamS(null, null, null);
        assertNull(fams.getParent());
        assertEquals("", fams.getString());
        assertEquals("", fams.getToString());
        assertEquals("", fams.getFromString());
        assertFalse(fams.getSpouse(null).isSet());
        assertEquals(0, fams.getChildren().size());

        fams = new FamS(person1, null, null);
        assertEquals(person1, fams.getParent());
        assertEquals("", fams.getString());
        assertEquals("", fams.getToString());
        assertEquals("I1", fams.getFromString());
        assertFalse(fams.getSpouse(person1).isSet());
        assertEquals(0, fams.getChildren().size());

        fams = new FamS(null, "", null);
        assertNull(fams.getParent());
        assertEquals("", fams.getString());
        assertEquals("", fams.getToString());
        assertEquals("", fams.getFromString());
        assertFalse(fams.getSpouse(null).isSet());
        assertEquals(0, fams.getChildren().size());

        fams = new FamS(person1, "", null);
        assertEquals(person1, fams.getParent());
        assertEquals("", fams.getString());
        assertEquals("", fams.getToString());
        assertEquals("I1", fams.getFromString());
        assertFalse(fams.getSpouse(person1).isSet());
        assertEquals(0, fams.getChildren().size());

        fams = new FamS(null, FAMS_TAG, null);
        assertNull(fams.getParent());
        assertEquals(FAMS_TAG, fams.getString());
        assertEquals("", fams.getToString());
        assertEquals("", fams.getFromString());
        assertFalse(fams.getSpouse(null).isSet());
        assertEquals(0, fams.getChildren().size());

        fams = new FamS(person1, TEST_STRING_LUNK, null);
        assertEquals(person1, fams.getParent());
        assertEquals(TEST_STRING_LUNK, fams.getString());
        assertEquals("", fams.getToString());
        assertEquals("I1", fams.getFromString());
        assertFalse(fams.getSpouse(person1).isSet());
        assertEquals(0, fams.getChildren().size());
    }

    /** */
    @Test
    public void testFamSGedObjectStringBlank() {
        FamS fams;
        fams = new FamS(null, null, new ObjectId(""));
        assertNull(fams.getParent());
        assertEquals("", fams.getString());
        assertEquals("", fams.getToString());
        assertEquals("", fams.getFromString());
        assertFalse(fams.getSpouse(null).isSet());
        assertEquals(0, fams.getChildren().size());

        fams = new FamS(person1, null, new ObjectId(""));
        assertEquals(person1, fams.getParent());
        assertEquals("", fams.getString());
        assertEquals("", fams.getToString());
        assertEquals("I1", fams.getFromString());
        assertFalse(fams.getSpouse(person1).isSet());
        assertEquals(0, fams.getChildren().size());

        fams = new FamS(null, "", new ObjectId(""));
        assertNull(fams.getParent());
        assertEquals("", fams.getString());
        assertEquals("", fams.getToString());
        assertEquals("", fams.getFromString());
        assertFalse(fams.getSpouse(null).isSet());
        assertEquals(0, fams.getChildren().size());

        fams = new FamS(person1, "", new ObjectId(""));
        assertEquals(person1, fams.getParent());
        assertEquals("", fams.getString());
        assertEquals("", fams.getToString());
        assertEquals("I1", fams.getFromString());
        assertFalse(fams.getSpouse(person1).isSet());
        assertEquals(0, fams.getChildren().size());

        fams = new FamS(null, FAMS_TAG, new ObjectId(""));
        assertNull(fams.getParent());
        assertEquals(FAMS_TAG, fams.getString());
        assertEquals("", fams.getToString());
        assertEquals("", fams.getFromString());
        assertFalse(fams.getSpouse(null).isSet());
        assertEquals(0, fams.getChildren().size());

        fams = new FamS(person1, TEST_STRING_LUNK, new ObjectId(""));
        assertEquals(person1, fams.getParent());
        assertEquals(TEST_STRING_LUNK, fams.getString());
        assertEquals("", fams.getToString());
        assertEquals("I1", fams.getFromString());
        assertFalse(fams.getSpouse(person1).isSet());
        assertEquals(0, fams.getChildren().size());
    }

    /** */
    @Test
    public void testFamSGedObjectStringString() {
        FamS fams;
        fams = new FamS(null, null, new ObjectId("F2"));
        assertNull(fams.getParent());
        assertEquals("", fams.getString());
        assertEquals("F2", fams.getToString());
        assertEquals("", fams.getFromString());
        assertFalse(fams.getSpouse(null).isSet());
        assertEquals(0, fams.getChildren().size());

        fams = new FamS(person1, null, new ObjectId("F3"));
        assertEquals(person1, fams.getParent());
        assertEquals("", fams.getString());
        assertEquals("F3", fams.getToString());
        assertEquals("I1", fams.getFromString());
        assertFalse(fams.getSpouse(person1).isSet());
        assertEquals(0, fams.getChildren().size());

        fams = new FamS(null, "", new ObjectId("F1"));
        assertNull(fams.getParent());
        assertEquals("", fams.getString());
        assertEquals("F1", fams.getToString());
        assertEquals("", fams.getFromString());
        assertFalse(fams.getSpouse(null).isSet());
        assertEquals(0, fams.getChildren().size());

        fams = new FamS(person1, "", new ObjectId("F2"));
        assertEquals(person1, fams.getParent());
        assertEquals("", fams.getString());
        assertEquals("F2", fams.getToString());
        assertEquals("I1", fams.getFromString());
        assertFalse(fams.getSpouse(person1).isSet());
        assertEquals(0, fams.getChildren().size());

        fams = new FamS(null, FAMS_TAG, new ObjectId("F3"));
        assertNull(fams.getParent());
        assertEquals(FAMS_TAG, fams.getString());
        assertEquals("F3", fams.getToString());
        assertEquals("", fams.getFromString());
        assertFalse(fams.getSpouse(null).isSet());
        assertEquals(0, fams.getChildren().size());

        fams = new FamS(person1, TEST_STRING_LUNK, new ObjectId("F1"));
        assertEquals(person1, fams.getParent());
        assertEquals(TEST_STRING_LUNK, fams.getString());
        assertEquals("F1", fams.getToString());
        assertEquals("I1", fams.getFromString());
        assertFalse(fams.getSpouse(person1).isSet());
        assertEquals(1, fams.getChildren().size());
    }

    // TODO we don't have a test where FamS.getSpouse returns a set person.
}
