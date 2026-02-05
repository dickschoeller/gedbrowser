import { TestBed } from '@angular/core/testing';
import { vi } from 'vitest';

import { PersonFamilySpouseComponent } from './person-family-spouse.component';
import { UserService } from '../../services/user.service';
import { setupPersonComponentTest } from '../testing/person-component-spec-helpers';


describe('PersonFamilySpouseComponent', () => {
  let component: PersonFamilySpouseComponent;
  let fixture: any;

  beforeEach(() => {
    const setup = setupPersonComponentTest(PersonFamilySpouseComponent);
    fixture = setup.fixture;
    component = setup.component;
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
