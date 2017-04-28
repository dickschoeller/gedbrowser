package org.schoellerfamily.gedbrowser.datamodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.appender.AppenderStrategy;
import org.schoellerfamily.gedbrowser.datamodel.appender.GedAppender;
import org.schoellerfamily.gedbrowser.datamodel.finder.FinderStrategy;
import org.schoellerfamily.gedbrowser.datamodel.finder.ParentFinder;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.TooManyMethods",
    "PMD.GodClass" })
public abstract class GedObject {

    /** */
    protected static final String DEFAULT_IDX_NAME = "";
    /** */
    public static final String VERSION = "1.1.2";
    /** */
    protected static final String DEFAULT_SURNAME = "";
    /** */
    protected static final String DEFAULT_BIRTHDATE = "";
    /** */
    protected static final String DEFAULT_DEATHDATE = "";
    /** */
    protected static final String DEFAULT_DATE = "";

    /** */
    private static final int PRIME = 31;

    /** */
    private String string;
    /** */
    private GedObject parent;
    /** */
    private final transient List<GedObject> attributes =
            new ArrayList<GedObject>();
    /** */
    private final transient boolean set;

    /** */
    private transient FinderStrategy finder;

    /** */
    private transient AppenderStrategy appender;

    /** */
    protected GedObject() {
        this.set = false;
        this.string = "";
        this.parent = null;
        finder = new ParentFinder();
        appender = new GedAppender(this);
    }

    /**
     * @param parent
     *            parent object of this object
     */
    protected GedObject(final GedObject parent) {
        this.string = "";
        this.parent = parent;
        this.set = true;
        finder = new ParentFinder();
        appender = new GedAppender(this);
    }

    /**
     * @param parent
     *            parent object of this object
     * @param string
     *            long version of type string
     */
    protected GedObject(final GedObject parent, final String string) {
        this.parent = parent;
        if (string == null) {
            this.string = "";
        } else {
            this.string = string;
        }
        set = true;
        finder = new ParentFinder();
        appender = new GedAppender(this);
    }

    /**
     * @return whether this object was set. If false this is a stub.
     */
    public final boolean isSet() {
        return this.set;
    }

    /**
     * @return the long version of the type string
     */
    public final String getString() {
        return string;
    }

    /**
     * @param string long version of type string
     */
    public final void setString(final String string) {
        if (string == null) {
            this.string = "";
        } else {
            this.string = string;
        }
    }

    /**
     * @param appendage
     *            text to append to the string
     */
    public final void appendString(final String appendage) {
        appender.appendString(appendage);
    }

    /**
     * @return the parent object of this object
     */
    public final GedObject getParent() {
        return parent;
    }

    /**
     * @param parent
     *            parent object of this object
     */
    public final void setParent(final GedObject parent) {
        this.parent = parent;
    }

    /**
     * @param attribute
     *            object to add to the attribute list
     */
    public final void addAttribute(final GedObject attribute) {
        attributes.add(attribute);
    }

    /**
     * @param attribute
     *            object to remove from the attribute list
     */
    public final void removeAttribute(final GedObject attribute) {
        attributes.remove(attribute);
    }

    /**
     * @param attribute
     *            object to check in the attribute list
     * @return whether the provided object is in the list
     */
    public final boolean hasAttribute(final GedObject attribute) {
        return attributes.contains(attribute);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        int result = 1;
        result = PRIME * result + parentHashCode();
        result = PRIME * result + stringHashCode();
        return result;
    }

    /**
     * @return return the string's hashCode
     */
    private int stringHashCode() {
        return string.hashCode();
    }

    /**
     * @return the parent's hashCode
     */
    private int parentHashCode() {
        if (parent == null) {
            return 0;
        }
        return parent.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings({ "PMD.CyclomaticComplexity",
            "PMD.ModifiedCyclomaticComplexity",
            "PMD.StdCyclomaticComplexity" })
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GedObject other = (GedObject) obj;
        if (parent == null) {
            if (other.parent != null) {
                return false;
            }
        } else if (!parent.equals(other.parent)) {
            return false;
        }
        return string.equals(other.string);
    }

    /**
     * @param str the ID string of the object being sought
     * @return the object found from the searching the top level object list
     */
    public final GedObject findInParent(final String str) {
        if (parent == null) {
            return null;
        }
        return parent.find(str);
    }

    /**
     * @param <T> the type to return
     * @param str the ID string of the object you're looking for
     * @param clazz the class object of the type you're looking for
     * @return the found object
     */
    public final <T extends GedObject> T findInParent(
            final String str, final Class<T> clazz) {
        return parent.find(str, clazz);
    }

    /**
     * @param <T> the type to return
     * @param str the ID string of the object you're looking for
     * @param clazz the class object of the type you're looking for
     * @return the found object
     */
    public final <T extends GedObject> T find(
            final String str, final Class<T> clazz) {
        return finder.find(this, str, clazz);
    }

    /**
     * @return the filename provided by the parent
     */
    public final String getParentFilename() {
        if (getParent() == null) {
            return null;
        }
        return getParent().getFilename();
    }

    /**
     * @return the filename provided by the parent
     */
    public final String getParentDbName() {
        if (getParent() == null) {
            return null;
        }
        return getParent().getDbName();
    }

    /**
     * @return the list of attributes and sub-objects for this object
     */
    public final List<GedObject> getAttributes() {
        return attributes;
    }

    /**
     * @return whether the list of attributes has some content
     */
    public final boolean hasAttributes() {
        return !attributes.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        return string;
    }

//    /**
//     * @param gob the object to compare with this one
//     * @return whether these are actually the same Java object
//     */
//    public final boolean sameAs(final GedObject gob) {
//        return this == gob;
//    }

    /**
     * @param str the ID string of the object being sought
     * @return the object found from the searching the top level object list
     */
    public final GedObject find(final String str) {
        return finder.find(this, str);
    }

    /**
     * @return the filename associated with this data set
     */
    public final String getFilename() {
        return finder.getFilename(this);
    }

    /**
     * @return the filename associated with this data set
     */
    public final String getDbName() {
        return finder.getDbName(this);
    }

    /**
     * @param gob object to insert
     */
    public final void insert(final GedObject gob) {
        if (gob == null) {
            return;
        }
        extraInsert(gob);
        finder.insert(this, gob);
    }

    /**
     * @param gob object to insert
     */
    @SuppressWarnings("PMD.EmptyMethodInAbstractClassShouldBeAbstract")
    public void extraInsert(final GedObject gob) {
        // The default implementation is empty. Some derived
        // classes implement this.
    }

    /**
     * Set the strategy that implements finding.
     *
     * @param finder the finder.
     */
    protected final void setFinder(final FinderStrategy finder) {
        this.finder = finder;
    }

    /**
     * Set the strategy that implements appending.
     *
     * @param appender the appender.
     */
    protected final void setAppender(final AppenderStrategy appender) {
        this.appender = appender;
    }

    /**
     * @param surname the surname of persons being sought.
     * @return collection of matches.
     */
    public final Collection<Person> findInParentBySurname(
            final String surname) {
        if (parent == null) {
            return null;
        }

        return parent.findBySurname(surname);
    }

    /**
     * @param surname the surname of the persons being sought.
     * @return collection of matches.
     */
    public final Collection<Person> findBySurname(final String surname) {
        return finder.findBySurname(this, surname);
    }

    /**
     * @param beginsWith the string that the surnames should beginWith.
     * @return collection of matches.
     */
    public final Collection<String> findInParentBySurnamesBeginWith(
            final String beginsWith) {
        return parent.findBySurnamesBeginWith(beginsWith);
    }

    /**
     * @param beginsWith the string that the surnames should beginWith.
     * @return collection of matches.
     */
    public final Collection<String> findBySurnamesBeginWith(
            final String beginsWith) {
        return finder.findBySurnamesBeginWith(this, beginsWith);
    }

    /**
     * @return the collection of initial letters
     */
    public final Collection<String> findInParentSurnameInitialLetters() {
        return parent.findSurnameInitialLetters();
    }

    /**
     * @return the collection of initial letters
     */
    public final Collection<String> findSurnameInitialLetters() {
        return finder.findSurnameInitialLetters(this);
    }

    /**
     * Hook for using the visitor design pattern to accumulate information
     * about a GedObject and its children.
     *
     * @param visitor the visitor
     */
    public abstract void accept(GedObjectVisitor visitor);
}
