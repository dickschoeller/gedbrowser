package org.schoellerfamily.gedbrowser.renderer;

/**
 * Renders null name index output for display.
 *
 * @author Richard Schoeller
 */
public class NullNameIndexRenderer implements NameIndexRenderer {
    /**
     * Returns the index name.
     *
     * @return the index name
     */
    @Override
    public final String getIndexName() {
        return "";
    }
}
