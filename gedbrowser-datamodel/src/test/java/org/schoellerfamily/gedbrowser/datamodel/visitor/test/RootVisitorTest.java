package org.schoellerfamily.gedbrowser.datamodel.visitor.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.datamodel.SubmittorLink;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.datamodel.Wife;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;
import org.schoellerfamily.gedbrowser.datamodel.visitor.RootVisitor;

/**
 * @author Dick Schoeller
 */
public class RootVisitorTest {
    /** */
    @Test
    public void testBasicGetPersons() {
        final Root root = new Root();
        final GedObjectBuilder builder = new GedObjectBuilder(root);
        builder.createPerson1();
        builder.createPerson2();
        final RootVisitor visitor = new RootVisitor();
        root.accept(visitor);
        assertEquals("Expected 2 persons", 2, visitor.getPersons().size());
    }

    /** */
    @Test
    public void testNoPersonsFromUnrelated() {
        final RootVisitor visitor = new RootVisitor();
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
        new Name().accept(visitor);
        new Place().accept(visitor);
        new Source().accept(visitor);
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
            }
        };
        gob.accept(visitor);
        assertTrue("Found unexpected content", visitor.getPersons().isEmpty());
    }
}
