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
import { vi } from 'vitest';

import { PersonFamilySpouseComponent } from './person-family-spouse.component';
import { ConfigService } from '../../services/config.service';

import { AuthApiService } from '../../services/auth-api.service';

import { UserService } from '../../services/user.service';

import { PersonService } from '../../services/person.service';


describe('PersonFamilySpouseComponent', () => {
  let component: PersonFamilySpouseComponent;
  let fixture: ComponentFixture<PersonFamilySpouseComponent>;
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
      declarations: [ PersonFamilySpouseComponent ],
      imports: [ MatButtonModule, RouterTestingModule ],
      providers: [ 
        { provide: PersonService, useValue: mockPersonService },
        UserService, AuthApiService, ConfigService 
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonFamilySpouseComponent);
    component = fixture.componentInstance;
    component.dataset = 'testDataset';
    component.parent = { families: [], refresh: () => {} } as any;
    component.attribute = { string: 'test', type: 'test' } as any;
    component.index = 0;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('ngOnInit and ngOnChanges both initialize person', () => {
    const spy = vi.spyOn(component as any, 'init');
    component.ngOnInit();
    component.ngOnChanges();
    expect(spy).toHaveBeenCalledTimes(2);
  });

  it('familyString and refreshPerson delegate to parent', () => {
    const parent = { familyString: () => 'F9', refreshPerson: vi.fn() } as any;
    component.parent = parent;
    expect(component.familyString()).toBe('F9');
    component.refreshPerson();
    expect(parent.refreshPerson).toHaveBeenCalled();
  });

  it('hasSignedIn reflects user presence', () => {
    const userSvc = TestBed.inject(UserService);
    expect(component.hasSignedIn()).toBe(false);
    (userSvc as any).currentUser = { id: 'u' };
    expect(component.hasSignedIn()).toBe(true);
  });
});
