package org.schoellerfamily.gedbrowser.renderer.href;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * Renders index href output for display.
 *
 * @author Richard Schoeller
 * @param <T> the GedObject type to render
 */
public interface IndexHrefRenderer<T extends GedObject> {
    /**
     * @return the GedObject
     */
    T getGedObject();

    /**
     * @return the href string to the index page containing this person.
     */
    default String getIndexHref() {
        return "surnames?db=" + getGedObject().getDbName() + "&letter=" + "A";
    }
}
