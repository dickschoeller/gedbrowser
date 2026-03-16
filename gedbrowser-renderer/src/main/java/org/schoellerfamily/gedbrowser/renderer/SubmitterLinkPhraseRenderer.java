package org.schoellerfamily.gedbrowser.renderer;

/**
 * @author Dick Schoeller
 */
public class SubmitterLinkPhraseRenderer implements PhraseRenderer {
    /**
     * Holder for the SubmitterLinkRenderer that is using this helper.
     */
    private final transient SubmitterLinkRenderer slRenderer;

    /**
     * Creates a new SubmitterLinkPhraseRenderer.
     *
     * @param renderer the renderer
     */
    protected SubmitterLinkPhraseRenderer(
            final SubmitterLinkRenderer renderer) {
        this.slRenderer = renderer;
    }

    /**
     * Returns the string.
     *
     * @return the resulting string
     */
    @Override
    public final String renderAsPhrase() {
        return slRenderer.getNameString();
    }
}
