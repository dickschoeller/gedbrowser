import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogModule } from '@angular/material/dialog';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { SourceButtonComponent } from './source-button.component';
import { SourceService } from '../../services';

describe('SourceButtonComponent', () => {
  let component: SourceButtonComponent;
  let fixture: ComponentFixture<SourceButtonComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ SourceButtonComponent ],
      imports: [ MatDialogModule, NoopAnimationsModule ],
      providers: [ SourceService ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SourceButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
