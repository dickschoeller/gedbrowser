package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
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
    private static final String[] CHECK_SURNAMES = {"?", MOYER_SURNAME,
            ROBINSON_SURNAME, SCHOELLER_SURNAME};
    /** */
    private static final String[] CHECK_IDS_NAMES = {"I4 = ?, ?",
            "I7 = Moyer, Mary Beer", "I2 = Robinson, Lisa Hope",
            "I8 = Schoeller, John", "I9 = Schoeller, John",
            "I3 = Schoeller, Karl Frederick, Jr.",
            "I6 = Schoeller, Karl Frederick, Sr.",
            "I1 = Schoeller, Richard John", "I5 = Schoeller, Whosis, Jr."};

    /** */
    private transient Root root;

    /** */
    @Before
    public void setUp() {
        root = new Root();
        final GedObjectBuilder builder = new GedObjectBuilder(root);
        builder.createPerson("I1", "Richard John/Schoeller/");
        builder.createPerson("I2", "Lisa Hope/Robinson/");
        final Person person3 =
                builder.createPerson("I3", "Karl Frederick/Schoeller/Jr.");
        final Person person4 = builder.createPerson("I4");
        builder.createPersonEvent(person4, "Birth");
        builder.createPersonEvent(person4, "Death");

        final Person person5 =
                builder.createPerson("I5", "Whosis/Schoeller/Jr.");
        builder.createPersonEvent(person5, "Birth", "1 January 1900");
        builder.createPersonEvent(person5, "Death", "1 January 1950");
        final Person person6 =
                builder.createPerson("I6", "Karl Frederick/Schoeller/Sr.");
        final Person person7 =
                builder.createPerson("I7", "Mary Beer/Moyer/");
        final Family family6 = builder.createFamily("F6");
        builder.addChildToFamily(family6, person3);
        builder.addHusbandToFamily(family6, person6);
        builder.addWifeToFamily(family6, person7);

        builder.createPerson("I8", "John/Schoeller/");
        builder.createPerson("I9", "John/Schoeller/");

        root.initIndex();
    }

    /** */
    @Test
    public void testGetNamesPerSurname1() {
        final Set<String> schoellerIndex = root.getIndex()
                .getNamesPerSurname(SCHOELLER_SURNAME);
        assertEquals("Schoeller count mismatch", SCHOELLERS_COUNT,
                schoellerIndex.size());
    }

    /** */
    @Test
    public void testGetNamesPerSurname2() {
        final Set<String> robinsonIndex = root.getIndex()
                .getNamesPerSurname(ROBINSON_SURNAME);
        assertEquals("Robinson count mismatch", ROBINSONS_COUNT,
                robinsonIndex.size());
    }

    /** */
    @Test
    public void testGetNamesPerSurname3() {
        final Set<String> moyerIndex = root.getIndex()
                .getNamesPerSurname(MOYER_SURNAME);
        assertEquals("Count not as expected", MOYER_COUNT, moyerIndex.size());
    }

    /** */
    @Test
    public void testGetNamesPerSurname4() {
        final Set<String> unknownIndex = root.getIndex()
                .getNamesPerSurname("?");
        assertEquals("Count not as expected", UNKNOWN_COUNT,
                unknownIndex.size());
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
        final Set<String> schoellerIndex = root.getIndex()
                .getNamesPerSurname(SCHOELLER_SURNAME);
        try {
            schoellerIndex.add(FOO);
            fail(SHOULD_THROW);
        } catch (UnsupportedOperationException e) {
            // Expected to get here
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
            // Expected to get here
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
        assertTrue("There is only 1 me", 1 == set.size() && set.contains("I1"));
    }

    /** */
    @Test
    public void testGetIdsPerNameSchoellerBob() {
        final Set<String> set2 = root.getIndex()
                .getIdsPerName(SCHOELLER_SURNAME, "Schoeller, Bob");
        assertEquals("Expected empty result set", 0, set2.size());
    }

    /** */
    @Test
    public void testGetIdsPerNameSchoellerJohn() {
        final Set<String> set = root.getIndex().getIdsPerName(SCHOELLER_SURNAME,
                "Schoeller, John");
        assertTrue("Results don't match",
                2 == set.size() && set.contains("I8") && set.contains("I9"));
    }

    /** */
    @Test
    public void testSurnames() {
        int arrayIndex = 0;
        for (final String surname : root.getIndex().getSurnames()) {
            for (final String indexName : root.getIndex()
                    .getNamesPerSurname(surname)) {

                for (final String idString : root.getIndex()
                        .getIdsPerName(surname, indexName)) {
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
