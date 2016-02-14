package org.schoellerfamily.gedbrowser.datamodel;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Dick Schoeller
 */
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
        Assert.assertEquals(person3, famS2.getSpouse(person2));
        Assert.assertEquals(person2, famS2.getSpouse(person3));
        Assert.assertFalse(famS2.getSpouse(person1).isSet());
        Assert.assertEquals(person2, famS2.getSpouse(null));
    }

    /** */
    @Test
    public void testGetFamilies() {
        final Family gottenFamily = famS2.getFamily();
        Assert.assertSame(family, gottenFamily);

        final FamS fams = new FamS(null, "F73");
        Assert.assertFalse(fams.getFamily().isSet());
    }

    /** */
    @Test
    public void testGetChildren() {
        List<Person> newList = famS2.getChildren();
        Assert.assertTrue(newList.contains(person1));
        newList = famS3.getChildren();
        Assert.assertTrue(newList.contains(person1));
    }

    /** */
    @Test
    public void testFamSGedObject() {
        FamS fams;
        fams = new FamS(null);
        Assert.assertNull(fams.getParent());
        Assert.assertEquals("", fams.getString());
        Assert.assertEquals("", fams.getToString());
        Assert.assertEquals("", fams.getFromString());
        Assert.assertFalse(fams.getSpouse(null).isSet());
        Assert.assertEquals(0, fams.getChildren().size());
        Assert.assertFalse(fams.getSpouse(null).isSet());

        fams = new FamS(person1);
        Assert.assertEquals(person1, fams.getParent());
        Assert.assertEquals("", fams.getString());
        Assert.assertEquals("", fams.getToString());
        Assert.assertEquals("I1", fams.getFromString());
        Assert.assertFalse(fams.getSpouse(person1).isSet());
        Assert.assertEquals(0, fams.getChildren().size());
        Assert.assertFalse(fams.getSpouse(null).isSet());
    }

    /** */
    @Test
    public void testFamSGedObjectString() {
        FamS fams;
        fams = new FamS(null, null);
        Assert.assertNull(fams.getParent());
        Assert.assertEquals("", fams.getString());
        Assert.assertEquals("", fams.getToString());
        Assert.assertEquals("", fams.getFromString());
        Assert.assertFalse(fams.getSpouse(null).isSet());
        Assert.assertEquals(0, fams.getChildren().size());
        Assert.assertFalse(fams.getSpouse(null).isSet());

        fams = new FamS(person1, null);
        Assert.assertEquals(person1, fams.getParent());
        Assert.assertEquals("", fams.getString());
        Assert.assertEquals("", fams.getToString());
        Assert.assertEquals("I1", fams.getFromString());
        Assert.assertFalse(fams.getSpouse(person1).isSet());
        Assert.assertEquals(0, fams.getChildren().size());
        Assert.assertFalse(fams.getSpouse(null).isSet());

        fams = new FamS(null, "");
        Assert.assertNull(fams.getParent());
        Assert.assertEquals("", fams.getString());
        Assert.assertEquals("", fams.getToString());
        Assert.assertEquals("", fams.getFromString());
        Assert.assertFalse(fams.getSpouse(null).isSet());
        Assert.assertEquals(0, fams.getChildren().size());
        Assert.assertFalse(fams.getSpouse(null).isSet());

        fams = new FamS(person1, "");
        Assert.assertEquals(person1, fams.getParent());
        Assert.assertEquals("", fams.getString());
        Assert.assertEquals("", fams.getToString());
        Assert.assertEquals("I1", fams.getFromString());
        Assert.assertFalse(fams.getSpouse(person1).isSet());
        Assert.assertEquals(0, fams.getChildren().size());
        Assert.assertFalse(fams.getSpouse(null).isSet());

        fams = new FamS(null, FAMS_TAG);
        Assert.assertNull(fams.getParent());
        Assert.assertEquals(FAMS_TAG, fams.getString());
        Assert.assertEquals("", fams.getToString());
        Assert.assertEquals("", fams.getFromString());
        Assert.assertFalse(fams.getSpouse(null).isSet());
        Assert.assertEquals(0, fams.getChildren().size());
        Assert.assertFalse(fams.getSpouse(null).isSet());

        fams = new FamS(person1, TEST_STRING_LUNK);
        Assert.assertEquals(person1, fams.getParent());
        Assert.assertEquals(TEST_STRING_LUNK, fams.getString());
        Assert.assertEquals("", fams.getToString());
        Assert.assertEquals("I1", fams.getFromString());
        Assert.assertFalse(fams.getSpouse(person1).isSet());
        Assert.assertEquals(0, fams.getChildren().size());
        Assert.assertFalse(fams.getSpouse(null).isSet());
    }

    /** */
    @Test
    public void testFamSGedObjectStringNull() {
        FamS fams;
        fams = new FamS(null, null, null);
        Assert.assertNull(fams.getParent());
        Assert.assertEquals("", fams.getString());
        Assert.assertEquals("", fams.getToString());
        Assert.assertEquals("", fams.getFromString());
        Assert.assertFalse(fams.getSpouse(null).isSet());
        Assert.assertEquals(0, fams.getChildren().size());

        fams = new FamS(person1, null, null);
        Assert.assertEquals(person1, fams.getParent());
        Assert.assertEquals("", fams.getString());
        Assert.assertEquals("", fams.getToString());
        Assert.assertEquals("I1", fams.getFromString());
        Assert.assertFalse(fams.getSpouse(person1).isSet());
        Assert.assertEquals(0, fams.getChildren().size());

        fams = new FamS(null, "", null);
        Assert.assertNull(fams.getParent());
        Assert.assertEquals("", fams.getString());
        Assert.assertEquals("", fams.getToString());
        Assert.assertEquals("", fams.getFromString());
        Assert.assertFalse(fams.getSpouse(null).isSet());
        Assert.assertEquals(0, fams.getChildren().size());

        fams = new FamS(person1, "", null);
        Assert.assertEquals(person1, fams.getParent());
        Assert.assertEquals("", fams.getString());
        Assert.assertEquals("", fams.getToString());
        Assert.assertEquals("I1", fams.getFromString());
        Assert.assertFalse(fams.getSpouse(person1).isSet());
        Assert.assertEquals(0, fams.getChildren().size());

        fams = new FamS(null, FAMS_TAG, null);
        Assert.assertNull(fams.getParent());
        Assert.assertEquals(FAMS_TAG, fams.getString());
        Assert.assertEquals("", fams.getToString());
        Assert.assertEquals("", fams.getFromString());
        Assert.assertFalse(fams.getSpouse(null).isSet());
        Assert.assertEquals(0, fams.getChildren().size());

        fams = new FamS(person1, TEST_STRING_LUNK, null);
        Assert.assertEquals(person1, fams.getParent());
        Assert.assertEquals(TEST_STRING_LUNK, fams.getString());
        Assert.assertEquals("", fams.getToString());
        Assert.assertEquals("I1", fams.getFromString());
        Assert.assertFalse(fams.getSpouse(person1).isSet());
        Assert.assertEquals(0, fams.getChildren().size());
    }

    /** */
    @Test
    public void testFamSGedObjectStringBlank() {
        FamS fams;
        fams = new FamS(null, null, new ObjectId(""));
        Assert.assertNull(fams.getParent());
        Assert.assertEquals("", fams.getString());
        Assert.assertEquals("", fams.getToString());
        Assert.assertEquals("", fams.getFromString());
        Assert.assertFalse(fams.getSpouse(null).isSet());
        Assert.assertEquals(0, fams.getChildren().size());

        fams = new FamS(person1, null, new ObjectId(""));
        Assert.assertEquals(person1, fams.getParent());
        Assert.assertEquals("", fams.getString());
        Assert.assertEquals("", fams.getToString());
        Assert.assertEquals("I1", fams.getFromString());
        Assert.assertFalse(fams.getSpouse(person1).isSet());
        Assert.assertEquals(0, fams.getChildren().size());

        fams = new FamS(null, "", new ObjectId(""));
        Assert.assertNull(fams.getParent());
        Assert.assertEquals("", fams.getString());
        Assert.assertEquals("", fams.getToString());
        Assert.assertEquals("", fams.getFromString());
        Assert.assertFalse(fams.getSpouse(null).isSet());
        Assert.assertEquals(0, fams.getChildren().size());

        fams = new FamS(person1, "", new ObjectId(""));
        Assert.assertEquals(person1, fams.getParent());
        Assert.assertEquals("", fams.getString());
        Assert.assertEquals("", fams.getToString());
        Assert.assertEquals("I1", fams.getFromString());
        Assert.assertFalse(fams.getSpouse(person1).isSet());
        Assert.assertEquals(0, fams.getChildren().size());

        fams = new FamS(null, FAMS_TAG, new ObjectId(""));
        Assert.assertNull(fams.getParent());
        Assert.assertEquals(FAMS_TAG, fams.getString());
        Assert.assertEquals("", fams.getToString());
        Assert.assertEquals("", fams.getFromString());
        Assert.assertFalse(fams.getSpouse(null).isSet());
        Assert.assertEquals(0, fams.getChildren().size());

        fams = new FamS(person1, TEST_STRING_LUNK, new ObjectId(""));
        Assert.assertEquals(person1, fams.getParent());
        Assert.assertEquals(TEST_STRING_LUNK, fams.getString());
        Assert.assertEquals("", fams.getToString());
        Assert.assertEquals("I1", fams.getFromString());
        Assert.assertFalse(fams.getSpouse(person1).isSet());
        Assert.assertEquals(0, fams.getChildren().size());
    }

    /** */
    @Test
    public void testFamSGedObjectStringString() {
        FamS fams;
        fams = new FamS(null, null, new ObjectId("F2"));
        Assert.assertNull(fams.getParent());
        Assert.assertEquals("", fams.getString());
        Assert.assertEquals("F2", fams.getToString());
        Assert.assertEquals("", fams.getFromString());
        Assert.assertFalse(fams.getSpouse(null).isSet());
        Assert.assertEquals(0, fams.getChildren().size());

        fams = new FamS(person1, null, new ObjectId("F3"));
        Assert.assertEquals(person1, fams.getParent());
        Assert.assertEquals("", fams.getString());
        Assert.assertEquals("F3", fams.getToString());
        Assert.assertEquals("I1", fams.getFromString());
        Assert.assertFalse(fams.getSpouse(person1).isSet());
        Assert.assertEquals(0, fams.getChildren().size());

        fams = new FamS(null, "", new ObjectId("F1"));
        Assert.assertNull(fams.getParent());
        Assert.assertEquals("", fams.getString());
        Assert.assertEquals("F1", fams.getToString());
        Assert.assertEquals("", fams.getFromString());
        Assert.assertFalse(fams.getSpouse(null).isSet());
        Assert.assertEquals(0, fams.getChildren().size());

        fams = new FamS(person1, "", new ObjectId("F2"));
        Assert.assertEquals(person1, fams.getParent());
        Assert.assertEquals("", fams.getString());
        Assert.assertEquals("F2", fams.getToString());
        Assert.assertEquals("I1", fams.getFromString());
        Assert.assertFalse(fams.getSpouse(person1).isSet());
        Assert.assertEquals(0, fams.getChildren().size());

        fams = new FamS(null, FAMS_TAG, new ObjectId("F3"));
        Assert.assertNull(fams.getParent());
        Assert.assertEquals(FAMS_TAG, fams.getString());
        Assert.assertEquals("F3", fams.getToString());
        Assert.assertEquals("", fams.getFromString());
        Assert.assertFalse(fams.getSpouse(null).isSet());
        Assert.assertEquals(0, fams.getChildren().size());

        fams = new FamS(person1, TEST_STRING_LUNK, new ObjectId("F1"));
        Assert.assertEquals(person1, fams.getParent());
        Assert.assertEquals(TEST_STRING_LUNK, fams.getString());
        Assert.assertEquals("F1", fams.getToString());
        Assert.assertEquals("I1", fams.getFromString());
        Assert.assertFalse(fams.getSpouse(person1).isSet());
        Assert.assertEquals(1, fams.getChildren().size());
    }

    // TODO we don't have a test where FamS.getSpouse returns a set person.
}
