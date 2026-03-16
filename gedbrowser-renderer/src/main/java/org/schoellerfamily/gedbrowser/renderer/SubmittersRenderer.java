package org.schoellerfamily.gedbrowser.renderer;

import java.util.Collection;

import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.renderer.href.HeaderHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.IndexHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.PlacesHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SaveHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SourcesHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SubmittersHrefRenderer;

import lombok.extern.slf4j.Slf4j;



/**
 * Renders submitters output for display.
 *
 * @author Richard Schoeller
 */
@Slf4j
public class SubmittersRenderer extends GedRenderer<Root>
        implements HeaderHrefRenderer<Root>, IndexHrefRenderer<Root>,
        PlacesHrefRenderer<Root>, SaveHrefRenderer<Root>,
        SourcesHrefRenderer<Root>, SubmittersHrefRenderer<Root> {

    /**
     * Creates a new SubmittersRenderer.
     *
     * @param root the root
     * @param renderingContext the rendering context
     */
    public SubmittersRenderer(final Root root,
            final RenderingContext renderingContext) {
        super(root, new GedRendererFactory(), renderingContext);
    }

    /**
     * Gets the submitters.
     *
     * @return the submitters
     */
    public Collection<SubmitterRenderer> getSubmitters() {
        log.info("Starting getSubmitters");
        final Collection<Submitter> submitters = getGedObject().getFinder()
            .find(getGedObject(), Submitter.class);
        return submitters.stream()
            .map(submitter -> (SubmitterRenderer) createGedRenderer(submitter))
            .toList();
    }
}
