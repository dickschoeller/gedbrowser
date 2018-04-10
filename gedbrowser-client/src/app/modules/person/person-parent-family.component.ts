import {Component, OnInit, Input} from '@angular/core';

import {ApiAttribute, ApiFamily} from '../../models';
import {FamilyService} from '../../services';

@Component({
  selector: 'app-person-parent-family',
  templateUrl: './person-parent-family.component.html',
  styleUrls: ['./person-parent-family.component.css']
})
export class PersonParentFamilyComponent implements OnInit {
  @Input() attribute: ApiAttribute;
  family: ApiFamily;
  initialized = false;

  constructor(
    private service: FamilyService,
  ) { }

  ngOnInit() {
    this.service.getOne('schoeller', this.attribute.string)
      .subscribe((family: ApiFamily) => {
        this.family = family;
        this.initialized = true;
    });
  }
}
