package org.schoellerfamily.gedbrowser.datamodel;

/**
 * @author Dick Schoeller
 */
public final class Trailer extends AbstractSpecialObject {
    /**
     * @param parent parent object of this object
     */
    public Trailer(final GedObject parent) {
        super(parent);
    }

    /**
     * @param parent parent object of this object
     * @param string long version of type string
     */
    public Trailer(final GedObject parent, final String string) {
        super(parent, string);
    }
}
