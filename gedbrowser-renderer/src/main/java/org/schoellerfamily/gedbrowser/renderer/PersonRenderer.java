package org.schoellerfamily.gedbrowser.renderer;

import java.util.List;

import org.schoellerfamily.gedbrowser.analytics.LivingEstimator;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.navigator.PersonNavigator;
import org.schoellerfamily.gedbrowser.datamodel.visitor.PersonVisitor;
import org.schoellerfamily.gedbrowser.renderer.href.HeaderHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.IndexHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.PlacesHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SaveHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SourcesHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SubmittersHrefRenderer;

/**
 * Renders person output for display.
 *
 * @author Richard Schoeller
 */
public final class PersonRenderer extends GedRenderer<Person>
        implements HeaderHrefRenderer<Person>, IndexHrefRenderer<Person>,
        PlacesHrefRenderer<Person>, PersonLifeSpanRenderer, PersonNameRenderer,
        SaveHrefRenderer<Person>, SourcesHrefRenderer<Person>,
        SubmittersHrefRenderer<Person> {
    /**
     * Connection to helper to estimate whether this person is living.
     */
    private final LivingEstimator le;

    /** */
    private final PersonNavigator navigator;

    /** */
    private final ParentsRenderer parentsRenderer;

    /**
     * Creates a new PersonRenderer.
     *
     * @param gedObject the ged object
     * @param rendererFactory the renderer factory
     * @param renderingContext the rendering context
     */
    public PersonRenderer(final Person gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
        setNameHtmlRenderer(new PersonNameHtmlRenderer(this));
        setNameIndexRenderer(new PersonNameIndexRenderer(this));
        setAttributeListOpenRenderer(new PersonAttributeListOpenRenderer());
        le = new LivingEstimator(gedObject, renderingContext);
        navigator = new PersonNavigator(gedObject);
        parentsRenderer = new ParentsRenderer(this);
    }

    /**
     * Get the renderer responsible for rendering the parents of this person.
     *
     * @return the parents renderer
     */
    public ParentsRenderer getParents() {
        return parentsRenderer;
    }

    /**
     * Gets the id string.
     *
     * @return the id string
     */
    public String getIdString() {
        return getGedObject().getString();
    }

    /**
     * Gets the families.
     *
     * @return the families
     */
    public List<FamilyRenderer> getFamilies() {
        if (isConfidential() || isHiddenLiving()) {
            return List.of();
        }

        final List<Family> families = navigator.getFamilies();
        return families.stream()
            .map(family -> (FamilyRenderer) createGedRenderer(family))
            .toList();
    }

    /**
     * Return the list of renderers that can be rendered in a list format.
     *
     * @return the list of attribute renderers.
     */
    @SuppressWarnings({ "unchecked" })
    public List<GedRenderer<GedObject>> getAttributes() {
        if (isConfidential() || isHiddenLiving()) {
            return List.of();
        }
        final Person person = getGedObject();
        return person.getAttributes().stream()
            .map(a -> (GedRenderer<GedObject>) createGedRenderer(a))
            .filter(renderer -> !renderer.getListItemContents().isEmpty())
            .toList();
    }

    /**
     * Returns the tree rows.
     *
     * @param generations the generations
     * @return the tree rows
     */
    public CellRow[] getTreeRows(final int generations) {
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
     * Checks whether confidential.
     *
     * @return true if the condition is met; otherwise false
     */
    public boolean isConfidential() {
        if (getRenderingContext().isAdmin()) {
            return false;
        }
        final PersonVisitor visitor = new PersonVisitor();
        getGedObject().accept(visitor);
        return visitor.isConfidential();
    }

    /**
     * Checks whether hidden living.
     *
     * @return true if the condition is met; otherwise false
     */
    public boolean isHiddenLiving() {
        return !getRenderingContext().isUser() && le.estimate();
    }

    /**
     * Gets the index href.
     *
     * @return the index href
     */
    @Override
    public String getIndexHref() {
        final String surnameLetter = getSurnameLetter();
        final String surname = getSurname();
        return "surnames?db=" + getGedObject().getDbName() + "&letter="
                + surnameLetter + "#" + surname;
    }
}
