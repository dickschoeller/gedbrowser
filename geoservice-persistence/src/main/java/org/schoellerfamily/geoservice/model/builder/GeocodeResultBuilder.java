package org.schoellerfamily.geoservice.model.builder;

import java.util.List;

import org.geojson.Feature;
import org.geojson.LngLatAlt;
import org.geojson.Point;
import org.geojson.Polygon;
import org.schoellerfamily.geoservice.model.GeoServiceAddressComponent;
import org.schoellerfamily.geoservice.model.GeoServiceBounds;
import org.schoellerfamily.geoservice.model.GeoServiceGeocodingResult;
import org.schoellerfamily.geoservice.model.GeoServiceGeometry;
import org.schoellerfamily.geoservice.model.GeoServiceItem;
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
     * @param feature the bounding box feature
     * @return the Bounds
     */
    public Bounds toBounds(final Feature feature) {
        if (feature == null) {
            return null;
        }
        final Polygon polygon = (Polygon) feature.getGeometry();
        final List<List<LngLatAlt>> coordinates = polygon.getCoordinates();
        if (coordinates == null || coordinates.isEmpty()) {
            return new Bounds();
        }
        final List<LngLatAlt> list = coordinates.get(0);
        if (list == null || list.isEmpty()) {
            return new Bounds();
        }
        final LngLatAlt northeast = list.get(2);
        final LngLatAlt southwest = list.get(0);
        final Bounds bounds = new Bounds();
        bounds.northeast = toLatLng(northeast);
        bounds.southwest = toLatLng(southwest);
        return bounds;
    }

    /**
     * Create a LatLng from a GeoJSON Point.
     *
     * @param point the Point
     * @return the LatLng
     */
    public LatLng toLatLng(final Point point) {
        if (point == null) {
            return null;
        }
        return toLatLng(point.getCoordinates());
    }

    /**
     * Create a LatLng from a GeoJSON LngLatAlt.
     *
     * @param lla the LngLatAlt
     * @return the LatLng
     */
    public LatLng toLatLng(final LngLatAlt lla) {
        if (lla == null) {
            return null;
        }
        final LatLng latLng = new LatLng(
                lla.getLatitude(),
                lla.getLongitude());
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
                toBox("bounds", geometry.bounds),
                toPoint(geometry.location),
                geometry.locationType,
                toBox("viewport", geometry.viewport));
    }

    /**
     * Create a Feature containing a single Polygon describing the bounding box
     * from the provided Bounds.
     *
     * @param id the ID string
     * @param bounds the Bounds
     * @return the Feature
     */
    public Feature toBox(final String id, final Bounds bounds) {
        if (bounds == null) {
            return null;
        }
        return GeoServiceBounds.createBounds(id,
                toLngLatAlt(bounds.southwest),
                toLngLatAlt(bounds.northeast));
    }

    /**
     * Create a GeoJSON Point from a LatLng.
     *
     * @param latLng the LatLng
     * @return the GeoServiceLatLng
     */
    public Point toPoint(final LatLng latLng) {
        if (latLng == null) {
            return null;
        }
        return new Point(latLng.lng, latLng.lat);
    }

    /**
     * Create a GeoJSON Point from a LatLng.
     *
     * @param latLng the LatLng
     * @return the GeoServiceLatLng
     */
    public LngLatAlt toLngLatAlt(final LatLng latLng) {
        if (latLng == null) {
            return null;
        }
        return new LngLatAlt(latLng.lng, latLng.lat);
    }
}
