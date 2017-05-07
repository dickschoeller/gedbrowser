package org.schoellerfamily.gedbrowser.datamodel.util;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;

/**
 * @author Dick Schoeller
 */
public interface PersonBuilder {
    /**
     * Get the root object of the data set.
     *
     * @return the root object
     */
    Root getRoot();

    /**
     * Create an empty person.
     *
     * @return the person
     */
    Person createPerson();

    /**
     * Create a person with the given ID and name.
     *
     * @param idString the ID
     * @return the person
     */
    Person createPerson(String idString);

    /**
     * Create a person with the given ID and name.
     *
     * @param idString the ID
     * @param name the name
     * @return the person
     */
    Person createPerson(String idString, String name);

    /**
     * @param person the person to add the name to
     * @param string the name string
     * @return the new name object
     */
    Name addNameToPerson(Person person, String string);

    /**
     * Create a dated event.
     *
     * @param person the person the event is for
     * @param type the type of event
     * @param dateString the date of the event
     * @return the created event
     */
    Attribute createPersonEvent(Person person, String type, String dateString);

    /**
     * Create an undated event.
     *
     * @param person the person the event is for
     * @param type the type of event
     * @return the created event
     */
    Attribute createPersonEvent(Person person, String type);

    /**
     * @param person the person to add the name to
     * @param string the reference string
     * @return the new multimedia object
     */
    Multimedia addMultimediaToPerson(Person person, String string);
}
