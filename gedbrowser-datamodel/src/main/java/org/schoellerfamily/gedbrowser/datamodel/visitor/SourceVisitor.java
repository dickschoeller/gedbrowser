package org.schoellerfamily.gedbrowser.datamodel.visitor;

import lombok.NoArgsConstructor;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Source;

/**
 * Visits source elements and applies visitor logic.
 *
 * @author Richard Schoeller
 */
@NoArgsConstructor
public final class SourceVisitor implements GedObjectVisitor {

    /**
     * The title string value.
     */
    private String titleString;

    /**
     * Gets the title string.
     *
     * @return the title string
     */
    public String getTitleString() {
        if (titleString == null) {
            return "";
        }
        return titleString;
    }

    /**
     * Executes visit.
     *
     * @param attribute the attribute
     */
    @Override
    public void visit(final Attribute attribute) {
        if ("Title".equals(attribute.getString())) {
            titleString = attribute.getTail();
        }
    }

    /**
     * Executes visit.
     *
     * @param source the source
     */
    @Override
    public void visit(final Source source) {
        titleString = source.getString();
        for (final GedObject gob : source.getAttributes()) {
            gob.accept(this);
        }
    }
}
