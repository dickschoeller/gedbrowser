package org.schoellerfamily.gedbrowser.datamodel.visitor.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.datamodel.visitor.PersonConfidentialVisitor;

/**
 * Contains tests for person confidential visitor.
 *
 * @author Richard Schoeller
 */
class PersonConfidentialVisitorTest {
    /** */
    private final GedObjectBuilder builder = new GedObjectBuilder();

    @Test
    void testNotConfidential() {
        final Person person =
                builder.createPerson("I1", "J. Random/Schoeller/");
        final PersonConfidentialVisitor visitor =
                new PersonConfidentialVisitor();
        person.accept(visitor);
        assertFalse(visitor.isConfidential(), "Should not be confidential");
    }

    @Test
    void testIsConfidential() {
        final Person person =
                builder.createPerson("I1", "J. Random/Schoeller/");
        builder.createAttribute(person, "Restriction", "confidential");
        final PersonConfidentialVisitor visitor =
                new PersonConfidentialVisitor();
        person.accept(visitor);
        assertTrue(visitor.isConfidential(), "Should be confidential");
    }
}
