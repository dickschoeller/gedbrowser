package org.schoellerfamily.gedbrowser.datamodel.visitor.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
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
import org.schoellerfamily.gedbrowser.datamodel.visitor.GetDateVisitor;

/**
 * @author Dick Schoeller
 */
final class GetDateVisitorTest {
    @Test
    void testNoPersonsFromUnrelated() {
        final GetDateVisitor visitor = new GetDateVisitor();
        new Child().accept(visitor);
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
            }
        };
        gob.accept(visitor);
        assertEquals("", visitor.getDate(), "Found unexpected content");
    }

    @Test
    void testVisitPersonFindsFirstDate() {
        final Person person = new Person();
        person.addAttribute(buildTypedDateAttribute(person, "Birth", "1 JAN 1900"));
        person.addAttribute(buildTypedDateAttribute(person, "Death", "1 JAN 2000"));

        final GetDateVisitor visitor = new GetDateVisitor();
        person.accept(visitor);

        assertEquals("1 JAN 1900", visitor.getDate());
        assertEquals("1900", visitor.getYear());
        assertEquals("19000101", visitor.getSortDate());
    }

    @Test
    void testVisitPersonFiltersByType() {
        final Person person = new Person();
        person.addAttribute(buildTypedDateAttribute(person, "Birth", "1 JAN 1900"));
        person.addAttribute(buildTypedDateAttribute(person, "Death", "31 DEC 2001"));

        final GetDateVisitor visitor = new GetDateVisitor("Death");
        person.accept(visitor);

        assertEquals("31 DEC 2001", visitor.getDate());
        assertEquals("2001", visitor.getYear());
        assertEquals("20011231", visitor.getSortDate());
    }

    @Test
    void testVisitAttributeSkipsMismatchedType() {
        final Attribute birth = buildTypedDateAttribute(null, "Birth", "1 JAN 1900");
        final GetDateVisitor visitor = new GetDateVisitor("Death");

        birth.accept(visitor);

        assertEquals("", visitor.getDate());
        assertEquals("", visitor.getYear());
        assertEquals("", visitor.getSortDate());
    }

    @Test
    void testVisitDateAppendsTimeFromNestedAttribute() {
        final Date date = new Date(null, "2 FEB 2020");
        final Attribute time = new Attribute(date, "Time", "10:11:12");
        date.addAttribute(time);

        final GetDateVisitor visitor = new GetDateVisitor();
        date.accept(visitor);

        assertEquals("2 FEB 2020 10:11:12", visitor.getDate());
        assertEquals("2020", visitor.getYear());
        assertEquals("20200202", visitor.getSortDate());
    }

    @Test
    void testVisitDateWithoutTimeKeepsDateOnly() {
        final Date date = new Date(null, "3 MAR 2021");
        date.addAttribute(new Attribute(date, "Place", "Berlin"));

        final GetDateVisitor visitor = new GetDateVisitor();
        date.accept(visitor);

        assertEquals("3 MAR 2021", visitor.getDate());
        assertEquals("2021", visitor.getYear());
        assertEquals("20210303", visitor.getSortDate());
    }

    private Attribute buildTypedDateAttribute(
            final GedObject parent, final String type, final String dateString) {
        final Attribute attr = new Attribute(parent, type, "");
        final Date date = new Date(attr, dateString);
        attr.addAttribute(date);
        return attr;
    }
}
