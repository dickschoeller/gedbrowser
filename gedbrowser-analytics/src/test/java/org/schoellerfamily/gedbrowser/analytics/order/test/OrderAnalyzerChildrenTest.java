package org.schoellerfamily.gedbrowser.analytics.order.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.analytics.order.OrderAnalyzerResult;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.FamilyBuilder;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.datamodel.util.PersonBuilder;

/**
 * @author Dick Schoeller
 */
public final class OrderAnalyzerChildrenTest implements AnalyzerTest {
    /** */
    private final OrderAnalyzerTestHelper helper =
            new OrderAnalyzerTestHelper();

    /** */
    private final GedObjectBuilder builder = new GedObjectBuilder();

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
    public void testPersonWithFamilyOneUndatedChildMatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family, person1);
        familyBuilder().addWifeToFamily(family, person2);
        final Person person3 = createAnonymousJones();
        familyBuilder().addChildToFamily(family, person3);
        final OrderAnalyzerResult result = helper.analyze(person1);
        assertTrue("Expected correct with one family with undated child",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithFamilyTwoUndatedChildrenMatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family, person1);
        familyBuilder().addWifeToFamily(family, person2);
        final Person person3 = createAnonymousJones();
        familyBuilder().addChildToFamily(family, person3);
        final Person person4 = createTooTall();
        familyBuilder().addChildToFamily(family, person4);
        final OrderAnalyzerResult result = helper.analyze(person1);
        assertTrue("Expected correct with one family with undated children",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithFamilyTwoChildren1stUndatedMatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family, person1);
        familyBuilder().addWifeToFamily(family, person2);
        final Person person3 = createAnonymousJones();
        familyBuilder().addChildToFamily(family, person3);
        final Person person4 = createTooTall();
        personBuilder().createPersonEvent(person4, "Birth", "9 JAN 2017");
        familyBuilder().addChildToFamily(family, person4);
        final OrderAnalyzerResult result = helper.analyze(person1);
        assertTrue("Expected correct with one family with children 1st undated",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithFamilyTwoChildren1stUndatedBirthMatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family, person1);
        familyBuilder().addWifeToFamily(family, person2);
        final Person person3 = createAnonymousJones();
        personBuilder().createPersonEvent(person3, "Birth");
        familyBuilder().addChildToFamily(family, person3);
        final Person person4 = createTooTall();
        personBuilder().createPersonEvent(person4, "Birth", "9 JAN 2017");
        familyBuilder().addChildToFamily(family, person4);
        final OrderAnalyzerResult result = helper.analyze(person1);
        assertTrue("Expected correct with one family with children 1st undated",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithFamilyTwoChildren2ndUndatedMatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family, person1);
        familyBuilder().addWifeToFamily(family, person2);
        final Person person3 = createAnonymousJones();
        personBuilder().createPersonEvent(person3, "Birth", "9 JAN 2017");
        familyBuilder().addChildToFamily(family, person3);
        final Person person4 = createTooTall();
        familyBuilder().addChildToFamily(family, person4);
        final OrderAnalyzerResult result = helper.analyze(person1);
        assertTrue("Expected correct with one family with children 2nd undated",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithFamilyTwoChildren2ndUndatedBirthMatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family, person1);
        familyBuilder().addWifeToFamily(family, person2);
        final Person person3 = createAnonymousJones();
        personBuilder().createPersonEvent(person3, "Birth", "9 JAN 2017");
        familyBuilder().addChildToFamily(family, person3);
        final Person person4 = createTooTall();
        personBuilder().createPersonEvent(person4, "Birth");
        familyBuilder().addChildToFamily(family, person4);
        final OrderAnalyzerResult result = helper.analyze(person1);
        assertTrue("Expected correct with one family with children 2nd undated",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithFamilyTwoChildrenDatedInOrderMatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family, person1);
        familyBuilder().addWifeToFamily(family, person2);
        final Person person3 = createAnonymousJones();
        personBuilder().createPersonEvent(person3, "Birth", "8 JAN 2016");
        familyBuilder().addChildToFamily(family, person3);
        final Person person4 = createTooTall();
        personBuilder().createPersonEvent(person3, "Birth", "9 JAN 2017");
        familyBuilder().addChildToFamily(family, person4);
        final OrderAnalyzerResult result = helper.analyze(person1);
        assertTrue("Expected correct 1 family with 2 children dated in order",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithFamilyTwoChildrenDatedOutOfOrderMisMatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family, person1);
        familyBuilder().addWifeToFamily(family, person2);
        final Person person3 = createAnonymousJones();
        personBuilder().createPersonEvent(person3, "Birth", "9 JAN 2017");
        familyBuilder().addChildToFamily(family, person3);
        final Person person4 = createTooTall();
        personBuilder().createPersonEvent(person4, "Birth", "8 JAN 2016");
        familyBuilder().addChildToFamily(family, person4);
        final OrderAnalyzerResult result = helper.analyze(person1);
        assertFalse("Expected incorrect 1 family with 2 children out of order",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithFamilyTwoChildrenDatedNearBirthInOrderMatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family, person1);
        familyBuilder().addWifeToFamily(family, person2);
        final Person person3 = createAnonymousJones();
        personBuilder().createPersonEvent(person3, "Birth", "8 JAN 2016");
        familyBuilder().addChildToFamily(family, person3);
        final Person person4 = createTooTall();
        personBuilder().createPersonEvent(person3, "Baptism", "9 JAN 2017");
        familyBuilder().addChildToFamily(family, person4);
        final OrderAnalyzerResult result = helper.analyze(person1);
        assertTrue("Expected correct 1 family with 2 children dated near "
                + "birth events in order",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithFamilyTwoChildrenDateBaptismOutOrderMisatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family, person1);
        familyBuilder().addWifeToFamily(family, person2);
        final Person person3 = createAnonymousJones();
        personBuilder().createPersonEvent(person3, "Birth", "9 JAN 2017");
        familyBuilder().addChildToFamily(family, person3);
        final Person person4 = createTooTall();
        personBuilder().createPersonEvent(person4, "Baptism", "8 JAN 2016");
        familyBuilder().addChildToFamily(family, person4);
        final OrderAnalyzerResult result = helper.analyze(person1);
        assertFalse("Expected incorrect 1 family with 2 children dated near "
                + "birth events out of order",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithFamilyTwoChildrenNamingBirthOutOrderMisatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family, person1);
        familyBuilder().addWifeToFamily(family, person2);
        final Person person3 = createAnonymousJones();
        personBuilder().createPersonEvent(person3, "Birth", "9 JAN 2017");
        familyBuilder().addChildToFamily(family, person3);
        final Person person4 = createTooTall();
        personBuilder().createPersonEvent(person4, "Naming", "8 JAN 2016");
        familyBuilder().addChildToFamily(family, person4);
        final OrderAnalyzerResult result = helper.analyze(person1);
        assertFalse("Expected incorrect 1 family with 2 children dated near "
                + "birth events out of order",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithFamily3ChildrenChristeningBirthOutOrderMisatch() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Family family = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family, person1);
        familyBuilder().addWifeToFamily(family, person2);
        final Person person3 = createAnonymousJones();
        personBuilder().createPersonEvent(person3, "Birth", "9 JAN 2017");
        familyBuilder().addChildToFamily(family, person3);
        final Person person4 = createTooTall();
        personBuilder().createPersonEvent(person4, "Christening", "8 JAN 2016");
        familyBuilder().addChildToFamily(family, person4);
        final Person person5 =
                personBuilder().createPerson("I5", "Anonyma/Schoeller/");
        familyBuilder().addChildToFamily(family, person5);
        personBuilder().createPersonEvent(person5, "Birth", "10 JAN 2017");
        final OrderAnalyzerResult result = helper.analyze(person1);
        assertFalse("Expected incorrect 1 family with 2 children dated near "
                + "birth events out of order",
                result.isCorrect());
    }
}
