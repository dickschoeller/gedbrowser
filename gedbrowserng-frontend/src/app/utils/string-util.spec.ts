import { StringUtil } from './string-util';

describe('StringUtil', () => {
  describe('truncate', () => {
    it('should not truncate string shorter than length', () => {
      expect(StringUtil.truncate('hello', 10)).toBe('hello');
    });

    it('should truncate string longer than length', () => {
      expect(StringUtil.truncate('hello world', 5)).toBe('hello...');
    });

    it('should trim string before truncating', () => {
      expect(StringUtil.truncate('  hello world  ', 5)).toBe('hello...');
    });

    it('should handle exact length', () => {
      expect(StringUtil.truncate('hello', 5)).toBe('hello');
    });

    it('should handle empty string', () => {
      expect(StringUtil.truncate('', 5)).toBe('');
    });

    it('should handle whitespace-only string', () => {
      expect(StringUtil.truncate('   ', 5)).toBe('');
    });
  });

  describe('titleCase', () => {
    it('should capitalize first word', () => {
      expect(StringUtil.titleCase('hello world')).toContain('Hello');
    });

    it('should capitalize last word', () => {
      expect(StringUtil.titleCase('hello world')).toContain('World');
    });

    it('should handle lowercase articles', () => {
      const result = StringUtil.titleCase('the united states of america');
      expect(result).toContain('The');
      expect(result).toContain('United');
    });

    it('should preserve all caps states', () => {
      expect(StringUtil.titleCase('california')).toContain('California');
    });

    it('should handle single word', () => {
      expect(StringUtil.titleCase('hello')).toBe('Hello');
    });

    it('should handle multiple words', () => {
      const result = StringUtil.titleCase('john michael smith');
      expect(result.split(' ').length).toBe(3);
    });
  });

  describe('capitalize', () => {
    it('should capitalize first character', () => {
      expect(StringUtil.capitalize('hello')).toBe('Hello');
    });

    it('should lowercase rest of string', () => {
      expect(StringUtil.capitalize('HELLO')).toBe('Hello');
    });

    it('should handle already capitalized', () => {
      expect(StringUtil.capitalize('Hello')).toBe('Hello');
    });

    it('should handle single character', () => {
      expect(StringUtil.capitalize('a')).toBe('A');
    });

    it('should handle empty string', () => {
      expect(StringUtil.capitalize('')).toBe('');
    });
  });

  describe('replaceAll', () => {
    it('should replace all occurrences', () => {
      expect(StringUtil.replaceAll('hello world hello', 'hello', 'hi')).toBe('hi world hi');
    });

    it('should handle no matches', () => {
      expect(StringUtil.replaceAll('hello world', 'foo', 'bar')).toBe('hello world');
    });

    it('should handle single match', () => {
      expect(StringUtil.replaceAll('hello world', 'hello', 'goodbye')).toBe('goodbye world');
    });

    it('should handle empty replacement', () => {
      expect(StringUtil.replaceAll('hello world', 'o', '')).toBe('hell wrld');
    });

    it('should handle empty search', () => {
      const result = StringUtil.replaceAll('hello', '', 'x');
      expect(result).toBeDefined();
    });
  });

  describe('isEmpty', () => {
    it('should return true for null', () => {
      expect(StringUtil.isEmpty(null)).toBe(true);
    });

    it('should return true for undefined', () => {
      expect(StringUtil.isEmpty(undefined)).toBe(true);
    });

    it('should return true for empty string', () => {
      expect(StringUtil.isEmpty('')).toBe(true);
    });

    it('should return false for non-empty string', () => {
      expect(StringUtil.isEmpty('hello')).toBe(false);
    });

    it('should return false for whitespace string', () => {
      expect(StringUtil.isEmpty('  ')).toBe(false);
    });
  });
});
