package org.schoellerfamily.gedbrowser.datamodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Dick Schoeller
 */
public final class FamCTest {
    /** */
    private static final String SHOULD_BE_FAMC = "Should be a FAMC";
    /** */
    private static final String SHOULD_BE_I1 = "Should be ID of person";
    /** */
    private static final String SHOULD_BE_EMPTY = "Should be empty";
    /** */
    private static final ObjectId XREF_NULL = new ObjectId("");
    /** */
    private static final ObjectId XREF_FAM_1 = new ObjectId("F1");
    /** */
    private static final String TEST_LUNK = "Lunk";
    /** */
    private static final String FAMC_TAG = "FamC";
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
    private final transient Family family = new Family(root, XREF_FAM_1);
    /** */
    private final transient FamC famC = new FamC(person1, "FAMC", XREF_FAM_1);
    /** */
    private final transient FamS famS2 = new FamS(person2, "FAMS", XREF_FAM_1);
    /** */
    private final transient FamS famS3 = new FamS(person3, "FAMS", XREF_FAM_1);
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
    public void testGetFather() {
        final FamC dummy = new FamC(null);
        assertFalse(dummy.getFather().isSet());
        assertEquals(person2, famC.getFather());
    }

    /** */
    @Test
    public void testGetMother() {
        final FamC dummy = new FamC(null);
        assertFalse(dummy.getMother().isSet());
        assertEquals(person3, famC.getMother());
    }

    /** */
    @Test
    public void testFamCGedObject() {
        FamC famc;
        famc = new FamC(null);
        assertNull(famc.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getString());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getToString());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getFromString());

        famc = new FamC(person1);
        assertEquals(person1, famc.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getString());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getToString());
        assertEquals(SHOULD_BE_I1, "I1", famc.getFromString());
    }

    /** */
    @Test
    public void testFamCGedObjectString() {
        FamC famc;
        famc = new FamC(null, null);
        assertNull(famc.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getString());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getToString());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getFromString());

        famc = new FamC(person1, null);
        assertEquals(person1, famc.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getString());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getToString());
        assertEquals(SHOULD_BE_I1, "I1", famc.getFromString());

        famc = new FamC(null, "");
        assertNull(famc.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getString());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getToString());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getFromString());

        famc = new FamC(person1, "");
        assertEquals(person1, famc.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getString());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getToString());
        assertEquals(SHOULD_BE_I1, "I1", famc.getFromString());

        famc = new FamC(null, FAMC_TAG);
        assertNull(famc.getParent());
        assertEquals(SHOULD_BE_FAMC, FAMC_TAG, famc.getString());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getToString());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getFromString());

        famc = new FamC(person1, TEST_LUNK);
        assertEquals(person1, famc.getParent());
        assertEquals(TEST_LUNK, famc.getString());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getToString());
        assertEquals(SHOULD_BE_I1, "I1", famc.getFromString());
    }

    /** */
    @Test
    public void testFamCGedObjectStringNull() {
        FamC famc;

        famc = new FamC(null, null, null);
        assertNull(famc.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getString());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getToString());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getFromString());

        famc = new FamC(person1, null, null);
        assertEquals(person1, famc.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getString());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getToString());
        assertEquals(SHOULD_BE_I1, "I1", famc.getFromString());

        famc = new FamC(null, "", null);
        assertNull(famc.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getString());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getToString());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getFromString());

        famc = new FamC(person1, "", null);
        assertEquals(person1, famc.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getString());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getToString());
        assertEquals(SHOULD_BE_I1, "I1", famc.getFromString());

        famc = new FamC(null, FAMC_TAG, null);
        assertNull(famc.getParent());
        assertEquals(SHOULD_BE_FAMC, FAMC_TAG, famc.getString());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getToString());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getFromString());

        famc = new FamC(person1, TEST_LUNK, null);
        assertEquals(person1, famc.getParent());
        assertEquals(TEST_LUNK, famc.getString());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getToString());
        assertEquals(SHOULD_BE_I1, "I1", famc.getFromString());
    }

    /** */
    @Test
    public void testFamCGedObjectStringBlank() {
        FamC famc;

        famc = new FamC(null, null, XREF_NULL);
        assertNull(famc.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getString());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getToString());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getFromString());

        famc = new FamC(person1, null, XREF_NULL);
        assertEquals(person1, famc.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getString());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getToString());
        assertEquals(SHOULD_BE_I1, "I1", famc.getFromString());

        famc = new FamC(null, "", XREF_NULL);
        assertNull(famc.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getString());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getToString());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getFromString());

        famc = new FamC(person1, "", XREF_NULL);
        assertEquals(person1, famc.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getString());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getToString());
        assertEquals(SHOULD_BE_I1, "I1", famc.getFromString());

        famc = new FamC(null, FAMC_TAG, XREF_NULL);
        assertNull(famc.getParent());
        assertEquals(SHOULD_BE_FAMC, FAMC_TAG, famc.getString());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getToString());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getFromString());

        famc = new FamC(person1, TEST_LUNK, XREF_NULL);
        assertEquals(person1, famc.getParent());
        assertEquals(TEST_LUNK, famc.getString());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getToString());
        assertEquals(SHOULD_BE_I1, "I1", famc.getFromString());
    }

    /** */
    @Test
    public void testFamCGedObjectStringString() {
        FamC famc;

        famc = new FamC(null, null, new ObjectId("I2"));
        assertNull(famc.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getString());
        assertEquals("I2", famc.getToString());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getFromString());

        famc = new FamC(person1, null, new ObjectId("I3"));
        assertEquals(person1, famc.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getString());
        assertEquals("I3", famc.getToString());
        assertEquals(SHOULD_BE_I1, "I1", famc.getFromString());

        famc = new FamC(null, "", XREF_FAM_1);
        assertNull(famc.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getString());
        assertEquals("F1", famc.getToString());
        assertEquals("", famc.getFromString());

        famc = new FamC(person1, "", new ObjectId("I2"));
        assertEquals(person1, famc.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getString());
        assertEquals("I2", famc.getToString());
        assertEquals(SHOULD_BE_I1, "I1", famc.getFromString());

        famc = new FamC(null, FAMC_TAG, new ObjectId("I3"));
        assertNull(famc.getParent());
        assertEquals(SHOULD_BE_FAMC, FAMC_TAG, famc.getString());
        assertEquals("I3", famc.getToString());
        assertEquals(SHOULD_BE_EMPTY, "", famc.getFromString());

        famc = new FamC(person1, TEST_LUNK, XREF_FAM_1);
        assertEquals(person1, famc.getParent());
        assertEquals(TEST_LUNK, famc.getString());
        assertEquals("F1", famc.getToString());
        assertEquals(SHOULD_BE_I1, "I1", famc.getFromString());
    }
}
