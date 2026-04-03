package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;

import lombok.RequiredArgsConstructor;

/**
 * Renders submitter name html output for display.
 *
 * @author Richard Schoeller
 */
@RequiredArgsConstructor
public class SubmitterNameHtmlRenderer implements NameHtmlRenderer {
    /** */
    private final SubmitterRenderer submitterRenderer;

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
