package org.schoellerfamily.gedbrowser.api.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.api.controller.PersonGeoService;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.users.UserRoleName;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.schoellerfamily.gedbrowser.security.model.SecurityUser;
import org.schoellerfamily.gedbrowser.security.service.UserService;
import org.schoellerfamily.gedbrowser.security.util.RequestUserUtil;
import org.schoellerfamily.geoservice.client.GeoServiceClient;
import org.schoellerfamily.geoservice.model.GeoServiceItem;

/**
 * Unit tests for PersonGeoService modern-place enrichment behavior.
 */
class PersonGeoServiceUnitTest {

    @Test
    void testSyncPlacesOnCreateLegacyPlaceAttribute() {
        final GeoServiceClient geoServiceClient = mock(GeoServiceClient.class);
        final PersonGeoService service = new PersonGeoService(
            geoServiceClient,
            mock(ApplicationInfo.class),
            mock(CalendarProvider.class));

        final ApiFamily family = ApiFamily.builder()
            .string("F1")
            .attribute(ApiAttribute.builder()
                .type("attribute")
                .string("Marriage")
                .attribute(ApiAttribute.builder()
                    .type("attribute")
                    .string("Place")
                    .tail("Legacy Place")
                    .build())
                .attribute(ApiAttribute.builder()
                    .type("attribute")
                    .string("Modern place")
                    .tail("Legacy Modern")
                    .build())
                .build())
            .build();

        service.syncPlacesOnCreate(family);

        verify(geoServiceClient).upsert(eq("Legacy Place"), eq("Legacy Modern"));
    }

    @Test
    void testSyncPlacesOnUpdateSwallowsGeoServiceException() {
        final GeoServiceClient geoServiceClient = mock(GeoServiceClient.class);
        doThrow(new RuntimeException("boom"))
            .when(geoServiceClient)
            .updateOrCreate(eq("Old Place"), eq("New Modern"));
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

        assertDoesNotThrow(() -> service.syncPlacesOnUpdate(before, after));
        verify(geoServiceClient).updateOrCreate(eq("Old Place"), eq("New Modern"));
    }

    @Test
    void testSyncPlacesOnUpdateWithNoAttributesDoesNothing() {
        final GeoServiceClient geoServiceClient = mock(GeoServiceClient.class);
        final PersonGeoService service = new PersonGeoService(
            geoServiceClient,
            mock(ApplicationInfo.class),
            mock(CalendarProvider.class));

        final ApiPerson before = ApiPerson.builder().string("I1").indexName("A").surname("B").build();
        final ApiPerson after = ApiPerson.builder().string("I1").indexName("A").surname("B").build();

        service.syncPlacesOnUpdate(before, after);

        verifyNoInteractions(geoServiceClient);
    }

    @Test
    void testEnrichModernPlacesFamilyNullSafe() {
        final GeoServiceClient geoServiceClient = mock(GeoServiceClient.class);
        final PersonGeoService service = new PersonGeoService(
            geoServiceClient,
            mock(ApplicationInfo.class),
            mock(CalendarProvider.class));

        assertNull(service.enrichModernPlaces((ApiFamily) null));
    }

    @Test
    void testEnrichModernPlacesPersonNullSafe() {
        final GeoServiceClient geoServiceClient = mock(GeoServiceClient.class);
        final PersonGeoService service = new PersonGeoService(
            geoServiceClient,
            mock(ApplicationInfo.class),
            mock(CalendarProvider.class));

        assertNull(service.enrichModernPlaces((ApiPerson) null));
    }

    @Test
    void testSyncPlacesOnCreateSwallowsGeoServiceException() {
        final GeoServiceClient geoServiceClient = mock(GeoServiceClient.class);
        doThrow(new RuntimeException("boom"))
            .when(geoServiceClient)
            .upsert(eq("Legacy Place"), eq("Legacy Modern"));
        final PersonGeoService service = new PersonGeoService(
            geoServiceClient,
            mock(ApplicationInfo.class),
            mock(CalendarProvider.class));

        final ApiFamily family = ApiFamily.builder()
            .string("F1")
            .attribute(ApiAttribute.builder()
                .type("attribute")
                .string("Marriage")
                .attribute(ApiAttribute.builder()
                    .type("attribute")
                    .string("Place")
                    .tail("Legacy Place")
                    .build())
                .attribute(ApiAttribute.builder()
                    .type("attribute")
                    .string("Modern place")
                    .tail("Legacy Modern")
                    .build())
                .build())
            .build();

        assertDoesNotThrow(() -> service.syncPlacesOnCreate(family));
        verify(geoServiceClient).upsert(eq("Legacy Place"), eq("Legacy Modern"));
    }

    @Test
    void testSyncPlacesOnUpdateUnchangedDoesNotCallUpdateOrCreate() {
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
                        .tail("Same Modern")
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
                        .tail("Same Modern")
                        .build())
                    .build())
                .build())
            .build();

        service.syncPlacesOnUpdate(before, after);

        verifyNoInteractions(geoServiceClient);
    }

    @Test
    void testEnrichModernPlacesCachesDuplicateLookupWithinRequest() {
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
            .attribute(ApiAttribute.builder()
                .type("attribute")
                .string("Residence")
                .attribute(ApiAttribute.builder()
                    .type("place")
                    .string("Old Place")
                    .build())
                .build())
            .build();

        final ApiPerson enriched = service.enrichModernPlaces(person);

        assertNotNull(enriched);
        verify(geoServiceClient, times(1)).get(eq("Old Place"));
    }

    @Test
    void testSyncPlacesOnCreateHandlesNullAndNullAttributeEntries() {
        final GeoServiceClient geoServiceClient = mock(GeoServiceClient.class);
        final PersonGeoService service = new PersonGeoService(
            geoServiceClient,
            mock(ApplicationInfo.class),
            mock(CalendarProvider.class));

        service.syncPlacesOnCreate(null);

        final ApiFamily family = ApiFamily.builder()
            .string("F1")
            .attribute(ApiAttribute.builder()
                .type("attribute")
                .string("Event")
                .attributes(new ArrayList<>())
                .build())
            .build();
        service.syncPlacesOnCreate(family);

        verifyNoInteractions(geoServiceClient);
    }

    @Test
    void testFetchPlacesBuildsAuthenticatedAndAnonymousRenderingContexts() {
        final GeoServiceClient geoServiceClient = mock(GeoServiceClient.class);
        final UserService userService = mock(UserService.class);
        final SecurityUser admin = mock(SecurityUser.class);
        when(admin.hasRole(UserRoleName.ADMIN)).thenReturn(true);
        when(admin.hasRole(UserRoleName.USER)).thenReturn(true);
        when(userService.findByUsername("admin")).thenReturn(admin);

        final PersonGeoService service = new PersonGeoService(
            geoServiceClient,
            mock(ApplicationInfo.class),
            mock(CalendarProvider.class));

        final RequestUserUtil adminUser = new RequestUserUtil((java.security.Principal) () -> "admin",
            userService);
        final RequestUserUtil anonymousUser = new RequestUserUtil((java.security.Principal) null,
            userService);

        assertNotNull(service.fetchPlaces(new Person(), adminUser));
        assertNotNull(service.fetchPlaces(new Person(), anonymousUser));
    }

    @Test
    void testResolveModernForUpdateBranchesViaReflection() throws ReflectiveOperationException {
        final PersonGeoService service = new PersonGeoService(
            mock(GeoServiceClient.class),
            mock(ApplicationInfo.class),
            mock(CalendarProvider.class));

        final String fallback = invokePrivate(service,
            "resolveModernForUpdate",
            new Class<?>[] { String.class, Set.class, Set.class, String.class },
            "Old Place",
            null,
            java.util.Collections.emptySet(),
            "Fallback Modern");
        assertEquals("Fallback Modern", fallback);

        final Set<String> previous = new LinkedHashSet<>();
        previous.add("Old Modern");
        final Set<String> currentWithNewSpecific = new LinkedHashSet<>();
        currentWithNewSpecific.add("New Modern");
        final String specific = invokePrivate(service,
            "resolveModernForUpdate",
            new Class<?>[] { String.class, Set.class, Set.class, String.class },
            "Old Place",
            previous,
            currentWithNewSpecific,
            "Old Place");
        assertEquals("New Modern", specific);

        final Set<String> currentFallbackOnly = new LinkedHashSet<>();
        currentFallbackOnly.add("Old Place");
        final String fallbackResult = invokePrivate(service,
            "resolveModernForUpdate",
            new Class<?>[] { String.class, Set.class, Set.class, String.class },
            "Old Place",
            previous,
            currentFallbackOnly,
            "Old Place");
        assertEquals("Old Place", fallbackResult);
    }

    @Test
    @SuppressWarnings("unchecked")
    void testPutPlaceMappingReplacesFallbackWithSpecificValue() throws ReflectiveOperationException {
        final PersonGeoService service = new PersonGeoService(
            mock(GeoServiceClient.class),
            mock(ApplicationInfo.class),
            mock(CalendarProvider.class));

        final Map<String, String> mapping = new TreeMap<>();
        mapping.put("Old Place", "Old Place");

        invokePrivate(service,
            "putPlaceMapping",
            new Class<?>[] { Map.class, String.class, String.class },
            mapping,
            "Old Place",
            "New Modern");

        assertEquals("New Modern", mapping.get("Old Place"));
    }

    @Test
    void testReadModernPlaceNameFromChildrenHandlesModernPlaceType() throws ReflectiveOperationException {
        final PersonGeoService service = new PersonGeoService(
            mock(GeoServiceClient.class),
            mock(ApplicationInfo.class),
            mock(CalendarProvider.class));

        final List<ApiAttribute> children = List.of(ApiAttribute.builder()
            .type("modernplace")
            .string("Modern String")
            .tail("")
            .build());

        final String modern = invokePrivate(service,
            "readModernPlaceNameFromChildren",
            new Class<?>[] { List.class },
            children);

        assertEquals("Modern String", modern);
    }

    @SuppressWarnings("unchecked")
    private <T> T invokePrivate(final PersonGeoService service,
            final String methodName,
            final Class<?>[] parameterTypes,
            final Object... args) throws ReflectiveOperationException {
        final Method method = PersonGeoService.class.getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return (T) method.invoke(service, args);
    }

    @Test
    void testEnrichModernPlacesAddsMissingModernPlaceForFamily() {
        final GeoServiceClient geoServiceClient = mock(GeoServiceClient.class);
        when(geoServiceClient.get("Family Place")).thenReturn(
            new GeoServiceItem("Family Place", "Family Modern Place", null));

        final PersonGeoService service = new PersonGeoService(
            geoServiceClient,
            mock(ApplicationInfo.class),
            mock(CalendarProvider.class));

        final ApiFamily family = ApiFamily.builder()
            .string("F1")
            .attribute(ApiAttribute.builder()
                .type("attribute")
                .string("Marriage")
                .attribute(ApiAttribute.builder()
                    .type("place")
                    .string("Family Place")
                    .build())
                .build())
            .build();

        final ApiFamily enriched = service.enrichModernPlaces(family);

        final ApiAttribute marriage = enriched.getAttributes().get(0);
        final ApiAttribute place = marriage.getAttributes().stream()
            .filter(a -> "place".equals(a.getType()))
            .findFirst()
            .orElse(null);
        assertNotNull(place);
        final ApiAttribute modern = place.getAttributes().stream()
            .filter(a -> "Modern place".equals(a.getString()))
            .findFirst()
            .orElse(null);

        assertNotNull(modern);
        assertEquals("Family Modern Place", modern.getTail());
    }

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
    void testSyncPlacesOnUpdateDuplicatePlacePrefersNewValue() {
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
            .attribute(ApiAttribute.builder()
                .type("attribute")
                .string("Residence")
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
            .attribute(ApiAttribute.builder()
                .type("attribute")
                .string("Residence")
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
