package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
final class NameTest {
    /** */
    private transient Person person;
    /** */
    private transient Name name1;
    /** */
    private transient Name name2;
    /** */
    private transient Name name3;
    /** */
    private transient Name name4;
    /** */
    private transient Name name5;

    /** */
    @BeforeEach
    void setUp() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        person = builder.createPerson("I1");
        final Person person1 = person;
        name1 = builder.addNameToPerson(person1, "Karl/Schoeller/Jr.");
        final Person person2 = person;
        name2 = builder.addNameToPerson(person2, "Karl/Schoeller/");
        final Person person3 = person;
        name3 = builder.addNameToPerson(person3, "Wingnut");
        final Person person4 = person;
        name4 = builder.addNameToPerson(person4, "/Noodle/");
        final Person person5 = person;
        name5 = builder.addNameToPerson(person5, "/Wang/Foo");
    }

    /** */
    @Test
    void testGetName1() {
        assertEquals(name1, name1.getName(), "Name string mismatch");
    }

    /** */
    @Test
    void testGetName2() {
        assertEquals(name2, name2.getName(), "Name string mismatch");
    }

    /** */
    @Test
    void testGetName3() {
        assertEquals(name3, name3.getName(), "Name string mismatch");
    }

    /** */
    @Test
    void testGetNameP() {
        assertEquals(name1, person.getName(), "Name string mismatch");
    }

    /**
     * Parameterized test for getIndexName across multiple name cases.
     *
     * @param idx name index (1-based)
     * @param expected expected index name string
     */
    @ParameterizedTest(name = "indexName[{index}] name{0} => {1}")
    @MethodSource("indexNameCases")
    void testGetIndexNameParameterized(final int idx, final String expected) {
        final Name target = getNameByIndex(idx);
        assertEquals(expected, target.getIndexName(), "Index name string mismatch");
    }

    @SuppressWarnings("magicnumber")
    static Stream<Arguments> indexNameCases() {
        return Stream.of(
                Arguments.of(1, "Schoeller, Karl, Jr."),
                Arguments.of(2, "Schoeller, Karl"),
                Arguments.of(3, "?, Wingnut"),
                Arguments.of(4, "Noodle, ?"),
                Arguments.of(5, "Wang Foo")
        );
    }

    /**
     * Parameterized test for getSurname across multiple name cases.
     *
     * @param idx name index (1-based)
     * @param expected expected surname
     */
    @ParameterizedTest(name = "surname[{index}] name{0} => {1}")
    @MethodSource("surnameCases")
    void testGetSurnameParameterized(final int idx, final String expected) {
        final Name target = getNameByIndex(idx);
        assertEquals(expected, target.getSurname(), "Surname string mismatch");
    }

    @SuppressWarnings("magicnumber")
    static Stream<Arguments> surnameCases() {
        return Stream.of(
                Arguments.of(1, "Schoeller"),
                Arguments.of(2, "Schoeller"),
                Arguments.of(3, "?"),
                Arguments.of(4, "Noodle"),
                Arguments.of(5, "Wang")
        );
    }

    /**
     * Parameterized test for getPrefix across multiple name cases.
     *
     * @param idx name index (1-based)
     * @param expected expected prefix
     */
    @ParameterizedTest(name = "prefix[{index}] name{0} => {1}")
    @MethodSource("prefixCases")
    void testGetPrefixParameterized(final int idx, final String expected) {
        final Name target = getNameByIndex(idx);
        assertEquals(expected, target.getPrefix(), "Prefix string mismatch");
    }

    @SuppressWarnings("magicnumber")
    static Stream<Arguments> prefixCases() {
        return Stream.of(
                Arguments.of(1, "Karl"),
                Arguments.of(2, "Karl"),
                Arguments.of(3, "Wingnut"),
                Arguments.of(4, ""),
                Arguments.of(5, "")
        );
    }

    /**
     * Parameterized test for getSuffix across multiple name cases.
     *
     * @param idx name index (1-based)
     * @param expected expected suffix
     */
    @ParameterizedTest(name = "suffix[{index}] name{0} => {1}")
    @MethodSource("suffixCases")
    void testGetSuffixParameterized(final int idx, final String expected) {
        final Name target = getNameByIndex(idx);
        assertEquals(expected, target.getSuffix(), "Suffix string mismatch");
    }

    @SuppressWarnings("magicnumber")
    static Stream<Arguments> suffixCases() {
        return Stream.of(
                Arguments.of(1, "Jr."),
                Arguments.of(2, ""),
                Arguments.of(3, ""),
                Arguments.of(4, ""),
                Arguments.of(5, "Foo")
        );
    }

    /**
     * Helper to map an index to one of the name fields set up in @BeforeEach.
     *
     * @param idx 1-based index of the name
     * @return corresponding Name instance
     */
    @SuppressWarnings("magicnumber")
    private Name getNameByIndex(final int idx) {
        switch (idx) {
        case 1:
            return name1;
        case 2:
            return name2;
        case 3:
            return name3;
        case 4:
            return name4;
        case 5:
            return name5;
        default:
            throw new IllegalArgumentException("Invalid name index: " + idx);
        }
    }

    /** */
    @Test
    void testNameGedObject() {
        final Root localRoot = new Root("Root");
        final Person localPerson = new Person(localRoot, new ObjectId("I1"));
        localRoot.insert(localPerson);
        final Name name = new Name(localPerson);
        localPerson.insert(name);
        assertMatch(localPerson, name, "", "", "?", "");
    }

    /** */
    @Test
    void testNameGedObjectString() {
        final Root localRoot = new Root("Root");
        final Person localPerson = new Person(localRoot, new ObjectId("I1"));
        localRoot.insert(localPerson);
        final Name name = new Name(localPerson, "Karl/Schoeller/Jr.");
        localPerson.insert(name);
        assertMatch(localPerson, name, "Karl/Schoeller/Jr.", "Karl",
                "Schoeller", "Jr.");
    }

    /**
     * @param localPerson the person to test
     * @param name the name object
     * @param expectedString the expected getString
     * @param expectedPrefix the expected getPrefix
     * @param expectedSurname the expected getSurname
     * @param expectedSuffix the expected getSuffix
     */
    private void assertMatch(final Person localPerson, final Name name,
            final String expectedString, final String expectedPrefix,
            final String expectedSurname, final String expectedSuffix) {
        assertEquals(localPerson, name.getParent(), "Parent mismatch");
        assertEquals(expectedString, name.getString(), "Name string mismatch");
        assertEquals(expectedPrefix, name.getPrefix(), "Prefix mismatch");
        assertEquals(expectedSurname, name.getSurname(), "Surname mismatch");
        assertEquals(expectedSuffix, name.getSuffix(), "Suffix mismatch");
    }
}
