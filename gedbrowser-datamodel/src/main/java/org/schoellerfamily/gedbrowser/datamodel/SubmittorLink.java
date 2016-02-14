package org.schoellerfamily.gedbrowser.datamodel;

/**
 * @author Dick Schoeller
 */
public final class SubmittorLink extends AbstractLink {
    /**
     * @param parent parent object of this link
     */
    public SubmittorLink(final GedObject parent) {
        super(parent);
    }

    /**
     * @param parent parent object of this link
     * @param tag long version of type string
     */
    public SubmittorLink(final GedObject parent, final String tag) {
        super(parent, tag);
    }

    /**
     * @param parent parent object of this submittor link
     * @param tag long version of type string
     * @param xref the reference to a submittor object
     */
    public SubmittorLink(final GedObject parent, final String tag,
            final ObjectId xref) {
        super(parent, tag, xref);
    }
}
