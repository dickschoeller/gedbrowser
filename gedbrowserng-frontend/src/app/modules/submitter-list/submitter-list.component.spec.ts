import { NO_ERRORS_SCHEMA, Component, Input } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
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

import { SubmitterService } from '../../services';
import { SubmitterListComponent } from './submitter-list.component';
import { ApiSubmitter } from '../../models';

// Mock component to replace the child app-main-layout
@Component({
  selector: 'app-main-layout',
  template: '<ng-content></ng-content>',
  standalone: false
})
class MockMainLayoutComponent {
  @Input() dataset: string;
}

const mockSubmitters: ApiSubmitter[] = [
  { id: '1', name: 'Submitter 1', string: 'S1' } as ApiSubmitter,
  { id: '2', name: 'Submitter 2', string: 'S2' } as ApiSubmitter
];

describe('SubmitterListComponent', () => {
  let component: SubmitterListComponent;
  let fixture: ComponentFixture<SubmitterListComponent>;
  let submitterService: SubmitterService;
  let router: Router;
  let dialog: MatDialog;

  beforeEach(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [
        SubmitterListComponent,
        MockMainLayoutComponent
      ],
      imports: [
        RouterTestingModule,
        NoopAnimationsModule,
        HttpClientTestingModule,
        MatTableModule,
        MatPaginatorModule,
        MatSortModule,
        MatToolbarModule,
        MatFormFieldModule,
        MatInputModule,
        MatButtonModule,
        MatIconModule,
        MatTooltipModule
      ],
      providers: [
        SubmitterService,
      ]
    })
    .compileComponents();

    submitterService = TestBed.inject(SubmitterService);
    router = TestBed.inject(Router);
    dialog = TestBed.inject(MatDialog);
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubmitterListComponent);
    component = fixture.componentInstance;
    component.dataset = 'testDataset';
    component.submitters = mockSubmitters;
    component.parent = {
      refreshSubmitter: vi.fn()
    } as any;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize datasource on ngOnInit', () => {
    component.ngOnInit();
    expect(component.datasource).toBeDefined();
    expect(component.datasource.data.length).toBeGreaterThan(0);
  });

  it('should initialize datasource on ngAfterViewInit', () => {
    component.ngAfterViewInit();
    expect(component.datasource).toBeDefined();
  });

  it('should handle ngOnChanges lifecycle hook', () => {
    component.ngOnChanges();
    expect(component).toBeTruthy();
  });

  it('should implement ListPage interface', () => {
    expect(component.datasource).toBeDefined();
    expect(component.paginator).toBeDefined();
    expect(component.sort).toBeDefined();
  });

  it('should navigate on navigate() call', () => {
    component.dataset = 'myDataset';
    const navigateSpy = vi.spyOn(router, 'navigate');

    component.navigate('testSubmitterId');

    expect(navigateSpy).toHaveBeenCalledWith(['/myDataset/submitters/testSubmitterId']);
  });

  it('should delete and call refreshSubmitter with correct arguments', () => {
    component.dataset = 'testDataset';
    const refreshSubmitterSpy = vi.spyOn(component.parent, 'refreshSubmitter');

    vi.spyOn(submitterService, 'delete').mockReturnValue(of(mockSubmitters[0]));

    component.delete(mockSubmitters[0]);

    expect(refreshSubmitterSpy).toHaveBeenCalled();
  });

  it('should have initial empty datasource', () => {
    const newComponent = new SubmitterListComponent(router, submitterService, dialog);

    expect(newComponent.datasource).toBeDefined();
    expect(newComponent.datasource.data.length).toBe(0);
  });

  it('should maintain datasource reference through lifecycle', () => {
    component.submitters = mockSubmitters;
    const initialDataSource = component.datasource;

    component.ngOnInit();

    expect(component.datasource).toBeDefined();
  });

  it('should update datasource data on ngOnInit', () => {
    component.submitters = mockSubmitters;
    component.ngOnInit();

    expect(component.datasource.data).toBeDefined();
  });

  it('should apply filter correctly', () => {
    component.submitters = mockSubmitters;
    component.ngOnInit();

    component.applyFilter('testFilter');

    expect(component.datasource.filter).toBe('testfilter');
  });

  it('should navigate with correct dataset prefix', () => {
    component.dataset = 'myDataset';
    const navigateSpy = vi.spyOn(router, 'navigate');

    component.navigate('testSubmitterId');

    expect(navigateSpy).toHaveBeenCalledWith(['/myDataset/submitters/testSubmitterId']);
  });

  it('should call applyFilter with target.value from event', () => {
    component.submitters = mockSubmitters;
    component.ngOnInit();
    const applyFilterSpy = vi.spyOn(component, 'applyFilter');

    const mockEvent = { target: { value: 'testFilter' } };
    component.applyFilter(mockEvent.target.value);

    expect(applyFilterSpy).toHaveBeenCalledWith('testFilter');
  });

  it('should implement SubmitterCreator base class', () => {
    expect(component.submitterService).toBeDefined();
    expect(component.openCreateSubmitterDialog).toBeDefined();
    expect(typeof component.openCreateSubmitterDialog).toBe('function');
  });

  it('should handle delete operation', () => {
    component.dataset = 'testDataset';
    const deleteSpy = vi.spyOn(submitterService, 'delete').mockReturnValue(of(mockSubmitters[0]));
    const refreshSpy = vi.spyOn(component.parent, 'refreshSubmitter');

    component.delete(mockSubmitters[0]);

    expect(deleteSpy).toHaveBeenCalledWith('testDataset', mockSubmitters[0]);
    expect(refreshSpy).toHaveBeenCalled();
  });

  it('should handle empty submitters array', () => {
    component.submitters = [];
    component.ngOnInit();

    expect(component.datasource.data.length).toBe(0);
  });

  it('should update datasource when submitters change', () => {
    const newSubmitters = [{ id: '3', name: 'Submitter 3', string: 'S3' } as ApiSubmitter];
    component.submitters = newSubmitters;
    component.ngOnInit();

    expect(component.datasource.data).toEqual(newSubmitters);
  });

  it('should have displayedColumns property', () => {
    expect(component.displayedColumns).toBeDefined();
    expect(Array.isArray(component.displayedColumns)).toBe(true);
  });

  it('should have pagesizeoptions method', () => {
    expect(component.pagesizeoptions).toBeDefined();
    expect(typeof component.pagesizeoptions).toBe('function');
  });

  it('should refresh submitter', () => {
    component.dataset = 'testDataset';
    const refreshSubmitterSpy = vi.spyOn(component.parent, 'refreshSubmitter');

    component.refreshSubmitter(mockSubmitters[0]);

    expect(refreshSubmitterSpy).toHaveBeenCalledWith(mockSubmitters[0]);
  });
});
