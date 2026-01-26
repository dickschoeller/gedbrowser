import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { vi } from 'vitest';
import { PersonFamilyChildComponent } from './person-family-child.component';
import { PersonService, UserService } from '../../services';
import { ApiPerson } from '../../models';
import { UrlBuilder } from '../../utils';


describe('PersonFamilyChildComponent', () => {
  let component: PersonFamilyChildComponent;
  let fixture: ComponentFixture<PersonFamilyChildComponent>;

  const personServiceMock = {
    getOne: vi.fn(),
    deleteLink: vi.fn(),
  } as unknown as PersonService;

  const userServiceMock = {
    currentUser: undefined as unknown,
  } as unknown as UserService;

  const createPerson = (overrides: Partial<ApiPerson> = {}): ApiPerson => ({
    string: 'test',
    indexName: 'Test Person',
    lifespan: {
      birthYear: 1950,
      deathYear: 2020,
      birthDate: '1 JAN 1950',
      deathDate: '15 DEC 2020',
    },
    ...overrides,
  } as ApiPerson);

  const parentMock = {
    family: {
      string: 'F1',
      children: [
        { string: 'C1' },
        { string: 'C2' },
        { string: 'C3' },
      ],
    },
    familyString: vi.fn().mockReturnValue('F1'),
    refreshPerson: vi.fn(),
  } as any;

  beforeEach(async () => {
    personServiceMock.getOne = vi.fn().mockReturnValue(of(createPerson()));
    personServiceMock.deleteLink = vi.fn().mockReturnValue(of({}));

    await TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [PersonFamilyChildComponent],
      providers: [
        { provide: PersonService, useValue: personServiceMock },
        { provide: UserService, useValue: userServiceMock },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(PersonFamilyChildComponent);
    component = fixture.componentInstance;
    component.dataset = 'testDataset';
    component.parent = parentMock;
    component.child = { string: 'C1', type: 'child' } as any;
    component.index = 0;
  });

  afterEach(() => {
    vi.clearAllMocks();
    userServiceMock.currentUser = undefined;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit()', () => {
    it('should initialize person data on init', () => {
      vi.spyOn(component as any, 'init');

      component.ngOnInit();

      expect((component as any).init).toHaveBeenCalledWith('testDataset', 'C1');
    });

    it('should fetch person from PersonService', () => {
      component.ngOnInit();

      expect(personServiceMock.getOne).toHaveBeenCalledWith('testDataset', 'C1');
      expect(component.person).toBeDefined();
      expect(component.person?.indexName).toBe('Test Person');
    });
  });

  describe('ngOnChanges()', () => {
    it('should reinitialize person data on changes', () => {
      vi.spyOn(component as any, 'init');

      component.ngOnChanges();

      expect((component as any).init).toHaveBeenCalledWith('testDataset', 'C1');
    });

    it('should fetch updated person when child changes', () => {
      component.child = { string: 'C2', type: 'child' } as any;

      component.ngOnChanges();

      expect(personServiceMock.getOne).toHaveBeenCalledWith('testDataset', 'C2');
    });
  });

  describe('first()', () => {
    it('should return true when index is 0', () => {
      component.index = 0;
      
      expect(component.first()).toBe(true);
    });

    it('should return false when index is not 0', () => {
      component.index = 1;
      
      expect(component.first()).toBe(false);
    });

    it('should return false when index is last position', () => {
      component.index = 2;
      
      expect(component.first()).toBe(false);
    });
  });

  describe('last()', () => {
    it('should return true when index is last position', () => {
      component.index = 2; // children.length = 3, so last index is 2
      
      expect(component.last()).toBe(true);
    });

    it('should return false when index is first position', () => {
      component.index = 0;
      
      expect(component.last()).toBe(false);
    });

    it('should return false when index is middle position', () => {
      component.index = 1;
      
      expect(component.last()).toBe(false);
    });

    it('should handle single child correctly', () => {
      component.parent.family.children = [{ string: 'C1' }];
      component.index = 0;
      
      expect(component.last()).toBe(true);
    });
  });

  describe('familyString()', () => {
    it('should return family string from parent', () => {
      const result = component.familyString();
      
      expect(component.parent.familyString).toHaveBeenCalled();
      expect(result).toBe('F1');
    });
  });

  describe('refreshPerson()', () => {
    it('should call parent refreshPerson method', () => {
      component.refreshPerson();
      
      expect(component.parent.refreshPerson).toHaveBeenCalled();
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

  describe('lifespanYearString()', () => {
    it('should return year range for person with both birth and death years', () => {
      component.ngOnInit();

      const result = component.lifespanYearString();
      expect(result.trim()).toBe('(1950-2020)');
    });

    it('should handle person with no lifespan data', () => {
      (personServiceMock.getOne as any).mockReturnValue(of(createPerson({
        lifespan: { birthYear: undefined, deathYear: undefined } as any,
      })));

      component.ngOnInit();

      const result = component.lifespanYearString();
      expect(result).toBeDefined();
    });
  });

  describe('unlink()', () => {
    it('should call deleteLink and refresh person', () => {
      component.person = createPerson();

      component.unlink();

      expect(personServiceMock.deleteLink).toHaveBeenCalledTimes(1);
      expect(parentMock.refreshPerson).toHaveBeenCalledTimes(1);
    });

    it('should use correct parameters for deleteLink', () => {
      component.person = createPerson({ string: 'P9' });
      (component as any).hiddenDataset = 'testDataset';

      component.unlink();

      const [builder, famString, person] = (personServiceMock.deleteLink as any).mock.calls[0];
      expect(builder.constructor.name).toBe('UrlBuilder');
      expect((builder as UrlBuilder).plusT()).toBe('/gedbrowserng/v1/dbs/testDataset/families');
      expect(famString).toBe('F1');
      expect(person.string).toBe('P9');
    });
  });

  describe('constructor', () => {
    it('should set famMemberType to children', () => {
      expect((component as any).famMemberType).toBe('children');
    });
  });
});
