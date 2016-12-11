package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * @author Dick Schoeller
 */
public class PersonAttributeListOpenRenderer implements
        AttributeListOpenRenderer {
    /**
     * {@inheritDoc}
     */
    @Override
    public final void renderAttributeListOpen(final StringBuilder builder,
            final int pad, final GedObject gob) {
        if (gob.hasAttributes()) {
            GedRenderer.renderPad(builder, pad, true);
            builder.append("<hr class=\"attributes\"/>");

            GedRenderer.renderPad(builder, pad, true);
            builder.append("<h3 class=\"attributes\">Attributes</h3>");

            GedRenderer.renderPad(builder, pad, true);
            builder.append("<ul>\n");
        }
    }
}
