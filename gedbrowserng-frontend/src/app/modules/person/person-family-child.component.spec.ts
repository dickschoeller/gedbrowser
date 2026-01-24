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
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PersonFamilyChildComponent } from './person-family-child.component';
import { ConfigService } from '../../services/config.service';

import { AuthApiService } from '../../services/auth-api.service';

import { UserService } from '../../services/user.service';

import { PersonService } from '../../services/person.service';


describe('PersonFamilyChildComponent', () => {
  let component: PersonFamilyChildComponent;
  let fixture: ComponentFixture<PersonFamilyChildComponent>;
  let mockPersonService: any;

  beforeEach(() => {
    mockPersonService = {
      getOne: () => of({ 
        string: 'test', 
        indexName: 'Test', 
        lifespan: {
          birthYear: undefined,
          deathYear: undefined,
          birthDate: undefined,
          deathDate: undefined
        }
      })
    };

    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [ PersonFamilyChildComponent ],
      imports: [ MatButtonModule, RouterTestingModule ],
      providers: [ 
        { provide: PersonService, useValue: mockPersonService },
        UserService, AuthApiService, ConfigService 
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonFamilyChildComponent);
    component = fixture.componentInstance;
    component.dataset = 'testDataset';
    component.parent = { family: { children: [] }, refresh: () => {} } as any;
    component.child = { string: 'test', type: 'test' } as any;
    component.index = 0;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
