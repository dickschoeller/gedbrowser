package org.schoellerfamily.gedbrowser.renderer;

import java.util.Comparator;

/**
 * Sort person renderers by index name.
 *
 * @author Dick Schoeller
 */
public class PersonRendererComparator implements Comparator<PersonRenderer> {
    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(final PersonRenderer arg0,
            final PersonRenderer arg1) {
        final String iName0 = arg0.getGedObject().getIndexName();
        final String iName1 = arg1.getGedObject().getIndexName();
        return iName0.compareTo(iName1);
    }
}
