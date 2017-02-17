package org.schoellerfamily.gedbrowser.datamodel;

/**
 * @author Dick Schoeller
 */
public abstract class AbstractSource extends GedObject {
    /**
     * Default constructor.
     */
    public AbstractSource() {
        super();
    }

    /**
     * @param parent parent object of this attribute
     * @param string long version of type string
     */
    public AbstractSource(final GedObject parent, final String string) {
        super(parent, string);
    }
}
