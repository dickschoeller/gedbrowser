package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * Represents note link in the domain model.
 *
 * @author Richard Schoeller
 */
public final class NoteLink extends AbstractLink {
    /**
     * Creates a new NoteLink.
     */
    public NoteLink() {
        super();
    }

    /**
     * Creates a new NoteLink.
     *
     * @param parent the parent
     * @param tag the tag
     */
    public NoteLink(final GedObject parent, final String tag) {
        super(parent, tag);
    }

    /**
     * Creates a new NoteLink.
     *
     * @param parent the parent
     * @param tag the tag
     */
    public NoteLink(final GedObject parent, final String tag,
            final ObjectId xref) {
        super(parent, tag, xref);
    }

    /**
     * Executes accept.
     *
     * @param visitor the visitor
     */
    @Override
    public void accept(final GedObjectVisitor visitor) {
        visitor.visit(this);
    }
}
