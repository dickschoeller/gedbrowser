package org.schoellerfamily.gedbrowser.renderer.href;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * Renders places href output for display.
 *
 * @author Richard Schoeller
 * @param <T> the GedObject type to render
 */
public interface PlacesHrefRenderer<T extends GedObject> {
    /**
     * @return the GedObject
     */
    T getGedObject();

    /**
     * @return the href string to the places page.
     */
    default String getPlacesHref() {
        return "places?db=" + getGedObject().getDbName();
    }
}
