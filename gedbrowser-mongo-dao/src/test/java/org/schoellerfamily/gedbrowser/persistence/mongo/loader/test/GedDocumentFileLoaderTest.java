package org.schoellerfamily.gedbrowser.persistence.mongo.loader.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.test.MongoTestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
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
    @Test
    public void testReset() {
        loader.loadDocument("mini-schoeller");
        loader.reset();
        assertRepositoryEmpty();
    }

    /** */
    @Test
    public void testLoad() {
        loader.reset();
        assertJustThisOne(loader.loadDocument("mini-schoeller"));
    }

    /** */
    @Test
    public void testBadLoad() {
        loader.reset();
        assertNull("Should be not found", loader.loadDocument("foofy"));
    }

    /** */
    @Test
    public void testReloadAll() {
        loader.reset();
        loader.loadDocument("mini-schoeller");
        loader.reloadAll();
        assertRepositoryHasOne();
    }

    /** */
    @Test
    public void testDetails() {
        loader.reset();
        loader.loadDocument("mini-schoeller");
        final Map<String, Object> details = loader.details("mini-schoeller");
        assertDetails(details);
    }

    /** */
    @Test
    public void testAllDetails() {
        loader.reset();
        loader.loadDocument("mini-schoeller");
        for (final Map<String, Object> details : loader.details()) {
            assertDetails(details);
        }
    }

    /**
     * @param details the details map
     */
    private void assertDetails(final Map<String, Object> details) {
        assertEquals("dbname mismatch", "mini-schoeller",
                details.get("dbname"));
        assertEquals("filename mismatch",
                "/var/lib/gedbrowser/mini-schoeller.ged",
                details.get("filename"));
        final long expectedPersonsCount = 20;
        final long expectedFamiliesCount = 7;
        final long expectedSourceCount = 11;
        assertEquals("person count mismatch",
                expectedPersonsCount, details.get("persons"));
        assertEquals("family count mismatch",
                expectedFamiliesCount, details.get("families"));
        assertEquals("sources count mismatch",
                expectedSourceCount, details.get("sources"));
    }

    /** */
    @Test
    public void testReload() {
        loader.reset();
        final RootDocument rootDocument1 =
                loader.loadDocument("mini-schoeller");
        final RootDocument resultDoc =
                loader.loadDocument("mini-schoeller");
        checkSame(rootDocument1, resultDoc);
        assertJustThisOne(resultDoc);
    }

    /**
     * Assert that we should have only the newly loaded document
     * this because we first cleared everything.
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
        assertEquals("should be the same DB object",
                doc1.getIdString(), doc2.getIdString());
    }

    /**
     * Check that the repository is empty.
     */
    private void assertRepositoryEmpty() {
        for (final RootDocument doc : findAll()) {
            fail("should not have found document " + doc.getDbName());
        }
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
        assertEquals("Count should be 1", 1, count);
    }

    /**
     * @return all of the root documents in the repository
     */
    private Iterable<RootDocumentMongo> findAll() {
        return repositoryManager.getRootDocumentRepository().findAll();
    }
}
