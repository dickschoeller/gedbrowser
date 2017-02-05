package org.schoellerfamily.gedbrowser.analytics.order;

import org.joda.time.LocalDate;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;

/**
 * @author Dick Schoeller
 */
public final class OrderAnalyzer extends AbstractOrderAnalyzer {
    /** Which person are we estimating. */
    private final Person person;

    /** */
    private final FamilyOrderAnalyzer familyOrderAnalyzer;

    /** */
    private final ChildrenOrderAnalyzer childrenOrderAnalyzer;

    /**
     * Constructor. Prepares an analyzer for the provided person.
     *
     * @param person the person this analyzer is for
     */
    public OrderAnalyzer(final Person person) {
        super(new OrderAnalyzerResult());
        this.person = person;
        this.familyOrderAnalyzer =
                new FamilyOrderAnalyzer(person, getResult());
        this.childrenOrderAnalyzer =
                new ChildrenOrderAnalyzer(person, getResult());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderAnalyzerResult analyze() {
        setCurrentDate(null);
        setSeenEvent(null);
        for (final GedObject gob : person.getAttributes()) {
            if (!(gob instanceof Attribute)) {
                continue;
            }
            final Attribute attribute = (Attribute) gob;
            if (ignoreable(attribute)) {
                continue;
            }
            basicOrderCheck(attribute);
            if (isUnorderedEvent(attribute)) {
                // We DO NOT pay attention to this event when
                // doing logical order analysis.
                continue;
            }
            birthCheck(attribute);
            deathCheck(attribute);
            setSeenEvent(attribute);
        }
        familyOrderAnalyzer.analyze();
        childrenOrderAnalyzer.analyze();
        return getResult();
    }

    /**
     * @param attribute the attribute to check
     */
    private void birthCheck(final Attribute attribute) {
        final LocalDate newDate = createLocalDate(attribute);
        if (isBirthRelatedEvent(attribute) && getSeenEvent() != null) {
            if (isBirthRelatedEvent(getSeenEvent())) {
                if (isBirthEvent(attribute) && isNamingEvent(getSeenEvent())) {
                    final String message = String.format(
                            "Logical order: %s%s after %s",
                            attribute.getString(), onDateString(newDate),
                            getSeenEvent().getString());
                    getResult().addMismatch(message);
                }
            } else {
                final String message = String.format(
                        "Logical order: %s%s after non birth event, %s",
                        attribute.getString(), onDateString(newDate),
                        getSeenEvent().getString());
                getResult().addMismatch(message);
            }
        }
    }

    /**
     * @param attribute the attribute to check
     */
    private void deathCheck(final Attribute attribute) {
        final LocalDate newDate = createLocalDate(attribute);
        if (getSeenEvent() != null && isDeathRelatedEvent(getSeenEvent())) {
            if (isDeathRelatedEvent(attribute)) {
                if (isDeathEvent(attribute)
                        && isPostDeathEvent(getSeenEvent())) {
                    final String message = String.format(
                            "Logical order: %s%s after post death even, %s",
                            attribute.getString(), onDateString(newDate),
                            getSeenEvent().getString());
                    getResult().addMismatch(message);
                }
            } else {
                final String message = String.format(
                        "Logical order: %s%s after death related event, %s",
                        attribute.getString(), onDateString(newDate),
                        getSeenEvent().getString());
                getResult().addMismatch(message);
            }
        }
    }

    /**
     * <p>If any of the death related events.</p>
     * <p>Only public for testability.</p>
     *
     * @param event the attribute to check if it's a death event
     * @return true if this is a death related event
     */
    public boolean isDeathRelatedEvent(final Attribute event) {
        if (isDeathEvent(event)) {
            return true;
        }
        if (isPostDeathEvent(event)) {
            return true;
        }
        return isUnorderedEvent(event);
    }

    /**
     * <p>Check if this event is death.</p>
     * <p>Only public for testability.</p>
     *
     * @param event the attribute to check if it's a birth event
     * @return true if this is a death event
     */
    public boolean isDeathEvent(final Attribute event) {
        return "Death".equals(event.getString());
    }

    /**
     * <p>Check for post death events, burial, cremation, funeral, head
     * stone unveiling come to mind.</p>
     * <p>Only public for testability.</p>
     *
     * @param event the attribute to check if it's a post death event
     * @return true if this is a death event
     */
    public boolean isPostDeathEvent(final Attribute event) {
        if ("Burial".equals(event.getString())) {
            return true;
        }
        if ("Cremation".equals(event.getString())) {
            return true;
        }
        if ("Funeral".equals(event.getString())) {
            return true;
        }
        return "Headstone unveiling".equals(event.getString());
    }

    /**
     * <p>Check for events that might be before or after death. Will comes to
     * mind.</p>
     * <p>Only public for testability.</p>
     *
     * @param event the attribute to check if it's a post death event
     * @return true if this is a death event
     */
    public boolean isUnorderedEvent(final Attribute event) {
        return "Will".equals(event.getString());
    }
}
