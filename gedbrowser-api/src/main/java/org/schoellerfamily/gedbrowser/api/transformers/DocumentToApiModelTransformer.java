package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiHead;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSource;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmission;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmitter;
import org.schoellerfamily.gedbrowser.persistence.domain.FamilyDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.HeadDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.PersonDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SourceDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmissionDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmitterDocument;

/**
 * @author Dick Schoeller
 */
public class DocumentToApiModelTransformer {
    /**
     * Constructor.
     */
    public DocumentToApiModelTransformer() {
        super();
    }

    /**
     * @param document the document to convert
     * @return the resulting object
     */
    public final ApiHead convert(final HeadDocument document) {
        final DocumentToApiModelVisitor v =
                new DocumentToApiModelVisitor();
        document.accept(v);
        return (ApiHead) v.getBaseObject();
    }

    /**
     * @param document the document to convert
     * @return the resulting object
     */
    public final ApiFamily convert(final FamilyDocument document) {
        final DocumentToApiModelVisitor v =
                new DocumentToApiModelVisitor();
        document.accept(v);
        return (ApiFamily) v.getBaseObject();
    }

    /**
     * @param document the document to convert
     * @return the resulting object
     */
    public final ApiPerson convert(final PersonDocument document) {
        final DocumentToApiModelVisitor v =
                new DocumentToApiModelVisitor();
        document.accept(v);
        return (ApiPerson) v.getBaseObject();
    }

    /**
     * @param document the document to convert
     * @return the resulting object
     */
    public final ApiSource convert(final SourceDocument document) {
        final DocumentToApiModelVisitor v =
                new DocumentToApiModelVisitor();
        document.accept(v);
        return (ApiSource) v.getBaseObject();
    }

    /**
     * @param document the document to convert
     * @return the resulting object
     */
    public final ApiSubmission convert(final SubmissionDocument document) {
        final DocumentToApiModelVisitor v =
                new DocumentToApiModelVisitor();
        document.accept(v);
        return (ApiSubmission) v.getBaseObject();
    }

    /**
     * @param document the document to convert
     * @return the resulting object
     */
    public final ApiSubmitter convert(final SubmitterDocument document) {
        final DocumentToApiModelVisitor v =
                new DocumentToApiModelVisitor();
        document.accept(v);
        return (ApiSubmitter) v.getBaseObject();
    }
}
