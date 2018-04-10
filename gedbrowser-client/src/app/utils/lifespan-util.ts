import {ApiLifespan} from '../models';

export class LifespanUtil {
  constructor(private lifespan: ApiLifespan) {}

  lifespanDateString() {
    if (this.lifespan.birthDate || this.lifespan.deathDate) {
      return ' (' + this.lifespan.birthDate + '-' + this.lifespan.deathDate + ')';
    } else {
      return '';
    }
  }

  lifespanYearString() {
    if (this.lifespan.birthYear || this.lifespan.deathYear) {
      return ' (' + this.lifespan.birthYear + '-' + this.lifespan.deathYear + ')';
    } else {
      return '';
    }
  }
}
