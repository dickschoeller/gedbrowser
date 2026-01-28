import { describe, it, expect } from 'vitest';
import { serialize } from './serialize';

describe('serialize', () => {
  it('returns empty params for null or undefined', () => {
    expect(serialize(null)).toEqual({});
    expect(serialize(undefined)).toEqual({});
  });

  it('serializes simple string values', () => {
    expect(serialize({ key: 'value' })).toEqual({ key: 'value' });
    expect(serialize({ name: 'test', id: '123' })).toEqual({ name: 'test', id: '123' });
  });

  it('serializes number values as strings', () => {
    expect(serialize({ count: 42 })).toEqual({ count: '42' });
    expect(serialize({ pi: 3.14 })).toEqual({ pi: '3.14' });
  });

  it('serializes Date values as ISO strings', () => {
    const date = new Date('2024-01-15T10:30:00Z');
    const result = serialize({ timestamp: date });
    expect(result.timestamp).toBe(date.toISOString());
  });

  it('serializes object values as JSON strings', () => {
    const obj = { nested: { value: 'test' } };
    const result = serialize({ data: obj });
    expect(result.data).toBe(JSON.stringify(obj));
  });

  it('handles JSON.stringify errors gracefully', () => {
    const circular: any = { a: 1 };
    circular.self = circular;
    const result = serialize({ circular });
    expect(typeof result.circular).toBe('string');
  });

  it('filters out null, undefined, and empty strings', () => {
    const result = serialize({
      valid: 'keep',
      nullValue: null,
      undefinedValue: undefined,
      emptyValue: '',
      another: 'keep'
    });
    expect(result).toEqual({ valid: 'keep', another: 'keep' });
  });

  it('serializes arrays without invalid values', () => {
    const result = serialize({ list: ['a', null, 'b', undefined, 'c', ''] });
    expect(result.list).toEqual(['a', 'b', 'c']);
  });

  it('omits arrays that become empty after filtering', () => {
    const result = serialize({ empty: [null, undefined, ''] });
    expect(result.empty).toBeUndefined();
  });

  it('serializes mixed array types', () => {
    const date = new Date('2024-01-01');
    const result = serialize({ mixed: ['text', 123, date, { obj: 'val' }] });
    expect(result.mixed).toEqual([
      'text',
      '123',
      date.toISOString(),
      JSON.stringify({ obj: 'val' })
    ]);
  });

  it('ignores prototype properties', () => {
    function TestClass() {
      this.own = 'value';
    }
    TestClass.prototype.inherited = 'ignored';
    const instance = new TestClass();
    const result = serialize(instance);
    expect(result).toEqual({ own: 'value' });
  });

  it('handles boolean values', () => {
    const result = serialize({ flag: true, disabled: false });
    expect(result).toEqual({ flag: 'true', disabled: 'false' });
  });

  it('filters out empty strings', () => {
    const result = serialize({ empty: '', name: 'test' });
    expect(result).toEqual({ name: 'test' });
  });

  it('handles zero values', () => {
    const result = serialize({ count: 0, index: 0 });
    expect(result).toEqual({ count: '0', index: '0' });
  });
});
