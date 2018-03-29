export class UrlBuilder {
  db: string;
  constructor(db: string) {
    this.db = db;
  }

  baseUrl(): string {
    return 'http://largo.schoellerfamily.org:9084/gedbrowser-api/v1/dbs/'
      + this.db;
  }

  typeUrl(t, id) {
    return this.baseUrl() + '/' + t + '/' + id;
  }

  spousesUrl(t, id) {
    return this.typeUrl(t, id) + '/spouses';
  }

  parentsUrl(t, id) {
    return this.typeUrl(t, id) + '/parents';
  }

  childrenUrl(t, id) {
    return this.typeUrl(t, id) + '/children';
  }
}
