package org.schoellerfamily.gedbrowser.persistence.mongo.fixture;

import java.io.IOException;
import java.util.Map;

import org.schoellerfamily.gedbrowser.dao.mongo.TestDataReader;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.GedDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.GedDocumentMongoFactory;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.GedDocumentMongoVisitor;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.SaveVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author Dick Schoeller
 */
public final class RepositoryFixture {
    /** */
    @Autowired
    private transient RepositoryManagerMongo repositoryManager;

    /** */
    @Autowired
    private transient MongoTemplate mongoTemplate;

    /**
     * This is private because this is a singleton.
     */
    public RepositoryFixture() {
        // Empty constructor.
    }

    /**
     * Clear and reload all of the tables in the repository.
     *
     * @return the Root document
     * @throws IOException if there is a problem reading the file
     */
    public Root loadRepository() throws IOException {
        clearRepository();

        final Root root = (Root) TestDataReader.getInstance().
                readBigTestSource();
        root.setFilename("bigtest.ged");
        root.setDbName("bigtest");

        final RootDocumentMongo rootdoc =
                (RootDocumentMongo) GedDocumentMongoFactory.getInstance()
                        .createGedDocument(root);
        repositoryManager.getRootDocumentRepository().save(rootdoc);

        final Map<String, GedObject> map = root.getObjects();
        GedDocumentMongoVisitor visitor = new SaveVisitor(repositoryManager);

        for (final GedObject ged : map.values()) {
            final GedDocumentMongo<GedObject> gedDoc = GedDocumentMongoFactory
                    .getInstance().createGedDocument(ged);
            gedDoc.accept(visitor);
        }
        return root;
    }

    /**
     * Clear out all of the tables in the repository.
     */
    public void clearRepository() {
        repositoryManager.reset();
        mongoTemplate.getDb().dropDatabase();
    }
}
