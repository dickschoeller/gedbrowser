package org.schoellerfamily.geoservice.model.builder;

import java.util.Arrays;
import java.util.List;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;
import org.geojson.Point;
import org.geojson.Polygon;
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
import com.google.maps.model.LocationType;

/**
 * Builder class to convert between our own geocoding results and
 * Google's types.
 *
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.GodClass" })
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
        final AddressComponent[] addressComponents =
                gsResult.getAddressComponents();
        if (addressComponents == null) {
            result.addressComponents = null;
        } else {
            result.addressComponents =
                    new AddressComponent[addressComponents.length];
            for (int i = 0; i < addressComponents.length; i++) {
                result.addressComponents[i] = copy(addressComponents[i]);
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
     * Copy an address component. Since they are NOT immutable, I don't
     * want to mess with the variability of the damn things.
     *
     * @param in the component to copy
     * @return the copy
     */
    private AddressComponent copy(final AddressComponent in) {
        final AddressComponent out = new AddressComponent();
        out.longName = in.longName;
        out.shortName = in.shortName;
        out.types = Arrays.copyOf(in.types, in.types.length);
        return out;
    }

    /**
     * Create a Geometry from a GeoServiceGeometry.
     *
     * @param featureCollection the feature collection representing the geometry
     * @return the Geometry
     */
    public Geometry toGeometry(final FeatureCollection featureCollection) {
        if (featureCollection == null) {
            return null;
        }
        final Geometry geometry = new Geometry();
        Feature location;
        if (featureCollection.getFeatures().isEmpty()) {
            location = null;
            geometry.bounds = toBounds(null);
            geometry.viewport = toBounds(null);
        } else {
            location = featureCollection.getFeatures().get(0);
            geometry.bounds = toBounds(featureCollection.getFeatures().get(1));
            geometry.viewport =
                    toBounds(featureCollection.getFeatures().get(2));
        }
        if (location == null) {
            geometry.location = null;
            geometry.locationType = null;
        } else {
            geometry.location = toLatLng((Point) location.getGeometry());
            geometry.locationType =
                    toLocationType(location.getProperty("locationType"));
        }
        return geometry;
    }

    /**
     * Convert a property to a Google LocationType.
     *
     * @param property we can expect this to be a string
     * @return the LocationType
     */
    private LocationType toLocationType(final Object property) {
        if (property == null) {
            return null;
        }
        return LocationType.valueOf(property.toString());
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
        if (feature.getGeometry() instanceof Polygon) {
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
        } else if (feature.getGeometry() instanceof Point) {
            final Point polygon = (Point) feature.getGeometry();
            final LngLatAlt coordinates = polygon.getCoordinates();
            if (coordinates == null) {
                return new Bounds();
            }
            final LngLatAlt northeast = null;
            final LngLatAlt southwest = coordinates;
            final Bounds bounds = new Bounds();
            bounds.northeast = toLatLng(northeast);
            bounds.southwest = toLatLng(southwest);
            return bounds;
        }
        return null;
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
        return new GeoServiceGeocodingResult(
                result.addressComponents,
                result.formattedAddress,
                result.postcodeLocalities,
                toGeoServiceGeometry(result.geometry),
                result.types,
                result.partialMatch,
                result.placeId);
    }

    /**
     * Create a GeoServiceGeometry from a Geometry.
     *
     * @param geometry the Geometry
     * @return the GeoServiceGeometry
     */
    public FeatureCollection toGeoServiceGeometry(final Geometry geometry) {
        if (geometry == null) {
            return GeoServiceGeometry.createFeatureCollection(
                    toLocation(new LatLng(Double.NaN, Double.NaN),
                            LocationType.UNKNOWN),
                    null, null);
        }
        return GeoServiceGeometry.createFeatureCollection(
                toLocation(geometry.location, geometry.locationType),
                toBox("bounds", geometry.bounds),
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
        if (bounds.southwest == null || bounds.northeast == null) {
            throw new IllegalArgumentException(
                    "Must have legitimate bounding box");
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
     * @param locationType the location type
     * @return the GeoServiceLatLng
     */
    public Feature toLocation(final LatLng latLng,
            final LocationType locationType) {
        if (latLng == null) {
            final Feature feature = new Feature();
            feature.setProperty("locationType", locationType);
            feature.setId("location");
            return feature;
        }
        final Point point = new Point(latLng.lng, latLng.lat);
        final Feature feature = new Feature();
        feature.setGeometry(point);
        feature.setProperty("locationType", locationType);
        feature.setId("location");
        return feature;
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
