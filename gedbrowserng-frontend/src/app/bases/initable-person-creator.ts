import { Component, OnInit, OnChanges } from '@angular/core';

import { NewPersonHelper } from '../utils';
import { PersonService } from '../services';
import { PersonCreator } from './person-creator';

@Component({
  standalone: true,
  template: ''
})
export abstract class InitablePersonCreator extends PersonCreator implements OnInit, OnChanges {
  nph = new NewPersonHelper();

  constructor(public readonly personService: PersonService) {
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
