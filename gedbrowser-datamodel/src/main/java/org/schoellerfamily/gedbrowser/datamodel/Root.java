package org.schoellerfamily.gedbrowser.datamodel;

import java.util.HashMap;
import java.util.Map;

import org.schoellerfamily.gedbrowser.datamodel.finder.FinderStrategy;
import org.schoellerfamily.gedbrowser.datamodel.finder.RootFinder;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * Represents root in the domain model.
 *
 * @author Richard Schoeller
 */
public final class Root extends AbstractSpecialObject {
    /**
     * The filename value.
     */
    private String filename;

    /**
     * The dbname value.
     */
    private String dbname;

    /**
     * Map of ID strings to GedObjects. This is the complete set of top level GedObjects.
     */
    private final Map<String, GedObject> objects = new HashMap<>();

    /**
     * Index object that manages index breakdown by Surname -> Complete Name -> Person.
     */
    private final Index surnameIndex;

    /**
     * Creates a new Root.
     */
    public Root() {
        this(new RootFinder());
    }

    /**
     * Creates a new Root.
     *
     * @param string the string
     * @param finder the finder
     */
    public Root(final String string, final FinderStrategy finder) {
        this(finder);
        setString(string);
    }

    /**
     * Creates a new Root.
     *
     * @param finder the finder
     */
    public Root(final FinderStrategy finder) {
        super();
        surnameIndex = new Index(this);
        setFinder(finder);
    }

    /**
     * Creates a new Root.
     *
     * @param string the string
     */
    public Root(final String string) {
        this(string, new RootFinder());
    }

    /**
     * Executes extra insert.
     *
     * @param gob the gob
     */
    @Override
    public void extraInsert(final FinderObject gob) {
        objects.put(gob.getString(), (GedObject) gob);
        addAttribute((GedObject) gob);
    }

    /**
     * Gets the objects.
     *
     * @return the objects
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
     * Gets the index.
     *
     * @return the index
     */
    public Index getIndex() {
        return surnameIndex;
    }

    /**
     * Sets the filename.
     *
     * @param filename the filename to use
     */
    public void setFilename(final String filename) {
        this.filename = filename;
    }

    /**
     * Gets the the filename.
     *
     * @return the the filename
     */
    public String getTheFilename() {
        return this.filename;
    }

    /**
     * Gets the the db name.
     *
     * @return the the db name
     */
    public String getTheDbName() {
        return this.dbname;
    }

    /**
     * Sets the db name.
     *
     * @param dbName the db name to use
     */
    public void setDbName(final String dbName) {
        this.dbname = dbName;
    }

    /**
     * Executes accept.
     *
     * @param visitor the visitor
     */
    @Override
    public void accept(final GedObjectVisitor visitor) {
        visitor.visit(this);
    }
}
