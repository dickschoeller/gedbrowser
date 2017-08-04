package org.schoellerfamily.gedbrowser.renderer;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Note;

/**
 * Render a Note.
 *
 * @author Dick Schoeller
 */
public final class NoteRenderer extends GedRenderer<Note>
        implements HeaderHrefRenderer<Note>, IndexHrefRenderer<Note>,
            PlacesHrefRenderer<Note>, SourcesHrefRenderer<Note>,
            SubmittersHrefRenderer<Note> {
    /**
     * @param gedObject the Note that we are going to render
     * @param rendererFactory the factory that creates the renderers for the
     *        attributes
     * @param renderingContext the context that we are rendering in
     */
    public NoteRenderer(final Note gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
        setNameIndexRenderer(new NoteNameIndexRenderer(this));
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
        final String tail = getGedObject().getTail();
        final int truncateLength = Integer.min(50, tail.length());
        return tail.substring(0, truncateLength);
    }

    /**
     * @return the content string
     */
    public String getContents() {
        return getGedObject().getTail().replace("\n", "</p>\n<p>");
    }

    /**
     * Return the list of renderers that can be rendered in a list format.
     *
     * @return the list of attribute renderers.
     */
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
        return list;
    }

    /**
     * @return the &lt;a href&gt; string for this source
     */
    public String getIndexNameHtml() {
        return this.getNameIndexRenderer().getIndexName();
    }
}
