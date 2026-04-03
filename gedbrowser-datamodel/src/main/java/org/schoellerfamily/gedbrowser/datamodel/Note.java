package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.appender.TailAppender;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * Represents note in the domain model.
 *
 * @author Richard Schoeller
 */
public final class Note extends GedObject implements Tail {
    /**
     * The tail value.
     */
    private String tail;

    /**
     * Creates a new Note.
     */
    public Note() {
        super();
        this.setAppender(new TailAppender(this));
    }

    /**
     * Creates a new Note.
     *
     * @param parent the parent
     * @param xref the xref
     */
    public Note(final GedObject parent, final ObjectId xref) {
        super(parent, xref.getIdString());
        this.setAppender(new TailAppender(this));
    }

    /**
     * Creates a new Note.
     *
     * @param parent the parent
     * @param xref the xref
     * @param tail the tail
     */
    public Note(final GedObject parent, final ObjectId xref,
            final String tail) {
        super(parent, xref.getIdString());
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
