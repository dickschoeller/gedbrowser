package org.schoellerfamily.geoservice.backup;

import org.schoellerfamily.geoservice.backup.model.BackupAddressComponent;
import org.schoellerfamily.geoservice.backup.model.BackupBounds;
import org.schoellerfamily.geoservice.backup.model.BackupGeoCodeItem;
import org.schoellerfamily.geoservice.backup.model.BackupGeocodingResult;
import org.schoellerfamily.geoservice.backup.model.BackupGeometry;
import org.schoellerfamily.geoservice.backup.model.BackupLatLng;
import org.schoellerfamily.geoservice.persistence.GeoCodeItem;

import com.google.maps.model.AddressComponent;
import com.google.maps.model.Bounds;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import com.google.maps.model.LatLng;

/**
 * Builder class to convert between our own backup geocoding results and
 * Google's types.
 *
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class GeocodeResultBuilder {
    /**
     * Create a GeoCodeItem from a BackupGeoCodeItem.
     *
     * @param backupItem the BackupGeoCodeItem
     * @return the GeoCodeItem
     */
    public GeoCodeItem toGeoCodeItem(final BackupGeoCodeItem backupItem) {
        if (backupItem == null) {
            return null;
        }
        return new GeoCodeItem(backupItem.getPlaceName(),
                backupItem.getModernPlaceName(),
                toGeocodingResult(backupItem.getResult()));
    }

    /**
     * Create a GeocodingResult from a BackupGeocodingResult.
     *
     * @param backupResult the BackupGeocodingResult
     * @return the GeocodingResult
     */
    public GeocodingResult toGeocodingResult(
            final BackupGeocodingResult backupResult) {
        if (backupResult == null) {
            return null;
        }
        final GeocodingResult result = new GeocodingResult();
        final BackupAddressComponent[] addressComponents =
                backupResult.getAddressComponents();
        if (addressComponents == null) {
            result.addressComponents = null;
        } else {
            result.addressComponents =
                    new AddressComponent[addressComponents.length];
            for (int i = 0; i < addressComponents.length; i++) {
                result.addressComponents[i] =
                        toAddressComponent(addressComponents[i]);
            }
        }
        result.formattedAddress = backupResult.getFormattedAddress();
        result.geometry = toGeometry(backupResult.getGeometry());
        result.partialMatch = backupResult.isPartialMatch();
        result.placeId = backupResult.getPlaceId();
        // This is safe because the backup object returns a copy of its array.
        result.postcodeLocalities = backupResult.getPostcodeLocalities();
        // This is safe because the backup object returns a copy of its array.
        result.types = backupResult.getTypes();
        return result;
    }

    /**
     * Create an AddressComponent from a BackupAddressComponent.
     *
     * @param backupComponent the BackupAddressComponent
     * @return the AddressComponent
     */
    public AddressComponent toAddressComponent(
            final BackupAddressComponent backupComponent) {
        if (backupComponent == null) {
            return null;
        }
        final AddressComponent component = new AddressComponent();
        component.longName = backupComponent.getLongName();
        component.shortName = backupComponent.getShortName();
        // This is safe because backup object returns a copy of its array.
        component.types = backupComponent.getTypes();
        return component;
    }

    /**
     * Create a Geometry from a BackupGeometry.
     *
     * @param backupGeometry the backupGeometry
     * @return the Geometry
     */
    public Geometry toGeometry(final BackupGeometry backupGeometry) {
        if (backupGeometry == null) {
            return null;
        }
        final Geometry geometry = new Geometry();
        geometry.bounds = toBounds(backupGeometry.getBounds());
        geometry.location = toLatLng(backupGeometry.getLocation());
        geometry.locationType = backupGeometry.getLocationType();
        geometry.viewport = toBounds(backupGeometry.getViewport());
        return geometry;
    }

    /**
     * Create a Bounds from a BackupBounds.
     *
     * @param backupBounds the BackupBounds
     * @return the Bounds
     */
    public Bounds toBounds(final BackupBounds backupBounds) {
        if (backupBounds == null) {
            return null;
        }
        final Bounds bounds = new Bounds();
        bounds.northeast = toLatLng(backupBounds.getNortheast());
        bounds.southwest = toLatLng(backupBounds.getSouthwest());
        return bounds;
    }

    /**
     * Create a LatLng from a BackupLatLng.
     *
     * @param backupLatLng the BackupLatLng
     * @return the LatLng
     */
    public LatLng toLatLng(final BackupLatLng backupLatLng) {
        if (backupLatLng == null) {
            return null;
        }
        final LatLng latLng = new LatLng(backupLatLng.getLatitude(),
                backupLatLng.getLongitude());
        return latLng;
    }

    /**
     * Create a BackupGeoCodeItem from a GeoCodeItem.
     *
     * @param item the GeoCodeItem
     * @return the BackupGeoCodeItem
     */
    public BackupGeoCodeItem toBackupGeoCodeItem(final GeoCodeItem item) {
        if (item == null) {
            return null;
        }
        return new BackupGeoCodeItem(item.getPlaceName(),
                item.getModernPlaceName(),
                toBackupGeocodingResult(item.getGeocodingResult()));
    }

    /**
     * Create a BackupGeocodingResult from a GeocodingResult.
     *
     * @param result the GeocodingResult
     * @return the BackupGeocodingResult
     */
    public BackupGeocodingResult toBackupGeocodingResult(
            final GeocodingResult result) {
        if (result == null) {
            return null;
        }
        BackupAddressComponent[] backupAddressComponents;
        final AddressComponent[] addressComponents = result.addressComponents;
        if (addressComponents == null) {
            backupAddressComponents = null;
        } else {
            backupAddressComponents =
                    new BackupAddressComponent[addressComponents.length];
            for (int i = 0; i < addressComponents.length; i++) {
                backupAddressComponents[i] = toBackupAddressComponent(
                        addressComponents[i]);
            }
        }
        return new BackupGeocodingResult(
                backupAddressComponents,
                result.formattedAddress,
                result.postcodeLocalities,
                toBackupGeometry(result.geometry),
                result.types,
                result.partialMatch,
                result.placeId);
    }

    /**
     * Create a BackupAddressComponent from an AddressComponent.
     *
     * @param addressComponent the AddressComponent
     * @return the BackupAddressComponent
     */
    public BackupAddressComponent toBackupAddressComponent(
            final AddressComponent addressComponent) {
        if (addressComponent == null) {
            return null;
        }
        return new BackupAddressComponent(addressComponent.longName,
        addressComponent.shortName,
        addressComponent.types);
    }

    /**
     * Create a BackupGeometry from a Geometry.
     *
     * @param geometry the Geometry
     * @return the BackupGeometry
     */
    public BackupGeometry toBackupGeometry(final Geometry geometry) {
        if (geometry == null) {
            return null;
        }
        return new BackupGeometry(
                toBackupBounds(geometry.bounds),
                toBackupLatLng(geometry.location),
                geometry.locationType,
                toBackupBounds(geometry.viewport));
    }

    /**
     * Create a BackupBounds from a Bounds.
     *
     * @param bounds the Bounds
     * @return the BackupBounds
     */
    public BackupBounds toBackupBounds(final Bounds bounds) {
        if (bounds == null) {
            return null;
        }
        return new BackupBounds(toBackupLatLng(bounds.northeast),
                toBackupLatLng(bounds.southwest));
    }

    /**
     * Create a BackupLatLng from a LatLng.
     *
     * @param latLng the LatLng
     * @return the BackupLatLng
     */
    public BackupLatLng toBackupLatLng(final LatLng latLng) {
        if (latLng == null) {
            return null;
        }
        return new BackupLatLng(latLng.lat, latLng.lng);
    }
}
