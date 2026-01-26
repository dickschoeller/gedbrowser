import { LifespanUtil } from './lifespan-util';
import { ApiLifespan } from '../models';

describe('LifespanUtil', () => {
  let lifespan: ApiLifespan;

  beforeEach(() => {
    lifespan = new ApiLifespan();
  });

  describe('lifespanDateString', () => {
    it('should return empty string when no dates', () => {
      const util = new LifespanUtil(lifespan);
      expect(util.lifespanDateString()).toBe('');
    });

    it('should return formatted string with birth date', () => {
      lifespan.birthDate = '1 JAN 1970';
      const util = new LifespanUtil(lifespan);
      expect(util.lifespanDateString()).toContain('1 JAN 1970');
    });

    it('should return formatted string with death date', () => {
      lifespan.deathDate = '1 JAN 2020';
      const util = new LifespanUtil(lifespan);
      expect(util.lifespanDateString()).toContain('1 JAN 2020');
    });

    it('should return formatted string with both dates', () => {
      lifespan.birthDate = '1 JAN 1970';
      lifespan.deathDate = '1 JAN 2020';
      const util = new LifespanUtil(lifespan);
      const result = util.lifespanDateString();
      expect(result).toContain('1 JAN 1970');
      expect(result).toContain('1 JAN 2020');
      expect(result).toContain('-');
    });

    it('should include parentheses', () => {
      lifespan.birthDate = '1 JAN 1970';
      const util = new LifespanUtil(lifespan);
      expect(util.lifespanDateString()).toMatch(/^\s*\(.*\)$/);
    });
  });

  describe('lifespanYearString', () => {
    it('should return empty string when no years', () => {
      const util = new LifespanUtil(lifespan);
      expect(util.lifespanYearString()).toBe('');
    });

    it('should return formatted string with birth year', () => {
      lifespan.birthYear = '1970';
      const util = new LifespanUtil(lifespan);
      expect(util.lifespanYearString()).toContain('1970');
    });

    it('should return formatted string with death year', () => {
      lifespan.deathYear = '2020';
      const util = new LifespanUtil(lifespan);
      expect(util.lifespanYearString()).toContain('2020');
    });

    it('should return formatted string with both years', () => {
      lifespan.birthYear = '1970';
      lifespan.deathYear = '2020';
      const util = new LifespanUtil(lifespan);
      const result = util.lifespanYearString();
      expect(result).toContain('1970');
      expect(result).toContain('2020');
      expect(result).toContain('-');
    });

    it('should include parentheses', () => {
      lifespan.birthYear = '1970';
      const util = new LifespanUtil(lifespan);
      expect(util.lifespanYearString()).toMatch(/^\s*\(.*\)$/);
    });
  });
});
