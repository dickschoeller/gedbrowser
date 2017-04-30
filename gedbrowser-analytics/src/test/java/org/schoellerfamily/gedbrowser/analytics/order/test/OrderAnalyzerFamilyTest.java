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
    private OrderAnalyzerTestHelper helper;

    /** */
    @Autowired
    private GedObjectBuilder builder;

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
    public void testPersonWithOneUndatedFamilyMatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family, person1);
        familyBuilder().addWifeToFamily(family, person2);
        final OrderAnalyzerResult result = helper.analyze(person1);
        assertTrue("Expected correct with one undated family",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithTwoUndatedFamiliesMatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family1 = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family1, person1);
        familyBuilder().addWifeToFamily(family1, person2);
        final Person person3 = createAnonymousJones();
        final Family family2 = familyBuilder().createFamily("F2");
        familyBuilder().addHusbandToFamily(family2, person1);
        familyBuilder().addWifeToFamily(family2, person3);
        final OrderAnalyzerResult result = helper.analyze(person1);
        assertTrue("Expected correct with 2 undated families",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithTwoFamiliesFirstDatedMatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family1 = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family1, person1);
        familyBuilder().addWifeToFamily(family1, person2);
        familyBuilder().createFamilyEvent(family1, "Marriage", "8 JAN 2016");
        final Person person3 = createAnonymousJones();
        final Family family2 = familyBuilder().createFamily("F2");
        familyBuilder().addHusbandToFamily(family2, person1);
        familyBuilder().addWifeToFamily(family2, person3);
        final OrderAnalyzerResult result = helper.analyze(person1);
        assertTrue("Expected correct with 2 families only the 1st is dated",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithTwoFamiliesSecondDatedMatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family1 = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family1, person1);
        familyBuilder().addWifeToFamily(family1, person2);
        final Person person3 = createAnonymousJones();
        final Family family2 = familyBuilder().createFamily("F2");
        familyBuilder().addHusbandToFamily(family2, person1);
        familyBuilder().addWifeToFamily(family2, person3);
        familyBuilder().createFamilyEvent(family2, "Marriage", "8 JAN 2016");
        final OrderAnalyzerResult result = helper.analyze(person1);
        assertTrue("Expected correct with 2 families only the 2nd is dated",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithTwoFamiliesDatedInOrderMatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family1 = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family1, person1);
        familyBuilder().addWifeToFamily(family1, person2);
        familyBuilder().createFamilyEvent(family1, "Marriage", "7 JAN 2016");
        final Person person3 = createAnonymousJones();
        final Family family2 = familyBuilder().createFamily("F2");
        familyBuilder().addHusbandToFamily(family2, person1);
        familyBuilder().addWifeToFamily(family2, person3);
        familyBuilder().createFamilyEvent(family2, "Marriage", "8 JAN 2016");
        final OrderAnalyzerResult result = helper.analyze(person1);
        assertTrue("Expected correct with 2 families dates are in order",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithTwoFamiliesDatedOutOfOrderMismatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family1 = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family1, person1);
        familyBuilder().addWifeToFamily(family1, person2);
        familyBuilder().createFamilyEvent(family1, "Marriage", "8 JAN 2016");
        final Person person3 = createAnonymousJones();
        final Family family2 = familyBuilder().createFamily("F2");
        familyBuilder().addHusbandToFamily(family2, person1);
        familyBuilder().addWifeToFamily(family2, person3);
        familyBuilder().createFamilyEvent(family2, "Marriage", "7 JAN 2016");
        final OrderAnalyzerResult result = helper.analyze(person1);
        assertFalse("Expected incorrect with 2 families dates are out of order",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithTwoFamiliesDatedInOrderWithUndatedBtwnMatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family1 = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family1, person1);
        familyBuilder().addWifeToFamily(family1, person2);
        familyBuilder().createFamilyEvent(family1, "Marriage", "7 JAN 2016");

        final Person person4 = createTooTall();
        final Family family3 = familyBuilder().createFamily("F3");
        familyBuilder().addHusbandToFamily(family3, person1);
        familyBuilder().addWifeToFamily(family3, person4);

        final Person person3 = createAnonymousJones();
        final Family family2 = familyBuilder().createFamily("F2");
        familyBuilder().addHusbandToFamily(family2, person1);
        familyBuilder().addWifeToFamily(family2, person3);
        familyBuilder().createFamilyEvent(family2, "Marriage", "8 JAN 2016");
        final OrderAnalyzerResult result = helper.analyze(person1);
        assertTrue("Expected correct with 2 families dates are in order",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithTwoFamiliesDatedOutOfOrderUndatedBtwnMismatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family1 = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family1, person1);
        familyBuilder().addWifeToFamily(family1, person2);
        familyBuilder().createFamilyEvent(family1, "Marriage", "8 JAN 2016");

        final Person person4 = createTooTall();
        final Family family3 = familyBuilder().createFamily("F3");
        familyBuilder().addHusbandToFamily(family3, person1);
        familyBuilder().addWifeToFamily(family3, person4);

        final Person person3 = createAnonymousJones();
        final Family family2 = familyBuilder().createFamily("F2");
        familyBuilder().addHusbandToFamily(family2, person1);
        familyBuilder().addWifeToFamily(family2, person3);
        familyBuilder().createFamilyEvent(family2, "Marriage", "7 JAN 2016");
        final OrderAnalyzerResult result = helper.analyze(person1);
        assertFalse("Expected incorrect with 2 families dates are out of order",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithFamilyDatesInOrderMatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family1 = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family1, person1);
        familyBuilder().addWifeToFamily(family1, person2);
        familyBuilder().createFamilyEvent(family1, "Engaged", "7 JAN 2016");
        familyBuilder().createFamilyEvent(family1, "Marriage", "8 JAN 2016");
        final OrderAnalyzerResult result = helper.analyze(person1);
        assertTrue("Expected correct with 2 events in order",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithFamilyDatesOutOfOrderMismatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family1 = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family1, person1);
        familyBuilder().addWifeToFamily(family1, person2);
        familyBuilder().createFamilyEvent(family1, "Marriage", "8 JAN 2016");
        familyBuilder().createFamilyEvent(family1, "Engaged", "7 JAN 2016");
        final OrderAnalyzerResult result = helper.analyze(person1);
        assertFalse("Expected incorrect with 2 events out of order",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWith1stFamilyDatesOutOfOrderMismatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family1 = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family1, person1);
        familyBuilder().addWifeToFamily(family1, person2);
        familyBuilder().createFamilyEvent(family1, "Marriage", "8 JAN 2016");
        familyBuilder().createFamilyEvent(family1, "Engaged", "7 JAN 2016");

        final Person person3 = createAnonymousJones();
        final Family family2 = familyBuilder().createFamily("F2");
        familyBuilder().addHusbandToFamily(family2, person1);
        familyBuilder().addWifeToFamily(family2, person3);
        familyBuilder().createFamilyEvent(family2, "Marriage", "9 JAN 2016");

        final OrderAnalyzerResult result = helper.analyze(person1);
        assertEquals("Expected incorrect with 2 events out of order",
                1, result.getMismatches().size());
    }

    /** */
    @Test
    public void testPersonWith2ndBefore1stAnd1stFamDatesOutOfOrderMismatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family1 = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family1, person1);
        familyBuilder().addWifeToFamily(family1, person2);
        familyBuilder().createFamilyEvent(family1, "Marriage", "8 JAN 2016");
        familyBuilder().createFamilyEvent(family1, "Engaged", "7 JAN 2016");

        final Person person3 = createAnonymousJones();
        final Family family2 = familyBuilder().createFamily("F2");
        familyBuilder().addHusbandToFamily(family2, person1);
        familyBuilder().addWifeToFamily(family2, person3);
        familyBuilder().createFamilyEvent(family2, "Marriage", "6 JAN 2016");

        final OrderAnalyzerResult result = helper.analyze(person1);
        assertEquals("Expected incorrect with 2 events out of order",
                2, result.getMismatches().size());
    }

    /** */
    @Test
    public void testPersonWithDateFromChildMismatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family1 = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family1, person1);
        familyBuilder().addWifeToFamily(family1, person2);
        familyBuilder().createFamilyEvent(family1, "Marriage", "8 JAN 2016");
        familyBuilder().createFamilyEvent(family1, "Engaged", "7 JAN 2016");

        final Person person3 = createAnonymousJones();
        final Family family2 = familyBuilder().createFamily("F2");
        familyBuilder().addHusbandToFamily(family2, person1);
        familyBuilder().addWifeToFamily(family2, person3);
        final Person person4 = createTooTall();
        personBuilder().createPersonEvent(person4, "Birth", "6 JAN 2016");
        familyBuilder().addChildToFamily(family2, person4);

        final OrderAnalyzerResult result = helper.analyze(person1);
        assertEquals("Expected incorrect with 2 events out of order",
                2, result.getMismatches().size());
    }
}
