import { Component, OnInit, Input, OnChanges , Inject } from '@angular/core';

import { ApiAttribute, ApiPerson } from '../../models';
import { PersonService, UserService } from '../../services';
import { HasFamily } from '../../interfaces/has-family';
import { PersonGetter } from './person-getter';
import { RefreshPerson } from '../../interfaces';
import { NgIf } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MatIconButton } from '@angular/material/button';
import { MatTooltip } from '@angular/material/tooltip';
import { MatIcon } from '@angular/material/icon';

/**
 * Implements a spouse block within a family on a person page.
 *
 * Inputs:
 *  attribute: the attribute referring to the spouse
 *
 * Fetches:
 *  person: the person identified by the attribute
 */
@Component({
    selector: 'app-person-family-spouse',
    template: `<span *ngIf="person" class="parent"><a
    [routerLink]="['/' + dataset + '/persons', person.string]"
    class="name">{{ person.indexName }} {{ lifespanYearString() }} [{{ person.string }}]</a>
</span>
<span *ngIf="person" class="example-fill-remaining-space"></span>
<span *ngIf="hasSignedIn()">
  <button mat-icon-button matTooltip="Unlink spouse" color="warn" (click)="unlink()">
    <mat-icon matListIcon>link_off</mat-icon></button>
</span>`,
    styles: [],
    imports: [NgIf, RouterLink, MatIconButton, MatTooltip, MatIcon]
})
export class PersonFamilySpouseComponent extends PersonGetter
    implements OnInit, OnChanges {
    @Input() dataset: string;
    @Input() parent: RefreshPerson & HasFamily;
    @Input() attribute: ApiAttribute;

    constructor(@Inject(PersonService) personService: PersonService,
        @Inject(UserService) private userService: UserService) {
        super(personService);
        this.famMemberType = 'spouses';
    }

    ngOnInit() {
        this.init(this.dataset, this.attribute.string);
    }

    ngOnChanges() {
        this.init(this.dataset, this.attribute.string);
    }

    familyString(): string {
        return this.parent.familyString();
    }

    refreshPerson(): void {
        this.parent.refreshPerson();
    }

    hasSignedIn() {
        return !!this.userService.currentUser;
    }
}
