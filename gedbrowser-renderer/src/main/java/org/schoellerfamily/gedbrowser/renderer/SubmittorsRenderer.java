package org.schoellerfamily.gedbrowser.renderer;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;

/**
 * @author Dick Schoeller
 */
public class SubmittorsRenderer extends GedRenderer<Root>
        implements IndexHrefRenderer<Root>, HeaderHrefRenderer<Root>,
            SubmittorsHrefRenderer<Root> {
    /** Logger. */
    private final Log logger = LogFactory.getLog(getClass());

    /**
     * Constructor.
     *
     * @param root root of data set
     * @param renderingContext the context that we are rendering in
     */
    public SubmittorsRenderer(final Root root,
            final RenderingContext renderingContext) {
        super(root, new GedRendererFactory(), renderingContext);
    }

    /**
     * @return the collection of submittors
     */
    public Collection<SubmittorRenderer> getSubmittors() {
        logger.info("Starting getSubmittors");
        final Collection<Submittor> submittors = getGedObject().getFinder()
                .find(getGedObject(), Submittor.class);
        final Collection<SubmittorRenderer> renderers = new ArrayList<>();
        for (final Submittor submittor : submittors) {
            renderers.add((SubmittorRenderer) createGedRenderer(submittor));
        }
        return renderers;
    }
}
