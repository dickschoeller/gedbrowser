package org.schoellerfamily.gedbrowser.renderer;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.visitor.SourceVisitor;
import org.schoellerfamily.gedbrowser.renderer.href.HeaderHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.IndexHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.PlacesHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SaveHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SourcesHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SubmittersHrefRenderer;

/**
 * Renders source output for display.
 *
 * @author Richard Schoeller
 */
public final class SourceRenderer extends GedRenderer<Source>
        implements HeaderHrefRenderer<Source>, IndexHrefRenderer<Source>,
            PlacesHrefRenderer<Source>, SaveHrefRenderer<Source>,
            SourcesHrefRenderer<Source>, SubmittersHrefRenderer<Source> {
    /**
     * Creates a new SourceRenderer.
     *
     * @param gedObject the ged object
     * @param rendererFactory the renderer factory
     * @param renderingContext the rendering context
     */
    public SourceRenderer(final Source gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
        setNameIndexRenderer(new SourceNameIndexRenderer(this));
    }

    /**
     * Gets the id string.
     *
     * @return the id string
     */
    public String getIdString() {
        return getGedObject().getString();
    }

    /**
     * Gets the title string.
     *
     * @return the title string
     */
    public String getTitleString() {
        final SourceVisitor visitor = new SourceVisitor();
        getGedObject().accept(visitor);
        return visitor.getTitleString();
    }

    /**
     * Return the list of renderers that can be rendered in a list format.
     *
     * @return the list of attribute renderers.
     */
    // Suppressed warnings are because of issues related to generics.
    @SuppressWarnings({ "java:S1452", "java:S6204" })
    public List<GedRenderer<?>> getAttributes() {
        return getGedObject().getAttributes().stream()
            .filter(attribute -> !"Title".equals(attribute.getString()))
            .map(this::createGedRenderer)
            .filter(renderer -> StringUtils.isNotEmpty(renderer.getListItemContents()))
            .collect(Collectors.toUnmodifiableList());
    }

    /**
     * Gets the index name html.
     *
     * @return the index name html
     */
    public String getIndexNameHtml() {
        return this.getNameIndexRenderer().getIndexName();
    }
}
