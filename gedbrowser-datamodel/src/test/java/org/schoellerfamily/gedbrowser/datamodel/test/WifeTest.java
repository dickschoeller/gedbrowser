package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Wife;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * Contains tests for wife.
 *
 * @author Richard Schoeller
 */
final class WifeTest {
    /** */
    private static final String WIFE_TAG = "WIFE";
    /** */
    private transient Person person2;
    /** */
    private transient Person person3;
    /** */
    private transient Wife wife1;
    /** */
    private transient Wife wife2a;
    /** */
    private transient Wife wife2b;

    @BeforeEach
    void setUp() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson("I1");
        person2 = builder.createPerson("I2");
        person3 = builder.createPerson("I3");
        final Family family1 = builder.createFamily("F1");
        builder.addHusbandToFamily(family1, person1);
        final Person person = person2;
        wife1 = builder.addWifeToFamily(family1, person);

        final Family family2 = builder.createFamily("F2");
        final Person person4 = person2;
        wife2a = builder.addWifeToFamily(family2, person4);
        final Person person5 = person3;
        wife2b = builder.addWifeToFamily(family2, person5);
    }

    @Test
    void testEmptyGetMother() {
        final Wife wife = new Wife();
        assertFalse(wife.getMother().isSet(), "Should not be set");
    }

    @ParameterizedTest(name = "mother[{index}] wife{0} => person{1}")
    @MethodSource("motherCases")
    void testGetMotherParameterized(final int wifeIndex, final int expectedMotherIndex) {
        final Wife target = getWifeByIndex(wifeIndex);
        final Person expected = getPersonByIndex(expectedMotherIndex);
        assertEquals(expected, target.getMother(), "Person's mother doesn't match");
    }

    @SuppressWarnings("magicnumber")
    static Stream<Arguments> motherCases() {
        return Stream.of(
                Arguments.of(1, 2),
                Arguments.of(2, 2),
                Arguments.of(3, 3)
        );
    }

    @ParameterizedTest(name = "spouseIsSet[{index}] wife{0} => isSet={1}")
    @MethodSource("spouseIsSetCases")
    void testGetSpouseIsSetParameterized(final int wifeIndex, final boolean expectedIsSet) {
        final Wife target = getWifeByIndex(wifeIndex);
        assertEquals(expectedIsSet, target.getSpouse().isSet(),
            "Person's spouse set-state mismatch");
    }

    @SuppressWarnings("magicnumber")
    static Stream<Arguments> spouseIsSetCases() {
        return Stream.of(
                Arguments.of(1, true),
                Arguments.of(2, true),
                Arguments.of(3, true)
        );
    }

    @ParameterizedTest(name = "spouse[{index}] wife{0} => person{1}")
    @MethodSource("spouseCases")
    void testGetSpouseParameterized(final int wifeIndex, final int expectedSpouseIndex) {
        final Wife target = getWifeByIndex(wifeIndex);
        final Person expected = getPersonByIndex(expectedSpouseIndex);
        assertEquals(expected, target.getSpouse(), "Person's spouse doesn't match");
    }

    @SuppressWarnings("magicnumber")
    static Stream<Arguments> spouseCases() {
        return Stream.of(
                Arguments.of(1, 2),
                Arguments.of(2, 2),
                Arguments.of(3, 3)
        );
    }

    @Test
    void testWifeGedObjectMotherNotSet() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily("F1");
        final Wife wife = new Wife(family, "Wife", null);
        assertFalse(wife.getMother().isSet(), "Mother should not be set");
    }

    @Test
    void testWifeGedObjectStringMotherNotSet() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily("F1");
        final Wife wife = new Wife(family, WIFE_TAG, null);
        assertFalse(wife.getMother().isSet(), "Mother should not be set");
    }

    @Test
    void testWifeGedObjectStringStringMother() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily("F1");
        final Wife wife = new Wife(family, WIFE_TAG, new ObjectId("@I3@"));
        assertFalse(wife.getMother().isSet(), "Mother should not be set");
    }

    @SuppressWarnings("magicnumber")
    private Wife getWifeByIndex(final int idx) {
        switch (idx) {
        case 1:
            return wife1;
        case 2:
            return wife2a;
        case 3:
            return wife2b;
        default:
            throw new IllegalArgumentException("Invalid wife index: " + idx);
        }
    }

    @SuppressWarnings("magicnumber")
    private Person getPersonByIndex(final int idx) {
        switch (idx) {
        case 0:
            return null;
        case 2:
            return person2;
        case 3:
            return person3;
        default:
            throw new IllegalArgumentException("Invalid person index: " + idx);
        }
    }
}
