package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.appender.TailAppender;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * @author Dick Schoeller
 *
 */
public class Note extends GedObject implements Tail {
    /** */
    private String tail;

    /**
     * Default constructor.
     */
    public Note() {
        super();
        this.setAppender(new TailAppender(this));
    }

    /**
     * @param parent parent object of this note
     * @param xref cross reference to this note
     */
    public Note(final GedObject parent, final ObjectId xref) {
        super(parent, xref.getIdString());
        this.setAppender(new TailAppender(this));
    }

    /**
     * @param parent parent object of this attribute
     * @param xref cross reference to this note
     * @param tail the note body
     */
    public Note(final GedObject parent, final ObjectId xref,
            final String tail) {
        super(parent, xref.getIdString());
        this.tail = tail;
        this.setAppender(new TailAppender(this));
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public void accept(final GedObjectVisitor visitor) {
        visitor.visit(this);
    }
}
