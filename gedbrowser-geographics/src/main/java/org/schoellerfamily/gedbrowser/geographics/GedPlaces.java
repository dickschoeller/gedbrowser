package org.schoellerfamily.gedbrowser.geographics;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Place;

/**
 * @author Dick Schoeller
 */
public abstract class GedPlaces {
    /**
     * Recurse through this object and its children to find places to report.
     *
     * @param gedobject the object whose places we are going to find
     * @return the collection of places found
     */
    protected final Collection<Place> getPlaces(final GedObject gedobject) {
        Set<Place> places = new TreeSet<>();
        if (gedobject instanceof Place) {
            places.add(clonePlace((Place) gedobject));
        } else if (gedobject != null) {
            // TODO the null check should be unnecessary.
            // Something putting nulls in some lists.
            List<GedObject> attributes = gedobject.getAttributes();
            for (GedObject attribute : attributes) {
                places.addAll(getPlaces(attribute));
            }
        }
        return places;
    }

    /**
     * Create a copy of a place without the parent information. This allows us
     * to use equals as defined for GedObjects.
     *
     * @param place
     *            the place to copy
     * @return the copy
     */
    protected final Place clonePlace(final Place place) {
        Place newPlace = new Place(null, place.getString());
        return newPlace;
    }
}
