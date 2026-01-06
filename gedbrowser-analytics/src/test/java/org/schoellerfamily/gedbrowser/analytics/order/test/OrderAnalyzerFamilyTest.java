package org.schoellerfamily.gedbrowser.analytics.order.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.analytics.order.OrderAnalyzerResult;
import org.schoellerfamily.gedbrowser.analytics.test.TestConfiguration;
import org.schoellerfamily.gedbrowser.datamodel.Family;
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
public final class OrderAnalyzerFamilyTest implements AnalyzerTest {
    /** */
    @Autowired
    private OrderAnalyzerTestWrapper wrapper;

    /** */
    @Autowired
    private GedObjectBuilder builder;

    /**
     * {@inheritDoc}
     */
    @Override
    public PersonBuilder personBuilder() {
        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FamilyBuilder familyBuilder() {
        return builder;
    }

    /** */
    @Test
    public void testPersonWithOneUndatedFamilyMatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family = builder.createFamily("F1");
        builder.addHusbandToFamily(family, person1);
        builder.addWifeToFamily(family, person2);
        final OrderAnalyzerResult result = wrapper.analyze(person1);
        assertTrue(result.isCorrect(), "Expected correct with one undated family");
    }

    /** */
    @Test
    public void testPersonWithTwoUndatedFamiliesMatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family1 = builder.createFamily("F1");
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        final Person person3 = createAnonymousJones();
        final Family family2 = builder.createFamily("F2");
        builder.addHusbandToFamily(family2, person1);
        builder.addWifeToFamily(family2, person3);
        final OrderAnalyzerResult result = wrapper.analyze(person1);
        assertTrue(result.isCorrect(), "Expected correct with 2 undated families");
    }

    /** */
    @Test
    public void testPersonWithTwoFamiliesFirstDatedMatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family1 = builder.createFamily("F1");
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        builder.createFamilyEvent(family1, "Marriage", "8 JAN 2016");
        final Person person3 = createAnonymousJones();
        final Family family2 = builder.createFamily("F2");
        builder.addHusbandToFamily(family2, person1);
        builder.addWifeToFamily(family2, person3);
        final OrderAnalyzerResult result = wrapper.analyze(person1);
        assertTrue(result.isCorrect(), "Expected correct with 2 families only the 1st is dated");
    }

    /** */
    @Test
    public void testPersonWithTwoFamiliesSecondDatedMatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family1 = builder.createFamily("F1");
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        final Person person3 = createAnonymousJones();
        final Family family2 = builder.createFamily("F2");
        builder.addHusbandToFamily(family2, person1);
        builder.addWifeToFamily(family2, person3);
        builder.createFamilyEvent(family2, "Marriage", "8 JAN 2016");
        final OrderAnalyzerResult result = wrapper.analyze(person1);
        assertTrue(result.isCorrect(), "Expected correct with 2 families only the 2nd is dated");
    }

    /** */
    @Test
    public void testPersonWithTwoFamiliesDatedInOrderMatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family1 = builder.createFamily("F1");
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        builder.createFamilyEvent(family1, "Marriage", "7 JAN 2016");
        final Person person3 = createAnonymousJones();
        final Family family2 = builder.createFamily("F2");
        builder.addHusbandToFamily(family2, person1);
        builder.addWifeToFamily(family2, person3);
        builder.createFamilyEvent(family2, "Marriage", "8 JAN 2016");
        final OrderAnalyzerResult result = wrapper.analyze(person1);
        assertTrue(result.isCorrect(), "Expected correct with 2 families dates are in order");
    }

    /** */
    @Test
    public void testPersonWithTwoFamiliesDatedOutOfOrderMismatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family1 = builder.createFamily("F1");
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        builder.createFamilyEvent(family1, "Marriage", "8 JAN 2016");
        final Person person3 = createAnonymousJones();
        final Family family2 = builder.createFamily("F2");
        builder.addHusbandToFamily(family2, person1);
        builder.addWifeToFamily(family2, person3);
        builder.createFamilyEvent(family2, "Marriage", "7 JAN 2016");
        final OrderAnalyzerResult result = wrapper.analyze(person1);
        assertFalse(result.isCorrect(),
            "Expected incorrect with 2 families dates are out of order");
    }

    /** */
    @Test
    public void testPersonWithTwoFamiliesDatedInOrderWithUndatedBtwnMatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family1 = builder.createFamily("F1");
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        builder.createFamilyEvent(family1, "Marriage", "7 JAN 2016");

        final Person person4 = createTooTall();
        final Family family3 = builder.createFamily("F3");
        builder.addHusbandToFamily(family3, person1);
        builder.addWifeToFamily(family3, person4);

        final Person person3 = createAnonymousJones();
        final Family family2 = builder.createFamily("F2");
        builder.addHusbandToFamily(family2, person1);
        builder.addWifeToFamily(family2, person3);
        builder.createFamilyEvent(family2, "Marriage", "8 JAN 2016");
        final OrderAnalyzerResult result = wrapper.analyze(person1);
        assertTrue(result.isCorrect(), "Expected correct with 2 families dates are in order");
    }

    /** */
    @Test
    public void testPersonWithTwoFamiliesDatedOutOfOrderUndatedBtwnMismatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family1 = builder.createFamily("F1");
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        builder.createFamilyEvent(family1, "Marriage", "8 JAN 2016");

        final Person person4 = createTooTall();
        final Family family3 = builder.createFamily("F3");
        builder.addHusbandToFamily(family3, person1);
        builder.addWifeToFamily(family3, person4);

        final Person person3 = createAnonymousJones();
        final Family family2 = builder.createFamily("F2");
        builder.addHusbandToFamily(family2, person1);
        builder.addWifeToFamily(family2, person3);
        builder.createFamilyEvent(family2, "Marriage", "7 JAN 2016");
        final OrderAnalyzerResult result = wrapper.analyze(person1);
        assertFalse(result.isCorrect(),
            "Expected incorrect with 2 families dates are out of order");
    }

    /** */
    @Test
    public void testPersonWithFamilyDatesInOrderMatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family1 = builder.createFamily("F1");
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        builder.createFamilyEvent(family1, "Engaged", "7 JAN 2016");
        builder.createFamilyEvent(family1, "Marriage", "8 JAN 2016");
        final OrderAnalyzerResult result = wrapper.analyze(person1);
        assertTrue(result.isCorrect(), "Expected correct with 2 events in order");
    }

    /** */
    @Test
    public void testPersonWithFamilyDatesOutOfOrderMismatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family1 = builder.createFamily("F1");
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        builder.createFamilyEvent(family1, "Marriage", "8 JAN 2016");
        builder.createFamilyEvent(family1, "Engaged", "7 JAN 2016");
        final OrderAnalyzerResult result = wrapper.analyze(person1);
        assertFalse(result.isCorrect(), "Expected incorrect with 2 events out of order");
    }

    /** */
    @Test
    public void testPersonWith1stFamilyDatesOutOfOrderMismatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family1 = builder.createFamily("F1");
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        builder.createFamilyEvent(family1, "Marriage", "8 JAN 2016");
        builder.createFamilyEvent(family1, "Engaged", "7 JAN 2016");

        final Person person3 = createAnonymousJones();
        final Family family2 = builder.createFamily("F2");
        builder.addHusbandToFamily(family2, person1);
        builder.addWifeToFamily(family2, person3);
        builder.createFamilyEvent(family2, "Marriage", "9 JAN 2016");

        final OrderAnalyzerResult result = wrapper.analyze(person1);
        assertEquals(1, result.getMismatches().size(),
            "Expected incorrect with 2 events out of order");
    }

    /** */
    @Test
    public void testPersonWith2ndBefore1stAnd1stFamDatesOutOfOrderMismatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family1 = builder.createFamily("F1");
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        builder.createFamilyEvent(family1, "Marriage", "8 JAN 2016");
        builder.createFamilyEvent(family1, "Engaged", "7 JAN 2016");

        final Person person3 = createAnonymousJones();
        final Family family2 = builder.createFamily("F2");
        builder.addHusbandToFamily(family2, person1);
        builder.addWifeToFamily(family2, person3);
        builder.createFamilyEvent(family2, "Marriage", "6 JAN 2016");

        final OrderAnalyzerResult result = wrapper.analyze(person1);
        assertEquals(2, result.getMismatches().size(),
            "Expected incorrect with 2 events out of order");
    }

    /** */
    @Test
    public void testPersonWithDateFromChildMismatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family1 = builder.createFamily("F1");
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        builder.createFamilyEvent(family1, "Marriage", "8 JAN 2016");
        builder.createFamilyEvent(family1, "Engaged", "7 JAN 2016");

        final Person person3 = createAnonymousJones();
        final Family family2 = builder.createFamily("F2");
        builder.addHusbandToFamily(family2, person1);
        builder.addWifeToFamily(family2, person3);
        final Person person4 = createTooTall();
        builder.createPersonEvent(person4, "Birth", "6 JAN 2016");
        builder.addChildToFamily(family2, person4);

        final OrderAnalyzerResult result = wrapper.analyze(person1);
        assertEquals(2, result.getMismatches().size(),
            "Expected incorrect with 2 events out of order");
    }
}
