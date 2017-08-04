package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;

/**
 * @author Dick Schoeller
 */
public class SubmitterNameHtmlRenderer implements NameHtmlRenderer {
    /** */
    private final transient SubmitterRenderer submitterRenderer;

    /**
     * Constructor.
     *
     * @param submitterRenderer the associated submitterRenderer
     */
    public SubmitterNameHtmlRenderer(
            final SubmitterRenderer submitterRenderer) {
        this.submitterRenderer = submitterRenderer;
    }

    /**
     * {@inheritDoc}
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
