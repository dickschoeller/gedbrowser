package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Link;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * Contains tests for link.
 *
 * @author Richard Schoeller
 */
final class LinkTest {
    /** */
    private static final String LINK_TEST = "Link";
    /** */
    private transient Person person1;
    /** */
    private final GedObjectBuilder builder = new GedObjectBuilder();

    @BeforeEach
    void setUp() {
        person1 = builder.createPerson("I1", "J. Random/Schoeller/");
        final Person person2 =
                builder.createPerson("I2", "Anonymous/Schoeller/");
        final Person person3 =
                builder.createPerson("I3", "Anonymous/Jones/");
        final Family family = builder.createFamily("F1");
        final Person person = person1;
        builder.addChildToFamily(family, person);
        builder.addHusbandToFamily(family, person2);
        builder.addWifeToFamily(family, person3);
    }

    @Test
    void testInitLink() {
        final Link link = new Link(null);
        link.initLink(new ObjectId("F2"));
        assertMatch(link, "", "F2");
    }

    @Test
    void testSetParentReinitLink() {
        final Link link = new Link(null);
        link.initLink(new ObjectId("F2"));
        link.setParent(person1);
        link.initLink(new ObjectId("F1"));
        assertMatch(link, "I1", "F1");
    }

    @Test
    void testNullifyParentReinitLink() {
        final Link link = new Link(null);
        link.initLink(new ObjectId("F2"));
        link.setParent(person1);
        link.initLink(new ObjectId("F1"));
        link.setParent(null);
        link.initLink(new ObjectId(""));
        assertMatch(link, "", "");
    }

    @Test
    void testInitLinkWithObjectId() {
        final Link link = new Link(null);
        link.initLink(new ObjectId("@F2@"));
        assertMatch(link, "", "F2");
    }

    @Test
    void testInitLinkWithObjectIdSetParent() {
        final Link link = new Link(null);
        link.initLink(new ObjectId("@F2@"));
        link.setParent(person1);
        link.initLink(new ObjectId("@F1@"));
        assertMatch(link, "I1", "F1");
    }

    @ParameterizedTest(name = "setFromString[{index}] input=''{0}'' => expectedFrom=''{1}''")
    @MethodSource("fromStringCases")
    void testSetGetFromStringVariants(final String input, final String expectedFrom) {
        final Link link = new Link(person1, LINK_TEST, new ObjectId("F1"));
        link.setFromString(input);
        assertMatch(link, expectedFrom, "F1");
    }

    static Stream<Arguments> fromStringCases() {
        return Stream.of(
                Arguments.of("", ""),
                Arguments.of("I2", "I2"),
                Arguments.of(null, "")
        );
    }

    @ParameterizedTest(name = "setToString[{index}] input=''{0}'' => expectedTo=''{1}''")
    @MethodSource("toStringCases")
    void testSetGetToStringVariants(final String input, final String expectedTo) {
        final Link link = new Link(person1, LINK_TEST, new ObjectId("F1"));
        link.setToString(input);
        assertMatch(link, "I1", expectedTo);
    }

    static Stream<Arguments> toStringCases() {
        return Stream.of(
                Arguments.of("", ""),
                Arguments.of("F2", "F2"),
                Arguments.of((String) null, "")
        );
    }

    private void assertMatch(final Link link, final String expectedFromString,
            final String expectedToString) {
        assertEquals(expectedFromString, link.getFromString(), "From string mismatch");
        assertEquals(expectedToString, link.getToString(), "To string mismatch");
    }
}
