package org.schoellerfamily.gedbrowser.renderer;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * Renders attributes output for display.
 *
 * @author Richard Schoeller
 *
 * @param <T> the data type being rendered
 */
public interface AttributesRenderer<T extends GedObject> {
    /**
     * @return the gedobject necessary to render the attributes
     */
    T getGedObject();

    /**
     * @param attribute a gedobject from the attribute list
     * @return the renderer for that object
     */
    @SuppressWarnings("java:S1452")
    GedRenderer<? extends GedObject> createGedRenderer(GedObject attribute);

    /**
     * Return the list of renderers that can be rendered in a list format.
     *
     * @return the list of attribute renderers.
     */
    // Suppressed warnings are because of issues related to generics.
    @SuppressWarnings({ "java:S1452", "java:S6204" })
    default List<GedRenderer<?>> getAttributes() {
        return getGedObject().getAttributes().stream()
            .map(attribute -> (GedRenderer<?>) createGedRenderer(attribute))
            .filter(renderer -> StringUtils.isNotEmpty(renderer.getListItemContents()))
            .collect(Collectors.toUnmodifiableList());
    }

}
