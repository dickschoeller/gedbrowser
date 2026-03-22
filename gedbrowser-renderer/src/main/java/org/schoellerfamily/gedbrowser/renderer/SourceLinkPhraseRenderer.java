package org.schoellerfamily.gedbrowser.renderer;

import lombok.RequiredArgsConstructor;

/**
 * Renders source link phrase output for display.
 *
 * @author Richard Schoeller
 */
@RequiredArgsConstructor
public final class SourceLinkPhraseRenderer implements PhraseRenderer {
    /** */
    private final SourceLinkRenderer slRenderer;

    /**
     * Executes render as phrase.
     *
     * @return the resulting string
     */
    @Override
    public String renderAsPhrase() {
        final StringBuilder builder = new StringBuilder(50);
        builder.append(" [<a href=\"source?db=");
        builder.append(slRenderer.getGedObject().getDbName());
        builder.append("&amp;id=");
        builder.append(slRenderer.getGedObject().getToString());
        builder.append("\">");
        builder.append(slRenderer.getGedObject().getToString());
        builder.append("</a>]");
        return builder.toString();
    }
}
