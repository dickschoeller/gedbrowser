import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialog } from '@angular/material/dialog';
import { of } from 'rxjs';
import { vi } from 'vitest';
import { PersonFamilyChildListComponent } from './person-family-child-list.component';
import { PersonService, FamilyService, UserService } from '../../services';


describe('PersonFamilyChildListComponent', () => {
  let component: PersonFamilyChildListComponent;
  let fixture: ComponentFixture<PersonFamilyChildListComponent>;

  const personServiceMock = {
    getOne: vi.fn(),
    putLink: vi.fn(),
  } as unknown as PersonService;

  const familyServiceMock = {
    put: vi.fn(),
  } as unknown as FamilyService;

  const userServiceMock = {
    currentUser: undefined as unknown,
  } as unknown as UserService;

  const dialogMock = {
    result: undefined as any,
    open: vi.fn(() => ({
      afterClosed: () => of(dialogMock.result)
    }))
  } as any;

  const parentMock = {
    person: { surname: 'ParentSurname', string: 'P1' },
    save: vi.fn(),
    refreshPerson: vi.fn(),
  } as any;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
    imports: [PersonFamilyChildListComponent],
    schemas: [NO_ERRORS_SCHEMA],
    providers: [
        { provide: PersonService, useValue: personServiceMock },
        { provide: FamilyService, useValue: familyServiceMock },
      { provide: MatDialog, useValue: dialogMock },
        { provide: UserService, useValue: userServiceMock },
    ],
}).compileComponents();

    fixture = TestBed.createComponent(PersonFamilyChildListComponent);
    component = fixture.componentInstance;
    component.dataset = 'testDataset';
    component.family = {
      string: 'F1',
      spouses: [],
      children: [],
    } as any;
    component.parent = parentMock;
  });

  afterEach(() => {
    vi.clearAllMocks();
    dialogMock.result = undefined;
    userServiceMock.currentUser = undefined;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('init()', () => {
    it('should set surname from husband when husband exists', () => {
      component.family.spouses = [
        { type: 'husband', string: 'H1' }
      ];
      (personServiceMock.getOne as any).mockReturnValue(of({ surname: 'HusbandSurname' } as any));
      
      component.init();
      
      expect(personServiceMock.getOne).toHaveBeenCalledWith('testDataset', 'H1');
      expect(component.surname).toBe('HusbandSurname');
    });

    it('should set surname from parent when no husband exists', () => {
      component.family.spouses = [
        { type: 'wife', string: 'W1' }
      ];
      
      component.init();
      
      expect(component.surname).toBe('ParentSurname');
    });

    it('should set surname from parent when spouse list is empty', () => {
      component.family.spouses = [];
      
      component.init();
      
      expect(component.surname).toBe('ParentSurname');
    });
  });

  describe('husbandId()', () => {
    it('should return husband id when husband exists', () => {
      component.family.spouses = [
        { type: 'wife', string: 'W1' },
        { type: 'husband', string: 'H1' }
      ];
      
      const result = (component as any).husbandId();
      
      expect(result).toBe('H1');
    });

    it('should return empty string when no husband exists', () => {
      component.family.spouses = [
        { type: 'wife', string: 'W1' }
      ];
      
      const result = (component as any).husbandId();
      
      expect(result).toBe('');
    });
  });

  describe('personUB()', () => {
    it('should return UrlBuilder instance', () => {
      const ub = component.personUB();
      
      expect(ub).toBeDefined();
      expect(ub.constructor.name).toBe('UrlBuilder');
    });
  });

  describe('personAnchor()', () => {
    it('should return family string', () => {
      component.family.string = 'F123';
      
      const result = component.personAnchor();
      
      expect(result).toBe('F123');
    });
  });

  describe('refreshPerson()', () => {
    it('should call personService.getOne and parent.refreshPerson', () => {
      const mockPerson = { string: 'P1', name: 'Test' };
      (personServiceMock.getOne as any).mockReturnValue(of(mockPerson as any));
      
      component.refreshPerson();
      
      expect(personServiceMock.getOne).toHaveBeenCalledWith('testDataset', 'P1');
      expect(component.parent.refreshPerson).toHaveBeenCalled();
    });
  });

  describe('spouseLinked()', () => {
    it('should return true when spouse is in family', () => {
      component.family.spouses = [
        { string: 'S1' },
        { string: 'S2' }
      ];
      const person = { string: 'S2' } as any;
      
      const result = component.spouseLinked(person);
      
      expect(result).toBe(true);
    });

    it('should return false when spouse is not in family', () => {
      component.family.spouses = [
        { string: 'S1' },
        { string: 'S2' }
      ];
      const person = { string: 'S3' } as any;
      
      const result = component.spouseLinked(person);
      
      expect(result).toBe(false);
    });

    it('should return false when spouses list is empty', () => {
      component.family.spouses = [];
      const person = { string: 'S1' } as any;
      
      const result = component.spouseLinked(person);
      
      expect(result).toBe(false);
    });
  });

  describe('childLinked()', () => {
    it('should return true when child is in children list', () => {
      component.children = [
        { string: 'C1' },
        { string: 'C2' }
      ] as any;
      const person = { string: 'C2' } as any;
      
      const result = component.childLinked(person);
      
      expect(result).toBe(true);
    });

    it('should return false when child is not in children list', () => {
      component.children = [
        { string: 'C1' },
        { string: 'C2' }
      ] as any;
      const person = { string: 'C3' } as any;
      
      const result = component.childLinked(person);
      
      expect(result).toBe(false);
    });

    it('should return false when children list is empty', () => {
      component.children = [];
      const person = { string: 'C1' } as any;
      
      const result = component.childLinked(person);
      
      expect(result).toBe(false);
    });
  });

  describe('linkChild()', () => {
    it('should link single child and refresh', () => {
      const mockPerson = { string: 'C1', name: 'Child1' };
      const data = {
        selected: [{ person: mockPerson }]
      } as any;
      (personServiceMock.putLink as any).mockReturnValue(of(mockPerson as any));
      
      component.linkChild(data);
      
      expect(personServiceMock.putLink).toHaveBeenCalledWith(
        component.personUB(),
        'F1',
        mockPerson
      );
      expect(component.parent.refreshPerson).toHaveBeenCalled();
    });

    it('should link multiple children and refresh for each', () => {
      const mockPerson1 = { string: 'C1', name: 'Child1' };
      const mockPerson2 = { string: 'C2', name: 'Child2' };
      const data = {
        selected: [
          { person: mockPerson1 },
          { person: mockPerson2 }
        ]
      } as any;
      (personServiceMock.putLink as any).mockReturnValue(of({} as any));
      
      component.linkChild(data);
      
      expect(personServiceMock.putLink).toHaveBeenCalledTimes(2);
      expect(personServiceMock.putLink).toHaveBeenCalledWith(
        component.personUB(),
        'F1',
        mockPerson1
      );
      expect(personServiceMock.putLink).toHaveBeenCalledWith(
        component.personUB(),
        'F1',
        mockPerson2
      );
    });
  });

  describe('openChildDialog()', () => {
    it('should route new mode result to createPerson', () => {
      dialogMock.result = { mode: 'new', newPersonData: { name: 'Kid' } };
      const createSpy = vi.spyOn(component as any, 'createPerson').mockImplementation(() => {});

      component.openChildDialog();

      expect(dialogMock.open).toHaveBeenCalled();
      expect(createSpy).toHaveBeenCalledWith({ name: 'Kid' });
    });

    it('should route existing mode result to linkChild with id', () => {
      dialogMock.result = { mode: 'existing', existingPersonIds: ['I999'] };
      const linkSpy = vi.spyOn(component, 'linkChild').mockImplementation(() => {});

      component.openChildDialog();

      expect(linkSpy).toHaveBeenCalled();
      expect((linkSpy as any).mock.calls[0][0].selectOne.person.string).toBe('I999');
    });

    it('should ignore undefined result', () => {
      dialogMock.result = undefined;
      const createSpy = vi.spyOn(component as any, 'createPerson').mockImplementation(() => {});
      const linkSpy = vi.spyOn(component, 'linkChild').mockImplementation(() => {});

      component.openChildDialog();

      expect(createSpy).not.toHaveBeenCalled();
      expect(linkSpy).not.toHaveBeenCalled();
    });
  });

  describe('preferredSurname()', () => {
    it('should return parent person surname', () => {
      component.parent.person.surname = 'TestSurname';
      
      const result = component.preferredSurname();
      
      expect(result).toBe('TestSurname');
    });
  });

  describe('familyString()', () => {
    it('should return family string', () => {
      component.family.string = 'F456';
      
      const result = component.familyString();
      
      expect(result).toBe('F456');
    });
  });

  describe('drop()', () => {
    it('should reorder children and update via familyService', () => {
      const children = [
        { string: 'C1' },
        { string: 'C2' },
        { string: 'C3' }
      ] as any;
      component.family.children = children;
      component.children = children;
      
      const mockUpdatedFamily = {
        ...component.family,
        children: [{ string: 'C2' }, { string: 'C1' }, { string: 'C3' }]
      };
      (familyServiceMock.put as any).mockReturnValue(of(mockUpdatedFamily as any));
      
      const event = {
        previousIndex: 1,
        currentIndex: 0
      } as any;
      
      component.drop(event);
      
      expect(familyServiceMock.put).toHaveBeenCalledWith('testDataset', component.family);
      expect(component.children).toBe(mockUpdatedFamily.children);
    });
  });

  describe('save()', () => {
    it('should call parent save method', () => {
      component.save();
      
      expect(component.parent.save).toHaveBeenCalled();
    });
  });

  describe('hasSignedIn()', () => {
    it('should return true when user is signed in', () => {
      userServiceMock.currentUser = { username: 'testuser' } as any;
      
      const result = component.hasSignedIn();
      
      expect(result).toBe(true);
    });

    it('should return false when user is not signed in', () => {
      userServiceMock.currentUser = null as any;
      
      const result = component.hasSignedIn();
      
      expect(result).toBe(false);
    });

    it('should return false when currentUser is undefined', () => {
      userServiceMock.currentUser = undefined as any;
      
      const result = component.hasSignedIn();
      
      expect(result).toBe(false);
    });
  });
});
