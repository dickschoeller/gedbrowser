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
 * Visits ged object elements and applies visitor logic.
 *
 * @author Richard Schoeller
 */
@SuppressWarnings("PMD.TooManyMethods")
public interface GedObjectVisitor {
    /**
     * Visit an attribute.
     * Override the default method if this type contributes to your algorithm.
     *
     * @param attribute the attribute to visit
     */
    default void visit(final Attribute attribute) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a child.
     * Override the default method if this type contributes to your algorithm.
     *
     * @param child the child to visit
     */
    default void visit(final Child child) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a date.
     * Override the default method if this type contributes to your algorithm.
     *
     * @param date the date to visit
     */
    default void visit(final Date date) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a link to a family that a person is a child of.
     * Override the default method if this type contributes to your algorithm.
     *
     * @param famc the link to visit
     */
    default void visit(final FamC famc) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a family.
     * Override the default method if this type contributes to your algorithm.
     *
     * @param family the family to visit
     */
    default void visit(final Family family) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a link to a family that a person is a spouse of.
     * Override the default method if this type contributes to your algorithm.
     *
     * @param fams the link to visit
     */
    default void visit(final FamS fams) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a header.
     * Override the default method if this type contributes to your algorithm.
     *
     * @param head the header to visit
     */
    default void visit(final Head head) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a link to the husband of a family.
     * Override the default method if this type contributes to your algorithm.
     *
     * @param husband the link to visit
     */
    default void visit(final Husband husband) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a link to another object.
     * Override the default method if this type contributes to your algorithm.
     *
     * @param link the link to visit
     */
    default void visit(final Link link) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a multimedia object.
     * Override the default method if this type contributes to your algorithm.
     *
     * @param multimedia the multimedia object to visit
     */
    default void visit(final Multimedia multimedia) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a name.
     * Override the default method if this type contributes to your algorithm.
     *
     * @param name the name to visit
     */
    default void visit(final Name name) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a note.
     * Override the default method if this type contributes to your algorithm.
     *
     * @param note the note to visit
     */
    default void visit(final Note note) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a note.
     * Override the default method if this type contributes to your algorithm.
     *
     * @param noteLink the noteLink to visit
     */
    default void visit(final NoteLink noteLink) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a person.
     * Override the default method if this type contributes to your algorithm.
     *
     * @param person the person to visit
     */
    default void visit(final Person person) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a place.
     * Override the default method if this type contributes to your algorithm.
     *
     * @param place the place to visit
     */
    default void visit(final Place place) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a root.
     * Override the default method if this type contributes to your algorithm.
     *
     * @param root the root to visit
     */
    default void visit(final Root root) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a source.
     * Override the default method if this type contributes to your algorithm.
     *
     * @param source the source to visit
     */
    default void visit(final Source source) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a link to a source.
     * Override the default method if this type contributes to your algorithm.
     *
     * @param sourceLink the link to visit
     */
    default void visit(final SourceLink sourceLink) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a submission.
     * Override the default method if this type contributes to your algorithm.
     *
     * @param submission the submission to visit
     */
    default void visit(final Submission submission) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a submission link.
     * Override the default method if this type contributes to your algorithm.
     *
     * @param submissionLink the submission to visit
     */
    default void visit(final SubmissionLink submissionLink) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a submitter.
     * Override the default method if this type contributes to your algorithm.
     *
     * @param submitter the submitter to visit
     */
    default void visit(final Submitter submitter) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a link to a submitter.
     * Override the default method if this type contributes to your algorithm.
     *
     * @param submitterLink the link to visit
     */
    default void visit(final SubmitterLink submitterLink) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a trailer.
     * Override the default method if this type contributes to your algorithm.
     *
     * @param trailer the trailer to visit
     */
    default void visit(final Trailer trailer) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a link to the wife of a family.
     * Override the default method if this type contributes to your algorithm.
     *
     * @param wife the link to visit
     */
    default void visit(final Wife wife) {
        // If not overridden, does not contribute to algorithm.
    }

    /**
     * Visit a gedObject that isn't one of the above.
     * Override the default method if this type contributes to your algorithm.
     *
     * @param gedObject the object to visit
     */
    default void visit(final GedObject gedObject) {
        // If not overridden, does not contribute to algorithm.
    }
}
