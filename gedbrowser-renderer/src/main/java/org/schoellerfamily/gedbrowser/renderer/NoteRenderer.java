package org.schoellerfamily.gedbrowser.renderer;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Note;
import org.schoellerfamily.gedbrowser.renderer.href.HeaderHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.IndexHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.PlacesHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SaveHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SourcesHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SubmittersHrefRenderer;

/**
 * Renders note output for display.
 *
 * @author Richard Schoeller
 */
public final class NoteRenderer extends GedRenderer<Note>
        implements HeaderHrefRenderer<Note>, IndexHrefRenderer<Note>,
            PlacesHrefRenderer<Note>, SaveHrefRenderer<Note>,
            SourcesHrefRenderer<Note>, SubmittersHrefRenderer<Note> {
    /**
     * Creates a new NoteRenderer.
     *
     * @param gedObject the ged object
     * @param rendererFactory the renderer factory
     * @param renderingContext the rendering context
     */
    public NoteRenderer(final Note gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
        setNameIndexRenderer(new NoteNameIndexRenderer(this));
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
        final String tail = getGedObject().getTail();
        final int truncateLength = Integer.min(50, tail.length());
        return tail.substring(0, truncateLength);
    }

    /**
     * Gets the contents.
     *
     * @return the contents
     */
    public String getContents() {
        return getGedObject().getTail().replace("\n", "</p>\n<p>");
    }

    /**
     * Return the list of renderers that can be rendered in a list format.
     *
     * @return the list of attribute renderers.
     */
    @SuppressWarnings("java:S1452")
    public List<GedRenderer<?>> getAttributes() {
        final Note source = getGedObject();
        final List<GedRenderer<?>> list = new ArrayList<GedRenderer<?>>();
        for (final GedObject attribute : source.getAttributes()) {
            final GedRenderer<?> attributeRenderer =
                    createGedRenderer(attribute);
            if (!attributeRenderer.getListItemContents().isEmpty()) {
                list.add(attributeRenderer);
            }
        }
        return list.stream().toList();
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
