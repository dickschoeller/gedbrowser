import { NO_ERRORS_SCHEMA, Type } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideRouter } from '@angular/router';
import { of } from 'rxjs';

import {
  DatasetsService,
  SaveService,
  UploadService,
  UserService,
  AuthService,
  AuthApiService,
  ConfigService
} from '../../services';

/**
 * Configuration options for setupListComponentTest
 */
export interface ListComponentTestOptions {
  /** Component inputs to set (e.g., { dataset: 'testDataset' }) */
  inputs?: Record<string, any>;
  /** Whether to call detectChanges after setup (default: true) */
  detectChanges?: boolean;
  /** Additional providers to include in TestBed */
  additionalProviders?: any[];
  /** Additional imports to include in TestBed */
  additionalImports?: any[];
  /** Service to inject (e.g., SourceService, NoteService) */
  primaryService?: Type<any>;
}

/**
 * Standard Material modules used in list and detail components
 */
const MATERIAL_MODULES = [
  MatTableModule,
  MatPaginatorModule,
  MatSortModule,
  MatToolbarModule,
  MatFormFieldModule,
  MatInputModule,
  MatButtonModule,
  MatIconModule,
  MatTooltipModule,
  MatSelectModule,
  ReactiveFormsModule,
  FormsModule
];

/**
 * Standard providers for list and detail components
 */
const STANDARD_PROVIDERS = [
  provideRouter([]),
  provideHttpClient(),
  provideHttpClientTesting(),
  provideAnimations(),
  { provide: DatasetsService, useValue: { get: () => of(['test-db']) } },
  { provide: SaveService, useValue: { getTextFile: (dataset: string) => of('GEDCOM content') } },
  { provide: UploadService, useValue: { uploadGedFile: (file: File) => of({ success: true }) } },
  { provide: UserService, useValue: { currentUser: null } },
  { provide: AuthService, useValue: { isLoggedIn: () => false, login: () => {}, logout: () => {} } },
  { provide: AuthApiService, useValue: { request: () => {} } },
  { provide: ConfigService, useValue: { apiUrl: 'http://localhost' } }
];

/**
 * Standard TestBed configuration for list and detail components
 * Includes all Material modules, common imports and providers
 */
export function setupListComponentTest<T>(
  componentClass: Type<T>,
  options: ListComponentTestOptions = {}
): {
  fixture: ComponentFixture<T>;
  component: T;
  primaryService?: any;
} {
  const {
    inputs = {},
    detectChanges = true,
    additionalProviders = [],
    additionalImports = [],
    primaryService
  } = options;

  const providers = [...STANDARD_PROVIDERS];
  if (primaryService) {
    providers.push(primaryService);
  }
  providers.push(...additionalProviders);

  TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [
      ...MATERIAL_MODULES,
      componentClass,
      ...additionalImports
    ],
    providers
  }).compileComponents();

  const fixture = TestBed.createComponent(componentClass);
  const component = fixture.componentInstance;

  // Set component inputs
  Object.assign(component, inputs);

  // Optionally trigger change detection
  if (detectChanges) {
    fixture.detectChanges();
  }

  const result: any = { fixture, component };
  if (primaryService) {
    result.primaryService = TestBed.inject(primaryService);
  }
  return result;
}

/**
 * Get injected service from TestBed by type
 */
export function getService<T>(serviceClass: Type<T>): T {
  return TestBed.inject(serviceClass);
}
