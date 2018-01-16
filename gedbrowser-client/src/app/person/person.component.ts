import { Component, OnInit, } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { ApiPerson, PersonService } from '../shared';

@Component({
  selector: 'app-person',
  templateUrl: './person.component.html',
  styleUrls: ['./person.component.css']
})
export class PersonComponent implements OnInit {
  person: ApiPerson;

  constructor(private route: ActivatedRoute,
    private personService: PersonService,
    private router: Router
  ) {}

  ngOnInit() {
    this.route.data.subscribe(
      (data: {person: ApiPerson}) => {
        this.person = data.person;
      }
    );
  }

  lfString = function() {
    const p: ApiPerson = this.person;
    if (p.lifespan.birthDate || p.lifespan.deathDate) {
        return ' (' + p.lifespan.birthDate + '-' + p.lifespan.deathDate + ')';
    } else {
        return '';
    }
  };
}
