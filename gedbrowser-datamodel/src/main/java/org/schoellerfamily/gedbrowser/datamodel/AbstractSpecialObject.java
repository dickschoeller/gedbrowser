package org.schoellerfamily.gedbrowser.datamodel;

/**
 * @author Dick Schoeller
 */
public abstract class AbstractSpecialObject extends GedObject {
    /**
     * Creates a new AbstractSpecialObject.
     *
     */
    protected AbstractSpecialObject() {
        super();
    }

    /**
     * Creates a new AbstractSpecialObject.
     *
     * @param parent the parent
     * @param string the string
     */
    protected AbstractSpecialObject(final GedObject parent, final String string) {
        super(parent, string);
    }
}
