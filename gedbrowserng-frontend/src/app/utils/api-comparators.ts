import { ApiSource, ApiPerson, ApiSubmitter } from '../models';

export class ApiComparators {
  comparePersons = (a: ApiPerson, b: ApiPerson) => {
    return this.compare(a.indexName, a.string, b.indexName, b.string);
  }

  compareSources = (a: ApiSource, b: ApiSource) => {
    return this.compare(a.title, a.string, b.title, b.string);
  }

  compareSubmitters = (a: ApiSubmitter, b: ApiSubmitter) => {
    return this.compare(a.name, a.string, b.name, b.string);
  }

  compare = (a1: string, a2: string, b1: string, b2: string) => {
    const val = a1.localeCompare(b1);
    if (val !== 0) {
      return val;
    }
    return a2.localeCompare(b2);
  }
}
