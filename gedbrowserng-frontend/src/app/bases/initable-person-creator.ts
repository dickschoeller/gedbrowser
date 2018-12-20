import { OnInit, OnChanges } from '@angular/core';

import { NewPersonHelper } from '../utils';
import { PersonService } from '../services';
import { PersonCreator } from './person-creator';

export abstract class InitablePersonCreator extends PersonCreator implements OnInit, OnChanges {
  nph = new NewPersonHelper();

  constructor(public personService: PersonService) {
    super(personService);
  }

  ngOnInit(): void {
    this.init();
  }

  ngOnChanges(): void {
    this.init();
  }

  abstract init(): void;
}
