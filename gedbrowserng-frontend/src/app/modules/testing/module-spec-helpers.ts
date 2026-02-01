import { TestBed } from '@angular/core/testing';
import { describe, it, expect } from 'vitest';

/**
 * Shared test helper for Angular module specs.
 * Reduces duplication across module instantiation tests.
 */

/**
 * Generates standard module instantiation test.
 * 
 * @param moduleName - Name of the module for display purposes
 * @param moduleClass - The module class to test
 */
export function describeModuleTest(moduleName: string, moduleClass: any): void {
  describe(moduleName, () => {
    it('should instantiate', () => {
      const module = TestBed.configureTestingModule({
        imports: [moduleClass]
      }).inject(moduleClass);
      expect(module).toBeTruthy();
    });
  });
}
