package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;

/**
 * @author Dick Schoeller
 */
public class SubmitterNameIndexRenderer implements NameIndexRenderer {
    /** */
    private final transient SubmitterRenderer submitterRenderer;

    /**
     * Creates a new SubmitterNameIndexRenderer.
     *
     * @param submitterRenderer the submitter renderer
     */
    public SubmitterNameIndexRenderer(
            final SubmitterRenderer submitterRenderer) {
        this.submitterRenderer = submitterRenderer;
    }

    /**
     * Returns the index name.
     *
     * @return the index name
     */
    @Override
    public String getIndexName() {
        final Submitter submitter = submitterRenderer.getGedObject();
        if (!submitter.isSet()) {
            return "";
        }
        final String nameHtml = getNameHtml(submitter);

        return "<a class=\"name\" href=\"submitter?db=" + submitter.getDbName()
                + "&amp;id=" + submitter.getString() + "\">" + nameHtml + " ["
                + submitter.getString() + "]" + "</a>";
    }

    private String getNameHtml(final Submitter submitter) {
        final GedRenderer<? extends GedObject> renderer =
                new SimpleNameRenderer(submitter.getName(),
                        submitterRenderer.getRendererFactory(),
                        submitterRenderer.getRenderingContext());
        return renderer.getNameHtml();
    }
}
