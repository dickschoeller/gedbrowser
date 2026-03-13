/**
 * Represents a longitude/latitude coordinate.
 */
export class LngLat {
  longitude: number;
  latitude: number;

  constructor(longitude?: number, latitude?: number) {
    this.longitude = longitude || 0;
    this.latitude = latitude || 0;
  }
}
