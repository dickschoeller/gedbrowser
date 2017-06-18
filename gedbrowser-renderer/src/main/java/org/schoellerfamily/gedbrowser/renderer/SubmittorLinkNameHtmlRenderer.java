package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.datamodel.SubmittorLink;

/**
 * @author Dick Schoeller
 */
public final class SubmittorLinkNameHtmlRenderer implements NameHtmlRenderer {
    /**
     * Holder for the SubmittorLinkRenderer that is using this helper.
     */
    private final transient SubmittorLinkRenderer submittorLinkRenderer;

    /**
     * Constructor.
     *
     * @param submittorLinkRenderer the renderer that is using this helper.
     */
    protected SubmittorLinkNameHtmlRenderer(
            final SubmittorLinkRenderer submittorLinkRenderer) {
        this.submittorLinkRenderer = submittorLinkRenderer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNameHtml() {
        final SubmittorLink submittorLink = submittorLinkRenderer
                .getGedObject();
        if (!submittorLink.isSet()) {
            return "";
        }
        final Submittor submittor =
                (Submittor) submittorLink.find(submittorLink.getToString());
        final GedRenderer<? extends GedObject> renderer =
                submittorLinkRenderer.createGedRenderer(submittor.getName());
        final String nameHtml = renderer.getNameHtml();

        return "<a class=\"name\" href=\"submittor?db="
                + submittorLink.getDbName() + "&amp;id="
                + submittorLink.getToString() + "\">" + nameHtml
                + " [" + submittorLink.getToString() + "]" + "</a>";
    }
}
