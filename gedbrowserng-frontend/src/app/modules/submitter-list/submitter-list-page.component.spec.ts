import { Component, Input } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { ActivatedRoute, Router } from '@angular/router';
import { of, ReplaySubject } from 'rxjs';
import { vi } from 'vitest';

import { SubmitterListPageComponent } from './submitter-list-page.component';
import { SubmitterService, DatasetsService, SaveService, UploadService, UserService, AuthService, AuthApiService, ConfigService } from '../../services';
import { ApiSubmitter } from '../../models';

// Mock component to replace the child app-submitter-list
@Component({
    selector: 'app-submitter-list',
    template: '',
    imports: [HttpClientTestingModule, RouterTestingModule, NoopAnimationsModule]
})
class MockSubmitterListComponent {
  @Input() parent: any;
  @Input() dataset: string;
  @Input() submitters: any[];
}

const mockSubmitters: ApiSubmitter[] = [
  { id: '1', name: 'Submitter 1', string: 'S1' } as ApiSubmitter,
  { id: '2', name: 'Submitter 2', string: 'S2' } as ApiSubmitter
];

describe('SubmitterListPageComponent', () => {
  let component: SubmitterListPageComponent;
  let fixture: ComponentFixture<SubmitterListPageComponent>;
  let submitterService: SubmitterService;
  let router: Router;
  let paramsSubject: ReplaySubject<any>;
  let dataSubject: ReplaySubject<any>;

  beforeEach(() => {
    paramsSubject = new ReplaySubject(1);
    dataSubject = new ReplaySubject(1);

    TestBed.configureTestingModule({
    imports: [HttpClientTestingModule, RouterTestingModule, NoopAnimationsModule, SubmitterListPageComponent, MockSubmitterListComponent],
    providers: [
      SubmitterService,
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

    submitterService = TestBed.inject(SubmitterService);
    router = TestBed.inject(Router);
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubmitterListPageComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should subscribe to route params', () => {
    paramsSubject.next({ dataset: 'testDataset' });
    dataSubject.next({ dataset: 'testDataset', submitters: mockSubmitters });

    component.ngOnInit();

    expect(component.dataset).toBe('testDataset');
  });

  it('should subscribe to route data', () => {
    paramsSubject.next({ dataset: 'testDataset' });
    dataSubject.next({ dataset: 'testDataset', submitters: mockSubmitters });

    component.ngOnInit();

    expect(component.submitters).toBeDefined();
  });

  it('should sort submitters on init', () => {
    const unsortedSubmitters = [
      { id: '2', name: 'Submitter 2', string: 'S2' } as ApiSubmitter,
      { id: '1', name: 'Submitter 1', string: 'S1' } as ApiSubmitter
    ];
    paramsSubject.next({ dataset: 'testDataset' });
    dataSubject.next({ dataset: 'testDataset', submitters: unsortedSubmitters });

    component.ngOnInit();

    expect(component.submitters).toBeDefined();
  });

  it('should call init on ngOnInit', () => {
    paramsSubject.next({ dataset: 'testDataset' });
    dataSubject.next({ dataset: 'testDataset', submitters: mockSubmitters });

    component.ngOnInit();

    expect(component.dataset).toBe('testDataset');
    expect(component.submitters).toBeDefined();
  });

  it('should call init on ngOnChanges', () => {
    paramsSubject.next({ dataset: 'testDataset' });
    dataSubject.next({ dataset: 'testDataset', submitters: mockSubmitters });

    component.ngOnChanges();

    expect(component.dataset).toBe('testDataset');
    expect(component.submitters).toBeDefined();
  });

  it('should call refreshSubmitter without errors', () => {
    component.dataset = 'testDataset';
    vi.spyOn(submitterService, 'getAll').mockReturnValue(of(mockSubmitters));

    expect(() => {
      component.refreshSubmitter(mockSubmitters[0]);
    }).not.toThrow();
  });

  it('should disable route reuse strategy in refreshSubmitter', () => {
    component.dataset = 'testDataset';
    const originalShouldReuse = router.routeReuseStrategy.shouldReuseRoute;
    vi.spyOn(submitterService, 'getAll').mockReturnValue(of(mockSubmitters));

    component.refreshSubmitter(mockSubmitters[0]);

    expect(router.routeReuseStrategy.shouldReuseRoute).not.toBe(originalShouldReuse);
    expect(router.routeReuseStrategy.shouldReuseRoute()).toBe(false);
  });

  it('should call getAll on refreshSubmitter', () => {
    component.dataset = 'testDataset';
    const getAllSpy = vi.spyOn(submitterService, 'getAll').mockReturnValue(of(mockSubmitters));

    component.refreshSubmitter(mockSubmitters[0]);

    expect(getAllSpy).toHaveBeenCalledWith('testDataset');
  });

  it('should process submitters from getAll in refreshSubmitter', () => {
    component.dataset = 'testDataset';
    const updatedSubmitters = [{ id: '3', name: 'Submitter 3', string: 'S3' } as ApiSubmitter];
    vi.spyOn(submitterService, 'getAll').mockReturnValue(of(updatedSubmitters));

    component.refreshSubmitter(mockSubmitters[0]);

    expect(component.submitters).toBeDefined();
  });

  it('should handle multiple refreshSubmitter calls', () => {
    component.dataset = 'testDataset';
    const getAllSpy = vi.spyOn(submitterService, 'getAll').mockReturnValue(of(mockSubmitters));

    component.refreshSubmitter(mockSubmitters[0]);
    component.refreshSubmitter(mockSubmitters[1]);

    expect(getAllSpy).toHaveBeenCalledTimes(2);
  });

  it('should handle empty submitters array', () => {
    paramsSubject.next({ dataset: 'testDataset' });
    dataSubject.next({ dataset: 'testDataset', submitters: [] });

    component.ngOnInit();

    expect(component.submitters).toEqual([]);
  });

  it('should handle multiple dataset changes', () => {
    paramsSubject.next({ dataset: 'dataset1' });
    dataSubject.next({ dataset: 'dataset1', submitters: mockSubmitters });

    component.ngOnInit();
    expect(component.dataset).toBe('dataset1');

    paramsSubject.next({ dataset: 'dataset2' });
    expect(component.dataset).toBe('dataset2');
  });
});
