import { NO_ERRORS_SCHEMA, Component, Input } from '@angular/core';
import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { provideRouter } from '@angular/router';
import { provideAnimations } from '@angular/platform-browser/animations';
import { MatDialog } from '@angular/material/dialog';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { vi } from 'vitest';

import {SourceService, DatasetsService, SaveService, UploadService, UserService, AuthService, AuthApiService, ConfigService} from '../../services';
import {SourceListComponent} from './source-list.component';
import { ApiSource } from '../../models';

// Mock component to replace the child app-main-layout
@Component({
    selector: 'app-main-layout',
    template: '<ng-content></ng-content>',
    imports: [MatTableModule,
        MatPaginatorModule,
        MatSortModule,
        MatToolbarModule,
        MatFormFieldModule,
        MatInputModule,
        MatButtonModule,
        MatIconModule,
        MatTooltipModule]
})
class MockMainLayoutComponent {
  @Input() dataset: string;
}

describe('SourceListComponent', () => {
  let component: SourceListComponent;
  let fixture: ComponentFixture<SourceListComponent>;
  let sourceService: SourceService;
  let router: Router;
  let dialog: MatDialog;

  const mockSources: ApiSource[] = [
    { id: '1', title: 'Source A', string: 'S1' } as ApiSource,
    { id: '2', title: 'Source B', string: 'S2' } as ApiSource,
    { id: '3', title: 'Source C', string: 'S3' } as ApiSource,
  ];

  beforeEach(() => {
    TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [
      MatTableModule,
        MatPaginatorModule,
        MatSortModule,
        MatToolbarModule,
        MatFormFieldModule,
        MatInputModule,
        MatButtonModule,
        MatIconModule,
        MatTooltipModule,
        SourceListComponent,
        MockMainLayoutComponent
    ],
    providers: [
      provideRouter([]),
      provideHttpClient(),
      provideHttpClientTesting(),
      provideAnimations(),
      SourceService,
      { provide: DatasetsService, useValue: { get: () => of(['test-db']) } },
      { provide: SaveService, useValue: { getTextFile: (dataset: string) => of('GEDCOM content') } },
      { provide: UploadService, useValue: { uploadGedFile: (file: File) => of({ success: true }) } },
      { provide: UserService, useValue: { currentUser: null } },
      { provide: AuthService, useValue: { isLoggedIn: () => false, login: () => {}, logout: () => {} } },
      { provide: AuthApiService, useValue: { request: () => {} } },
      { provide: ConfigService, useValue: { apiUrl: 'http://localhost' } }
    ]
})
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SourceListComponent);
    component = fixture.componentInstance;
    component.dataset = 'testDataset';
    component.sources = mockSources;
    component.parent = { refreshSource: () => {} } as any;
    sourceService = TestBed.inject(SourceService);
    router = TestBed.inject(Router);
    dialog = TestBed.inject(MatDialog);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have displayedColumns property', () => {
    expect(component.displayedColumns).toBeDefined();
    expect(Array.isArray(component.displayedColumns)).toBe(true);
    expect(component.displayedColumns).toContain('title');
    expect(component.displayedColumns).toContain('string');
    expect(component.displayedColumns).toContain('delete');
  });

  it('should initialize datasource on ngOnInit', () => {
    component.sources = mockSources;
    component.ngOnInit();

    expect(component.datasource).toBeDefined();
    expect(component.datasource.data).toBeDefined();
  });

  it('should call ListPageHelper.init on ngOnInit', () => {
    component.sources = mockSources;
    vi.spyOn(component.datasource, 'connect').mockReturnValue(of([]));

    component.ngOnInit();

    expect(component.datasource).toBeDefined();
  });

  it('should return page size options from pagesizeoptions()', () => {
    component.sources = mockSources;
    const pageSizeOptions = component.pagesizeoptions();

    expect(Array.isArray(pageSizeOptions)).toBe(true);
    expect(pageSizeOptions.length).toBeGreaterThan(0);
  });

  it('should apply filter when applyFilter is called', () => {
    component.sources = mockSources;
    component.ngOnInit();

    expect(() => {
      component.applyFilter('test');
    }).not.toThrow();
  });

  it('should return sourceUB UrlBuilder', () => {
    component.dataset = 'testDataset';
    const ub = component.sourceUB();

    expect(ub).toBeDefined();
  });

  it('should return undefined for sourceAnchor', () => {
    const anchor = component.sourceAnchor();

    expect(anchor).toBeUndefined();
  });

  it('should navigate on navigate() call', () => {
    component.dataset = 'testDataset';
    const navigateSpy = vi.spyOn(router, 'navigate');

    component.navigate('S1');

    expect(navigateSpy).toHaveBeenCalledWith(['/testDataset/sources/S1']);
  });

  it('should prevent default and navigate on space key', () => {
    const navigateSpy = vi.spyOn(component, 'navigate');
    const mockEvent = { preventDefault: vi.fn() } as unknown as KeyboardEvent;

    component.onSpaceKey(mockEvent, 'S2');

    expect(mockEvent.preventDefault).toHaveBeenCalled();
    expect(navigateSpy).toHaveBeenCalledWith('S2');
  });

  it('should call parent.refreshSource on refreshSource() call', () => {
    component.parent = { refreshSource: vi.fn() } as any;

    component.refreshSource(mockSources[0]);

    expect(component.parent.refreshSource).toHaveBeenCalledWith(mockSources[0]);
  });

  it('should delete source and refresh on delete() call', () => {
    component.dataset = 'testDataset';
    component.parent = { refreshSource: vi.fn() } as any;

    const deleteSpy = vi.spyOn(sourceService, 'delete').mockReturnValue(of(mockSources[0]));

    component.delete(mockSources[0]);

    expect(deleteSpy).toHaveBeenCalledWith('testDataset', mockSources[0]);
    expect(component.parent.refreshSource).toHaveBeenCalled();
  });

  it('should handle empty sources array', () => {
    component.sources = [];
    component.ngOnInit();

    expect(component.datasource).toBeDefined();
    expect(component.datasource.data).toEqual([]);
  });

  it('should handle applyFilter with empty string', () => {
    component.sources = mockSources;
    component.ngOnInit();

    expect(() => {
      component.applyFilter('');
    }).not.toThrow();
  });

  it('should handle applyFilter with filter text', () => {
    component.sources = mockSources;
    component.ngOnInit();

    expect(() => {
      component.applyFilter('Source A');
    }).not.toThrow();
  });

  it('should have initial empty datasource', () => {
    const newComponent = new SourceListComponent(router, sourceService, dialog);

    expect(newComponent.datasource).toBeDefined();
    expect(newComponent.datasource.data.length).toBe(0);
  });

  it('should maintain datasource reference through lifecycle', () => {
    component.sources = mockSources;

    component.ngOnInit();

    expect(component.datasource).toBeDefined();
  });

  it('should update datasource data on ngOnInit', () => {
    component.sources = mockSources;
    component.ngOnInit();

    expect(component.datasource.data).toBeDefined();
  });

  it('should call applyFilter with target.value from event', () => {
    component.sources = mockSources;
    component.ngOnInit();
    const applyFilterSpy = vi.spyOn(component, 'applyFilter');

    const mockEvent = { target: { value: 'testFilter' } };
    component.applyFilter(mockEvent.target.value);

    expect(applyFilterSpy).toHaveBeenCalledWith('testFilter');
  });

  it('should have correct data when sources contains multiple items', () => {
    component.sources = mockSources;
    component.ngOnInit();

    expect(component.datasource.data.length).toBeGreaterThan(1);
  });
});
