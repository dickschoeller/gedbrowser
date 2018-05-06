import {Component, OnInit, Input, OnChanges} from '@angular/core';

import {ApiAttribute, ApiFamily} from '../../models';
import {FamilyService} from '../../services';

@Component({
  selector: 'app-person-parent-family',
  templateUrl: './person-parent-family.component.html',
  styleUrls: ['./person-parent-family.component.css']
})
export class PersonParentFamilyComponent implements OnInit, OnChanges {
  @Input() dataset: string;
  @Input() attribute: ApiAttribute;
  family: ApiFamily;
  initialized = false;

  constructor(
    private service: FamilyService,
  ) { }

  ngOnInit() {
    this.init();
  }

  ngOnChanges() {
    this.init();
  }

  private init(): void {
    this.service.getOne(this.dataset, this.attribute.string)
      .subscribe((family: ApiFamily) => {
        this.family = family;
        this.initialized = true;
      });
  }
}
