package org.schoellerfamily.gedbrowser.datamodel.visitor;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;

/**
 * @author Dick Schoeller
 */
public final class GetTimeVisitor implements GedObjectVisitor {
    /**
     * Accumulates the best time information.
     */
    private String timeString = "";

    /**
     * Visit an Attribute. We will look at the attributes of this Attribute
     * for Dates. Once one is found, quit.
     *
     * @see GedObjectVisitor#visit(Attribute)
     */
    @Override
    public void visit(final Attribute attribute) {
        if ("Time".equals(attribute.getString())) {
            timeString = attribute.getTail();
        }
    }

    /**
     * Did we find one?
     *
     * @return the time string if we found one.
     */
    public String getTimeString() {
        return timeString;
    }
}
