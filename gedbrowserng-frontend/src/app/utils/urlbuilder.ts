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
      return this.plusId(id) + '/' + id2;
    } else if (id) {
      return this.plusId(id);
    }
    return this.plusT();
  }

  plusId(id: string) {
    return this.plusT() + '/' + id + '/' + this.sub;
  }

  plusT(): string {
    this.baseUrl() + '/' + this.t;
  }
}
