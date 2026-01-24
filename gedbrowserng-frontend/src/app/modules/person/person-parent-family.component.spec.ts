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

import { PersonParentFamilyComponent } from './person-parent-family.component';
import { ConfigService } from '../../services/config.service';

import { AuthApiService } from '../../services/auth-api.service';

import { UserService } from '../../services/user.service';

import { FamilyService } from '../../services/family.service';

import { PersonService } from '../../services/person.service';


describe('PersonParentFamilyComponent', () => {
  let component: PersonParentFamilyComponent;
  let fixture: ComponentFixture<PersonParentFamilyComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [ PersonParentFamilyComponent ],
      providers: [ PersonService, FamilyService, UserService, AuthApiService, ConfigService ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonParentFamilyComponent);
    component = fixture.componentInstance;
    component.dataset = 'testDataset';
    component.attribute = { string: 'F1', tail: [] } as any;
    component.parent = { refresh: () => {}, person: { string: 'test' } } as any;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
