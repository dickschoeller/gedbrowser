package org.schoellerfamily.gedbrowser.analytics.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import org.schoellerfamily.gedbrowser.reader.GedLineToGedObjectTransformer;
import org.schoellerfamily.gedbrowser.reader.testreader.TestResourceReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.ExcessiveClassLength" })
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
final class BirthDateEstimatorTest implements AnalyzerTest {
    /** */
    @Autowired
    private GedObjectBuilder builder;
    /** */
    @Autowired
    private transient GedLineToGedObjectTransformer g2g;

    @Override
    public PersonBuilder personBuilder() {
        return builder;
    }

    @Override
    public FamilyBuilder familyBuilder() {
        return builder;
    }

    /** */
    @Test
    void testSimple() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Birth", "11 JUL 1960");

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final int birthYear = 1960;
        final int birthMonth = 7;
        final int birthDay = 11;
        Calendar cal = new Calendar.Builder().setDate(birthYear, birthMonth - 1, birthDay).build();
        final LocalDate expected = new LocalDate(cal);
        final LocalDate actual = estimator.estimateBirthDate();
        assertEquals(expected, actual, "Dates should match");
    }

    /** */
    @Test
    void testEmpty() {
        final Person person = createJRandom();

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final LocalDate actual = estimator.estimateBirthDate();
        assertNull(actual, "Expected a null date");
    }

    /** */
    @Test
    void testFromYoungerSibling() {
        final Person person1 =
                createJRandom();
        builder.createPersonEvent(person1, "Birth", "11 JUL 1960");
        final Person person2 =
                builder.createPerson("I2", "Anon/Schoeller/");
        final Family family = builder.createFamily("F1");

        builder.addChildToFamily(family, person1);
        builder.addChildToFamily(family, person2);

        final BirthDateEstimator estimator = createBirthEstimator(person2);
        final int birthYear = 1962;
        final int birthMonth = 7;
        final int birthDay = 1;
        Calendar cal = new Calendar.Builder().setDate(birthYear, birthMonth - 1, birthDay).build();
        final LocalDate expected = new LocalDate(cal);
        final LocalDate actual = estimator.estimateBirthDate();
        assertEquals(expected, actual, "Dates should match");
    }

    /** */
    @Test
    void testFromYoungerSiblings() {
        final Person person1 = createJRandom();
        builder.createPersonEvent(person1, "Birth", "11 JUL 1960");

        final Person person2 =
                builder.createPerson("I2", "Anon/Schoeller/");
        final Person person3 =
                builder.createPerson("I3", "Anonyma/Schoeller/");

        final Family family = builder.createFamily("F1");
        builder.addChildToFamily(family, person1);
        builder.addChildToFamily(family, person2);
        builder.addChildToFamily(family, person3);

        final BirthDateEstimator estimator = createBirthEstimator(person3);
        final int birthYear = 1964;
        final int birthMonth = 7;
        final int birthDay = 1;
        Calendar cal = new Calendar.Builder().setDate(birthYear, birthMonth - 1, birthDay).build();
        final LocalDate expected = new LocalDate(cal);
        final LocalDate actual = estimator.estimateBirthDate();
        assertEquals(expected, actual, "Dates should match");
    }

    /** */
    @Test
    void testFromEmptyYoungerSiblings() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Person person3 = createAnonymousJones();

        final Family family = builder.createFamily("F1");
        builder.addChildToFamily(family, person1);
        builder.addChildToFamily(family, person2);
        builder.addChildToFamily(family, person3);

        final BirthDateEstimator estimator = createBirthEstimator(person3);
        final LocalDate actual = estimator.estimateBirthDate();
        assertNull(actual, "Expected a null date");
    }

    /** */
    @Test
    void testFromOlderSibling() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();

        builder.createPersonEvent(person1, "Birth", "11 JUL 1960");

        final Family family = builder.createFamily("F1");
        builder.addChildToFamily(family, person1);
        builder.addChildToFamily(family, person2);

        final BirthDateEstimator estimator = createBirthEstimator(person2);
        final int birthYear = 1962;
        final int birthMonth = 7;
        final int birthDay = 1;
        Calendar cal = new Calendar.Builder().setDate(birthYear, birthMonth - 1, birthDay).build();
        final LocalDate expected = new LocalDate(cal);
        final LocalDate actual = estimator.estimateBirthDate();
        assertEquals(expected, actual, "Dates should match");
    }

    /** */
    @Test
    void testFromOlderSiblings() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Person person3 = createAnonymousJones();

        builder.createPersonEvent(person1, "Birth", "11 JUL 1960");

        final Family family = builder.createFamily("F1");
        builder.addChildToFamily(family, person1);
        builder.addChildToFamily(family, person2);
        builder.addChildToFamily(family, person3);

        final BirthDateEstimator estimator = createBirthEstimator(person3);
        final int birthYear = 1964;
        final int birthMonth = 7;
        final int birthDay = 1;
        Calendar cal = new Calendar.Builder().setDate(birthYear, birthMonth - 1, birthDay).build();
        final LocalDate expected = new LocalDate(cal);
        final LocalDate actual = estimator.estimateBirthDate();
        assertEquals(expected, actual, "Dates should match");
    }

    /** */
    @Test
    void testFromEmptyOlderSiblings() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Person person3 = createAnonymousJones();

        final Family family = builder.createFamily("F1");
        builder.addChildToFamily(family, person1);
        builder.addChildToFamily(family, person2);
        builder.addChildToFamily(family, person3);

        final BirthDateEstimator estimator = createBirthEstimator(person3);
        final LocalDate actual = estimator.estimateBirthDate();
        assertNull(actual, "Expected a null date");
    }

    /** */
    @Test
    void testFromOwnMarriage() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();

        final Family family = builder.createFamily("F1");
        builder.addHusbandToFamily(family, person1);
        builder.addWifeToFamily(family, person2);

        builder.createFamilyEvent(family, "Marriage", "27 MAY 1984");

        final BirthDateEstimator estimator = createBirthEstimator(person2);
        final int birthYear = 1959;
        final int birthMonth = 1;
        final int birthDay = 1;
        Calendar cal = new Calendar.Builder().setDate(birthYear, birthMonth - 1, birthDay).build();
        final LocalDate expected = new LocalDate(cal);
        final LocalDate actual = estimator.estimateBirthDate();
        assertEquals(expected, actual, "Dates should match");
    }

    /** */
    @Test
    void testFromOwnMarriageWithNoDate() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();

        final Family family = builder.createFamily("F1");
        builder.addHusbandToFamily(family, person1);
        builder.addWifeToFamily(family, person2);

        final BirthDateEstimator estimator = createBirthEstimator(person2);
        final LocalDate actual = estimator.estimateBirthDate();
        assertNull(actual, "Expected a null date");
    }

    /** */
    @Test
    void testFromOwnMarriages() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Person person3 = createAnonymousJones();

        final Family family1 = builder.createFamily("F1");
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);

        final Family family2 = builder.createFamily("F2");
        builder.addHusbandToFamily(family2, person1);
        builder.addWifeToFamily(family2, person3);

        builder.createFamilyEvent(family1, "Marriage", "14 JUN 1980");
        builder.createFamilyEvent(family2, "Marriage", "27 MAY 1984");

        final BirthDateEstimator estimator = createBirthEstimator(person1);
        final int birthYear = 1955;
        final int birthMonth = 1;
        final int birthDay = 1;
        Calendar cal = new Calendar.Builder().setDate(birthYear, birthMonth - 1, birthDay).build();
        final LocalDate expected = new LocalDate(cal);
        final LocalDate actual = estimator.estimateBirthDate();
        assertEquals(expected, actual, "Dates should match");
    }

    /** */
    @Test
    void testFromParentsMarriage() {
        final Person person1 = createJRandom();
        builder.createPersonEvent(person1, "Birth", "11 JUL 1960");

        final Person person2 = createAnonymousSmith();
        final Person person3 = createAnonymousJones();

        final Family family1 = builder.createFamily("F1");
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
        Calendar cal = new Calendar.Builder().setDate(birthYear, birthMonth - 1, birthDay).build();
        final LocalDate expected = new LocalDate(cal);
        final LocalDate actual = estimator.estimateBirthDate();
        assertEquals(expected, actual, "Dates should match");
    }

    /** */
    @Test
    void testFromParentsMarriageWithOlderSiblings() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSmith();
        final Person person3 = createAnonymousJones();
        final Person person4 =
                builder.createPerson("I4", "No Name/Johnson/");
        final Family family1 = builder.createFamily("F1");
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        builder.addChildToFamily(family1, person3);
        builder.addChildToFamily(family1, person4);
        builder.createFamilyEvent(family1, "Marriage", "27 MAY 1984");

        final BirthDateEstimator estimator = createBirthEstimator(person4);
        final int birthYear = 1988;
        final int birthMonth = 5;
        final int birthDay = 1;
        Calendar cal = new Calendar.Builder().setDate(birthYear, birthMonth - 1, birthDay).build();
        final LocalDate expected = new LocalDate(cal);
        final LocalDate actual = estimator.estimateBirthDate();
        assertEquals(expected, actual, "Dates should match");
    }

    /** */
    @Test
    void testFromChild() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSmith();
        final Person person3 = createAnonymousJones();
        builder.createPersonEvent(person3, "Birth", "11 JUL 1960");

        final Family family1 = builder.createFamily("F1");
        builder.addHusbandToFamily(family1, person1);
        builder.addChildToFamily(family1, person2);
        builder.addChildToFamily(family1, person3);

        final BirthDateEstimator estimator = createBirthEstimator(person1);
        final int birthYear = 1931;
        final int birthMonth = 7;
        final int birthDay = 1;
        Calendar cal = new Calendar.Builder().setDate(birthYear, birthMonth - 1, birthDay).build();
        final LocalDate expected = new LocalDate(cal);
        final LocalDate actual = estimator.estimateBirthDate();
        assertEquals(expected, actual, "Dates should match");
    }

    /** */
    @Test
    void testFromChildren() {
        final Person person1 = createJRandom();
        final Person person3 = createAnonymousJones();
        builder.createPersonEvent(person3, "Birth", "11 JUL 1960");

        final Family family1 = builder.createFamily("F1");
        builder.addHusbandToFamily(family1, person1);
        builder.addChildToFamily(family1, person3);

        final BirthDateEstimator estimator = createBirthEstimator(person1);
        final int birthYear = 1933;
        final int birthMonth = 7;
        final int birthDay = 1;
        Calendar cal = new Calendar.Builder().setDate(birthYear, birthMonth - 1, birthDay).build();
        final LocalDate expected = new LocalDate(cal);
        final LocalDate actual = estimator.estimateBirthDate();
        assertEquals(expected, actual, "Dates should match");
    }

    /** */
    @Test
    void testFromHusband() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSmith();
        builder.createPersonEvent(person1, "Birth", "11 JUL 1960");

        final Family family1 = builder.createFamily("F1");
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);

        final BirthDateEstimator estimator = createBirthEstimator(person2);
        final int birthYear = 1965;
        final int birthMonth = 7;
        final int birthDay = 1;
        Calendar cal = new Calendar.Builder().setDate(birthYear, birthMonth - 1, birthDay).build();
        final LocalDate expected = new LocalDate(cal);
        final LocalDate actual = estimator.estimateBirthDate();
        assertEquals(expected, actual, "Dates should match");
    }

    /** */
    @Test
    void testFromWife() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSmith();
        builder.createPersonEvent(person2, "Birth", "11 JUL 1960");

        final Family family1 = builder.createFamily("F1");
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);

        final BirthDateEstimator estimator = createBirthEstimator(person1);
        final int birthYear = 1955;
        final int birthMonth = 7;
        final int birthDay = 1;
        Calendar cal = new Calendar.Builder().setDate(birthYear, birthMonth - 1, birthDay).build();
        final LocalDate expected = new LocalDate(cal);
        final LocalDate actual = estimator.estimateBirthDate();
        assertEquals(expected, actual, "Dates should match");
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
    void testFactoryGedFile() throws IOException {
        final AbstractGedLine top = readFileTestSource();
        final Root root = g2g.create(top);
        final List<Person> unhandled = root.findSurnameInitialLetters().stream()
                .flatMap(letter -> root.findBySurnamesBeginWith(letter).stream())
                .flatMap(surname -> root.findBySurname(surname).stream())
                .filter(person -> createBirthEstimator(person).estimateBirthDate() == null)
                .toList();
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
        assertEquals(0, max, "Shouldn't have found any unhandled dates");
    }

    /** */
    @Test
    void testFromBaptism() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Baptism", "11 JUL 1960");

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final int birthYear = 1960;
        final int birthMonth = 7;
        final int birthDay = 1;
        Calendar cal = new Calendar.Builder().setDate(birthYear, birthMonth - 1, birthDay).build();
        final LocalDate expected = new LocalDate(cal);
        final LocalDate actual = estimator.estimateBirthDate();
        assertEquals(expected, actual, "Dates should match");
    }

    /** */
    @Test
    void testChristening() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Christening", "11 JUL 1960");

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final int birthYear = 1960;
        final int birthMonth = 7;
        final int birthDay = 1;
        Calendar cal = new Calendar.Builder().setDate(birthYear, birthMonth - 1, birthDay).build();
        final LocalDate expected = new LocalDate(cal);
        final LocalDate actual = estimator.estimateBirthDate();
        assertEquals(expected, actual, "Dates should match");
    }

    /** */
    @Test
    void testBarMitzah() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Bar Mitzvah", "11 JUL 1960");

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final int birthYear = 1947;
        final int birthMonth = 7;
        final int birthDay = 1;
        Calendar cal = new Calendar.Builder().setDate(birthYear, birthMonth - 1, birthDay).build();
        final LocalDate expected = new LocalDate(cal);
        final LocalDate actual = estimator.estimateBirthDate();
        assertEquals(expected, actual, "Dates should match");
    }

    /** */
    @Test
    void testBatMitzvah() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Bat Mitzvah", "11 JUL 1960");

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final int birthYear = 1947;
        final int birthMonth = 7;
        final int birthDay = 1;
        Calendar cal = new Calendar.Builder().setDate(birthYear, birthMonth - 1, birthDay).build();
        final LocalDate expected = new LocalDate(cal);
        final LocalDate actual = estimator.estimateBirthDate();
        assertEquals(expected, actual, "Dates should match");
    }

    /** */
    @Test
    void testDeath() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Death", "11 JUL 1960");

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final int birthYear = 1885;
        final int birthMonth = 1;
        final int birthDay = 1;
        Calendar cal = new Calendar.Builder().setDate(birthYear, birthMonth - 1, birthDay).build();
        final LocalDate expected = new LocalDate(cal);
        final LocalDate actual = estimator.estimateBirthDate();
        assertEquals(expected, actual, "Dates should match");
    }

    /** */
    @Test
    void testBurial() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Burial", "11 JUL 1960");

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final int birthYear = 1885;
        final int birthMonth = 1;
        final int birthDay = 1;
        Calendar cal = new Calendar.Builder().setDate(birthYear, birthMonth - 1, birthDay).build();
        final LocalDate expected = new LocalDate(cal);
        final LocalDate actual = estimator.estimateBirthDate();
        assertEquals(expected, actual, "Dates should match");
    }

    /** */
    @Test
    void testChanged() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Changed", "11 JUL 1960");

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final LocalDate actual = estimator.estimateBirthDate();
        assertNull(actual, "Changed event should return null");
    }

    /** */
    @Test
    void testOtherEvent() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Occupation", "11 JUL 1960");

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final int birthYear = 1935;
        final int birthMonth = 1;
        final int birthDay = 1;
        Calendar cal = new Calendar.Builder().setDate(birthYear, birthMonth - 1, birthDay).build();
        final LocalDate expected = new LocalDate(cal);
        final LocalDate actual = estimator.estimateBirthDate();
        assertEquals(expected, actual, "Dates should match");
    }

    /** */
    @Test
    void testNoDate() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Death");

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final LocalDate actual = estimator.estimateBirthDate();
        assertNull(actual, "No date event should return null");
    }

    /** */
    @Test
    void testBadDate() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Changed", "");

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final LocalDate actual = estimator.estimateBirthDate();
        assertNull(actual, "Bad date event should return null");
    }

    /** */
    @Test
    void testEstimateBirthDate() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Birth", "11 JUL 1960");

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final int birthYear = 1960;
        final int birthMonth = 7;
        final int birthDay = 11;
        Calendar cal = new Calendar.Builder().setDate(birthYear, birthMonth - 1, birthDay).build();
        final LocalDate expected = new LocalDate(cal);
        final LocalDate actual = estimator.estimate();
        assertEquals(expected, actual, "Dates should match");
    }

    /** */
    @Test
    void testEstimateMarriageDate() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Marriage", "11 JUL 1960");

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final int birthYear = 1935;
        final int birthMonth = 1;
        final int birthDay = 1;
        Calendar cal = new Calendar.Builder().setDate(birthYear, birthMonth - 1, birthDay).build();
        final LocalDate expected = new LocalDate(cal);
        final LocalDate actual = estimator.estimate();
        assertEquals(expected, actual, "Dates should match");
    }

    /** */
    @Test
    void testEstimateOtherDate() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Occupation", "11 JUL 1960");

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final int birthYear = 1935;
        final int birthMonth = 1;
        final int birthDay = 1;
        Calendar cal = new Calendar.Builder().setDate(birthYear, birthMonth - 1, birthDay).build();
        final LocalDate expected = new LocalDate(cal);
        final LocalDate actual = estimator.estimate();
        assertEquals(expected, actual, "Dates should match");
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
     * Read data for tests available to prepare data for tests.
     *
     * @return a populated GedLine parse tree.
     * @throws IOException because reader might throw.
     */
    private AbstractGedLine readFileTestSource() throws IOException {
        return TestResourceReader.readFileTestSource(this, "gl120368.ged");
    }
}
