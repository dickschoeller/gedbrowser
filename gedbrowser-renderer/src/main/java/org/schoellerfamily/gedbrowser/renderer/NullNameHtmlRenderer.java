package org.schoellerfamily.gedbrowser.renderer;

/**
 * Renders null name html output for display.
 *
 * @author Richard Schoeller
 */
public class NullNameHtmlRenderer implements NameHtmlRenderer {
    /**
     * Returns the name html.
     *
     * @return the name html
     */
    @Override
    public final String getNameHtml() {
        return "";
    }
}
