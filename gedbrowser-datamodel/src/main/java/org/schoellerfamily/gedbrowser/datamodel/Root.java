package org.schoellerfamily.gedbrowser.datamodel;

import java.util.HashMap;
import java.util.Map;

import org.schoellerfamily.gedbrowser.datamodel.finder.FinderStrategy;
import org.schoellerfamily.gedbrowser.datamodel.finder.RootFinder;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

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
     * Default constructor.
     */
    public Root() {
        this(new RootFinder());
    }

    /**
     * @param string long version of type string
     * @param finder the engine for find objects in this data set
     */
    public Root(final String string, final FinderStrategy finder) {
        this(finder);
        setString(string);
    }

    /**
     * @param finder the engine for finding objects in the data set
     */
    public Root(final FinderStrategy finder) {
        super();
        surnameIndex = new Index(this);
        setFinder(finder);
    }

    /**
     * @param string long version of type string
     */
    public Root(final String string) {
        this(string, new RootFinder());
    }

    /**
     * @param gob object to insert
     */
    @Override
    public void extraInsert(final GedObject gob) {
        objects.put(gob.getString(), gob);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final GedObjectVisitor visitor) {
        visitor.visit(this);
    }
}
