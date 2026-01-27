import { describe, it, expect } from 'vitest';
import { of } from 'rxjs';

import { PersonCreator } from './person-creator';
import { UrlBuilder } from '../utils';
import { ApiPerson, NewPersonDialogData, LinkPersonDialogData } from '../models';

class StubPersonService {
  posted = false;
  linked = false;
  postLink(ub: UrlBuilder, anchor: string, person: ApiPerson) {
    this.posted = true;
    return of(new ApiPerson());
  }
  putLink(ub: UrlBuilder, anchor: string, person: ApiPerson) {
    this.linked = true;
    return of(new ApiPerson());
  }
}

class TestPersonCreator extends PersonCreator {
  refreshed = false;
  constructor(public personService: StubPersonService) { super(personService as any); }
  personUB(): UrlBuilder { return new UrlBuilder('ds', 'persons'); }
  personAnchor(): string { return 'P1'; }
  refreshPerson(): void { this.refreshed = true; }
}

describe('PersonCreator', () => {
  it('does nothing when createPerson called with null', () => {
    const svc = new StubPersonService();
    const creator = new TestPersonCreator(svc);
    creator.createPerson(null as unknown as NewPersonDialogData);
    expect(svc.posted).toBe(false);
    expect(creator.refreshed).toBe(false);
  });

  it('creates person and refreshes when valid data provided', () => {
    const svc = new StubPersonService();
    const creator = new TestPersonCreator(svc);
    const data: NewPersonDialogData = {
      sex: 'M', name: 'John/Smith/', birthDate: '', birthPlace: '', deathDate: '', deathPlace: ''
    };
    creator.createPerson(data);
    expect(svc.posted).toBe(true);
    expect(creator.refreshed).toBe(true);
  });

  it('links person and refreshes', () => {
    const svc = new StubPersonService();
    const creator = new TestPersonCreator(svc);
    const data: LinkPersonDialogData = {
      selectOne: { person: new ApiPerson() }, selected: []
    } as any;
    creator.linkPerson(data);
    expect(svc.linked).toBe(true);
    expect(creator.refreshed).toBe(true);
  });
});
