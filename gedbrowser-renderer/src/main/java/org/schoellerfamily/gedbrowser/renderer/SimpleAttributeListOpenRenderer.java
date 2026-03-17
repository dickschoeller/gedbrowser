package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * Renders simple attribute list open output for display.
 *
 * @author Richard Schoeller
 */
public class SimpleAttributeListOpenRenderer implements
        AttributeListOpenRenderer {
    /**
     * Executes render attribute list open.
     *
     * @param builder the builder
     * @param pad the pad
     * @param subObject the sub object
     */
    @Override
    public final void renderAttributeListOpen(final StringBuilder builder,
            final int pad, final GedObject subObject) {
        if (subObject.hasAttributes()) {
            GedRenderer.renderPad(builder, pad, true);
            builder.append("<ul>\n");
        }
    }
}
