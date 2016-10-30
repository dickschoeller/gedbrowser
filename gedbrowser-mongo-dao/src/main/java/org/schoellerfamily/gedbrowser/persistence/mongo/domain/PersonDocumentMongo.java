package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.domain.PersonDocument;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Dick Schoeller
 */
@Document(collection = "persons")
@CompoundIndexes({
    @CompoundIndex(name = "unique_idx", def = "{'string': 1, 'filename': 1}")
})
public class PersonDocumentMongo extends GedDocumentMongo<Person>
        implements PersonDocument {
    /** */
    private String indexName;
    /** */
    private String surname;

    /**
     * Constructor.
     */
    public PersonDocumentMongo() {
        setType("person");
    }

    @Override
    public final void loadGedObject(final GedObject ged) {
        if (!(ged instanceof Person)) {
            throw new PersistenceException("Wrong type");
        }
        final Person gedObject = (Person) ged;
        this.setGedObject(gedObject);
        this.setString(gedObject.getString());
        this.setFilename(gedObject.getFilename());
        this.loadAttributes(gedObject.getAttributes());
        indexName = gedObject.getIndexName();
        surname = gedObject.getSurname();
        // TODO may want to put in birth date handling for duplicate names
    }

    @Override
    public final String getSurname() {
        return surname;
    }

    @Override
    public final String getIndexName() {
        return indexName;
    }
}
