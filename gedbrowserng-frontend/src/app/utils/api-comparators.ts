import { ApiNote, ApiSource, ApiPerson, ApiSubmitter } from '../models';

export class ApiComparators {
  public static comparePersons(a: ApiPerson, b: ApiPerson) {
    return this.compare(a.indexName, a.string, b.indexName, b.string);
  }

  public static compareSources(a: ApiSource, b: ApiSource) {
    return this.compare(a.title, a.string, b.title, b.string);
  }

  public static compareSubmitters(a: ApiSubmitter, b: ApiSubmitter) {
    return this.compare(a.name, a.string, b.name, b.string);
  }

  public static compareNotes(a: ApiNote, b: ApiNote) {
    return this.compare(a.tail, a.string, b.tail, b.string);
  }

  private static compare (a1: string, a2: string, b1: string, b2: string) {
    const val = this.strip(a1).localeCompare(this.strip(b1));
    if (val !== 0) {
      return val;
    }
    return this.strip(a2).localeCompare(this.strip(b2));
  }

  private static strip(a: string): string {
    if (a === undefined) {
      return '';
    }
    const punctRE = /[\u2000-\u206F\u2E00-\u2E7F\\'!"#$%&()*+,\-.\/:;<=>?@\[\]^_`{|}~]/g;
    const spaceRE = /\s+/g;
    return a.replace(punctRE, ' ').replace(/(\s){2,}/g, ' ').trim();
  }
}
