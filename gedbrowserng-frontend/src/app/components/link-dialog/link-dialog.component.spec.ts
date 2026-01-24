import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogModule, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatListModule } from '@angular/material/list';
import { RouterTestingModule } from '@angular/router/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { LinkDialogComponent } from './link-dialog.component';

describe('LinkDialogComponent', () => {
  let component: LinkDialogComponent;
  let fixture: ComponentFixture<LinkDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [ LinkDialogComponent ],
      imports: [ MatDialogModule, MatListModule, RouterTestingModule, BrowserAnimationsModule ],
      providers: [ { provide: MAT_DIALOG_DATA, useValue: {} } ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LinkDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
