package org.schoellerfamily.geoservice.backup.model;

import java.util.Arrays;

import com.google.maps.model.AddressComponentType;

/**
 * A variant of Google's AddressComponent that will work with Jackson
 * serialization to JSON.
 *
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.UseVarargs")
public final class BackupAddressComponent {
    /**
     * {@code longName} is the full text description or name of the address
     * component as returned by the Geocoder.
     */
    private String longName;

    /**
     * {@code shortName} is an abbreviated textual name for the address
     * component, if available. For example, an address component for the state
     * of Alaska may have a longName of "Alaska" and a shortName of "AK" using
     * the 2-letter postal abbreviation.
     */
    private String shortName;

    /**
     * This indicates the type of each part of the address. Examples include
     * street number or country.
     */
    private AddressComponentType[] types;

    /**
     * Default constructor used in serialization.
     */
    public BackupAddressComponent() {
        this.longName = null;
        this.shortName = null;
        this.types = new AddressComponentType[0];
    }

    /**
     * Constructor.
     *
     * @param longName the long name
     * @param shortName the short name
     * @param types the type of each part of the address
     */
    public BackupAddressComponent(final String longName,
            final String shortName, final AddressComponentType[] types) {
        this.longName = longName;
        this.shortName = shortName;
        this.types = Arrays.copyOf(types, types.length);
    }

    /**
     * @return the longName
     */
    public String getLongName() {
        return longName;
    }

    /**
     * @param longName the longName to set
     */
    public void setLongName(final String longName) {
        this.longName = longName;
    }

    /**
     * @return the shortName
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * @param shortName the shortName to set
     */
    public void setShortName(final String shortName) {
        this.shortName = shortName;
    }

    /**
     * @return the types
     */
    public AddressComponentType[] getTypes() {
        return Arrays.copyOf(types, types.length);
    }

    /**
     * @param types the types to set
     */
    public void setTypes(final AddressComponentType[] types) {
        this.types = Arrays.copyOf(types, types.length);
    }
}
