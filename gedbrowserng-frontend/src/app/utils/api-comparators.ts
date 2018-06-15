import { ApiSource, ApiPerson, ApiSubmitter } from '../models';

export class ApiComparators {
  comparePersons = function(a: ApiPerson, b: ApiPerson) {
    return this.twoPartCompare(a.indexName, a. string, b.indexName, b.string);
  };

  compareSources = function(a: ApiSource, b: ApiSource) {
    return this.twoPartCompare(a.title, a. string, b.title, b.string);
  };

  compareSubmitters = function(a: ApiSubmitter, b: ApiSubmitter) {
    return this.twoPartCompare(a.name, a.string, b.name, b.string);
  };

  private twoPartCompare(a1: string, a2: string, b1: string, b2: string): number {
    const val = a1.localeCompare(b1);
    if (val !== 0) {
      return val;
    }
    return a2.localeCompare(b2);
  }
}
