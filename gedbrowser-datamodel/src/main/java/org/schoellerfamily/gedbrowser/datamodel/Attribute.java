package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.appender.TailAppender;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * @author Dick Schoeller
 */
public final class Attribute extends AbstractAttribute implements Tail {
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
     * Creates a new Attribute.
     *
     * @param parent the parent
     */
    public Attribute(final GedObject parent) {
        super(parent);
        this.tail = "";
        this.setAppender(new TailAppender(this));
    }

    /**
     * Creates a new Attribute.
     *
     * @param parent the parent
     * @param string the string
     */
    public Attribute(final GedObject parent, final String string) {
        super(parent, string);
        tail = "";
        this.setAppender(new TailAppender(this));
    }

    /**
     * Creates a new Attribute.
     *
     * @param parent the parent
     * @param string the string
     * @param tail the tail
     */
    public Attribute(final GedObject parent, final String string,
            final String tail) {
        super(parent, string);
        this.tail = tail;
        this.setAppender(new TailAppender(this));
    }

    /**
     * Gets the tail.
     *
     * @return the tail
     */
    @Override
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
    @Override
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
