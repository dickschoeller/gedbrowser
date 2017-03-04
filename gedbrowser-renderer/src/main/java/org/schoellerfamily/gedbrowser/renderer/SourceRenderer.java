package org.schoellerfamily.gedbrowser.renderer;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.visitor.SourceVisitor;

/**
 * Render a Source.
 *
 * @author Dick Schoeller
 */
public final class SourceRenderer extends GedRenderer<Source> {
    /**
     * @param gedObject the Source that we are going to render
     * @param rendererFactory the factory that creates the renderers for the
     *        attributes
     * @param renderingContext the context that we are rendering in
     * @param provider calendar provider
     */
    public SourceRenderer(final Source gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext,
            final CalendarProvider provider) {
        super(gedObject, rendererFactory, renderingContext, provider);
    }

    /**
     * @return the ID string of the person.
     */
    public String getIdString() {
        return getGedObject().getString();
    }

    /**
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
    public List<GedRenderer<?>> getAttributes() {
        final Source source = getGedObject();
        final List<GedRenderer<?>> list = new ArrayList<GedRenderer<?>>();
        for (final GedObject attribute : source.getAttributes()) {
            final GedRenderer<?> attributeRenderer =
                    createGedRenderer(attribute);
            if ("Title".equals(attribute.getString())) {
                continue;
            }
            if (!attributeRenderer.getListItemContents().isEmpty()) {
                list.add(attributeRenderer);
            }
        }
        return list;
    }

    /**
     * @return the href string to the index page containing this person.
     */
    public String getIndexHref() {
        final Source source = getGedObject();
        return "surnames?db=" + source.getDbName() + "&letter=A";
    }
}
