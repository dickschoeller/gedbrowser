package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Dick Schoeller
 */
public class RepositoryManagerMongo {
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
     * @return the repository
     */
    public PersonDocumentRepositoryMongo getPersonDocumentRepository() {
        return personDocumentRepository;
    }

    /**
     * @return the repository
     */
    public FamilyDocumentRepositoryMongo getFamilyDocumentRepository() {
        return familyDocumentRepository;
    }

    /**
     * @return the repository
     */
    public SourceDocumentRepositoryMongo getSourceDocumentRepository() {
        return sourceDocumentRepository;
    }

    /**
     * @return the repository
     */
    public HeadDocumentRepositoryMongo getHeadDocumentRepository() {
        return headDocumentRepository;
    }

    /**
     * @return the repository
     */
    public SubmittorDocumentRepositoryMongo getSubmittorDocumentRepository() {
        return submittorDocumentRepository;
    }

    /**
     * @return the repository
     */
    public TrailerDocumentRepositoryMongo getTrailerDocumentRepository() {
        return trailerDocumentRepository;
    }
}
