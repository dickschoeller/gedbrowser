package org.schoellerfamily.gedbrowser.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.renderer.PlaceInfo;
import org.schoellerfamily.gedbrowser.renderer.PlaceListRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.schoellerfamily.gedbrowser.security.util.RequestUserUtil;
import org.schoellerfamily.geoservice.client.GeoServiceClient;
import org.schoellerfamily.geoservice.model.GeoServiceItem;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



/**
 * Provides services for person geo.
 *
 * @author Richard Schoeller
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PersonGeoService {

    /** */
    private final GeoServiceClient geoServiceClient;

    /** */
    private final ApplicationInfo appInfo;

    /** */
    private final CalendarProvider provider;

    /**
     * Fetch the places for a person based on the authenticated-user context.
     *
     * @param person the person to fetch places for
     * @param requestUserUtil utility to inspect the current request user
     * @return the list of resolved places
     */
    public List<PlaceInfo> fetchPlaces(
            final Person person, final RequestUserUtil requestUserUtil) {
        final RenderingContext renderingContext = createRenderingContext(requestUserUtil);
        return new PlaceListRenderer(person, geoServiceClient, renderingContext).render();
    }

    /**
     * Enrich a person payload with derived "Modern place" child attributes when a
     * place exists but no modern place is present.
     *
     * @param person the person payload to enrich
     * @return enriched person payload
     */
    public ApiPerson enrichModernPlaces(final ApiPerson person) {
        if (person == null || person.getAttributes() == null) {
            return person;
        }
        final Map<String, String> modernByPlace = new TreeMap<>();
        final List<ApiAttribute> enriched = enrichAttributes(person.getAttributes(), modernByPlace);
        return person.toBuilder().clearAttributes().attributes(enriched).build();
    }

    /**
     * Synchronize all place/modern-place entries from a newly created person.
     *
        * @param object created object payload
     */
    public void syncPlacesOnCreate(final ApiObject object) {
        final Map<String, String> currentPlaces = extractPlaces(object);
        for (final Map.Entry<String, String> entry : currentPlaces.entrySet()) {
            syncOne(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Synchronize changed place/modern-place entries on update.
     *
        * @param before prior object payload
        * @param after updated object payload
     */
    public void syncPlacesOnUpdate(final ApiObject before, final ApiObject after) {
        final Map<String, String> currentPlaces = extractPlaces(after);
        final Map<String, String> previousPlaces = extractPlaces(before);
        log.info("syncPlacesOnUpdate: previousCount={} currentCount={}",
            previousPlaces.size(), currentPlaces.size());
        for (final Map.Entry<String, String> entry : currentPlaces.entrySet()) {
            final String previousModern = previousPlaces.get(entry.getKey());
            final String currentModern = entry.getValue();
            if (previousModern == null || !previousModern.equals(currentModern)) {
                log.info("syncPlacesOnUpdate changed mapping: placeName={} previousModern={}"
                    + " currentModern={}",
                    entry.getKey(), previousModern, currentModern);
            } else {
                log.debug("syncPlacesOnUpdate unchanged mapping: placeName={} modernPlaceName={}",
                    entry.getKey(), currentModern);
            }
            syncOneOnUpdate(entry.getKey(), currentModern);
        }
    }

    private RenderingContext createRenderingContext(
            final RequestUserUtil requestUserUtil) {
        if (requestUserUtil.hasAdmin() || requestUserUtil.hasUser()) {
            return new RenderingContext(requestUserUtil.getUser(), appInfo, provider);
        }
        return RenderingContext.anonymous(appInfo, provider);
    }

    private List<ApiAttribute> enrichAttributes(final List<ApiAttribute> attributes,
            final Map<String, String> modernByPlace) {
        final List<ApiAttribute> output = new ArrayList<>();
        for (final ApiAttribute attribute : attributes) {
            if (attribute == null) {
                continue;
            }
            output.add(enrichAttribute(attribute, modernByPlace));
        }
        return output;
    }

    private ApiAttribute enrichAttribute(final ApiAttribute attribute,
            final Map<String, String> modernByPlace) {
        if (attribute.getAttributes() == null) {
            return attribute;
        }
        final List<ApiAttribute> enrichedChildren =
            enrichAttributes(attribute.getAttributes(), modernByPlace);
        final ApiAttribute rebuilt = attribute.toBuilder()
            .clearAttributes()
            .attributes(enrichedChildren)
            .build();

        final String placeName = readPlaceName(rebuilt);
        final String existingModern = readModernPlaceName(rebuilt);
        if (isBlank(placeName) || !isBlank(existingModern)) {
            return rebuilt;
        }

        final String lookedUpModern = lookupModernPlaceName(placeName, modernByPlace);
        if (isBlank(lookedUpModern)) {
            return rebuilt;
        }

        final ApiAttribute placeAttribute = findPlaceAttribute(rebuilt);
        if (placeAttribute == null) {
            return rebuilt;
        }
        final ApiAttribute enrichedPlace = placeAttribute.toBuilder()
            .clearAttributes()
            .attributes(addModernChild(placeAttribute, lookedUpModern))
            .build();
        final List<ApiAttribute> children = new ArrayList<>();
        for (final ApiAttribute child : rebuilt.getAttributes()) {
            if (child == placeAttribute) {
                children.add(enrichedPlace);
            } else {
                children.add(child);
            }
        }
        return rebuilt.toBuilder().clearAttributes().attributes(children).build();
    }

    private List<ApiAttribute> addModernChild(final ApiAttribute placeAttribute,
            final String modernPlaceName) {
        final List<ApiAttribute> children = new ArrayList<>();
        if (placeAttribute.getAttributes() != null) {
            children.addAll(placeAttribute.getAttributes());
        }
        children.add(ApiAttribute.builder()
            .type("attribute")
            .string("Modern place")
            .tail(modernPlaceName)
            .build());
        return children;
    }

    private ApiAttribute findPlaceAttribute(final ApiAttribute attribute) {
        for (final ApiAttribute child : attribute.getAttributes()) {
            if (child != null && "place".equalsIgnoreCase(child.getType())) {
                return child;
            }
        }
        return null;
    }

    private String lookupModernPlaceName(final String placeName,
            final Map<String, String> modernByPlace) {
        if (modernByPlace.containsKey(placeName)) {
            return modernByPlace.get(placeName);
        }
        String modernPlaceName = null;
        try {
            final GeoServiceItem item = geoServiceClient.get(placeName);
            if (item != null) {
                modernPlaceName = item.getModernPlaceName();
            }
        } catch (RuntimeException e) {
            log.warn("Failed modern-place lookup for placeName={}", placeName, e);
        }
        modernByPlace.put(placeName, modernPlaceName);
        return modernPlaceName;
    }

    private Map<String, String> extractPlaces(final ApiObject object) {
        final Map<String, String> placeToModern = new TreeMap<>();
        if (object == null || object.getAttributes() == null) {
            return placeToModern;
        }
        for (final ApiAttribute topLevelAttribute : object.getAttributes()) {
            collectPlaces(topLevelAttribute, placeToModern);
        }
        return placeToModern;
    }

    private void collectPlaces(final ApiAttribute attribute,
            final Map<String, String> placeToModern) {
        if (attribute == null) {
            return;
        }
        final String placeName = readPlaceName(attribute);
        if (placeName != null && !placeName.isBlank()) {
            final String modernPlaceName = readModernPlaceName(attribute);
            putPlaceMapping(placeToModern, placeName, modernPlaceName);
        }
        if (attribute.getAttributes() != null) {
            for (final ApiAttribute child : attribute.getAttributes()) {
                collectPlaces(child, placeToModern);
            }
        }
    }

    private String readPlaceName(final ApiAttribute attribute) {
        if (attribute == null) {
            return null;
        }
        if (isPlaceAttribute(attribute)) {
            return placeValue(attribute);
        }
        if (attribute.getAttributes() == null) {
            return null;
        }
        for (final ApiAttribute child : attribute.getAttributes()) {
            if (isPlaceAttribute(child)) {
                return placeValue(child);
            }
        }
        return null;
    }

    private String readModernPlaceName(final ApiAttribute attribute) {
        if (attribute == null) {
            return null;
        }
        if (isPlaceAttribute(attribute)) {
            final String nested = readModernPlaceNameFromChildren(attribute.getAttributes());
            if (!isBlank(nested)) {
                return nested;
            }
        }
        if (attribute.getAttributes() == null) {
            return null;
        }
        for (final ApiAttribute child : attribute.getAttributes()) {
            if (child == null) {
                continue;
            }
            if (isPlaceAttribute(child) && child.getAttributes() != null) {
                final String nested = readModernPlaceNameFromChildren(child.getAttributes());
                if (!isBlank(nested)) {
                    return nested;
                }
            }
        }
        return readModernPlaceNameFromChildren(attribute.getAttributes());
    }

    private String readModernPlaceNameFromChildren(final List<ApiAttribute> children) {
        if (children == null) {
            return null;
        }
        for (final ApiAttribute child : children) {
            if (child == null) {
                continue;
            }
            if ("Modern place".equalsIgnoreCase(child.getString())) {
                return child.getTail();
            }
            if ("modernplace".equalsIgnoreCase(child.getType())) {
                if (!isBlank(child.getTail())) {
                    return child.getTail();
                }
                return child.getString();
            }
        }
        return null;
    }

    private boolean isPlaceAttribute(final ApiAttribute attribute) {
        if (attribute == null) {
            return false;
        }
        if ("place".equalsIgnoreCase(attribute.getType())) {
            return true;
        }
        return "attribute".equalsIgnoreCase(attribute.getType())
            && "Place".equalsIgnoreCase(attribute.getString());
    }

    private String placeValue(final ApiAttribute attribute) {
        if (attribute == null) {
            return null;
        }
        if ("place".equalsIgnoreCase(attribute.getType())) {
            return attribute.getString();
        }
        if ("attribute".equalsIgnoreCase(attribute.getType())
                && "Place".equalsIgnoreCase(attribute.getString())) {
            return isBlank(attribute.getTail()) ? attribute.getString() : attribute.getTail();
        }
        return null;
    }

    private boolean isBlank(final String value) {
        return value == null || value.isBlank();
    }

    private void putPlaceMapping(final Map<String, String> placeToModern,
            final String placeName,
            final String modernPlaceName) {
        final String normalizedModern = isBlank(modernPlaceName) ? placeName : modernPlaceName;
        final String existingModern = placeToModern.get(placeName);
        if (existingModern == null) {
            placeToModern.put(placeName, normalizedModern);
            return;
        }
        final boolean existingIsFallback = existingModern.equals(placeName);
        final boolean newIsSpecific = !normalizedModern.equals(placeName);
        if (existingIsFallback && newIsSpecific) {
            placeToModern.put(placeName, normalizedModern);
        }
    }

    private void syncOne(final String placeName, final String modernPlaceName) {
        try {
            geoServiceClient.upsert(placeName, modernPlaceName);
        } catch (RuntimeException e) {
            log.error("Failed geoservice sync for placeName={} modernPlaceName={}",
                placeName, modernPlaceName, e);
        }
    }

    private void syncOneOnUpdate(final String placeName, final String modernPlaceName) {
        try {
            geoServiceClient.updateOrCreate(placeName, modernPlaceName);
        } catch (RuntimeException e) {
            log.error("Failed geoservice update sync for placeName={} modernPlaceName={}",
                placeName, modernPlaceName, e);
        }
    }
}
