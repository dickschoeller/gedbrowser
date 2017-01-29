package org.schoellerfamily.gedbrowser.analytics.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.analytics.BirthDateEstimator;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.reader.AbstractGedLine;
import org.schoellerfamily.gedbrowser.reader.ReaderHelper;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.ExcessiveClassLength" })
public class BirthDateEstimatorTest {
    /** */
    @Test
    public final void testSimple() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person =
                builder.createPerson("I0", "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Birth", "11 JUL 1960");

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final int birthYear = 1960;
        final int birthMonth = 7;
        final int birthDay = 11;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateBirthDate();
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testEmpty() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person =
                builder.createPerson("I0", "J. Random/Schoeller/");

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final LocalDate actual = estimator.estimateBirthDate();
        assertNull("Expected a null date", actual);
    }

    /** */
    @Test
    public final void testFromYoungerSibling() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 =
                builder.createPerson("I1", "J. Random/Schoeller/");
        builder.createPersonEvent(person1, "Birth", "11 JUL 1960");
        final Person person2 =
                builder.createPerson("I2", "Anon/Schoeller/");
        final Family family = builder.createFamily1();

        builder.addChildToFamily(family, person1);
        builder.addChildToFamily(family, person2);

        final BirthDateEstimator estimator = createBirthEstimator(person2);
        final int birthYear = 1962;
        final int birthMonth = 7;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateBirthDate();
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testFromYoungerSiblings() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson("I1",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person1, "Birth", "11 JUL 1960");

        final Person person2 = builder.createPerson("I2", "Anon/Schoeller/");
        final Person person3 = builder.createPerson("I3", "Anonyma/Schoeller/");

        final Family family = builder.createFamily1();
        builder.addChildToFamily(family, person1);
        builder.addChildToFamily(family, person2);
        builder.addChildToFamily(family, person3);

        final BirthDateEstimator estimator = createBirthEstimator(person3);
        final int birthYear = 1964;
        final int birthMonth = 7;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateBirthDate();
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testFromEmptyYoungerSiblings() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Person person2 = builder.createPerson2();
        final Person person3 = builder.createPerson3();

        final Family family = builder.createFamily1();
        builder.addChildToFamily(family, person1);
        builder.addChildToFamily(family, person2);
        builder.addChildToFamily(family, person3);

        final BirthDateEstimator estimator = createBirthEstimator(person3);
        final LocalDate actual = estimator.estimateBirthDate();
        assertNull("Expected a null date", actual);
    }

    /** */
    @Test
    public final void testFromOlderSibling() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Person person2 = builder.createPerson2();

        builder.createPersonEvent(person1, "Birth", "11 JUL 1960");

        final Family family = builder.createFamily1();
        builder.addChildToFamily(family, person1);
        builder.addChildToFamily(family, person2);

        final BirthDateEstimator estimator = createBirthEstimator(person2);
        final int birthYear = 1962;
        final int birthMonth = 7;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateBirthDate();
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testFromOlderSiblings() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Person person2 = builder.createPerson2();
        final Person person3 = builder.createPerson3();

        builder.createPersonEvent(person1, "Birth", "11 JUL 1960");

        final Family family = builder.createFamily1();
        builder.addChildToFamily(family, person1);
        builder.addChildToFamily(family, person2);
        builder.addChildToFamily(family, person3);

        final BirthDateEstimator estimator = createBirthEstimator(person3);
        final int birthYear = 1964;
        final int birthMonth = 7;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateBirthDate();
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testFromEmptyOlderSiblings() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Person person2 = builder.createPerson2();
        final Person person3 = builder.createPerson3();

        final Family family = builder.createFamily1();
        builder.addChildToFamily(family, person1);
        builder.addChildToFamily(family, person2);
        builder.addChildToFamily(family, person3);

        final BirthDateEstimator estimator = createBirthEstimator(person3);
        final LocalDate actual = estimator.estimateBirthDate();
        assertNull("Expected a null date", actual);
    }

    /** */
    @Test
    public final void testFromOwnMarriage() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Person person2 = builder.createPerson2();

        final Family family = builder.createFamily1();
        builder.addHusbandToFamily(family, person1);
        builder.addWifeToFamily(family, person2);

        builder.createFamilyEvent(family, "Marriage", "27 MAY 1984");

        final BirthDateEstimator estimator = createBirthEstimator(person2);
        final int birthYear = 1959;
        final int birthMonth = 1;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateBirthDate();
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testFromOwnMarriageWithNoDate() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Person person2 = builder.createPerson2();

        final Family family = builder.createFamily1();
        builder.addHusbandToFamily(family, person1);
        builder.addWifeToFamily(family, person2);

        final BirthDateEstimator estimator = createBirthEstimator(person2);
        final LocalDate actual = estimator.estimateBirthDate();
        assertNull("Expected a null date", actual);
    }

    /** */
    @Test
    public final void testFromOwnMarriages() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Person person2 = builder.createPerson2();
        final Person person3 = builder.createPerson3();

        final Family family1 = builder.createFamily1();
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);

        final Family family2 = builder.createFamily2();
        builder.addHusbandToFamily(family2, person1);
        builder.addWifeToFamily(family2, person3);

        builder.createFamilyEvent(family1, "Marriage", "14 JUN 1980");
        builder.createFamilyEvent(family2, "Marriage", "27 MAY 1984");

        final BirthDateEstimator estimator = createBirthEstimator(person1);
        final int birthYear = 1955;
        final int birthMonth = 1;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateBirthDate();
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testFromParentsMarriage() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson("I1",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person1, "Birth", "11 JUL 1960");

        final Person person2 = builder.createPerson("I2", "Anonymous/Smith/");
        final Person person3 = builder.createPerson("I3", "Anonymous/Jones/");

        final Family family1 = builder.createFamily1();
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        builder.addChildToFamily(family1, person3);

        final Attribute marriage1 = new Attribute(family1, "Marriage");
        family1.insert(marriage1);
        final Date marriageDate1 = new Date(marriage1, "27 MAY 1984");
        marriage1.insert(marriageDate1);

        final BirthDateEstimator estimator = createBirthEstimator(person3);
        final int birthYear = 1986;
        final int birthMonth = 5;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateBirthDate();
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testFromParentsMarriageWithOlderSiblings() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Person person2 = builder.createPerson("I2", "Anonymous/Smith/");
        final Person person3 = builder.createPerson("I3", "Anonymous/Jones/");
        final Person person4 = builder.createPerson("I4", "No Name/Johnson/");
        final Family family1 = builder.createFamily1();
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        builder.addChildToFamily(family1, person3);
        builder.addChildToFamily(family1, person4);
        builder.createFamilyEvent(family1, "Marriage", "27 MAY 1984");

        final BirthDateEstimator estimator = createBirthEstimator(person4);
        final int birthYear = 1988;
        final int birthMonth = 5;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateBirthDate();
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testFromChild() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Person person2 = builder.createPerson("I2", "Anonymous/Smith/");
        final Person person3 = builder.createPerson("I3", "Anonymous/Jones/");
        builder.createPersonEvent(person3, "Birth", "11 JUL 1960");

        final Family family1 = builder.createFamily1();
        builder.addHusbandToFamily(family1, person1);
        builder.addChildToFamily(family1, person2);
        builder.addChildToFamily(family1, person3);

        final BirthDateEstimator estimator = createBirthEstimator(person1);
        final int birthYear = 1931;
        final int birthMonth = 7;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateBirthDate();
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testFromChildren() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Person person3 = builder.createPerson("I3", "Anonymous/Jones/");
        builder.createPersonEvent(person3, "Birth", "11 JUL 1960");

        final Family family1 = builder.createFamily1();
        builder.addHusbandToFamily(family1, person1);
        builder.addChildToFamily(family1, person3);

        final BirthDateEstimator estimator = createBirthEstimator(person1);
        final int birthYear = 1933;
        final int birthMonth = 7;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateBirthDate();
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testFromHusband() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Person person2 = builder.createPerson("I2", "Anonymous/Smith/");
        builder.createPersonEvent(person1, "Birth", "11 JUL 1960");

        final Family family1 = builder.createFamily1();
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);

        final BirthDateEstimator estimator = createBirthEstimator(person2);
        final int birthYear = 1965;
        final int birthMonth = 7;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateBirthDate();
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testFromWife() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson("I1",
                "J. Random/Schoeller/");
        final Person person2 = builder.createPerson("I2", "Anonymous/Smith/");
        builder.createPersonEvent(person2, "Birth", "11 JUL 1960");

        final Family family1 = builder.createFamily1();
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);

        final BirthDateEstimator estimator = createBirthEstimator(person1);
        final int birthYear = 1955;
        final int birthMonth = 7;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateBirthDate();
        assertMatch(expected, actual);
    }

    /**
     * Test for persons for who the algorithm fails to come up with
     * a birth date.
     *
     * TODO this test exercises stuff not test elsewhere. Fix that.
     *
     * @throws IOException if the file can't be read
     */
    @Test
    public final void testFactoryGedFile() throws IOException {
        final AbstractGedLine top = readFileTestSource();
        final GedObject root = top.createGedObject((AbstractGedLine) null);
        final List<Person> unhandled = new ArrayList<>();
        for (final String letter : root.findSurnameInitialLetters()) {
            for (final String surname : root.findBySurnamesBeginWith(letter)) {
                for (final Person person : root.findBySurname(surname)) {
                    final BirthDateEstimator estimator =
                            createBirthEstimator(person);
                    final LocalDate localDate = estimator.estimateBirthDate();
                    if (localDate == null) {
                        unhandled.add(person);
                    }
                }
            }
        }
        final int max = Math.min(unhandled.size(), 50);
        if (max != 0) {
            System.out.println("Found " + unhandled.size()
                    + " Persons with unestimated birth date");
            System.out.println("Top " + max + ":");
        }
        int printed = 0;
        for (final Person person : unhandled) {
            System.out.println("    " + person.getString() + " "
                    + person.getIndexName());
            if (printed++ > max) {
                break;
            }
        }
        assertEquals("Shouldn't have found any unhandled dates", 0, max);
    }

    /** */
    @Test
    public final void testFromBaptism() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Baptism", "11 JUL 1960");

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final int birthYear = 1960;
        final int birthMonth = 7;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateBirthDate();
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testChristening() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Christening", "11 JUL 1960");

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final int birthYear = 1960;
        final int birthMonth = 7;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateBirthDate();
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testBarMitzah() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Bar Mitzvah", "11 JUL 1960");

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final int birthYear = 1947;
        final int birthMonth = 7;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateBirthDate();
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testBatMitzvah() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Bat Mitzvah", "11 JUL 1960");

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final int birthYear = 1947;
        final int birthMonth = 7;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateBirthDate();
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testDeath() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Death", "11 JUL 1960");

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final int birthYear = 1885;
        final int birthMonth = 1;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateBirthDate();
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testBurial() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Burial", "11 JUL 1960");

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final int birthYear = 1885;
        final int birthMonth = 1;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateBirthDate();
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testChanged() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Changed", "11 JUL 1960");

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final LocalDate actual = estimator.estimateBirthDate();
        assertNull("Changed event should return null", actual);
    }

    /** */
    @Test
    public final void testOtherEvent() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Occupation", "11 JUL 1960");

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final int birthYear = 1935;
        final int birthMonth = 1;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateBirthDate();
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testNoDate() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Death");

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final LocalDate actual = estimator.estimateBirthDate();
        assertNull("No date event should return null", actual);
    }

    /** */
    @Test
    public final void testBadDate() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Changed", "");

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final LocalDate actual = estimator.estimateBirthDate();
        assertNull("Bad date event should return null", actual);
    }

    /** */
    @Test
    public final void testEstimateBirthDate() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Birth", "11 JUL 1960");

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final int birthYear = 1960;
        final int birthMonth = 7;
        final int birthDay = 11;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimate();
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testEstimateMarriageDate() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Marriage", "11 JUL 1960");

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final int birthYear = 1935;
        final int birthMonth = 1;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimate();
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testEstimateOtherDate() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Occupation", "11 JUL 1960");

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final int birthYear = 1935;
        final int birthMonth = 1;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimate();
        assertMatch(expected, actual);
    }

    /**
     * Wrap calls to the constructor, so that we avoid the creation in
     * loops warning.
     *
     * @param person the person to estimate
     * @return the new estimator
     */
    private BirthDateEstimator createBirthEstimator(final Person person) {
        return new BirthDateEstimator(
                person);
    }

    /**
     * @param expected expected date
     * @param actual actual date
     */
    private void assertMatch(final LocalDate expected, final LocalDate actual) {
        assertTrue(mismatchString(expected, actual),
                expected.isEqual(actual));
    }

    /**
     * @param expected expected date
     * @param actual actual date
     * @return string describing the mismatch
     */
    private String mismatchString(final LocalDate expected,
            final LocalDate actual) {
        return "Don't match! expected: " + expected + ", actual: " + actual;
    }

    /**
     * Read data for tests available to prepare data for tests.
     *
     * @return a populated GedLine parse tree.
     * @throws IOException because reader might throw.
     */
    private AbstractGedLine readFileTestSource() throws IOException {
        return ReaderHelper.readFileTestSource(this, "gl120368.ged");
    }
}
