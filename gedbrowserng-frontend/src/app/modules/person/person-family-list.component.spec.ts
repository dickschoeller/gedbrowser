import { describe, it, expect, vi } from 'vitest';
import { of } from 'rxjs';

import { PersonFamilyListComponent } from './person-family-list.component';
import { ApiPerson } from '../../models';
import { UrlBuilder } from '../../utils';

class StubUserService { currentUser: any = null; }

class StubPersonService {
  putLinkCalls: Array<{ ub: UrlBuilder; anchor: string; person: ApiPerson }> = [];
  getOne(_: string, __: string) { return of(this._mainPerson); }
  putLink(ub: UrlBuilder, anchor: string, person: ApiPerson) {
    this.putLinkCalls.push({ ub, anchor, person });
    return of(new ApiPerson());
  }
  _mainPerson: ApiPerson = new ApiPerson();
}

class StubParent { person: ApiPerson = new ApiPerson(); saveCalled = false; save() { this.saveCalled = true; } }

function makePerson(string: string, sex?: string): ApiPerson {
  const p = new ApiPerson();
  p.string = string as any;
  p.attributes = [] as any;
  if (sex) { (p.attributes as any).push({ string: 'Sex', tail: sex }); }
  return p;
}

describe('PersonFamilyListComponent', () => {
  it('init sets childSurname based on partnerSex', () => {
    const svc = new StubPersonService();
    const user = new StubUserService();
    const comp = new PersonFamilyListComponent(svc as any, user as any);
    // partner sex M -> childSurname '?'
    comp.person = makePerson('P1', 'F'); // opposite sex guessed 'M'
    comp.init();
    expect(comp.childSurname).toBe('?');
    // partner sex F -> childSurname person.surname
    comp.person = makePerson('P2', 'M');
    (comp.person as any).surname = 'Smith';
    comp.init();
    expect(comp.childSurname).toBe('Smith');
  });

  it('hasSignedIn reflects user presence', () => {
    const svc = new StubPersonService();
    const user = new StubUserService();
    const comp = new PersonFamilyListComponent(svc as any, user as any);
    expect(comp.hasSignedIn()).toBe(false);
    user.currentUser = { id: 'u' };
    expect(comp.hasSignedIn()).toBe(true);
  });

  it('linkChildren links first via person UB then remaining via family UB', () => {
    const svc = new StubPersonService();
    const user = new StubUserService();
    const comp = new PersonFamilyListComponent(svc as any, user as any);
    comp.dataset = 'ds';
    comp.person = makePerson('P1');
    comp.parent = new StubParent() as any;
    const fam = { string: 'F9' } as any;
    (svc._mainPerson as any).famss = [{ string: 'F1' }, fam];
    const selected = [{ person: makePerson('C1') }, { person: makePerson('C2') }];
    const data: any = { selected: selected.slice() };
    comp.linkChildren(data);
    // First call uses persons/children on main person
    expect(svc.putLinkCalls[0].ub.t).toBe('persons');
    expect(svc.putLinkCalls[0].ub.sub).toBe('children');
    // Remaining children use families/children on last famss
    const remainingCalls = svc.putLinkCalls.slice(1);
    expect(remainingCalls.length).toBe(1);
    expect(remainingCalls[0].ub.t).toBe('families');
    expect(remainingCalls[0].ub.sub).toBe('children');
  });

  it('drop reorders and triggers save', () => {
    const svc = new StubPersonService();
    const user = new StubUserService();
    const comp = new PersonFamilyListComponent(svc as any, user as any);
    comp.parent = new StubParent() as any;
    comp.person = new ApiPerson();
    (comp.person as any).famss = ['A', 'B', 'C'] as any;
    comp.drop({ previousIndex: 0, currentIndex: 2 } as any);
    expect((comp.person as any).famss[2]).toBe('A');
    expect((comp.parent as any).saveCalled).toBe(true);
  });

  it('refreshPerson updates parent.person from service', () => {
    const svc = new StubPersonService();
    const user = new StubUserService();
    const comp = new PersonFamilyListComponent(svc as any, user as any);
    const updated = makePerson('P2');
    svc._mainPerson = updated;
    comp.dataset = 'ds';
    comp.person = makePerson('P1');
    const parent = new StubParent() as any;
    comp.parent = parent;
    comp.refreshPerson();
    expect(parent.person).toBe(updated);
  });

  it('spouseLinked and childLinked behave as intended', () => {
    const svc = new StubPersonService();
    const user = new StubUserService();
    const comp = new PersonFamilyListComponent(svc as any, user as any);
    comp.person = makePerson('P1');
    expect(comp.spouseLinked(makePerson('P1'))).toBe(true);
    expect(comp.childLinked(makePerson('C1'))).toBe(false);
  });

  it('linkSpouse sets UB to persons/spouses and delegates', () => {
    const svc = new StubPersonService();
    const user = new StubUserService();
    const comp = new PersonFamilyListComponent(svc as any, user as any);
    comp.dataset = 'ds';
    comp.person = makePerson('P1');
    const spy = vi.spyOn(comp as any, 'linkPerson').mockImplementation(() => {});
    comp.linkSpouse({ selected: [] } as any);
    expect(spy).toHaveBeenCalled();
    expect((comp as any)._ub.t).toBe('persons');
    expect((comp as any)._ub.sub).toBe('spouses');
  });

  it('createSpouse and createChild set UB correctly before delegating', () => {
    const svc = new StubPersonService();
    const user = new StubUserService();
    const comp = new PersonFamilyListComponent(svc as any, user as any);
    comp.dataset = 'ds';
    comp.person = makePerson('P1');
    const createSpy = vi.spyOn(comp as any, 'createPerson').mockImplementation(() => {});
    comp.createSpouse({} as any);
    expect(createSpy).toHaveBeenCalled();
    expect((comp as any)._ub.sub).toBe('spouses');
    comp.createChild({} as any);
    expect((comp as any)._ub.sub).toBe('children');
  });
});
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

import { PersonFamilyListComponent } from './person-family-list.component';
import { ConfigService } from '../../services/config.service';

import { AuthApiService } from '../../services/auth-api.service';

import { UserService } from '../../services/user.service';

import { PersonService } from '../../services/person.service';


describe('PersonFamilyListComponent', () => {
  let component: PersonFamilyListComponent;
  let fixture: ComponentFixture<PersonFamilyListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [ PersonFamilyListComponent ],
      providers: [ PersonService, UserService, AuthApiService, ConfigService ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonFamilyListComponent);
    component = fixture.componentInstance;
    component.dataset = 'testDataset';
    component.person = { families: [], attributes: [] } as any;
    component.parent = { families: [], refresh: () => {} } as any;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
