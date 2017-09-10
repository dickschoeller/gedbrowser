package org.schoellerfamily.gedbrowser.renderer.href;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * Provides the pattern of returning the href to the header page.
 *
 * @author Dick Schoeller
 * @param <T> the GedObject type to render
 */
public interface HeaderHrefRenderer<T extends GedObject> {
    /**
     * @return the GedObject
     */
    T getGedObject();


    /**
     * @return the href string to the index page containing this person.
     */
    default String getHeaderHref() {
        return "head?db=" + getGedObject().getDbName();
    }
}
