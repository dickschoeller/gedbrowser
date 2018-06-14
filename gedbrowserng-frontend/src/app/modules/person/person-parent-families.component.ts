import { Component, Input } from '@angular/core';

import { InitablePersonCreator } from '../../bases';
import { HasLifespan, HasPerson, Saveable, RefreshPerson } from '../../interfaces';
import { ApiAttribute, ApiPerson, ApiFamily, LinkPersonDialogData } from '../../models';
import { PersonService, NewPersonLinkService } from '../../services';
import { UrlBuilder } from '../../utils';

@Component({
  selector: 'app-person-parent-families',
  templateUrl: './person-parent-families.component.html',
  styleUrls: ['./person-parent-families.component.css']
})
export class PersonParentFamiliesComponent extends InitablePersonCreator
  implements HasLifespan, HasPerson, RefreshPerson, Saveable {
  @Input() dataset: string;
  @Input() parent: HasPerson & HasLifespan & Saveable;
  get person(): ApiPerson {
    return this.parent.person;
  }
  sex = 'M';
  get surname(): string {
    return this.person.surname;
  }

  constructor(private personService: PersonService,
    newPersonLinkService: NewPersonLinkService) {
    super(newPersonLinkService);
  }

  init(): void {
  }

  personUB(): UrlBuilder {
    return new UrlBuilder(this.dataset, 'persons', 'parents');
  }

  personAnchor () {
    return this.person.string;
  }

  refreshPerson() {
    this.personService.getOne(this.dataset, this.person.string).subscribe(
      (data: ApiPerson) => this.parent.person = data);
  }

  spouseLinked(person: ApiPerson): boolean {
//    for (const spouse of this.family.spouses) {
//      if (spouse.string === person.string) {
//        return true;
//      }
//    }
    return false;
  }

  childLinked(person: ApiPerson): boolean {
//    for (const child of this.children) {
//      if (child.string === person.string) {
//        return true;
//      }
//    }
    return false;
  }

  lifespanDateString(): string {
    return this.parent.lifespanDateString();
  }

  save(): void {
    this.parent.save();
  }
}
