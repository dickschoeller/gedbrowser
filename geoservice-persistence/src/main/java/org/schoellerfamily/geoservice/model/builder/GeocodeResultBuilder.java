package org.schoellerfamily.geoservice.model.builder;

import org.schoellerfamily.geoservice.model.GeoServiceAddressComponent;
import org.schoellerfamily.geoservice.model.GeoServiceBounds;
import org.schoellerfamily.geoservice.model.GeoServiceItem;
import org.schoellerfamily.geoservice.model.GeoServiceGeocodingResult;
import org.schoellerfamily.geoservice.model.GeoServiceGeometry;
import org.schoellerfamily.geoservice.model.GeoServiceLatLng;
import org.schoellerfamily.geoservice.persistence.GeoCodeItem;

import com.google.maps.model.AddressComponent;
import com.google.maps.model.Bounds;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import com.google.maps.model.LatLng;

/**
 * Builder class to convert between our own geocoding results and
 * Google's types.
 *
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class GeocodeResultBuilder {
    /**
     * Create a GeoCodeItem from a GeoServiceItem.
     *
     * @param gsItem the GeoServiceItem
     * @return the GeoCodeItem
     */
    public GeoCodeItem toGeoCodeItem(final GeoServiceItem gsItem) {
        if (gsItem == null) {
            return null;
        }
        return new GeoCodeItem(gsItem.getPlaceName(),
                gsItem.getModernPlaceName(),
                toGeocodingResult(gsItem.getResult()));
    }

    /**
     * Create a GeocodingResult from a GeoServiceGeocodingResult.
     *
     * @param gsResult the GeoServiceGeocodingResult
     * @return the GeocodingResult
     */
    public GeocodingResult toGeocodingResult(
            final GeoServiceGeocodingResult gsResult) {
        if (gsResult == null) {
            return null;
        }
        final GeocodingResult result = new GeocodingResult();
        final GeoServiceAddressComponent[] addressComponents =
                gsResult.getAddressComponents();
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
        result.formattedAddress = gsResult.getFormattedAddress();
        result.geometry = toGeometry(gsResult.getGeometry());
        result.partialMatch = gsResult.isPartialMatch();
        result.placeId = gsResult.getPlaceId();
        // This is safe because the gs object returns a copy of its array.
        result.postcodeLocalities = gsResult.getPostcodeLocalities();
        // This is safe because the gs object returns a copy of its array.
        result.types = gsResult.getTypes();
        return result;
    }

    /**
     * Create an AddressComponent from a GeoServiceAddressComponent.
     *
     * @param gsComponent the GeoServiceAddressComponent
     * @return the AddressComponent
     */
    public AddressComponent toAddressComponent(
            final GeoServiceAddressComponent gsComponent) {
        if (gsComponent == null) {
            return null;
        }
        final AddressComponent component = new AddressComponent();
        component.longName = gsComponent.getLongName();
        component.shortName = gsComponent.getShortName();
        // This is safe because gs object returns a copy of its array.
        component.types = gsComponent.getTypes();
        return component;
    }

    /**
     * Create a Geometry from a GeoServiceGeometry.
     *
     * @param gsGeometry the gsGeometry
     * @return the Geometry
     */
    public Geometry toGeometry(final GeoServiceGeometry gsGeometry) {
        if (gsGeometry == null) {
            return null;
        }
        final Geometry geometry = new Geometry();
        geometry.bounds = toBounds(gsGeometry.getBounds());
        geometry.location = toLatLng(gsGeometry.getLocation());
        geometry.locationType = gsGeometry.getLocationType();
        geometry.viewport = toBounds(gsGeometry.getViewport());
        return geometry;
    }

    /**
     * Create a Bounds from a GeoServiceBounds.
     *
     * @param gsBounds the GeoServiceBounds
     * @return the Bounds
     */
    public Bounds toBounds(final GeoServiceBounds gsBounds) {
        if (gsBounds == null) {
            return null;
        }
        final Bounds bounds = new Bounds();
        bounds.northeast = toLatLng(gsBounds.getNortheast());
        bounds.southwest = toLatLng(gsBounds.getSouthwest());
        return bounds;
    }

    /**
     * Create a LatLng from a GeoServiceLatLng.
     *
     * @param gsLatLng the GeoServiceLatLng
     * @return the LatLng
     */
    public LatLng toLatLng(final GeoServiceLatLng gsLatLng) {
        if (gsLatLng == null) {
            return null;
        }
        final LatLng latLng = new LatLng(gsLatLng.getLatitude(),
                gsLatLng.getLongitude());
        return latLng;
    }

    /**
     * Create a GeoServiceItem from a GeoCodeItem.
     *
     * @param item the GeoCodeItem
     * @return the GeoServiceItem
     */
    public GeoServiceItem toGeoServiceItem(final GeoCodeItem item) {
        if (item == null) {
            return null;
        }
        return new GeoServiceItem(item.getPlaceName(),
                item.getModernPlaceName(),
                toGeoServiceGeocodingResult(item.getGeocodingResult()));
    }

    /**
     * Create a GeoServiceGeocodingResult from a GeocodingResult.
     *
     * @param result the GeocodingResult
     * @return the GeoServiceGeocodingResult
     */
    public GeoServiceGeocodingResult toGeoServiceGeocodingResult(
            final GeocodingResult result) {
        if (result == null) {
            return null;
        }
        GeoServiceAddressComponent[] gsAddressComponents;
        final AddressComponent[] addressComponents = result.addressComponents;
        if (addressComponents == null) {
            gsAddressComponents = null;
        } else {
            gsAddressComponents =
                    new GeoServiceAddressComponent[addressComponents.length];
            for (int i = 0; i < addressComponents.length; i++) {
                gsAddressComponents[i] = toGeoServiceAddressComponent(
                        addressComponents[i]);
            }
        }
        return new GeoServiceGeocodingResult(
                gsAddressComponents,
                result.formattedAddress,
                result.postcodeLocalities,
                toGeoServiceGeometry(result.geometry),
                result.types,
                result.partialMatch,
                result.placeId);
    }

    /**
     * Create a GeoServiceAddressComponent from an AddressComponent.
     *
     * @param addressComponent the AddressComponent
     * @return the GeoServiceAddressComponent
     */
    public GeoServiceAddressComponent toGeoServiceAddressComponent(
            final AddressComponent addressComponent) {
        if (addressComponent == null) {
            return null;
        }
        return new GeoServiceAddressComponent(addressComponent.longName,
        addressComponent.shortName,
        addressComponent.types);
    }

    /**
     * Create a GeoServiceGeometry from a Geometry.
     *
     * @param geometry the Geometry
     * @return the GeoServiceGeometry
     */
    public GeoServiceGeometry toGeoServiceGeometry(final Geometry geometry) {
        if (geometry == null) {
            return null;
        }
        return new GeoServiceGeometry(
                toGeoServiceBounds(geometry.bounds),
                toGeoServiceLatLng(geometry.location),
                geometry.locationType,
                toGeoServiceBounds(geometry.viewport));
    }

    /**
     * Create a GeoServiceBounds from a Bounds.
     *
     * @param bounds the Bounds
     * @return the GeoServiceBounds
     */
    public GeoServiceBounds toGeoServiceBounds(final Bounds bounds) {
        if (bounds == null) {
            return null;
        }
        return new GeoServiceBounds(toGeoServiceLatLng(bounds.northeast),
                toGeoServiceLatLng(bounds.southwest));
    }

    /**
     * Create a GeoServiceLatLng from a LatLng.
     *
     * @param latLng the LatLng
     * @return the GeoServiceLatLng
     */
    public GeoServiceLatLng toGeoServiceLatLng(final LatLng latLng) {
        if (latLng == null) {
            return null;
        }
        return new GeoServiceLatLng(latLng.lat, latLng.lng);
    }
}
