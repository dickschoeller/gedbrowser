/**
 * 
 */
package org.schoellerfamily.gedbrowser.writer;

import java.util.Comparator;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * @author Dick Schoeller
 */
public class IdComparator<T extends GedObject> implements Comparator<T> {
    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(T arg0, T arg1) {
        return arg0.getString().compareTo(arg1.getString());
    }
}
