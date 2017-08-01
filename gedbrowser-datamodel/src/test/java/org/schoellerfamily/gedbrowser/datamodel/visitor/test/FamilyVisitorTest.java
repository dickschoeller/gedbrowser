package org.schoellerfamily.gedbrowser.datamodel.visitor.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
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
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.datamodel.SubmittorLink;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.datamodel.visitor.FamilyVisitor;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * @author Dick Schoeller
 */
public final class FamilyVisitorTest {
    /** */
    @Test
    public void testUninitHusband() {
        final FamilyVisitor visitor = new FamilyVisitor();
        assertFalse("Should be unset", visitor.getHusband().isSet());
    }

    /** */
    @Test
    public void testUninitWife() {
        final FamilyVisitor visitor = new FamilyVisitor();
        assertFalse("Should be unset", visitor.getWife().isSet());
    }

    /** */
    @Test
    public void testUninitChildren() {
        final FamilyVisitor visitor = new FamilyVisitor();
        assertTrue("Should be empty", visitor.getChildren().isEmpty());
    }

    /** */
    @Test
    public void testUninitChildList() {
        final FamilyVisitor visitor = new FamilyVisitor();
        assertTrue("Should be empty", visitor.getChildList().isEmpty());
    }

    /** */
    @Test
    public void testNoImpactFromUnrelated() {
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
        new Submittor().accept(visitor);
        new SubmittorLink().accept(visitor);
        new Trailer().accept(visitor);
        final GedObject gob = new GedObject() {
            /**
             * {@inheritDoc}
             */
            @Override
            public void accept(final GedObjectVisitor visitor) {
                visitor.visit(this);
            } };
        gob.accept(visitor);
        assertTrue("Found unexpected content",
                visitor.getChildren().isEmpty());
    }
}
