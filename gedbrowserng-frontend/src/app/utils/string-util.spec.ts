import { StringUtil } from './string-util';

describe('StringUtil', () => {
  describe('truncate', () => {
    it.each([
      ['shorter than length', 'hello', 10, 'hello'],
      ['longer than length', 'hello world', 5, 'hello...'],
      ['with surrounding whitespace', '  hello world  ', 5, 'hello...'],
      ['exact length', 'hello', 5, 'hello'],
      ['empty string', '', 5, ''],
      ['whitespace-only string', '   ', 5, '']
    ])('should handle %s', (scenario, input, length, expected) => {
      expect(StringUtil.truncate(input, length)).toBe(expected);
    });
  });

  describe('titleCase', () => {
    it.each([
      ['hello world', ['Hello', 'World']],
      ['the united states of america', ['The', 'United']],
      ['california', ['California']]
    ])('should titleCase %s', (input, expectedParts) => {
      const result = StringUtil.titleCase(input);
      expectedParts.forEach((part) => expect(result).toContain(part));
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
    it.each([
      ['hello', 'Hello'],
      ['HELLO', 'Hello'],
      ['Hello', 'Hello'],
      ['a', 'A'],
      ['', '']
    ])('should capitalize %s', (input, expected) => {
      expect(StringUtil.capitalize(input)).toBe(expected);
    });
  });

  describe('replaceAll', () => {
    it.each([
      ['hello world hello', 'hello', 'hi', 'hi world hi'],
      ['hello world', 'foo', 'bar', 'hello world'],
      ['hello world', 'hello', 'goodbye', 'goodbye world'],
      ['hello world', 'o', '', 'hell wrld']
    ])('should replace all for %s', (input, search, replacement, expected) => {
      expect(StringUtil.replaceAll(input, search, replacement)).toBe(expected);
    });

    it('should handle empty search', () => {
      const result = StringUtil.replaceAll('hello', '', 'x');
      expect(result).toBeDefined();
    });
  });

  describe('isEmpty', () => {
    it.each([
      [null, true],
      [undefined, true],
      ['', true],
      ['hello', false],
      ['  ', false]
    ])('should return %s for %s', (input, expected) => {
      expect(StringUtil.isEmpty(input as string | null | undefined)).toBe(expected);
    });
  });
});
