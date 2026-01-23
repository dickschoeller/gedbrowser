import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogModule } from '@angular/material/dialog';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { NoteButtonComponent } from './note-button.component';
import { NoteService } from '../../services';

describe('NoteButtonComponent', () => {
  let component: NoteButtonComponent;
  let fixture: ComponentFixture<NoteButtonComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ NoteButtonComponent ],
      imports: [ MatDialogModule, NoopAnimationsModule ],
      providers: [ NoteService ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NoteButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
