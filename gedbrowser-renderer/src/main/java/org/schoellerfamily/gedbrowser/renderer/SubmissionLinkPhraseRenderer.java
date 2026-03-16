package org.schoellerfamily.gedbrowser.renderer;

/**
 * @author Dick Schoeller
 */
public class SubmissionLinkPhraseRenderer implements PhraseRenderer {
    /**
     * Holder for the SubmissionLinkRenderer that is using this helper.
     */
    private final transient SubmissionLinkRenderer slRenderer;

    /**
     * Creates a new SubmissionLinkPhraseRenderer.
     *
     * @param renderer the renderer
     */
    protected SubmissionLinkPhraseRenderer(
            final SubmissionLinkRenderer renderer) {
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
