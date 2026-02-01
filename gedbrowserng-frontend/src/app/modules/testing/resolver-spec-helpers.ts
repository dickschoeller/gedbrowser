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
      const route = { params: options.routeParams || {} } as any;
      const state = { url: options.stateUrl || '' } as any;
      const spy = vi.spyOn(service.rh, 'resolve');
      service.resolve(route, state);
      expect(spy).toHaveBeenCalledWith(route, state, TestBed.inject(dataService));
    });
  }
}
