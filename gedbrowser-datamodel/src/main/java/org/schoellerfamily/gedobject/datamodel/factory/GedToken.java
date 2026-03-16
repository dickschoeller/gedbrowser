package org.schoellerfamily.gedobject.datamodel.factory;

/**
 * Associates a GEDCOM token with a full string and a factory for GedObject
 * creation.
 *
 * @author Richard Schoeller
 */
/* default */ class GedToken {
    /** */
    private final transient String fullString;
    /** */
    private final transient AbstractGedObjectFactory factory;

    /* default */ GedToken(final String fullstring,
            final AbstractGedObjectFactory factory) {
        this.fullString = fullstring;
        this.factory = factory;
    }

    /**
     * Gets the full string.
     *
     * @return the full string
     */
    public String getFullString() {
        return fullString;
    }

    /**
     * Gets the factory.
     *
     * @return the factory
     */
    public AbstractGedObjectFactory getFactory() {
        return factory;
    }
}
