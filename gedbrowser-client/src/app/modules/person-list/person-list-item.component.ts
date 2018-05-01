import {Component, OnInit, Input} from '@angular/core';

import {ApiPerson} from '../../models';
import {PersonService} from '../../services';
import {LifespanUtil} from '../../utils';

@Component({
  selector: 'app-person-list-item',
  templateUrl: './person-list-item.component.html',
  styleUrls: ['./person-list-item.component.css']
})
export class PersonListItemComponent implements OnInit {
  @Input() dataset: string;
  @Input() person: ApiPerson;

  constructor(private personService: PersonService) {
  }

  ngOnInit() {
  }

  lifespanYearString() {
    return new LifespanUtil(this.person.lifespan).lifespanYearString();
  }
}
