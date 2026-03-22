package org.schoellerfamily.gedbrowser.renderer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.visitor.PersonConfidentialVisitor;
import org.schoellerfamily.gedbrowser.datamodel.visitor.PlaceVisitor;
import org.schoellerfamily.gedbrowser.renderer.href.HeaderHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.IndexHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.PlacesHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SaveHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SourcesHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SubmittersHrefRenderer;
import org.schoellerfamily.geoservice.client.GeoServiceClient;
import org.schoellerfamily.geoservice.model.GeoServiceItem;
import org.schoellerfamily.geoservice.model.GeoServiceItemComparator;

import lombok.extern.slf4j.Slf4j;



/**
 * Renders index by place output for display.
 *
 * @author Richard Schoeller
 */
@Slf4j
public final class IndexByPlaceRenderer extends GedRenderer<Root>
    implements HeaderHrefRenderer<Root>, IndexHrefRenderer<Root>,
        PlacesHrefRenderer<Root>, SaveHrefRenderer<Root>,
        SourcesHrefRenderer<Root>, SubmittersHrefRenderer<Root> {

    /** */
    private final GeoServiceClient client;

    /** */
    private final Map<String, Set<PersonRenderer>> theMap;

    /**
     * Creates a new IndexByPlaceRenderer.
     *
     * @param root the root
     * @param client the client
     * @param renderingContext the rendering context
     */
    public IndexByPlaceRenderer(final Root root,
            final GeoServiceClient client,
            final RenderingContext renderingContext) {
        super(root, new GedRendererFactory(), renderingContext);
        this.client = client;
        theMap = createWholeIndex();
    }

    /**
     * Build the index.
     *
     * @return the complete index
     */
    private Map<String, Set<PersonRenderer>> createWholeIndex() {
        log.info("In getWholeIndex");
        final Map<String, Set<PersonRenderer>> aMap = new TreeMap<>();
        if (!getRenderingContext().isUser()) {
            log.info("Leaving getWholeIndex not logged in");
            return aMap;
        }
        final Collection<Person> persons = getGedObject().find(Person.class);
        for (final Person person : persons) {
            if (isHidden(person)) {
                continue;
            }
            locatePerson(aMap, person);
        }
        log.info("Leaving getWholeIndex");
        return aMap;
    }

    /**
     * Add a person's locations to the map.
     *
     * @param aMap the map
     * @param person the person
     */
    private void locatePerson(final Map<String, Set<PersonRenderer>> aMap,
            final Person person) {
        final PersonRenderer renderer = new PersonRenderer(person,
                getRendererFactory(), getRenderingContext());
        for (final String place : getPlaces(person)) {
            placePerson(aMap, renderer, place);
        }
    }

    /**
     * @param gob the ged object to place
     * @return the set of places found
     */
    private Set<String> getPlaces(final Person gob) {
        final Set<String> set = new HashSet<>();
        if (gob == null) {
            return set;
        }
        if (isHidden(gob)) {
            return set;
        }

        final PlaceVisitor visitor = new PlaceVisitor();
        gob.accept(visitor);
        return visitor.getPlaceStrings();
    }

    /**
     * Add a person to a particular location in the map.
     *
     * @param aMap the map
     * @param renderer the PersonRenderer
     * @param place the current place
     */
    private void placePerson(final Map<String, Set<PersonRenderer>> aMap,
            final PersonRenderer renderer, final String place) {
        final Set<PersonRenderer> locatedPersons = personRendererSet(aMap, place);
        locatedPersons.add(renderer);
    }

    /**
     * @param aMap the map
     * @param place the place
     * @return the set for the place from the map
     */
    private Set<PersonRenderer> personRendererSet(
            final Map<String, Set<PersonRenderer>> aMap, final String place) {
        return aMap.computeIfAbsent(place, k -> new TreeSet<>(new PersonRendererComparator()));
    }

    /**
     * @param person the person we are checking
     * @return true if the person is confidential
     */
    private boolean isHidden(final Person person) {
        if (getRenderingContext().isAdmin()) {
            return false;
        }
        final PersonConfidentialVisitor visitor = new PersonConfidentialVisitor();
        person.accept(visitor);
        return visitor.isConfidential() || !getRenderingContext().isUser();
    }

    /**
     * Gets the whole index.
     *
     * @return the whole index
     */
    public Map<String, Set<PersonRenderer>> getWholeIndex() {
        return theMap;
    }

    /**
     * Gets the places.
     *
     * @return the places
     */
    public Set<String> getPlaces() {
        return getWholeIndex().keySet();
    }

    /**
     * Returns the persons at place.
     *
     * @param place the place
     * @return the persons at place
     */
    public Set<PersonRenderer> getPersonsAtPlace(final String place) {
        log.info("In getPersonsAtPlace");
        return getWholeIndex().get(place);
    }

    /**
     * Executes render.
     *
     * @return the resulting set
     */
    public Map<GeoServiceItem, Set<PersonRenderer>> render() {
        return getWholeIndex().entrySet().stream()
            .collect(Collectors.toMap(
                entry -> client.get(entry.getKey()),
                Map.Entry::getValue,
                (v1, v2) -> {
                    final Set<PersonRenderer> merged =
                        new TreeSet<>(new PersonRendererComparator());
                    merged.addAll(v1);
                    merged.addAll(v2);
                    return merged;
                },
                () -> new TreeMap<>(new GeoServiceItemComparator())
            ));
    }
}
