package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.appender.MultimediaAppender;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * Represents multimedia in the domain model.
 *
 * @author Richard Schoeller
 */
public final class Multimedia extends AbstractAttribute {
    /**
     * The tail value.
     */
    private String tail;

    /**
     * Creates a new Multimedia.
     */
    public Multimedia() {
        super();
        this.tail = "";
        this.setAppender(new MultimediaAppender(this));
    }

    /**
     * Creates a new Multimedia.
     *
     * @param parent the parent
     * @param string the string
     */
    public Multimedia(final GedObject parent, final String string) {
        super(parent, string);
        tail = "";
        this.setAppender(new MultimediaAppender(this));
    }

    /**
     * Creates a new Multimedia.
     *
     * @param parent the parent
     * @param string the string
     */
    public Multimedia(final GedObject parent, final String string,
            final String tail) {
        super(parent, string);
        this.tail = tail;
        this.setAppender(new MultimediaAppender(this));
    }

    /**
     * Gets the tail.
     *
     * @return the tail
     */
    public String getTail() {
        if (tail == null) {
            return "";
        } else {
            return tail;
        }
    }

    /**
     * Sets the tail.
     *
     * @param tail the tail
     */
    public void setTail(final String tail) {
        if (tail == null) {
            this.tail = "";
        } else {
            this.tail = tail;
        }
    }

    /**
     * Executes accept.
     *
     * @param visitor the visitor
     */
    @Override
    public void accept(final GedObjectVisitor visitor) {
        visitor.visit(this);
    }
}
