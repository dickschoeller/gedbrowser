import {Component, OnInit, Input} from '@angular/core';
import {PersonService, ApiPerson, ApiObject} from '../shared';

@Component({
  selector: 'app-person-list-item',
  templateUrl: './person-list-item.component.html',
  styleUrls: ['./person-list-item.component.css']
})
export class PersonListItemComponent implements OnInit {
  @Input() person: ApiPerson;

  constructor(private personService: PersonService) {
  }

  ngOnInit() {
  }

  onClick = function() {
      alert(this.person.indexName
        + this.lfString()
        + ' [' + this.person.string + ']');
  };

  lfString = function() {
    const p: ApiPerson = this.person;
    if (p.lifespan.birthDate || p.lifespan.deathDate) {
        return ' (' + p.lifespan.birthDate + '-' + p.lifespan.deathDate + ')';
    } else {
        return '';
    }
  };
}
