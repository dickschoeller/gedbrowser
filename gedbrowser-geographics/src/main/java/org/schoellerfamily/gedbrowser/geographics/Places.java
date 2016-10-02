package org.schoellerfamily.gedbrowser.geographics;

import java.util.Collection;

import org.schoellerfamily.gedbrowser.datamodel.Place;

/**
 * All of the various finder collectors will implement this interface. What they
 * are getting places from will be a function of data type provided to the
 * classes' constructors.
 *
 * @author Dick Schoeller
 */
public interface Places {
    /**
     * Return a collection of unique places.
     *
     * @return the collection
     */
    Collection<Place> getPlaces();
}
