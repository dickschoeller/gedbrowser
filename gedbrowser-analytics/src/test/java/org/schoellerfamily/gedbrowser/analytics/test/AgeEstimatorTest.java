package org.schoellerfamily.gedbrowser.analytics.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.schoellerfamily.gedbrowser.analytics.AgeEstimator;
import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.analytics.order.test.AnalyzerTest;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.FamilyBuilder;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.datamodel.util.PersonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
final class AgeEstimatorTest implements AnalyzerTest {
    /** */
    @Autowired
    private CalendarProvider provider;
    /** */
    @Autowired
    private GedObjectBuilder builder;

    @Override
    public PersonBuilder personBuilder() {
        return builder;
    }

    @Override
    public FamilyBuilder familyBuilder() {
        return builder;
    }

    /**
     * Test against a variety of birth date formats.
     *
     * @param birthDate birth date string
     * @param expectedAge expected age in years
     */
    @ParameterizedTest(name = "birth={0} -> age={1}")
    @CsvSource({
        "'14 DEC 1958', 57",
        "'09 MAY 1960', 55",
        "'01 JAN 1960', 55",
        "'31 DEC 1960', 54",
        "'BEF 1960', 55",
        "'BEF DEC 1960', 55",
        "'BEF 13 DEC 1960', 55",
        "'BEF 14 DEC 1960', 55",
        "'BEF 15 DEC 1960', 55",
        "'BEF 16 DEC 1960', 54",
        "'AFT 1960', 54",
        "'AFT DEC 1960', 54",
        "'AFT JAN 1960', 55",
        "'AFT 12 DEC 1960', 55",
        "'AFT 13 DEC 1960', 55",
        "'AFT 14 DEC 1960', 54",
        "'AFT 15 DEC 1960', 54"
    })
    void testEstimateInYears(final String birthDate,
            final int expectedAge) {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Birth", birthDate);

        final AgeEstimator estimator = new AgeEstimator(person, provider);
        assertEquals(expectedAge, estimator.estimateInYears(),
                "estimate does not match expected value of " + expectedAge);
    }

    /**
     * Test formatting for years/months/days estimation.
     *
     * @param birthDate birth date string
     * @param expected expected formatted age string
     */
    @ParameterizedTest(name = "birth={0} -> {1}")
    @CsvSource({
        "'13 DEC 1960', '55 years, 1 day'",
        "'13 JAN 1961', '54 years, 11 months, 1 day'",
        "'16 DEC 1960', '54 years, 11 months, 28 days'",
        "'14 JAN 1961', '54 years, 11 months'"
    })
    void testEstimateInYearsMonthsDays(final String birthDate,
            final String expected) {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Birth", birthDate);

        final AgeEstimator estimator = new AgeEstimator(person, provider);
        assertEquals(expected, estimator.estimateInYearsMonthsDays(),
                "estimate string doesn't match");
    }
}
