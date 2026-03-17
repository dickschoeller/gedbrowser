package org.schoellerfamily.geoservice.model;

import java.beans.Transient;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.geojson.Feature;
import org.geojson.FeatureCollection;

import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressType;



/**
 * Represents the result of geo service geocoding processing.
 *
 * @author Richard Schoeller
 */
@SuppressWarnings({ "PMD.CommentSize", "java:S1168" })
public final class GeoServiceGeocodingResult {
    /** */
    private static final String ADDRESS_COMPONENTS = "addressComponents";
    /** */
    private static final String FORMATTED_ADDRESS = "formattedAddress";
    /** */
    private static final String PARTIAL_MATCH = "partialMatch";
    /** */
    private static final String PLACE_ID = "placeId";
    /** */
    private static final String POSTCODE_LOCALITIES = "postcodeLocalities";
    /** */
    private static final String TYPES = "types";

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
     * Creates a new GeoServiceGeocodingResult.
     *
     * @param addressComponents the address components
     * @param formattedAddress the formatted address
     * @param postcodeLocalities the postcode localities to use
     * @param geometry the geometry
     * @param types the types to use
     * @param partialMatch the partial match
     * @param placeId the unique identifier for place
     */
    public GeoServiceGeocodingResult(
            final AddressComponent[] addressComponents,
            final String formattedAddress, final String[] postcodeLocalities,
            final FeatureCollection geometry,
            final AddressType[] types,
            final boolean partialMatch, final String placeId) {
        if (types == null) {
            this.types = null;
        } else {
            this.types = Arrays.copyOf(types, types.length);
        }

        if (addressComponents == null) {
            this.addressComponents = null;
        } else {
            this.addressComponents = Arrays.copyOf(addressComponents, addressComponents.length);
        }

        this.geometry = geometry;
        if (geometry == null || CollectionUtils.isEmpty(geometry.getFeatures())) {
            return;
        }
        final Feature feature = geometry.getFeatures().get(0);
        setProperty(feature, FORMATTED_ADDRESS, formattedAddress);
        setProperty(feature, PARTIAL_MATCH, partialMatch);
        setProperty(feature, PLACE_ID, placeId);
        setProperty(feature, POSTCODE_LOCALITIES, postcodeLocalities);
        setProperty(feature, TYPES, types);
        setProperty(feature, ADDRESS_COMPONENTS, addressComponents);
    }

    private void setProperty(final Feature feature, final String propertyName, final String value) {
        if (value != null || feature.getProperty(propertyName) == null) {
            feature.setProperty(propertyName, value);
        }
    }

    private void setProperty(final Feature feature, final String propertyName,
        final boolean value) {
        if (value || feature.getProperty(propertyName) == null) {
            feature.setProperty(propertyName, Boolean.valueOf(value));
        }
    }

    private void setProperty(final Feature feature, final String propertyName,
        final String... value) {
        if (value != null || feature.getProperty(propertyName) == null) {
            feature.setProperty(propertyName,
                value == null ? null : Arrays.copyOf(value, value.length));
        }
    }

    private void setProperty(final Feature feature, final String propertyName,
        final AddressType... value) {
        if (value != null || feature.getProperty(propertyName) == null) {
            feature.setProperty(propertyName,
                value == null ? null : Arrays.copyOf(value, value.length));
        }
    }

    private void setProperty(final Feature feature, final String propertyName,
        final AddressComponent... value) {
        if (value != null || feature.getProperty(propertyName) == null) {
            feature.setProperty(propertyName,
                value == null ? null : Arrays.copyOf(value, value.length));
        }
    }

    /**
     * {@code types} array indicates the type of the returned result. This
     * array contains a set of zero or more tags identifying the type of feature
     * returned in the result. For example, a geocode of "Chicago" returns
     * "locality" which indicates that "Chicago" is a city, and also returns
     * "political" which indicates it is a political entity.
     *
     * @return the array of components
     */
    public AddressComponent[] getAddressComponents() {
        if (addressComponents == null) {
            // Null is a valid value for this field, and we want to preserve it if it is
            // null. But if it is not null, we want to return a copy of the array to avoid
            // mutability issues.
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
        return location.getProperty(FORMATTED_ADDRESS);
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
            // Null is a valid value for this field, and we want to preserve it if it is
            // null. But if it is not null, we want to return a copy of the array to avoid
            // mutability issues.
            return null;
        }
        return location.getProperty(POSTCODE_LOCALITIES);
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
            // Null is a valid value for this field, and we want to preserve it if it is
            // null. But if it is not null, we want to return a copy of the array to avoid
            // mutability issues.
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
    @SuppressWarnings({ "PMD.SimplifyBooleanReturns" })
    public boolean isPartialMatch() {
        final Feature location = getLocation();
        if (location == null) {
            return false;
        }
        return location.getProperty(PARTIAL_MATCH);
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
        return location.getProperty(PLACE_ID);
    }

    @Transient
    private Feature getLocation() {
        if (geometry == null) {
            return null;
        }
        final List<Feature> features = geometry.getFeatures();
        if (CollectionUtils.isEmpty(features)) {
            return null;
        }
        return features.get(0);
    }
}
