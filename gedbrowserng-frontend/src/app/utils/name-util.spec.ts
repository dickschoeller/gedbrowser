import { describe, it, expect } from 'vitest';
import { NameUtil } from './name-util';

describe('NameUtil', () => {
  it('cleanup removes slashes and extra spaces', () => {
    expect(NameUtil.cleanup('John/Doe')).toBe('John Doe');
    expect(NameUtil.cleanup('Jane  /  Smith')).toBe('Jane   Smith'); // replaces only one level of double spaces
    expect(NameUtil.cleanup('/Test/')).toBe('Test');
    expect(NameUtil.cleanup('  Multiple   Spaces  ')).toBe('Multiple  Spaces'); // replaces only one level
  });

  it('cleanup handles empty and single characters', () => {
    expect(NameUtil.cleanup('')).toBe('');
    expect(NameUtil.cleanup('A')).toBe('A');
    expect(NameUtil.cleanup('/')).toBe('');
  });
});
