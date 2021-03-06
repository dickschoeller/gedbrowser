package org.schoellerfamily.gedbrowser.analytics.order.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.analytics.order.OrderAnalyzerResult;
import org.schoellerfamily.gedbrowser.analytics.test.TestConfiguration;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.FamilyBuilder;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.datamodel.util.PersonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
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
        assertTrue("Expected correct with one undated family",
                result.isCorrect());
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
        assertTrue("Expected correct with 2 undated families",
                result.isCorrect());
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
        assertTrue("Expected correct with 2 families only the 1st is dated",
                result.isCorrect());
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
        assertTrue("Expected correct with 2 families only the 2nd is dated",
                result.isCorrect());
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
        assertTrue("Expected correct with 2 families dates are in order",
                result.isCorrect());
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
        assertFalse("Expected incorrect with 2 families dates are out of order",
                result.isCorrect());
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
        assertTrue("Expected correct with 2 families dates are in order",
                result.isCorrect());
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
        assertFalse("Expected incorrect with 2 families dates are out of order",
                result.isCorrect());
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
        assertTrue("Expected correct with 2 events in order",
                result.isCorrect());
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
        assertFalse("Expected incorrect with 2 events out of order",
                result.isCorrect());
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
        assertEquals("Expected incorrect with 2 events out of order",
                1, result.getMismatches().size());
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
        assertEquals("Expected incorrect with 2 events out of order",
                2, result.getMismatches().size());
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
        assertEquals("Expected incorrect with 2 events out of order",
                2, result.getMismatches().size());
    }
}
