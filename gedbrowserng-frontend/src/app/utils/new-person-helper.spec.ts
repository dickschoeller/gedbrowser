import { describe, it, expect } from 'vitest';
import { NewPersonHelper } from './new-person-helper';
import { ApiPerson, ApiAttribute } from '../models';

type PersonData = {
  name: string;
  sex: string;
  birthDate: string;
  birthPlace: string;
  deathDate: string;
  deathPlace: string;
};

const makePersonData = (overrides: Partial<PersonData> = {}): PersonData => ({
  name: '',
  sex: 'M',
  birthDate: '',
  birthPlace: '',
  deathDate: '',
  deathPlace: '',
  ...overrides
});

const findEventAttribute = (person: ApiPerson, label: string) =>
  person.attributes.find((a) => a.type === 'attribute' && a.string === label);

const findChildAttribute = (parent: ApiAttribute | undefined, type: string) =>
  parent?.attributes?.find((a) => a.type === type);

describe('NewPersonHelper', () => {
  describe('buildPerson', () => {
    it('creates a person with all provided data', () => {
      const data = makePersonData({
        name: 'John/Smith/',
        sex: 'M',
        birthDate: '1980-01-15',
        birthPlace: 'New York',
        deathDate: '2050-05-20',
        deathPlace: 'Boston'
      });

      const person = NewPersonHelper.buildPerson(data);

      expect(person).toBeInstanceOf(ApiPerson);
      expect(person.attributes).toBeDefined();
      expect(person.attributes.length).toBeGreaterThan(0);
      
      // Check name
      const nameAttr = person.attributes.find(a => a.type === 'name');
      expect(nameAttr).toBeDefined();
      expect(nameAttr?.string).toBe('John/Smith/');
      
      // Check sex
      const sexAttr = findEventAttribute(person, 'Sex');
      expect(sexAttr).toBeDefined();
      expect(sexAttr?.tail).toBe('M');
      
      // Check birth
      const birthAttr = findEventAttribute(person, 'Birth');
      expect(birthAttr).toBeDefined();
      const birthDateAttr = findChildAttribute(birthAttr, 'date');
      expect(birthDateAttr?.string).toBe('1980-01-15');
      const birthPlaceAttr = findChildAttribute(birthAttr, 'place');
      expect(birthPlaceAttr?.string).toBe('New York');
      
      // Check death
      const deathAttr = findEventAttribute(person, 'Death');
      expect(deathAttr).toBeDefined();
      const deathDateAttr = findChildAttribute(deathAttr, 'date');
      expect(deathDateAttr?.string).toBe('2050-05-20');
      const deathPlaceAttr = findChildAttribute(deathAttr, 'place');
      expect(deathPlaceAttr?.string).toBe('Boston');
    });

    it.each([
      ['M', 'Anonymous'],
      ['F', 'Anonyma']
    ])('uses default name when name is empty for %s', (sex, expectedName) => {
      const person = NewPersonHelper.buildPerson(makePersonData({ sex, name: '' }));
      const nameAttr = person.attributes.find((a) => a.type === 'name');
      expect(nameAttr?.string).toBe(expectedName);
    });

    it('skips birth when date and place are empty', () => {
      const person = NewPersonHelper.buildPerson(
        makePersonData({ name: 'Jane/Doe/', sex: 'F' })
      );
      const birthAttr = findEventAttribute(person, 'Birth');
      expect(birthAttr).toBeUndefined();
    });

    it.each([
      ['date', { birthDate: '1990-05-10', birthPlace: '' }, '1990-05-10'],
      ['place', { birthDate: '', birthPlace: 'Paris' }, 'Paris']
    ])('includes birth when only %s is provided', (type, overrides, expected) => {
      const person = NewPersonHelper.buildPerson(
        makePersonData({ name: 'Jane/Doe/', sex: 'F', ...overrides })
      );
      const birthAttr = findEventAttribute(person, 'Birth');
      expect(birthAttr).toBeDefined();
      const childAttr = findChildAttribute(birthAttr, type);
      expect(childAttr?.string).toBe(expected);
    });

    it('skips death when date and place are empty', () => {
      const person = NewPersonHelper.buildPerson(
        makePersonData({
          name: 'Bob/Brown/',
          sex: 'M',
          birthDate: '1950-01-01',
          birthPlace: 'London'
        })
      );
      const deathAttr = findEventAttribute(person, 'Death');
      expect(deathAttr).toBeUndefined();
    });

    it.each([
      ['date', { deathDate: '2020-12-25', deathPlace: '' }, '2020-12-25'],
      ['place', { deathDate: '', deathPlace: 'Chicago' }, 'Chicago']
    ])('includes death when only %s is provided', (type, overrides, expected) => {
      const person = NewPersonHelper.buildPerson(
        makePersonData({ name: 'Bob/Brown/', sex: 'M', ...overrides })
      );
      const deathAttr = findEventAttribute(person, 'Death');
      expect(deathAttr).toBeDefined();
      const childAttr = findChildAttribute(deathAttr, type);
      expect(childAttr?.string).toBe(expected);
    });
  });

  describe('config', () => {
    it('wraps data in config object', () => {
      const input = { name: 'Test', sex: 'M' };
      const result = NewPersonHelper.config(input);
      expect(result).toEqual({ data: input });
    });
  });

  describe('initNew', () => {
    it('initializes new person data for male', () => {
      const result = NewPersonHelper.initNew('M', 'Johnson');
      expect(result).toEqual({
        sex: 'M',
        name: 'Anonymous/Johnson/',
        birthDate: '',
        birthPlace: '',
        deathDate: '',
        deathPlace: ''
      });
    });

    it('initializes new person data for female', () => {
      const result = NewPersonHelper.initNew('F', 'Williams');
      expect(result).toEqual({
        sex: 'F',
        name: 'Anonyma/Williams/',
        birthDate: '',
        birthPlace: '',
        deathDate: '',
        deathPlace: ''
      });
    });
  });

  describe('guessPartnerSex', () => {
    it('returns opposite sex for male person', () => {
      const person = new ApiPerson();
      person.attributes = [
        { string: 'Name', tail: 'John/Doe/' } as ApiAttribute,
        { string: 'Sex', tail: 'M' } as ApiAttribute
      ];

      const result = NewPersonHelper.guessPartnerSex(person);
      expect(result).toBe('F');
    });

    it('returns opposite sex for female person', () => {
      const person = new ApiPerson();
      person.attributes = [
        { string: 'Name', tail: 'Jane/Doe/' } as ApiAttribute,
        { string: 'Sex', tail: 'F' } as ApiAttribute
      ];

      const result = NewPersonHelper.guessPartnerSex(person);
      expect(result).toBe('M');
    });

    it('defaults to M when no Sex attribute found', () => {
      const person = new ApiPerson();
      person.attributes = [
        { string: 'Name', tail: 'Unknown/Person/' } as ApiAttribute
      ];

      const result = NewPersonHelper.guessPartnerSex(person);
      expect(result).toBe('M');
    });

    it('returns F for unknown or unspecified sex', () => {
      const person = new ApiPerson();
      person.attributes = [
        { string: 'Sex', tail: 'U' } as ApiAttribute
      ];

      const result = NewPersonHelper.guessPartnerSex(person);
      expect(result).toBe('F');
    });
  });
});
