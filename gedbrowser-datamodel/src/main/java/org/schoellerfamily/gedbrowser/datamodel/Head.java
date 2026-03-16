package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * @author Dick Schoeller
 */
public final class Head extends AbstractSpecialObject {
    /**
     * Default constructor.
     */
    public Head() {
        super();
    }

    /**
     * @param parent parent object of this object
     * @param tag long version of type string
     */
    public Head(final GedObject parent, final String tag) {
        super(parent, tag);
    }

    /**
     * @param parent parent object of this child
     * @param tag long version of type string
     * @param tail additional text to append to the string
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

    @Override
    public void accept(final GedObjectVisitor visitor) {
        visitor.visit(this);
    }
}
