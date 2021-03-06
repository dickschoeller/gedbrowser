package org.schoellerfamily.gedbrowser.renderer;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.navigator.FamilyNavigator;

/**
 * Render an Family.
 *
 * @author Dick Schoeller
 */
public final class FamilyRenderer extends GedRenderer<Family>
        implements AttributesRenderer<Family> {
    /** */
    private static final int INDENT_INCREMENT = 2;

    /**
     * @param gedObject the Family that we are going to render.
     * @param rendererFactory the factory that creates the renderers for the
     *        attributes
     * @param renderingContext the context that we are rendering in
     */
    public FamilyRenderer(final Family gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
    }

    /**
     * @param personRenderer the person renderer whose page we are on
     * @return the spouses renderer.
     */
    public PersonRenderer getSpouse(
            final PersonRenderer personRenderer) {
        final Family family = getGedObject();
        final Person person = personRenderer.getGedObject();
        final Person spouse = (new FamilyNavigator(family)).getSpouse(person);
        return (PersonRenderer) createGedRenderer(spouse);
    }

    /**
     * @return the list of renderers for the children in this family.
     */
    public List<PersonRenderer> getChildren() {
        final Family family = getGedObject();
        final FamilyNavigator navigator = new FamilyNavigator(family);
        final List<Person> children = navigator.getChildren();
        final List<PersonRenderer> rendererList =
                new ArrayList<PersonRenderer>(children.size());
        for (final Person child : children) {
            final PersonRenderer personRenderer =
                    (PersonRenderer) createGedRenderer(child);
            rendererList.add(personRenderer);
        }
        return rendererList;
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
