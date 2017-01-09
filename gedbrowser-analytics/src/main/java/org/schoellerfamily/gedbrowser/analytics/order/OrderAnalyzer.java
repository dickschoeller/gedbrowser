package org.schoellerfamily.gedbrowser.analytics.order;

import org.joda.time.LocalDate;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;

/**
 * @author Dick Schoeller
 */
public class OrderAnalyzer extends AbstractOrderAnalyzer {
    /** Which person are we estimating. */
    private final Person person;

    /** */
    private final FamilyOrderAnalyzer familyOrderAnalyzer;

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
                    getResult().addMismatch(
                            attribute.getString() + onDateString(newDate)
                            + " after " + getSeenEvent().getString());
                }
            } else {
                getResult().addMismatch(attribute.getString()
                        + onDateString(newDate) + " after non birth event, "
                        + getSeenEvent().getString());
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
                    getResult().addMismatch(
                            attribute.getString() + onDateString(newDate)
                                    + " after post death event, "
                                    + getSeenEvent().getString());
                }
            } else {
                getResult().addMismatch(attribute.getString()
                        + onDateString(newDate) + " after death related event, "
                        + getSeenEvent().getString());
            }
        }
    }

    /**
     * @param attribute the attribute to check if it's a birth event
     * @return true if this is a birth related event
     */
    private boolean isBirthRelatedEvent(final Attribute attribute) {
        return isNamingEvent(attribute) || isBirthEvent(attribute);
    }

    /**
     * @param attribute the attribute to check if it's a birth event
     * @return true if this is a naming related event
     */
    private boolean isNamingEvent(final Attribute attribute) {
        final String type = attribute.getString();
        if ("Baptism".equals(type)) {
            return true;
        }
        if ("Christening".equals(type)) {
            return true;
        }
        return ("Naming".equals(type));
    }

    /**
     * @param attribute the attribute to check if it's a birth event
     * @return true if this is a birth event
     */
    private boolean isBirthEvent(final Attribute attribute) {
        return "Birth".equals(attribute.getString());
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
