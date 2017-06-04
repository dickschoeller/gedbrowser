package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;

/**
 * @author Dick Schoeller
 */
public class SubmittorNameIndexRenderer implements NameIndexRenderer {
    /** */
    private final transient SubmittorRenderer submittorRenderer;

    /**
     * Constructor.
     *
     * @param submittorRenderer the associated submittorRenderer
     */
    public SubmittorNameIndexRenderer(
            final SubmittorRenderer submittorRenderer) {
        this.submittorRenderer = submittorRenderer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIndexName() {
        final Submittor submittor = submittorRenderer.getGedObject();
        if (!submittor.isSet()) {
            return "";
        }
        final GedRenderer<? extends GedObject> renderer =
                submittorRenderer.createGedRenderer(submittor.getName());
        final String nameHtml = renderer.getNameHtml();

        return "<a class=\"name\" href=\"submittor?db=" + submittor.getDbName()
                + "&amp;id=" + submittor.getString() + "\">" + nameHtml + " ["
                + submittor.getString() + "]" + "</a>";
    }
}
