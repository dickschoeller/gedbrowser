package org.schoellerfamily.gedbrowser.persistence.domain;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * @author Dick Schoeller
 *
 * @param <G> the associated GedObject subclass
 */
public abstract class GedDocument <G extends GedObject> {
    /** */
    @Id
    private String idString;

    /** */
    private String type;

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
    private final List<GedDocument<? extends GedObject>> attributes =
            new ArrayList<>();

    /** */
    @Transient
    private G gedObject;

    /**
     * @return the idString
     */
    public final String getIdString() {
        return idString;
    }

    /**
     * @param idString the idString to set
     */
    public final void setIdString(final String idString) {
        this.idString = idString;
    }

    /**
     * @return the type
     */
    public final String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public final void setType(final String type) {
        this.type = type;
    }

    /**
     * @return the string
     */
    public final String getString() {
        return string;
    }

    /**
     * @param string the string to set
     */
    public final void setString(final String string) {
        this.string = string;
    }

    /**
     * @return the filename
     */
    public final String getFilename() {
        return filename;
    }

    /**
     * @param filename the filename to set
     */
    public final void setFilename(final String filename) {
        this.filename = filename;
    }


    /**
     * @return the DB name
     */
    public final String getDbName() {
        return dbName;
    }

    /**
     * @param dbName the DB name to set
     */
    public final void setDbName(final String dbName) {
        this.dbName = dbName;
    }

    /**
     * @return the gedObject
     */
    public final G getGedObject() {
        return gedObject;
    }

    /**
     * @param gedObject the gedObject to set
     */
    public final void setGedObject(final G gedObject) {
        this.gedObject = gedObject;
    }


    /**
     * @return the attributes
     */
    public final List<GedDocument<? extends GedObject>> getAttributes() {
        return attributes;
    }

    /**
     * @param attributes a new set of attributes
     */
    public final void setAttributes(
            final List<GedDocument<? extends GedObject>> attributes) {
        this.attributes.clear();
        this.attributes.addAll(attributes);
    }

    /**
     * Empty out the attributes list.
     */
    public final void clearAttributes() {
        attributes.clear();
    }

    /**
     * @param gedAttributes load in all of the attributes (sub-objects)
     */
    protected final void loadAttributes(final List<GedObject> gedAttributes) {
        this.attributes.clear();
        for (final GedObject ged : gedAttributes) {
            // TODO this happens because appenders create a list entry when they
            // are not retained.
            if (ged == null) {
                continue;
            }
            final GedDocument<?> documentAttribute = GedDocumentFactory
                    .getInstance().createGedDocument(ged);
            this.attributes.add(documentAttribute);
        }
    }

    /**
     * This method is used to assign the GedObject and to pass its values along
     * to the fields for persistence.
     *
     * @param ged the associated gedObject
     */
    public abstract void loadGedObject(final GedObject ged);
}
