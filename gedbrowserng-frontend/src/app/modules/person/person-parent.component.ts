import { ChangeDetectorRef, Component, Inject, Input, NgZone, OnChanges, OnInit } from '@angular/core';

import { ApiAttribute } from '../../models';
import { PersonService, UserService } from '../../services';
import { HasFamily } from '../../interfaces/has-family';
import { PersonGetter } from './person-getter';
import { RefreshPerson } from '../../interfaces';
import { RouterLink } from '@angular/router';
import { MatIconButton } from '@angular/material/button';
import { MatTooltip } from '@angular/material/tooltip';
import { MatIcon } from '@angular/material/icon';

@Component({
    selector: 'app-person-parent',
    template: `@if (person) {
  <a class="name"
    [routerLink]="['/' + dataset + '/persons', person.string]">
    {{ person.indexName }} {{ lifespanYearString() }} [{{ person.string }}]
  </a>
}
@if (person && hasSignedIn()) {
  <span class="hidden">
    <button mat-icon-button matTooltip="Unlink parent" color="warn" (click)="unlink()">
      <mat-icon matListIcon>link_off</mat-icon>
    </button>
  </span>
}`,
    styles: [`
:host {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  min-width: 0;
}

:host .name {
  display: inline;
  line-height: 24px;
  text-decoration: none;
}

:host:hover .hidden {
  visibility: visible;
}

:host:focus-within .hidden {
  visibility: visible;
}
`],
    imports: [RouterLink, MatIconButton, MatTooltip, MatIcon]
})
export class PersonParentComponent extends PersonGetter implements OnInit, OnChanges {
  @Input() dataset: string;
  @Input() parent: HasFamily & RefreshPerson;
  @Input() attribute: ApiAttribute;

  constructor(@Inject(PersonService) personService: PersonService,
    @Inject(UserService) private readonly userService: UserService,
    @Inject(NgZone) zone: NgZone,
    @Inject(ChangeDetectorRef) cdr: ChangeDetectorRef) {
    super(personService, zone, cdr);
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

  refreshPerson() {
    this.parent.refreshPerson();
  }

  hasSignedIn() {
    return !!this.userService.currentUser;
  }
}
