package org.schoellerfamily.gedbrowser.renderer;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * @author Dick Schoeller
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
    GedRenderer<? extends GedObject> createGedRenderer(GedObject attribute);

    /**
     * Return the list of renderers that can be rendered in a list format.
     *
     * @return the list of attribute renderers.
     */
    default List<GedRenderer<?>> getAttributes() {
        final List<GedRenderer<?>> list = new ArrayList<GedRenderer<?>>();
        final T gob = getGedObject();
        for (final GedObject attribute : gob.getAttributes()) {
            final GedRenderer<?> renderer = createGedRenderer(attribute);
            if (!renderer.getListItemContents().isEmpty()) {
                list.add(renderer);
            }
        }
        return list;
    }

}
