package org.schoellerfamily.gedbrowser.datamodel;

/**
 * @author Dick Schoeller
 */
public final class SourceLink extends AbstractLink {
    /**
     * @param parent parent object of this link
     */
    public SourceLink(final GedObject parent) {
        super(parent);
    }

    /**
     * @param parent parent object of this link
     * @param tag long version of type string
     */
    public SourceLink(final GedObject parent, final String tag) {
        super(parent, tag);
    }

    /**
     * @param parent parent object of this source link
     * @param tag long version of type string
     * @param xref the reference to a source object
     */
    public SourceLink(final GedObject parent, final String tag,
            final ObjectId xref) {
        super(parent, tag, xref);
    }
}
