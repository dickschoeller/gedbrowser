package org.schoellerfamily.gedbrowser.datamodel.visitor;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Source;

/**
 * @author Dick Schoeller
 */
public final class SourceVisitor implements GedObjectVisitor {
    /** */
    private String titleString;

    /**
     * @return the title string of the source
     */
    public String getTitleString() {
        if (titleString == null) {
            return "";
        }
        return titleString;
    }

    /**
     * Visit an Attribute. Specific Attributes provide interesting data for
     * the Source that is the primary focus.
     *
     * @see GedObjectVisitor#visit(Attribute)
     */
    @Override
    public void visit(final Attribute attribute) {
        if ("Title".equals(attribute.getString())) {
            titleString = attribute.getTail();
        }
    }

    /**
     * Visit a Source. This is the primary focus of the visitation. From
     * here, interesting information is gathered.
     *
     * @see GedObjectVisitor#visit(Source)
     */
    @Override
    public void visit(final Source source) {
        titleString = source.getString();
        for (final GedObject gob : source.getAttributes()) {
            gob.accept(this);
        }
    }
}
