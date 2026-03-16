package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;

/**
 * Render an Attribute.
 *
 * @author Dick Schoeller
 */
public final class AttributeRenderer extends GedRenderer<Attribute> {
    /**
     * Creates a new AttributeRenderer.
     *
     * @param gedObject the ged object
     * @param rendererFactory the renderer factory
     * @param renderingContext the rendering context
     */
    public AttributeRenderer(final Attribute gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
        setListItemRenderer(new AttributeListItemRenderer(this));
        setPhraseRenderer(new AttributePhraseRenderer(this));
    }

    /**
     * Gets the escaped string.
     *
     * @return the escaped string
     */
    public String getEscapedString() {
        final Attribute attribute = getGedObject();
        return escapeString(attribute);
    }
}
