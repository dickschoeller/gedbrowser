package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.domain.TrailerDocument;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Dick Schoeller
 */
@Document(collection = "trailers")
@CompoundIndexes({
    @CompoundIndex(name = "unique_idx", def = "{'string': 1, 'filename': 1}")
})
public class TrailerDocumentMongo extends GedDocumentMongo<Trailer>
        implements TrailerDocument {
    /**
     * Constructor.
     */
    public TrailerDocumentMongo() {
        setType("trailer");
    }

    @Override
    public final void loadGedObject(final GedObject ged) {
        if (!(ged instanceof Trailer)) {
            throw new PersistenceException("Wrong type");
        }
        final Trailer gedObject = (Trailer) ged;
        this.setGedObject(gedObject);
        this.setString(gedObject.getString());
        this.setFilename(gedObject.getFilename());
        this.loadAttributes(gedObject.getAttributes());
    }
}
