package org.schoellerfamily.gedbrowser.renderer;

/**
 * Renders phrase output for display.
 *
 * @author Richard Schoeller
 */
public interface PhraseRenderer {
    /**
     * Render the associated object as a phrase in a sentence.
     *
     * @return the phrase.
     */
    String renderAsPhrase();
}
