import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import {TestBed} from '@angular/core/testing';
import { vi } from 'vitest';

import {PersonResolverService} from './person-resolver.service';
import { PersonService } from '../../services';

describe('PersonResolverService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ HttpClientTestingModule, RouterTestingModule ],
      providers: [PersonResolverService, PersonService]
    });
  });

  it('should be created', () => {
    const service: PersonResolverService = TestBed.inject(PersonResolverService);
    expect(service).toBeTruthy();
  });

  it('resolve delegates to rh.resolve', () => {
    const service: PersonResolverService = TestBed.inject(PersonResolverService);
    const route = { params: { dataset: 'ds', id: 'P1' } } as any;
    const state = { url: '/ds/persons/P1' } as any;
    const spy = vi.spyOn(service.rh, 'resolve');
    service.resolve(route, state);
    expect(spy).toHaveBeenCalledWith(route, state, TestBed.inject(PersonService));
  });
});
