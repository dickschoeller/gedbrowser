package org.schoellerfamily.gedbrowser.datamodel.visitor.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
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
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.datamodel.SubmissionLink;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.SubmitterLink;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.datamodel.visitor.FamilyVisitor;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * @author Dick Schoeller
 */
final class FamilyVisitorTest {
    @Test
    void testUninitHusband() {
        final FamilyVisitor visitor = new FamilyVisitor();
        assertFalse(visitor.getHusband().isSet(), "Should be unset");
    }

    @Test
    void testUninitWife() {
        final FamilyVisitor visitor = new FamilyVisitor();
        assertFalse(visitor.getWife().isSet(), "Should be unset");
    }

    @Test
    void testUninitChildren() {
        final FamilyVisitor visitor = new FamilyVisitor();
        assertTrue(visitor.getChildren().isEmpty(), "Should be empty");
    }

    @Test
    void testUninitChildList() {
        final FamilyVisitor visitor = new FamilyVisitor();
        assertTrue(visitor.getChildList().isEmpty(), "Should be empty");
    }

    @Test
    void testNoImpactFromUnrelated() {
        final FamilyVisitor visitor = new FamilyVisitor();
        new Attribute().accept(visitor);
        new Date(null).accept(visitor);
        new FamC().accept(visitor);
        new FamS().accept(visitor);
        new Head().accept(visitor);
        new Link(null).accept(visitor);
        new Multimedia().accept(visitor);
        new Name().accept(visitor);
        new Note().accept(visitor);
        new NoteLink().accept(visitor);
        new Person().accept(visitor);
        new Place().accept(visitor);
        new Root().accept(visitor);
        new Source().accept(visitor);
        new SourceLink().accept(visitor);
        new Submission().accept(visitor);
        new SubmissionLink().accept(visitor);
        new Submitter().accept(visitor);
        new SubmitterLink().accept(visitor);
        new Trailer().accept(visitor);
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
        assertTrue(visitor.getChildren().isEmpty(), "Found unexpected content");
    }
}
