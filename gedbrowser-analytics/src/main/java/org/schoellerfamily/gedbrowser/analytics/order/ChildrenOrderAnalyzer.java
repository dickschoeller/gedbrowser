package org.schoellerfamily.gedbrowser.analytics.order;

import java.util.List;

import org.joda.time.LocalDate;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.navigator.FamilyNavigator;
import org.schoellerfamily.gedbrowser.datamodel.navigator.PersonNavigator;

/**
 * @author Dick Schoeller
 */
public final class ChildrenOrderAnalyzer extends AbstractOrderAnalyzer {
    /** Format for child order problem message. */
    private static final String CHILD_ORDER_FORMAT =
            "Child order: %s, born on %s, is after %s, born on %s";

    /** */
    private final OrderAnalyzerResult result;

    /** */
    private final Person person;

    /**
     * @param person the person being analyzed
     * @param result the result accumulator
     */
    public ChildrenOrderAnalyzer(final Person person,
            final OrderAnalyzerResult result) {
        super(result);
        this.person = person;
        this.result = result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderAnalyzerResult analyze() {
        final PersonNavigator navigator = new PersonNavigator(person);
        final List<Family> families = navigator.getFamilies();
        for (final Family family : families) {
            analyzeFamily(family);
        }
        return result;
    }

    /**
     * Analyze the order of children in one family.
     *
     * @param family the family
     */
    private void analyzeFamily(final Family family) {
        Person prevChild = null;
        final FamilyNavigator navigator = new FamilyNavigator(family);
        for (final Person child : navigator.getChildren()) {
            prevChild = analyzeChild(child, prevChild);
        }
    }

    /**
     * Check the order of one child against the previous dated child.
     *
     * @param child the child
     * @param prevChild the previous child
     * @return the current child if dated
     */
    private Person analyzeChild(final Person child, final Person prevChild) {
        Person retChild = prevChild;
        if (retChild == null) {
            return child;
        }
        final LocalDate birthDate = getNearBirthEventDate(child);
        if (birthDate == null) {
            return retChild;
        }
        final LocalDate prevDate = getNearBirthEventDate(prevChild);
        if (prevDate == null) {
            return child;
        }
        if (birthDate.isBefore(prevDate)) {
            final String message = String.format(CHILD_ORDER_FORMAT,
                    child.getName().getString(), birthDate,
                    prevChild.getName().getString(), prevDate);
            getResult().addMismatch(message);
        }
            retChild = child;
        return retChild;
    }
}
