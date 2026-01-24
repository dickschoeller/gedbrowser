import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NoteComponent } from './note.component';
import { NoteService } from '../../services';

describe('NoteComponent', () => {
  let component: NoteComponent;
  let fixture: ComponentFixture<NoteComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [ NoteComponent ],
      imports: [ MatButtonModule, MatSelectModule, MatFormFieldModule, MatInputModule, ReactiveFormsModule, FormsModule, HttpClientTestingModule, NoopAnimationsModule ],
      providers: [
        NoteService,
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({ dataset: 'test', note: {} }),
            params: of({ dataset: 'test' })
          }
        }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NoteComponent);
    component = fixture.componentInstance;
    // Don't call detectChanges here - component needs route data first
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
