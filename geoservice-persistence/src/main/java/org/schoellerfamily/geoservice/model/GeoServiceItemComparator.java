package org.schoellerfamily.geoservice.model;

import java.util.Comparator;

/**
 * Sort geoservice items by modern place name. Consider doing a sort
 * that includes country, state, etc.
 *
 * @author Dick Schoeller
 */
public class GeoServiceItemComparator implements Comparator<GeoServiceItem> {
    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(final GeoServiceItem o1, final GeoServiceItem o2) {
        return o1.getModernPlaceName().compareTo(o2.getModernPlaceName());
    }
}
