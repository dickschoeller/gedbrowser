package org.schoellerfamily.gedbrowser.datamodel;

/**
 * @author Dick Schoeller
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
     * Default constructor.
     */
    public AbstractLink() {
        super();
    }

    /**
     * @param parent
     *            parent object of this link
     */
    public AbstractLink(final GedObject parent) {
        super(parent);
        initLink(new ObjectId(""));
    }

    /**
     * @param parent
     *            parent object of this link
     * @param string
     *            long version of type string
     */
    public AbstractLink(final GedObject parent, final String string) {
        super(parent, string);
        initLink(new ObjectId(""));
    }

    /**
     * @param parent
     *            parent object of this link
     * @param string
     *            long version of type string
     * @param tail
     *            ID string of referred object
     */
    public AbstractLink(final GedObject parent, final String string,
            final ObjectId tail) {
        super(parent, string);
        initLink(tail);
    }

    /**
     * @param xref
     *            the reference ID of the object this link points to. May
     *            contain bracketing '@' characters.
     */
    protected final void initLink(final ObjectId xref) {
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
     * @return the ID of the object this links from.
     */
    public final String getFromString() {
        return fromString;
    }

    /**
     * @param fromString
     *            the ID of the object this links from.
     */
    public final void setFromString(final String fromString) {
        if (fromString == null) {
            this.fromString = "";
        } else {
            this.fromString = fromString;
        }
    }

    /**
     * @return the ID of the object this links to.
     */
    public final String getToString() {
        return toString;
    }

    /**
     * @param toString
     *            the ID of the object this links to.
     */
    public final void setToString(final String toString) {
        if (toString == null) {
            this.toString = "";
        } else {
            this.toString = toString;
        }
    }
}
