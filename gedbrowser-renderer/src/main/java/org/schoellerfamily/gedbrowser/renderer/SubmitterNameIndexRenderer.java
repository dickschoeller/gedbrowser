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
     * Constructor.
     *
     * @param submitterRenderer the associated submitterRenderer
     */
    public SubmitterNameIndexRenderer(
            final SubmitterRenderer submitterRenderer) {
        this.submitterRenderer = submitterRenderer;
    }

    /**
     * {@inheritDoc}
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

    /**
     * Handle the messy getting of the name ready for HTML formatting.
     *
     * @param submitter the submitter whose name we are doing
     * @return the string
     */
    private String getNameHtml(final Submitter submitter) {
        final GedRenderer<? extends GedObject> renderer =
                new SimpleNameRenderer(submitter.getName(),
                        submitterRenderer.getRendererFactory(),
                        submitterRenderer.getRenderingContext());
        return renderer.getNameHtml();
    }
}
