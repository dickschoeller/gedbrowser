import { Inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { AuthApiService } from './auth-api.service';
import { ConfigService } from './config.service';

@Injectable()
export class MapKeyService {
  constructor(
    @Inject(HttpClient) private readonly http: HttpClient,
    @Inject(AuthApiService) private readonly apiService: AuthApiService,
    @Inject(ConfigService) private readonly config: ConfigService
  ) {}

  getMapKey() {
    const mapKeyUrl = this.config.map_key_url;

    // Try a simple GET first to avoid unnecessary CORS preflight requirements.
    return this.http.get(mapKeyUrl, { responseType: 'text' }).pipe(
      map((response: string) => this.extractKey(response)),
      catchError(() => this.apiService.get(mapKeyUrl).pipe(
        map((response: any) => this.extractKey(response)),
        catchError(() => of(''))
      ))
    );
  }

  private extractKey(response: any): string {
    if (typeof response === 'string') {
      const trimmed = response.trim();

      if (!trimmed) {
        return '';
      }

      // If the response is JSON text (e.g. '{"key":"..."}' or '"..."'),
      // attempt to parse it and extract a key property if present.
      try {
        const parsed: any = JSON.parse(trimmed);
        if (parsed && typeof parsed.key === 'string') {
          return parsed.key.trim();
        }
      } catch {
        // Not JSON, fall through to return the trimmed string as-is.
      }

      return trimmed;
    }

    if (response && typeof response.key === 'string') {
      return response.key.trim();
    }

    return '';
  }
}
