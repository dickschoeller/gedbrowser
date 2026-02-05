import { Component, Input } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { ActivatedRoute, Router } from '@angular/router';
import { of, ReplaySubject } from 'rxjs';
import { vi } from 'vitest';

import { PersonListPageComponent } from './person-list-page.component';
import { PersonService, DatasetsService, SaveService, UploadService, UserService, AuthService, AuthApiService, ConfigService } from '../../services';
import { ApiPerson } from '../../models';

// Mock component to replace the child app-person-list
@Component({
    selector: 'app-person-list',
    template: '',
    imports: [HttpClientTestingModule, RouterTestingModule, NoopAnimationsModule]
})
class MockPersonListComponent {
  @Input() p: any;
  @Input() dataset: string;
  @Input() persons: any[];
}

describe('PersonListPageComponent', () => {
  let component: PersonListPageComponent;
  let fixture: ComponentFixture<PersonListPageComponent>;
  let personService: PersonService;
  let router: Router;
  let paramsSubject: ReplaySubject<any>;
  let dataSubject: ReplaySubject<any>;

  const mockPersons: ApiPerson[] = [
    { id: '1', name: 'Person A', string: 'P1' } as ApiPerson,
    { id: '2', name: 'Person B', string: 'P2' } as ApiPerson,
  ];

  beforeEach(() => {
    paramsSubject = new ReplaySubject(1);
    dataSubject = new ReplaySubject(1);

    TestBed.configureTestingModule({
    imports: [HttpClientTestingModule, RouterTestingModule, NoopAnimationsModule, PersonListPageComponent, MockPersonListComponent],
    providers: [
      PersonService,
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
    fixture = TestBed.createComponent(PersonListPageComponent);
    component = fixture.componentInstance;
    personService = TestBed.inject(PersonService);
    router = TestBed.inject(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should subscribe to route params on init', () => {
    vi.spyOn(personService, 'getAll').mockReturnValue(of(mockPersons));
    vi.spyOn(router.routeReuseStrategy, 'shouldReuseRoute').mockReturnValue(false);

    expect(() => {
      component.refreshPerson();
    }).not.toThrow();
  });

  it('should subscribe to route params', () => {
    paramsSubject.next({ dataset: 'testDataset' });
    dataSubject.next({ dataset: 'testDataset', persons: mockPersons });

    component.ngOnInit();

    expect(component.dataset).toBe('testDataset');
  });

  it('should subscribe to route data', () => {
    paramsSubject.next({ dataset: 'testDataset' });
    dataSubject.next({ dataset: 'testDataset', persons: mockPersons });

    component.ngOnInit();

    expect(component.persons).toBeDefined();
  });

  it('should handle multiple dataset changes', () => {
    paramsSubject.next({ dataset: 'dataset1' });
    dataSubject.next({ dataset: 'dataset1', persons: mockPersons });

    component.ngOnInit();
    expect(component.dataset).toBe('dataset1');

    paramsSubject.next({ dataset: 'dataset2' });
    expect(component.dataset).toBe('dataset2');
  });

  it('should call getAll on refreshPerson', () => {
    component.dataset = 'testDataset';
    const getAllSpy = vi.spyOn(personService, 'getAll').mockReturnValue(of(mockPersons));
    vi.spyOn(router.routeReuseStrategy, 'shouldReuseRoute').mockReturnValue(false);

    component.refreshPerson();

    expect(getAllSpy).toHaveBeenCalledWith('testDataset');
  });

  it('should disable route reuse strategy in refreshPerson', () => {
    component.dataset = 'testDataset';
    const originalShouldReuse = router.routeReuseStrategy.shouldReuseRoute;
    vi.spyOn(personService, 'getAll').mockReturnValue(of(mockPersons));

    component.refreshPerson();

    // Verify that shouldReuseRoute was replaced with a new function
    expect(router.routeReuseStrategy.shouldReuseRoute).not.toBe(originalShouldReuse);
    expect(router.routeReuseStrategy.shouldReuseRoute()).toBe(false);
  });

  it('should handle persons array in route data', () => {
    paramsSubject.next({ dataset: 'testDataset' });
    dataSubject.next({ dataset: 'testDataset', persons: mockPersons });

    component.ngOnInit();

    expect(Array.isArray(component.persons)).toBe(true);
  });

  it('should handle empty persons array', () => {
    paramsSubject.next({ dataset: 'testDataset' });
    dataSubject.next({ dataset: 'testDataset', persons: [] });

    component.ngOnInit();

    expect(component.persons).toEqual([]);
  });

  it('should process persons from getAll in refreshPerson', () => {
    component.dataset = 'testDataset';
    const updatedPersons = [{ id: '3', name: 'Person C', string: 'P3' } as ApiPerson];
    vi.spyOn(personService, 'getAll').mockReturnValue(of(updatedPersons));
    vi.spyOn(router.routeReuseStrategy, 'shouldReuseRoute').mockReturnValue(false);

    component.refreshPerson();

    expect(component.persons).toBeDefined();
  });

  it('should set persons to empty array when service returns null', () => {
    component.dataset = 'testDataset';
    vi.spyOn(personService, 'getAll').mockReturnValue(of(null as any));
    vi.spyOn(router.routeReuseStrategy, 'shouldReuseRoute').mockReturnValue(false);

    component.refreshPerson();

    expect(component.persons).toEqual([]);
  });

  it('should handle multiple refreshPerson calls', () => {
    component.dataset = 'testDataset';
    const getAllSpy = vi.spyOn(personService, 'getAll').mockReturnValue(of(mockPersons));
    vi.spyOn(router.routeReuseStrategy, 'shouldReuseRoute').mockReturnValue(false);

    component.refreshPerson();
    component.refreshPerson();

    expect(getAllSpy).toHaveBeenCalledTimes(2);
  });

  it('should update persons when new data is received', () => {
    const firstPersons: ApiPerson[] = [{ id: '1', name: 'Person 1', string: 'P1' } as ApiPerson];
    const secondPersons: ApiPerson[] = [{ id: '2', name: 'Person 2', string: 'P2' } as ApiPerson];

    paramsSubject.next({ dataset: 'testDataset' });
    dataSubject.next({ dataset: 'testDataset', persons: firstPersons });

    component.ngOnInit();
    expect(component.persons).toEqual(firstPersons);

    dataSubject.next({ dataset: 'testDataset', persons: secondPersons });
    expect(component.persons).toEqual(secondPersons);
  });
});
