package org.schoellerfamily.gedbrowser.datamodel.visitor;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;

/**
 * Visits get time elements and applies visitor logic.
 *
 * @author Richard Schoeller
 */
public final class GetTimeVisitor implements GedObjectVisitor {
    /**
     * Creates a new GetTimeVisitor.
     */
    public GetTimeVisitor() {
    }

    /**
     * Accumulates the best time information.
     */
    private String timeString = "";

    /**
     * Executes visit.
     *
     * @param attribute the attribute
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
