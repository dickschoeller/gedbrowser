import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { provideAnimations } from '@angular/platform-browser/animations';
import { ActivatedRoute, provideRouter } from '@angular/router';
import { of } from 'rxjs';

import { NoteComponent } from './note.component';
import { NoteService, DatasetsService, SaveService, UploadService, UserService, AuthService, AuthApiService, ConfigService } from '../../services';
import { ApiNote, ApiAttribute } from '../../models';

describe('NoteComponent', () => {
  let component: NoteComponent;
  let fixture: ComponentFixture<NoteComponent>;
  let noteService: NoteService;
  let mockNote: ApiNote;

  beforeEach(() => {
    mockNote = {
      string: 'Note ID',
      tail: 'This is a test note with some content',
      attributes: [
        { type: 'attribute', string: 'Test', tail: 'value' } as ApiAttribute
      ]
    } as ApiNote;

    TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [
        MatButtonModule,
        MatSelectModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
        FormsModule,
        NoteComponent
    ],
    providers: [
      provideRouter([]),
      provideHttpClient(),
      provideHttpClientTesting(),
      provideAnimations(),
      NoteService,
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
          data: of({ dataset: 'test-dataset', note: mockNote }),
          params: of({ dataset: 'test-dataset' })
        }
      },
    ]
})
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NoteComponent);
    component = fixture.componentInstance;
    noteService = TestBed.inject(NoteService);
    // Don't call detectChanges - the component uses 'dataset' which conflicts with HTMLElement.dataset
    // Manually trigger ngOnInit for tests
    component.ngOnInit();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('initializes with dataset from route params', () => {
    expect(component.dataset).toBe('test-dataset');
  });

  it('initializes with note from route data', () => {
    expect(component.note).toEqual(mockNote);
    expect(component.attributes).toEqual(mockNote.attributes);
  });

  it('truncates note with default length', () => {
    component.note = { tail: 'a'.repeat(100) } as ApiNote;
    const result = component.truncateNote();
    // Truncate adds '...' so result is length + 3
    expect(result.length).toBeLessThanOrEqual(83); // 80 + '...'
    expect(result).toContain('...');
  });

  it('truncates note with custom length', () => {
    component.note = { tail: 'a'.repeat(100) } as ApiNote;
    const result = component.truncateNote(20);
    // Truncate adds '...' so result is length + 3
    expect(result.length).toBeLessThanOrEqual(23); // 20 + '...'
    expect(result).toContain('...');
  });

  it('does not truncate short notes', () => {
    component.note = { tail: 'Short note' } as ApiNote;
    const result = component.truncateNote(70);
    expect(result).toBe('Short note');
  });

  it('returns options array', () => {
    const options = component.options();
    expect(options).toBeDefined();
    expect(options.length).toBeGreaterThan(0);
    expect(options[0]).toEqual({ value: 'sourcelink', label: 'Source Link' });
  });

  it('returns default data for attribute dialog', () => {
    const data = component.defaultData();
    expect(data).toBeDefined();
    expect(data.type).toBe('sourcelink');
    expect(data.insert).toBe(true);
  });

  it('save calls service put and updates note', () => {
    const updatedNote = { ...mockNote, tail: 'Updated content' } as ApiNote;
    const putSpy = vi.spyOn(noteService, 'put').mockReturnValue(of(updatedNote));
    
    component.save();
    
    expect(putSpy).toHaveBeenCalledWith('test-dataset', mockNote);
    expect(component.note).toEqual(updatedNote);
  });

  it('save preserves dataset and note reference', () => {
    const originalDataset = component.dataset;
    const putSpy = vi.spyOn(noteService, 'put').mockReturnValue(of(mockNote));
    
    component.save();
    
    expect(component.dataset).toBe(originalDataset);
    expect(putSpy).toHaveBeenCalledWith(originalDataset, component.note);
  });
});
