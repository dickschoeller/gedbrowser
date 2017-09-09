package org.schoellerfamily.gedbrowser.renderer;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.renderer.href.HeaderHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.IndexHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.PlacesHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SaveHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SourcesHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SubmittersHrefRenderer;

/**
 * @author Dick Schoeller
 */
public class SubmittersRenderer extends GedRenderer<Root>
        implements HeaderHrefRenderer<Root>, IndexHrefRenderer<Root>,
        PlacesHrefRenderer<Root>, SaveHrefRenderer<Root>,
        SourcesHrefRenderer<Root>, SubmittersHrefRenderer<Root> {
    /** Logger. */
    private final Log logger = LogFactory.getLog(getClass());

    /**
     * Constructor.
     *
     * @param root root of data set
     * @param renderingContext the context that we are rendering in
     */
    public SubmittersRenderer(final Root root,
            final RenderingContext renderingContext) {
        super(root, new GedRendererFactory(), renderingContext);
    }

    /**
     * @return the collection of submitters
     */
    public Collection<SubmitterRenderer> getSubmitters() {
        logger.info("Starting getSubmitters");
        final Collection<Submitter> submitters = getGedObject().getFinder()
                .find(getGedObject(), Submitter.class);
        final Collection<SubmitterRenderer> renderers = new ArrayList<>();
        for (final Submitter submitter : submitters) {
            renderers.add((SubmitterRenderer) createGedRenderer(submitter));
        }
        return renderers;
    }
}
