package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.renderer.href.HeaderHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.IndexHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.PlacesHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SaveHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SourcesHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SubmittersHrefRenderer;

/**
 * Render a Submitter.
 *
 * @author Dick Schoeller
 */
public final class SubmitterRenderer extends GedRenderer<Submitter>
        implements IndexHrefRenderer<Submitter>, AttributesRenderer<Submitter>,
        HeaderHrefRenderer<Submitter>, PlacesHrefRenderer<Submitter>,
        SaveHrefRenderer<Submitter>, SubmittersHrefRenderer<Submitter>,
        SourcesHrefRenderer<Submitter> {
    /**
     * Creates a new SubmitterRenderer.
     *
     * @param gedObject the ged object
     * @param rendererFactory the renderer factory
     * @param renderingContext the rendering context
     */
    public SubmitterRenderer(final Submitter gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
        setNameHtmlRenderer(new SubmitterNameHtmlRenderer(this));
        setNameIndexRenderer(new SubmitterNameIndexRenderer(this));
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
        final Submitter gob = getGedObject();
        final Name name = gob.getName();
        final String nameString = name.getString();
        return nameString.replace("/", " ").replace("  ", " ").trim();
    }
}
