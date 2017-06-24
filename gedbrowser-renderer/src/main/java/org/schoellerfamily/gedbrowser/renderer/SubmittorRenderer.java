package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;

/**
 * Render a Submittor.
 *
 * @author Dick Schoeller
 */
public final class SubmittorRenderer extends GedRenderer<Submittor>
        implements IndexHrefRenderer<Submittor>, AttributesRenderer<Submittor>,
        HeaderHrefRenderer<Submittor>, SubmittorsHrefRenderer<Submittor>,
        SourcesHrefRenderer<Submittor> {
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
        setNameHtmlRenderer(new SubmittorNameHtmlRenderer(this));
        setNameIndexRenderer(new SubmittorNameIndexRenderer(this));
    }

    /**
     * @return the ID string of the person.
     */
    public String getIdString() {
        return getGedObject().getString();
    }

    /**
     * @return a nicely string string for the title
     */
    public String getTitleString() {
        final Submittor gob = getGedObject();
        final Name name = gob.getName();
        final String nameString = name.getString();
        return nameString.replace("/", " ").replace("  ", " ").trim();
    }
}
