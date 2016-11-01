package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * @author Dick Schoeller
 *
 * @param <G> the associated GedObject subclass
 */
public abstract class GedDocumentMongo<G extends GedObject>
        implements GedDocument<G> {
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

    @Override
    public final String getIdString() {
        return idString;
    }

    @Override
    public final void setIdString(final String idString) {
        this.idString = idString;
    }

    @Override
    public final String getType() {
        return type;
    }

    @Override
    public final void setType(final String type) {
        this.type = type;
    }

    @Override
    public final String getString() {
        return string;
    }

    @Override
    public final void setString(final String string) {
        this.string = string;
    }

    @Override
    public final String getFilename() {
        return filename;
    }

    @Override
    public final void setFilename(final String filename) {
        this.filename = filename;
    }


    @Override
    public final String getDbName() {
        return dbName;
    }

    @Override
    public final void setDbName(final String dbName) {
        this.dbName = dbName;
    }

    @Override
    public final G getGedObject() {
        return gedObject;
    }

    @Override
    public final void setGedObject(final G gedObject) {
        this.gedObject = gedObject;
    }


    @Override
    public final List<GedDocument<? extends GedObject>> getAttributes() {
        return attributes;
    }

    @Override
    public final void setAttributes(
            final List<GedDocument<? extends GedObject>> attributes) {
        this.attributes.clear();
        this.attributes.addAll(attributes);
    }

    @Override
    public final void clearAttributes() {
        attributes.clear();
    }

    @Override
    public final void loadAttributes(final List<GedObject> gedAttributes) {
        this.attributes.clear();
        for (final GedObject ged : gedAttributes) {
            // TODO this happens because appenders create a list entry when they
            // are not retained.
            if (ged == null) {
                continue;
            }
            final GedDocument<?> documentAttribute = GedDocumentMongoFactory
                    .getInstance().createGedDocument(ged);
            this.attributes.add(documentAttribute);
        }
    }

    @Override
    public abstract void loadGedObject(final GedObject ged);
}
