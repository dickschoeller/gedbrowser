package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import java.util.Map;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implements a mixin that provides the submission repository.
 *
 * @author Dick Schoeller
 */
public interface SubmissionRepoProvider {
    /**
     * Provide the map to the derived class.
     *
     * @return the maps of classes to repository objects
     */
    Map<Class<? extends GedObject>, Object> getMap();

    /**
     * @param repository the repository
     */
    @Autowired
    default void setSubmissionDocumentRepository(
            final SubmissionDocumentRepositoryMongo repository) {
        getMap().put(Submission.class, repository);
    }

    /**
     * @return the repository
     */
    default SubmissionDocumentRepositoryMongo getSubmissionDocumentRepository() {
        return (SubmissionDocumentRepositoryMongo) getMap().get(Submission.class);
    }
}
