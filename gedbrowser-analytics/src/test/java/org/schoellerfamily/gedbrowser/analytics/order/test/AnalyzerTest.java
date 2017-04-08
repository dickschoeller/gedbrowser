package org.schoellerfamily.gedbrowser.analytics.order.test;

import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.FamilyBuilder;
import org.schoellerfamily.gedbrowser.datamodel.util.PersonBuilder;

/**
 * @author Dick Schoeller
 */
public interface AnalyzerTest {
    /**
     * @return get the person builder associated with this test
     */
    PersonBuilder personBuilder();

    /**
     * @return get the family builder associated with this test
     */
    FamilyBuilder familyBuilder();

    /**
     * A common person creator.
     *
     * @return the person
     */
    default Person createJRandom() {
        return personBuilder().createPerson("I1", "J. Random/Schoeller/");
    }

    /**
     * A common person creator.
     *
     * @return the person
     */
    default Person createAnonymousSchoeller() {
        return personBuilder().createPerson("I2", "Anonymous/Schoeller/");
    }

    /**
     * A common person creator.
     *
     * @return the person
     */
    default Person createAnonymousJones() {
        return personBuilder().createPerson("I3", "Anonymous/Jones/");
    }

    /**
     * A common person creator.
     *
     * @return the person
     */
    default Person createTooTall() {
        return personBuilder().createPerson("I4", "Too Tall/Jones/");
    }

    /**
     * A common person creator.
     *
     * @return the person
     */
    default Person createAnonymousSmith() {
        return personBuilder().createPerson("I6", "Anonymous/Smith/");
    }
}
