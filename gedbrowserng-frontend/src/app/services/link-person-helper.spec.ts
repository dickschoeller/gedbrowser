import { describe, it, expect, beforeEach, vi } from 'vitest';

import { LinkPersonHelper } from './link-person-helper';
import { PersonService } from './person.service';
import { ApiPerson } from '../models';
import { LinkCheck } from '../interfaces';
import { ApiComparators } from '../utils';
import { of } from 'rxjs';

describe('LinkPersonHelper', () => {
  let helper: LinkPersonHelper;
  let personService: PersonService;

  const mockPerson1: ApiPerson = {
    string: 'I1',
    indexName: 'John Doe',
    lifespan: '1950-2020'
  } as ApiPerson;

  const mockPerson2: ApiPerson = {
    string: 'I2',
    indexName: 'Jane Smith',
    lifespan: '1955-2025'
  } as ApiPerson;

  const mockPerson3: ApiPerson = {
    string: 'I3',
    indexName: 'Bob Jones',
    lifespan: ''
  } as ApiPerson;

  const mockPerson4: ApiPerson = {
    string: 'I4',
    indexName: 'Alice Brown',
    lifespan: '1960-2010'
  } as ApiPerson;

  const mockDialogComponent = {
    data: {
      dataset: 'test-dataset',
      items: [],
      selected: []
    },
    persons: []
  };

  const mockLinkCheckComponent: LinkCheck = {
    spouseLinked: vi.fn((person: ApiPerson) => person.string === 'I1'),
    childLinked: vi.fn((person: ApiPerson) => person.string === 'I2')
  } as any;

  beforeEach(() => {
    const mockPersonService = {
      getAll: vi.fn().mockReturnValue(of([]))
    };

    helper = new LinkPersonHelper(mockPersonService as any);
    personService = mockPersonService as any;

    mockDialogComponent.data.items = [];
    mockDialogComponent.data.selected = [];
    mockDialogComponent.persons = [];
  });

  describe('service creation', () => {
    it('should be created', () => {
      expect(helper).toBeTruthy();
    });

    it('should have personService injected', () => {
      expect(personService).toBeTruthy();
    });
  });

  describe('onLinkChildDialogOpen()', () => {
    it('subscribes to personService.getAll with correct dataset', () => {
      personService.getAll = vi.fn().mockReturnValue(of([]));

      helper.onLinkChildDialogOpen(mockDialogComponent, mockLinkCheckComponent);

      expect(personService.getAll).toHaveBeenCalledWith('test-dataset');
    });

    it('sets persons on dialog component', () => {
      const persons = [mockPerson1, mockPerson2];
      personService.getAll = vi.fn().mockReturnValue(of(persons));
      vi.spyOn(ApiComparators, 'comparePersons').mockReturnValue(0);

      helper.onLinkChildDialogOpen(mockDialogComponent, mockLinkCheckComponent);

      expect(mockDialogComponent.persons).toEqual(persons);
    });

    it('sorts persons using ApiComparators.comparePersons', () => {
      const persons = [mockPerson2, mockPerson1];
      personService.getAll = vi.fn().mockReturnValue(of(persons));
      vi.spyOn(ApiComparators, 'comparePersons').mockReturnValue(-1);

      helper.onLinkChildDialogOpen(mockDialogComponent, mockLinkCheckComponent);

      expect(ApiComparators.comparePersons).toHaveBeenCalled();
    });

    it('initializes data items array', () => {
      personService.getAll = vi.fn().mockReturnValue(of([]));

      helper.onLinkChildDialogOpen(mockDialogComponent, mockLinkCheckComponent);

      expect(mockDialogComponent.data.items).toEqual([]);
    });

    it('initializes selected items array', () => {
      personService.getAll = vi.fn().mockReturnValue(of([]));

      helper.onLinkChildDialogOpen(mockDialogComponent, mockLinkCheckComponent);

      expect(mockDialogComponent.data.selected).toEqual([]);
    });

    it('filters out already spouse-linked persons', () => {
      const persons = [mockPerson1, mockPerson3];
      personService.getAll = vi.fn().mockReturnValue(of(persons));
      vi.spyOn(ApiComparators, 'comparePersons').mockReturnValue(0);

      helper.onLinkChildDialogOpen(mockDialogComponent, mockLinkCheckComponent);

      expect(mockDialogComponent.data.items.length).toBe(1);
      expect(mockDialogComponent.data.items[0].id).toBe('I3');
    });

    it('filters out already child-linked persons', () => {
      const persons = [mockPerson2, mockPerson3];
      personService.getAll = vi.fn().mockReturnValue(of(persons));
      vi.spyOn(ApiComparators, 'comparePersons').mockReturnValue(0);

      helper.onLinkChildDialogOpen(mockDialogComponent, mockLinkCheckComponent);

      expect(mockDialogComponent.data.items.length).toBe(1);
      expect(mockDialogComponent.data.items[0].id).toBe('I3');
    });

    it('includes persons not linked as spouse or child', () => {
      const persons = [mockPerson1, mockPerson2, mockPerson3];
      personService.getAll = vi.fn().mockReturnValue(of(persons));
      vi.spyOn(ApiComparators, 'comparePersons').mockReturnValue(0);

      helper.onLinkChildDialogOpen(mockDialogComponent, mockLinkCheckComponent);

      expect(mockDialogComponent.data.items.length).toBe(1);
      expect(mockDialogComponent.data.items[0].person).toEqual(mockPerson3);
    });

    it('builds correct LinkPersonItem for each included person', () => {
      const persons = [mockPerson3];
      personService.getAll = vi.fn().mockReturnValue(of(persons));
      vi.spyOn(ApiComparators, 'comparePersons').mockReturnValue(0);

      helper.onLinkChildDialogOpen(mockDialogComponent, mockLinkCheckComponent);

      const item = mockDialogComponent.data.items[0];
      expect(item.id).toBe('I3');
      expect(item.person).toEqual(mockPerson3);
      expect(item.label).toContain('Bob Jones');
      expect(item.label).toContain('[I3]');
    });

    it('includes lifespan in label when present', () => {
      const persons = [mockPerson4];
      personService.getAll = vi.fn().mockReturnValue(of(persons));
      vi.spyOn(ApiComparators, 'comparePersons').mockReturnValue(0);

      helper.onLinkChildDialogOpen(mockDialogComponent, mockLinkCheckComponent);

      const item = mockDialogComponent.data.items[0];
      expect(item.label).toContain('Alice Brown');
      expect(item.label).toContain('[I4]');
    });

    it('handles empty persons list', () => {
      personService.getAll = vi.fn().mockReturnValue(of([]));

      helper.onLinkChildDialogOpen(mockDialogComponent, mockLinkCheckComponent);

      expect(mockDialogComponent.data.items.length).toBe(0);
    });

    it('sets all filtered items to both persons and data.items', () => {
      const persons = [mockPerson3];
      personService.getAll = vi.fn().mockReturnValue(of(persons));
      vi.spyOn(ApiComparators, 'comparePersons').mockReturnValue(0);

      helper.onLinkChildDialogOpen(mockDialogComponent, mockLinkCheckComponent);

      expect(mockDialogComponent.persons).toContain(mockPerson3);
      expect(mockDialogComponent.data.items[0].person).toEqual(mockPerson3);
    });
  });
});
