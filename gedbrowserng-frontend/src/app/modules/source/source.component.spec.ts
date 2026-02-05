import { NO_ERRORS_SCHEMA, Component, Input } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { provideNoopAnimations } from '@angular/platform-browser/animations';
import { ActivatedRoute, provideRouter } from '@angular/router';
import { of, ReplaySubject } from 'rxjs';
import { vi } from 'vitest';

import { SourceComponent } from './source.component';
import { SourceService, DatasetsService, SaveService, UploadService, UserService, AuthService, AuthApiService, ConfigService } from '../../services';
import { ApiSource, ApiAttribute } from '../../models';

// Mock component to replace the child app-main-layout
@Component({
    selector: 'app-main-layout',
    template: '<ng-content></ng-content>',
    imports: [MatButtonModule,
      MatSelectModule,
      MatFormFieldModule,
      MatInputModule,
      ReactiveFormsModule,
      FormsModule]
})
class MockMainLayoutComponent {
  @Input() dataset: string;
}

// Mock attribute-list component
@Component({
    selector: 'app-attribute-list',
    template: '',
    imports: [MatButtonModule,
      MatSelectModule,
      MatFormFieldModule,
      MatInputModule,
      ReactiveFormsModule,
      FormsModule]
})
class MockAttributeListComponent {
  @Input() dataset: string;
  @Input() parent: any;
  @Input() attributes: any[];
  @Input() showSources: boolean;
  @Input() showSubmitters: boolean;
}

// Mock multimedia-gallery component
@Component({
    selector: 'app-multimedia-gallery',
    template: '',
    imports: [MatButtonModule,
      MatSelectModule,
      MatFormFieldModule,
      MatInputModule,
      ReactiveFormsModule,
      FormsModule]
})
class MockMultimediaGalleryComponent {
  @Input() dataset: string;
  @Input() parent: any;
  @Input() multimedia: any[];
}

describe('SourceComponent', () => {
  let component: SourceComponent;
  let fixture: ComponentFixture<SourceComponent>;
  let sourceService: SourceService;
  let paramsSubject: ReplaySubject<any>;
  let dataSubject: ReplaySubject<any>;

  const mockAttributes: ApiAttribute[] = [
    { type: 'Title', value: 'Test Title' } as ApiAttribute,
    { type: 'Author', value: 'Test Author' } as ApiAttribute
  ];

  const mockSource: ApiSource = {
    title: 'Test Source',
    string: 'S456',
    type: 'source',
    attributes: mockAttributes,
    images: []
  } as ApiSource;

  beforeEach(() => {
    paramsSubject = new ReplaySubject(1);
    dataSubject = new ReplaySubject(1);

    TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [
        MatButtonModule,
        MatSelectModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
        FormsModule,
        SourceComponent,
        MockMainLayoutComponent,
        MockAttributeListComponent,
        MockMultimediaGalleryComponent
    ],
    providers: [
      provideRouter([]),
      provideHttpClient(),
      provideHttpClientTesting(),
      provideNoopAnimations(),
      SourceService,
      { provide: DatasetsService, useValue: { get: () => of(['test-db']) } },
      { provide: SaveService, useValue: { getTextFile: (dataset: string) => of('GEDCOM content') } },
      { provide: UploadService, useValue: { uploadGedFile: (file: File) => of({ success: true }) } },
      { provide: UserService, useValue: { currentUser: null } },
      { provide: AuthService, useValue: { isLoggedIn: () => false, login: () => {}, logout: () => {} } },
      { provide: AuthApiService, useValue: { request: () => {} } },
      { provide: ConfigService, useValue: { apiUrl: 'http://localhost' } },
      {
        provide: ActivatedRoute,
        useValue: {
          params: paramsSubject.asObservable(),
          data: dataSubject.asObservable()
        }
      }
    ]
})
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SourceComponent);
    component = fixture.componentInstance;
    sourceService = TestBed.inject(SourceService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize on ngOnInit', () => {
    paramsSubject.next({ dataset: 'testDataset' });
    dataSubject.next({ dataset: 'testDataset', source: mockSource });

    component.ngOnInit();

    expect(component.dataset).toBe('testDataset');
  });

  it('should set dataset from route params', () => {
    paramsSubject.next({ dataset: 'myDataset' });
    dataSubject.next({ dataset: 'myDataset', source: mockSource });

    component.ngOnInit();

    expect(component.dataset).toBe('myDataset');
  });

  it('should load source from route data', () => {
    paramsSubject.next({ dataset: 'testDataset' });
    dataSubject.next({ dataset: 'testDataset', source: mockSource });

    component.ngOnInit();

    expect(component.source).toEqual(mockSource);
  });

  it('should set attributes from source', () => {
    paramsSubject.next({ dataset: 'testDataset' });
    dataSubject.next({ dataset: 'testDataset', source: mockSource });

    component.ngOnInit();

    expect(component.attributes).toEqual(mockAttributes);
  });

  it('should call save() and update source', () => {
    component.dataset = 'testDataset';
    component.source = mockSource;

    const updatedSource = { ...mockSource, title: 'Updated Title' } as ApiSource;
    vi.spyOn(sourceService, 'put').mockReturnValue(of(updatedSource));

    component.save();

    expect(sourceService.put).toHaveBeenCalledWith('testDataset', mockSource);
    expect(component.source).toEqual(updatedSource);
  });

  it('should return _options from options() method', () => {
    const options = component.options();
    expect(options).toBeDefined();
    expect(Array.isArray(options)).toBe(true);
    expect(options.length).toBeGreaterThan(0);
    
    // Verify some expected options exist
    const hasAbbreviation = options.some((opt: any) => opt.value === 'Abbreviation');
    const hasAddress = options.some((opt: any) => opt.value === 'Address');
    const hasTitle = options.some((opt: any) => opt.value === 'Title');
    
    expect(hasAbbreviation).toBe(true);
    expect(hasAddress).toBe(true);
    expect(hasTitle).toBe(true);
  });

  it('should have extensive options list', () => {
    const options = component.options();
    expect(options.length).toBeGreaterThan(100);
  });

  it('should return default data from defaultData() method', () => {
    const defaultData = component.defaultData();
    expect(defaultData).toBeDefined();
    // The defaultData method returns an object with the creator's default data structure
    expect(typeof defaultData).toBe('object');
  });

  it('should handle source with empty attributes', () => {
    const sourceWithoutAttributes = { ...mockSource, attributes: [] } as ApiSource;
    
    paramsSubject.next({ dataset: 'testDataset' });
    dataSubject.next({ dataset: 'testDataset', source: sourceWithoutAttributes });

    component.ngOnInit();

    expect(component.attributes).toEqual([]);
  });

  it('should handle source with images', () => {
    const sourceWithImages = { 
      ...mockSource, 
      images: [
        { title: 'Image 1', filename: 'img1.jpg' },
        { title: 'Image 2', filename: 'img2.jpg' }
      ]
    } as ApiSource;
    
    paramsSubject.next({ dataset: 'testDataset' });
    dataSubject.next({ dataset: 'testDataset', source: sourceWithImages });

    component.ngOnInit();

    expect(component.source.images).toEqual(sourceWithImages.images);
  });

  it('should have all option values as strings', () => {
    const options = component.options();
    options.forEach((opt: any) => {
      expect(typeof opt.value).toBe('string');
      expect(typeof opt.label).toBe('string');
    });
  });

  it('should include Birth option', () => {
    const options = component.options();
    const birthOption = options.find((opt: any) => opt.value === 'Birth');
    expect(birthOption).toBeDefined();
    expect(birthOption.label).toBe('Birth');
  });

  it('should include Death option', () => {
    const options = component.options();
    const deathOption = options.find((opt: any) => opt.value === 'Death');
    expect(deathOption).toBeDefined();
    expect(deathOption.label).toBe('Death');
  });

  it('should include Marriage option', () => {
    const options = component.options();
    const marriageOption = options.find((opt: any) => opt.value === 'Marriage');
    expect(marriageOption).toBeDefined();
    expect(marriageOption.label).toBe('Marriage');
  });
});
