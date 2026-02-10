import { NO_ERRORS_SCHEMA, Type } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatDialogModule } from '@angular/material/dialog';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { provideRouter } from '@angular/router';
import { of } from 'rxjs';
import { vi } from 'vitest';

import {
  ConfigService,
  AuthApiService,
  UserService,
  PersonService,
  FamilyService,
  SaveService,
  UploadService,
  AuthService,
  DatasetsService
} from '../../services';
import { ApiPerson, ApiAttribute, ApiFamily } from '../../models';

/**
 * Common mock for PersonService
 */
export function createMockPersonService(overrides?: Partial<PersonService>): any {
  return {
    getOne: vi.fn().mockReturnValue(of({
      string: 'test',
      indexName: 'Test Person',
      lifespan: {
        birthYear: undefined,
        deathYear: undefined,
        birthDate: undefined,
        deathDate: undefined
      }
    } as ApiPerson)),
    deleteLink: vi.fn().mockReturnValue(of({})),
    ...overrides
  };
}

/**
 * Common mock for FamilyService
 */
export function createMockFamilyService(overrides?: Partial<FamilyService>): any {
  return {
    getOne: vi.fn().mockReturnValue(of({
      attributes: [],
      spouses: []
    } as ApiFamily)),
    put: vi.fn().mockReturnValue(of({})),
    ...overrides
  };
}

/**
 * Common mock for UserService
 */
export function createMockUserService(): any {
  return {
    currentUser: null
  };
}

/**
 * Common mock for ConfigService
 */
export function createMockConfigService(): any {
  return {
    config: {}
  };
}

/**
 * Common mock for AuthApiService
 */
export function createMockAuthApiService(): any {
  return {};
}

/**
 * Common mock for SaveService
 */
export function createMockSaveService(): any {
  return {
    save: vi.fn().mockReturnValue(of({}))
  };
}

/**
 * Common mock for UploadService
 */
export function createMockUploadService(): any {
  return {
    upload: vi.fn().mockReturnValue(of({}))
  };
}

/**
 * Common mock for AuthService
 */
export function createMockAuthService(): any {
  return {
    getToken: vi.fn().mockReturnValue('test-token')
  };
}

/**
 * Common mock for DatasetsService
 */
export function createMockDatasetsService(): any {
  return {
    getSelectedDatasets: vi.fn().mockReturnValue([])
  };
}

/**
 * Configuration options for setupPersonComponentTest
 */
export interface PersonComponentTestOptions {
  /** Component inputs to set (e.g., { dataset: 'testDataset', index: 0 }) */
  inputs?: Record<string, any>;
  /** Whether to call detectChanges after setup (default: false) */
  detectChanges?: boolean;
  /** Additional providers to include in TestBed */
  additionalProviders?: any[];
  /** Additional imports to include in TestBed */
  additionalImports?: any[];
  /** Custom mock overrides for PersonService */
  personServiceOverrides?: Partial<PersonService>;
  /** Custom mock overrides for FamilyService */
  familyServiceOverrides?: Partial<FamilyService>;
}

/**
 * Standard TestBed configuration for person module components
 * Includes all common imports and providers
 */
export function setupPersonComponentTest<T>(
  componentClass: Type<T>,
  options: PersonComponentTestOptions = {}
): {
  fixture: ComponentFixture<T>;
  component: T;
  mockPersonService: any;
  mockFamilyService: any;
  mockUserService: any;
} {
  const {
    inputs = {},
    detectChanges = false,
    additionalProviders = [],
    additionalImports = [],
    personServiceOverrides = {},
    familyServiceOverrides = {}
  } = options;

  const mockPersonService = createMockPersonService(personServiceOverrides);
  const mockFamilyService = createMockFamilyService(familyServiceOverrides);
  const mockUserService = createMockUserService();

  TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [
      MatButtonModule,
      MatSelectModule,
      MatFormFieldModule,
      MatInputModule,
      ReactiveFormsModule,
      FormsModule,
      MatDialogModule,
      componentClass,
      ...additionalImports
    ],
    providers: [
      provideRouter([]),
      provideHttpClient(),
      provideHttpClientTesting(),
      { provide: PersonService, useValue: mockPersonService },
      { provide: FamilyService, useValue: mockFamilyService },
      { provide: UserService, useValue: mockUserService },
      AuthApiService,
      ConfigService,
      SaveService,
      UploadService,
      AuthService,
      DatasetsService,
      ...additionalProviders
    ]
  }).compileComponents();

  const fixture = TestBed.createComponent(componentClass);
  const component = fixture.componentInstance;

  // Set component inputs
  Object.assign(component, inputs);

  // Optionally trigger change detection
  if (detectChanges) {
    fixture.detectChanges();
  }

  return { fixture, component, mockPersonService, mockFamilyService, mockUserService };
}

/**
 * Helper to create a test ApiPerson with optional overrides
 */
export function createTestPerson(overrides?: Partial<ApiPerson>): ApiPerson {
  return {
    string: 'P1',
    indexName: 'Test Person',
    lifespan: {
      birthYear: 1950,
      deathYear: 2020,
      birthDate: '1 JAN 1950',
      deathDate: '15 DEC 2020'
    },
    ...overrides
  } as ApiPerson;
}

/**
 * Helper to create a test ApiFamily with optional spouses and children
 */
export function createTestFamily(
  spouses?: ApiAttribute[],
  children?: ApiAttribute[]
): ApiFamily {
  const fam = new ApiFamily();
  (fam as any).string = 'F1';
  (fam as any).spouses = spouses || [];
  (fam as any).children = children || [];
  (fam as any).attributes = [];
  return fam;
}

/**
 * Helper to create a test ApiAttribute
 */
export function createTestAttribute(string: string): ApiAttribute {
  const attr = new ApiAttribute();
  attr.string = string as any;
  return attr;
}
