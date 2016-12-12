package org.schoellerfamily.gedbrowser.renderer;

/**
 * @author Dick Schoeller
 */
public interface ListItemRenderer {
    /**
     * Render the GedObject as an item in a list.
     *
     * @param builder buffer for holding the rendition
     * @param newLine put in a new line for each line.
     * @param pad minimum number spaces for padding each line of the output
     * @return the builder
     */
    StringBuilder renderAsListItem(StringBuilder builder, boolean newLine,
            int pad);

    /**
     * @return the inner part of the object as a list item.
     */
    String getListItemContents();
}
