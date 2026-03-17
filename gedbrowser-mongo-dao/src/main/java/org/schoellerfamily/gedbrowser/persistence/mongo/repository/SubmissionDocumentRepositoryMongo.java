package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmissionDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    SubmissionDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;
import org.springframework.data.repository.CrudRepository;

/**
 * Defines persistence operations for submission document repository mongo.
 *
 * @author Richard Schoeller
 */
public interface SubmissionDocumentRepositoryMongo extends
    CrudRepository<SubmissionDocumentMongo, String>,
    FindableDocument<Submission, SubmissionDocument>  {
}
