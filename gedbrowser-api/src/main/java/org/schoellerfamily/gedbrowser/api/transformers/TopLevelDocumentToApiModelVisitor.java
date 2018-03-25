package org.schoellerfamily.gedbrowser.api.transformers;

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
 * @author Dick Schoeller
 */
public class TopLevelDocumentToApiModelVisitor
        implements TopLevelGedDocumentVisitor, LifespanBuilder,
        SourceTitleBuilder, SubmitterNameBuilder {

    /** The base object created. */
    private ApiObject baseObject;

    /**
     * @return get the base object after a visit
     */
    public final ApiObject getBaseObject() {
        return baseObject;
    }

    /**
     * @param baseObject the base object created from this visit
     */
    public final void setBaseObject(final ApiObject baseObject) {
        this.baseObject = baseObject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void visit(final PersonDocument document) {
        final ApiLifespan lifespan = buildLifespan(document);
        setBaseObject(new ApiPerson(document.getType(), document.getString(),
                document.getIndexName(), document.getSurname(), lifespan));
        addPersonAttributes(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void visit(final FamilyDocument document) {
        setBaseObject(new ApiFamily(document.getType(), document.getString()));
        addAttributes(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void visit(final SourceDocument document) {
        setBaseObject(new ApiSource(document.getType(), document.getString(),
                title(document)));
        addAttributes(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void visit(final HeadDocument document) {
        setBaseObject(new ApiHead(document.getType(), document.getString()));
        addAttributes(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void visit(final SubmissionDocument document) {
        setBaseObject(
                new ApiSubmission(document.getType(), document.getString()));
        addAttributes(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void visit(final SubmitterDocument document) {
        setBaseObject(new ApiSubmitter(document.getType(), document.getString(),
                name(document)));
        addAttributes(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void visit(final TrailerDocument document) {
        setBaseObject(new ApiObject(document.getType(),
                document.getString()));
        addAttributes(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void visit(final NoteDocument document) {
        setBaseObject(new ApiNote(document.getType(), document.getString(),
                document.getTail()));
        addAttributes(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void visit(final GedDocument<? extends GedObject> document) {
        setBaseObject(new ApiObject(document.getType(),
                document.getString()));
        addAttributes(document);
    }

    /**
     * Recurse into the child documents converting and adding to the list.
     *
     * @param document the current document
     */
    protected void addAttributes(final GedDocument<?> document) {
        for (final GedDocument<? extends GedObject> attribute : document
                .getAttributes()) {
            final DocumentToApiModelVisitor v = createVisitor();
            attribute.accept(v);
            baseObject.getAttributes()
                    .add(convertToAttribute(v.getBaseObject()));
        }
    }

    /**
     * Recurse into the child documents converting and adding to the list.
     * Person is handled specially because it breaks the list up.
     *
     * @param document the current document
     */
    protected void addPersonAttributes(final GedDocument<?> document) {
        for (final GedDocument<? extends GedObject> attribute : document
                .getAttributes()) {
            final DocumentToApiModelVisitor v = createVisitor();
            attribute.accept(v);
            ((ApiPerson) baseObject)
                    .addAttribute(convertToAttribute(v.getBaseObject()));
        }
    }

    /**
     * @param object the object to copy or return
     * @return the object or new object with fields copied
     */
    private ApiAttribute convertToAttribute(final ApiObject object) {
        if (object.getClass() == ApiAttribute.class) {
            return (ApiAttribute) object;
        }
        return new ApiAttribute(object.getType(), object.getString(),
                object.getAttributes(), "");
    }

    /**
     * @return the visitor
     */
    private DocumentToApiModelVisitor createVisitor() {
        return new DocumentToApiModelVisitor();
    }
}
