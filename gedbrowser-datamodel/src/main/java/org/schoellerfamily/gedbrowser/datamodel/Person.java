package org.schoellerfamily.gedbrowser.datamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.GodClass")
public final class Person extends GedObject implements Nameable, FamilyLinkage {
    /**
     * Default constructor.
     */
    public Person() {
        super();
    }

    /**
     * @param parent parent object of this person
     * @param xref cross reference to this person
     */
    public Person(final GedObject parent, final ObjectId xref) {
        super(parent, xref.getIdString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSurname() {
        final Name name = getName();
        return name.getSurname();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIndexName() {
        final Name name = getName();
        return name.getIndexName();
    }

    /**
     * Find an attribute that can return a birth date string for this person and
     * get that string.
     *
     * @return the birth date string.
     */
    public String getBirthDate() {
        for (final GedObject gob : getAttributes()) {
            if (!(gob instanceof Attribute)) {
                continue;
            }
            final Attribute attr = (Attribute) gob;
            final String datestring = attr.getBirthDate();
            if (!datestring.isEmpty()) {
                return datestring;
            }
        }
        return "";
    }

    /**
     * Find an attribute that can return a birth date string for this person and
     * get that string.
     *
     * @return the birth date string.
     */
    public String getBirthYear() {
        for (final GedObject gob : getAttributes()) {
            if (!(gob instanceof Attribute)) {
                continue;
            }
            final Attribute attr = (Attribute) gob;
            final String datestring = attr.getBirthYear();
            if (!datestring.isEmpty()) {
                return datestring;
            }
        }
        return "";
    }

    /**
     * Find an attribute that can return a birth date string for this person and
     * get that string.
     *
     * @return the birth date string.
     */
    public String getSortDate() {
        for (final GedObject gob : getAttributes()) {
            if (!(gob instanceof Attribute)) {
                continue;
            }
            final Attribute attr = (Attribute) gob;
            final String datestring = attr.getSortDate();
            if (!datestring.isEmpty()) {
                return datestring;
            }
        }
        return "";
    }

    /**
     * Find an attribute that can return a death date string for this person and
     * get that string.  If not found return an empty string.
     *
     * @return the death date string.
     */
    public String getDeathDate() {
        for (final GedObject gob : getAttributes()) {
            if (!(gob instanceof Attribute)) {
                continue;
            }
            final Attribute attr = (Attribute) gob;
            final String datestring = attr.getDeathDate();
            if (!datestring.isEmpty()) {
                return datestring;
            }
        }
        return "";
    }

    /**
     * Doesn't care about dates, just checks whether there is a death
     * attribute.
     *
     * @return true if a death attribute is found
     */
    public boolean hasDeathAttribute() {
        for (final GedObject gob : getAttributes()) {
            if (!(gob instanceof Attribute)) {
                continue;
            }
            final Attribute attr = (Attribute) gob;
            if ("Death".equals(attr.getString())) {
                return true;
            }
        }
        return false;
    }
    /**
     * Find an attribute that can return a death date string for this person and
     * get that string.  If not found return an empty string.
     *
     * @return the death date string.
     */
    public String getDeathYear() {
        for (final GedObject gob : getAttributes()) {
            if (!(gob instanceof Attribute)) {
                continue;
            }
            final Attribute attr = (Attribute) gob;
            final String datestring = attr.getDeathYear();
            if (!datestring.isEmpty()) {
                return datestring;
            }
        }
        return "";
    }

    /**
     * Find the mother of this person.  If not found, return an unset Person.
     *
     * @return the mother.
     */
    public Person getMother() {
        for (final GedObject gob : getAttributes()) {
            if (!(gob instanceof FamC)) {
                continue;
            }
            final FamC link = (FamC) gob;
            final Person mother = link.getMother();
            if (mother.isSet()) {
                return mother;
            }
        }
        return new Person();
    }

    /**
     * Find the father of this person.  If not found, return an unset Person.
     *
     * @return the father.
     */
    public Person getFather() {
        for (final GedObject gob : getAttributes()) {
            if (!(gob instanceof FamC)) {
                continue;
            }
            final FamC link = (FamC) gob;
            final Person father = link.getFather();
            if (father.isSet()) {
                return father;
            }
        }
        return new Person();
    }

    /**
     * Get the list of families that this person is a spouse in.
     *
     * @param families a list that has already been started.
     * @return the list of families
     */
    public List<Family> getFamilies(final List<Family> families) {
        final List<Family> retVal = families;
        for (final GedObject gob : getAttributes()) {
            if (gob instanceof FamS) {
                final Family family = ((FamS) gob).getFamily();
                if (family != null) {
                    retVal.add(family);
                }
            }
        }
        return retVal;
    }

    /**
     * Get the list of all of the spouses of this person.
     *
     * @param spouses the already started list of spouses, can be null.
     * @param person the current person.
     * @return the list of spouses.
     */
    public List<Person> getSpouses(final List<Person> spouses,
            final Person person) {
        List<Person> retVal;
        if (spouses == null) {
            retVal = new ArrayList<Person>();
        } else {
            retVal = spouses;
        }
        for (final GedObject gob : getAttributes()) {
            if (gob instanceof FamS) {
                final Person spouse = ((FamS) gob).getSpouse(person);
                if (spouse.isSet()) {
                    retVal.add(spouse);
                }
            }
        }
        return retVal;
    }

    /**
     * Get the list of all of children of this person.
     *
     * @return the list of children
     */
    public List<Person> getChildren() {
        final List<Person> retVal = new ArrayList<Person>();
        for (final GedObject gob : getAttributes()) {
            if (gob instanceof FamS) {
                retVal.addAll(((FamS) gob).getChildren());
            }
        }
        return retVal;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Name getName() {
        return getNameAttribute();
    }

    /**
     * @return the first letter of the surname (or a question mark)
     */
    public String getSurnameLetter() {
        return getSurname().substring(0, 1);
    }

    /**
     * @param families list to add to
     * @return list of all families that this person is a child of
     */
    public List<Family> getFamiliesC(final List<Family> families) {
        for (final GedObject gob : getAttributes()) {
            if (!(gob instanceof FamC)) {
                continue;
            }
            final FamC famc = (FamC) gob;
            final Family family = famc.findFamily();
            families.add(family);
        }
        return families;
    }
}
