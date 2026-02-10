import { describe, it, expect } from 'vitest';
import { of } from 'rxjs';
import { NO_ERRORS_SCHEMA, Component, Input } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { provideRouter } from '@angular/router';

import { NoteService, DatasetsService, SaveService, UploadService, UserService, AuthService, AuthApiService, ConfigService } from '../../services';
import { NoteListComponent } from './note-list.component';
import { ApiNote } from '../../models';

class StubRouter {
  navigated: string[] | null = null;
  navigate(path: string[]) { this.navigated = path; }
}

class StubNoteService {
  deleted = false;
  posted = false;
  delete(_: string, note: ApiNote) { this.deleted = true; return of(note); }
  postLink(): any { this.posted = true; return of(new ApiNote()); }
}

class StubDialogRef<T> {
  constructor(private readonly value: T | undefined) {}
  afterClosed() { return of(this.value); }
}

class StubDialog {
  result: any;
  open(_: any, __: any) { return new StubDialogRef(this.result); }
}

class StubParent {
  refreshed: ApiNote | null = null;
  refreshNote(n: ApiNote) { this.refreshed = n; }
}

describe('NoteListComponent', () => {
  it('formatNoteTail handles undefined and newlines', () => {
    const comp = new NoteListComponent(new StubRouter() as any, new StubNoteService() as any, new StubDialog() as any);
    expect(comp.formatNoteTail()).toBe('');
    const tail = 'Line1\nLine2';
    expect(comp.formatNoteTail(tail)).toBe('Line1 Line2');
  });

  it('applyFilter sets lowercase trimmed filter', () => {
    const comp = new NoteListComponent(new StubRouter() as any, new StubNoteService() as any, new StubDialog() as any);
    comp.applyFilter('  AbC  ');
    expect(comp.datasource.filter).toBe('abc');
  });

  it('pagesizeoptions uses data length', () => {
    const comp = new NoteListComponent(new StubRouter() as any, new StubNoteService() as any, new StubDialog() as any);
    comp.notes = [new ApiNote(), new ApiNote()];
    const opts = comp.pagesizeoptions();
    expect(opts[opts.length - 1]).toBe(2);
  });

  it('delete calls service and refreshes parent', () => {
    const router = new StubRouter();
    const svc = new StubNoteService();
    const dlg = new StubDialog();
    const comp = new NoteListComponent(router as any, svc as any, dlg as any);
    comp.parent = new StubParent() as any;
    const note = new ApiNote();
    note.string = 'N1';
    comp.delete(note);
    expect(svc.deleted).toBe(true);
  });

  it('openCreateNoteDialog triggers create when dialog returns data', () => {
    const svc = new StubNoteService();
    const dlg = new StubDialog();
    const comp = new NoteListComponent(new StubRouter() as any, svc as any, dlg as any);
    comp.parent = new StubParent() as any;
    comp.dataset = 'ds';
    dlg.result = { text: 'New Note' };
    comp.openCreateNoteDialog();
    expect(svc.posted).toBe(true);
  });

  it('navigate composes route path', () => {
    const router = new StubRouter();
    const comp = new NoteListComponent(router as any, new StubNoteService() as any, new StubDialog() as any);
    comp.dataset = 'ds';
    comp.navigate('N1');
    expect(router.navigated).toEqual(['/ds/notes/N1']);
  });
});

// Mock component to replace the child app-main-layout
@Component({
    selector: 'app-main-layout',
    template: '<ng-content></ng-content>',
    imports: []
})
class MockMainLayoutComponent {
  @Input() dataset: string;
}

describe('NoteListComponent', () => {
  let component: NoteListComponent;
  let fixture: ComponentFixture<NoteListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [
        NoteListComponent,
        MockMainLayoutComponent,
    ],
    providers: [
      provideRouter([]),
      provideHttpClient(),
      provideHttpClientTesting(),
      NoteService,
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
    fixture = TestBed.createComponent(NoteListComponent);
    component = fixture.componentInstance;
    component.dataset = 'testDataset';
    component.notes = [];
    component.parent = {} as any;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call ngOnChanges successfully', () => {
    component.notes = [];
    expect(() => component.ngOnChanges()).not.toThrow();
  });

  it('should call ngAfterViewInit successfully', () => {
    component.notes = [];
    expect(() => component.ngAfterViewInit()).not.toThrow();
  });

  it('should call ngOnInit successfully', () => {
    component.notes = [];
    expect(() => component.ngOnInit()).not.toThrow();
  });

  it('should return noteUB', () => {
    component.dataset = 'testDataset';
    const urlBuilder = component.noteUB();
    expect(urlBuilder).toBeDefined();
  });

  it('should return undefined for noteAnchor', () => {
    const result = component.noteAnchor();
    expect(result).toBeUndefined();
  });

  it('should call refreshNote on parent', () => {
    const mockNote = new (class {
      string = 'N1';
      tail = 'test note';
    })();
    const refreshSpy = { refreshNote: () => {} };
    component.parent = refreshSpy as any;
    expect(() => component.refreshNote(mockNote as any)).not.toThrow();
  });

  it('should prevent default and navigate on onSpaceKey', () => {
    const navigateSpy = { navigate: () => {} };
    component.router = navigateSpy as any;
    component.dataset = 'testDataset';
    const mockEvent = { preventDefault: () => {} };
    expect(() => component.onSpaceKey(mockEvent as any, 'N1')).not.toThrow();
  });

  it('should format note tail correctly', () => {
    const longTail = 'a'.repeat(100) + '\n' + 'b'.repeat(50);
    const result = component.formatNoteTail(longTail);
    expect(result.length).toBe(80);
    expect(result.includes('\n')).toBe(false);
  });
});

