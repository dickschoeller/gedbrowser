package org.schoellerfamily.gedbrowser.api.transformers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.schoellerfamily.gedbrowser.api.controller.exception.ObjectNotFoundException;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiHead;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.datamodel.util.GetStringComparator;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.HeadDocument;

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
     * @param <T> the data type returned
     * @param <V> the data type input
     * @param listIn list of FamilyDocument
     * @return list of ApiFamily
     */
    public final <T extends ApiObject, V extends GedDocument<?>> List<T>
            convert(final List<V> listIn) {
        final List<T> listOut = new ArrayList<>();
        for (final V family : listIn) {
            listOut.add(convert(family));
        }
        listOut.sort(new GetStringComparator());
        return listOut;
    }

    /**
     * @param <T> the data type returned
     * @param <V> the data type input
     * @param document the document to convert
     * @return the resulting object
     */
    @SuppressWarnings("unchecked")
    public final <T extends ApiObject, V extends GedDocument<?>> T convert(
            final V document) {
        final DocumentToApiModelVisitor v =
                new DocumentToApiModelVisitor();
        document.accept(v);
        return (T) v.getBaseObject();
    }

    /**
     * @param <V> the data type input
     * @param document the document to convert and find attributes
     * @return the resulting list of attributes
     */
    public final <V extends GedDocument<?>> List<ApiAttribute> attributes(
            final V document) {
        return convert(document).getAttributes();
    }

    /**
     * @param <V> the data type input
     * @param document the document to convert and find attributes
     * @param index the attribute index to return
     * @return the resulting attribute
     */
    public final <V extends GedDocument<?>> ApiAttribute attribute(
            final V document, final int index) {
        final List<ApiAttribute> attributes = attributes(document);
        if (index >= attributes.size()) {
            throw new ObjectNotFoundException(
                    "Attribute " + index
                    + " of " + typeString(document)
                    + " " + document.getString()
                    + " not found",
                    "attribute",
                    document.getString() + "/attributes/" + index,
                    document.getDbName());
        }
        return attributes.get(index);
    }

    /**
     * @param <V> the type of document input
     * @param document the document
     * @return it's simple name in lower case
     */
    private <V extends GedDocument<?>> String typeString(final V document) {
        return document.getGedObject().getClass()
                .getSimpleName().toLowerCase(Locale.ENGLISH);
    }

    /**
     * @param <V> the data type input
     * @param document the document to convert and find attributes
     * @param type the attribute type we want
     * @return the resulting list of attributes of the requested type
     */
    public final <V extends GedDocument<?>> List<ApiAttribute> attributes(
            final V document, final String type) {
        final List<ApiAttribute> list = new ArrayList<>();
        for (final ApiAttribute object : attributes(document)) {
            if (object.isType(type)) {
                list.add(object);
            }
        }
        return list;
    }

    /**
     * @param <V> the data type input
     * @param document the document to convert and find attributes
     * @param type the attribute type we want
     * @param index the index of the attribute of the type we want
     * @return the resulting attribute
     */
    public final <V extends GedDocument<?>> ApiObject attribute(
            final V document, final String type, final int index) {
        final List<ApiAttribute> list =
                attributes(document, type);
        if (index >= list.size()) {
            throw new ObjectNotFoundException(
                    type + " " + index
                    + " of " + typeString(document)
                    + " " + document.getString()
                    + " not found",
                    "attribute",
                    document.getString() + "/attributes/" + type + "/" + index,
                    document.getDbName());
        }
        return list.get(index);
    }
}
