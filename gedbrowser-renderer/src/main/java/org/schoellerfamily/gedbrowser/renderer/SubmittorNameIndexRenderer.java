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
        final String nameHtml = getNameHtml(submittor);

        return "<a class=\"name\" href=\"submittor?db=" + submittor.getDbName()
                + "&amp;id=" + submittor.getString() + "\">" + nameHtml + " ["
                + submittor.getString() + "]" + "</a>";
    }

    /**
     * Handle the messy getting of the name ready for HTML formatting.
     *
     * @param submittor the submittor whose name we are doing
     * @return the string
     */
    private String getNameHtml(final Submittor submittor) {
        final GedRenderer<? extends GedObject> renderer =
                new SimpleNameRenderer(submittor.getName(),
                        submittorRenderer.getRendererFactory(),
                        submittorRenderer.getRenderingContext());
        return renderer.getNameHtml();
    }
}
