package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * Provides the pattern of returning the href to the submitters page.
 *
 * @author Dick Schoeller
 * @param <T> the GedObject type to render
 */
public interface SubmittersHrefRenderer<T extends GedObject> {
    /**
     * @return the GedObject
     */
    T getGedObject();

    /**
     * @return the href string to the index page containing this person.
     */
    default String getSubmittersHref() {
        return "submitters?db=" + getGedObject().getDbName();
    }
}
