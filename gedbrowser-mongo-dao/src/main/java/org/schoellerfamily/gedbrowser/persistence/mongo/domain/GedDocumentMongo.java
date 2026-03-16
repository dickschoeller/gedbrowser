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
 *
 * @param <G> the associated GedObject subclass
 */
@Setter
@Getter
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
}
