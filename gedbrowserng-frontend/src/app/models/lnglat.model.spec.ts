import { LngLat } from './lnglat.model';

describe('LngLat Model', () => {
  it('uses provided longitude and latitude', () => {
    const point = new LngLat(-71.2377548, 42.2809285);

    expect(point.longitude).toBe(-71.2377548);
    expect(point.latitude).toBe(42.2809285);
  });

  it('defaults both values to 0 when omitted', () => {
    const point = new LngLat();

    expect(point.longitude).toBe(0);
    expect(point.latitude).toBe(0);
  });

  it('falls back to 0 when values are 0 or undefined', () => {
    const point = new LngLat(0, undefined);

    expect(point.longitude).toBe(0);
    expect(point.latitude).toBe(0);
  });
});
