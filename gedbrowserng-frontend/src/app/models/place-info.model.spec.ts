import { LngLat } from './lnglat.model';
import { PlaceInfo } from './place-info.model';

describe('PlaceInfo Model', () => {
  it('uses provided constructor values', () => {
    const location = new LngLat(-71.2377548, 42.2809285);
    const southwest = new LngLat(-71.2477548, 42.2709285);
    const northeast = new LngLat(-71.2277548, 42.2909285);

    const place = new PlaceInfo('Needham, Massachusetts, USA', location, southwest, northeast);

    expect(place.placeName).toBe('Needham, Massachusetts, USA');
    expect(place.location).toBe(location);
    expect(place.southwest).toBe(southwest);
    expect(place.northeast).toBe(northeast);
  });

  it('defaults name and location when omitted', () => {
    const place = new PlaceInfo();

    expect(place.placeName).toBe('');
    expect(place.location).toEqual(new LngLat());
    expect(place.southwest).toBeUndefined();
    expect(place.northeast).toBeUndefined();
  });

  it('falls back for falsey place name while preserving provided location', () => {
    const location = new LngLat(1, 2);
    const place = new PlaceInfo('', location);

    expect(place.placeName).toBe('');
    expect(place.location).toBe(location);
  });
});
