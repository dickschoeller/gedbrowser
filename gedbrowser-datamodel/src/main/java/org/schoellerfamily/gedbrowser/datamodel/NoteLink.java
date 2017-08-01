package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * @author Dick Schoeller
 */
public class NoteLink extends AbstractLink {
    /**
     * Default constructor.
     */
    public NoteLink() {
        super();
    }

    /**
     * @param parent parent object of this link
     * @param tag long version of type string
     */
    public NoteLink(final GedObject parent, final String tag) {
        super(parent, tag);
    }

    /**
     * @param parent parent object of this source link
     * @param tag long version of type string
     * @param xref the reference to a source object
     */
    public NoteLink(final GedObject parent, final String tag,
            final ObjectId xref) {
        super(parent, tag, xref);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final GedObjectVisitor visitor) {
        visitor.visit(this);
    }
}
