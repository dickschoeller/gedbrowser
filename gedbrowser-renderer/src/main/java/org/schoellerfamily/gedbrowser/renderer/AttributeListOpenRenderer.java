package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * @author Dick Schoeller
 */
public interface AttributeListOpenRenderer {
    /**
     * Render the opening of an attribute list.
     *
     * @param builder the builder that will deal with this.
     * @param pad the amount of indentation we need.
     * @param subObject a subobject.
     */
    void renderAttributeListOpen(StringBuilder builder, int pad,
            GedObject subObject);
}
