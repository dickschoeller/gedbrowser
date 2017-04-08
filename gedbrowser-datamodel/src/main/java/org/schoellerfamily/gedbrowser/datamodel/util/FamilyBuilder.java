package org.schoellerfamily.gedbrowser.datamodel.util;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Wife;

/**
 * @author Dick Schoeller
 */
public class FamilyBuilder {
    /** */
    private final GedObjectBuilder gedObjectBuilder;

    /**
     * @param gedObjectBuilder the containing builder
     */
    public FamilyBuilder(final GedObjectBuilder gedObjectBuilder) {
        this.gedObjectBuilder = gedObjectBuilder;
    }

    /**
     * Get the root object from the parent builder.
     *
     * @return the root object
     */
    private Root getRoot() {
        return gedObjectBuilder.getRoot();
    }


    /**
     * Encapsulate creating family with the given ID.
     *
     * @param idString the id string for the family
     * @return the family
     */
    public Family createFamily(final String idString) {
        if (idString == null) {
            return new Family();
        }
        final Family family = new Family(getRoot(), new ObjectId(idString));
        getRoot().insert(family);
        return family;
    }

    /**
     * Create a dated event.
     *
     * @param family the family the event is for
     * @param type the type of event
     * @param dateString the date of the event
     * @return the created event
     */
    public Attribute createFamilyEvent(final Family family, final String type,
            final String dateString) {
        if (family == null || type == null || dateString == null) {
            return new Attribute();
        }
        final Attribute event = gedObjectBuilder.createAttribute(family, type);
        event.insert(new Date(event, dateString));
        return event;
    }

    /**
     * Create an undated event.
     *
     * @param family the family the event is for
     * @param type the type of event
     * @return the created event
     */
    public Attribute createFamilyEvent(final Family family,
            final String type) {
        return gedObjectBuilder.createAttribute(family, type);
    }

    /**
     * Add a person as the husband in a family.
     * @param family the family
     * @param person the person
     *
     * @return the husband object
     */
    public Husband addHusbandToFamily(final Family family,
            final Person person) {
        if (family == null || person == null) {
            return new Husband();
        }
        final FamS famS = new FamS(person, "FAMS",
                new ObjectId(family.getString()));
        final Husband husband = new Husband(family, "Husband",
                new ObjectId(person.getString()));
        family.insert(husband);
        person.insert(famS);
        return husband;
    }

    /**
     * Add a person as the wife in a family.
     *
     * @param family the family
     * @param person the person
     * @return the wife object
     */
    public Wife addWifeToFamily(final Family family, final Person person) {
        if (family == null || person == null) {
            return new Wife();
        }
        final FamS famS = new FamS(person, "FAMS",
                new ObjectId(family.getString()));
        final Wife wife = new Wife(family, "Wife",
                new ObjectId(person.getString()));
        family.insert(wife);
        person.insert(famS);
        return wife;
    }

    /**
     * Add a person as a child in a family.
     *
     * @param person the person
     * @param family the family
     * @return the Child object
     */
    public Child addChildToFamily(final Family family, final Person person) {
        if (family == null || person == null) {
            return new Child();
        }
        final FamC famC = new FamC(person, "FAMC",
                new ObjectId(family.getString()));
        final Child child = new Child(family, "Child",
                new ObjectId(person.getString()));
        family.insert(child);
        person.insert(famC);
        return child;
    }
}
