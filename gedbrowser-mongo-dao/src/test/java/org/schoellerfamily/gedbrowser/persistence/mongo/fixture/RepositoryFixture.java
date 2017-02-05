package org.schoellerfamily.gedbrowser.persistence.mongo.fixture;

import java.io.IOException;
import java.util.Map;

import org.schoellerfamily.gedbrowser.dao.mongo.TestDataReader;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.FamilyDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.GedDocumentMongoFactory;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.HeadDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.PersonDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SourceDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SubmittorDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.TrailerDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.FamilyDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.HeadDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.PersonDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RootDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.SourceDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.SubmittorDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.TrailerDocumentRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Dick Schoeller
 */
public final class RepositoryFixture {
    /** */
    @Autowired
    private transient RootDocumentRepositoryMongo rootDocumentRepository;

    /** */
    @Autowired
    private transient PersonDocumentRepositoryMongo personDocumentRepository;

    /** */
    @Autowired
    private transient FamilyDocumentRepositoryMongo familyDocumentRepository;

    /** */
    @Autowired
    private transient SourceDocumentRepositoryMongo sourceDocumentRepository;

    /** */
    @Autowired
    private transient HeadDocumentRepositoryMongo headDocumentRepository;

    /** */
    @Autowired
    private transient SubmittorDocumentRepositoryMongo
        submittorDocumentRepository;

    /** */
    @Autowired
    private transient TrailerDocumentRepositoryMongo trailerDocumentRepository;

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
        rootDocumentRepository.save(rootdoc);

        // TODO we aren't saving root.

        final Map<String, GedObject> map = root.getObjects();
        for (final GedObject ged : map.values()) {
            final GedDocument<?> gedDoc = GedDocumentMongoFactory.getInstance().
                    createGedDocument(ged);
            if (gedDoc instanceof PersonDocumentMongo) {
                personDocumentRepository.save((PersonDocumentMongo) gedDoc);
            } else if (gedDoc instanceof FamilyDocumentMongo) {
                familyDocumentRepository.save((FamilyDocumentMongo) gedDoc);
            } else if (gedDoc instanceof SourceDocumentMongo) {
                sourceDocumentRepository.save((SourceDocumentMongo) gedDoc);
            } else if (gedDoc instanceof HeadDocumentMongo) {
                headDocumentRepository.save((HeadDocumentMongo) gedDoc);
            } else if (gedDoc instanceof SubmittorDocumentMongo) {
                submittorDocumentRepository
                        .save((SubmittorDocumentMongo) gedDoc);
            } else if (gedDoc instanceof TrailerDocumentMongo) {
                trailerDocumentRepository.save((TrailerDocumentMongo) gedDoc);
            }
        }
        return root;
    }

    /**
     * Clear out all of the tables in the repository.
     */
    public void clearRepository() {
        rootDocumentRepository.deleteAll();
        personDocumentRepository.deleteAll();
        familyDocumentRepository.deleteAll();
        sourceDocumentRepository.deleteAll();
        headDocumentRepository.deleteAll();
        submittorDocumentRepository.deleteAll();
        trailerDocumentRepository.deleteAll();
    }
}
