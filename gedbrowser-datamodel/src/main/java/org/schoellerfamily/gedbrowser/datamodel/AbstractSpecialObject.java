package org.schoellerfamily.gedbrowser.datamodel;

/**
 * @author Dick Schoeller
 */
public abstract class AbstractSpecialObject extends GedObject {
    /**
     * Default constructor.
     */
    public AbstractSpecialObject() {
        super();
    }

    /**
     * @param parent parent object of this attribute
     * @param string long version of type string
     */
    public AbstractSpecialObject(final GedObject parent, final String string) {
        super(parent, string);
    }
}
