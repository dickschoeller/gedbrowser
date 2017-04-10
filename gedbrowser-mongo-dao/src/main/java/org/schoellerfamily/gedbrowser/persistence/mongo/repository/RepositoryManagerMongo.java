package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import java.util.HashMap;
import java.util.Map;

import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;
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

    /**
     * Get the map that we need to go from class to repository.
     *
     * @return the map
     */
    protected Map<Class<? extends GedObject>,
    FindableDocument<? extends GedObject,
            ? extends GedDocument<?>>> getRepoMap() {
        /**
         * Holds the connections between ged classes and repositories.
         */
        final Map<Class<? extends GedObject>,
            FindableDocument<? extends GedObject,
                    ? extends GedDocument<?>>> repoMap = new HashMap<>();
        repoMap.put(Family.class, familyDocumentRepository);
        repoMap.put(Head.class, headDocumentRepository);
        repoMap.put(Person.class, personDocumentRepository);
        repoMap.put(Source.class, sourceDocumentRepository);
        repoMap.put(Submittor.class, submittorDocumentRepository);
        repoMap.put(Trailer.class, trailerDocumentRepository);
        return repoMap;
    }

}
