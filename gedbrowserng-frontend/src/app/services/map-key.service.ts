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
    return this.http.get(mapKeyUrl).pipe(
      map((response: any) => response?.key || ''),
      catchError(() => this.apiService.get(mapKeyUrl).pipe(
        map((response: any) => response?.key || ''),
        catchError(() => of(''))
      ))
    );
  }
}
