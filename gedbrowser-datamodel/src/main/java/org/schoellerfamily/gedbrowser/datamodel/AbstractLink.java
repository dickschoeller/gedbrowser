package org.schoellerfamily.gedbrowser.datamodel;

/**
 * Represents abstract link in the domain model.
 *
 * @author Richard Schoeller
 */
public abstract class AbstractLink extends GedObject {
    /**
     * ID string of the object that this link is coming from.
     */
    private String fromString = "";
    /**
     * ID string of the object that this link is going to.
     */
    private String toString = "";

    /**
     * Creates a new AbstractLink.
     */
    protected AbstractLink() {
        super();
    }

    /**
     * Creates a new AbstractLink.
     *
     * @param parent the parent
     */
    protected AbstractLink(final GedObject parent) {
        super(parent);
        initLink(new ObjectId(""));
    }

    /**
     * Creates a new AbstractLink.
     *
     * @param parent the parent
     * @param string the string
     */
    protected AbstractLink(final GedObject parent, final String string) {
        super(parent, string);
        initLink(new ObjectId(""));
    }

    /**
     * Creates a new AbstractLink.
     *
     * @param parent the parent
     * @param string the string
     */
    protected AbstractLink(final GedObject parent, final String string,
            final ObjectId tail) {
        super(parent, string);
        initLink(tail);
    }

    /**
     * Executes init link.
     *
     * @param xref the xref
     */
    public final void initLink(final ObjectId xref) {
        if (getParent() == null) {
            setFromString("");
        } else {
            setFromString(getParent().getString());
        }

        if (xref == null) {
            setToString("");
        } else {
            setToString(xref.getIdString().replace("@", ""));
        }
    }

    /**
     * Gets the from string.
     *
     * @return the from string
     */
    public final String getFromString() {
        return fromString;
    }

    /**
     * Sets the from string.
     *
     * @param fromString the from string
     */
    public final void setFromString(final String fromString) {
        if (fromString == null) {
            this.fromString = "";
        } else {
            this.fromString = fromString;
        }
    }

    /**
     * Gets the to string.
     *
     * @return the to string
     */
    public final String getToString() {
        return toString;
    }

    /**
     * Sets the to string.
     *
     * @param toString the to string
     */
    public final void setToString(final String toString) {
        if (toString == null) {
            this.toString = "";
        } else {
            this.toString = toString;
        }
    }
}
