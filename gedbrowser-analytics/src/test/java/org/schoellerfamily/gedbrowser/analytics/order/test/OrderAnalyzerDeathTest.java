package org.schoellerfamily.gedbrowser.analytics.order.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
final class OrderAnalyzerDeathTest implements AnalyzerTest {
    /** */
    @Autowired
    private OrderAnalyzerTestWrapper wrapper;
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

    /** */
    @Test
    void testPersonWithOnlyDeathMatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Death");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue(result.isCorrect(), "Expected correct with only death event");
    }

    /** */
    @Test
    void testPersonWithDeathAfterNonDeathMatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Education");
        builder.createPersonEvent(person, "Death");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue(result.isCorrect(), "Expected correct with death events are after others");
    }

    /** */
    @Test
    void testPersonWithNonDeathAfterDeathMismatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Death");
        builder.createPersonEvent(person, "Education");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertFalse(result.isCorrect(), "Expected incorrect with death events are before others");
    }

    /** */
    @Test
    void testPersonWithWillAfterDeathMatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Death");
        builder.createPersonEvent(person, "Will");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue(result.isCorrect(), "Expected correct with death before will");
    }

    /** */
    @Test
    void testPersonWithWillBeforeDeathMatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Will");
        builder.createPersonEvent(person, "Death");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue(result.isCorrect(), "Expected correct with will before death");
    }

    /** */
    @Test
    void testPersonWithWillEducDeathMatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Will");
        builder.createPersonEvent(person, "Education");
        builder.createPersonEvent(person, "Death");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue(result.isCorrect(), "Expected correct with will, education, death");
    }

    /** */
    @Test
    void testPersonWithDeathFuneralBurialMatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Death");
        builder.createPersonEvent(person, "Funeral");
        builder.createPersonEvent(person, "Burial");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue(result.isCorrect(), "Expected correct with death, funeral, burial");
    }

    /** */
    @Test
    void testPersonWithDeathBurialMatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Death");
        builder.createPersonEvent(person, "Burial");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue(result.isCorrect(), "Expected correct with death, burial");
    }

    /** */
    @Test
    void testPersonWithFuneralBurialDeathMismatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Funeral");
        builder.createPersonEvent(person, "Burial");
        builder.createPersonEvent(person, "Death");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertFalse(result.isCorrect(), "Expected incorrect with funeral, burial, death");
    }

    /** */
    @Test
    void testPersonWithDeathBurialDeathMismatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Death");
        builder.createPersonEvent(person, "Burial");
        builder.createPersonEvent(person, "Death");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertFalse(result.isCorrect(), "Expected incorrect with death, burial, death");
    }

    /** */
    @Test
    void testPersonWithBurialBurialBurialMatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Burial");
        builder.createPersonEvent(person, "Burial");
        builder.createPersonEvent(person, "Burial");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue(result.isCorrect(), "Expected correct with burial, burial, burial");
    }

    /** */
    @Test
    void testPersonWithDeathDeathDeathMismatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Death");
        builder.createPersonEvent(person, "Death");
        builder.createPersonEvent(person, "Death");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue(result.isCorrect(), "Expected correct with death, death, death");
    }

    /** */
    @Test
    void testPersonWithDeathWillDeathMismatch() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Death");
        builder.createPersonEvent(person, "Will");
        builder.createPersonEvent(person, "Death");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue(result.isCorrect(), "Expected correct with death, will, death");
    }


    /** */
    @Test
    void testDeathIsDeath() {
        final Person person1 = createJRandom();
        final Attribute event =
                builder.createPersonEvent(person1, "Death");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue(analyzer.isDeathEvent(event), "Death is death");
    }

    /** */
    @Test
    void testBurialIsNotDeath() {
        final Person person1 = createJRandom();
        final Attribute event =
                builder.createPersonEvent(person1, "Burial");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertFalse(analyzer.isDeathEvent(event), "Burial is not death");
    }

    /** */
    @ParameterizedTest
    @ValueSource(strings = {
        "Death",
        "Burial",
        "Cremation",
        "Headstone unveiling",
        "Will"
    })
    void testIsDeathRelated(final String eventType) {
        final Person person1 = createJRandom();
        final Attribute event = "Headstone unveiling".equals(eventType)
            ? builder.createPersonEvent(person1, eventType)
            : builder.createPersonEvent(person1, eventType);
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue(analyzer.isDeathRelatedEvent(event), eventType + " is death related");
    }

    /** */
    @Test
    void testDeathIsNotPostDeath() {
        final Person person1 = createJRandom();
        final Attribute event =
                builder.createPersonEvent(person1, "Death");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertFalse(analyzer.isPostDeathEvent(event), "Death is not post death");
    }

    /** */
    @Test
    void testBurialIsPostDeath() {
        final Person person1 = createJRandom();
        final Attribute event =
                builder.createPersonEvent(person1, "Burial");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue(analyzer.isPostDeathEvent(event), "Burial is post death");
    }

    /** */
    @Test
    void testCremationIsPostDeath() {
        final Person person1 = createJRandom();
        final Attribute event =
                builder.createPersonEvent(person1, "Cremation");
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue(analyzer.isPostDeathEvent(event), "Cremation is post death");
    }

    /** */
    @ParameterizedTest
    @ValueSource(strings = {
        "Burial",
        "Cremation",
        "Headstone unveiling",
        "Funeral"
    })
    void testIsPostDeath(final String eventType) {
        final Person person1 = createJRandom();
        final Attribute event = builder.createPersonEvent(person1, eventType);
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertTrue(analyzer.isPostDeathEvent(event), eventType + " is post death");
    }

    /** */
    @ParameterizedTest
    @ValueSource(strings = {
        "Death",
        "Will"
    })
    void testIsNotPostDeath(final String eventType) {
        final Person person1 = createJRandom();
        final Attribute event = builder.createPersonEvent(person1, eventType);
        final OrderAnalyzer analyzer = new OrderAnalyzer(person1);
        assertFalse(analyzer.isPostDeathEvent(event), eventType + " is not post death");
    }
}
