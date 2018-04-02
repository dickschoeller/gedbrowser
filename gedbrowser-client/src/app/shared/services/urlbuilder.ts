export class UrlBuilder {
  db: string;
  constructor(db: string) {
    this.db = db;
  }

  baseUrl(): string {
    return 'http://largo.schoellerfamily.org:9084/gedbrowser-api/v1/dbs/'
      + this.db;
  }

  url(t, id, sub) {
    return this.baseUrl() + '/' + t + '/' + id + '/' + sub;
  }
}
