package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.domain.SourceDocument;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Dick Schoeller
 */
@Document(collection = "sources")
@CompoundIndexes({
    @CompoundIndex(name = "unique_idx", def = "{'string': 1, 'filename': 1}")
})
public class SourceDocumentMongo extends GedDocumentMongo<Source>
        implements SourceDocument {
    /**
     * Constructor.
     */
    public SourceDocumentMongo() {
        setType("source");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void loadGedObject(final GedObject ged) {
        if (!(ged instanceof Source)) {
            throw new PersistenceException("Wrong type");
        }
        final Source gedObject = (Source) ged;
        this.setGedObject(gedObject);
        this.setString(gedObject.getString());
        this.setFilename(gedObject.getFilename());
        this.loadAttributes(gedObject.getAttributes());
    }
}
