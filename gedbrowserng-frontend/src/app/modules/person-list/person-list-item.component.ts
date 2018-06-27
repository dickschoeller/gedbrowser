import { RefreshPerson } from '../../interfaces';
import { Component, Input } from '@angular/core';

import { ApiPerson } from '../../models';
import { PersonService } from '../../services';
import { LifespanUtil } from '../../utils';

@Component({
  selector: 'app-person-list-item',
  templateUrl: './person-list-item.component.html',
  styleUrls: ['./person-list-item.component.css']
})
export class PersonListItemComponent {
  @Input() dataset: string;
  @Input() parent: RefreshPerson;
  @Input() person: ApiPerson;

  constructor(private personService: PersonService) {
  }

  lifespanYearString() {
    return new LifespanUtil(this.person.lifespan).lifespanYearString();
  }

  delete() {
    this.personService.delete(this.dataset, this.person).subscribe((person: ApiPerson) => {
      this.parent.refreshPerson();
    });
  }
}
