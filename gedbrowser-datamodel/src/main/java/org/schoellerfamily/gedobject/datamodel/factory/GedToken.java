package org.schoellerfamily.gedobject.datamodel.factory;

/**
 * Associates a GEDCOM token with a full string and a factory for GedObject
 * creation.
 *
 * @author Dick Schoeller
 */
/* default */ class GedToken {
    /** */
    private final transient String fullString;
    /** */
    private final transient AbstractGedObjectFactory factory;

    /**
     * Token processing for GEDCOM lines.
     *
     * @param fullstring the input string.
     * @param factory the factory for GedObject creation.
     */
    /* default */ GedToken(final String fullstring,
            final AbstractGedObjectFactory factory) {
        this.fullString = fullstring;
        this.factory = factory;
    }

    /**
     * @return the full string of the line.
     */
    public String getFullString() {
        return fullString;
    }

    /**
     * @return the GedObject factory.
     */
    public AbstractGedObjectFactory getFactory() {
        return factory;
    }
}
