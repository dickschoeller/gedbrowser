package org.schoellerfamily.gedbrowser.renderer;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;

/**
 * Render a Head.
 *
 * @author Dick Schoeller
 */
public final class HeadRenderer extends GedRenderer<Head>
        implements IndexHrefRenderer<Head>, AttributesRenderer<Head> {
    /**
     * @param gedObject the Head that we are going to render.
     * @param rendererFactory the factory that creates the renderers for the
     *        attributes
     * @param renderingContext the context that we are rendering in
     */
    public HeadRenderer(final Head gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
    }


    /**
     * Return the list of renderers that can be rendered in a list format.
     *
     * @return the list of attribute renderers.
     */
    public List<GedRenderer<?>> getAttributes() {
        final List<GedRenderer<?>> rendererList =
                new ArrayList<GedRenderer<?>>();
        final Head head = getGedObject();
        for (final GedObject attribute : head.getAttributes()) {
            final GedRenderer<?> attributeRenderer =
                    createGedRenderer(attribute);
            if (!attributeRenderer.getListItemContents().isEmpty()) {
                rendererList.add(attributeRenderer);
            }
        }
        return rendererList;
    }
}
