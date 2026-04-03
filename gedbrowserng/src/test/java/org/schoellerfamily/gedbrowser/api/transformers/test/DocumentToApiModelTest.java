package org.schoellerfamily.gedbrowser.api.transformers.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.transformers.DocumentToApiModelVisitor;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.GedDocumentLoader;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocumentVisitor;
import org.schoellerfamily.gedbrowser.persistence.domain.TrailerDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.TrailerDocumentMongo;

/**
 * Contains tests for document to api model.
 *
 * @author Richard Schoeller
 */
class DocumentToApiModelTest {
    @Test
    void basicTrailerTest() {
        final TrailerDocument document = new TrailerDocumentMongo();
        final DocumentToApiModelVisitor visitor = new DocumentToApiModelVisitor();

        document.accept(visitor);
        final ApiObject baseObject = visitor.getBaseObject();
        assertEquals("trailer", baseObject.getType(), "type mismatch");
    }

    @Test
    void basicObjectTest() {
        final GedDocument<GedObject> document = new GedDocumentStub();
        final DocumentToApiModelVisitor visitor = new DocumentToApiModelVisitor();

        document.accept(visitor);
        final ApiObject baseObject = visitor.getBaseObject();
        assertEquals("TYPE", baseObject.getType(), "type mismatch");
    }

    /**
     * @author Richard Schoeller
     */
    private final class GedDocumentStub implements GedDocument<GedObject> {
        /**
         * Returns the id string.
         *
         * @return the id string
         */
        @Override
        public String getIdString() {
            return "XXXX";
        }

        /**
         * Sets the id string.
         *
         * @param idString the id string
         */
        @Override
        public void setIdString(final String idString) {
            // Empty
        }

        /**
         * Returns the type.
         *
         * @return the type
         */
        @Override
        public String getType() {
            return "TYPE";
        }

        /**
         * Returns the string.
         *
         * @return the string
         */
        @Override
        public String getString() {
            return "STRING";
        }

        /**
         * Sets the string.
         *
         * @param string the string
         */
        @Override
        public void setString(final String string) {
            // Empty
        }

        /**
         * Returns the filename.
         *
         * @return the filename
         */
        @Override
        public String getFilename() {
            return "FILENAME.GED";
        }

        /**
         * Sets the filename.
         *
         * @param filename the filename to use
         */
        @Override
        public void setFilename(final String filename) {
            // Empty
        }

        /**
         * Returns the db name.
         *
         * @return the db name
         */
        @Override
        public String getDbName() {
            return "FILENAME";
        }

        /**
         * Sets the db name.
         *
         * @param dbName the db name to use
         */
        @Override
        public void setDbName(final String dbName) {
            // Empty
        }

        /**
         * Returns the ged object.
         *
         * @return the ged object
         */
        @Override
        public GedObject getGedObject() {
            return null;
        }

        /**
         * Sets the ged object.
         *
         * @param gedObject the ged object
         */
        @Override
        public void setGedObject(final GedObject gedObject) {
            // Empty
        }

        /**
         * Returns the attributes.
         *
         * @return the attributes
         */
        @Override
        public List<GedDocument<? extends GedObject>> getAttributes() {
            return Collections.emptyList();
        }

        /**
         * Sets the attributes.
         *
         * @param attributes the attributes
         */
        @Override
        public void setAttributes(final List<GedDocument<? extends GedObject>> attributes) {
            // Empty
        }

        /**
         * Executes add attribute.
         *
         * @param attribute the attribute
         */
        @Override
        public void addAttribute(final GedDocument<?> attribute) {
            // Empty
        }

        /**
         * Executes clear attributes.
         */
        @Override
        public void clearAttributes() {
            // Empty
        }

        /**
         * Loads the ged object.
         *
         * @param loader the loader
         * @param ged the ged
         */
        @Override
        public void loadGedObject(final GedDocumentLoader loader, final GedObject ged) {
            // Empty
        }

        /**
         * Executes accept.
         *
         * @param visitor the visitor
         */
        @Override
        public void accept(final GedDocumentVisitor visitor) {
            visitor.visit(this);
        }
    }
}
