package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.SubmitterLink;

/**
 * @author Dick Schoeller
 */
public final class SubmitterLinkNameHtmlRenderer implements NameHtmlRenderer {
    /**
     * Holder for the SubmitterLinkRenderer that is using this helper.
     */
    private final transient SubmitterLinkRenderer submitterLinkRenderer;

    /**
     * Constructor.
     *
     * @param submitterLinkRenderer the renderer that is using this helper.
     */
    protected SubmitterLinkNameHtmlRenderer(
            final SubmitterLinkRenderer submitterLinkRenderer) {
        this.submitterLinkRenderer = submitterLinkRenderer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNameHtml() {
        final SubmitterLink submitterLink = submitterLinkRenderer
                .getGedObject();
        if (!submitterLink.isSet()) {
            return "";
        }
        final Submitter submitter =
                (Submitter) submitterLink.find(submitterLink.getToString());
        final GedRenderer<? extends GedObject> renderer =
                new SimpleNameRenderer(submitter.getName(),
                        submitterLinkRenderer.getRendererFactory(),
                        submitterLinkRenderer.getRenderingContext());
        final String nameHtml = renderer.getNameHtml();

        return "<a class=\"name\" href=\"submitter?db="
                + submitterLink.getDbName() + "&amp;id="
                + submitterLink.getToString() + "\">" + nameHtml
                + " [" + submitterLink.getToString() + "]" + "</a>";
    }
}
