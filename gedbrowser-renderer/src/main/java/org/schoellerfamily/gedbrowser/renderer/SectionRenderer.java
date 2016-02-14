package org.schoellerfamily.gedbrowser.renderer;

/**
 * @author Dick Schoeller
 */
public interface SectionRenderer {
    /**
     * Render the GedObject as a section of a page.
     *
     * @param builder buffer for holding the rendition
     * @param outerRenderer the renderer being used for the current page
     * @param newLine put in a new line for each line.
     * @param pad minimum number spaces for padding each line of the output
     * @param sectionNumber numbers repeated sections of the same type.
     * @return the builder
     */
    StringBuilder renderAsSection(StringBuilder builder,
            GedRenderer<?> outerRenderer, boolean newLine, int pad,
            int sectionNumber);

}
