package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;

/**
 * @author Dick Schoeller
 */
public class SubmittorNameHtmlRenderer implements NameHtmlRenderer {
    /** */
    private final transient SubmittorRenderer submittorRenderer;

    /**
     * Constructor.
     *
     * @param submittorRenderer the associated submittorRenderer
     */
    public SubmittorNameHtmlRenderer(
            final SubmittorRenderer submittorRenderer) {
        this.submittorRenderer = submittorRenderer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNameHtml() {
        final Submittor submittor = submittorRenderer.getGedObject();
        if (!submittor.isSet()) {
            return "";
        }
        final GedRenderer<? extends GedObject> renderer =
                submittorRenderer.createGedRenderer(submittor.getName());
        return renderer.getNameHtml();
    }

}
