import { ChangeDetectorRef, Component, Inject, Input, NgZone, OnChanges, OnInit } from '@angular/core';

import { ApiAttribute } from '../../models';
import { PersonService, UserService } from '../../services';
import { HasFamily } from '../../interfaces/has-family';
import { HasPerson } from '../../interfaces/has-person';
import { PersonGetter } from './person-getter';
import { RefreshPerson } from '../../interfaces';
import { RouterLink } from '@angular/router';
import { MatIconButton } from '@angular/material/button';
import { MatTooltip } from '@angular/material/tooltip';
import { MatIcon } from '@angular/material/icon';

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
    selector: 'app-person-family-child',
    template: `<div class="parent custom-section-colors">
    @if (person) {
        <span class="custom-section-colors">
            <a [routerLink]="['/' + dataset + '/persons', person.string]" class="name">{{ person.indexName }} {{ lifespanYearString() }} [{{ person.string }}]</a>
        </span>
    }
    <span class="example-fill-remaining-space custom-section-colors"></span>
    @if (hasSignedIn()) {
        <span class="hidden">
            <button mat-icon-button matTooltip="Unlink" color="warn" (click)="unlink()">
                <mat-icon matListIcon>link_off</mat-icon></button>
        </span>
    }
</div>`,
    styles: [],
    imports: [RouterLink, MatIconButton, MatTooltip, MatIcon]
})
export class PersonFamilyChildComponent extends PersonGetter
    implements OnInit, OnChanges, HasPerson {
    @Input() dataset: string;
    @Input() parent: HasFamily & RefreshPerson;
    @Input() child: ApiAttribute;
    @Input() index: number;

    constructor(@Inject(PersonService) personService: PersonService,
        @Inject(UserService) private readonly userService: UserService,
        @Inject(NgZone) zone: NgZone,
        @Inject(ChangeDetectorRef) cdr: ChangeDetectorRef) {
        super(personService, zone, cdr);
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
