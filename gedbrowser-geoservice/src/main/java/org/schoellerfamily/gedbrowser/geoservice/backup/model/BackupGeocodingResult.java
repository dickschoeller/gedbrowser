package org.schoellerfamily.gedbrowser.geoservice.backup.model;

import java.util.Arrays;

import com.google.maps.model.AddressType;

/**
 * A variant of Google's GeocodingResult that will work with Jackson
 * serialization to JSON.
 *
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.CommentSize")
public final class BackupGeocodingResult {
    /**
     * {@code addressComponents} is an array containing the separate address
     * components.
     */
    private final BackupAddressComponent[] addressComponents;

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
     */
    private final String formattedAddress;

    /**
     * {@code postcodeLocalities} is an array denoting all the localities
     * contained in a postal code. This is only present when the result is a
     * postal code that contains multiple localities.
     */
    private final String[] postcodeLocalities;

    /**
     * {@code geometry} contains location information.
     */
    private final BackupGeometry geometry;

    /**
     * The {@code types} array indicates the type of the returned result. This
     * array contains a set of zero or more tags identifying the type of feature
     * returned in the result. For example, a geocode of "Chicago" returns
     * "locality" which indicates that "Chicago" is a city, and also returns
     * "political" which indicates it is a political entity.
     */
    private final AddressType[] types;

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
     */
    private final boolean partialMatch;

    /**
     * {@code placeId} is a unique identifier for a place.
     */
    private final String placeId;

    /**
     * Default constructor used in serialization.
     */
    public BackupGeocodingResult() {
        this.addressComponents = new BackupAddressComponent[0];
        this.formattedAddress = null;
        this.postcodeLocalities = new String[0];
        this.geometry = new BackupGeometry();
        this.types = new AddressType[0];
        this.partialMatch = false;
        this.placeId = null;
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
    public BackupGeocodingResult(
            final BackupAddressComponent[] addressComponents,
            final String formattedAddress, final String[] postcodeLocalities,
            final BackupGeometry geometry, final AddressType[] types,
            final boolean partialMatch, final String placeId) {
        if (addressComponents == null) {
            this.addressComponents = null;
        } else {
            this.addressComponents = Arrays.copyOf(addressComponents,
                    addressComponents.length);
        }
        this.formattedAddress = formattedAddress;
        if (postcodeLocalities == null) {
            this.postcodeLocalities = null;
        } else {
            this.postcodeLocalities = Arrays.copyOf(postcodeLocalities,
                    postcodeLocalities.length);
        }
        this.geometry = geometry;
        if (types == null) {
            this.types = null;
        } else {
            this.types = Arrays.copyOf(types, types.length);
        }
        this.partialMatch = partialMatch;
        this.placeId = placeId;
    }

    /**
     * @return the addressComponents
     */
    public BackupAddressComponent[] getAddressComponents() {
        if (addressComponents == null) {
            return null;
        }
        return Arrays.copyOf(addressComponents, addressComponents.length);
    }

    /**
     * @return the formattedAddress
     */
    public String getFormattedAddress() {
        return formattedAddress;
    }

    /**
     * @return the postcodeLocalities
     */
    public String[] getPostcodeLocalities() {
        if (postcodeLocalities == null) {
            return null;
        }
        return Arrays.copyOf(postcodeLocalities, postcodeLocalities.length);
    }

    /**
     * @return the geometry
     */
    public BackupGeometry getGeometry() {
        return geometry;
    }

    /**
     * @return the types
     */
    public AddressType[] getTypes() {
        if (types == null) {
            return null;
        }
        return Arrays.copyOf(types, types.length);
    }

    /**
     * @return the partialMatch
     */
    public boolean isPartialMatch() {
        return partialMatch;
    }

    /**
     * @return the placeId
     */
    public String getPlaceId() {
        return placeId;
    }
}
