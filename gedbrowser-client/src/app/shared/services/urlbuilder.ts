export class UrlBuilder {
  db: string;
  t: string;
  sub: string;
  constructor(db: string, t: string, sub: string) {
    this.db = db;
    this.t = t;
    this.sub = sub;
  }

  baseUrl(): string {
    return 'http://largo.schoellerfamily.org:9084/gedbrowser-api/v1/dbs/'
      + this.db;
  }

  url(id) {
    return this.baseUrl() + '/' + this.t + '/' + id + '/' + this.sub;
  }
}
