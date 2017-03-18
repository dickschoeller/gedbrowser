package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.appender.MultimediaAppender;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

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
     * @param tail additional data
     */
    public void setTail(final String tail) {
        if (tail == null) {
            this.tail = "";
        } else {
            this.tail = tail;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final GedObjectVisitor visitor) {
        visitor.visit(this);
    }
}
