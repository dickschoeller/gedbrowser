package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * Provides the pattern of returning an index query. The default goes to the
 * letter A.
 *
 * @author Dick Schoeller
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
