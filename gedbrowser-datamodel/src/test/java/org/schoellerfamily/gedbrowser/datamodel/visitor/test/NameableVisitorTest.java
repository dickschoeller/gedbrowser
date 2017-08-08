package org.schoellerfamily.gedbrowser.datamodel.visitor.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.Link;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.datamodel.Note;
import org.schoellerfamily.gedbrowser.datamodel.NoteLink;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.datamodel.SubmissionLink;
import org.schoellerfamily.gedbrowser.datamodel.SubmitterLink;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.datamodel.Wife;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;
import org.schoellerfamily.gedbrowser.datamodel.visitor.NameableVisitor;

/**
 * @author Dick Schoeller
 */
public final class NameableVisitorTest {
    /** */
    @Test
    public void testUninit() {
        final NameableVisitor visitor = new NameableVisitor();
        assertEquals("Found unexpected content",
                "?, ?", visitor.getIndexName());
    }

    /** */
    @Test
    public void testNoImpactFromUnrelated() {
        final NameableVisitor visitor = new NameableVisitor();
        new Attribute().accept(visitor);
        new Child().accept(visitor);
        new Date(null).accept(visitor);
        new FamC().accept(visitor);
        new Family().accept(visitor);
        new FamS().accept(visitor);
        new Head().accept(visitor);
        new Husband().accept(visitor);
        new Link(null).accept(visitor);
        new Multimedia().accept(visitor);
        new Note().accept(visitor);
        new NoteLink().accept(visitor);
        new Place().accept(visitor);
        new Root().accept(visitor);
        new Source().accept(visitor);
        new SourceLink().accept(visitor);
        new Submission().accept(visitor);
        new SubmissionLink().accept(visitor);
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
            } };
        gob.accept(visitor);
        assertEquals("Found unexpected content",
                "?, ?", visitor.getIndexName());
    }
}
