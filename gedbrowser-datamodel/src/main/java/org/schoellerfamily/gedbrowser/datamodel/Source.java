package org.schoellerfamily.gedbrowser.datamodel;

/**
 * @author Dick Schoeller
 */
public final class Source extends AbstractSource {
    /**
     * @param parent parent object of this source
     */
    public Source(final GedObject parent) {
        super(parent);
    }

    /**
     * @param parent parent object of this source
     * @param xref cross reference to this source
     */
    public Source(final GedObject parent, final ObjectId xref) {
        super(parent, xref.getIdString());
    }
}
