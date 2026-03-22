package org.schoellerfamily.gedbrowser.renderer;

import lombok.RequiredArgsConstructor;

/**
 * Renders submission link phrase output for display.
 *
 * @author Richard Schoeller
 */
@RequiredArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class SubmissionLinkPhraseRenderer implements PhraseRenderer {
    /**
     * Holder for the SubmissionLinkRenderer that is using this helper.
     */
    private final SubmissionLinkRenderer slRenderer;

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
