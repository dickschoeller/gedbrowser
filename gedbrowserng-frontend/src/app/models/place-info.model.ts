import { LngLat } from './lnglat.model';

/**
 * Represents place information including location and bounding box.
 */
export class PlaceInfo {
  placeName = '';
  location: LngLat;
  southwest?: LngLat;
  northeast?: LngLat;

  constructor(placeName?: string, location?: LngLat, southwest?: LngLat, northeast?: LngLat) {
    this.placeName = placeName || '';
    this.location = location || new LngLat();
    this.southwest = southwest;
    this.northeast = northeast;
  }
}
