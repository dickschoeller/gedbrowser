package org.schoellerfamily.gedbrowser.analytics.order.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.analytics.order.OrderAnalyzer;
import org.schoellerfamily.gedbrowser.analytics.order.OrderAnalyzerResult;
import org.schoellerfamily.gedbrowser.analytics.test.TestConfiguration;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
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
public final class OrderAnalyzerDeathTest implements AnalyzerTest {
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
    public void testPersonWithOnlyDeathMatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Death");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue("Expected correct with only death event",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithDeathAfterNonDeathMatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Education");
        builder.createPersonEvent(person, "Death");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue("Expected correct with death events are after others",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithNonDeathAfterDeathMismatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Death");
        builder.createPersonEvent(person, "Education");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertFalse("Expected incorrect with death events are before others",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithWillAfterDeathMatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Death");
        builder.createPersonEvent(person, "Will");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue("Expected correct with death before will",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithWillBeforeDeathMatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Will");
        builder.createPersonEvent(person, "Death");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue("Expected correct with will before death",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithWillEducDeathMatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Will");
        builder.createPersonEvent(person, "Education");
        builder.createPersonEvent(person, "Death");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue("Expected correct with will, education, death",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithDeathFuneralBurialMatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Death");
        builder.createPersonEvent(person, "Funeral");
        builder.createPersonEvent(person, "Burial");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue("Expected correct with death, funeral, burial",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithDeathBurialMatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Death");
        builder.createPersonEvent(person, "Burial");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue("Expected correct with death, burial",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithFuneralBurialDeathMismatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Funeral");
        builder.createPersonEvent(person, "Burial");
        builder.createPersonEvent(person, "Death");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertFalse("Expected incorrect with funeral, burial, death",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithDeathBurialDeathMismatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Death");
        builder.createPersonEvent(person, "Burial");
        builder.createPersonEvent(person, "Death");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertFalse("Expected incorrect with death, burial, death",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithBurialBurialBurialMatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Burial");
        builder.createPersonEvent(person, "Burial");
        builder.createPersonEvent(person, "Burial");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue("Expected correct with burial, burial, burial",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithDeathDeathDeathMismatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Death");
        builder.createPersonEvent(person, "Death");
        builder.createPersonEvent(person, "Death");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue("Expected correct with death, death, death",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithDeathWillDeathMismatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Death");
        builder.createPersonEvent(person, "Will");
        builder.createPersonEvent(person, "Death");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue("Expected correct with death, will, death",
                result.isCorrect());
    }


    /** */
    @Test
    public void testDeathIsDeath() {
        final Person person1 = createJRandom();
        final Attribute event =
                builder.createPersonEvent(person1, "Death");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue("Death is death", analyzer.isDeathEvent(event));
    }

    /** */
    @Test
    public void testBurialIsNotDeath() {
        final Person person1 = createJRandom();
        final Attribute event =
                builder.createPersonEvent(person1, "Burial");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertFalse("Burial is not death", analyzer.isDeathEvent(event));
    }

    /** */
    @Test
    public void testDeathIsDeathRelated() {
        final Person person1 = createJRandom();
        final Attribute event =
                builder.createPersonEvent(person1, "Death");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue("Death is death related",
                analyzer.isDeathRelatedEvent(event));
    }

    /** */
    @Test
    public void testBurialIsDeathRelated() {
        final Person person1 = createJRandom();
        final Attribute event =
                builder.createPersonEvent(person1, "Burial");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue("Burial is death related",
                analyzer.isDeathRelatedEvent(event));
    }

    /** */
    @Test
    public void testCremationIsDeathRelated() {
        final Person person1 = createJRandom();
        final Attribute event =
                builder.createPersonEvent(person1, "Cremation");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue("Cremation is death related",
                analyzer.isDeathRelatedEvent(event));
    }

    /** */
    @Test
    public void testUnveilingIsDeathRelated() {
        final Person person1 = createJRandom();
        final Attribute event = builder.createPersonEvent(
                person1, "Headstone unveiling");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue("Unveiling is death related",
                analyzer.isDeathRelatedEvent(event));
    }

    /** */
    @Test
    public void testWillIsDeathRelated() {
        final Person person1 = createJRandom();
        final Attribute event =
                builder.createPersonEvent(person1, "Will");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue("Will is death related",
                analyzer.isDeathRelatedEvent(event));
    }

    /** */
    @Test
    public void testDeathIsNotPostDeath() {
        final Person person1 = createJRandom();
        final Attribute event =
                builder.createPersonEvent(person1, "Death");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertFalse("Death is not post death",
                analyzer.isPostDeathEvent(event));
    }

    /** */
    @Test
    public void testBurialIsPostDeath() {
        final Person person1 = createJRandom();
        final Attribute event =
                builder.createPersonEvent(person1, "Burial");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue("Burial is post death",
                analyzer.isPostDeathEvent(event));
    }

    /** */
    @Test
    public void testCremationIsPostDeath() {
        final Person person1 = createJRandom();
        final Attribute event =
                builder.createPersonEvent(person1, "Cremation");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue("Cremation is post death",
                analyzer.isPostDeathEvent(event));
    }

    /** */
    @Test
    public void testUnveilingIsPostDeath() {
        final Person person1 = createJRandom();
        final Attribute event = builder.createPersonEvent(
                person1, "Headstone unveiling");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue("Unveiling is post death",
                analyzer.isPostDeathEvent(event));
    }

    /** */
    @Test
    public void testFuneralIsPostDeath() {
        final Person person1 = createJRandom();
        final Attribute event =
                builder.createPersonEvent(person1, "Funeral");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue("Funeral is post death",
                analyzer.isPostDeathEvent(event));
    }

    /** */
    @Test
    public void testWillIsNotPostDeath() {
        final Person person1 = createJRandom();
        final Attribute event = builder.createPersonEvent(
                person1, "Will");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertFalse("Will is not post death",
                analyzer.isPostDeathEvent(event));
    }

    /** */
    @Test
    public void testDeathIsNotUnordered() {
        final Person person1 = createJRandom();
        final Attribute event = builder.createPersonEvent(
                person1, "Death");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertFalse("Death is ordered", analyzer.isUnorderedEvent(event));
    }

    /** */
    @Test
    public void testWillIsUnordered() {
        final Person person1 = createJRandom();
        final Attribute event = builder.createPersonEvent(
                person1, "Will");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue("Will is unordered", analyzer.isUnorderedEvent(event));
    }
}
