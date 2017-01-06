package org.schoellerfamily.gedbrowser.datamodel;

/**
 * @author Dick Schoeller
 */
public final class Attribute extends AbstractAttribute {
    /** */
    private String tail;

    /**
     * Null object constructor.
     */
    public Attribute() {
        super();
        this.tail = "";
    }

    /**
     * @param parent
     *            parent object of this attribute
     */
    public Attribute(final GedObject parent) {
        super(parent);
        this.tail = "";
        this.setAppender(new AttributeAppender(this));
    }

    /**
     * @param parent
     *            parent object of this attribute
     * @param string
     *            long version of type string
     */
    public Attribute(final GedObject parent, final String string) {
        super(parent, string);
        tail = "";
        this.setAppender(new AttributeAppender(this));
    }

    /**
     * @param parent
     *            parent object of this attribute
     * @param string
     *            long version of type string
     * @param tail
     *            additional data
     */
    public Attribute(final GedObject parent, final String string,
            final String tail) {
        super(parent, string);
        this.tail = tail;
        this.setAppender(new AttributeAppender(this));
    }

    /**
     * @return tail string
     */
    public String getTail() {
        if (tail == null) {
            return "";
        } else {
            return tail;
        }
    }

    /**
     * @param tail
     *            additional data
     */
    public void setTail(final String tail) {
        if (tail == null) {
            this.tail = "";
        } else {
            this.tail = tail;
        }
    }

    /**
     * If this is a birth attribute, return the date as a string. Otherwise
     * return an empty string.
     *
     * @return the birth date string.
     */
    public String getBirthDate() {
        if (getString().equals("Birth")) {
            return getDate();
        } else {
            return "";
        }
    }

    /**
     * If this is a birth attribute, return the date as a string. Otherwise
     * return an empty string.
     *
     * @return the birth date string.
     */
    public String getBirthYear() {
        if (getString().equals("Birth")) {
            return getYear();
        } else {
            return "";
        }
    }

    /**
     * If this is a birth attribute, return the date as a string. Otherwise
     * return an empty string.
     *
     * @return the birth date string.
     */
    public String getSortDate() {
        if (getString().equals("Birth")) {
            return getSortableDate();
        } else {
            return "";
        }
    }

    /**
     * If this is a death attribute, return the date as a string. Otherwise
     * return an empty string.
     *
     * @return the death date string.
     */
    public String getDeathDate() {
        if (getString().equals("Death")) {
            return getDate();
        } else {
            return "";
        }
    }

    /**
     * If this is a death attribute, return the date as a string. Otherwise
     * return an empty string.
     *
     * @return the death date string.
     */
    public String getDeathYear() {
        if (getString().equals("Death")) {
            return getYear();
        } else {
            return "";
        }
    }

    /**
     * Get the first date attribute found as a string. If none are found return
     * an empty string.
     *
     * @return the date string.
     */
    public String getDate() {
        for (final GedObject gedObject : getAttributes()) {
            if (!(gedObject instanceof Date)) {
                continue;
            }
            final Date attr = (Date) gedObject;
            if (!attr.getDate().isEmpty()) {
                return attr.getDate();
            }
        }
        return "";
    }

    /**
     * Get the first date attribute found as a string. If none are found return
     * an empty string.
     *
     * @return the date string.
     */
    public String getYear() {
        for (final GedObject gedObject : getAttributes()) {
            if (!(gedObject instanceof Date)) {
                continue;
            }
            final Date attr = (Date) gedObject;
            if (!attr.getDate().isEmpty()) {
                return attr.getYear();
            }
        }
        return "";
    }

    /**
     * Get the first date attribute found as a string. If none are found return
     * an empty string.
     *
     * @return the date string.
     */
    public String getSortableDate() {
        for (final GedObject gedObject : getAttributes()) {
            if (!(gedObject instanceof Date)) {
                continue;
            }
            final Date attr = (Date) gedObject;
            if (!attr.getDate().isEmpty()) {
                return attr.getSortDate();
            }
        }
        return "";
    }
}
