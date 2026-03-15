import { CommonModule } from '@angular/common';
import { AfterViewInit, Component, ElementRef, Input, OnChanges, ViewChild } from '@angular/core';
import { MatCard, MatCardContent, MatCardTitle } from '@angular/material/card';

import { PlaceInfo } from '../../models';
import { environment } from '../../../environments/environment';

declare global {
  interface Window {
    google?: any;
    GOOGLE_MAPS_API_KEY?: string;
  }
}

/**
 * Displays place locations on a Google map and auto-fits the viewport bounds.
 */
@Component({
  selector: 'app-google-map',
  standalone: true,
  imports: [CommonModule, MatCard, MatCardTitle, MatCardContent],
  template: `@if (hasPlaces()) {
  <mat-card class="map-card">
    <mat-card-title>{{ title }}</mat-card-title>
    <mat-card-content>
      <div #mapContainer class="map-canvas"></div>
      @if (loadError) {
        <div class="map-error">Unable to load Google Maps.</div>
      }
    </mat-card-content>
  </mat-card>
}`,
  styles: [`
.map-card {
  height: 100%;
}

.map-canvas {
  width: 100%;
  min-height: 260px;
  height: 320px;
  border-radius: 4px;
}

.map-error {
  margin-top: 8px;
  color: #b00020;
}
  `]
})
export class GoogleMapComponent implements AfterViewInit, OnChanges {
  @Input() places: Array<PlaceInfo> | null | undefined = [];
  @Input() apiKey = '';
  @Input() title = 'Places Map';
  @ViewChild('mapContainer') mapContainer?: ElementRef<HTMLDivElement>;

  private map: any;
  private markers: any[] = [];
  private static mapsScriptPromise: Promise<void> | null = null;
  private static readonly scriptId = 'google-maps-script';

  loadError = false;

  ngAfterViewInit(): void {
    this.initializeMap();
  }

  ngOnChanges(): void {
    this.initializeMap();
  }

  hasPlaces(): boolean {
    return (this.places?.length ?? 0) > 0;
  }

  private initializeMap(): void {
    if (!this.hasPlaces() || !this.mapContainer) {
      return;
    }

    this.ensureGoogleMapsLoaded()
      .then(() => {
        this.loadError = false;
        if (!this.map) {
          this.map = new globalThis.google.maps.Map(this.mapContainer?.nativeElement, {
            mapTypeId: globalThis.google.maps.MapTypeId.ROADMAP
          });
        }
        this.renderPlaces();
      })
      .catch(() => {
        this.loadError = true;
      });
  }

  private ensureGoogleMapsLoaded(): Promise<void> {
    if (globalThis.google?.maps) {
      return Promise.resolve();
    }
    if (GoogleMapComponent.mapsScriptPromise !== null) {
      return GoogleMapComponent.mapsScriptPromise;
    }

    const mapKey = this.resolveApiKey();
    if (!mapKey) {
      return Promise.reject(new Error('Google Maps API key is missing'));
    }

    GoogleMapComponent.mapsScriptPromise = new Promise<void>((resolve, reject) => {
      const existing = document.getElementById(GoogleMapComponent.scriptId) as HTMLScriptElement | null;
      if (existing) {
        existing.addEventListener('load', () => resolve(), { once: true });
        existing.addEventListener('error', () => {
          GoogleMapComponent.mapsScriptPromise = null;
          existing.remove();
          reject(new Error('Script load failed'));
        }, { once: true });
        return;
      }

      const script = document.createElement('script');
      script.id = GoogleMapComponent.scriptId;
      script.async = true;
      script.defer = true;

      script.src = `https://maps.googleapis.com/maps/api/js?key=${encodeURIComponent(mapKey)}`;

      script.onload = () => resolve();
      script.onerror = () => {
        GoogleMapComponent.mapsScriptPromise = null;
        script.remove();
        reject(new Error('Script load failed'));
      };
      document.head.appendChild(script);
    });

    return GoogleMapComponent.mapsScriptPromise;
  }

  private resolveApiKey(): string {
    return this.apiKey || globalThis.GOOGLE_MAPS_API_KEY
      || (environment as any).googleMapsApiKey || '';
  }

  private renderPlaces(): void {
    if (!this.map || !globalThis.google?.maps || !this.hasPlaces()) {
      return;
    }

    this.clearMarkers();

    const bounds = new globalThis.google.maps.LatLngBounds();
    let visibleMarkers = 0;

    for (const place of this.places ?? []) {
      const markerLocation = this.parsePoint((place as any)?.location);
      if (!markerLocation) {
        continue;
      }

      const latLng = new globalThis.google.maps.LatLng(
        markerLocation.latitude, markerLocation.longitude);
      const marker = new globalThis.google.maps.Marker({
        position: latLng,
        map: this.map,
        title: place.placeName
      });
      this.markers.push(marker);
      bounds.extend(marker.getPosition());
      visibleMarkers++;

      const southwest = this.parsePoint((place as any)?.southwest);
      if (southwest) {
        bounds.extend(new globalThis.google.maps.LatLng(
          southwest.latitude, southwest.longitude));
      }

      const northeast = this.parsePoint((place as any)?.northeast);
      if (northeast) {
        bounds.extend(new globalThis.google.maps.LatLng(
          northeast.latitude, northeast.longitude));
      }
    }

    if (visibleMarkers > 0) {
      this.map.fitBounds(bounds);
    }
  }

  private clearMarkers(): void {
    for (const marker of this.markers) {
      marker.setMap(null);
    }
    this.markers = [];
  }

  private parsePoint(point: any): { latitude: number; longitude: number } | null {
    if (!point) {
      return null;
    }

    // Handle GeoJSON-style coordinates array: [longitude, latitude].
    if (Array.isArray(point)) {
      return this.pointFromArray(point);
    }

    if (Array.isArray(point.coordinates)) {
      return this.pointFromArray(point.coordinates);
    }

    const latitude = Number(point.latitude ?? point.lat);
    const longitude = Number(point.longitude ?? point.lng ?? point.lon);
    if (!Number.isFinite(latitude) || !Number.isFinite(longitude)) {
      return null;
    }
    return { latitude, longitude };
  }

  private pointFromArray(coordinates: any[]): { latitude: number; longitude: number } | null {
    const longitude = Number(coordinates[0]);
    const latitude = Number(coordinates[1]);
    if (!Number.isFinite(latitude) || !Number.isFinite(longitude)) {
      return null;
    }
    return { latitude, longitude };
  }
}
