import { Component, Input , Inject } from '@angular/core';
import { CdkDragDrop, moveItemInArray, CdkDropList, CdkDrag } from '@angular/cdk/drag-drop';

import { InitablePersonCreator } from '../../bases';
import { HasLifespan, HasPerson, Saveable, RefreshPerson } from '../../interfaces';
import { ApiPerson } from '../../models';
import { PersonService, UserService } from '../../services';
import { UrlBuilder } from '../../utils';
import { MatCard, MatCardTitle, MatCardContent } from '@angular/material/card';
import { MatToolbar } from '@angular/material/toolbar';
import { NewPersonComponent } from './new-person.component';
import { LinkPersonComponent } from './link-person.component';
import { PersonParentFamilyComponent } from './person-parent-family.component';

@Component({
    standalone: true,
    selector: 'app-person-parent-families',
    template: `<mat-card>
  <mat-card-title>
    <mat-toolbar>
    Parents and Siblings
    <span class="example-fill-remaining-space"></span>
    @if (!person.famcs.length && hasSignedIn()) {
      <span>
        <app-new-person
            [sex]="sex" [surname]="surname" [label]="'Create parent'"
            color="primary"
            (emitOK)="createPerson($event)"></app-new-person>
        <app-link-person
            [parent]="this" [dataset]="dataset" [multi]="false" [label]="'Link parent'"
            color="primary"
            (emitOK)="linkPerson($event)"></app-link-person>
      </span>
    }
    </mat-toolbar>
  </mat-card-title>
  <mat-card-content>
    <div cdkDropList class="family-list" (cdkDropListDropped)="drop($event)"
        [cdkDropListDisabled]="!hasSignedIn()">
      @for (attribute of person?.famcs; track $index; let i = $index) {
        <div cdkDrag class="{{ hasSignedIn() ? 'family-box' : '' }}">
          <app-person-parent-family
              [dataset]="dataset" [parent]="this" [attribute]="attribute"></app-person-parent-family>
        </div>
      }
    </div>
  </mat-card-content>
</mat-card>`,
    styles: [],
    imports: [MatCard, MatCardTitle, MatToolbar, NewPersonComponent, LinkPersonComponent, MatCardContent, CdkDropList, CdkDrag, PersonParentFamilyComponent]
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

  constructor(@Inject(PersonService) public personService: PersonService,
    @Inject(UserService) private userService: UserService) {
    super(personService);
  }

  init(): void {
    // Empty
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
    moveItemInArray(this.person.famcs, event.previousIndex, event.currentIndex);
    this.parent.save();
  }

  save(): void {
    this.parent.save();
  }

    hasSignedIn() {
        return !!this.userService.currentUser;
    }
}
