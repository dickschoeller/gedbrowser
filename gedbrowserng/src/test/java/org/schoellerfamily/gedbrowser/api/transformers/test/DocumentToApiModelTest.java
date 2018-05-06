package org.schoellerfamily.gedbrowser.api.transformers.test;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.transformers.DocumentToApiModelVisitor;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.GedDocumentLoader;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocumentVisitor;
import org.schoellerfamily.gedbrowser.persistence.domain.TrailerDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.TrailerDocumentMongo;

/**
 * @author Dick Schoeller
 */
public class DocumentToApiModelTest {
    /** */
    @Test
    public void basicTrailerTest() {
        final TrailerDocument document =
                new TrailerDocumentMongo();
        final DocumentToApiModelVisitor visitor =
                new DocumentToApiModelVisitor();

        document.accept(visitor);
        final ApiObject baseObject = visitor.getBaseObject();
        assertEquals("type mismatch", "trailer", baseObject.getType());
    }

    /** */
    @Test
    public void basicObjectTest() {
        final GedDocument<GedObject> document = new GedDocumentStub();
        final DocumentToApiModelVisitor visitor =
                new DocumentToApiModelVisitor();

        document.accept(visitor);
        final ApiObject baseObject = visitor.getBaseObject();
        assertEquals("type mismatch", "TYPE", baseObject.getType());
    }

    /**
     * @author Dick Schoeller
     */
    private final class GedDocumentStub implements GedDocument<GedObject> {
        /**
         * {@inheritDoc}
         */
        @Override
        public String getIdString() {
            return "XXXX";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setIdString(final String idString) {
            // Empty
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getType() {
            return "TYPE";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getString() {
            return "STRING";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setString(final String string) {
            // Empty
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getFilename() {
            return "FILENAME.GED";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setFilename(final String filename) {
            // Empty
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getDbName() {
            return "FILENAME";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setDbName(final String dbName) {
            // Empty
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public GedObject getGedObject() {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setGedObject(final GedObject gedObject) {
            // Empty
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<GedDocument<? extends GedObject>> getAttributes() {
            return Collections.emptyList();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setAttributes(
                final List<GedDocument<? extends GedObject>> attributes) {
            // Empty
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void addAttribute(
                final GedDocument<?> attribute) {
            // Empty
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clearAttributes() {
            // Empty
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void loadGedObject(final GedDocumentLoader loader,
                final GedObject ged) {
            // Empty
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void accept(final GedDocumentVisitor visitor) {
            visitor.visit(this);
        }
    }
}
