package org.schoellerfamily.gedbrowser.renderer;

/**
 * @author Dick Schoeller
 */
public class NullNameHtmlRenderer implements NameHtmlRenderer {
    /**
     * {@inheritDoc}
     */
    @Override
    public final String getNameHtml() {
        return "";
    }
}
