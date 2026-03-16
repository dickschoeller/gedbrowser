package org.schoellerfamily.gedbrowser.api.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.schoellerfamily.gedbrowser.api.endpoint.CleanupDuplicatesEndpoint;
import org.schoellerfamily.gedbrowser.api.endpoint.DuplicateCleanupService;
import org.schoellerfamily.gedbrowser.api.endpoint.LogDuplicatesEndpoint;
import org.schoellerfamily.gedbrowser.api.endpoint.ApplicationHealthIndicator;
import org.schoellerfamily.gedbrowser.api.endpoint.ApplicationInfoContributor;
import org.schoellerfamily.gedbrowser.api.endpoint.RestoreEndpoint;
import org.schoellerfamily.gedbrowser.api.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.health.contributor.Health;



/**
 * Contains tests for endpoint components.
 */
@SuppressWarnings("PMD.UnitTestContainsTooManyAsserts")
final class EndpointComponentsTest {

    @Test
    void testApplicationHealthIndicatorIncludesVersionAndDatasetDetails() {
        final ApplicationInfo appInfo = Mockito.mock(ApplicationInfo.class);
        final GedObjectFileLoader loader = Mockito.mock(GedObjectFileLoader.class);
        final RepositoryManagerMongo manager = Mockito.mock(RepositoryManagerMongo.class);
        final List<Map<String, Object>> datasets = List.of(Map.of("db", "mini"));

        when(appInfo.getVersion()).thenReturn("1.2.3");
        when(loader.details(manager)).thenReturn(datasets);

        final ApplicationHealthIndicator indicator =
            new ApplicationHealthIndicator(appInfo, loader, manager);

        final Health health = indicator.health();

        assertEquals("1.2.3", health.getDetails().get("version"));
        assertEquals(datasets, health.getDetails().get("datasets"));
    }

    @Test
    void testApplicationInfoContributorAddsAppMap() {
        final ApplicationInfo appInfo = Mockito.mock(ApplicationInfo.class);
        when(appInfo.getInfoMap()).thenReturn(Map.of("version", "1.2.3"));

        final ApplicationInfoContributor contributor = new ApplicationInfoContributor(appInfo);
        final Info.Builder builder = new Info.Builder();

        contributor.contribute(builder);

        final Info info = builder.build();
        assertTrue(info.getDetails().containsKey("app"));
    }

    @Test
    void testRestoreEndpointReloadsAndReportsDatasetCount() {
        final GedObjectFileLoader loader = Mockito.mock(GedObjectFileLoader.class);
        final RepositoryManagerMongo manager = Mockito.mock(RepositoryManagerMongo.class);
        when(loader.details(manager)).thenReturn(List.of(Map.of("db", "a"), Map.of("db", "b")));

        final RestoreEndpoint endpoint = new RestoreEndpoint(loader, manager);

        final List<String> result = endpoint.invoke();

        verify(loader).reloadAll(manager);
        assertEquals("Reloaded 2 datasets", result.get(0));
    }

    @Test
    void testCleanupDuplicatesEndpointReportsCounts() {
        final int rootCount = 1;
        final int personCount = 2;
        final int sourceCount = 3;
        final int submitterCount = 4;
        final int totalCount = 10;
        final DuplicateCleanupService cleanupService = Mockito.mock(DuplicateCleanupService.class);
        when(cleanupService.cleanup()).thenReturn(Map.of(
            "roots", rootCount,
            "persons", personCount,
            "sources", sourceCount,
            "submitters", submitterCount,
            "total", totalCount));

        final CleanupDuplicatesEndpoint endpoint = new CleanupDuplicatesEndpoint(cleanupService);

        final List<String> result = endpoint.invoke();

        verify(cleanupService).cleanup();
        assertEquals("Removed duplicates: roots=1, persons=2, sources=3, submitters=4, total=10",
            result.get(0));
    }

    @Test
    void testLogDuplicatesEndpointReportsCounts() {
        final int rootCount = 1;
        final int personCount = 2;
        final int sourceCount = 3;
        final int submitterCount = 4;
        final int totalCount = 10;
        final DuplicateCleanupService cleanupService = Mockito.mock(DuplicateCleanupService.class);
        when(cleanupService.logDuplicates()).thenReturn(Map.of(
            "roots", rootCount,
            "persons", personCount,
            "sources", sourceCount,
            "submitters", submitterCount,
            "total", totalCount));

        final LogDuplicatesEndpoint endpoint = new LogDuplicatesEndpoint(cleanupService);

        final List<String> result = endpoint.invoke();

        verify(cleanupService).logDuplicates();
        assertEquals("Logged duplicates: roots=1, persons=2, sources=3, submitters=4, total=10",
            result.get(0));
    }
}
