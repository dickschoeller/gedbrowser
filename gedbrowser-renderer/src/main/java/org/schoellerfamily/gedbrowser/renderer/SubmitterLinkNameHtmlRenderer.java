package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.SubmitterLink;

import lombok.RequiredArgsConstructor;

/**
 * Renders submitter link name html output for display.
 *
 * @author Richard Schoeller
 */
@RequiredArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public final class SubmitterLinkNameHtmlRenderer implements NameHtmlRenderer {
    /**
     * Holder for the SubmitterLinkRenderer that is using this helper.
     */
    private final SubmitterLinkRenderer submitterLinkRenderer;

    /**
     * Returns the name html.
     *
     * @return the name html
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
