import { Component, OnInit, Input, OnChanges , Inject } from '@angular/core';
import { CdkDragDrop, moveItemInArray } from '@angular/cdk/drag-drop';

import { InitablePersonCreator } from '../../bases';
import { ApiAttribute, ApiFamily, ApiPerson, LinkPersonDialogData } from '../../models';
import { FamilyService, PersonService, UserService } from '../../services';
import { RefreshPerson, Saveable, HasPerson, HasFamily } from '../../interfaces';
import { UrlBuilder } from '../../utils';

@Component({
    standalone: false,
    selector: 'app-person-parent-family',
    template: `<mat-card>
  <mat-card-title>
    <mat-toolbar color="primary">
      <mat-toolbar-row *ngFor="let spouse of family?.spouses">
        <app-person-parent [dataset]="dataset" [parent]="this"
            [attribute]="spouse"></app-person-parent>
        <span class="example-fill-remaining-space"></span>
        <span *ngIf="family?.spouses.length < 2">
          <app-new-person
              [sex]="sex" [surname]="surname" [label]="'Create parent'"
              (emitOK)="createPerson($event)"></app-new-person>
          <app-link-person
              [parent]="this" [dataset]="dataset" [multi]="false" [label]="'Link parent'"
              (emitOK)="linkPerson($event)"></app-link-person>
        </span>
      </mat-toolbar-row>
      <mat-toolbar-row *ngIf="family?.spouses.length == 0">
          <app-new-person
              [sex]="sex" [surname]="surname" [label]="'Create parent'"
              (emitOK)="createPerson($event)"></app-new-person>
          <app-link-person
              [parent]="this" [dataset]="dataset" [multi]="false" [label]="'Link parent'"
              (emitOK)="linkPerson($event)"></app-link-person>
      </mat-toolbar-row>
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
          <div cdkDrag class="{{ hasSignedIn() ? 'child-box' : ''"
              *ngFor="let child of family?.children; let i=index">
            <app-person-family-child [dataset]="dataset" [parent]="this"
                [child]="child" [index]="i"></app-person-family-child>
          </div>
        </div>
      </mat-card-content>
    </mat-card>
  </div>
  <div class="ui-g-1"></div>
</div>
  </mat-card-content>
</mat-card>`,
    styles: []
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

    constructor(@Inject(PersonService) @Inject(PersonService) @Inject(PersonService) @Inject(PersonService) public personService: PersonService,
        @Inject(FamilyService) @Inject(FamilyService) @Inject(FamilyService) private service: FamilyService,
        @Inject(UserService) @Inject(UserService) @Inject(UserService) private userService: UserService) {
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
