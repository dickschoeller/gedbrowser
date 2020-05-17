package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Dick Schoeller
 */
public class RepositoryManagerMongo extends MappedRepositoryManagerMongo
        implements RootRepoProvider, PersonRepoProvider, FamilyRepoProvider, SourceRepoProvider,
            HeadRepoProvider, SubmissionRepoProvider, SubmitterRepoProvider, TrailerRepoProvider,
            NoteRepoProvider {
    /**
     * Get a repository based on the class of ged object we are working with.
     *
     * @param clazz the class of ged object
     * @return the repository
     */
    @SuppressWarnings("unchecked")
    public final FindableDocument<? extends GedObject, ? extends GedDocument<?>>
        get(final Class<? extends GedObject> clazz) {
        return (FindableDocument<? extends GedObject, ? extends GedDocument<?>>)
                getMap().get(clazz);
    }

    /**
     * Clear all of the repositories in the dataset.
     */
    public void reset() {
        for (final Object repo : getMap().values()) {
            ((CrudRepository<?, ?>) repo).deleteAll();
        }
    }
}
