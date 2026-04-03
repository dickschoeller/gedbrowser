package org.schoellerfamily.gedbrowser.renderer.href;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * Renders save href output for display.
 *
 * @author Richard Schoeller
 * @param <T> the GedObject type to render
 */
public interface SaveHrefRenderer<T extends GedObject> {
    /**
     * @return the GedObject
     */
    T getGedObject();

    /**
     * @return the href string to the index page containing this person.
     */
    default String getSaveHref() {
        return "save?db=" + getGedObject().getDbName();
    }

    /**
     * @return the href string with the base filename.
     */
    default String getSaveFilename() {
        final String filename = getGedObject().getFilename();
        final int lastIndexOf = filename.lastIndexOf("/");
        if (lastIndexOf == -1) {
            return filename;
        }
        return filename.substring(lastIndexOf + 1);
    }
}
