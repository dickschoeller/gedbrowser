package org.schoellerfamily.gedbrowser.api.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.api.controller.PersonGeoService;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.schoellerfamily.geoservice.client.GeoServiceClient;
import org.schoellerfamily.geoservice.model.GeoServiceItem;

/**
 * Unit tests for PersonGeoService modern-place enrichment behavior.
 */
class PersonGeoServiceUnitTest {

    @Test
    void testSyncPlacesOnUpdateDirectPlaceAttribute() {
        final GeoServiceClient geoServiceClient = mock(GeoServiceClient.class);
        final PersonGeoService service = new PersonGeoService(
            geoServiceClient,
            mock(ApplicationInfo.class),
            mock(CalendarProvider.class));

        final ApiPerson before = ApiPerson.builder()
            .string("I1")
            .indexName("Doe, John")
            .surname("Doe")
            .attribute(ApiAttribute.builder()
                .type("attribute")
                .string("Birth")
                .attribute(ApiAttribute.builder()
                    .type("place")
                    .string("Old Place")
                    .attribute(ApiAttribute.builder()
                        .type("attribute")
                        .string("Modern place")
                        .tail("Old Modern")
                        .build())
                    .build())
                .build())
            .build();

        final ApiPerson after = ApiPerson.builder()
            .string("I1")
            .indexName("Doe, John")
            .surname("Doe")
            .attribute(ApiAttribute.builder()
                .type("attribute")
                .string("Birth")
                .attribute(ApiAttribute.builder()
                    .type("place")
                    .string("Old Place")
                    .attribute(ApiAttribute.builder()
                        .type("attribute")
                        .string("Modern place")
                        .tail("New Modern")
                        .build())
                    .build())
                .build())
            .build();

        service.syncPlacesOnUpdate(before, after);

        verify(geoServiceClient).updateOrCreate(eq("Old Place"), eq("New Modern"));
    }

    @Test
    void testSyncPlacesOnUpdateLegacyPlaceAttribute() {
        final GeoServiceClient geoServiceClient = mock(GeoServiceClient.class);
        final PersonGeoService service = new PersonGeoService(
            geoServiceClient,
            mock(ApplicationInfo.class),
            mock(CalendarProvider.class));

        final ApiPerson before = ApiPerson.builder()
            .string("I1")
            .indexName("Doe, John")
            .surname("Doe")
            .attribute(ApiAttribute.builder()
                .type("attribute")
                .string("Birth")
                .attribute(ApiAttribute.builder()
                    .type("attribute")
                    .string("Place")
                    .tail("Old Place")
                    .build())
                .attribute(ApiAttribute.builder()
                    .type("attribute")
                    .string("Modern place")
                    .tail("Old Modern")
                    .build())
                .build())
            .build();

        final ApiPerson after = ApiPerson.builder()
            .string("I1")
            .indexName("Doe, John")
            .surname("Doe")
            .attribute(ApiAttribute.builder()
                .type("attribute")
                .string("Birth")
                .attribute(ApiAttribute.builder()
                    .type("attribute")
                    .string("Place")
                    .tail("Old Place")
                    .build())
                .attribute(ApiAttribute.builder()
                    .type("attribute")
                    .string("Modern place")
                    .tail("New Modern")
                    .build())
                .build())
            .build();

        service.syncPlacesOnUpdate(before, after);

        verify(geoServiceClient).updateOrCreate(eq("Old Place"), eq("New Modern"));
    }

    @Test
    void testEnrichModernPlacesAddsMissingModernPlace() {
        final GeoServiceClient geoServiceClient = mock(GeoServiceClient.class);
        when(geoServiceClient.get("Old Place")).thenReturn(
            new GeoServiceItem("Old Place", "New Place", null));

        final PersonGeoService service = new PersonGeoService(
            geoServiceClient,
            mock(ApplicationInfo.class),
            mock(CalendarProvider.class));

        final ApiPerson person = ApiPerson.builder()
            .string("I1")
            .indexName("Doe, John")
            .surname("Doe")
            .attribute(ApiAttribute.builder()
                .type("attribute")
                .string("Birth")
                .attribute(ApiAttribute.builder()
                    .type("place")
                    .string("Old Place")
                    .build())
                .build())
            .build();

        final ApiPerson enriched = service.enrichModernPlaces(person);

        final ApiAttribute birth = enriched.getAttributes().get(0);
        final ApiAttribute place = birth.getAttributes().stream()
            .filter(a -> "place".equals(a.getType()))
            .findFirst()
            .orElse(null);
        assertNotNull(place);
        final ApiAttribute modern = place.getAttributes().stream()
            .filter(a -> "Modern place".equals(a.getString()))
            .findFirst()
            .orElse(null);

        assertNotNull(modern);
        assertEquals("New Place", modern.getTail());
    }

    @Test
    void testEnrichModernPlacesPreservesExistingModernPlace() {
        final GeoServiceClient geoServiceClient = mock(GeoServiceClient.class);
        final PersonGeoService service = new PersonGeoService(
            geoServiceClient,
            mock(ApplicationInfo.class),
            mock(CalendarProvider.class));

        final ApiPerson person = ApiPerson.builder()
            .string("I1")
            .indexName("Doe, John")
            .surname("Doe")
            .attribute(ApiAttribute.builder()
                .type("attribute")
                .string("Birth")
                .attribute(ApiAttribute.builder()
                    .type("place")
                    .string("Old Place")
                    .attribute(ApiAttribute.builder()
                        .type("attribute")
                        .string("Modern place")
                        .tail("Already Modern")
                        .build())
                    .build())
                .build())
            .build();

        final ApiPerson enriched = service.enrichModernPlaces(person);

        final ApiAttribute place = enriched.getAttributes().get(0).getAttributes().stream()
            .filter(a -> "place".equals(a.getType()))
            .findFirst()
            .orElse(null);
        assertNotNull(place);
        final List<ApiAttribute> children = place.getAttributes();
        final long modernCount = children.stream()
            .filter(a -> "Modern place".equals(a.getString()))
            .count();
        final ApiAttribute modern = children.stream()
            .filter(a -> "Modern place".equals(a.getString()))
            .findFirst()
            .orElse(null);

        assertEquals(1, modernCount);
        assertNotNull(modern);
        assertEquals("Already Modern", modern.getTail());
    }

    @Test
    void testEnrichModernPlacesSkipsWhenLookupFails() {
        final GeoServiceClient geoServiceClient = mock(GeoServiceClient.class);
        when(geoServiceClient.get("Old Place")).thenThrow(new RuntimeException("boom"));

        final PersonGeoService service = new PersonGeoService(
            geoServiceClient,
            mock(ApplicationInfo.class),
            mock(CalendarProvider.class));

        final ApiPerson person = ApiPerson.builder()
            .string("I1")
            .indexName("Doe, John")
            .surname("Doe")
            .attribute(ApiAttribute.builder()
                .type("attribute")
                .string("Birth")
                .attribute(ApiAttribute.builder()
                    .type("place")
                    .string("Old Place")
                    .build())
                .build())
            .build();

        final ApiPerson enriched = service.enrichModernPlaces(person);

        final ApiAttribute place = enriched.getAttributes().get(0).getAttributes().stream()
            .filter(a -> "place".equals(a.getType()))
            .findFirst()
            .orElse(null);
        assertNotNull(place);
        final ApiAttribute modern = place.getAttributes().stream()
            .filter(a -> "Modern place".equals(a.getString()))
            .findFirst()
            .orElse(null);

        assertNull(modern);
    }
}
