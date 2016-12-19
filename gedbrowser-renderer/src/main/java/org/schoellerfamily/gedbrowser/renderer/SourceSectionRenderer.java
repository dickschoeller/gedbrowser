package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Source;

/**
 * @author Dick Schoeller
 */
public class SourceSectionRenderer implements SectionRenderer {
    /**
     * Holder for the SourceRenderer that is using this helper.
     */
    private final transient SourceRenderer sourceRenderer;

    /**
     * Constructor.
     *
     * @param sourceRenderer the renderer that this is associated with.
     */
    protected SourceSectionRenderer(final SourceRenderer sourceRenderer) {
        this.sourceRenderer = sourceRenderer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final StringBuilder renderAsSection(final StringBuilder builder,
            final GedRenderer<?> pageRenderer, final boolean newLine,
            final int pad, final int sectionNumber) {
        final Source source = sourceRenderer.getGedObject();
        GedRenderer.renderNewLine(builder, newLine);

        sourceRenderer.renderAttributeList(builder, pad, source);

        return builder;
    }
}
