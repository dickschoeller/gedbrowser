package org.schoellerfamily.gedbrowser.renderer;

/**
 * Renders null phrase output for display.
 *
 * @author Richard Schoeller
 */
public class NullPhraseRenderer implements PhraseRenderer {
    /**
     * Returns the string.
     *
     * @return the resulting string
     */
    @Override
    public final String renderAsPhrase() {
        return "";
    }

}
