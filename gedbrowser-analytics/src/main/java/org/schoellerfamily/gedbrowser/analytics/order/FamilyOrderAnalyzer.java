package org.schoellerfamily.gedbrowser.analytics.order;

import org.joda.time.LocalDate;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;

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
        // Can't check FamSes against other events because they are
        // often lumped near the end of the lists.
        for (final GedObject gob : person.getAttributes()) {
            setCurrentDate(null);
            setSeenEvent(null);
            if (!(gob instanceof FamS)) {
                continue;
            }
            final FamS fams = (FamS) gob;
            final Family family = fams.getFamily();
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
                    final String message = String.format(
                            "Family order: family with spouse %s (%s) is after "
                            + "family with spouse %s (%s)",
                            family.getSpouse(person).getName().getString(),
                            familyDate.toString(),
                            seenFamily.getSpouse(person).getName().getString(),
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
        LocalDate earliestDate = null;
        for (final GedObject gob : family.getAttributes()) {
            if (!(gob instanceof Attribute)) {
                continue;
            }
            final Attribute attribute = (Attribute) gob;
            if (ignoreable(attribute)) {
                continue;
            }
            basicOrderCheck(attribute);
            setSeenEvent(attribute);
            final LocalDate date = createLocalDate(attribute);
            earliestDate = minDate(earliestDate, date);
        }
        return earliestDate(family);
    }

    /**
     * @param family the family to check
     * @return the earliest date of an event in the family
     */
    private LocalDate earliestDate(final Family family) {
        LocalDate earliestDate = null;
        if (family == null) {
            return earliestDate;
        }
        for (final GedObject gob : family.getAttributes()) {
            if (gob instanceof Attribute) {
                final Attribute attribute = (Attribute) gob;
                final LocalDate date = createLocalDate(attribute);
                earliestDate = minDate(earliestDate, date);
            }
            if (gob instanceof Child) {
                final Child child = (Child) gob;
                final Person personC = child.getChild();
                final LocalDate birthDate = getNearBirthEventDate(personC);
                earliestDate = minDate(earliestDate, birthDate);
            }
        }
        return earliestDate;
    }
}
