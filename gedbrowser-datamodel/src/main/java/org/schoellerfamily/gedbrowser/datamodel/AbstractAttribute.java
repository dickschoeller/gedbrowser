package org.schoellerfamily.gedbrowser.datamodel;


/**
 * @author Dick Schoeller
 */
public abstract class AbstractAttribute extends GedObject {
    /**
     * Null object constructor.
     */
    public AbstractAttribute() {
        super();
    }

    /**
     * @param parent parent object of this attribute
     */
    public AbstractAttribute(final GedObject parent) {
        super(parent);
    }

    /**
     * @param parent parent object of this attribute
     * @param string long version of type string
     */
    public AbstractAttribute(final GedObject parent, final String string) {
        super(parent, string);
    }
}
