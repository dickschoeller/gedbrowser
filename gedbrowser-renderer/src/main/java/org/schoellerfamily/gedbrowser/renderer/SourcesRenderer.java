package org.schoellerfamily.gedbrowser.renderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;

/**
 * @author Dick Schoeller
 */
public class SourcesRenderer extends GedRenderer<Root>
        implements IndexHrefRenderer<Root>, HeaderHrefRenderer<Root>,
        SubmittorsHrefRenderer<Root>, SourcesHrefRenderer<Root> {
    /** Logger. */
    private final Log logger = LogFactory.getLog(getClass());

    /**
     * Constructor.
     *
     * @param root root of data set
     * @param renderingContext the context that we are rendering in
     */
    public SourcesRenderer(final Root root,
            final RenderingContext renderingContext) {
        super(root, new GedRendererFactory(), renderingContext);
    }

    /**
     * @return the collection of source renderers
     */
    public Collection<SourceRenderer> getSources() {
        logger.info("Starting getSources");
        final Collection<Source> sources = getGedObject().getFinder()
                .find(getGedObject(), Source.class);
        final List<SourceRenderer> renderers = new ArrayList<>();
        for (final Source source : sources) {
            renderers.add((SourceRenderer) createGedRenderer(source));
        }
        renderers.sort(new Comparator<SourceRenderer>() {
            /**
             * {@inheritDoc}
             */
            @Override
            public int compare(final SourceRenderer renderer0,
                    final SourceRenderer renderer1) {
                return renderer0.getTitleString()
                        .compareToIgnoreCase(renderer1.getTitleString());
            }
        });
        return renderers;
    }
}
