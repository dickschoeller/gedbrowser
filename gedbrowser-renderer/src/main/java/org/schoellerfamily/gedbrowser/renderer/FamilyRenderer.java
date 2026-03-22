package org.schoellerfamily.gedbrowser.renderer;

import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.navigator.FamilyNavigator;

/**
 * Renders family output for display.
 *
 * @author Richard Schoeller
 */
public final class FamilyRenderer extends GedRenderer<Family>
        implements AttributesRenderer<Family> {
    /** */
    private static final int INDENT_INCREMENT = 2;

    /**
     * Creates a new FamilyRenderer.
     *
     * @param gedObject the ged object
     * @param rendererFactory the renderer factory
     * @param renderingContext the rendering context
     */
    public FamilyRenderer(final Family gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
    }

    /**
     * Returns the spouse.
     *
     * @param personRenderer the person renderer
     * @return the spouse
     */
    public PersonRenderer getSpouse(final PersonRenderer personRenderer) {
        final Family family = getGedObject();
        final Person person = personRenderer.getGedObject();
        final Person spouse = (new FamilyNavigator(family)).getSpouse(person);
        return (PersonRenderer) createGedRenderer(spouse);
    }

    /**
     * Gets the children.
     *
     * @return the children
     */
    public List<PersonRenderer> getChildren() {
        final Family family = getGedObject();
        final FamilyNavigator navigator = new FamilyNavigator(family);
        final List<Person> children = navigator.getChildren();
        return children.stream()
            .map(this::createGedRenderer)
            .map(PersonRenderer.class::cast)
            .toList();
    }

    /**
     * Get the standard amount of indent for this construct.
     *
     * @return the increment.
     */
    protected int getIndentIncrement() {
        return INDENT_INCREMENT;
    }
}
