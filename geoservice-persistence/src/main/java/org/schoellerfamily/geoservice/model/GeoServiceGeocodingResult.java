package org.schoellerfamily.geoservice.model;

import java.beans.Transient;
import java.util.Arrays;
import java.util.List;

import org.geojson.Feature;
import org.geojson.FeatureCollection;

import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressType;

/**
 * A variant of Google's GeocodingResult that will work with Jackson
 * serialization to JSON.
 *
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.CommentSize")
public final class GeoServiceGeocodingResult {
    /**
     * The {@code types} array indicates the type of the returned result. This
     * array contains a set of zero or more tags identifying the type of feature
     * returned in the result. For example, a geocode of "Chicago" returns
     * "locality" which indicates that "Chicago" is a city, and also returns
     * "political" which indicates it is a political entity.
     */
    private final AddressType[] types;

    /**
     * {@code addressComponents} is an array containing the separate address
     * components.
     */
    private final AddressComponent[] addressComponents;

    /**
     * {@code geometry} contains location information.
     */
    private final FeatureCollection geometry;

    /**
     * Default constructor used in serialization.
     */
    public GeoServiceGeocodingResult() {
        this.addressComponents = new AddressComponent[0];
        this.types = new AddressType[0];
        this.geometry = new FeatureCollection();
    }

    /**
     * @param addressComponents
     *            an array containing the separate address components
     * @param formattedAddress human readable address string
     * @param postcodeLocalities
     *            an array denoting all of the addresses in the postal code
     * @param geometry location information
     * @param types an array describing the applicable location types
     * @param partialMatch true if this is a partial match
     * @param placeId unique identifier for the place
     */
    public GeoServiceGeocodingResult(
            final AddressComponent[] addressComponents,
            final String formattedAddress, final String[] postcodeLocalities,
            final FeatureCollection geometry,
            final AddressType[] types,
            final boolean partialMatch, final String placeId) {
        this.geometry = geometry;
        if (geometry != null
                && geometry.getFeatures() != null
                && !geometry.getFeatures().isEmpty()) {
            final Feature fg = geometry.getFeatures().get(0);
            fg.setProperty("formattedAddress", formattedAddress);
            fg.setProperty("partialMatch", Boolean.valueOf(partialMatch));
            fg.setProperty("placeId", placeId);
            if (postcodeLocalities == null) {
                fg.setProperty("postcodeLocalities", null);
            } else {
                fg.setProperty("postcodeLocalities", Arrays
                        .copyOf(postcodeLocalities, postcodeLocalities.length));
            }
            fg.setProperty("types", types);
            fg.setProperty("addressComponents", addressComponents);
        }

        if (types == null) {
            this.types = null;
        } else {
            this.types = Arrays.copyOf(types, types.length);
        }

        if (addressComponents == null) {
            this.addressComponents = null;
        } else {
            this.addressComponents = Arrays.copyOf(addressComponents,
                    addressComponents.length);
        }
    }

    /**
     * {@code addressComponents} is an array containing the separate address
     * components.
     *
     * @return the array of components
     */
    public AddressComponent[] getAddressComponents() {
        if (addressComponents == null) {
            return null;
        }
        return Arrays.copyOf(addressComponents, addressComponents.length);
    }

    /**
     * {@code formattedAddress} is the human-readable address of this location.
     * Often this address is equivalent to the "postal address," which sometimes
     * differs from country to country. (Note that some countries, such as the
     * United Kingdom, do not allow distribution of true postal addresses due to
     * licensing restrictions.) This address is generally composed of one or
     * more address components. For example, the address "111 8th Avenue, New
     * York, NY" contains separate address components for "111" (the street
     * number, "8th Avenue" (the route), "New York" (the city) and "NY" (the US
     * state). These address components contain additional information.
     *
     * @return the formatted address
     */
    @Transient
    public String getFormattedAddress() {
        final Feature location = getLocation();
        if (location == null) {
            return null;
        }
        return location.getProperty("formattedAddress");
    }

    /**
     * {@code postcodeLocalities} is an array denoting all the localities
     * contained in a postal code. This is only present when the result is a
     * postal code that contains multiple localities.
     *
     * @return the postcode localities
     */
    @Transient
    public String[] getPostcodeLocalities() {
        final Feature location = getLocation();
        if (location == null) {
            return null;
        }
        return location.getProperty("postcodeLocalities");
    }

    /**
     * Get the geometry. It has been transformed into a GeoJSON
     * FeatureCollection. It has the location, bounds and viewport as separate
     * features.
     *
     * @return the geometry
     */
    public FeatureCollection getGeometry() {
        return geometry;
    }

    /**
     * The {@code types} array indicates the type of the returned result. This
     * array contains a set of zero or more tags identifying the type of feature
     * returned in the result. For example, a geocode of "Chicago" returns
     * "locality" which indicates that "Chicago" is a city, and also returns
     * "political" which indicates it is a political entity.
     *
     * @return the types
     */
    public AddressType[] getTypes() {
        if (types == null) {
            return null;
        }
        return Arrays.copyOf(types, types.length);
    }

    /**
     * {@code partialMatch} indicates that the geocoder did not return an exact
     * match for the original request, though it was able to match part of the
     * requested address. You may wish to examine the original request for
     * misspellings and/or an incomplete address.
     *
     * <p>
     * Partial matches most often occur for street addresses that do not exist
     * within the locality you pass in the request. Partial matches may also be
     * returned when a request matches two or more locations in the same
     * locality. For example, "21 Henr St, Bristol, UK" will return a partial
     * match for both Henry Street and Henrietta Street. Note that if a request
     * includes a misspelled address component, the geocoding service may
     * suggest an alternate address. Suggestions triggered in this way will not
     * be marked as a partial match.
     *
     * @return the partialMatch
     */
    @Transient
    public boolean isPartialMatch() {
        final Feature location = getLocation();
        if (location == null) {
            return false;
        }
        return location.getProperty("partialMatch");
    }

    /**
     * {@code placeId} is a unique identifier for a place.
     *
     * @return the placeId
     */
    @Transient
    public String getPlaceId() {
        final Feature location = getLocation();
        if (location == null) {
            return null;
        }
        return location.getProperty("placeId");
    }

    /**
     * Return the location. It is in the form of a GeoJSON feature.
     * Much of the descriptive data from Google is in the properties
     * of that feature.
     *
     * @return the location feature
     */
    @Transient
    private Feature getLocation() {
        if (geometry == null) {
            return null;
        }
        final List<Feature> features = geometry.getFeatures();
        if (features == null || features.isEmpty()) {
            return null;
        }
        return features.get(0);
    }
}
