package org.schoellerfamily.gedbrowser.datamodel.util;

import java.io.Serializable;
import java.util.Comparator;

import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GetDateVisitor;

/**
 * Compares person values.
 *
 * @author Richard Schoeller
 */
public final class PersonComparator implements Comparator<Person>, Serializable {
    /** */
    private static final long serialVersionUID = 1L;

    /**
     * Compare two {@link Person} instances for ordering.
     * <p>
     * Persons are ordered by index name; if the index names are equal,
     * the birth sort date is used; if those are also equal, the person's
     * identifier string is used as a final tiebreaker.
     *
     * @param p0 the first person to compare
     * @param p1 the second person to compare
     * @return a negative integer, zero, or a positive integer as {@code p0}
     *         is less than, equal to, or greater than {@code p1}
     */
    @Override
    public int compare(final Person p0, final Person p1) {
        final int nameComparison =
                p0.getIndexName().compareTo(p1.getIndexName());
        if (nameComparison != 0) {
            return nameComparison;
        }
        // If the names are the same, use the sort date (approximates on
        // birth).
        final GetDateVisitor visitor0 = new GetDateVisitor("Birth");
        p0.accept(visitor0);
        final String sortDate0 = visitor0.getSortDate();
        final GetDateVisitor visitor1 = new GetDateVisitor("Birth");
        p1.accept(visitor1);
        final String sortDate1 = visitor1.getSortDate();
        final int dateComparison =
                sortDate0.compareTo(sortDate1);
        if (dateComparison != 0) {
            return dateComparison;
        }
        // If the dates are the same, use the I number.
        return p0.getString().compareTo(p1.getString());
    }
}
