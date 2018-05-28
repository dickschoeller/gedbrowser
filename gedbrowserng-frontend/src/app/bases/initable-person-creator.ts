import {OnInit, OnChanges} from '@angular/core';

import {NewPersonHelper} from '../utils';
import {NewPersonLinkService} from '../services';
import {PersonCreator} from './person-creator';

export abstract class InitablePersonCreator extends PersonCreator implements OnInit, OnChanges {
  nph = new NewPersonHelper();

  constructor(public newPersonLinkService: NewPersonLinkService) {
    super(newPersonLinkService);
  }

  ngOnInit(): void {
    this.init();
  }

  ngOnChanges(): void {
    this.init();
  }

  abstract init(): void;
}
