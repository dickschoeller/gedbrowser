package org.schoellerfamily.gedbrowser.writer.creator;

import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.writer.GedWriterLine;

/**
 * @author Dick Schoeller
 */
public interface IdentifiedRecordLineVisitor extends GedObjectLineVisitor {
    /**
     * {@inheritDoc}
     */
    @Override
    default void visit(final Family family) {
        createIdentifiedRecordLine(family, "FAM");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default void visit(final Person person) {
        createIdentifiedRecordLine(person, "INDI");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default void visit(final Source source) {
        createIdentifiedRecordLine(source, "SOUR");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default void visit(final Submission submission) {
        createIdentifiedRecordLine(submission, "SUBN");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default void visit(final Submitter submitter) {
        createIdentifiedRecordLine(submitter, "SUBM");
    }

    /**
     * Create line representing the base line of a record.
     *
     * @param gedObject the ged object
     * @param tag its tag
     */
    default void createIdentifiedRecordLine(final GedObject gedObject,
            final String tag) {
        final GedWriterLine line = new GedWriterLine(getLevel(), gedObject,
                getLevel() + " @" + gedObject.getString() + "@ " + tag);
        getLines().add(line);
        handleChildren(gedObject);
    }
}
