import {Component, OnInit, Input} from '@angular/core';
import {ApiAttribute, ApiFamily, FamilyService} from '../shared';

@Component({
  selector: 'app-person-parent-family',
  templateUrl: './person-parent-family.component.html',
  styleUrls: ['./person-parent-family.component.css']
})
export class PersonParentFamilyComponent implements OnInit {
  @Input() attribute: ApiAttribute;
  family: ApiFamily;

  constructor(
    private service: FamilyService,
  ) { }

  ngOnInit() {
    this.service.getOne('schoeller', this.attribute.string)
      .subscribe((family: ApiFamily) => {
        this.family = family;
    });
  }

  spouses(): Array<ApiAttribute> {
    const spouses: Array<ApiAttribute> = new Array<ApiAttribute>();
    if (this.family === undefined) {
      return spouses;
    }
    for (const attribute of this.family.attributes) {
      if (attribute.type === 'husband' || attribute.type === 'wife') {
        spouses.push(attribute);
      }
    }
    return spouses;

  }
}
