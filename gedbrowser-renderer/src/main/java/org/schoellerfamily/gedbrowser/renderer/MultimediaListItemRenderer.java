package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.datamodel.visitor.MultimediaVisitor;

import lombok.RequiredArgsConstructor;

/**
 * Renders multimedia list item output for display.
 *
 * @author Richard Schoeller
 */
@RequiredArgsConstructor
public final class MultimediaListItemRenderer implements ListItemRenderer {
    /**
     * Holder for the MultimediaRenderer that is using this helper.
     */
    private final MultimediaRenderer multimediaRenderer;

    /**
     * Executes render as list item.
     *
     * @param builder the builder
     * @param newLine the new line
     * @param pad the pad
     * @return the resulting string builder
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
     * Returns the list item contents.
     *
     * @return the list item contents
     */
    @Override
    public String getListItemContents() {
        final StringBuilder builder = new StringBuilder();
        renderListItemContents(builder);
        return builder.toString();
    }

    private void renderListItemContents(final StringBuilder builder) {
        final Multimedia multimedia = multimediaRenderer.getGedObject();
        builder.append("<span class=\"label\">");
        builder.append(RenderingContextRenderer.escapeString(multimedia));
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
