package org.schoellerfamily.gedbrowser.datamodel.visitor.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.Link;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.Note;
import org.schoellerfamily.gedbrowser.datamodel.NoteLink;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.SubmitterLink;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.datamodel.Wife;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;
import org.schoellerfamily.gedbrowser.datamodel.visitor.PersonVisitor;

/**
 * @author Dick Schoeller
 */
public final class PersonVisitorTest {
    /** */
    private final GedObjectBuilder builder = new GedObjectBuilder();

    /** */
    @Test
    public void testEmptyGetFamily() {
        final Person person =
                builder.createPerson("I1", "J. Random/Schoeller/");
        final PersonVisitor visitor = new PersonVisitor();
        person.accept(visitor);
        assertFalse("Should be no family", visitor.getFamily().isSet());
    }

    /** */
    @Test
    public void testGetFamily() {
        final Person person =
                builder.createPerson("I1", "J. Random/Schoeller/");
        final Family family = builder.createFamily("F1");
        builder.addChildToFamily(family, person);
        final PersonVisitor visitor = new PersonVisitor();
        person.accept(visitor);
        assertSame("Unmatched family", family, visitor.getFamily());
    }

    /** */
    @Test
    public void testGetFirstFamily() {
        final Person person =
                builder.createPerson("I1", "J. Random/Schoeller/");
        final Family family = builder.createFamily("F1");
        final Family family2 = builder.createFamily("F2");
        builder.addChildToFamily(family, person);
        builder.addChildToFamily(family2, person);
        final PersonVisitor visitor = new PersonVisitor();
        person.accept(visitor);
        assertSame("Unmatched family", family, visitor.getFamily());
    }

    /** */
    @Test
    public void testGetFirstFamiliesC() {
        final Person person =
                builder.createPerson("I1", "J. Random/Schoeller/");
        final Family family = builder.createFamily("F1");
        final Family family2 = builder.createFamily("F2");
        builder.addChildToFamily(family, person);
        builder.addChildToFamily(family2, person);
        final PersonVisitor visitor = new PersonVisitor();
        person.accept(visitor);
        final List<Family> familiesC = visitor.getFamiliesC();
        assertTrue("Expected both families to be present",
                familiesC.contains(family) && familiesC.contains(family2));
    }

    /** */
    @Test
    public void testNoFamilyFromUnrelated() {
        final PersonVisitor visitor = new PersonVisitor();
        new Child().accept(visitor);
        new Date(null).accept(visitor);
        new Family().accept(visitor);
        new Head().accept(visitor);
        new Husband().accept(visitor);
        new Link(null).accept(visitor);
        new Multimedia().accept(visitor);
        new Name().accept(visitor);
        new Note().accept(visitor);
        new NoteLink().accept(visitor);
        new Place().accept(visitor);
        new Root().accept(visitor);
        new Source().accept(visitor);
        new SourceLink().accept(visitor);
        new Submitter().accept(visitor);
        new SubmitterLink().accept(visitor);
        new Trailer().accept(visitor);
        new Wife().accept(visitor);
        final GedObject gob = new GedObject() {
            /**
             * {@inheritDoc}
             */
            @Override
            public void accept(final GedObjectVisitor visitor) {
                visitor.visit(this);
            }
        };
        gob.accept(visitor);
        assertFalse("Found unexpected content", visitor.getFamily().isSet());
    }
}
