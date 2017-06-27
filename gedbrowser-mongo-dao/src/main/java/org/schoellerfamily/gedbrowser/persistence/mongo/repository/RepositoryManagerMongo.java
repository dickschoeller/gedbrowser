package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import java.util.HashMap;
import java.util.Map;

import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Dick Schoeller
 */
public class RepositoryManagerMongo {
    /**
     * This map manages the relationship between GedObject classes and
     * repositories.
     */
    private final Map<Class<? extends GedObject>, Object>
        classToRepoMap = new HashMap<>();

    /**
     * @param repository
     *            the repository
     */
    @Autowired
    public void setRootDocumentRepository(
            final RootDocumentRepositoryMongo repository) {
        classToRepoMap.put(Root.class, repository);
    }

    /**
     * @return the repository
     */
    public final RootDocumentRepositoryMongo getRootDocumentRepository() {
        return (RootDocumentRepositoryMongo) classToRepoMap.get(Root.class);
    }

    /**
     * @param repository
     *            the repository
     */
    @Autowired
    public void setPersonDocumentRepository(
            final PersonDocumentRepositoryMongo repository) {
        classToRepoMap.put(Person.class, repository);
    }

    /**
     * @return the repository
     */
    public final PersonDocumentRepositoryMongo getPersonDocumentRepository() {
        return (PersonDocumentRepositoryMongo) classToRepoMap.get(Person.class);
    }

    /**
     * @param repository
     *            the repository
     */
    @Autowired
    public void setFamilyDocumentRepository(
            final FamilyDocumentRepositoryMongo repository) {
        classToRepoMap.put(Family.class, repository);
    }

    /**
     * @return the repository
     */
    public final FamilyDocumentRepositoryMongo getFamilyDocumentRepository() {
        return (FamilyDocumentRepositoryMongo) classToRepoMap.get(Family.class);
    }

    /**
     * @param repository
     *            the repository
     */
    @Autowired
    public void setSourceDocumentRepository(
            final SourceDocumentRepositoryMongo repository) {
        classToRepoMap.put(Source.class, repository);
    }

    /**
     * @return the repository
     */
    public final SourceDocumentRepositoryMongo getSourceDocumentRepository() {
        return (SourceDocumentRepositoryMongo) classToRepoMap.get(Source.class);
    }

    /**
     * @param repository
     *            the repository
     */
    @Autowired
    public void setHeadDocumentRepository(
            final HeadDocumentRepositoryMongo repository) {
        classToRepoMap.put(Head.class, repository);
    }

    /**
     * @return the repository
     */
    public final HeadDocumentRepositoryMongo getHeadDocumentRepository() {
        return (HeadDocumentRepositoryMongo) classToRepoMap.get(Head.class);
    }

    /**
     * @param repository
     *            the repository
     */
    @Autowired
    public void setSubmittorDocumentRepository(
            final SubmittorDocumentRepositoryMongo repository) {
        classToRepoMap.put(Submittor.class, repository);
    }

    /**
     * @return the repository
     */
    public final SubmittorDocumentRepositoryMongo
        getSubmittorDocumentRepository() {
        return (SubmittorDocumentRepositoryMongo) classToRepoMap
                .get(Submittor.class);
    }

    /**
     * @param repository
     *            the repository
     */
    @Autowired
    public void setTrailerDocumentRepository(
            final TrailerDocumentRepositoryMongo repository) {
        classToRepoMap.put(Trailer.class, repository);
    }

    /**
     * @return the repository
     */
    public final TrailerDocumentRepositoryMongo getTrailerDocumentRepository() {
        return (TrailerDocumentRepositoryMongo) classToRepoMap
                .get(Trailer.class);
    }

    /**
     * Get a repository based on the class of ged object we are working with.
     *
     * @param clazz
     *            the class of ged object
     * @return the repository
     */
    @SuppressWarnings("unchecked")
    public final FindableDocument<? extends GedObject, ? extends GedDocument<?>>
        get(final Class<? extends GedObject> clazz) {
        return (FindableDocument<? extends GedObject, ? extends GedDocument<?>>)
                classToRepoMap .get(clazz);
    }

    /**
     * Clear all of the repositories in the dataset.
     */
    public void reset() {
        for (final Object repo : classToRepoMap.values()) {
            ((CrudRepository<?, ?>) repo).deleteAll();
        }
    }
}
