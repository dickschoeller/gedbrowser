package org.schoellerfamily.gedbrowser.datamodel;

/**
 * Represents abstract special object in the domain model.
 *
 * @author Richard Schoeller
 */
public abstract class AbstractSpecialObject extends GedObject {
    /**
     * Creates a new AbstractSpecialObject.
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
