package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * @author Dick Schoeller
 */
public final class Head extends AbstractSpecialObject {
    /**
     * Creates a new Head.
     *
     */
    public Head() {
        super();
    }

    /**
     * Creates a new Head.
     *
     * @param parent the parent
     * @param tag the tag
     */
    public Head(final GedObject parent, final String tag) {
        super(parent, tag);
    }

    /**
     * Creates a new Head.
     *
     * @param parent the parent
     * @param tag the tag
     * @param tail the tail
     */
    public Head(final GedObject parent, final String tag, final String tail) {
        super(parent, buildParentString(tag, tail));
    }

    private static String buildParentString(final String tag,
            final String tail) {
        if (tail.isEmpty()) {
            return tag;
        } else {
            return tag + " " + tail;
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
