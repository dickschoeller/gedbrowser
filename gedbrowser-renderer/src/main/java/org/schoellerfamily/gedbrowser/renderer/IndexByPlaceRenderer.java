package org.schoellerfamily.gedbrowser.renderer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import org.schoellerfamily.geoservice.model.GeoServiceItemComparator;
import org.schoellerfamily.geoservice.model.GeoServiceItem;

/**
 * @author Dick Schoeller
 */
public final class IndexByPlaceRenderer extends GedRenderer<Root>
    implements HeaderHrefRenderer<Root>, IndexHrefRenderer<Root>,
        PlacesHrefRenderer<Root>, SaveHrefRenderer<Root>,
        SourcesHrefRenderer<Root>, SubmittersHrefRenderer<Root> {
    /** Logger. */
    private final Log logger = LogFactory.getLog(getClass());

    /** */
    private final GeoServiceClient client;

    /** */
    private final Map<String, Set<PersonRenderer>> theMap;

    /**
     * Constructor.
     *
     * @param root root of data set
     * @param client the geoservice client to use
     * @param renderingContext the context that we are rendering in
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
        logger.info("In getWholeIndex");
        final Map<String, Set<PersonRenderer>> aMap = new TreeMap<>();
        if (!getRenderingContext().isUser()) {
            logger.info("Leaving getWholeIndex not logged in");
            return aMap;
        }
        final Collection<Person> persons = getGedObject().find(Person.class);
        for (final Person person : persons) {
            if (isHidden(person)) {
                continue;
            }
            locatePerson(aMap, person);
        }
        logger.info("Leaving getWholeIndex");
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
        final Set<PersonRenderer> locatedPersons =
                personRendererSet(aMap, place);
        locatedPersons.add(renderer);
    }

    /**
     * @param aMap the map
     * @param place the place
     * @return the set for the place from the map
     */
    private Set<PersonRenderer> personRendererSet(
            final Map<String, Set<PersonRenderer>> aMap, final String place) {
        Set<PersonRenderer> locatedPersons = aMap.get(place);
        if (locatedPersons == null) {
            locatedPersons = new TreeSet<>(new PersonRendererComparator());
            aMap.put(place, locatedPersons);
        }
        return locatedPersons;
    }

    /**
     * @param person the person we are checking
     * @return true if the person is confidential
     */
    private boolean isHidden(final Person person) {
        if (getRenderingContext().isAdmin()) {
            return false;
        }
        final PersonConfidentialVisitor visitor =
                new PersonConfidentialVisitor();
        person.accept(visitor);
        if (visitor.isConfidential()) {
            return true;
        }
        return !getRenderingContext().isUser();
    }

    /**
     * @return the complete index
     */
    public Map<String, Set<PersonRenderer>> getWholeIndex() {
        return theMap;
    }

    /**
     * @return collection of place names
     */
    public Set<String> getPlaces() {
        return getWholeIndex().keySet();
    }

    /**
     * @param place the place name
     * @return persons in the place
     */
    public Set<PersonRenderer> getPersonsAtPlace(final String place) {
        logger.info("In getPersonsAtPlace");
        return getWholeIndex().get(place);
    }

    /**
     * @return a map of geoservice results to sets of persons
     */
    public Map<GeoServiceItem, Set<PersonRenderer>> render() {
        final Map<GeoServiceItem, Set<PersonRenderer>> aMap = new TreeMap<>(
                new GeoServiceItemComparator());
        final Map<String, Set<PersonRenderer>> map = getWholeIndex();
        for (final Map.Entry<String, Set<PersonRenderer>> entry
                : map.entrySet()) {
            final GeoServiceItem item = client.get(entry.getKey());
            aMap.put(item, entry.getValue());
        }
        return aMap;
    }
}
