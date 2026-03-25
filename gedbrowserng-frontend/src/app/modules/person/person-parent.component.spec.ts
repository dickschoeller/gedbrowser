import { of } from 'rxjs';
import { vi } from 'vitest';

import { PersonParentComponent } from './person-parent.component';
import { ApiAttribute } from '../../models';
import { setupPersonComponentTest } from '../testing/person-component-spec-helpers';


describe('PersonParentComponent', () => {
  let component: PersonParentComponent;
  let mockPersonService: any;

  const mockAttribute: ApiAttribute = {
    string: 'I123',
    type: 'husband',
    tail: '',
    attributes: []
  } as ApiAttribute;

  const mockParent = {
    familyString: () => 'F123',
    refreshPerson: () => {}
  };

  beforeEach(() => {
    const setup = setupPersonComponentTest(PersonParentComponent, {
      inputs: {
        dataset: 'testDataset',
        attribute: mockAttribute,
        parent: mockParent
      },
      detectChanges: true
    });
    component = setup.component;
    mockPersonService = setup.mockPersonService;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('familyString delegates to parent', () => {
    expect(component.familyString()).toBe('F123');
  });

  it('refreshPerson delegates to parent', () => {
    const spy = vi.spyOn(mockParent, 'refreshPerson');
    component.refreshPerson();
    expect(spy).toHaveBeenCalled();
  });

  it('unlink calls deleteLink and refreshes', () => {
    const person = { string: 'P1' } as any;
    component.person = person;
    const spy = vi.spyOn(mockPersonService, 'deleteLink').mockReturnValue(of(person));
    const refreshSpy = vi.spyOn(mockParent, 'refreshPerson');
    component.unlink();
    expect(spy).toHaveBeenCalled();
    expect(refreshSpy).toHaveBeenCalled();
  });

  it('ngOnChanges re-initializes with current dataset and attribute', () => {
    const initSpy = vi.spyOn(component as any, 'init').mockImplementation(() => {});
    component.dataset = 'ds';
    component.attribute = { ...mockAttribute, string: 'I999' } as any;
    component.ngOnChanges();
    expect(initSpy).toHaveBeenCalledWith('ds', 'I999');
  });
});
