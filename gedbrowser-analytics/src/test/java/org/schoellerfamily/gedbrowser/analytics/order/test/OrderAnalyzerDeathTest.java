package org.schoellerfamily.gedbrowser.analytics.order.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.analytics.order.OrderAnalyzer;
import org.schoellerfamily.gedbrowser.analytics.order.OrderAnalyzerResult;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
public class OrderAnalyzerDeathTest {
    /** */
    private final OrderAnalyzerTestHelper helper =
            new OrderAnalyzerTestHelper();

    /** */
    @Test
    public void testPersonWithOnlyDeathMatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        builder.createPersonEvent(person, "Death", null);
        final OrderAnalyzerResult result = helper.analyze(person);
        assertTrue("Expected correct with only death event",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithDeathAfterNonDeathMatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        builder.createPersonEvent(person, "Education", null);
        builder.createPersonEvent(person, "Death", null);
        final OrderAnalyzerResult result = helper.analyze(person);
        assertTrue("Expected correct with death events are after others",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithNonDeathAfterDeathMismatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        builder.createPersonEvent(person, "Death", null);
        builder.createPersonEvent(person, "Education", null);
        final OrderAnalyzerResult result = helper.analyze(person);
        assertFalse("Expected incorrect with death events are before others",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithWillAfterDeathMatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        builder.createPersonEvent(person, "Death", null);
        builder.createPersonEvent(person, "Will", null);
        final OrderAnalyzerResult result = helper.analyze(person);
        assertTrue("Expected correct with death before will",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithWillBeforeDeathMatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        builder.createPersonEvent(person, "Will", null);
        builder.createPersonEvent(person, "Death", null);
        final OrderAnalyzerResult result = helper.analyze(person);
        assertTrue("Expected correct with will before death",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithWillEducDeathMatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        builder.createPersonEvent(person, "Will", null);
        builder.createPersonEvent(person, "Education", null);
        builder.createPersonEvent(person, "Death", null);
        final OrderAnalyzerResult result = helper.analyze(person);
        assertTrue("Expected correct with will, education, death",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithDeathFuneralBurialMatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        builder.createPersonEvent(person, "Death", null);
        builder.createPersonEvent(person, "Funeral", null);
        builder.createPersonEvent(person, "Burial", null);
        final OrderAnalyzerResult result = helper.analyze(person);
        assertTrue("Expected correct with death, funeral, burial",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithDeathBurialMatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        builder.createPersonEvent(person, "Death", null);
        builder.createPersonEvent(person, "Burial", null);
        final OrderAnalyzerResult result = helper.analyze(person);
        assertTrue("Expected correct with death, burial",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithFuneralBurialDeathMismatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        builder.createPersonEvent(person, "Funeral", null);
        builder.createPersonEvent(person, "Burial", null);
        builder.createPersonEvent(person, "Death", null);
        final OrderAnalyzerResult result = helper.analyze(person);
        assertFalse("Expected incorrect with funeral, burial, death",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithDeathBurialDeathMismatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        builder.createPersonEvent(person, "Death", null);
        builder.createPersonEvent(person, "Burial", null);
        builder.createPersonEvent(person, "Death", null);
        final OrderAnalyzerResult result = helper.analyze(person);
        assertFalse("Expected incorrect with death, burial, death",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithBurialBurialBurialMatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        builder.createPersonEvent(person, "Burial", null);
        builder.createPersonEvent(person, "Burial", null);
        builder.createPersonEvent(person, "Burial", null);
        final OrderAnalyzerResult result = helper.analyze(person);
        assertTrue("Expected correct with burial, burial, burial",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithDeathDeathDeathMismatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        builder.createPersonEvent(person, "Death", null);
        builder.createPersonEvent(person, "Death", null);
        builder.createPersonEvent(person, "Death", null);
        final OrderAnalyzerResult result = helper.analyze(person);
        assertTrue("Expected correct with death, death, death",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithDeathWillDeathMismatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        builder.createPersonEvent(person, "Death", null);
        builder.createPersonEvent(person, "Will", null);
        builder.createPersonEvent(person, "Death", null);
        final OrderAnalyzerResult result = helper.analyze(person);
        assertTrue("Expected correct with death, will, death",
                result.isCorrect());
    }


    /** */
    @Test
    public void testDeathIsDeath() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Attribute event =
                builder.createPersonEvent(person1, "Death", null);
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue("Death is death", analyzer.isDeathEvent(event));
    }

    /** */
    @Test
    public void testBurialIsNotDeath() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Attribute event =
                builder.createPersonEvent(person1, "Burial", null);
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertFalse("Burial is not death", analyzer.isDeathEvent(event));
    }

    /** */
    @Test
    public void testDeathIsDeathRelated() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Attribute event =
                builder.createPersonEvent(person1, "Death", null);
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue("Death is death related",
                analyzer.isDeathRelatedEvent(event));
    }

    /** */
    @Test
    public void testBurialIsDeathRelated() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Attribute event =
                builder.createPersonEvent(person1, "Burial", null);
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue("Burial is death related",
                analyzer.isDeathRelatedEvent(event));
    }

    /** */
    @Test
    public void testCremationIsDeathRelated() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Attribute event =
                builder.createPersonEvent(person1, "Cremation", null);
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue("Cremation is death related",
                analyzer.isDeathRelatedEvent(event));
    }

    /** */
    @Test
    public void testUnveilingIsDeathRelated() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Attribute event =
                builder.createPersonEvent(person1, "Headstone unveiling", null);
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue("Unveiling is death related",
                analyzer.isDeathRelatedEvent(event));
    }


    /** */
    @Test
    public void testWillIsDeathRelated() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Attribute event =
                builder.createPersonEvent(person1, "Will", null);
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue("Will is death related",
                analyzer.isDeathRelatedEvent(event));
    }


    /** */
    @Test
    public void testDeathIsNotPostDeath() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Attribute event =
                builder.createPersonEvent(person1, "Death", null);
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertFalse("Death is not post death",
                analyzer.isPostDeathEvent(event));
    }

    /** */
    @Test
    public void testBurialIsPostDeath() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Attribute event =
                builder.createPersonEvent(person1, "Burial", null);
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue("Burial is post death",
                analyzer.isPostDeathEvent(event));
    }

    /** */
    @Test
    public void testCremationIsPostDeath() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Attribute event =
                builder.createPersonEvent(person1, "Cremation", null);
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue("Cremation is post death",
                analyzer.isPostDeathEvent(event));
    }

    /** */
    @Test
    public void testUnveilingIsPostDeath() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Attribute event =
                builder.createPersonEvent(person1, "Headstone unveiling", null);
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue("Unveiling is post death",
                analyzer.isPostDeathEvent(event));
    }

    /** */
    @Test
    public void testFuneralIsPostDeath() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Attribute event =
                builder.createPersonEvent(person1, "Funeral", null);
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue("Funeral is post death",
                analyzer.isPostDeathEvent(event));
    }

    /** */
    @Test
    public void testWillIsNotPostDeath() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Attribute event =
                builder.createPersonEvent(person1, "Will", null);
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertFalse("Will is not post death",
                analyzer.isPostDeathEvent(event));
    }

    /** */
    @Test
    public void testDeathIsNotUnordered() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Attribute event =
                builder.createPersonEvent(person1, "Death", null);
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertFalse("Death is not unorderd",
                analyzer.isUnorderedEvent(event));
    }

    /** */
    @Test
    public void testWillIsUnordered() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Attribute event =
                builder.createPersonEvent(person1, "Will", null);
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue("Will is unorderd",
                analyzer.isUnorderedEvent(event));
    }

}
