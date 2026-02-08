import { Component, OnInit, Input, OnChanges , Inject } from '@angular/core';
import { CdkDragDrop, moveItemInArray, CdkDropList, CdkDrag } from '@angular/cdk/drag-drop';

import { InitablePersonCreator } from '../../bases';
import { ApiAttribute, ApiFamily, ApiPerson, LinkPersonDialogData } from '../../models';
import { FamilyService, PersonService, UserService } from '../../services';
import { RefreshPerson, Saveable, HasPerson, HasFamily } from '../../interfaces';
import { UrlBuilder } from '../../utils';
import { MatCard, MatCardTitle, MatCardContent } from '@angular/material/card';
import { MatToolbar, MatToolbarRow } from '@angular/material/toolbar';
import { PersonParentComponent } from './person-parent.component';
import { NewPersonComponent } from './new-person.component';
import { LinkPersonComponent } from './link-person.component';
import { PersonFamilyChildComponent } from './person-family-child.component';

@Component({
    standalone: true,
    selector: 'app-person-parent-family',
    template: `<mat-card>
  <mat-card-title>
    <mat-toolbar color="primary">
      @for (spouse of family?.spouses; track $index) {
        <mat-toolbar-row>
          <app-person-parent [dataset]="dataset" [parent]="this"
              [attribute]="spouse"></app-person-parent>
          <span class="example-fill-remaining-space"></span>
          @if (family?.spouses.length < 2) {
            <span>
              <app-new-person
                  [sex]="sex" [surname]="surname" [label]="'Create parent'"
                  (emitOK)="createPerson($event)"></app-new-person>
              <app-link-person
                  [parent]="this" [dataset]="dataset" [multi]="false" [label]="'Link parent'"
                  (emitOK)="linkPerson($event)"></app-link-person>
            </span>
          }
        </mat-toolbar-row>
      }
      @if (family?.spouses.length == 0) {
        <mat-toolbar-row>
            <app-new-person
                [sex]="sex" [surname]="surname" [label]="'Create parent'"
                (emitOK)="createPerson($event)"></app-new-person>
            <app-link-person
                [parent]="this" [dataset]="dataset" [multi]="false" [label]="'Link parent'"
                (emitOK)="linkPerson($event)"></app-link-person>
        </mat-toolbar-row>
      }
    </mat-toolbar>
  </mat-card-title>
  <mat-card-content>
<div class="ui-g">
  <div class="ui-g-1"></div>
  <div class="ui-g-10">
    <mat-card>
      <mat-card-title>
        <mat-toolbar>
          Siblings (including self)
        </mat-toolbar>
      </mat-card-title>
      <mat-card-content>
        <div cdkDropList class="child-list" (cdkDropListDropped)="drop($event)"
            [cdkDropListDisabled]="!hasSignedIn()">
          @for (child of family?.children; track $index; let i = $index) {
            <div cdkDrag class="{{ hasSignedIn() ? 'child-box' : '' }}">
              <app-person-family-child [dataset]="dataset" [parent]="this"
                  [child]="child" [index]="i"></app-person-family-child>
            </div>
          }
        </div>
      </mat-card-content>
    </mat-card>
  </div>
  <div class="ui-g-1"></div>
</div>
  </mat-card-content>
</mat-card>`,
    styles: [],
    imports: [MatCard, MatCardTitle, MatToolbar, MatToolbarRow, PersonParentComponent, NewPersonComponent, LinkPersonComponent, MatCardContent, CdkDropList, CdkDrag, PersonFamilyChildComponent]
})
export class PersonParentFamilyComponent extends InitablePersonCreator
    implements OnInit, OnChanges, HasFamily, RefreshPerson {
    @Input() dataset: string;
    @Input() parent: RefreshPerson & Saveable & HasPerson;
    @Input() attribute: ApiAttribute;

    family: ApiFamily;
    initialized = false;
    sex = 'M';
    get surname(): string {
        return this.parent.person.surname;
    }

    constructor(@Inject(PersonService) public readonly personService: PersonService,
        @Inject(FamilyService) private readonly service: FamilyService,
        @Inject(UserService) private readonly userService: UserService) {
        super(personService);
    }

    init(): void {
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

    personUB(): UrlBuilder {
        return new UrlBuilder(this.dataset, 'families', 'spouses');
    }

    personAnchor(): string {
        return this.family.string;
    }

    preferredSurname(): string {
        return this.surname;
    }

    childLinked(): boolean {
        return false;
    }

    spouseLinked(): boolean {
        return false;
    }

    drop(event: CdkDragDrop<string[]>) {
        moveItemInArray(this.family.children, event.previousIndex, event.currentIndex);
        this.service.put(this.dataset, this.family)
            .subscribe((family: ApiFamily) => { this.family = family; });
    }

    hasSignedIn() {
        return !!this.userService.currentUser;
    }
}
