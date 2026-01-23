import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogModule, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { MultimediaDialogComponent } from './multimedia-dialog.component';
import { UserService } from '../../services';

describe('MultimediaDialogComponent', () => {
  let component: MultimediaDialogComponent;
  let fixture: ComponentFixture<MultimediaDialogComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ MultimediaDialogComponent ],
      imports: [ MatDialogModule, DragDropModule, NoopAnimationsModule ],
      providers: [ { provide: MAT_DIALOG_DATA, useValue: {} }, UserService ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MultimediaDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
