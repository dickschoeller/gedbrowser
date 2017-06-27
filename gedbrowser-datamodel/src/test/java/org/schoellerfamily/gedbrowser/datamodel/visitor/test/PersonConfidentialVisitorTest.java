package org.schoellerfamily.gedbrowser.datamodel.visitor.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.datamodel.visitor.PersonConfidentialVisitor;

/**
 * @author Dick Schoeller
 */
public class PersonConfidentialVisitorTest {
    /** */
    private final GedObjectBuilder builder = new GedObjectBuilder();

    /** */
    @Test
    public void testNotConfidential() {
        final Person person =
                builder.createPerson("I1", "J. Random/Schoeller/");
        final PersonConfidentialVisitor visitor =
                new PersonConfidentialVisitor();
        person.accept(visitor);
        assertFalse("Should not be confidential", visitor.isConfidential());
    }

    /** */
    @Test
    public void testIsConfidential() {
        final Person person =
                builder.createPerson("I1", "J. Random/Schoeller/");
        builder.createAttribute(person, "Restriction", "confidential");
        final PersonConfidentialVisitor visitor =
                new PersonConfidentialVisitor();
        person.accept(visitor);
        assertTrue("Should be confidential", visitor.isConfidential());
    }
}
