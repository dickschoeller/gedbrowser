package org.schoellerfamily.gedbrowser.analytics.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.analytics.BirthDateEstimator;
import org.schoellerfamily.gedbrowser.analytics.order.test.AnalyzerTest;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.util.FamilyBuilder;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.datamodel.util.PersonBuilder;
import org.schoellerfamily.gedbrowser.reader.AbstractGedLine;
import org.schoellerfamily.gedbrowser.reader.GedObjectCreator;
import org.schoellerfamily.gedbrowser.reader.testreader.TestResourceReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.ExcessiveClassLength" })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class BirthDateEstimatorTest implements AnalyzerTest {
    /** */
    @Autowired
    private GedObjectBuilder builder;
    /** */
    @Autowired
    private transient GedObjectCreator g2g;

    /**
     * {@inheritDoc}
     */
    @Override
    public PersonBuilder personBuilder() {
        return builder.getPersonBuilder();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FamilyBuilder familyBuilder() {
        return builder.getFamilyBuilder();
    }

    /** */
    @Test
    public void testSimple() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Birth", "11 JUL 1960");

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
    public void testEmpty() {
        final Person person = createJRandom();

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final LocalDate actual = estimator.estimateBirthDate();
        assertNull("Expected a null date", actual);
    }

    /** */
    @Test
    public void testFromYoungerSibling() {
        final Person person1 =
                createJRandom();
        personBuilder().createPersonEvent(person1, "Birth", "11 JUL 1960");
        final Person person2 =
                personBuilder().createPerson("I2", "Anon/Schoeller/");
        final Family family = familyBuilder().createFamily("F1");

        familyBuilder().addChildToFamily(family, person1);
        familyBuilder().addChildToFamily(family, person2);

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
    public void testFromYoungerSiblings() {
        final Person person1 = createJRandom();
        personBuilder().createPersonEvent(person1, "Birth", "11 JUL 1960");

        final Person person2 =
                personBuilder().createPerson("I2", "Anon/Schoeller/");
        final Person person3 =
                personBuilder().createPerson("I3", "Anonyma/Schoeller/");

        final Family family = familyBuilder().createFamily("F1");
        familyBuilder().addChildToFamily(family, person1);
        familyBuilder().addChildToFamily(family, person2);
        familyBuilder().addChildToFamily(family, person3);

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
    public void testFromEmptyYoungerSiblings() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Person person3 = createAnonymousJones();

        final Family family = familyBuilder().createFamily("F1");
        familyBuilder().addChildToFamily(family, person1);
        familyBuilder().addChildToFamily(family, person2);
        familyBuilder().addChildToFamily(family, person3);

        final BirthDateEstimator estimator = createBirthEstimator(person3);
        final LocalDate actual = estimator.estimateBirthDate();
        assertNull("Expected a null date", actual);
    }

    /** */
    @Test
    public void testFromOlderSibling() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();

        personBuilder().createPersonEvent(person1, "Birth", "11 JUL 1960");

        final Family family = familyBuilder().createFamily("F1");
        familyBuilder().addChildToFamily(family, person1);
        familyBuilder().addChildToFamily(family, person2);

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
    public void testFromOlderSiblings() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Person person3 = createAnonymousJones();

        personBuilder().createPersonEvent(person1, "Birth", "11 JUL 1960");

        final Family family = familyBuilder().createFamily("F1");
        familyBuilder().addChildToFamily(family, person1);
        familyBuilder().addChildToFamily(family, person2);
        familyBuilder().addChildToFamily(family, person3);

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
    public void testFromEmptyOlderSiblings() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Person person3 = createAnonymousJones();

        final Family family = familyBuilder().createFamily("F1");
        familyBuilder().addChildToFamily(family, person1);
        familyBuilder().addChildToFamily(family, person2);
        familyBuilder().addChildToFamily(family, person3);

        final BirthDateEstimator estimator = createBirthEstimator(person3);
        final LocalDate actual = estimator.estimateBirthDate();
        assertNull("Expected a null date", actual);
    }

    /** */
    @Test
    public void testFromOwnMarriage() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();

        final Family family = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family, person1);
        familyBuilder().addWifeToFamily(family, person2);

        familyBuilder().createFamilyEvent(family, "Marriage", "27 MAY 1984");

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
    public void testFromOwnMarriageWithNoDate() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();

        final Family family = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family, person1);
        familyBuilder().addWifeToFamily(family, person2);

        final BirthDateEstimator estimator = createBirthEstimator(person2);
        final LocalDate actual = estimator.estimateBirthDate();
        assertNull("Expected a null date", actual);
    }

    /** */
    @Test
    public void testFromOwnMarriages() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Person person3 = createAnonymousJones();

        final Family family1 = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family1, person1);
        familyBuilder().addWifeToFamily(family1, person2);

        final Family family2 = familyBuilder().createFamily("F2");
        familyBuilder().addHusbandToFamily(family2, person1);
        familyBuilder().addWifeToFamily(family2, person3);

        familyBuilder().createFamilyEvent(family1, "Marriage", "14 JUN 1980");
        familyBuilder().createFamilyEvent(family2, "Marriage", "27 MAY 1984");

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
    public void testFromParentsMarriage() {
        final Person person1 = createJRandom();
        personBuilder().createPersonEvent(person1, "Birth", "11 JUL 1960");

        final Person person2 = createAnonymousSmith();
        final Person person3 = createAnonymousJones();

        final Family family1 = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family1, person1);
        familyBuilder().addWifeToFamily(family1, person2);
        familyBuilder().addChildToFamily(family1, person3);

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
    public void testFromParentsMarriageWithOlderSiblings() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSmith();
        final Person person3 = createAnonymousJones();
        final Person person4 =
                personBuilder().createPerson("I4", "No Name/Johnson/");
        final Family family1 = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family1, person1);
        familyBuilder().addWifeToFamily(family1, person2);
        familyBuilder().addChildToFamily(family1, person3);
        familyBuilder().addChildToFamily(family1, person4);
        familyBuilder().createFamilyEvent(family1, "Marriage", "27 MAY 1984");

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
    public void testFromChild() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSmith();
        final Person person3 = createAnonymousJones();
        personBuilder().createPersonEvent(person3, "Birth", "11 JUL 1960");

        final Family family1 = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family1, person1);
        familyBuilder().addChildToFamily(family1, person2);
        familyBuilder().addChildToFamily(family1, person3);

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
    public void testFromChildren() {
        final Person person1 = createJRandom();
        final Person person3 = createAnonymousJones();
        personBuilder().createPersonEvent(person3, "Birth", "11 JUL 1960");

        final Family family1 = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family1, person1);
        familyBuilder().addChildToFamily(family1, person3);

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
    public void testFromHusband() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSmith();
        personBuilder().createPersonEvent(person1, "Birth", "11 JUL 1960");

        final Family family1 = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family1, person1);
        familyBuilder().addWifeToFamily(family1, person2);

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
    public void testFromWife() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSmith();
        personBuilder().createPersonEvent(person2, "Birth", "11 JUL 1960");

        final Family family1 = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family1, person1);
        familyBuilder().addWifeToFamily(family1, person2);

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
    public void testFactoryGedFile() throws IOException {
        final AbstractGedLine top = readFileTestSource();
        final Root root = g2g.create(top);
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
    public void testFromBaptism() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Baptism", "11 JUL 1960");

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
    public void testChristening() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Christening", "11 JUL 1960");

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
    public void testBarMitzah() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Bar Mitzvah", "11 JUL 1960");

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
    public void testBatMitzvah() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Bat Mitzvah", "11 JUL 1960");

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
    public void testDeath() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Death", "11 JUL 1960");

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
    public void testBurial() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Burial", "11 JUL 1960");

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
    public void testChanged() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Changed", "11 JUL 1960");

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final LocalDate actual = estimator.estimateBirthDate();
        assertNull("Changed event should return null", actual);
    }

    /** */
    @Test
    public void testOtherEvent() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Occupation", "11 JUL 1960");

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
    public void testNoDate() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Death");

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final LocalDate actual = estimator.estimateBirthDate();
        assertNull("No date event should return null", actual);
    }

    /** */
    @Test
    public void testBadDate() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Changed", "");

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final LocalDate actual = estimator.estimateBirthDate();
        assertNull("Bad date event should return null", actual);
    }

    /** */
    @Test
    public void testEstimateBirthDate() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Birth", "11 JUL 1960");

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
    public void testEstimateMarriageDate() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Marriage", "11 JUL 1960");

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
    public void testEstimateOtherDate() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Occupation", "11 JUL 1960");

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
        return TestResourceReader.readFileTestSource(this, "gl120368.ged");
    }
}
