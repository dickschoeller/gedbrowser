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
 * Represents person document mongo for persistence operations.
 *
 * @author Richard Schoeller
 */
@Document(collection = "persons")
@CompoundIndexes({
    @CompoundIndex(name = "person_unique_idx",
            def = "{'string': 1, 'filename': 1}",
            unique = true)
})
public final class PersonDocumentMongo extends GedDocumentMongo<Person>
        implements PersonDocument {
    /** */
    private String indexName;
    /** */
    private String surname;

    /**
     * Gets the type.
     *
     * @return the type
     */
    @Override
    public String getType() {
        return "person";
    }

    /**
     * Loads the ged object.
     *
     * @param loader the loader
     * @param ged the ged
     */
    @Override
    public void loadGedObject(final GedDocumentLoader loader,
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
     * Gets the surname.
     *
     * @return the surname
     */
    @Override
    public String getSurname() {
        return surname;
    }

    /**
     * Gets the index name.
     *
     * @return the index name
     */
    @Override
    public String getIndexName() {
        return indexName;
    }

    /**
     * Executes accept.
     *
     * @param visitor the visitor
     */
    @Override
    public void accept(final TopLevelGedDocumentMongoVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Executes accept.
     *
     * @param visitor the visitor
     */
    @Override
    public void accept(final GedDocumentMongoVisitor visitor) {
        visitor.visit(this);
    }
}
