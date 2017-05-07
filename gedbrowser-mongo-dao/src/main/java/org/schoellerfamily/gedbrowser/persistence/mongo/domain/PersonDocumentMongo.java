package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.persistence.GedDocumentLoader;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.domain.PersonDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.visitor.GedDocumentMongoVisitor;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.visitor.TopLevelGedDocumentMongoVisitor;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Dick Schoeller
 */
@Document(collection = "persons")
@CompoundIndexes({
    @CompoundIndex(name = "person_unique_idx",
            def = "{'string': 1, 'filename': 1}",
            unique = true)
})
public class PersonDocumentMongo extends GedDocumentMongo<Person>
        implements PersonDocument {
    /** */
    private String indexName;
    /** */
    private String surname;

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getType() {
        return "person";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void loadGedObject(final GedDocumentLoader loader,
            final GedObject ged) {
        if (!(ged instanceof Person)) {
            throw new PersistenceException("Wrong type");
        }
        final Person gedObject = (Person) ged;
        this.setGedObject(gedObject);
        this.setString(gedObject.getString());
        this.setFilename(gedObject.getFilename());
        loader.loadAttributes(this, gedObject.getAttributes());
        indexName = gedObject.getIndexName();
        surname = gedObject.getSurname();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getSurname() {
        return surname;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getIndexName() {
        return indexName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final TopLevelGedDocumentMongoVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final GedDocumentMongoVisitor visitor) {
        visitor.visit(this);
    }
}
