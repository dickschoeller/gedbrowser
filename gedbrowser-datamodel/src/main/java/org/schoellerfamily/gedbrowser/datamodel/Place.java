package org.schoellerfamily.gedbrowser.datamodel;

/**
 * @author Dick Schoeller
 */
public final class Place extends AbstractAttribute {
    /**
     * @param parent parent object of this child
     */
    public Place(final GedObject parent) {
        super(parent);
    }

    /**
     * @param parent parent object of this child
     * @param string place name
     */
    public Place(final GedObject parent, final String string) {
        super(parent, string);
    }
}
