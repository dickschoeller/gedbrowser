package org.schoellerfamily.gedbrowser.persistence.fixture;

import java.io.IOException;
import java.util.Map;

import org.schoellerfamily.gedbrowser.dao.mongo.TestDataReader;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.persistence.domain.FamilyDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocumentFactory;
import org.schoellerfamily.gedbrowser.persistence.domain.HeadDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.PersonDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SourceDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmittorDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.TrailerDocument;
import org.schoellerfamily.gedbrowser.persistence.repository.
    FamilyDocumentRepository;
import org.schoellerfamily.gedbrowser.persistence.repository.
    HeadDocumentRepository;
import org.schoellerfamily.gedbrowser.persistence.repository.
    PersonDocumentRepository;
import org.schoellerfamily.gedbrowser.persistence.repository.
    RootDocumentRepository;
import org.schoellerfamily.gedbrowser.persistence.repository.
    SourceDocumentRepository;
import org.schoellerfamily.gedbrowser.persistence.repository.
    SubmittorDocumentRepository;
import org.schoellerfamily.gedbrowser.persistence.repository.
    TrailerDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Dick Schoeller
 */
public final class RepositoryFixture {
    /** */
    @Autowired
    private transient RootDocumentRepository rootDocumentRepository;

    /** */
    @Autowired
    private transient PersonDocumentRepository personDocumentRepository;

    /** */
    @Autowired
    private transient FamilyDocumentRepository familyDocumentRepository;

    /** */
    @Autowired
    private transient SourceDocumentRepository sourceDocumentRepository;

    /** */
    @Autowired
    private transient HeadDocumentRepository headDocumentRepository;

    /** */
    @Autowired
    private transient SubmittorDocumentRepository
        submittorDocumentRepository; // NOPMD

    /** */
    @Autowired
    private transient TrailerDocumentRepository trailerDocumentRepository;

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

        final RootDocument rootdoc = (RootDocument) GedDocumentFactory.
                getInstance().createGedDocument(root);
        rootDocumentRepository.save(rootdoc);

        // TODO we aren't saving root.

        final Map<String, GedObject> map = root.getObjects();
        for (final GedObject ged : map.values()) {
            final GedDocument<?> gedDoc = GedDocumentFactory.getInstance().
                    createGedDocument(ged);
            if (gedDoc instanceof PersonDocument) {
                personDocumentRepository.save((PersonDocument) gedDoc);
            } else if (gedDoc instanceof FamilyDocument) {
                familyDocumentRepository.save((FamilyDocument) gedDoc);
            } else if (gedDoc instanceof SourceDocument) {
                sourceDocumentRepository.save((SourceDocument) gedDoc);
            } else if (gedDoc instanceof HeadDocument) {
                headDocumentRepository.save((HeadDocument) gedDoc);
            } else if (gedDoc instanceof SubmittorDocument) {
                submittorDocumentRepository.save((SubmittorDocument) gedDoc);
            } else if (gedDoc instanceof TrailerDocument) {
                trailerDocumentRepository.save((TrailerDocument) gedDoc);
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
