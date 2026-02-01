import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { it, expect, beforeEach, vi } from 'vitest';

/**
 * Shared test helpers for resolver services.
 * Reduces duplication across person, source, note, submitter, head, and list resolver specs.
 */

/**
 * Setup function for resolver service tests.
 * Configures TestBed with common imports and providers.
 */
export function setupResolverTest(
  resolverService: any,
  dataService: any
): void {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule],
      providers: [resolverService, dataService]
    });
  });
}

/**
 * Generates common test suite for resolver services.
 * Includes creation test and optional resolve delegation test.
 * 
 * @param resolverServiceName - Name of the resolver service (for error messages)
 * @param resolverService - The resolver service class to test
 * @param dataService - The data service class used by the resolver
 * @param options - Configuration options for the test suite
 * @param options.testResolve - If true, tests that resolve() delegates to rh.resolve()
 * @param options.routeParams - Mock route parameters for resolve test
 * @param options.stateUrl - Mock state URL for resolve test
 * 
 * @remarks
 * This helper assumes that resolver services follow the standard pattern:
 * - Must have an 'rh' property that is a ResolverHelper instance
 * - The 'rh' property must have a 'resolve' method
 * If your resolver doesn't follow this pattern, a runtime error will be thrown.
 */
export function describeResolverTests(
  resolverServiceName: string,
  resolverService: any,
  dataService: any,
  options: {
    testResolve?: boolean;
    routeParams?: { [key: string]: string };
    stateUrl?: string;
  } = {}
): void {
  it(`${resolverServiceName} should be created`, () => {
    const service = TestBed.inject(resolverService);
    expect(service).toBeTruthy();
  });

  if (options.testResolve) {
    it(`${resolverServiceName} resolve delegates to rh.resolve`, () => {
      const service = TestBed.inject(resolverService) as any;
      
      // Runtime check: Ensure the service follows the expected pattern
      if (!service.rh || typeof service.rh.resolve !== 'function') {
        throw new Error(
          `${resolverServiceName} does not follow the expected pattern. ` +
          `Expected service to have an 'rh' property with a 'resolve' method.`
        );
      }
      
      const route = { params: options.routeParams || {} } as any;
      const state = { url: options.stateUrl || '' } as any;
      const spy = vi.spyOn(service.rh, 'resolve');
      service.resolve(route, state);
      expect(spy).toHaveBeenCalledWith(route, state, TestBed.inject(dataService));
    });
  }
}
