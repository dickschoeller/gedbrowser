package org.schoellerfamily.gedbrowser.renderer;

/**
 * Renders note link phrase output for display.
 *
 * @author Richard Schoeller
 */
public final class NoteLinkPhraseRenderer implements PhraseRenderer {
    /** */
    private final transient NoteLinkRenderer nlRenderer;

    /**
     * Creates a new NoteLinkPhraseRenderer.
     *
     * @param renderer the renderer
     */
    public NoteLinkPhraseRenderer(
            final NoteLinkRenderer renderer) {
        this.nlRenderer = renderer;
    }

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
