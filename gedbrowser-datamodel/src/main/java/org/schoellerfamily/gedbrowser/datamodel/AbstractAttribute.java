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
     * @param parent parent object of this attribute
     */
    protected AbstractAttribute(final GedObject parent) {
        super(parent);
    }

    /**
     * @param parent parent object of this attribute
     * @param string long version of type string
     */
    protected AbstractAttribute(final GedObject parent, final String string) {
        super(parent, string);
    }
}
