package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;

import lombok.Getter;
import lombok.Setter;



/**
 * Represents ged document mongo for persistence operations.
 *
 * @author Richard Schoeller
 * @param <G> the associated GedObject subclass
 */
@Setter
@Getter
public abstract class GedDocumentMongo<G extends GedObject>
        extends HasAttributes<G> implements Accepts {
    /**
     * Creates a new GedDocumentMongo.
     */
    public GedDocumentMongo() {
    }

    /**
     * The id string value.
     */
    @Id
    private String idString;

    /**
     * The string value.
     */
    @Indexed
    private String string;

    /**
     * The filename value.
     */
    @Indexed
    private String filename;

    /**
     * The db name value.
     */
    @Indexed
    private String dbName;

    /**
     * The ged object value.
     */
    @Transient
    private G gedObject;
}
