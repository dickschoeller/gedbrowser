package org.schoellerfamily.gedbrowser.api.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.schoellerfamily.gedbrowser.api.controller.DatasetsController;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RootDocumentRepositoryMongo;

/**
 * Tests for {@link DatasetsController}.
 */
final class DatasetsControllerTest {

    /** */
    @Test
    void testDbsReturnsExpectedCount() {
        final RepositoryManagerMongo manager = Mockito.mock(RepositoryManagerMongo.class);
        final RootDocumentRepositoryMongo repository =
            Mockito.mock(RootDocumentRepositoryMongo.class);
        final RootDocumentMongo first = new RootDocumentMongo();
        first.setDbName("alpha");
        final RootDocumentMongo second = new RootDocumentMongo();
        second.setDbName("beta");

        doReturn(repository).when(manager).get(Root.class);
        when(repository.findAll()).thenReturn(List.of(first, second));

        final DatasetsController controller = new DatasetsController(manager);
        final List<String> dbs = controller.dbs();

        assertEquals(2, dbs.size());
    }

    /** */
    @Test
    void testDbsReturnsFirstDatasetName() {
        final RepositoryManagerMongo manager = Mockito.mock(RepositoryManagerMongo.class);
        final RootDocumentRepositoryMongo repository =
            Mockito.mock(RootDocumentRepositoryMongo.class);
        final RootDocumentMongo first = new RootDocumentMongo();
        first.setDbName("alpha");
        final RootDocumentMongo second = new RootDocumentMongo();
        second.setDbName("beta");

        doReturn(repository).when(manager).get(Root.class);
        when(repository.findAll()).thenReturn(List.of(first, second));

        final DatasetsController controller = new DatasetsController(manager);
        final List<String> dbs = controller.dbs();

        assertEquals("alpha", dbs.get(0));
    }

    /** */
    @Test
    void testDbsReturnsEmptyListWhenNoDatasets() {
        final RepositoryManagerMongo manager = Mockito.mock(RepositoryManagerMongo.class);
        final RootDocumentRepositoryMongo repository =
            Mockito.mock(RootDocumentRepositoryMongo.class);

        doReturn(repository).when(manager).get(Root.class);
        when(repository.findAll()).thenReturn(List.of());

        final DatasetsController controller = new DatasetsController(manager);
        final List<String> dbs = controller.dbs();

        assertTrue(dbs.isEmpty());
    }
}
