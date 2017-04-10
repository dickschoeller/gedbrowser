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
     * @return the repository
     */
    public final RootDocumentRepositoryMongo getRootDocumentRepository() {
        return rootDocumentRepository;
    }

    /**
     * @return the repository
     */
    public final PersonDocumentRepositoryMongo getPersonDocumentRepository() {
        return personDocumentRepository;
    }

    /**
     * @return the repository
     */
    public final FamilyDocumentRepositoryMongo getFamilyDocumentRepository() {
        return familyDocumentRepository;
    }

    /**
     * @return the repository
     */
    public final SourceDocumentRepositoryMongo getSourceDocumentRepository() {
        return sourceDocumentRepository;
    }

    /**
     * @return the repository
     */
    public final HeadDocumentRepositoryMongo getHeadDocumentRepository() {
        return headDocumentRepository;
    }

    /**
     * @return the repository
     */
    public final SubmittorDocumentRepositoryMongo
        getSubmittorDocumentRepository() {
        return submittorDocumentRepository;
    }

    /**
     * @return the repository
     */
    public final TrailerDocumentRepositoryMongo getTrailerDocumentRepository() {
        return trailerDocumentRepository;
    }

    /**
     * Get the map that we need to go from class to repository.
     *
     * @return the map
     */
    public final Map<Class<? extends GedObject>,
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

    /**
     * Clear all of the repositories in the dataset.
     */
    public void reset() {
        rootDocumentRepository.deleteAll();
        personDocumentRepository.deleteAll();
        familyDocumentRepository.deleteAll();
        sourceDocumentRepository.deleteAll();
        headDocumentRepository.deleteAll();
        submittorDocumentRepository.deleteAll();
        trailerDocumentRepository.deleteAll();
    }
}
