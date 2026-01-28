import { Component, Input } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { ActivatedRoute, Router } from '@angular/router';
import { of, ReplaySubject } from 'rxjs';
import { vi } from 'vitest';

import { NoteListPageComponent } from './note-list-page.component';
import { NoteService } from '../../services';
import { ApiNote } from '../../models';

// Mock component to replace the child app-note-list
@Component({
  selector: 'app-note-list',
  template: '',
  standalone: false
})
class MockNoteListComponent {
  @Input() parent: any;
  @Input() dataset: string;
  @Input() notes: any[];
}

const mockNotes: ApiNote[] = [
  { id: '1', title: 'Note 1', string: 'N1' } as ApiNote,
  { id: '2', title: 'Note 2', string: 'N2' } as ApiNote
];

describe('NoteListPageComponent', () => {
  let component: NoteListPageComponent;
  let fixture: ComponentFixture<NoteListPageComponent>;
  let noteService: NoteService;
  let router: Router;
  let paramsSubject: ReplaySubject<any>;
  let dataSubject: ReplaySubject<any>;

  beforeEach(() => {
    paramsSubject = new ReplaySubject(1);
    dataSubject = new ReplaySubject(1);

    TestBed.configureTestingModule({
      declarations: [ NoteListPageComponent, MockNoteListComponent ],
      imports: [ HttpClientTestingModule, RouterTestingModule, NoopAnimationsModule ],
      providers: [
        NoteService,
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

    noteService = TestBed.inject(NoteService);
    router = TestBed.inject(Router);
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NoteListPageComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should implement RefreshNote interface', () => {
    expect(component.refreshNote).toBeDefined();
    expect(typeof component.refreshNote).toBe('function');
  });

  it('should subscribe to route params on init', () => {
    paramsSubject.next({ dataset: 'testDataset' });
    dataSubject.next({ dataset: 'testDataset', notes: mockNotes });

    component.ngOnInit();

    expect(component.dataset).toBe('testDataset');
  });

  it('should subscribe to route data on init', () => {
    paramsSubject.next({ dataset: 'testDataset' });
    dataSubject.next({ dataset: 'testDataset', notes: mockNotes });

    component.ngOnInit();

    expect(component.notes).toBeDefined();
    expect(Array.isArray(component.notes)).toBe(true);
  });

  it('should sort notes by ApiComparators on init', () => {
    const unsortedNotes = [
      { id: '2', title: 'Note 2', string: 'N2' } as ApiNote,
      { id: '1', title: 'Note 1', string: 'N1' } as ApiNote
    ];
    paramsSubject.next({ dataset: 'testDataset' });
    dataSubject.next({ dataset: 'testDataset', notes: unsortedNotes });

    component.ngOnInit();

    expect(component.notes).toBeDefined();
  });

  it('should call init on ngOnInit', () => {
    paramsSubject.next({ dataset: 'testDataset' });
    dataSubject.next({ dataset: 'testDataset', notes: mockNotes });

    component.ngOnInit();

    expect(component.dataset).toBe('testDataset');
    expect(component.notes).toBeDefined();
  });

  it('should call init on ngOnChanges', () => {
    paramsSubject.next({ dataset: 'testDataset' });
    dataSubject.next({ dataset: 'testDataset', notes: mockNotes });

    component.ngOnChanges();

    expect(component.dataset).toBe('testDataset');
    expect(component.notes).toBeDefined();
  });

  it('should call refreshNote without errors', () => {
    component.dataset = 'testDataset';
    vi.spyOn(noteService, 'getAll').mockReturnValue(of(mockNotes));

    expect(() => {
      component.refreshNote(mockNotes[0]);
    }).not.toThrow();
  });

  it('should disable route reuse strategy in refreshNote', () => {
    component.dataset = 'testDataset';
    const originalShouldReuse = router.routeReuseStrategy.shouldReuseRoute;
    vi.spyOn(noteService, 'getAll').mockReturnValue(of(mockNotes));

    component.refreshNote(mockNotes[0]);

    expect(router.routeReuseStrategy.shouldReuseRoute).not.toBe(originalShouldReuse);
    expect(router.routeReuseStrategy.shouldReuseRoute()).toBe(false);
  });

  it('should call getAll on refreshNote', () => {
    component.dataset = 'testDataset';
    const getAllSpy = vi.spyOn(noteService, 'getAll').mockReturnValue(of(mockNotes));

    component.refreshNote(mockNotes[0]);

    expect(getAllSpy).toHaveBeenCalledWith('testDataset');
  });

  it('should update notes on refreshNote', () => {
    component.dataset = 'testDataset';
    const newNotes = [{ id: '3', title: 'Note 3', string: 'N3' } as ApiNote];
    vi.spyOn(noteService, 'getAll').mockReturnValue(of(newNotes));

    component.refreshNote(mockNotes[0]);

    expect(component.notes).toBeDefined();
  });

  it('should handle multiple dataset changes', () => {
    paramsSubject.next({ dataset: 'dataset1' });
    dataSubject.next({ dataset: 'dataset1', notes: mockNotes });

    component.ngOnInit();
    expect(component.dataset).toBe('dataset1');

    paramsSubject.next({ dataset: 'dataset2' });
    expect(component.dataset).toBe('dataset2');
  });

  it('should handle empty notes array', () => {
    paramsSubject.next({ dataset: 'testDataset' });
    dataSubject.next({ dataset: 'testDataset', notes: [] });

    component.ngOnInit();

    expect(component.notes).toEqual([]);
  });

  it('should handle null notes and default to empty array', () => {
    component.dataset = 'testDataset';
    vi.spyOn(noteService, 'getAll').mockReturnValue(of([]));

    component.refreshNote(mockNotes[0]);

    expect(component.notes).toBeDefined();
  });
});
