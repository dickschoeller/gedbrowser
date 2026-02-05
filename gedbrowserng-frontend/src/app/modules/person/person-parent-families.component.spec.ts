import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { of } from 'rxjs';
import { CdkDragDrop } from '@angular/cdk/drag-drop';
import { PersonParentFamiliesComponent } from './person-parent-families.component';
import { PersonService, UserService } from '../../services';
import { ApiPerson } from '../../models';
import { UrlBuilder } from '../../utils';

const createPerson = (overrides: Partial<ApiPerson> = {}): ApiPerson => ({
  string: 'P1',
  surname: 'TestSurname',
  famcs: [],
  ...overrides,
});

describe('PersonParentFamiliesComponent', () => {
  let component: PersonParentFamiliesComponent;
  let fixture: ComponentFixture<PersonParentFamiliesComponent>;

  const personServiceMock = {
    getOne: vi.fn(),
  } as unknown as PersonService;

  const userServiceMock = {
    currentUser: undefined as unknown,
  } as unknown as UserService;

  const parentMock = {
    person: createPerson(),
    lifespanDateString: vi.fn().mockReturnValue('1900-1950'),
    save: vi.fn(),
  } as any;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
    imports: [PersonParentFamiliesComponent],
    providers: [
        { provide: PersonService, useValue: personServiceMock },
        { provide: UserService, useValue: userServiceMock },
    ],
    schemas: [NO_ERRORS_SCHEMA],
}).compileComponents();

    fixture = TestBed.createComponent(PersonParentFamiliesComponent);
    component = fixture.componentInstance;
    component.dataset = 'testDataset';
    component.parent = parentMock;
    component.person = parentMock.person;
  });

  afterEach(() => {
    vi.clearAllMocks();
    userServiceMock.currentUser = undefined;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('returns surname from person', () => {
    component.person = createPerson({ surname: 'Smith' });
    expect(component.surname).toBe('Smith');
  });

  it('creates a UrlBuilder for personUB', () => {
    const builder = component.personUB();
    expect(builder.constructor.name).toBe('UrlBuilder');
    expect((builder as UrlBuilder).plusT()).toBe('/gedbrowserng/v1/dbs/testDataset/persons');
  });

  it('returns person anchor', () => {
    component.person = createPerson({ string: 'P123' });
    expect(component.personAnchor()).toBe('P123');
  });

  it('refreshPerson loads data and assigns to parent', () => {
    const updatedPerson = createPerson({ string: 'P2', surname: 'Updated' });
    (personServiceMock.getOne as any).mockReturnValue(of(updatedPerson));

    component.refreshPerson();

    expect(personServiceMock.getOne).toHaveBeenCalledWith('testDataset', 'P1');
    expect(component.parent.person).toBe(updatedPerson);
  });

  it('spouseLinked always returns false', () => {
    expect(component.spouseLinked(createPerson())).toBe(false);
  });

  it('childLinked always returns false', () => {
    expect(component.childLinked(createPerson())).toBe(false);
  });

  it('delegates lifespanDateString to parent', () => {
    expect(component.lifespanDateString()).toBe('1900-1950');
    expect(parentMock.lifespanDateString).toHaveBeenCalledTimes(1);
  });

  it('reorders items on drop and calls save', () => {
    component.person = createPerson({ famcs: ['a', 'b', 'c'] });
    const event = { previousIndex: 0, currentIndex: 2 } as CdkDragDrop<string[]>;

    component.drop(event);

    expect(component.person.famcs).toEqual(['b', 'c', 'a']);
    expect(parentMock.save).toHaveBeenCalledTimes(1);
  });

  it('save delegates to parent.save', () => {
    component.save();
    expect(parentMock.save).toHaveBeenCalledTimes(1);
  });

  it('hasSignedIn returns false when no currentUser', () => {
    userServiceMock.currentUser = undefined;
    expect(component.hasSignedIn()).toBe(false);
  });

  it('hasSignedIn returns true when currentUser exists', () => {
    userServiceMock.currentUser = { name: 'tester' } as any;
    expect(component.hasSignedIn()).toBe(true);
  });

  it('init does nothing but is callable', () => {
    expect(() => component.init()).not.toThrow();
  });
});
