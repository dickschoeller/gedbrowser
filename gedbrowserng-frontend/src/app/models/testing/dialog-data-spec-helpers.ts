import { describe, it, expect } from 'vitest';

/**
 * Shared test helpers for dialog data model specs.
 * Reduces duplication across NewNoteDialogData, NewSourceDialogData, NewSubmitterDialogData tests.
 */

/**
 * Common test for dialog data model instantiation.
 */
export function describeDialogDataInstantiation(modelName: string, modelClass: any): void {
  it('should create an instance', () => {
    const data = new modelClass();
    expect(data).toBeTruthy();
  });
}

/**
 * Test for text property with empty and multiline support.
 */
export function describeTextProperty(modelClass: any): void {
  it('should allow setting text', () => {
    const data = new modelClass();
    data.text = 'This is a note';
    expect(data.text).toBe('This is a note');
  });

  it('should handle empty text', () => {
    const data = new modelClass();
    data.text = '';
    expect(data.text).toBe('');
  });

  it('should handle multiline text', () => {
    const data = new modelClass();
    data.text = 'Line 1\\nLine 2\\nLine 3';
    expect(data.text).toBe('Line 1\\nLine 2\\nLine 3');
  });
}

/**
 * Test for name property with empty and long name support.
 */
export function describeNameProperty(modelClass: any): void {
  it('should allow setting name', () => {
    const data = new modelClass();
    data.name = 'John Submitter';
    expect(data.name).toBe('John Submitter');
  });

  it('should handle empty name', () => {
    const data = new modelClass();
    data.name = '';
    expect(data.name).toBe('');
  });

  it('should handle long names', () => {
    const data = new modelClass();
    data.name = 'A Very Long Submitter Name With Many Words';
    expect(data.name).toBe('A Very Long Submitter Name With Many Words');
  });
}

/**
 * Test for source-specific properties (title, abbreviation, text).
 */
export function describeSourceProperties(modelClass: any): void {
  it('should allow setting title', () => {
    const data = new modelClass();
    data.title = 'Source Title';
    expect(data.title).toBe('Source Title');
  });

  it('should allow setting abbreviation', () => {
    const data = new modelClass();
    data.abbreviation = 'ST';
    expect(data.abbreviation).toBe('ST');
  });

  it('should allow setting text', () => {
    const data = new modelClass();
    data.text = 'Source text content';
    expect(data.text).toBe('Source text content');
  });

  it('should allow setting all properties at once', () => {
    const data = new modelClass();
    data.title = 'Census Record';
    data.abbreviation = 'CENSUS';
    data.text = '1900 US Census';

    expect(data.title).toBe('Census Record');
    expect(data.abbreviation).toBe('CENSUS');
    expect(data.text).toBe('1900 US Census');
  });
}
