import { describe, it, expect } from 'vitest';

/**
 * Shared test helpers for API model specs.
 * Reduces duplication across ApiNote, ApiSource, ApiSubmitter, ApiSubmission, ApiTail tests.
 */

/**
 * Common test for model instantiation.
 */
export function describeModelInstantiation(modelName: string, modelClass: any): void {
  it(`should create an ${modelName} instance`, () => {
    const instance = new modelClass();
    expect(instance).toBeTruthy();
  });
}

/**
 * Test that model inherits from ApiObject.
 */
export function describeApiObjectInheritance(modelClass: any): void {
  it('should inherit from ApiObject', () => {
    const instance = new modelClass();
    instance.string = 'TEST001';
    expect(instance.string).toBe('TEST001');
  });
}

/**
 * Test for models with tail property (ApiTail descendants).
 */
export function describeApiTailInheritance(modelClass: any, propertyName: string = 'tail'): void {
  it(`should inherit from ApiTail`, () => {
    const instance = new modelClass();
    instance.string = 'N001';
    instance[propertyName] = 'test content';
    expect(instance.string).toBe('N001');
    expect(instance[propertyName]).toBe('test content');
  });
}

/**
 * Test for string property initialization.
 */
export function describeStringProperty(
  modelClass: any,
  propertyName: string,
  defaultValue: string = ''
): void {
  it(`should initialize ${propertyName} as ${defaultValue === '' ? 'empty string' : defaultValue}`, () => {
    const instance = new modelClass();
    expect(instance[propertyName]).toBe(defaultValue);
  });

  it(`should allow setting ${propertyName}`, () => {
    const instance = new modelClass();
    instance[propertyName] = 'Test Value';
    expect(instance[propertyName]).toBe('Test Value');
  });
}

/**
 * Test for array property initialization.
 */
export function describeArrayProperty(modelClass: any, propertyName: string): void {
  it(`should initialize ${propertyName} array`, () => {
    const instance = new modelClass();
    expect(instance[propertyName]).toEqual([]);
    expect(Array.isArray(instance[propertyName])).toBe(true);
  });

  it(`should allow adding items to ${propertyName}`, () => {
    const instance = new modelClass();
    const item = { string: 'item1', tail: 'content', attributes: [] };
    instance[propertyName].push(item as any);
    expect(instance[propertyName].length).toBe(1);
    expect(instance[propertyName][0]).toEqual(item);
  });
}

/**
 * Test for multiple instances.
 */
export function describeMultipleInstances(modelClass: any, propertyName: string): void {
  it('should support multiple instances', () => {
    const instance1 = new modelClass();
    const instance2 = new modelClass();
    instance1[propertyName] = 'Value 1';
    instance2[propertyName] = 'Value 2';
    expect(instance1[propertyName]).toBe('Value 1');
    expect(instance2[propertyName]).toBe('Value 2');
  });
}
