package org.schoellerfamily.gedbrowser.geographics;

import java.util.Collection;

import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.visitor.PlaceVisitor;

/**
 * Report the places for a data set.
 *
 * @author Dick Schoeller
 */
public class RootPlaces implements Places {
    /**
     * The root object of the data set to report.
     */
    private final Root root;

    /**
     * Constructor.
     *
     * @param root the root object of the data set to report
     */
    public RootPlaces(final Root root) {
        this.root = root;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Collection<Place> getPlaces() {
        final PlaceVisitor visitor = new PlaceVisitor();
        root.accept(visitor);
        return visitor.getPlaces();
    }
}
