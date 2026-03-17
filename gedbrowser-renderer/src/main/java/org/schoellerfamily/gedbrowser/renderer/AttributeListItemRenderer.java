package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * Renders attribute list item output for display.
 *
 * @author Richard Schoeller
 */
public class AttributeListItemRenderer implements ListItemRenderer {
    /**
     * Holder for the AttributeRenderer that is using this helper.
     */
    private final transient AttributeRenderer attributeRenderer;

    /**
     * Creates a new AttributeListItemRenderer.
     *
     * @param attributeRenderer the attribute renderer
     */
    public AttributeListItemRenderer(
            final AttributeRenderer attributeRenderer) {
        this.attributeRenderer = attributeRenderer;
    }

    /**
     * Executes render as list item.
     *
     * @param builder the builder
     * @param newLine the new line
     * @param pad the pad
     * @return the resulting string builder
     */
    @Override
    public final StringBuilder renderAsListItem(final StringBuilder builder,
            final boolean newLine, final int pad) {

        builder.append("<li>");

        renderListItemContents(builder);

        builder.append("</li>\n");

        return builder;
    }

    /**
     * Returns the list item contents.
     *
     * @return the list item contents
     */
    @Override
    public final String getListItemContents() {
        final StringBuilder builder = new StringBuilder();
        renderListItemContents(builder);
        return builder.toString();
    }

    /**
     * Render the inner part of this thing in a list item.  Will never
     * have an &lt;li&gt; tag.
     *
     * @param builder the string builder that we will be appending to.
     */
    protected final void renderListItemContents(final StringBuilder builder) {
        final Attribute attribute = attributeRenderer.getGedObject();
        builder.append("<span class=\"label\">");
        builder.append(RenderingContextRenderer.escapeString(attribute));
        builder.append(":</span> ");
        builder.append(attributeRenderer.renderAsPhrase());

        boolean previousIsFull = !attribute.getTail().isEmpty();
        for (final GedObject subAttr : attribute.getAttributes()) {
            final GedRenderer<? extends GedObject> renderer =
                    attributeRenderer.createGedRenderer(subAttr);
            final String html = renderer.renderAsPhrase();
            final boolean currentIsFull = !html.isEmpty();
            final String separator = renderer.getSeparator(currentIsFull
                    && previousIsFull);
            if (currentIsFull) {
                previousIsFull = true;
            }
            builder.append(separator);
            builder.append(html);
        }
    }
}
