import { describe, it, expect } from 'vitest';
import { NewPersonHelper } from './new-person-helper';
import { ApiPerson, ApiAttribute } from '../models';

describe('NewPersonHelper', () => {
  describe('buildPerson', () => {
    it('creates a person with all provided data', () => {
      const data = {
        name: 'John/Smith/',
        sex: 'M',
        birthDate: '1980-01-15',
        birthPlace: 'New York',
        deathDate: '2050-05-20',
        deathPlace: 'Boston'
      };

      const person = NewPersonHelper.buildPerson(data);

      expect(person).toBeInstanceOf(ApiPerson);
      expect(person.attributes).toBeDefined();
      expect(person.attributes.length).toBeGreaterThan(0);
      
      // Check name
      const nameAttr = person.attributes.find(a => a.type === 'name');
      expect(nameAttr).toBeDefined();
      expect(nameAttr?.string).toBe('John/Smith/');
      
      // Check sex
      const sexAttr = person.attributes.find(a => a.type === 'attribute' && a.string === 'Sex');
      expect(sexAttr).toBeDefined();
      expect(sexAttr?.tail).toBe('M');
      
      // Check birth
      const birthAttr = person.attributes.find(a => a.type === 'attribute' && a.string === 'Birth');
      expect(birthAttr).toBeDefined();
      const birthDateAttr = birthAttr?.attributes?.find(a => a.type === 'date');
      expect(birthDateAttr?.string).toBe('1980-01-15');
      const birthPlaceAttr = birthAttr?.attributes?.find(a => a.type === 'place');
      expect(birthPlaceAttr?.string).toBe('New York');
      
      // Check death
      const deathAttr = person.attributes.find(a => a.type === 'attribute' && a.string === 'Death');
      expect(deathAttr).toBeDefined();
      const deathDateAttr = deathAttr?.attributes?.find(a => a.type === 'date');
      expect(deathDateAttr?.string).toBe('2050-05-20');
      const deathPlaceAttr = deathAttr?.attributes?.find(a => a.type === 'place');
      expect(deathPlaceAttr?.string).toBe('Boston');
    });

    it('uses default name when name is empty for male', () => {
      const data = {
        name: '',
        sex: 'M',
        birthDate: '',
        birthPlace: '',
        deathDate: '',
        deathPlace: ''
      };

      const person = NewPersonHelper.buildPerson(data);
      const nameAttr = person.attributes.find(a => a.type === 'name');
      expect(nameAttr?.string).toBe('Anonymous');
    });

    it('uses default name when name is empty for female', () => {
      const data = {
        name: '',
        sex: 'F',
        birthDate: '',
        birthPlace: '',
        deathDate: '',
        deathPlace: ''
      };

      const person = NewPersonHelper.buildPerson(data);
      const nameAttr = person.attributes.find(a => a.type === 'name');
      expect(nameAttr?.string).toBe('Anonyma');
    });

    it('skips birth when date and place are empty', () => {
      const data = {
        name: 'Jane/Doe/',
        sex: 'F',
        birthDate: '',
        birthPlace: '',
        deathDate: '',
        deathPlace: ''
      };

      const person = NewPersonHelper.buildPerson(data);
      const birthAttr = person.attributes.find(a => a.type === 'attribute' && a.string === 'Birth');
      expect(birthAttr).toBeUndefined();
    });

    it('includes birth when only date is provided', () => {
      const data = {
        name: 'Jane/Doe/',
        sex: 'F',
        birthDate: '1990-05-10',
        birthPlace: '',
        deathDate: '',
        deathPlace: ''
      };

      const person = NewPersonHelper.buildPerson(data);
      const birthAttr = person.attributes.find(a => a.type === 'attribute' && a.string === 'Birth');
      expect(birthAttr).toBeDefined();
      const birthDateAttr = birthAttr?.attributes?.find(a => a.type === 'date');
      expect(birthDateAttr?.string).toBe('1990-05-10');
    });

    it('includes birth when only place is provided', () => {
      const data = {
        name: 'Jane/Doe/',
        sex: 'F',
        birthDate: '',
        birthPlace: 'Paris',
        deathDate: '',
        deathPlace: ''
      };

      const person = NewPersonHelper.buildPerson(data);
      const birthAttr = person.attributes.find(a => a.type === 'attribute' && a.string === 'Birth');
      expect(birthAttr).toBeDefined();
      const birthPlaceAttr = birthAttr?.attributes?.find(a => a.type === 'place');
      expect(birthPlaceAttr?.string).toBe('Paris');
    });

    it('skips death when date and place are empty', () => {
      const data = {
        name: 'Bob/Brown/',
        sex: 'M',
        birthDate: '1950-01-01',
        birthPlace: 'London',
        deathDate: '',
        deathPlace: ''
      };

      const person = NewPersonHelper.buildPerson(data);
      const deathAttr = person.attributes.find(a => a.type === 'attribute' && a.string === 'Death');
      expect(deathAttr).toBeUndefined();
    });

    it('includes death when only date is provided', () => {
      const data = {
        name: 'Bob/Brown/',
        sex: 'M',
        birthDate: '',
        birthPlace: '',
        deathDate: '2020-12-25',
        deathPlace: ''
      };

      const person = NewPersonHelper.buildPerson(data);
      const deathAttr = person.attributes.find(a => a.type === 'attribute' && a.string === 'Death');
      expect(deathAttr).toBeDefined();
      const deathDateAttr = deathAttr?.attributes?.find(a => a.type === 'date');
      expect(deathDateAttr?.string).toBe('2020-12-25');
    });

    it('includes death when only place is provided', () => {
      const data = {
        name: 'Bob/Brown/',
        sex: 'M',
        birthDate: '',
        birthPlace: '',
        deathDate: '',
        deathPlace: 'Chicago'
      };

      const person = NewPersonHelper.buildPerson(data);
      const deathAttr = person.attributes.find(a => a.type === 'attribute' && a.string === 'Death');
      expect(deathAttr).toBeDefined();
      const deathPlaceAttr = deathAttr?.attributes?.find(a => a.type === 'place');
      expect(deathPlaceAttr?.string).toBe('Chicago');
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
