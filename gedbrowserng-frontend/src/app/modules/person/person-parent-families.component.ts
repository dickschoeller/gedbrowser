import { Component, Input } from '@angular/core';
import { CdkDragDrop, moveItemInArray } from '@angular/cdk/drag-drop';

import { InitablePersonCreator } from '../../bases';
import { HasLifespan, HasPerson, Saveable, RefreshPerson } from '../../interfaces';
import { ApiPerson } from '../../models';
import { PersonService, UserService } from '../../services';
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
  @Input() person: ApiPerson;
  sex = 'M';
  get surname(): string {
    return this.person.surname;
  }

  constructor(public personService: PersonService,
    private userService: UserService) {
    super(personService);
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

  drop(event: CdkDragDrop<string[]>) {
    moveItemInArray(this.person.famc, event.previousIndex, event.currentIndex);
    this.parent.save();
  }

  save(): void {
    this.parent.save();
  }

    hasSignedIn() {
        return !!this.userService.currentUser;
    }
}
