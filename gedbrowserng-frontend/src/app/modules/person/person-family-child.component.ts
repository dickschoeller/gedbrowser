import { Component, OnInit, Input, OnChanges , Inject } from '@angular/core';

import { ApiAttribute, ApiPerson } from '../../models';
import { PersonService, UserService } from '../../services';
import { HasFamily } from '../../interfaces/has-family';
import { HasPerson } from '../../interfaces/has-person';
import { PersonGetter } from './person-getter';
import { RefreshPerson } from '../../interfaces';

/**
 * Implements a child block within a family on a person page.
 *
 * Inputs:
 *  child: the attribute referring to the child
 *
 * Fetches:
 *  person: the person identified by the attribute
 */
@Component({
    standalone: false,
    selector: 'app-person-family-child',
    template: `<div class="parent">
  <span *ngIf="person">
    <a [routerLink]="['/' + dataset + '/persons', person.string]" class="name">{{ person.indexName }} {{ lifespanYearString() }} [{{ person.string }}]</a>
  </span>
  <span class="example-fill-remaining-space"></span>
  <span class="hidden" *ngIf="hasSignedIn()">
    <button mat-icon-button matTooltip="Unlink" color="warn" (click)="unlink()">
      <mat-icon matListIcon>link_off</mat-icon></button>
  </span>
</div>`,
    styles: []
})
export class PersonFamilyChildComponent extends PersonGetter
    implements OnInit, OnChanges, HasPerson {
    @Input() dataset: string;
    @Input() parent: HasFamily & RefreshPerson;
    @Input() child: ApiAttribute;
    @Input() index: number;

    constructor(@Inject(PersonService) personService: PersonService,
        @Inject(UserService) private userService: UserService) {
        super(personService);
        this.famMemberType = 'children';
    }

    ngOnInit() {
        this.init(this.dataset, this.child.string);
    }

    ngOnChanges() {
        this.init(this.dataset, this.child.string);
    }

    first(): boolean {
        return this.index === 0;
    }

    last(): boolean {
        return this.index + 1 >= this.parent.family.children.length;
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
