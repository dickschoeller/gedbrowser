import { TestBed } from '@angular/core/testing';

import { NoteListModule } from './note-list.module';
import { NoteListPageComponent } from './note-list-page.component';
import { NoteListComponent } from './note-list.component';

describe('NoteListModule', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NoteListModule]
    }).compileComponents();
  });

  it('should be defined', () => {
    expect(NoteListModule).toBeDefined();
  });

  it('should create NoteListPageComponent', () => {
    const fixture = TestBed.createComponent(NoteListPageComponent);
    const component = fixture.componentInstance;

    expect(component).toBeTruthy();
  });

  it('should create NoteListComponent', () => {
    const fixture = TestBed.createComponent(NoteListComponent);
    const component = fixture.componentInstance;

    expect(component).toBeTruthy();
  });
});
