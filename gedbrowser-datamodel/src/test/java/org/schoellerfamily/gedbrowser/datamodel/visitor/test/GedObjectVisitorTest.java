package org.schoellerfamily.gedbrowser.datamodel.visitor.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.datamodel.SubmissionLink;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.SubmitterLink;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.datamodel.Wife;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.ExcessiveImports")
final class GedObjectVisitorTest {
    private final GedObjectVisitorStub visitor = new GedObjectVisitorStub();

    /**
     * @author Dick Schoeller
     */
    @SuppressWarnings("PMD.TooManyMethods")
    private static final class GedObjectVisitorStub implements GedObjectVisitor {
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

        @Override
        public void visit(final Attribute attribute) {
            gob = attribute;
            type = "attribute";
        }

        @Override
        public void visit(final Child child) {
            gob = child;
            type = "child";
        }

        @Override
        public void visit(final Date date) {
            gob = date;
            type = "date";
        }

        @Override
        public void visit(final FamC famc) {
            gob = famc;
            type = "famc";
        }

        @Override
        public void visit(final Family family) {
            gob = family;
            type = "family";
        }

        @Override
        public void visit(final FamS fams) {
            gob = fams;
            type = "fams";
        }

        @Override
        public void visit(final Head head) {
            gob = head;
            type = "head";
        }

        @Override
        public void visit(final Husband husband) {
            gob = husband;
            type = "husband";
        }

        @Override
        public void visit(final Link link) {
            gob = link;
            type = "link";
        }

        @Override
        public void visit(final Multimedia multimedia) {
            gob = multimedia;
            type = "multimedia";
        }

        @Override
        public void visit(final Name name) {
            gob = name;
            type = "name";
        }

        @Override
        public void visit(final Note note) {
            gob = note;
            type = "note";
        }

        @Override
        public void visit(final NoteLink noteLink) {
            gob = noteLink;
            type = "noteLink";
        }

        @Override
        public void visit(final Person person) {
            gob = person;
            type = "person";
        }

        @Override
        public void visit(final Place place) {
            gob = place;
            type = "place";
        }

        @Override
        public void visit(final Root root) {
            gob = root;
            type = "root";
        }

        @Override
        public void visit(final Source source) {
            gob = source;
            type = "source";
        }

        @Override
        public void visit(final SourceLink sourceLink) {
            gob = sourceLink;
            type = "sourceLink";
        }

        @Override
        public void visit(final Submission submission) {
            gob = submission;
            type = "submission";
        }

        @Override
        public void visit(final SubmissionLink submissionLink) {
            gob = submissionLink;
            type = "submissionLink";
        }

        @Override
        public void visit(final Submitter submitter) {
            gob = submitter;
            type = "submitter";
        }

        @Override
        public void visit(final SubmitterLink submitterLink) {
            gob = submitterLink;
            type = "submitterLink";
        }

        @Override
        public void visit(final Trailer trailer) {
            gob = trailer;
            type = "trailer";
        }

        @Override
        public void visit(final Wife wife) {
            gob = wife;
            type = "wife";
        }

        @Override
        public void visit(final GedObject gedObject) {
            gob = gedObject;
            type = "unknown";
        }
    };

    @SuppressWarnings("checkstyle:nowhitespaceafter")
    static Stream<Arguments> params() {
        final GedObject generic = new GedObject() {
            @Override
            public void accept(final GedObjectVisitor v) {
                v.visit(this);
            }
        };
        return Arrays
            .stream(new Object[][] { { new Attribute(), "attribute" }, { new Child(), "child" },
                { new Date(null), "date" }, { new FamC(), "famc" }, { new Family(), "family" },
                { new FamS(), "fams" }, { new Head(), "head" }, { new Husband(), "husband" },
                { new Link(null), "link" }, { new Multimedia(), "multimedia" },
                { new Name(), "name" }, { new Note(), "note" }, { new NoteLink(), "noteLink" },
                { new Person(), "person" }, { new Place(), "place" }, { new Root(), "root" },
                { new Source(), "source" }, { new SourceLink(), "sourceLink" },
                { new Submission(), "submission" }, { new SubmissionLink(), "submissionLink" },
                { new Submitter(), "submitter" }, { new SubmitterLink(), "submitterLink" },
                { new Trailer(), "trailer" }, { new Wife(), "wife" }, { generic, "unknown" }, })
            .map(Arguments::of);
    }

    @ParameterizedTest
    @MethodSource("params")
    void testVisit(final GedObject visited, final String type) {
        visited.accept(visitor);
        checkSame(visited);
        assertEquals(type, visitor.getType(), "Should match type");
    }

    private void checkSame(final GedObject visited) {
        assertSame(visited, visitor.getGedObject(), "Should be same object");
    }
}
