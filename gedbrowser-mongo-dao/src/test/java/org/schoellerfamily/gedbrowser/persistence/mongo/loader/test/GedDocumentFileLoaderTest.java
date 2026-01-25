package org.schoellerfamily.gedbrowser.persistence.mongo.loader.test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Iterator;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RootDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.test.MongoTestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { MongoTestConfiguration.class })
public class GedDocumentFileLoaderTest {
    /** */
    @Autowired
    private transient GedDocumentFileLoader loader;

    /**
     * Manages the persistence repository.
     */
    @Autowired
    private transient RepositoryManagerMongo repositoryManager;

    /** */
    @Autowired
    private transient RootDocumentRepositoryMongo rootDocumentRepository;

    /** */
    @Value("${gedbrowser.home:#{ systemProperties['user.dir'] }/src/test/resources}")
    private transient String gedbrowserHome;

    /** */
    @Test
    void testReset() {
        loader.loadDocument(repositoryManager, "mini-schoeller");
        loader.reset(repositoryManager);
        assertRepositoryEmpty();
    }

    /** */
    @Test
    void testLoad() {
        loader.reset(repositoryManager);
        assertJustThisOne(loader.loadDocument(repositoryManager, "mini-schoeller"));
    }

    /** */
    @Test
    void testBadLoad() {
        loader.reset(repositoryManager);
        assertNull(loader.loadDocument(repositoryManager, "foofy"), "Should be not found");
    }

    /** */
    @Test
    void testReloadAll() {
        loader.reset(repositoryManager);
        loader.loadDocument(repositoryManager, "mini-schoeller");
        loader.reloadAll(repositoryManager);
        assertRepositoryHasOne();
    }

    /** */
    @Test
    void testDetails() {
        loader.reset(repositoryManager);
        loader.loadDocument(repositoryManager, "mini-schoeller");
        final Map<String, Object> details = loader.details(repositoryManager, "mini-schoeller");
        assertDetails(details);
    }

    /** */
    @Test
    void testAllDetails() {
        loader.reset(repositoryManager);
        loader.loadDocument(repositoryManager, "mini-schoeller");
        for (final Map<String, Object> details : loader.details(repositoryManager)) {
            assertDetails(details);
        }
    }

    /**
     * @param details the details map
     */
    private void assertDetails(final Map<String, Object> details) {
        assertEquals("mini-schoeller", details.get("dbname"), "dbname mismatch");
        assertEquals(gedbrowserHome + "/mini-schoeller.ged", details.get("filename"),
            "filename mismatch");
        final long expectedPersonsCount = 20;
        final long expectedFamiliesCount = 7;
        final long expectedSourceCount = 11;
        assertEquals(expectedPersonsCount, details.get("persons"), "person count mismatch");
        assertEquals(expectedFamiliesCount, details.get("families"), "family count mismatch");
        assertEquals(expectedSourceCount, details.get("sources"), "sources count mismatch");
    }

    /** */
    @Test
    void testReload() {
        loader.reset(repositoryManager);
        final RootDocument rootDocument1 = loader.loadDocument(repositoryManager, "mini-schoeller");
        final RootDocument resultDoc = loader.loadDocument(repositoryManager, "mini-schoeller");
        checkSame(rootDocument1, resultDoc);
        assertJustThisOne(resultDoc);
    }

    /**
     * Assert that we should have only the newly loaded document this because we
     * first cleared everything.
     *
     * @param resultDoc the newly loaded document
     */
    private void assertJustThisOne(final RootDocument resultDoc) {
        for (final RootDocument foundDoc : findAll()) {
            checkSame(resultDoc, foundDoc);
        }
    }

    /**
     * Check that the 2 documents are actually the same object.
     *
     * @param doc1 first document
     * @param doc2 second document
     */
    private void checkSame(final RootDocument doc1, final RootDocument doc2) {
        assertEquals(doc1.getIdString(), doc2.getIdString(), "should be the same DB object");
    }

    /**
     * Check that the repository is empty.
     */
    private void assertRepositoryEmpty() {
        final Iterable<RootDocumentMongo> allDocs = findAll();
        assertThatExceptionOfType(java.util.NoSuchElementException.class)
            .isThrownBy(() -> allDocs.iterator().next());
    }

    /**
     * Check that the repository is empty.
     */
    private void assertRepositoryHasOne() {
        int count = 0;
        final Iterator<RootDocumentMongo> iterator = findAll().iterator();
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        assertEquals(1, count, "Count should be 1");
    }

    /**
     * @return all of the root documents in the repository
     */
    private Iterable<RootDocumentMongo> findAll() {
        return rootDocumentRepository.findAll();
    }
}
