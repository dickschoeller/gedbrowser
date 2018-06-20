import { ApiSource, ApiPerson, ApiSubmitter } from '../models';

export class ApiComparators {
  comparePersons(a: ApiPerson, b: ApiPerson) {
    const val = a.indexName.localeCompare(b.indexName);
    if (val !== 0) {
      return val;
    }
    return a.string.localeCompare(b.string);
  }

  compareSources(a: ApiSource, b: ApiSource) {
    const val = a.title.localeCompare(b.title);
    if (val !== 0) {
      return val;
    }
    return a.string.localeCompare(b.string);
  }

  compareSubmitters(a: ApiSubmitter, b: ApiSubmitter) {
    const val = a.name.localeCompare(b.name);
    if (val !== 0) {
      return val;
    }
    return a.string.localeCompare(b.string);
  }
}
