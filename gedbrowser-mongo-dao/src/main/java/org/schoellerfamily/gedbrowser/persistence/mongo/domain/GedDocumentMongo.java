package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * @author Dick Schoeller
 *
 * @param <G> the associated GedObject subclass
 */
public abstract class GedDocumentMongo<G extends GedObject>
        extends HasAttributes<G> implements Accepts {
    /** */
    @Id
    private String idString;

    /** */
    @Indexed
    private String string;

    /** */
    @Indexed
    private String filename;

    /** */
    @Indexed
    private String dbName;

    /** */
    @Transient
    private G gedObject;

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getIdString() {
        return idString;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setIdString(final String idString) {
        this.idString = idString;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getString() {
        return string;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setString(final String string) {
        this.string = string;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getFilename() {
        return filename;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setFilename(final String filename) {
        this.filename = filename;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getDbName() {
        return dbName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setDbName(final String dbName) {
        this.dbName = dbName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final G getGedObject() {
        return gedObject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setGedObject(final G gedObject) {
        this.gedObject = gedObject;
    }
}
