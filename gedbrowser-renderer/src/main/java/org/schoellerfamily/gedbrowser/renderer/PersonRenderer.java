package org.schoellerfamily.gedbrowser.renderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.schoellerfamily.gedbrowser.analytics.LivingEstimator;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.Person;

/**
 * Render a Person.
 *
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.GodClass" })
public final class PersonRenderer extends GedRenderer<Person> {
    /**
     * Number of generations, including the root when rendering the tree.
     */
    private static final int TREE_GENERATIONS = 5;

    /**
     * Connection to helper to estimate whether this person is living.
     */
    private final LivingEstimator le;

    /**
     * @param gedObject the Person that we are going to render
     * @param rendererFactory the factory that creates the renderers for the
     *        attributes
     * @param renderingContext the user context we are rendering in
     */
    public PersonRenderer(final Person gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
        setNameHtmlRenderer(new PersonNameHtmlRenderer(this));
        setNameIndexRenderer(new PersonNameIndexRenderer(this));
        setAttributeListOpenRenderer(new PersonAttributeListOpenRenderer());
        le = new LivingEstimator(gedObject);
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
        renderPad(builder, pad, true);
        builder.append("<p class=\"parent\">");

        renderPad(builder, pad, true);
        builder.append(
                " <span class=\"parent label\">" + parentLabel
                + ":</span> ").append(
                        parentHtml);

        renderPad(builder, pad, true);
        builder.append("</p>");
    }

    /**
     * @return the name string in title format.
     */
    public String getTitleName() {
        final Name name = getGedObject().getName();
        if (isConfidential()) {
            return "Confidential";
        } else if (isHiddenLiving()) {
            return "Living";
        } else if (name == null) {
            return "? ?";
        } else {
            final GedRenderer<?> nameRenderer = createGedRenderer(name);
            return nameRenderer.getNameHtml();
        }
    }

    /**
     * @return lifespan string.
     */
    public String getLifeSpanString() {
        if (isConfidential() || isHiddenLiving()) {
            return "";
        }

        // Get some of used strings.
        final String birthDate = getGedObject().getBirthDate();
        final String deathDate = getGedObject().getDeathDate();
        return "(" + birthDate + "-" + deathDate + ")";
    }

    /**
     * @return the ID string of the person.
     */
    public String getIdString() {
        return getGedObject().getString();
    }

    /**
     * @return Get the whole name without markup.
     */
    public String getWholeName() {
        if (isConfidential()) {
            return "Confidential";
        } else if (isHiddenLiving()) {
            return "Living";
        }
        final Name name = getGedObject().getName();
        if (name == null) {
            return "? ?";
        }

        final String prefix = GedRenderer.escapeString(name.getPrefix());
        final String surname = GedRenderer.escapeString(name.getSurname());
        final String suffix = GedRenderer.escapeString(name.getSuffix());

        final StringBuilder builder = new StringBuilder();
        builder.append(prefix).append(' ');
        builder.append(surname);
        if (!suffix.isEmpty()) {
            builder.append(' ').append(suffix);
        }

        return builder.toString();
    }

    /**
     * @return the father string.
     */
    public String getFatherRendition() {
        final StringBuilder builder = new StringBuilder();
        final Person father = getGedObject().getFather();
        if (father != null) {
            renderParent(builder, 0, createGedRenderer(father).getNameHtml(),
                    "Father");
        }
        return builder.toString();
    }

    /**
     * @return the mother string
     */
    public String getMotherRendition() {
        final StringBuilder builder = new StringBuilder();
        final Person mother = getGedObject().getMother();
        if (mother != null) {
            renderParent(builder, 0, createGedRenderer(mother).getNameHtml(),
                    "Mother");
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
        return createGedRenderer(getGedObject().getFather()).getNameHtml();
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
        return createGedRenderer(getGedObject().getMother()).getNameHtml();
    }

    /**
     * @return the list of renderers for the families of the person.
     */
    public List<FamilyRenderer> getFamilies() {
        if (isConfidential() || isHiddenLiving()) {
            return Collections.emptyList();
        }

        final List<Family> families = getGedObject().getFamilies(
                new ArrayList<Family>());
        final List<FamilyRenderer> rendererList =
                new ArrayList<FamilyRenderer>(families.size());

        for (final Family family : families) {
            final FamilyRenderer familyRenderer =
                    (FamilyRenderer) createGedRenderer(family);
            rendererList.add(familyRenderer);
        }
        return rendererList;
    }

    /**
     * Return the list of renderers that can be rendered in a list format.
     *
     * @return the list of attribute renderers.
     */
    public List<GedRenderer<?>> getAttributes() {
        final List<GedRenderer<?>> rendererList =
                new ArrayList<GedRenderer<?>>();
        if (isConfidential() || isHiddenLiving()) {
            return rendererList;
        }
        final Person person = getGedObject();
        for (final GedObject attribute : person.getAttributes()) {
            final GedRenderer<?> attributeRenderer =
                    createGedRenderer(attribute);
            if (!attributeRenderer.getListItemContents().isEmpty()) {
                rendererList.add(attributeRenderer);
            }
        }
        return rendererList;
    }

    /**
     * @return the 2D array of cells.
     */
    public CellRow[] getTreeRows() {
        final TreeTableRenderer ttr = new TreeTableRenderer(this,
                confidentialGenCount(TREE_GENERATIONS));
        return ttr.getTreeRows();
    }

    /**
     * @param generations the number of generations to diplay.
     * @return the 2D array of cells.
     */
    public CellRow[] getTreeRows(final int generations) {
        // TODO this is the one that is actually used.
        final TreeTableRenderer ttr = new TreeTableRenderer(this,
                confidentialGenCount(generations));
        return ttr.getTreeRows();
    }

    /**
     * @param generations default number of generations
     * @return actual number of generations
     */
    private int confidentialGenCount(final int generations) {
        if (isConfidential() || isHiddenLiving()) {
            return 1;
        } else {
            return generations;
        }
    }

    /**
     * @return whether the current person is confidential.
     */
    public boolean isConfidential() {
        if (getRenderingContext().isAdmin()) {
            return false;
        }
        return getGedObject().isConfidential();
    }

    /**
     * @return whether the current person is confidential.
     */
    public boolean isHiddenLiving() {
        if (getRenderingContext().isUser()) {
            return false;
        }
        return le.estimate();
    }

    /**
     * @return the href string to the index page containing this person.
     */
    public String getIndexHref() {
        final String surnameLetter = getSurnameLetter();
        final String surname = getSurname();
        return "surnames?db=" + getGedObject().getDbName() + "&letter="
                + surnameLetter + "#" + surname;
    }

    /**
     * @return surname if not confidential
     */
    public String getSurname() {
        if (isConfidential() || isHiddenLiving()) {
            return "?";
        } else {
            return getGedObject().getSurname();
        }
    }

    /**
     * @return surname first letter if not confidential
     */
    public String getSurnameLetter() {
        if (isConfidential() || isHiddenLiving()) {
            return "?";
        } else {
            return getGedObject().getSurnameLetter();
        }
    }
}
