package org.schoellerfamily.gedbrowser.datamodel.util;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Wife;

/**
 * @author Dick Schoeller
 */
public interface FamilyBuilder {
    /**
     * Get the root object of the data set.
     *
     * @return the root object
     */
    Root getRoot();

    /**
     * Create empty family.
     *
     * @return the family
     */
    Family createFamily();

    /**
     * Encapsulate creating family with the given ID.
     *
     * @param idString the id string for the family
     * @return the family
     */
    Family createFamily(String idString);

    /**
     * Create a dated event.
     *
     * @param family the family the event is for
     * @param type the type of event
     * @param dateString the date of the event
     * @return the created event
     */
    Attribute createFamilyEvent(Family family, String type, String dateString);

    /**
     * Create an undated event.
     *
     * @param family the family the event is for
     * @param type the type of event
     * @return the created event
     */
    Attribute createFamilyEvent(Family family, String type);

    /**
     * Add a person as the husband in a family.
     * @param family the family
     * @param person the person
     *
     * @return the husband object
     */
    Husband addHusbandToFamily(Family family, Person person);

    /**
     * Add a person as the wife in a family.
     *
     * @param family the family
     * @param person the person
     * @return the wife object
     */
    Wife addWifeToFamily(Family family, Person person);

    /**
     * Add a person as a child in a family.
     *
     * @param person the person
     * @param family the family
     * @return the Child object
     */
    Child addChildToFamily(Family family, Person person);
}
