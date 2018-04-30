import {Component, OnInit, Input, OnChanges} from '@angular/core';

import {ApiAttribute, ApiPerson} from '../../models';
import {PersonService} from '../../services';
import {LifespanUtil} from '../../utils';

@Component({
  selector: 'app-person-parent',
  templateUrl: './person-parent.component.html',
  styleUrls: ['./person-parent.component.css']
})
export class PersonParentComponent implements OnInit, OnChanges {
  @Input() attribute: ApiAttribute;
  person: ApiPerson;

  constructor(
    private personService: PersonService,
  ) { }

  ngOnInit() {
    this.init();
  }

  ngOnChanges() {
    this.init();
  }

  private init(): void {
    this.personService.getOne('schoeller', this.attribute.string)
      .subscribe((person: ApiPerson) => {
        this.person = person;
      });
  }

  label(): string {
    if (this.attribute.type === 'wife') {
      return 'Mother';
    }
    if (this.attribute.type === 'husband') {
      return 'Father';
    }
    return 'Parent';
  }

  lifespanYearString(): string {
    return new LifespanUtil(this.person.lifespan).lifespanYearString();
  }
}
