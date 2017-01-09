package org.schoellerfamily.gedbrowser.analytics.order.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.analytics.order.OrderAnalyzerResult;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
public class OrderAnalyzerFamilytest {
    /** */
    private final OrderAnalyzerTestHelper helper =
            new OrderAnalyzerTestHelper();

    /** */
    @Test
    public void testPersonWithOneUndatedFamilyMatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Person person2 = builder.createPerson2();
        final Family family = builder.createFamily1();
        builder.addHusbandToFamily(family, person1);
        builder.addWifeToFamily(family, person2);
        final OrderAnalyzerResult result = helper.analyze(person1);
        assertTrue("Expected correct with one undated family",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithTwoUndatedFamiliesMatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Person person2 = builder.createPerson2();
        final Family family1 = builder.createFamily1();
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        final Person person3 = builder.createPerson3();
        final Family family2 = builder.createFamily2();
        builder.addHusbandToFamily(family2, person1);
        builder.addWifeToFamily(family2, person3);
        final OrderAnalyzerResult result = helper.analyze(person1);
        assertTrue("Expected correct with 2 undated families",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithTwoFamiliesFirstDatedMatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Person person2 = builder.createPerson2();
        final Family family1 = builder.createFamily1();
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        builder.createFamilyEvent(family1, "Marriage", "8 JAN 2016");
        final Person person3 = builder.createPerson3();
        final Family family2 = builder.createFamily2();
        builder.addHusbandToFamily(family2, person1);
        builder.addWifeToFamily(family2, person3);
        final OrderAnalyzerResult result = helper.analyze(person1);
        assertTrue("Expected correct with 2 families only the 1st is dated",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithTwoFamiliesSecondDatedMatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Person person2 = builder.createPerson2();
        final Family family1 = builder.createFamily1();
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        final Person person3 = builder.createPerson3();
        final Family family2 = builder.createFamily2();
        builder.addHusbandToFamily(family2, person1);
        builder.addWifeToFamily(family2, person3);
        builder.createFamilyEvent(family2, "Marriage", "8 JAN 2016");
        final OrderAnalyzerResult result = helper.analyze(person1);
        assertTrue("Expected correct with 2 families only the 2nd is dated",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithTwoFamiliesDatedInOrderMatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Person person2 = builder.createPerson2();
        final Family family1 = builder.createFamily1();
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        builder.createFamilyEvent(family1, "Marriage", "7 JAN 2016");
        final Person person3 = builder.createPerson3();
        final Family family2 = builder.createFamily2();
        builder.addHusbandToFamily(family2, person1);
        builder.addWifeToFamily(family2, person3);
        builder.createFamilyEvent(family2, "Marriage", "8 JAN 2016");
        final OrderAnalyzerResult result = helper.analyze(person1);
        assertTrue("Expected correct with 2 families dates are in order",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithTwoFamiliesDatedOutOfOrderMismatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Person person2 = builder.createPerson2();
        final Family family1 = builder.createFamily1();
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        builder.createFamilyEvent(family1, "Marriage", "8 JAN 2016");
        final Person person3 = builder.createPerson3();
        final Family family2 = builder.createFamily2();
        builder.addHusbandToFamily(family2, person1);
        builder.addWifeToFamily(family2, person3);
        builder.createFamilyEvent(family2, "Marriage", "7 JAN 2016");
        final OrderAnalyzerResult result = helper.analyze(person1);
        assertFalse("Expected incorrect with 2 families dates are out of order",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithTwoFamiliesDatedInOrderWithUndatedBtwnMatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Person person2 = builder.createPerson2();
        final Family family1 = builder.createFamily1();
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        builder.createFamilyEvent(family1, "Marriage", "7 JAN 2016");

        final Person person4 = builder.createPerson4();
        final Family family3 = builder.createFamily3();
        builder.addHusbandToFamily(family3, person1);
        builder.addWifeToFamily(family3, person4);

        final Person person3 = builder.createPerson3();
        final Family family2 = builder.createFamily2();
        builder.addHusbandToFamily(family2, person1);
        builder.addWifeToFamily(family2, person3);
        builder.createFamilyEvent(family2, "Marriage", "8 JAN 2016");
        final OrderAnalyzerResult result = helper.analyze(person1);
        assertTrue("Expected correct with 2 families dates are in order",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithTwoFamiliesDatedOutOfOrderUndatedBtwnMismatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Person person2 = builder.createPerson2();
        final Family family1 = builder.createFamily1();
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        builder.createFamilyEvent(family1, "Marriage", "8 JAN 2016");

        final Person person4 = builder.createPerson4();
        final Family family3 = builder.createFamily3();
        builder.addHusbandToFamily(family3, person1);
        builder.addWifeToFamily(family3, person4);

        final Person person3 = builder.createPerson3();
        final Family family2 = builder.createFamily2();
        builder.addHusbandToFamily(family2, person1);
        builder.addWifeToFamily(family2, person3);
        builder.createFamilyEvent(family2, "Marriage", "7 JAN 2016");
        final OrderAnalyzerResult result = helper.analyze(person1);
        assertFalse("Expected incorrect with 2 families dates are out of order",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithFamilyDatesInOrderMatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Person person2 = builder.createPerson2();
        final Family family1 = builder.createFamily1();
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        builder.createFamilyEvent(family1, "Engaged", "7 JAN 2016");
        builder.createFamilyEvent(family1, "Marriage", "8 JAN 2016");
        final OrderAnalyzerResult result = helper.analyze(person1);
        assertTrue("Expected correct with 2 events in order",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithFamilyDatesOutOfOrderMismatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Person person2 = builder.createPerson2();
        final Family family1 = builder.createFamily1();
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        builder.createFamilyEvent(family1, "Marriage", "8 JAN 2016");
        builder.createFamilyEvent(family1, "Engaged", "7 JAN 2016");
        final OrderAnalyzerResult result = helper.analyze(person1);
        assertFalse("Expected incorrect with 2 events out of order",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWith1stFamilyDatesOutOfOrderMismatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Person person2 = builder.createPerson2();
        final Family family1 = builder.createFamily1();
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        builder.createFamilyEvent(family1, "Marriage", "8 JAN 2016");
        builder.createFamilyEvent(family1, "Engaged", "7 JAN 2016");

        final Person person3 = builder.createPerson3();
        final Family family2 = builder.createFamily2();
        builder.addHusbandToFamily(family2, person1);
        builder.addWifeToFamily(family2, person3);
        builder.createFamilyEvent(family2, "Marriage", "9 JAN 2016");

        final OrderAnalyzerResult result = helper.analyze(person1);
        assertEquals("Expected incorrect with 2 events out of order",
                1, result.getMismatches().size());
    }

    /** */
    @Test
    public void testPersonWith2ndBefore1stAnd1stFamDatesOutOfOrderMismatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Person person2 = builder.createPerson2();
        final Family family1 = builder.createFamily1();
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        builder.createFamilyEvent(family1, "Marriage", "8 JAN 2016");
        builder.createFamilyEvent(family1, "Engaged", "7 JAN 2016");

        final Person person3 = builder.createPerson3();
        final Family family2 = builder.createFamily2();
        builder.addHusbandToFamily(family2, person1);
        builder.addWifeToFamily(family2, person3);
        builder.createFamilyEvent(family2, "Marriage", "6 JAN 2016");

        final OrderAnalyzerResult result = helper.analyze(person1);
        assertEquals("Expected incorrect with 2 events out of order",
                2, result.getMismatches().size());
    }
}
