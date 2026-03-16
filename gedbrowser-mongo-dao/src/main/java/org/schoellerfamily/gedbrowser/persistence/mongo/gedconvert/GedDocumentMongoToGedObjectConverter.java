package org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.finder.FinderStrategy;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.GedDocumentMongo;

/**
 * @author Dick Schoeller
 *
 */
public class GedDocumentMongoToGedObjectConverter {
    /**
     * Creates the ged object.
     *
     * @param parent the parent
     * @param document the document
     * @return the resulting ged object
     */
    public GedObject createGedObject(final GedObject parent,
            final GedDocument<?> document) {
        if (document == null) {
            throw new PersistenceException("whoops: null document");
        }

        final GedDocumentMongo<? extends GedObject> documentMongo =
                (GedDocumentMongo<? extends GedObject>) document;
        final GedDocumentMongoToGedObjectConverterVisitor visitor =
                new GedDocumentMongoToGedObjectConverterVisitor(parent);
        documentMongo.accept(visitor);
        final GedObject retval = visitor.getGedObject();

        for (final GedDocument<?> subDocument : document.getAttributes()) {
            final GedObject subGed = createGedObject(retval, subDocument);
            retval.addAttribute(subGed);
        }
        return retval;
    }

    /**
     * Creates the root.
     *
     * @param document the document
     * @param finder the finder
     * @return the resulting root
     */
    public Root createRoot(final RootDocument document,
            final FinderStrategy finder) {
        final Root root = new Root("Root", finder);
        root.setFilename(document.getFilename());
        root.setDbName(document.getDbName());
        return root;
    }
}
