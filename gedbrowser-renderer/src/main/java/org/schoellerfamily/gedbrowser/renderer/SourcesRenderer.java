package org.schoellerfamily.gedbrowser.renderer;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;

import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.renderer.href.HeaderHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.IndexHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.PlacesHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SaveHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SourcesHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SubmittersHrefRenderer;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@Slf4j
public class SourcesRenderer extends GedRenderer<Root>
        implements HeaderHrefRenderer<Root>, IndexHrefRenderer<Root>,
        PlacesHrefRenderer<Root>, SaveHrefRenderer<Root>,
        SourcesHrefRenderer<Root>, SubmittersHrefRenderer<Root> {

    /**
     * Compares source renderers for sorting by title string.
     *
     * @author Dick Schoeller
     */
    private static final class SourceRendererComparator
            implements Comparator<SourceRenderer>, Serializable {
        /** */
        private static final long serialVersionUID = 1L;

        /**
         * Returns the int.
         *
         * @param renderer0 the renderer0
         * @param renderer1 the renderer1
         * @return the resulting int
         */
        @Override
        public int compare(final SourceRenderer renderer0,
                final SourceRenderer renderer1) {
            return renderer0.getTitleString()
                    .compareToIgnoreCase(renderer1.getTitleString());
        }
    }

    /**
     * Creates a new SourcesRenderer.
     *
     * @param root the root
     * @param renderingContext the rendering context
     */
    public SourcesRenderer(final Root root,
            final RenderingContext renderingContext) {
        super(root, new GedRendererFactory(), renderingContext);
    }

    /**
     * Gets the sources.
     *
     * @return the sources
     */
    public Collection<SourceRenderer> getSources() {
        log.info("Starting getSources");
        final Collection<Source> sources = getGedObject().getFinder()
            .find(getGedObject(), Source.class);
        return sources.stream()
            .map(source -> (SourceRenderer) createGedRenderer(source))
            .sorted(new SourceRendererComparator())
            .toList();
    }
}
