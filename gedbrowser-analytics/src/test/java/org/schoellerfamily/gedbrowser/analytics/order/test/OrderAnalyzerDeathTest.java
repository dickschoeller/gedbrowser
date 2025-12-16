package org.schoellerfamily.gedbrowser.analytics.order.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
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
        assertTrue(result.isCorrect(), "Expected correct with only death event");
    }

    /** */
    @Test
    public void testPersonWithDeathAfterNonDeathMatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Education");
        builder.createPersonEvent(person, "Death");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue(result.isCorrect(), "Expected correct with death events are after others");
    }

    /** */
    @Test
    public void testPersonWithNonDeathAfterDeathMismatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Death");
        builder.createPersonEvent(person, "Education");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertFalse(result.isCorrect(), "Expected incorrect with death events are before others");
    }

    /** */
    @Test
    public void testPersonWithWillAfterDeathMatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Death");
        builder.createPersonEvent(person, "Will");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue(result.isCorrect(), "Expected correct with death before will");
    }

    /** */
    @Test
    public void testPersonWithWillBeforeDeathMatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Will");
        builder.createPersonEvent(person, "Death");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue(result.isCorrect(), "Expected correct with will before death");
    }

    /** */
    @Test
    public void testPersonWithWillEducDeathMatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Will");
        builder.createPersonEvent(person, "Education");
        builder.createPersonEvent(person, "Death");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue(result.isCorrect(), "Expected correct with will, education, death");
    }

    /** */
    @Test
    public void testPersonWithDeathFuneralBurialMatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Death");
        builder.createPersonEvent(person, "Funeral");
        builder.createPersonEvent(person, "Burial");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue(result.isCorrect(), "Expected correct with death, funeral, burial");
    }

    /** */
    @Test
    public void testPersonWithDeathBurialMatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Death");
        builder.createPersonEvent(person, "Burial");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue(result.isCorrect(), "Expected correct with death, burial");
    }

    /** */
    @Test
    public void testPersonWithFuneralBurialDeathMismatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Funeral");
        builder.createPersonEvent(person, "Burial");
        builder.createPersonEvent(person, "Death");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertFalse(result.isCorrect(), "Expected incorrect with funeral, burial, death");
    }

    /** */
    @Test
    public void testPersonWithDeathBurialDeathMismatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Death");
        builder.createPersonEvent(person, "Burial");
        builder.createPersonEvent(person, "Death");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertFalse(result.isCorrect(), "Expected incorrect with death, burial, death");
    }

    /** */
    @Test
    public void testPersonWithBurialBurialBurialMatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Burial");
        builder.createPersonEvent(person, "Burial");
        builder.createPersonEvent(person, "Burial");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue(result.isCorrect(), "Expected correct with burial, burial, burial");
    }

    /** */
    @Test
    public void testPersonWithDeathDeathDeathMismatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Death");
        builder.createPersonEvent(person, "Death");
        builder.createPersonEvent(person, "Death");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue(result.isCorrect(), "Expected correct with death, death, death");
    }

    /** */
    @Test
    public void testPersonWithDeathWillDeathMismatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Death");
        builder.createPersonEvent(person, "Will");
        builder.createPersonEvent(person, "Death");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue(result.isCorrect(), "Expected correct with death, will, death");
    }


    /** */
    @Test
    public void testDeathIsDeath() {
        final Person person1 = createJRandom();
        final Attribute event =
                builder.createPersonEvent(person1, "Death");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue(analyzer.isDeathEvent(event), "Death is death");
    }

    /** */
    @Test
    public void testBurialIsNotDeath() {
        final Person person1 = createJRandom();
        final Attribute event =
                builder.createPersonEvent(person1, "Burial");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertFalse(analyzer.isDeathEvent(event), "Burial is not death");
    }

    /** */
    @Test
    public void testDeathIsDeathRelated() {
        final Person person1 = createJRandom();
        final Attribute event =
                builder.createPersonEvent(person1, "Death");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue(analyzer.isDeathRelatedEvent(event), "Death is death related");
    }

    /** */
    @Test
    public void testBurialIsDeathRelated() {
        final Person person1 = createJRandom();
        final Attribute event =
                builder.createPersonEvent(person1, "Burial");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue(analyzer.isDeathRelatedEvent(event), "Burial is death related");
    }

    /** */
    @Test
    public void testCremationIsDeathRelated() {
        final Person person1 = createJRandom();
        final Attribute event =
                builder.createPersonEvent(person1, "Cremation");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue(analyzer.isDeathRelatedEvent(event), "Cremation is death related");
    }

    /** */
    @Test
    public void testUnveilingIsDeathRelated() {
        final Person person1 = createJRandom();
        final Attribute event = builder.createPersonEvent(
                person1, "Headstone unveiling");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue(analyzer.isDeathRelatedEvent(event), "Unveiling is death related");
    }

    /** */
    @Test
    public void testWillIsDeathRelated() {
        final Person person1 = createJRandom();
        final Attribute event =
                builder.createPersonEvent(person1, "Will");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue(analyzer.isDeathRelatedEvent(event), "Will is death related");
    }

    /** */
    @Test
    public void testDeathIsNotPostDeath() {
        final Person person1 = createJRandom();
        final Attribute event =
                builder.createPersonEvent(person1, "Death");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertFalse(analyzer.isPostDeathEvent(event), "Death is not post death");
    }

    /** */
    @Test
    public void testBurialIsPostDeath() {
        final Person person1 = createJRandom();
        final Attribute event =
                builder.createPersonEvent(person1, "Burial");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue(analyzer.isPostDeathEvent(event), "Burial is post death");
    }

    /** */
    @Test
    public void testCremationIsPostDeath() {
        final Person person1 = createJRandom();
        final Attribute event =
                builder.createPersonEvent(person1, "Cremation");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue(analyzer.isPostDeathEvent(event), "Cremation is post death");
    }

    /** */
    @Test
    public void testUnveilingIsPostDeath() {
        final Person person1 = createJRandom();
        final Attribute event = builder.createPersonEvent(
                person1, "Headstone unveiling");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue(analyzer.isPostDeathEvent(event), "Unveiling is post death");
    }

    /** */
    @Test
    public void testFuneralIsPostDeath() {
        final Person person1 = createJRandom();
        final Attribute event =
                builder.createPersonEvent(person1, "Funeral");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue(analyzer.isPostDeathEvent(event), "Funeral is post death");
    }

    /** */
    @Test
    public void testWillIsNotPostDeath() {
        final Person person1 = createJRandom();
        final Attribute event = builder.createPersonEvent(
                person1, "Will");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertFalse(analyzer.isPostDeathEvent(event), "Will is not post death");
    }

    /** */
    @Test
    public void testDeathIsNotUnordered() {
        final Person person1 = createJRandom();
        final Attribute event = builder.createPersonEvent(
                person1, "Death");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertFalse(analyzer.isUnorderedEvent(event), "Death is ordered");
    }

    /** */
    @Test
    public void testWillIsUnordered() {
        final Person person1 = createJRandom();
        final Attribute event = builder.createPersonEvent(
                person1, "Will");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue(analyzer.isUnorderedEvent(event), "Will is unordered");
    }
}
