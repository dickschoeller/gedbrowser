package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collections;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Wife;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.TooManyFields")
public final class IndexTest {
    /**
     * Message if we didn't throw when we should.
     */
    private static final String SHOULD_THROW = "We shouldn't have gotten here";

    /**
     * Dummy input line.
     */
    private static final String FOO = "FOO";

    /** */
    private static final int UNKNOWN_COUNT = 1;
    /** */
    private static final int MOYER_COUNT = 1;
    /** */
    private static final int ROBINSONS_COUNT = 1;
    /** */
    private static final int SCHOELLERS_COUNT = 5;
    /** */
    private static final int SURNAME_COUNT = 4;
    /** */
    private static final String MOYER_SURNAME = "Moyer";
    /** */
    private static final String ROBINSON_SURNAME = "Robinson";
    /** */
    private static final String SCHOELLER_SURNAME = "Schoeller";
    /** */
    private static final String[] CHECK_SURNAMES = {
            "?", MOYER_SURNAME,
            ROBINSON_SURNAME, SCHOELLER_SURNAME };
    /** */
    private static final String[] CHECK_IDS_NAMES = {
            "I4 = ?, ?",
            "I7 = Moyer, Mary Beer", "I2 = Robinson, Lisa Hope",
            "I8 = Schoeller, John", "I9 = Schoeller, John",
            "I3 = Schoeller, Karl Frederick, Jr.",
            "I6 = Schoeller, Karl Frederick, Sr.",
            "I1 = Schoeller, Richard John", "I5 = Schoeller, Whosis, Jr." };

    /** */
    private final transient Root root = new Root(null);
    /** */
    private final transient Person person1 = new Person(null);
    /** */
    private final transient Name name1 = new Name(person1,
            "Richard John/Schoeller/");
    /** */
    private final transient Person person2 = new Person(root);
    /** */
    private final transient Name name2 = new Name(person2,
            "Lisa Hope/Robinson/");
    /** */
    private final transient Person person3 = new Person(root);
    /** */
    private final transient Name name3 = new Name(person3,
            "Karl Frederick/Schoeller/Jr.");
    /** */
    private final transient Person person4 = new Person(root);
    /** */
    private final transient Attribute birth4 = new Attribute(person4, "Birth");
    /** */
    private final transient Attribute death4 = new Attribute(person4, "Death");
    /** */
    private final transient Person person5 = new Person(root);
    /** */
    private final transient Name name5 = new Name(person5,
            "Whosis/Schoeller/Jr.");
    /** */
    private final transient Attribute birth5 = new Attribute(person5, "Birth");
    /** */
    private final transient Date birthDate5 =
            new Date(birth5, "1 January 1900");
    /** */
    private final transient Attribute death5 = new Attribute(person5, "Death");
    /** */
    private final transient Date deathDate5 =
            new Date(death5, "1 January 1950");
    /** */
    private final transient Family family6 = new Family(root,
            new ObjectId("F6"));
    /** */
    private final transient FamC famc3 = new FamC(person3, "FamC",
            new ObjectId("F6"));
    /** */
    private final transient Person person6 = new Person(root);
    /** */
    private final transient FamS fams6 = new FamS(person6, "FamS",
            new ObjectId("F6"));
    /** */
    private final transient Name name6 = new Name(person3,
            "Karl Frederick/Schoeller/Sr.");
    /** */
    private final transient Person person7 = new Person(root);
    /** */
    private final transient FamS fams7 = new FamS(person7, "FamS",
            new ObjectId("F6"));
    /** */
    private final transient Name name7 = new Name(person3, "Mary Beer/Moyer/");
    /** */
    private final transient Husband husband6 = new Husband(family6, "Husband",
            new ObjectId("I6"));
    /** */
    private final transient Wife wife7 = new Wife(family6, "Wife",
            new ObjectId("I7"));
    /** */
    private final transient Child child3 = new Child(family6, "Child",
            new ObjectId("I3"));
    /** */
    private final transient Person person8 = new Person(root,
            new ObjectId("I8"));
    /** */
    private final transient Name name8 = new Name(person8, "John/Schoeller/");
    /** */
    private final transient Person person9 = new Person(root,
            new ObjectId("I9"));
    /** */
    private final transient Name name9 = new Name(person9, "John/Schoeller/");

    /** */
    @Before
    public void setUp() {
        root.insert("I1", person1);
        person1.insert(name1);
        root.insert("I2", person2);
        person2.insert(name2);
        root.insert("I3", person3);
        person3.insert(name3);
        root.insert("I4", person4);
        person4.insert(birth4);
        person4.insert(death4);
        root.insert("I5", person5);
        person5.insert(name5);
        person5.insert(birth5);
        person5.insert(death5);
        birth5.insert(birthDate5);
        death5.insert(deathDate5);

        // Make people and family findable.
        root.insert("I6", person6);
        root.insert("I7", person7);
        root.insert("F6", family6);
        // People point to family.
        person3.insert(famc3);
        person6.insert(fams6);
        person6.insert(name6);
        person7.insert(fams7);
        person7.insert(name7);
        // Family points to people.
        family6.insert(husband6);
        family6.insert(wife7);
        family6.insert(child3);

        root.insert(null, person8);
        root.insert(null, person9);
        person8.insert(name8);
        person9.insert(name9);
        root.initIndex();
    }

    /** */
    @Test
    public void testGetNamesPerSurname1() {
        final Set<String> schoellerIndex = root.getIndex().getNamesPerSurname(
                SCHOELLER_SURNAME);
        assertEquals("Schoeller count mismatch", SCHOELLERS_COUNT,
                schoellerIndex.size());
    }

    /** */
    @Test
    public void testGetNamesPerSurname2() {
        final Set<String> robinsonIndex = root.getIndex().getNamesPerSurname(
                ROBINSON_SURNAME);
        assertEquals("Robinson count mismatch", ROBINSONS_COUNT,
                robinsonIndex.size());
    }

    /** */
    @Test
    public void testGetNamesPerSurname3() {
        final Set<String> moyerIndex = root.getIndex().getNamesPerSurname(
                MOYER_SURNAME);
        assertEquals("Count not as expected", MOYER_COUNT, moyerIndex.size());
    }

    /** */
    @Test
    public void testGetNamesPerSurname4() {
        final Set<String> unknownIndex =
                root.getIndex().getNamesPerSurname("?");
        assertEquals("Count not as expected",
                UNKNOWN_COUNT, unknownIndex.size());
    }

    /** */
    @Test
    public void testGetNamesPerSurname5() {
        final Set<String> emptyIndex = root.getIndex().getNamesPerSurname(null);
        assertEquals("Index should be empty", 0, emptyIndex.size());
    }

    /** */
    @Test
    public void testGetNamesPerSurname6() {
        final Set<String> schoellerIndex = root.getIndex().getNamesPerSurname(
                SCHOELLER_SURNAME);
        try {
            schoellerIndex.add(FOO);
            fail(SHOULD_THROW);
        } catch (UnsupportedOperationException e) {
            assertTrue("Expected to get here", true); // NOPMD
        }
    }

    /** */
    @Test
    public void testGetSurnamesSize() {
        final Set<String> set = root.getIndex().getSurnames();
        assertEquals("Surname count mismatch", SURNAME_COUNT, set.size());
    }

    /** */
    @Test
    public void testGetSurnames() {
        final Set<String> set = root.getIndex().getSurnames();
        int arrayIndex = 0;
        for (final String surname : set) {
            assertEquals("Surname anomaly", CHECK_SURNAMES[arrayIndex++],
                    surname);
        }
    }

    /** */
    @Test
    public void testGetSurnamesImmutable() {
        final Set<String> set = root.getIndex().getSurnames();
        try {
            set.add(FOO);
            fail(SHOULD_THROW);
        } catch (UnsupportedOperationException e) {
            // Expected to get here.
        }
    }

    /** */
    @Test
    public void testGetIdsPerNameNullNull() {
        Set<String> emptySet;
        emptySet = root.getIndex().getIdsPerName(null, null);
        assertEquals("Expected empty result set", 0, emptySet.size());
    }

    /** */
    @Test
    public void testGetIdsPerNameSchoellerNull() {
        Set<String> emptySet;
        emptySet = root.getIndex().getIdsPerName(SCHOELLER_SURNAME, null);
        assertEquals("Expected empty result set", 0, emptySet.size());
        try {
            emptySet.add(FOO);
            fail(SHOULD_THROW);
        } catch (UnsupportedOperationException e) {
            emptySet = Collections.emptySet();
        }
    }

    /** */
    @Test
    public void testGetIdsPerNameNullBob() {
        Set<String> emptySet;
        emptySet = root.getIndex().getIdsPerName(null, "Bob");
        assertEquals("Expected empty result set", 0, emptySet.size());

    }

    /** */
    @Test
    public void testGetIdsPerNameMe() {
        final Set<String> set = root.getIndex().getIdsPerName(SCHOELLER_SURNAME,
                "Schoeller, Richard John");
        assertTrue("There is only 1 me",
                1 == set.size() && set.contains("I1"));
    }

    /** */
    @Test
    public void testGetIdsPerNameSchoellerBob() {
        final Set<String> set2 =
                root.getIndex().getIdsPerName(SCHOELLER_SURNAME,
                "Schoeller, Bob");
        assertEquals("Expected empty result set", 0, set2.size());
    }

    /** */
    @Test
    public void testGetIdsPerNameSchoellerJohn() {
        final Set<String> set =
                root.getIndex().getIdsPerName(SCHOELLER_SURNAME,
                        "Schoeller, John");
        assertTrue("Results don't match",
                2 == set.size() && set.contains("I8") && set.contains("I9"));
    }

    /** */
    @Test
    public void testSurnames() {
        int arrayIndex = 0;
        for (final String surname : root.getIndex().getSurnames()) {
            for (final String indexName
                    : root.getIndex().getNamesPerSurname(surname)) {

                for (final String idString : root.getIndex().getIdsPerName(
                        surname, indexName)) {
                    final String test = idString + " = " + indexName;
                    assertEquals("Name Anomaly", CHECK_IDS_NAMES[arrayIndex++],
                            test);
                }
            }
        }
    }

    /** */
    @Test
    public void testJunkSurname() {
        assertTrue("Expected empty result set", root.getIndex()
                .getIdsPerName("Mumble", "Mumble, Bob").isEmpty());
    }

    /** */
    @Test
    public void testSize() {
        assertEquals("Surname count mismatch", SURNAME_COUNT,
                root.getIndex().surnameCount());
    }
}
