import { vi } from 'vitest';

import { PersonFamilySpouseComponent } from './person-family-spouse.component';
import { setupPersonComponentTest } from '../testing/person-component-spec-helpers';


describe('PersonFamilySpouseComponent', () => {
  let component: PersonFamilySpouseComponent;
  let fixture: any;
  let mockUserService: any;

  beforeEach(() => {
    const setup = setupPersonComponentTest(PersonFamilySpouseComponent, {
      inputs: {
        dataset: 'testDataset',
        parent: { families: [], refresh: () => {} },
        attribute: { string: 'test', type: 'test' },
        index: 0
      },
      detectChanges: true
    });
    fixture = setup.fixture;
    component = setup.component;
    mockUserService = setup.mockUserService;
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
    expect(component.hasSignedIn()).toBe(false);
    mockUserService.currentUser = { id: 'u' };
    expect(component.hasSignedIn()).toBe(true);
  });
});
