package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.navigator.PersonNavigator;

/**
 * @author Dick Schoeller
 */
public class ParentsRenderer {
    /** */
    private final PersonRenderer personRenderer;

    /** */
    private final PersonNavigator navigator;

    /**
     * Constructor.
     *
     * @param personRenderer the containing renderer
     */
    public ParentsRenderer(final PersonRenderer personRenderer) {
        this.personRenderer = personRenderer;
        navigator = new PersonNavigator(personRenderer.getGedObject());
    }

    /**
     * This method is public for testing purposes only. Do not try to call it
     * outside of the context of the rendering engine.
     *
     * @param builder Buffer for holding the rendition
     * @param pad Minimum number spaces for padding each line of the output
     * @param father Father
     */
    public void renderFather(final StringBuilder builder,
            final int pad, final Person father) {
        renderParent(builder, pad, father, "Father");
    }

    /**
     * This method is public for testing purposes only. Do not try to call it
     * outside of the context of the rendering engine.
     *
     * @param builder Buffer for holding the rendition
     * @param pad Minimum number spaces for padding each line of the output
     * @param mother Mother
     */
    public void renderMother(final StringBuilder builder,
            final int pad, final Person mother) {
        renderParent(builder, pad, mother, "Mother");
    }

    /**
     * @param builder Buffer for holding the rendition
     * @param pad Minimum number spaces for padding each line of the output
     * @param parent The parent being rendered
     * @param parentLabel The string containing the parent type
     */
    private void renderParent(final StringBuilder builder,
            final int pad, final Person parent, final String parentLabel) {
        if (parent != null) {
            final String parentHtml = createGedRenderer(parent).getNameHtml();
            renderParent(builder, pad, parentHtml, parentLabel);
        }
    }

    /**
     * @param builder Buffer for holding the rendition
     * @param pad Minimum number spaces for padding each line of the output
     * @param parentHtml The parent being rendered
     * @param parentLabel The string containing the parent type
     */
    private void renderParent(final StringBuilder builder,
            final int pad, final String parentHtml, final String parentLabel) {
        if (!parentHtml.isEmpty() && (isConfidential() || isHiddenLiving())) {
            return;
        }
        GedRenderer.renderPad(builder, pad, true);
        builder.append("<p class=\"parent\">");

        GedRenderer.renderPad(builder, pad, true);
        builder.append(
                " <span class=\"parent label\">" + parentLabel
                + ":</span> ").append(
                        parentHtml);

        GedRenderer.renderPad(builder, pad, true);
        builder.append("</p>");
    }

    /**
     * @return the father string.
     */
    public String getFatherRendition() {
        final StringBuilder builder = new StringBuilder();
        final Person father = navigator.getFather();
        if (father != null) {
            renderParent(builder, 0,
                    createGedRenderer(father).getNameHtml(), "Father");
        }
        return builder.toString();
    }

    /**
     * @return the mother string
     */
    public String getMotherRendition() {
        final StringBuilder builder = new StringBuilder();
        final Person mother = navigator.getMother();
        if (mother != null) {
            renderParent(builder, 0,
                    createGedRenderer(mother).getNameHtml(), "Mother");
        }
        return builder.toString();
    }

    /**
     * @return the HTML string format of the father's name.
     */
    public String getFatherNameHtml() {
        if (isConfidential()) {
            return "";
        }
        if (isHiddenLiving()) {
            return "";
        }
        return createGedRenderer(navigator.getFather()).getNameHtml();
    }

    /**
     * @return the HTML string format of the mother's name.
     */
    public String getMotherNameHtml() {
        if (isConfidential()) {
            return "";
        }
        if (isHiddenLiving()) {
            return "";
        }
        return createGedRenderer(navigator.getMother()).getNameHtml();
    }

    /**
     * @param parent the parent that we are creating a renderer for
     * @return the renderer
     */
    private GedRenderer<? extends GedObject> createGedRenderer(
            final Person parent) {
        return personRenderer.createGedRenderer(parent);
    }

    /**
     * @return true if the person is hidden because confidential
     */
    private boolean isConfidential() {
        return personRenderer.isConfidential();
    }

    /**
     * @return true if the person is hidden because living
     */
    private boolean isHiddenLiving() {
        return personRenderer.isHiddenLiving();
    }
}
