package org.schoellerfamily.gedbrowser.datamodel.visitor;

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



/**
 * Visitor for the {@link org.schoellerfamily.gedbrowser.datamodel.GedObject}
 *
 * @author Richard Schoeller
 */
@SuppressWarnings("PMD.TooManyMethods")
public interface GedObjectVisitor {
    /**
     * Visit an attribute.
     *
     * @param attribute the attribute to visit
     */
    default void visit(final Attribute attribute) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a child.
     *
     * @param child the child to visit
     */
    default void visit(final Child child) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a date.
     *
     * @param date the date to visit
     */
    default void visit(final Date date) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a link to a family that a person is a child of.
     *
     * @param famc the link to visit
     */
    default void visit(final FamC famc) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a family.
     *
     * @param family the family to visit
     */
    default void visit(final Family family) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a link to a family that a person is a spouse of.
     *
     * @param fams the link to visit
     */
    default void visit(final FamS fams) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a header.
     *
     * @param head the header to visit
     */
    default void visit(final Head head) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a link to the husband of a family.
     *
     * @param husband the link to visit
     */
    default void visit(final Husband husband) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a link to another object.
     *
     * @param link the link to visit
     */
    default void visit(final Link link) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a multimedia object.
     *
     * @param multimedia the multimedia object to visit
     */
    default void visit(final Multimedia multimedia) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a name.
     *
     * @param name the name to visit
     */
    default void visit(final Name name) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a note.
     *
     * @param note the note to visit
     */
    default void visit(final Note note) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a note.
     *
     * @param noteLink the noteLink to visit
     */
    default void visit(final NoteLink noteLink) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a person.
     *
     * @param person the person to visit
     */
    default void visit(final Person person) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a place.
     *
     * @param place the place to visit
     */
    default void visit(final Place place) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a root.
     *
     * @param root the root to visit
     */
    default void visit(final Root root) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a source.
     *
     * @param source the source to visit
     */
    default void visit(final Source source) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a link to a source.
     *
     * @param sourceLink the link to visit
     */
    default void visit(final SourceLink sourceLink) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a submission.
     *
     * @param submission the submission to visit
     */
    default void visit(final Submission submission) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a submission link.
     *
     * @param submissionLink the submission to visit
     */
    default void visit(final SubmissionLink submissionLink) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a submitter.
     *
     * @param submitter the submitter to visit
     */
    default void visit(final Submitter submitter) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a link to a submitter.
     *
     * @param submitterLink the link to visit
     */
    default void visit(final SubmitterLink submitterLink) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a trailer.
     *
     * @param trailer the trailer to visit
     */
    default void visit(final Trailer trailer) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a link to the wife of a family.
     *
     * @param wife the link to visit
     */
    default void visit(final Wife wife) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a gedObject that isn't one of the above.
     *
     * @param gedObject the object to visit
     */
    default void visit(final GedObject gedObject) {
        // If not overridden, does not contribute to algorithm.
    }
}
