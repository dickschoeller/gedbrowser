package org.schoellerfamily.gedbrowser.datamodel.visitor.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
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

/**
 * @author Dick Schoeller
 */
@RunWith(Parameterized.class)
@SuppressWarnings("PMD.ExcessiveImports")
public class GedObjectVisitorTest {
    /** */
    private final GedObjectVisitorStub visitor = new GedObjectVisitorStub();

    /** */
    private final GedObject visited;

    /** */
    private final String type;

    /**
     * @author Dick Schoeller
     */
    @SuppressWarnings("PMD.TooManyMethods")
    private static class GedObjectVisitorStub implements GedObjectVisitor {
        /** */
        private GedObject gob;
        /** */
        private String type;

        /**
         * @return the visited ged object
         */
        public GedObject getGedObject() {
            return gob;
        }

        /**
         * @return the type of the visited ged object
         */
        public String getType() {
            return type;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visit(final Attribute attribute) {
            gob = attribute;
            type = "attribute";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visit(final Child child) {
            gob = child;
            type = "child";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visit(final Date date) {
            gob = date;
            type = "date";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visit(final FamC famc) {
            gob = famc;
            type = "famc";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visit(final Family family) {
            gob = family;
            type = "family";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visit(final FamS fams) {
            gob = fams;
            type = "fams";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visit(final Head head) {
            gob = head;
            type = "head";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visit(final Husband husband) {
            gob = husband;
            type = "husband";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visit(final Link link) {
            gob = link;
            type = "link";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visit(final Multimedia multimedia) {
            gob = multimedia;
            type = "multimedia";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visit(final Name name) {
            gob = name;
            type = "name";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visit(final Note note) {
            gob = note;
            type = "note";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visit(final NoteLink noteLink) {
            gob = noteLink;
            type = "noteLink";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visit(final Person person) {
            gob = person;
            type = "person";
        }

        @Override
        /**
         * {@inheritDoc}
         */
        public void visit(final Place place) {
            gob = place;
            type = "place";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visit(final Root root) {
            gob = root;
            type = "root";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visit(final Source source) {
            gob = source;
            type = "source";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visit(final SourceLink sourceLink) {
            gob = sourceLink;
            type = "sourceLink";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visit(final Submittor submittor) {
            gob = submittor;
            type = "submittor";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visit(final SubmittorLink submittorLink) {
            gob = submittorLink;
            type = "submittorLink";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visit(final Trailer trailer) {
            gob = trailer;
            type = "trailer";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visit(final Wife wife) {
            gob = wife;
            type = "wife";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visit(final GedObject gedObject) {
            gob = gedObject;
            type = "unknown";
        }
    };

    /**
     * @param visited the visited object
     * @param type the expected type name
     */
    public GedObjectVisitorTest(final GedObject visited, final String type) {
        this.visited = visited;
        this.type = type;
    }

    /**
     * @return collection of parameter arrays
     */
    @Parameters
    public static Collection<Object[]> params() {
        final GedObject generic = new GedObject() {
            /**
             * {@inheritDoc}
             */
            @Override
            public void accept(final GedObjectVisitor v) {
                v.visit(this);
            }
        };
        return Arrays.asList(new Object[][] {
            {new Attribute(), "attribute"},
            {new Child(), "child"},
            {new Date(null), "date"},
            {new FamC(), "famc"},
            {new Family(), "family"},
            {new FamS(), "fams"},
            {new Head(), "head"},
            {new Husband(), "husband"},
            {new Link(null), "link"},
            {new Multimedia(), "multimedia"},
            {new Name(), "name"},
            {new Note(), "note"},
            {new NoteLink(), "noteLink"},
            {new Person(), "person"},
            {new Place(), "place"},
            {new Root(), "root"},
            {new Source(), "source"},
            {new SourceLink(), "sourceLink"},
            {new Submittor(), "submittor"},
            {new SubmittorLink(), "submittorLink"},
            {new Trailer(), "trailer"},
            {new Wife(), "wife"},
            {generic, "unknown"},
        });
    }

    /**
     * Basic test.
     */
    @Test
    public void testVisit() {
        visited.accept(visitor);
        checkSame();
        assertEquals("Should match type", type, visitor.getType());
    }

    /**
     * Check that the visitor saw what we thought it saw.
     */
    private void checkSame() {
        assertSame("Should be same object", visited, visitor.getGedObject());
    }
}
