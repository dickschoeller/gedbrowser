import { Component, Input , Inject } from '@angular/core';
import { CdkDragDrop, moveItemInArray } from '@angular/cdk/drag-drop';

import { InitablePersonCreator } from '../../bases';
import { HasPerson, LinkCheck, Saveable } from '../../interfaces';
import { ApiPerson, NewPersonDialogData, LinkPersonDialogData, LinkPersonItem } from '../../models';
import { UrlBuilder, NewPersonHelper } from '../../utils';
import { PersonService, UserService } from '../../services';

/**
 * Implements a the list of families on a person page
 *
 * Inputs:
 *  person: the person this page is for
 */
@Component({
    standalone: false,
    selector: 'app-person-family-list',
    template: `<mat-card>
  <mat-card-title>
    <mat-toolbar>Spouses and Children</mat-toolbar>
  </mat-card-title>
  <mat-card-content>
    <div cdkDropList class="family-list" (cdkDropListDropped)="drop($event)"
        [cdkDropListDisabled]="!hasSignedIn()">
      <div cdkDrag class="{{ hasSignedIn() ? 'family-box' : ''"
          *ngFor="let attribute of person.famss; let i=index">
        <app-person-family
            [dataset]="dataset" [person]="person" [parent]="this" [string]="attribute.string"
            [index]="i"></app-person-family>
      </div>
    </div>
    <app-new-person *ngIf="hasSignedIn()"
        [sex]="partnerSex" [surname]="partnerSurname" [label]="'Create spouse'"
        color="primary"
        (emitOK)="createSpouse($event)"></app-new-person>
    <app-link-person *ngIf="hasSignedIn()"
        [parent]="this" [dataset]="dataset" [multi]="false" [label]="'Link spouse'"
        color="primary"
        (emitOK)="linkSpouse($event)"></app-link-person>
    <app-new-person *ngIf="hasSignedIn()"
        [sex]="childSex" [surname]="childSurname" [label]="'Create child'"
        color="primary"
        (emitOK)="createChild($event)"></app-new-person>
    <app-link-person *ngIf="hasSignedIn()"
        [parent]="this" [dataset]="dataset" [multi]="true" [label]="'Link children'"
        color="primary"
        (emitOK)="linkChildren($event)"></app-link-person>
  </mat-card-content>
</mat-card>`,
    styles: []
})
export class PersonFamilyListComponent extends InitablePersonCreator implements LinkCheck {
    @Input() dataset: string;
    @Input() parent: HasPerson & Saveable;
    @Input() person: ApiPerson;
    partnerSurname: string;
    childSurname: string;
    partnerSex: string;
    childSex = 'M';
    _ub: UrlBuilder;

    constructor(@Inject(PersonService) @Inject(PersonService) @Inject(PersonService) @Inject(PersonService) public personService: PersonService,
        @Inject(UserService) @Inject(UserService) @Inject(UserService) private userService: UserService) {
        super(personService);
    }

    init() {
        this.partnerSex = NewPersonHelper.guessPartnerSex(this.person);
        if (this.partnerSex === 'M') {
            this.childSurname = '?';
        } else {
            this.childSurname = this.person.surname;
        }
        this.partnerSurname = '?';
    }

    personUB(): UrlBuilder {
        return this._ub;
    }

    personAnchor(): string {
        return this.person.string;
    }

    refreshPerson(): void {
        this.personService.getOne(this.dataset, this.person.string).subscribe(
            (person: ApiPerson) => this.updatePerson(person));
    }

    private updatePerson(person: ApiPerson) {
        this.parent.person = person;
    }

    spouseLinked(person: ApiPerson): boolean {
        return this.person.string === person.string;
    }

    childLinked(person: ApiPerson): boolean {
        return false;
    }

    linkChildren(data: LinkPersonDialogData) {
        this._ub = new UrlBuilder(this.dataset, 'persons', 'children');
        const selected: Array<LinkPersonItem> = data.selected.splice(0, 1);
        this.personService.putLink(this.personUB(), this.personAnchor(), selected[0].person)
            .subscribe((person: ApiPerson) => {
                this.linkChildrenToMainPerson(data);
            });
    }

    private linkChildrenToMainPerson(data: LinkPersonDialogData) {
        this.personService.getOne(this.dataset, this.person.string)
            .subscribe((mainPerson: ApiPerson) => {
                this.linkRemainingChildren(data, mainPerson);
            });
    }

    private linkRemainingChildren(data: LinkPersonDialogData, mainPerson: ApiPerson): void {
        this.updatePerson(mainPerson);
        const famss = mainPerson.famss[mainPerson.famss.length - 1];
        const ub = new UrlBuilder(this.dataset, 'families', 'children');
        for (const selected of data.selected) {
            this.personService.putLink(ub, famss.string, selected.person)
                .subscribe((p: ApiPerson) => { this.refreshPerson(); });
        }
    }

    linkSpouse(data: LinkPersonDialogData) {
        this._ub = new UrlBuilder(this.dataset, 'persons', 'spouses');
        this.linkPerson(data);
    }

    createSpouse(data: NewPersonDialogData): void {
        this._ub = new UrlBuilder(this.dataset, 'persons', 'spouses');
        this.createPerson(data);
    }

    createChild(data: NewPersonDialogData): void {
        this._ub = new UrlBuilder(this.dataset, 'persons', 'children');
        this.createPerson(data);
    }

    drop(event: CdkDragDrop<string[]>) {
        moveItemInArray(this.person.famss, event.previousIndex, event.currentIndex);
        this.parent.save();
    }

    hasSignedIn() {
        return !!this.userService.currentUser;
    }
}
