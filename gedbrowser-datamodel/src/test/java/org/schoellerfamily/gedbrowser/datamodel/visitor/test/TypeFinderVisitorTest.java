package org.schoellerfamily.gedbrowser.datamodel.visitor.test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
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
import org.schoellerfamily.gedbrowser.datamodel.visitor.TypeFinderVisitor;

/**
 * @author Dick Schoeller
 */
public class TypeFinderVisitorTest {
    /** */
    private transient TypeFinderVisitor visitor;

    /** */
    @Before
    public void init() {
        visitor = new TypeFinderVisitor();
    }

    /** */
    @Test
    public void testInit() {
        assertNull("Expected same object", visitor.getAttribute());
    }

    /** */
    @Test
    public void testAttribute() {
        final Attribute gob = new Attribute();
        gob.accept(visitor);
        assertSame("Expected same object", gob, visitor.getAttribute());
    }

    /** */
    @Test
    public void testChild() {
        final Child gob = new Child();
        gob.accept(visitor);
        assertSame("Expected same object", gob, visitor.getChild());
    }

    /** */
    @Test
    public void testDate() {
        final Date gob = new Date(null);
        gob.accept(visitor);
        assertSame("Expected same object", gob, visitor.getDate());
    }

    /** */
    @Test
    public void testFamC() {
        final FamC gob = new FamC();
        gob.accept(visitor);
        assertSame("Expected same object", gob, visitor.getFamC());
    }

    /** */
    @Test
    public void testFamily() {
        final Family gob = new Family();
        gob.accept(visitor);
        assertSame("Expected same object", gob, visitor.getFamily());
    }

    /** */
    @Test
    public void testFamS() {
        final FamS gob = new FamS();
        gob.accept(visitor);
        assertSame("Expected same object", gob, visitor.getFamS());
    }

    /** */
    @Test
    public void testHead() {
        final Head gob = new Head();
        gob.accept(visitor);
        assertSame("Expected same object", gob, visitor.getHead());
    }

    /** */
    @Test
    public void testHusband() {
        final Husband gob = new Husband();
        gob.accept(visitor);
        assertSame("Expected same object", gob, visitor.getHusband());
    }

    /** */
    @Test
    public void testLink() {
        final Link gob = new Link(null);
        gob.accept(visitor);
        assertSame("Expected same object", gob, visitor.getLink());
    }

    /** */
    @Test
    public void testMultimedia() {
        final Multimedia gob = new Multimedia();
        gob.accept(visitor);
        assertSame("Expected same object", gob, visitor.getMultimedia());
    }

    /** */
    @Test
    public void testName() {
        final Name gob = new Name();
        gob.accept(visitor);
        assertSame("Expected same object", gob, visitor.getName());
    }

    /** */
    @Test
    public void testPerson() {
        final Person gob = new Person();
        gob.accept(visitor);
        assertSame("Expected same object", gob, visitor.getPerson());
    }

    /** */
    @Test
    public void testPlace() {
        final Place gob = new Place();
        gob.accept(visitor);
        assertSame("Expected same object", gob, visitor.getPlace());
    }

    /** */
    @Test
    public void testRoot() {
        final Root gob = new Root();
        gob.accept(visitor);
        assertSame("Expected same object", gob, visitor.getRoot());
    }

    /** */
    @Test
    public void testSource() {
        final Source gob = new Source();
        gob.accept(visitor);
        assertSame("Expected same object", gob, visitor.getSource());
    }

    /** */
    @Test
    public void testSourceLink() {
        final SourceLink gob = new SourceLink();
        gob.accept(visitor);
        assertSame("Expected same object", gob, visitor.getSourceLink());
    }

    /** */
    @Test
    public void testSubmittor() {
        final Submittor gob = new Submittor();
        gob.accept(visitor);
        assertSame("Expected same object", gob, visitor.getSubmittor());
    }

    /** */
    @Test
    public void testSubmittorLink() {
        final SubmittorLink gob = new SubmittorLink();
        gob.accept(visitor);
        assertSame("Expected same object", gob, visitor.getSubmittorLink());
    }

    /** */
    @Test
    public void testTrailer() {
        final Trailer gob = new Trailer();
        gob.accept(visitor);
        assertSame("Expected same object", gob, visitor.getTrailer());
    }

    /** */
    @Test
    public void testWife() {
        final Wife gob = new Wife();
        gob.accept(visitor);
        assertSame("Expected same object", gob, visitor.getWife());
    }

    /** */
    @Test
    public void testGedObject() {
        final GedObject gob = new GedObject() {
            /**
             * {@inheritDoc}
             */
            @Override
            public void accept(final GedObjectVisitor v) {
                v.visit(this);
            } };
        gob.accept(visitor);
        assertSame("Expected same object", gob, visitor.getGedObject());
    }
}
