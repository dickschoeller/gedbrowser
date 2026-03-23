package org.schoellerfamily.gedbrowser.api.transformers;

import java.util.List;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocumentVisitor;

/**
 * The visitor for the base GedDocument.
 *
 * @author Richard Schoeller
 */
/* default */ interface GedDocumentBaseVisitor extends GedDocumentVisitor {
    /**
     * Executes visit.
     *
     * @param document the document
     */
    default void visit(final GedDocument<? extends GedObject> document) {
        setBaseObject(createAttribute(document));
    }

    /**
     * Return the base object for the visitor chain.
     *
     * @return the base ApiObject
     */
    ApiObject getBaseObject();

    /**
     * Set the base object for the visitor chain.
     *
     * @param baseObject the base ApiObject
     */
    void setBaseObject(ApiObject baseObject);

    /**
     * @return the visitor
     */
    GedDocumentBaseVisitor createVisitor();

    /**
     * Recurse into the child documents converting and adding to the list.
     * Several document types require special processing because they have
     * split lists.
     *
     * @param document the current document
     * @return the list of converted attributes
     */
    default List<ApiAttribute> processAttributes(final GedDocument<?> document) {
        return document.getAttributes().stream()
            .map(attr -> {
                final GedDocumentBaseVisitor v = createVisitor();
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
     * Create an attribute for a link object.
     *
     * @param document the document to create a link attribute for
     * @return the created link attribute
     */
    default ApiAttribute createAttribute(final GedDocument<?> document) {
        return ApiAttribute.builder()
            .type(document.getType())
            .string(document.getString())
            .attributes(processAttributes(document))
            .build();
    }
}
