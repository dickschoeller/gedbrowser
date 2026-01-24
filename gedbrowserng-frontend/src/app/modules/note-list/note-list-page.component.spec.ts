import { Component, Input } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NoteListPageComponent } from './note-list-page.component';
import { NoteService } from '../../services';

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

describe('NoteListPageComponent', () => {
  let component: NoteListPageComponent;
  let fixture: ComponentFixture<NoteListPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ NoteListPageComponent, MockNoteListComponent ],
      imports: [ HttpClientTestingModule, RouterTestingModule, NoopAnimationsModule ],
      providers: [
        NoteService,
        {
          provide: ActivatedRoute,
          useValue: {
            params: of({ dataset: 'testDataset' }),
            data: of({ dataset: 'testDataset', notes: [] })
          }
        }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NoteListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
