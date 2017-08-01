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
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.Note;
import org.schoellerfamily.gedbrowser.datamodel.NoteLink;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.datamodel.SubmittorLink;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.datamodel.Wife;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;
import org.schoellerfamily.gedbrowser.datamodel.visitor.SourceVisitor;

/**
 * @author Dick Schoeller
 */
public final class SourceVisitorTest {
    /** */
    @Test
    public void testGetTitleString() {
        final Root root = new Root("Root");
        final Source source = new Source(root, new ObjectId("S1"));
        root.insert(source);
        final Attribute title = new Attribute(source, "Title", "Title 9");
        source.insert(title);
        final SourceVisitor visitor = new SourceVisitor();
        source.accept(visitor);
        assertEquals("Found wrong title", "Title 9", visitor.getTitleString());
    }

    /** */
    @Test
    public void testGetNonTitleAttribute() {
        final Root root = new Root("Root");
        final Source source = new Source(root, new ObjectId("S1"));
        root.insert(source);
        final Attribute title = new Attribute(source, "TITLE", "Title 9");
        source.insert(title);
        final SourceVisitor visitor = new SourceVisitor();
        source.accept(visitor);
        assertEquals("Found wrong title", "S1", visitor.getTitleString());
    }

    /** */
    @Test
    public void testNoTitleString() {
        final Root root = new Root("Root");
        final Source source = new Source(root, new ObjectId("S1"));
        root.insert(source);
        final SourceVisitor visitor = new SourceVisitor();
        source.accept(visitor);
        assertEquals("Found wrong title", "S1", visitor.getTitleString());
    }

    /** */
    @Test
    public void testNoSource() {
        final SourceVisitor visitor = new SourceVisitor();
        assertEquals("Found wrong title", "", visitor.getTitleString());
    }

    /** */
    @Test
    public void testNoImpactFromUnrelated() {
        final SourceVisitor visitor = new SourceVisitor();
        new Child().accept(visitor);
        new Date(null).accept(visitor);
        new FamC().accept(visitor);
        new Family().accept(visitor);
        new FamS().accept(visitor);
        new Head().accept(visitor);
        new Husband().accept(visitor);
        new Link(null).accept(visitor);
        new Multimedia().accept(visitor);
        new Name().accept(visitor);
        new Note().accept(visitor);
        new NoteLink().accept(visitor);
        new Person().accept(visitor);
        new Place().accept(visitor);
        new Root().accept(visitor);
        new SourceLink().accept(visitor);
        new Submittor().accept(visitor);
        new SubmittorLink().accept(visitor);
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
        assertEquals("Found wrong title", "", visitor.getTitleString());
    }
}
