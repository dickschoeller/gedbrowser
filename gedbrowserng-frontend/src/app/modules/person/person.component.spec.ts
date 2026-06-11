import { NO_ERRORS_SCHEMA, Component, Input } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute, provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { vi } from 'vitest';

import { PersonComponent } from './person.component';
import { PersonService, DatasetsService, SaveService, UploadService, UserService, AuthService, AuthApiService, ConfigService, MapKeyService } from '../../services';
import { ApiPerson, ApiAttribute, ApiLifespan } from '../../models';

// Mock component to replace the child app-main-layout
@Component({
    selector: 'app-main-layout',
    template: '<ng-content></ng-content>',
    imports: []
})
class MockMainLayoutComponent {
  @Input() dataset: string;
}

// Mock attribute-list component
@Component({
    selector: 'app-attribute-list',
    template: '',
    imports: []
})
class MockAttributeListComponent {
  @Input() dataset: string;
  @Input() parent: any;
  @Input() attributes: any[];
  @Input() toggleable: boolean;
}

// Mock multimedia-gallery component
@Component({
    selector: 'app-multimedia-gallery',
    template: '',
    imports: []
})
class MockMultimediaGalleryComponent {
  @Input() dataset: string;
  @Input() parent: any;
  @Input() multimedia: any[];
}

// Mock person-family-list component
@Component({
    selector: 'app-person-family-list',
    template: '',
    imports: []
})
class MockPersonFamilyListComponent {
  @Input() dataset: string;
  @Input() person: any;
  @Input() parent: any;
}

// Mock person-parent-families component
@Component({
    selector: 'app-person-parent-families',
    template: '',
    imports: []
})
class MockPersonParentFamiliesComponent {
  @Input() dataset: string;
  @Input() person: any;
  @Input() parent: any;
}

describe('PersonComponent', () => {
  let component: PersonComponent;
  let fixture: ComponentFixture<PersonComponent>;

  const mockPerson: ApiPerson = {
    indexName: 'John Doe',
    surname: 'Doe',
    string: 'John Doe',
    attributes: [],
    lifespan: {} as ApiLifespan,
    famss: [],
    famcs: [],
    refns: [{ string: 'REFN', tail: '12345' } as ApiAttribute],
    changes: [{ string: 'CHAN', attributes: [{ string: '1 JAN 2000' } as ApiAttribute] } as ApiAttribute],
    images: []
  } as ApiPerson;

  beforeEach(() => {
    TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [
        PersonComponent,
        MockMainLayoutComponent,
        MockAttributeListComponent,
        MockMultimediaGalleryComponent,
        MockPersonFamilyListComponent,
        MockPersonParentFamiliesComponent
    ],
    providers: [
      provideRouter([]),
      provideHttpClient(),
      provideHttpClientTesting(),
      PersonService,
      { provide: DatasetsService, useValue: { get: () => of(['test-db']) } },
      { provide: SaveService, useValue: { getTextFile: (dataset: string) => of('GEDCOM content') } },
      { provide: UploadService, useValue: { uploadGedFile: (file: File) => of({ success: true }) } },
      { provide: UserService, useValue: { currentUser: null } },
      { provide: AuthService, useValue: { isLoggedIn: () => false, login: () => {}, logout: () => {} } },
      { provide: AuthApiService, useValue: { request: () => {} } },
      { provide: ConfigService, useValue: { apiUrl: 'http://localhost' } },
      { provide: MapKeyService, useValue: { getMapKey: () => of('PLUGH') } },
      {
        provide: ActivatedRoute,
        useValue: {
          params: of({ dataset: 'testDataset' }),
          data: of({ dataset: 'testDataset', person: mockPerson })
        }
      }
    ]
})
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('calls lifespanDateString', () => {
    component.person = mockPerson;
    const result = component.lifespanDateString();
    expect(typeof result).toBe('string');
  });

  it('save calls service.put', () => {
    component.person = mockPerson;
    component.dataset = 'ds';
    const spy = vi.spyOn(TestBed.inject(PersonService), 'put').mockReturnValue(of(mockPerson));
    const getOneSpy = vi.spyOn(TestBed.inject(PersonService), 'getOne').mockReturnValue(of(mockPerson));
    component.save();
    expect(spy).toHaveBeenCalledWith('ds', mockPerson);
    expect(getOneSpy).toHaveBeenCalledWith('ds', mockPerson.string);
  });

  it('save falls back to the put response when no person id is available', () => {
    const personWithoutId = { attributes: [] } as ApiPerson;
    const putResponse = { attributes: [] } as ApiPerson;
    component.person = personWithoutId;
    component.dataset = 'ds';

    const putSpy = vi.spyOn(TestBed.inject(PersonService), 'put').mockReturnValue(of(putResponse));
    const getOneSpy = vi.spyOn(TestBed.inject(PersonService), 'getOne').mockReturnValue(of(mockPerson));

    component.save();

    expect(putSpy).toHaveBeenCalledWith('ds', personWithoutId);
    expect(getOneSpy).not.toHaveBeenCalled();
    expect(component.person).toBe(putResponse);
  });

  it('options returns array of SelectItem', () => {
    const opts = component.options();
    expect(Array.isArray(opts)).toBe(true);
    expect(opts.length).toBeGreaterThan(0);
  });

  it('defaultData returns AttributeDialogData', () => {
    const data = component.defaultData();
    expect(data).toBeDefined();
    expect(data.type).toBe('Name');
  });

  it('ngOnInit falls back to empty attributes when none provided', async () => {
    TestBed.resetTestingModule();
    await TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [PersonComponent,
      MockMainLayoutComponent,
      MockAttributeListComponent,
      MockMultimediaGalleryComponent,
      MockPersonFamilyListComponent,
      MockPersonParentFamiliesComponent],
    providers: [
      provideRouter([]),
      provideHttpClient(),
      provideHttpClientTesting(),
      PersonService,
      { provide: DatasetsService, useValue: { get: () => of(['test-db']) } },
      { provide: SaveService, useValue: { getTextFile: (dataset: string) => of('GEDCOM content') } },
      { provide: UploadService, useValue: { uploadGedFile: (file: File) => of({ success: true }) } },
      { provide: UserService, useValue: { currentUser: null } },
      { provide: AuthService, useValue: { isLoggedIn: () => false, login: () => {}, logout: () => {} } },
      { provide: AuthApiService, useValue: { request: () => {} } },
      { provide: ConfigService, useValue: { apiUrl: 'http://localhost' } },
      { provide: MapKeyService, useValue: { getMapKey: () => of('PLUGH') } },
      {
        provide: ActivatedRoute,
        useValue: {
          params: of({ dataset: 'testDataset' }),
          data: of({
            dataset: 'testDataset',
            person: {
              attributes: undefined,
              lifespan: {},
                        refns: [{ string: '', tail: '' }],
                        changes: [{ string: '', attributes: [{ string: '' }] }],
                        famss: [],
                        famcs: [],
                        images: []
                    }
                })
            }
        }
    ]
}).compileComponents();

    const fixture2 = TestBed.createComponent(PersonComponent);
    const component2 = fixture2.componentInstance;
    fixture2.detectChanges();
    expect(component2.attributes).toEqual([]);
  });

  it('reads map key on init', () => {
    expect(component.googleMapsApiKey).toBe('PLUGH');
  });

  it('initializes an empty map key when the service returns no key', async () => {
    TestBed.resetTestingModule();
    await TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      imports: [
        PersonComponent,
        MockMainLayoutComponent,
        MockAttributeListComponent,
        MockMultimediaGalleryComponent,
        MockPersonFamilyListComponent,
        MockPersonParentFamiliesComponent
      ],
      providers: [
        provideRouter([]),
        provideHttpClient(),
        provideHttpClientTesting(),
        PersonService,
        { provide: DatasetsService, useValue: { get: () => of(['test-db']) } },
        { provide: SaveService, useValue: { getTextFile: (dataset: string) => of('GEDCOM content') } },
        { provide: UploadService, useValue: { uploadGedFile: (file: File) => of({ success: true }) } },
        { provide: UserService, useValue: { currentUser: null } },
        { provide: AuthService, useValue: { isLoggedIn: () => false, login: () => {}, logout: () => {} } },
        { provide: AuthApiService, useValue: { request: () => {} } },
        { provide: ConfigService, useValue: { apiUrl: 'http://localhost' } },
        { provide: MapKeyService, useValue: { getMapKey: () => of('') } },
        {
          provide: ActivatedRoute,
          useValue: {
            params: of({ dataset: 'testDataset' }),
            data: of({ dataset: 'testDataset', person: mockPerson })
          }
        }
      ]
    }).compileComponents();

    const fixture2 = TestBed.createComponent(PersonComponent);
    const component2 = fixture2.componentInstance;
    fixture2.detectChanges();

    expect(component2.googleMapsApiKey).toBe('');
  });

  it('normalizes a single place object into mapPlaces', () => {
    const singlePlace = { placeName: 'Needham', location: { coordinates: [-71.2377548, 42.2809285] } };
    const normalized = (component as any).normalizePlaces(singlePlace);
    expect(normalized).toEqual([singlePlace]);
  });

  it('returns the same array for an array place payload', () => {
    const places = [
      { placeName: 'A', location: { coordinates: [1, 2] } },
      { placeName: 'B', southwest: [3, 4] }
    ];

    const normalized = (component as any).normalizePlaces(places);

    expect(normalized).toBe(places);
  });

  it('normalizes object map payload and filters non-place values', () => {
    const payload = {
      one: { placeName: 'A', southwest: [1, 2] },
      two: { nope: true },
      three: { placeName: 'B', northeast: [3, 4] }
    };
    const normalized = (component as any).normalizePlaces(payload);
    expect(normalized.length).toBe(2);
    expect(normalized[0].placeName).toBe('A');
    expect(normalized[1].placeName).toBe('B');
  });

  it('returns an empty array for object payloads without place data', () => {
    const payload = {
      one: { nope: true },
      two: { alsoNope: true }
    };

    const normalized = (component as any).normalizePlaces(payload);

    expect(normalized).toEqual([]);
  });

  it('returns an empty array for primitive place payloads', () => {
    const normalized = (component as any).normalizePlaces('not-a-place');

    expect(normalized).toEqual([]);
  });

  it('returns empty list for null place payload', () => {
    const normalized = (component as any).normalizePlaces(null);
    expect(normalized).toEqual([]);
  });

  it('hasMapPlaces reflects mapPlaces length', () => {
    component.mapPlaces = [];
    expect(component.hasMapPlaces()).toBe(false);
    component.mapPlaces = [{ placeName: 'Needham', location: [0, 0] }];
    expect(component.hasMapPlaces()).toBe(true);
  });

  it('canRenderMap requires both map places and a map key', () => {
    component.mapPlaces = [];
    component.googleMapsApiKey = 'PLUGH';
    expect(component.canRenderMap()).toBe(false);

    component.mapPlaces = [{ placeName: 'Needham', location: [0, 0] }];
    component.googleMapsApiKey = '';
    expect(component.canRenderMap()).toBe(false);

    component.googleMapsApiKey = 'PLUGH';
    expect(component.canRenderMap()).toBe(true);
  });
});
