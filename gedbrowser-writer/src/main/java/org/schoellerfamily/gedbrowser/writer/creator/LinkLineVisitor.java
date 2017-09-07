package org.schoellerfamily.gedbrowser.writer.creator;

import org.schoellerfamily.gedbrowser.datamodel.AbstractLink;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.Link;
import org.schoellerfamily.gedbrowser.datamodel.NoteLink;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.SubmissionLink;
import org.schoellerfamily.gedbrowser.datamodel.SubmitterLink;
import org.schoellerfamily.gedbrowser.datamodel.Wife;
import org.schoellerfamily.gedbrowser.writer.GedWriterLine;

/**
 * @author Dick Schoeller
 */
public interface LinkLineVisitor extends GedObjectLineVisitor {

    /**
     * {@inheritDoc}
     */
    @Override
    default void visit(final Child child) {
        createLinkLine(child);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default void visit(final FamC famc) {
        createLinkLine(famc);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default void visit(final FamS fams) {
        createLinkLine(fams);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default void visit(final Husband husband) {
        createLinkLine(husband);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default void visit(final Link link) {
        createLinkLine(link);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default void visit(final NoteLink noteLink) {
        createLinkLine(noteLink);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default void visit(final SourceLink sourceLink) {
        createLinkLine(sourceLink);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default void visit(final SubmissionLink submissionLink) {
        createLinkLine(submissionLink);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default void visit(final SubmitterLink submitterLink) {
        createLinkLine(submitterLink);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default void visit(final Wife wife) {
        createLinkLine(wife);
    }

    /**
     * Create a line representing a link to another object.
     *
     * @param link the object defining in the link
     */
    default void createLinkLine(final AbstractLink link) {
        final GedWriterLine line = new GedWriterLine(getLevel(), link,
                getLevel() + " " + mapTag(link.getString())
                + " @" + link.getToString() + "@");
        getLines().add(line);
        handleChildren(link);
    }
}
