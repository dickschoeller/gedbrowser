package org.schoellerfamily.gedbrowser.datamodel;

/**
 * @author Dick Schoeller
 */
public final class Multimedia extends AbstractAttribute {
    /** */
    private String tail;

    /**
     * Default constructor.
     */
    public Multimedia() {
        super();
        this.tail = "";
        this.setAppender(new MultimediaAppender(this));
    }

    /**
     * @param parent parent object of this attribute
     * @param string long version of type string
     */
    public Multimedia(final GedObject parent, final String string) {
        super(parent, string);
        tail = "";
        this.setAppender(new MultimediaAppender(this));
    }

    /**
     * @param parent
     *            parent object of this attribute
     * @param string
     *            long version of type string
     * @param tail
     *            additional data
     */
    public Multimedia(final GedObject parent, final String string,
            final String tail) {
        super(parent, string);
        this.tail = tail;
        this.setAppender(new MultimediaAppender(this));
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
        return "";
    }

    /**
     * If this is a death attribute, return the date as a string. Otherwise
     * return an empty string.
     *
     * @return the death date string.
     */
    public String getDeathDate() {
        return "";
    }

    /**
     * Get the first date attribute found as a string. If none are found return
     * an empty string.
     *
     * @return the date string.
     */
    public String getDate() {
        return "";
    }

    /**
     * @return the path to the multimedia file
     */
    public String getFilePath() {
        for (final GedObject gedObject : getAttributes()) {
            if (!(gedObject instanceof Attribute)) {
                continue;
            }
            final Attribute attribute = (Attribute) gedObject;
            final String string = attribute.getString();
            if ("File".equals(string)) {
                return attribute.getTail();
            }
        }
        return null;
    }

    /**
     * @return the path to the multimedia file
     */
    public String getFileFormat() {
        for (final GedObject gedObject : getAttributes()) {
            if (!(gedObject instanceof Attribute)) {
                continue;
            }
            final Attribute attribute = (Attribute) gedObject;
            final String string = attribute.getString();
            if ("File".equals(string)) {
                for (final GedObject subObject : attribute.getAttributes()) {
                    if (!(subObject instanceof Attribute)) {
                        continue;
                    }
                    final Attribute subAttr = (Attribute) subObject;
                    if ("Format".equals(subAttr.getString())) {
                        return subAttr.getTail();
                    }
                }
            }
        }
        return null;
    }

    /**
     * @return the path to the multimedia file
     */
    public String getFileTitle() {
        for (final GedObject gedObject : getAttributes()) {
            if (!(gedObject instanceof Attribute)) {
                continue;
            }
            final Attribute attribute = (Attribute) gedObject;
            final String string = attribute.getString();
            if ("Title".equals(string)) {
                return attribute.getTail();
            }
            if ("File".equals(string)) {
                for (final GedObject subObject : attribute.getAttributes()) {
                    if (!(subObject instanceof Attribute)) {
                        continue;
                    }
                    final Attribute subAttr = (Attribute) subObject;
                    if ("Title".equals(subAttr.getString())) {
                        return subAttr.getTail();
                    }
                }
            }
        }
        return null;
    }

    /**
     * @return whether the type is an image type
     */
    public boolean isImage() {
        final String format = getFileFormat();
        return "jpg".equalsIgnoreCase(format)
                || "gif".equalsIgnoreCase(format)
                || "png".equalsIgnoreCase(format)
                || "tif".equalsIgnoreCase(format);
    }
}
