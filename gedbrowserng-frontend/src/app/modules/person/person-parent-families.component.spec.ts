import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatButtonModule } from '@angular/material/button';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatDialogModule } from '@angular/material/dialog';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { PersonParentFamiliesComponent } from './person-parent-families.component';
import { ConfigService } from '../../services/config.service';

import { AuthApiService } from '../../services/auth-api.service';

import { UserService } from '../../services/user.service';

import { PersonService } from '../../services/person.service';


describe('PersonParentFamiliesComponent', () => {
  let component: PersonParentFamiliesComponent;
  let fixture: ComponentFixture<PersonParentFamiliesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [ PersonParentFamiliesComponent ],
      providers: [ PersonService, UserService, AuthApiService, ConfigService ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonParentFamiliesComponent);
    component = fixture.componentInstance;
    component.dataset = 'testDataset';
    component.person = { string: 'test', name: 'Test Person', famcs: [] } as any;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
