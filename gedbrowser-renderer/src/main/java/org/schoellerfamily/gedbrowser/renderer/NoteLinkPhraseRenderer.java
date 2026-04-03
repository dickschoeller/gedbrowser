package org.schoellerfamily.gedbrowser.renderer;

import lombok.RequiredArgsConstructor;

/**
 * Renders note link phrase output for display.
 *
 * @author Richard Schoeller
 */
@RequiredArgsConstructor
public final class NoteLinkPhraseRenderer implements PhraseRenderer {
    /** */
    private final NoteLinkRenderer nlRenderer;

    /**
     * Executes render as phrase.
     *
     * @return the resulting string
     */
    @Override
    public String renderAsPhrase() {
        final StringBuilder builder = new StringBuilder(50);
        builder.append(" [<a href=\"note?db=");
        builder.append(nlRenderer.getGedObject().getDbName());
        builder.append("&amp;id=");
        builder.append(nlRenderer.getGedObject().getToString());
        builder.append("\">");
        builder.append(nlRenderer.getGedObject().getToString());
        builder.append("</a>]");
        return builder.toString();
    }
}
