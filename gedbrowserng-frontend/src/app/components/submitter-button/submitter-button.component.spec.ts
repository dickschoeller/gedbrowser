import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogModule } from '@angular/material/dialog';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { SubmitterButtonComponent } from './submitter-button.component';
import { SubmitterService } from '../../services';

describe('SubmitterButtonComponent', () => {
  let component: SubmitterButtonComponent;
  let fixture: ComponentFixture<SubmitterButtonComponent>;
  let mockSubmitterService: any;

  beforeEach(() => {
    mockSubmitterService = {
      getUrls: () => ({})
    };

    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [ SubmitterButtonComponent ],
      imports: [ MatDialogModule, MatMenuModule, MatIconModule, MatTooltipModule, NoopAnimationsModule ],
      providers: [ { provide: SubmitterService, useValue: mockSubmitterService } ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubmitterButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
