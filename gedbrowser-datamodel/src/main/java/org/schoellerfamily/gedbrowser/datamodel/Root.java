package org.schoellerfamily.gedbrowser.datamodel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dick Schoeller
 */
public final class Root extends AbstractSpecialObject {
    /** */
    private transient String filename;

    /** */
    private transient String dbname;

    /**
     * Map of ID strings to GedObjects. This is the complete set of top level
     * objects, Persons, Families, Sources and Submittors.
     */
    private final transient Map<String, GedObject> objects =
            new HashMap<String, GedObject>();

    /**
     * Index object that manages index breakdown by Surname -> Complete Name ->
     * individual persons.
     */
    private final transient Index surnameIndex;

    /**
     * @param parent
     *            parent object of this child
     */
    public Root(final GedObject parent) {
        super(parent);
        surnameIndex = new Index(this);
        setFinder(new RootFinder());
    }

    /**
     * @param parent parent object of this child
     * @param string long version of type string
     * @param finder the engine for find objects in this data set
     */
    public Root(final GedObject parent, final String string,
            final FinderStrategy finder) {
        super(parent, string);
        surnameIndex = new Index(this);
        setFinder(finder);
    }

    /**
     * @param parent parent object of this child
     * @param finder the engine for finding objects in the data set
     */
    public Root(final GedObject parent, final FinderStrategy finder) {
        super(parent);
        surnameIndex = new Index(this);
        setFinder(finder);
    }

    /**
     * @param parent
     *            parent object of this child
     * @param string
     *            long version of type string
     */
    public Root(final GedObject parent, final String string) {
        super(parent, string);
        surnameIndex = new Index(this);
        setFinder(new RootFinder());
    }

    /**
     * @param str ID string of object to insert
     * @param gob object to insert
     */
    public void insert(final String str, final GedObject gob) {
        if (gob == null) {
            // I don't think this should happen. We would only get
            // here is the factory for a level 0 tag didn't return
            // anything. Even then, I think that that insert would
            // have been cut off by gob being NULL.
            return;
        }

        if (str == null || str.isEmpty()) {
            objects.put(gob.getString(), gob);
        } else {
            objects.put(str, gob);
        }
        addAttribute(gob);
    }

    /**
     * @return the map of IDs to objects.
     */
    public Map<String, GedObject> getObjects() {
        return objects;
    }

    /**
     * Initialize the surname index.
     */
    public void initIndex() {
        surnameIndex.init();
    }

    /**
     * @return the index object
     */
    public Index getIndex() {
        return surnameIndex;
    }

    /**
     * @param filename
     *            the file name for this data set
     */
    public void setFilename(final String filename) {
        this.filename = filename;
    }

    /**
     * @return the file name for this data set
     */
    public String getTheFilename() {
        return this.filename;
    }

    /**
     * @return the DB name for this data set
     */
    public String getTheDbName() {
        return this.dbname;
    }

    /**
     * @param dbName
     *            the file name for this data set
     */
    public void setDbName(final String dbName) {
        this.dbname = dbName;
    }
}
