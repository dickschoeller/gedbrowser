package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.datamodel.visitor.MultimediaVisitor;

/**
 * @author Dick Schoeller
 */
public final class MultimediaListItemRenderer implements ListItemRenderer {
    /**
     * Holder for the MultimediaRenderer that is using this helper.
     */
    private final transient MultimediaRenderer multimediaRenderer;

    /**
     * Constructor.
     *
     * @param multimediaRenderer the renderer that this is associated with.
     */
    public MultimediaListItemRenderer(
            final MultimediaRenderer multimediaRenderer) {
        this.multimediaRenderer = multimediaRenderer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StringBuilder renderAsListItem(final StringBuilder builder,
            final boolean newLine, final int pad) {

        builder.append("<li>");

        renderListItemContents(builder);

        builder.append("</li>\n");

        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getListItemContents() {
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
    private void renderListItemContents(final StringBuilder builder) {
        final Multimedia multimedia = multimediaRenderer.getGedObject();
        builder.append("<span class=\"label\">");
        builder.append(GedRenderer.escapeString(multimedia));
        builder.append(":</span> ");

        final MultimediaVisitor visitor = new MultimediaVisitor();
        multimedia.accept(visitor);
        if (visitor.isImage()) {
            builder.append(visitor.getTitle());
            builder.append("<br/>\n<a href=\"");
            builder.append(visitor.getFilePath());
            builder.append("\"><img height=\"300px\" src=\"");
            builder.append(visitor.getFilePath());
            builder.append("\" title=\"");
            builder.append(visitor.getTitle());
            builder.append("\"/></a>");
        } else {
            builder.append("<a href=\"");
            builder.append(visitor.getFilePath());
            builder.append("\">");
            builder.append(visitor.getTitle());
            builder.append("</a>");
        }
    }
}
