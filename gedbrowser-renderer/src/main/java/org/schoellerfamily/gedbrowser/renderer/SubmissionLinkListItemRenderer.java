package org.schoellerfamily.gedbrowser.renderer;

import lombok.RequiredArgsConstructor;

/**
 * Renders submission link list item output for display.
 *
 * @author Richard Schoeller
 */
@RequiredArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class SubmissionLinkListItemRenderer implements ListItemRenderer {
    /**
     * Holder for the SubmissionLinkRenderer that is using this helper.
     */
    private final SubmissionLinkRenderer submissionLinkRenderer;

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
        renderListItemContents(builder);
        return builder;
    }

    private void renderListItemContents(final StringBuilder builder) {
        builder.append("<span class=\"label\">Submission:</span> ");
        builder.append(submissionLinkRenderer.getNameHtml());
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
}
