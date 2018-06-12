import { Component, OnInit, Input, OnChanges } from '@angular/core';

import { ApiAttribute, ApiFamily } from '../../models';
import { FamilyService } from '../../services';
import { RefreshPerson, Saveable } from '../../interfaces';

@Component({
  selector: 'app-person-parent-family',
  templateUrl: './person-parent-family.component.html',
  styleUrls: ['./person-parent-family.component.css']
})
export class PersonParentFamilyComponent implements OnInit, OnChanges {
  @Input() dataset: string;
  @Input() parent: RefreshPerson & Saveable;
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

  familyString(): string {
    return this.family.string;
  }

  refreshPerson() {
    this.parent.refreshPerson();
  }

  save() {
    this.parent.save();
  }
}
