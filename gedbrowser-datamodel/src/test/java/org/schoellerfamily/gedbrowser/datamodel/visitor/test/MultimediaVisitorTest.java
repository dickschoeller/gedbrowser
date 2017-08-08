package org.schoellerfamily.gedbrowser.datamodel.visitor.test;

import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.Link;
import org.schoellerfamily.gedbrowser.datamodel.Name;
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
import org.schoellerfamily.gedbrowser.datamodel.Wife;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;
import org.schoellerfamily.gedbrowser.datamodel.visitor.MultimediaVisitor;

/**
 * @author Dick Schoeller
 */
public final class MultimediaVisitorTest {
    /** */
    @Test
    public void testNoFilePathFromUnrelated() {
        final MultimediaVisitor visitor = new MultimediaVisitor();
        new Child().accept(visitor);
        new Date(null).accept(visitor);
        new FamC().accept(visitor);
        new Family().accept(visitor);
        new FamS().accept(visitor);
        new Head().accept(visitor);
        new Husband().accept(visitor);
        new Link(null).accept(visitor);
        new Name().accept(visitor);
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
        assertNull("Found unexpected content",
                visitor.getFilePath());
    }

    /** */
    @Test
    public void testNoFormatFromUnrelated() {
        final MultimediaVisitor visitor = new MultimediaVisitor();
        new Child().accept(visitor);
        new Date(null).accept(visitor);
        new FamC().accept(visitor);
        new Family().accept(visitor);
        new FamS().accept(visitor);
        new Head().accept(visitor);
        new Husband().accept(visitor);
        new Link(null).accept(visitor);
        new Name().accept(visitor);
        new Person().accept(visitor);
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
            } };
        gob.accept(visitor);
        assertNull("Found unexpected content",
                visitor.getFormat());
    }

    /** */
    @Test
    public void testNoTitleFromUnrelated() {
        final MultimediaVisitor visitor = new MultimediaVisitor();
        new Child().accept(visitor);
        new Date(null).accept(visitor);
        new FamC().accept(visitor);
        new Family().accept(visitor);
        new FamS().accept(visitor);
        new Head().accept(visitor);
        new Husband().accept(visitor);
        new Link(null).accept(visitor);
        new Name().accept(visitor);
        new Person().accept(visitor);
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
            } };
        gob.accept(visitor);
        assertNull("Found unexpected content",
                visitor.getTitle());
    }
}
