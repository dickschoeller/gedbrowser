package org.schoellerfamily.gedbrowser.analytics.order;

import org.joda.time.LocalDate;
import org.schoellerfamily.gedbrowser.analytics.visitor.FamilyAnalysisVisitor;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.navigator.FamilyNavigator;
import org.schoellerfamily.gedbrowser.datamodel.navigator.PersonNavigator;

/**
 * Analyze the order of family data for this person.
 *
 * @author Dick Schoeller
 */
public final class FamilyOrderAnalyzer extends AbstractOrderAnalyzer {
    /** */
    private final Person person;

    /** */
    private Family seenFamily;

    /**
     * @param person the person analyzed
     * @param orderAnalyzerResult the result accumulator for this analysis
     */
    public FamilyOrderAnalyzer(final Person person,
            final OrderAnalyzerResult orderAnalyzerResult) {
        super(orderAnalyzerResult);
        this.person = person;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderAnalyzerResult analyze() {
        seenFamily = null;
        final PersonNavigator navigator = new PersonNavigator(person);
        for (final Family family : navigator.getFamilies()) {
            setCurrentDate(null);
            setSeenEvent(null);
            checkFamily(family);
        }
        return getResult();
    }

    /**
     * Check this family for internal order and relative
     * order to the other families for this person.
     *
     * @param family the family to check
     */
    public void checkFamily(final Family family) {
        final LocalDate familyDate = analyzeDates(family);
        final LocalDate seenDate = earliestDate(seenFamily);
        if (seenDate == null) {
            if (familyDate != null) {
                seenFamily = family;
            }
        } else {
            if (familyDate != null) {
                if (familyDate.isBefore(seenDate)) {
                    final Person spouse = (new FamilyNavigator(family))
                            .getSpouse(person);
                    final Person seenSpouse = (new FamilyNavigator(seenFamily))
                            .getSpouse(person);
                    final String message = String.format(
                            "Family order: family with spouse %s (%s) is after "
                            + "family with spouse %s (%s)",
                            spouse.getName().getString(),
                            familyDate.toString(),
                            seenSpouse.getName().getString(),
                            seenDate.toString());
                    getResult().addMismatch(message);
                }
                seenFamily = family;
            }
        }
    }

    /**
     * Analyze the family. Add any date order problems to the analysis.
     *
     * @param family the family to check
     * @return the earliest date of an event in the family
     */
    private LocalDate analyzeDates(final Family family) {
        if (family == null) {
            return null;
        }
        LocalDate earliestDate = null;
        final FamilyAnalysisVisitor visitor = new FamilyAnalysisVisitor();
        family.accept(visitor);
        for (final Attribute attribute : visitor.getTrimmedAttributes()) {
            basicOrderCheck(attribute);
            setSeenEvent(attribute);
            final LocalDate date = createLocalDate(attribute);
            earliestDate = minDate(earliestDate, date);
        }
        return earliestDate(visitor);
    }

    /**
     * @param visitor the visitor that is doing the processing
     * @return the earliest date from the contents
     */
    private LocalDate earliestDate(final FamilyAnalysisVisitor visitor) {
        LocalDate earliestDate = null;
        for (final Attribute attribute : visitor.getTrimmedAttributes()) {
            final LocalDate date = createLocalDate(attribute);
            earliestDate = minDate(earliestDate, date);
        }
        for (final Child child : visitor.getChildren()) {
            final Person personC = child.getChild();
            final LocalDate birthDate = getNearBirthEventDate(personC);
            earliestDate = minDate(earliestDate, birthDate);
        }
        return earliestDate;
    }

    /**
     * @param family the family to check
     * @return the earliest date of an event in the family
     */
    private LocalDate earliestDate(final Family family) {
        if (family == null) {
            return null;
        }
        final FamilyAnalysisVisitor visitor = new FamilyAnalysisVisitor();
        family.accept(visitor);
        return earliestDate(visitor);
    }
}
