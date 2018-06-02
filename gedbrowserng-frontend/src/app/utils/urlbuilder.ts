export class UrlBuilder {
  db: string;
  t: string;
  sub: string;
  constructor(db: string, t: string, sub?: string) {
    this.db = db;
    this.t = t;
    if (sub) {
      this.sub = sub;
    }
  }

  baseUrl(): string {
    return '/gedbrowserng/v1/dbs/' + this.db;
  }

  url(id?: string, id2?: string) {
    if (id2) {
      return this.baseUrl() + '/' + this.t + '/' + id + '/' + this.sub + '/' + id2;
    } else if (id) {
      return this.baseUrl() + '/' + this.t + '/' + id + '/' + this.sub;
    }
    return this.baseUrl() + '/' + this.t;
  }
}
