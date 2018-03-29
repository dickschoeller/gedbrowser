export class UrlBuilder {
  db: string;
  constructor(db: string) {
    this.db = db;
  }

  baseUrl(): string {
    return 'http://largo.schoellerfamily.org:9084/gedbrowser-api/v1/dbs/'
      + this.db;
  }

  typeUrl(t: string, id: string): string {
    return this.baseUrl() + '/' + t + '/' + id;
  }

  spousesUrl(t: string, id: string): string {
    return this.typeUrl(t, id) + '/spouses';
  }

  childrenUrl(t: string, id: string): string {
    return this.typeUrl(t, id) + '/children';
  }
}
