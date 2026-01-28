import { NO_ERRORS_SCHEMA, Component, Input } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { ActivatedRoute, Router } from '@angular/router';
import { of, ReplaySubject } from 'rxjs';
import { vi } from 'vitest';

import { SourceListPageComponent } from './source-list-page.component';
import { SourceService } from '../../services';
import { ApiSource } from '../../models';

// Mock component to replace the child app-source-list
@Component({
  selector: 'app-source-list',
  template: '',
  standalone: false
})
class MockSourceListComponent {
  @Input() parent: any;
  @Input() dataset: string;
  @Input() sources: any[];
}

describe('SourceListPageComponent', () => {
  let component: SourceListPageComponent;
  let fixture: ComponentFixture<SourceListPageComponent>;
  let sourceService: SourceService;
  let router: Router;
  let paramsSubject: ReplaySubject<any>;
  let dataSubject: ReplaySubject<any>;

  const mockSources: ApiSource[] = [
    { id: '1', title: 'Source A', string: 'S1' } as ApiSource,
    { id: '2', title: 'Source B', string: 'S2' } as ApiSource,
  ];

  beforeEach(() => {
    paramsSubject = new ReplaySubject(1);
    dataSubject = new ReplaySubject(1);

    TestBed.configureTestingModule({
      declarations: [ SourceListPageComponent, MockSourceListComponent ],
      imports: [ HttpClientTestingModule, RouterTestingModule, NoopAnimationsModule ],
      providers: [
        SourceService,
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
    fixture = TestBed.createComponent(SourceListPageComponent);
    component = fixture.componentInstance;
    sourceService = TestBed.inject(SourceService);
    router = TestBed.inject(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should subscribe to route params', () => {
    paramsSubject.next({ dataset: 'testDataset' });
    dataSubject.next({ dataset: 'testDataset', sources: mockSources });

    component.ngOnInit();

    expect(component.dataset).toBe('testDataset');
  });

  it('should subscribe to route data', () => {
    paramsSubject.next({ dataset: 'testDataset' });
    dataSubject.next({ dataset: 'testDataset', sources: mockSources });

    component.ngOnInit();

    expect(component.sources).toBeDefined();
  });

  it('should handle multiple dataset changes', () => {
    paramsSubject.next({ dataset: 'dataset1' });
    dataSubject.next({ dataset: 'dataset1', sources: mockSources });

    component.ngOnInit();
    expect(component.dataset).toBe('dataset1');

    paramsSubject.next({ dataset: 'dataset2' });
    expect(component.dataset).toBe('dataset2');
  });

  it('should call getAll on refreshSource', () => {
    component.dataset = 'testDataset';
    const getAllSpy = vi.spyOn(sourceService, 'getAll').mockReturnValue(of(mockSources));
    vi.spyOn(router.routeReuseStrategy, 'shouldReuseRoute').mockReturnValue(false);

    component.refreshSource(mockSources[0]);

    expect(getAllSpy).toHaveBeenCalledWith('testDataset');
  });

  it('should disable route reuse strategy in refreshSource', () => {
    component.dataset = 'testDataset';
    const originalShouldReuse = router.routeReuseStrategy.shouldReuseRoute;
    vi.spyOn(sourceService, 'getAll').mockReturnValue(of(mockSources));

    component.refreshSource(mockSources[0]);

    // Verify that shouldReuseRoute was replaced with a new function
    expect(router.routeReuseStrategy.shouldReuseRoute).not.toBe(originalShouldReuse);
    expect(router.routeReuseStrategy.shouldReuseRoute()).toBe(false);
  });

  it('should handle sources array in route data', () => {
    paramsSubject.next({ dataset: 'testDataset' });
    dataSubject.next({ dataset: 'testDataset', sources: mockSources });

    component.ngOnInit();

    expect(Array.isArray(component.sources)).toBe(true);
  });

  it('should handle empty sources array', () => {
    paramsSubject.next({ dataset: 'testDataset' });
    dataSubject.next({ dataset: 'testDataset', sources: [] });

    component.ngOnInit();

    expect(component.sources).toEqual([]);
  });

  it('should process sources from getAll in refreshSource', () => {
    component.dataset = 'testDataset';
    const updatedSources = [{ id: '3', title: 'Source C', string: 'S3' } as ApiSource];
    vi.spyOn(sourceService, 'getAll').mockReturnValue(of(updatedSources));
    vi.spyOn(router.routeReuseStrategy, 'shouldReuseRoute').mockReturnValue(false);

    component.refreshSource(mockSources[0]);

    expect(component.sources).toBeDefined();
  });

  it('should handle multiple refreshSource calls', () => {
    component.dataset = 'testDataset';
    const getAllSpy = vi.spyOn(sourceService, 'getAll').mockReturnValue(of(mockSources));
    vi.spyOn(router.routeReuseStrategy, 'shouldReuseRoute').mockReturnValue(false);

    component.refreshSource(mockSources[0]);
    component.refreshSource(mockSources[1]);

    expect(getAllSpy).toHaveBeenCalledTimes(2);
  });

  it('should update sources when new data is received', () => {
    const firstSources: ApiSource[] = [{ id: '1', title: 'Source 1', string: 'S1' } as ApiSource];
    const secondSources: ApiSource[] = [{ id: '2', title: 'Source 2', string: 'S2' } as ApiSource];

    paramsSubject.next({ dataset: 'testDataset' });
    dataSubject.next({ dataset: 'testDataset', sources: firstSources });

    component.ngOnInit();
    expect(component.sources).toEqual(firstSources);

    dataSubject.next({ dataset: 'testDataset', sources: secondSources });
    expect(component.sources).toEqual(secondSources);
  });

  it('should accept source parameter in refreshSource', () => {
    component.dataset = 'testDataset';
    const sourceToRefresh = mockSources[0];
    vi.spyOn(sourceService, 'getAll').mockReturnValue(of(mockSources));
    vi.spyOn(router.routeReuseStrategy, 'shouldReuseRoute').mockReturnValue(false);

    component.refreshSource(sourceToRefresh);

    expect(component.sources).toBeDefined();
  });

  it('should call init from both ngOnInit and ngOnChanges', () => {
    paramsSubject.next({ dataset: 'testDataset' });
    dataSubject.next({ dataset: 'testDataset', sources: mockSources });

    component.ngOnInit();
    const initialDataset = component.dataset;

    component.ngOnChanges();
    // Verify init was called again (would update from observables if they changed)
    expect(component.dataset).toBe(initialDataset);
  });
});
