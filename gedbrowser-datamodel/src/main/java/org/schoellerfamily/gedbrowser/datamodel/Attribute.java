package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.appender.AttributeAppender;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

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
     * @param parent parent object of this attribute
     */
    public Attribute(final GedObject parent) {
        super(parent);
        this.tail = "";
        this.setAppender(new AttributeAppender(this));
    }

    /**
     * @param parent parent object of this attribute
     * @param string long version of type string
     */
    public Attribute(final GedObject parent, final String string) {
        super(parent, string);
        tail = "";
        this.setAppender(new AttributeAppender(this));
    }

    /**
     * @param parent parent object of this attribute
     * @param string long version of type string
     * @param tail additional data
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
