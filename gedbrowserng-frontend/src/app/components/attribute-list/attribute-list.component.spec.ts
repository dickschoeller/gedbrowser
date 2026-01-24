import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { AttributeListComponent } from './attribute-list.component';
import { UserService, AuthApiService, ConfigService } from '../../services';

describe('AttributeListComponent', () => {
  let component: AttributeListComponent;
  let fixture: ComponentFixture<AttributeListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [ AttributeListComponent ],
      imports: [ MatButtonModule, MatSelectModule, MatFormFieldModule, MatInputModule, ReactiveFormsModule, FormsModule, NoopAnimationsModule, HttpClientTestingModule ],
      providers: [ UserService, AuthApiService, ConfigService ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AttributeListComponent);
    component = fixture.componentInstance;
    component.attributes = [];
    component.parent = { attributes: [], deleteAttribute: () => {}, addAttribute: () => {} } as any;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
