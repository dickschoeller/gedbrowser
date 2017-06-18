package org.schoellerfamily.gedbrowser.renderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.schoellerfamily.gedbrowser.analytics.LivingEstimator;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.navigator.PersonNavigator;
import org.schoellerfamily.gedbrowser.datamodel.visitor.PersonVisitor;

/**
 * Render a Person.
 *
 * @author Dick Schoeller
 */
public final class PersonRenderer extends GedRenderer<Person>
        implements IndexHrefRenderer<Person>, HeaderHrefRenderer<Person>,
        SubmittorsHrefRenderer<Person>, SourcesHrefRenderer<Person>,
        PersonNameRenderer, PersonLifeSpanRenderer {
    /**
     * Connection to helper to estimate whether this person is living.
     */
    private final LivingEstimator le;

    /** */
    private final PersonNavigator navigator;

    /** */
    private final ParentsRenderer parentsRenderer;

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
     * @return the ID string of the person.
     */
    public String getIdString() {
        return getGedObject().getString();
    }

    /**
     * @return the list of renderers for the families of the person.
     */
    public List<FamilyRenderer> getFamilies() {
        if (isConfidential() || isHiddenLiving()) {
            return Collections.emptyList();
        }

        final List<Family> families = navigator.getFamilies();
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
     * @param generations the number of generations to display.
     * @return the 2D array of cells.
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
     * @return whether the current person is confidential.
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
}
