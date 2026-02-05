import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatButtonModule } from '@angular/material/button';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { vi } from 'vitest';

import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatDialogModule } from '@angular/material/dialog';
import { provideNoopAnimations } from '@angular/platform-browser/animations';

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
    imports: [PersonParentFamilyComponent],
    providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        provideNoopAnimations()
    ],
    schemas: [NO_ERRORS_SCHEMA],
    providers: [PersonService, FamilyService, UserService, AuthApiService, ConfigService]
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

  it('returns surname from parent.person', () => {
    component.parent = { person: { surname: 'Doe' } } as any;
    expect(component.surname).toBe('Doe');
  });

  it('init fetches family and sets initialized', () => {
    const family = { string: 'F1', spouses: [], children: [] } as any;
    vi.spyOn(TestBed.inject(FamilyService), 'getOne').mockReturnValue(of(family));
    component.init();
    expect(component.family).toBe(family);
    expect(component.initialized).toBe(true);
  });

  it('familyString returns family.string', () => {
    component.family = { string: 'F99' } as any;
    expect(component.familyString()).toBe('F99');
  });

  it('refreshPerson delegates to parent', () => {
    component.parent = { refreshPerson: vi.fn() } as any;
    component.refreshPerson();
    expect(component.parent.refreshPerson).toHaveBeenCalled();
  });

  it('drop reorders children and saves', () => {
    component.family = { children: ['a', 'b', 'c'], string: 'F1' } as any;
    const spy = vi.spyOn(TestBed.inject(FamilyService), 'put').mockReturnValue(of(component.family));
    const event = { previousIndex: 0, currentIndex: 2 } as any;
    component.drop(event);
    expect(component.family.children).toEqual(['b', 'c', 'a']);
    expect(spy).toHaveBeenCalledWith('testDataset', component.family);
  });

  it('spouseLinked returns false', () => {
    expect(component.spouseLinked({} as any)).toBe(false);
  });

  it('childLinked returns false', () => {
    expect(component.childLinked({} as any)).toBe(false);
  });

  it('hasSignedIn checks userService.currentUser', () => {
    TestBed.inject(UserService).currentUser = null;
    expect(component.hasSignedIn()).toBe(false);
    TestBed.inject(UserService).currentUser = { id: 'u' } as any;
    expect(component.hasSignedIn()).toBe(true);
  });

  it('personUB returns UrlBuilder', () => {
    const ub = component.personUB();
    expect(ub.constructor.name).toBe('UrlBuilder');
  });

  it('personAnchor returns family.string', () => {
    component.family = { string: 'F2' } as any;
    expect(component.personAnchor()).toBe('F2');
  });

  it('preferredSurname returns surname', () => {
    component.parent = { person: { surname: 'Smith' } } as any;
    expect(component.preferredSurname()).toBe('Smith');
  });

  it('drop calls service.put to save family', () => {
    const family = { string: 'F1', children: ['a', 'b', 'c'] } as any;
    component.family = family;
    const spy = vi.spyOn(TestBed.inject(FamilyService), 'put').mockReturnValue(of(family));
    const event = { previousIndex: 0, currentIndex: 2 } as any;
    component.drop(event);
    expect(spy).toHaveBeenCalledWith('testDataset', family);
  });
});
