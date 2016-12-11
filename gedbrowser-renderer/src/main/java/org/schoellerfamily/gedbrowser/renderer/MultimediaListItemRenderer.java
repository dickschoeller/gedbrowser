package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Multimedia;

/**
 * @author dick
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

        if (isImage(multimedia)) {
            builder.append(getFileTitle(multimedia));
            builder.append("<br/>\n<a href=\"");
            builder.append(getFilePath(multimedia));
            builder.append("\"><img height=\"300px\" src=\"");
            builder.append(getFilePath(multimedia));
            builder.append("\" title=\"");
            builder.append(getFileTitle(multimedia));
            builder.append("\"/></a>");
        } else {
            builder.append("<a href=\"");
            builder.append(getFilePath(multimedia));
            builder.append("\">");
            builder.append(getFileTitle(multimedia));
            builder.append("</a>");
        }
    }

    /**
     * Check if a multimedia item is an image.
     *
     * @param multimedia the multimedia object
     * @return true if this is an image
     */
    private boolean isImage(final Multimedia multimedia) {
        return multimedia.isImage();
    }

    /**
     * Get the file path to the multimedia object.
     *
     * @param multimedia the multimedia object
     * @return the path
     */
    private String getFilePath(final Multimedia multimedia) {
        return multimedia.getFilePath();
    }

    /**
     * Get the file title of the multimedia object.
     *
     * @param multimedia the multimedia object
     * @return the title
     */
    private String getFileTitle(final Multimedia multimedia) {
        return multimedia.getFileTitle();
    }
}
