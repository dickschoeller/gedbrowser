import { ComponentFixture, TestBed } from '@angular/core/testing';
import { describe, beforeEach, afterEach, expect, it, vi } from 'vitest';

import { GoogleMapComponent } from './google-map.component';
import { PlaceInfo, LngLat } from '../../models';

describe('GoogleMapComponent', () => {
  let fixture: ComponentFixture<GoogleMapComponent>;
  let component: GoogleMapComponent;

  const fitBounds = vi.fn();
  const markerSetMap = vi.fn();

  beforeEach(async () => {
    const LatLng = vi.fn(function(this: any, latitude: number, longitude: number) {
      this.latitude = latitude;
      this.longitude = longitude;
    });
    const LatLngBounds = vi.fn(function(this: any) {
      this.extend = vi.fn();
    });
    const GoogleMap = vi.fn(function(this: any) {
      this.fitBounds = fitBounds;
    });
    const Marker = vi.fn(function(this: any, options: any) {
      this.getPosition = () => options.position;
      this.setMap = markerSetMap;
    });

    (globalThis as any).google = {
      maps: {
        MapTypeId: {
          ROADMAP: 'ROADMAP'
        },
        LatLng,
        LatLngBounds,
        Map: GoogleMap,
        Marker
      }
    };

    (GoogleMapComponent as any).mapsScriptPromise = null;

    await TestBed.configureTestingModule({
      imports: [GoogleMapComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(GoogleMapComponent);
    component = fixture.componentInstance;
  });

  afterEach(() => {
    vi.clearAllMocks();
    delete (globalThis as any).google;
    delete (globalThis as any).GOOGLE_MAPS_API_KEY;
  });

  it('should create', () => {
    fixture.detectChanges();
    expect(component).toBeTruthy();
  });

  it('does not render map card when there are no places', () => {
    component.places = [];
    fixture.detectChanges();

    const card = fixture.nativeElement.querySelector('.map-card');
    expect(card).toBeNull();
  });

  it('renders and initializes map when places are present', async () => {
    component.apiKey = 'test-maps-key';
    component.places = [
      new PlaceInfo(
        'Needham, Massachusetts, USA',
        new LngLat(-71.2377548, 42.2809285),
        new LngLat(-71.2477548, 42.2709285),
        new LngLat(-71.2277548, 42.2909285)
      )
    ];

    fixture.detectChanges();
    await fixture.whenStable();
    await new Promise(resolve => setTimeout(resolve, 0));

    const card = fixture.nativeElement.querySelector('.map-card');
    expect(card).toBeTruthy();
    expect((globalThis as any).google.maps.Map).toHaveBeenCalledTimes(1);
    expect((globalThis as any).google.maps.Marker).toHaveBeenCalledTimes(1);
    expect(fitBounds).toHaveBeenCalledTimes(1);
  });

  it('renders markers when location is represented as coordinates array', async () => {
    component.apiKey = 'test-maps-key';
    component.places = [
      {
        placeName: 'Ipswich, Suffolk, England',
        location: { coordinates: [1.15545, 52.05917] },
        southwest: [1.14545, 52.04917],
        northeast: [1.16545, 52.06917]
      } as unknown as PlaceInfo
    ];

    fixture.detectChanges();
    await fixture.whenStable();
    await new Promise(resolve => setTimeout(resolve, 0));

    expect((globalThis as any).google.maps.Marker).toHaveBeenCalledTimes(1);
    expect(fitBounds).toHaveBeenCalledTimes(1);
  });

  it('rejects loading when no API key is available', async () => {
    delete (globalThis as any).google;
    component.apiKey = '';
    (globalThis as any).GOOGLE_MAPS_API_KEY = '';

    await expect((component as any).ensureGoogleMapsLoaded()).rejects.toThrow('Google Maps API key is missing');
  });

  it('resolves API key from window when input key is empty', () => {
    component.apiKey = '';
    (globalThis as any).GOOGLE_MAPS_API_KEY = 'window-key';

    const key = (component as any).resolveApiKey();
    expect(key).toBe('window-key');
  });

  it('clears old markers before rendering new ones', async () => {
    component.apiKey = 'test-maps-key';
    component.places = [
      new PlaceInfo('Needham, Massachusetts, USA', new LngLat(-71.2377548, 42.2809285))
    ];

    fixture.detectChanges();
    await fixture.whenStable();
    await new Promise(resolve => setTimeout(resolve, 0));

    component.places = [
      { placeName: 'Ipswich', location: { lat: 52.05917, lng: 1.15545 } } as unknown as PlaceInfo
    ];
    component.ngOnChanges();

    await fixture.whenStable();
    await new Promise(resolve => setTimeout(resolve, 0));

    expect(markerSetMap).toHaveBeenCalledWith(null);
    expect((globalThis as any).google.maps.Marker).toHaveBeenCalledTimes(2);
  });

  it('does not fit bounds when all place locations are invalid', async () => {
    component.apiKey = 'test-maps-key';
    component.places = [
      { placeName: 'Bad place', location: { latitude: 'nope', longitude: 'nope' } } as unknown as PlaceInfo
    ];

    fixture.detectChanges();
    await fixture.whenStable();
    await new Promise(resolve => setTimeout(resolve, 0));

    expect((globalThis as any).google.maps.Marker).toHaveBeenCalledTimes(0);
    expect(fitBounds).toHaveBeenCalledTimes(0);
  });

  it('returns existing script promise when one is already in progress', async () => {
    delete (globalThis as any).google;
    const existingPromise = Promise.resolve();
    (GoogleMapComponent as any).mapsScriptPromise = existingPromise;

    const resolved = (component as any).ensureGoogleMapsLoaded();

    expect(resolved).toBe(existingPromise);
    await expect(resolved).resolves.toBeUndefined();
  });

  it('resolves when an existing script element emits load', async () => {
    delete (globalThis as any).google;
    component.apiKey = 'test-maps-key';
    const script = document.createElement('script');
    script.id = 'google-maps-script';
    document.head.appendChild(script);

    const promise = (component as any).ensureGoogleMapsLoaded();
    script.dispatchEvent(new Event('load'));

    await expect(promise).resolves.toBeUndefined();
    script.remove();
    (GoogleMapComponent as any).mapsScriptPromise = null;
  });

  it('rejects when an existing script element emits error', async () => {
    delete (globalThis as any).google;
    component.apiKey = 'test-maps-key';
    const script = document.createElement('script');
    script.id = 'google-maps-script';
    document.head.appendChild(script);

    const promise = (component as any).ensureGoogleMapsLoaded();
    script.dispatchEvent(new Event('error'));

    await expect(promise).rejects.toThrow('Script load failed');
    script.remove();
    (GoogleMapComponent as any).mapsScriptPromise = null;
  });

  it('returns null for invalid coordinate arrays in pointFromArray', () => {
    const point = (component as any).pointFromArray(['bad', 'data']);
    expect(point).toBeNull();
  });

  it('supports lon alias in parsePoint', () => {
    const point = (component as any).parsePoint({ lat: 10, lon: 20 });
    expect(point).toEqual({ latitude: 10, longitude: 20 });
  });

  it('returns null from parsePoint when coordinates array is invalid', () => {
    const point = (component as any).parsePoint({ coordinates: ['x', 'y'] });
    expect(point).toBeNull();
  });

  it('returns early from renderPlaces when map is not initialized', () => {
    component.places = [
      { placeName: 'Needham', location: { latitude: 42.28, longitude: -71.23 } } as unknown as PlaceInfo
    ];

    expect(() => (component as any).renderPlaces()).not.toThrow();
    expect((globalThis as any).google.maps.Marker).toHaveBeenCalledTimes(0);
  });

  it('sets loadError when initializeMap cannot load Google Maps', async () => {
    component.places = [
      new PlaceInfo('Needham, Massachusetts, USA', new LngLat(-71.2377548, 42.2809285))
    ];
    (component as any).mapContainer = { nativeElement: document.createElement('div') };
    vi.spyOn(component as any, 'ensureGoogleMapsLoaded').mockRejectedValue(new Error('nope'));

    (component as any).initializeMap();
    await Promise.resolve();
    await Promise.resolve();

    expect(component.loadError).toBe(true);
  });

  it('creates a script tag and resolves when script loads', async () => {
    delete (globalThis as any).google;
    component.apiKey = 'test-maps-key';
    (GoogleMapComponent as any).mapsScriptPromise = null;

    const promise = (component as any).ensureGoogleMapsLoaded();
    const script = document.getElementById('google-maps-script') as HTMLScriptElement;

    expect(script).toBeTruthy();
    expect(script.src).toContain('maps.googleapis.com/maps/api/js?loading=async');
    expect(script.src).toContain('key=test-maps-key');

    script.onload?.(new Event('load'));
    await expect(promise).resolves.toBeUndefined();

    script.remove();
    (GoogleMapComponent as any).mapsScriptPromise = null;
  });

  it('creates a script tag and rejects when script fails', async () => {
    delete (globalThis as any).google;
    component.apiKey = 'test-maps-key';
    (GoogleMapComponent as any).mapsScriptPromise = null;

    const promise = (component as any).ensureGoogleMapsLoaded();
    const script = document.getElementById('google-maps-script') as HTMLScriptElement;

    script.onerror?.(new Event('error'));
    await expect(promise).rejects.toThrow('Script load failed');

    script.remove();
    (GoogleMapComponent as any).mapsScriptPromise = null;
  });
});
