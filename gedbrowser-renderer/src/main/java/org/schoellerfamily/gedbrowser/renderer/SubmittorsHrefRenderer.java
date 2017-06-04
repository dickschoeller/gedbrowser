package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * Provides the pattern of returning the href to the submittors page.
 *
 * @author Dick Schoeller
 * @param <T> the GedObject type to render
 */
public interface SubmittorsHrefRenderer<T extends GedObject> {
    /**
     * @return the GedObject
     */
    T getGedObject();


    /**
     * @return the href string to the index page containing this person.
     */
    default String getSubmittorsHref() {
        return "submittors?db=" + getGedObject().getDbName();
    }
}
