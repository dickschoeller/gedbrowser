package org.schoellerfamily.gedbrowser.renderer;

/**
 * @author Dick Schoeller
 */
public class NullSectionRenderer implements SectionRenderer {
    /**
     * {@inheritDoc}
     */
    @Override
    public final StringBuilder renderAsSection(final StringBuilder builder,
            final GedRenderer<?> outerRenderer, final boolean newLine,
            final int pad, final int sectionNumber) {
        return builder;
    }
}
