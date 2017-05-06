package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;

/**
 * Render an Attribute.
 *
 * @author Dick Schoeller
 */
public final class AttributeRenderer extends GedRenderer<Attribute> {
    /**
     * @param gedObject the Attribute that we are going to render
     * @param rendererFactory the factory that creates the renderers for the
     *        attributes
     * @param renderingContext the context that we are rendering in
     */
    public AttributeRenderer(final Attribute gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
        setListItemRenderer(new AttributeListItemRenderer(this));
        setPhraseRenderer(new AttributePhraseRenderer(this));
    }

    /**
     * @return the string field processed for HTML special characters.
     */
    public String getEscapedString() {
        final Attribute attribute = getGedObject();
        return GedRenderer.escapeString(attribute);
    }
}
