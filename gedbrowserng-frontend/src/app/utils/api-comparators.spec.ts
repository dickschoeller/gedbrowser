import { ApiComparators } from './api-comparators';
import { ApiNote, ApiSource, ApiPerson, ApiSubmitter } from '../models';

describe('ApiComparators', () => {
  describe('strip', () => {
    it('should return empty string for undefined', () => {
      expect(ApiComparators.strip(undefined as any)).toBe('');
    });

    it('should remove punctuation', () => {
      const result = ApiComparators.strip('Hello, World!');
      expect(result).not.toContain(',');
      expect(result).not.toContain('!');
    });

    it('should normalize multiple spaces', () => {
      const result = ApiComparators.strip('Hello    World');
      expect(result).toBe('Hello World');
    });

    it('should trim whitespace', () => {
      const result = ApiComparators.strip('  Hello  ');
      expect(result).toBe('Hello');
    });
  });

  describe('compare', () => {
    it('should return 0 for equal values', () => {
      const result = ApiComparators.compare('John', 'A', 'John', 'A');
      expect(result).toBe(0);
    });

    it('should compare by first parameter', () => {
      const result = ApiComparators.compare('Alice', 'A', 'Bob', 'B');
      expect(result).toBeLessThan(0);
    });

    it('should use second parameter as tiebreaker', () => {
      const result = ApiComparators.compare('Same', 'A', 'Same', 'B');
      expect(result).toBeLessThan(0);
    });
  });

  describe('comparePersons', () => {
    it('should compare two persons', () => {
      const person1 = new ApiPerson();
      person1.indexName = 'Doe, John';
      person1.string = 'P001';
      
      const person2 = new ApiPerson();
      person2.indexName = 'Smith, Jane';
      person2.string = 'P002';
      
      const result = ApiComparators.comparePersons(person1, person2);
      expect(result).toBeLessThan(0);
    });
  });

  describe('compareSources', () => {
    it('should compare two sources', () => {
      const source1 = new ApiSource();
      source1.title = 'Bible';
      source1.string = 'S001';
      
      const source2 = new ApiSource();
      source2.title = 'Census';
      source2.string = 'S002';
      
      const result = ApiComparators.compareSources(source1, source2);
      expect(result).toBeLessThan(0);
    });
  });

  describe('compareSubmitters', () => {
    it('should compare two submitters', () => {
      const submitter1 = new ApiSubmitter();
      submitter1.name = 'Alice';
      submitter1.string = 'SUB001';
      
      const submitter2 = new ApiSubmitter();
      submitter2.name = 'Bob';
      submitter2.string = 'SUB002';
      
      const result = ApiComparators.compareSubmitters(submitter1, submitter2);
      expect(result).toBeLessThan(0);
    });
  });

  describe('compareNotes', () => {
    it('should compare two notes', () => {
      const note1 = new ApiNote();
      note1.tail = 'First note';
      note1.string = 'N001';
      
      const note2 = new ApiNote();
      note2.tail = 'Second note';
      note2.string = 'N002';
      
      const result = ApiComparators.compareNotes(note1, note2);
      expect(result).toBeLessThan(0);
    });
  });
});
