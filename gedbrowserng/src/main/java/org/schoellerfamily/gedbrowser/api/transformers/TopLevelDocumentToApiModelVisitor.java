package org.schoellerfamily.gedbrowser.api.transformers;

import java.util.List;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiHead;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiLifespan;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiNote;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSource;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmission;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmitter;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.domain.FamilyDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.HeadDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.NoteDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.PersonDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SourceDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmissionDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmitterDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.TopLevelGedDocumentVisitor;
import org.schoellerfamily.gedbrowser.persistence.domain.TrailerDocument;

/**
 * Visits top level document to api model elements and applies visitor logic.
 *
 * @author Richard Schoeller
 */
public abstract class TopLevelDocumentToApiModelVisitor
        implements HasBaseObject, TopLevelGedDocumentVisitor, LifespanBuilder,
        SourceTitleBuilder, SubmitterNameBuilder {

    /** The base object created. */
    private ApiObject baseObject;

    /**
     * Gets the base object.
     *
     * @return the base object
     */
    @Override
    public final ApiObject getBaseObject() {
        return baseObject;
    }

    /**
     * Sets the base object.
     *
     * @param baseObject the base object
     */
    public final void setBaseObject(final ApiObject baseObject) {
        this.baseObject = baseObject;
    }

    /**
     * Executes visit.
     *
     * @param document the document
     */
    @Override
    public final void visit(final PersonDocument document) {
        final ApiLifespan lifespan = buildLifespan(document);
        final ApiPerson person = ApiPerson.builder()
            .type(document.getType())
            .string(document.getString())
            .indexName(document.getIndexName())
            .surname(document.getSurname())
            .lifespan(lifespan)
            .attributes(processAttributes(document))
            .build();
        setBaseObject(person);
    }

    /**
     * Executes visit.
     *
     * @param document the document
     */
    @Override
    public final void visit(final FamilyDocument document) {
        // Use builder instead of direct constructor
        setBaseObject(ApiFamily.builder()
            .type(document.getType())
            .string(document.getString())
            .attributes(processAttributes(document))
            .build());
    }

    /**
     * Executes visit.
     *
     * @param document the document
     */
    @Override
    public final void visit(final SourceDocument document) {
        // Use builder so we can set the title field explicitly
        setBaseObject(ApiSource.builder()
            .type(document.getType())
            .string(document.getString())
            .title(title(document))
            .attributes(processAttributes(document))
            .build());
    }

    /**
     * Executes visit.
     *
     * @param document the document
     */
    @Override
    public final void visit(final HeadDocument document) {
        setBaseObject(ApiHead.builder()
            .type(document.getType())
            .string(document.getString())
            .attributes(processAttributes(document))
            .build());
    }

    /**
     * Executes visit.
     *
     * @param document the document
     */
    @Override
    public final void visit(final SubmissionDocument document) {
        setBaseObject(ApiSubmission.builder()
            .type(document.getType())
            .string(document.getString())
            .attributes(processAttributes(document))
            .build());
    }

    /**
     * Executes visit.
     *
     * @param document the document
     */
    @Override
    public final void visit(final SubmitterDocument document) {
        setBaseObject(ApiSubmitter.builder()
            .type(document.getType())
            .string(document.getString())
            .name(name(document))
            .attributes(processAttributes(document))
            .build());
    }

    /**
     * Executes visit.
     *
     * @param document the document
     */
    @Override
    public final void visit(final TrailerDocument document) {
        setBaseObject(ApiObject.builder()
            .type(document.getType())
            .string(document.getString())
            .attributes(processAttributes(document))
            .build());
    }

    /**
     * Executes visit.
     *
     * @param document the document
     */
    @Override
    public final void visit(final NoteDocument document) {
        setBaseObject(ApiNote.builder()
            .type(document.getType())
            .string(document.getString())
            .tail(document.getTail())
            .attributes(processAttributes(document))
            .build());
    }

    /**
     * Executes visit.
     *
     * @param document the document
     */
    @Override
    public final void visit(final GedDocument<? extends GedObject> document) {
        setBaseObject(ApiObject.builder()
            .type(document.getType())
            .string(document.getString())
            .attributes(processAttributes(document))
            .build());
    }

    /**
     * Recurse into the child documents converting and adding to the list.
     * Several document types require special processing because they have
     * split lists.
     *
     * @param document the current document
     * @return the list of converted attributes
     */
    protected List<ApiAttribute> processAttributes(final GedDocument<?> document) {
        return document.getAttributes().stream()
            .map(attr -> {
                final GedDocumentTransformerVisitor v = createVisitor();
                attr.accept(v);
                return convertToAttribute(v.getBaseObject());
            })
            .toList();
    }

    /**
     * @param object the object to copy or return
     * @return the object or new object with fields copied
     */
    private ApiAttribute convertToAttribute(final ApiObject object) {
        if (object.getClass() == ApiAttribute.class) {
            return (ApiAttribute) object;
        }
        return ApiAttribute.builder()
            .type(object.getType())
            .string(object.getString())
            .attributes(object.getAttributes())
            .tail("")
            .build();
    }

    /**
     * @return the visitor
     */
    public abstract GedDocumentTransformerVisitor createVisitor();
}
