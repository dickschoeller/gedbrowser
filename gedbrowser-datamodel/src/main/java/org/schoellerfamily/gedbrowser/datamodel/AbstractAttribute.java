package org.schoellerfamily.gedbrowser.datamodel;


/**
 * @author Dick Schoeller
 */
public abstract class AbstractAttribute extends GedObject {
    /**
     * Null object constructor.
     */
    protected AbstractAttribute() {
        super();
    }

    /**
     * Creates a new AbstractAttribute.
     *
     * @param parent the parent
     */
    protected AbstractAttribute(final GedObject parent) {
        super(parent);
    }

    /**
     * Creates a new AbstractAttribute.
     *
     * @param parent the parent
     * @param string the string
     */
    protected AbstractAttribute(final GedObject parent, final String string) {
        super(parent, string);
    }
}
