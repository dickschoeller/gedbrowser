package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;

/**
 * Renders submitter name html output for display.
 *
 * @author Richard Schoeller
 */
public class SubmitterNameHtmlRenderer implements NameHtmlRenderer {
    /** */
    private final transient SubmitterRenderer submitterRenderer;

    /**
     * Creates a new SubmitterNameHtmlRenderer.
     *
     * @param submitterRenderer the submitter renderer
     */
    public SubmitterNameHtmlRenderer(
            final SubmitterRenderer submitterRenderer) {
        this.submitterRenderer = submitterRenderer;
    }

    /**
     * Returns the name html.
     *
     * @return the name html
     */
    @Override
    public String getNameHtml() {
        final Submitter submitter = submitterRenderer.getGedObject();
        if (!submitter.isSet()) {
            return "";
        }
        final GedRenderer<? extends GedObject> renderer =
                new SimpleNameRenderer(submitter.getName(),
                        submitterRenderer.getRendererFactory(),
                        submitterRenderer.getRenderingContext());
        return renderer.getNameHtml();
    }

}
