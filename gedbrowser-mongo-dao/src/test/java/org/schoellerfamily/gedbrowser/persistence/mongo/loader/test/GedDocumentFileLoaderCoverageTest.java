package org.schoellerfamily.gedbrowser.persistence.mongo.loader.test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RootDocumentRepositoryMongo;
import org.springframework.dao.DataAccessResourceFailureException;

/**
 * Coverage tests for GedDocumentFileLoader error paths.
 *
 * @author Dick Schoeller
 */
class GedDocumentFileLoaderCoverageTest {

    @Test
    void testBuildFileNameWithNullDbName() {
        final TestLoader loader = new TestLoader();
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> loader.callBuildFileName(null));
    }

    @Test
    void testSaveHandlesDataAccessException() throws Exception {
        final Root root = new Root();
        root.setDbName("test-db");

        final Class<?>[] classes = {
            RootDocumentRepositoryMongo.class
        };
        final RootDocumentRepositoryMongo repositoryProxy =
            (RootDocumentRepositoryMongo) Proxy.newProxyInstance(
                RootDocumentRepositoryMongo.class.getClassLoader(),
                classes,
                (proxy, method, args) -> {
                    if ("save".equals(method.getName())) {
                        throw new DataAccessResourceFailureException("boom");
                    }
                    return null;
                });

        final GedObjectToGedDocumentMongoConverter converter =
            new GedObjectToGedDocumentMongoConverter() {
                @SuppressWarnings("unchecked")
                @Override
                public RootDocumentMongo createGedDocument(
                    final org.schoellerfamily.gedbrowser.datamodel.GedObject ged) {
                    final RootDocumentMongo doc = new RootDocumentMongo();
                    doc.setGedObject((Root) ged);
                    return doc;
                }
            };

        final GedDocumentFileLoader loader = new GedDocumentFileLoader(null, null, converter,
            repositoryProxy, "home");

        final Method saveMethod = GedDocumentFileLoader.class.getDeclaredMethod("save", Root.class);
        saveMethod.setAccessible(true);
        final Object result = saveMethod.invoke(loader, root);
        assertNull(result, "Save should return null when DataAccessException occurs");
    }

    /**
     * Test subclass to expose protected methods.
     */
    private static final class TestLoader extends GedDocumentFileLoader {
        private TestLoader() {
            super(null, null, new GedObjectToGedDocumentMongoConverter(),
                (RootDocumentRepositoryMongo) Proxy.newProxyInstance(
                    RootDocumentRepositoryMongo.class.getClassLoader(),
                    new Class<?>[] {
                        RootDocumentRepositoryMongo.class
                    },
                    (proxy, method, args) -> null),
                "home");
        }

        private String callBuildFileName(final String dbName) {
            return buildFileName(dbName);
        }

    }
}
