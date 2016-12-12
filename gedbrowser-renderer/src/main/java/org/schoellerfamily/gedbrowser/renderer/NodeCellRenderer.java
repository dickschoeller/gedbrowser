package org.schoellerfamily.gedbrowser.renderer;

/**
 * Cell for a person in the table that represents the ancestor
 * tree.
 *
 * @author Dick Schoeller
 */
public final class NodeCellRenderer implements CellRenderer {
    /** */
    private final transient PersonRenderer personRenderer;

    /**
     * Constructor.
     *
     * @param personRenderer the render associated with this location.
     */
    public NodeCellRenderer(final PersonRenderer personRenderer) {
        this.personRenderer = personRenderer;
    }

    /**
     * @return the person renderer.
     */
    public PersonRenderer getPersonRenderer() {
        return personRenderer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNameHtml() {
        String nameHtml = personRenderer.getNameHtml();
        if (nameHtml == null || nameHtml.equals("")) {
            nameHtml = "&nbsp;&nbsp;&nbsp;";
        }
        return "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
                + nameHtml + "</td></tr></table>";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCellClass() {
        return "";
    }
}
