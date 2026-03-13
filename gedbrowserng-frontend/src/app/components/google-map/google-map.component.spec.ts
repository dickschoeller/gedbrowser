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
    const Map = vi.fn(function(this: any) {
      this.fitBounds = fitBounds;
    });
    const Marker = vi.fn(function(this: any, options: any) {
      this.getPosition = () => options.position;
      this.setMap = markerSetMap;
    });

    (window as any).google = {
      maps: {
        MapTypeId: {
          ROADMAP: 'ROADMAP'
        },
        LatLng,
        LatLngBounds,
        Map,
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
    delete (window as any).google;
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
    expect((window as any).google.maps.Map).toHaveBeenCalledTimes(1);
    expect((window as any).google.maps.Marker).toHaveBeenCalledTimes(1);
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

    expect((window as any).google.maps.Marker).toHaveBeenCalledTimes(1);
    expect(fitBounds).toHaveBeenCalledTimes(1);
  });
});
