import { Component, OnInit, Input, OnChanges , Inject } from '@angular/core';

import { ApiAttribute, ApiPerson } from '../../models';
import { PersonService } from '../../services';
import { HasFamily } from '../../interfaces/has-family';
import { PersonGetter } from './person-getter';
import { RefreshPerson } from '../../interfaces';
import { RouterLink } from '@angular/router';
import { MatIconButton } from '@angular/material/button';
import { MatTooltip } from '@angular/material/tooltip';
import { MatIcon } from '@angular/material/icon';

@Component({
    selector: 'app-person-parent',
    template: `{{ label() }}:&nbsp;
@if (person) {
  <span><a class="name"
    [routerLink]="['/' + dataset + '/persons', person.string]">
    {{ person.indexName }} {{ lifespanYearString() }} [{{ person.string }}]</a>
  <span class="example-fill-remaining-space"></span>
  <span class="hidden">
    <button mat-icon-button matTooltip="Unlink parent" color="warn" (click)="unlink()">
      <mat-icon matListIcon>link_off</mat-icon></button>
  </span>
  </span>
}`,
    styles: [],
    imports: [RouterLink, MatIconButton, MatTooltip, MatIcon]
})
export class PersonParentComponent extends PersonGetter implements OnInit, OnChanges {
  @Input() dataset: string;
  @Input() parent: HasFamily & RefreshPerson;
  @Input() attribute: ApiAttribute;

  constructor(@Inject(PersonService) personService: PersonService) {
    super(personService);
    this.famMemberType = 'spouses';
  }

  ngOnInit() {
    this.init(this.dataset, this.attribute.string);
  }

  ngOnChanges() {
    this.init(this.dataset, this.attribute.string);
  }

  label(): string {
    if (this.attribute.type === 'wife') {
      return 'Mother';
    }
    if (this.attribute.type === 'husband') {
      return 'Father';
    }
    return 'Parent';
  }

  familyString(): string {
    return this.parent.familyString();
  }

  refreshPerson() {
    this.parent.refreshPerson();
  }
}
