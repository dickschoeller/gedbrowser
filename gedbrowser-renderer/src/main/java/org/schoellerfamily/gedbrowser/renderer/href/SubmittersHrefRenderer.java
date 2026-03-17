package org.schoellerfamily.gedbrowser.renderer.href;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * Renders submitters href output for display.
 *
 * @author Richard Schoeller
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
