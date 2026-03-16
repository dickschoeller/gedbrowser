package org.schoellerfamily.gedbrowser.datamodel;

/**
 * Represents abstract source in the domain model.
 *
 * @author Richard Schoeller
 */
public abstract class AbstractSource extends GedObject {
    /**
     * Creates a new AbstractSource.
     */
    protected AbstractSource() {
        super();
    }

    /**
     * Creates a new AbstractSource.
     *
     * @param parent the parent
     * @param string the string
     */
    protected AbstractSource(final GedObject parent, final String string) {
        super(parent, string);
    }
}
