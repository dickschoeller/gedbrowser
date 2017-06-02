package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Submittor;

/**
 * Render a Submittor.
 *
 * @author Dick Schoeller
 */
public final class SubmittorRenderer extends GedRenderer<Submittor> implements
        IndexHrefRenderer<Submittor>, AttributesRenderer<Submittor>,
        HeaderHrefRenderer<Submittor> {
    /**
     * @param gedObject the Submittor that we are going to render
     * @param rendererFactory the factory that creates the renderers for the
     *        attributes
     * @param renderingContext the context that we are rendering in
     */
    public SubmittorRenderer(final Submittor gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
    }

    /**
     * @return the ID string of the person.
     */
    public String getIdString() {
        return getGedObject().getString();
    }
}
