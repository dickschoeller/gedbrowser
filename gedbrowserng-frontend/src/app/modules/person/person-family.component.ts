import { Component, Input , Inject } from '@angular/core';

import { ApiAttribute, ApiFamily, ApiPerson, AttributeDialogData, SelectItem } from '../../models';
import { FamilyService, PersonService, UserService } from '../../services';
import { UrlBuilder, NewPersonHelper } from '../../utils';
import { InitablePersonCreator } from '../../bases';
import { HasAttributeList, HasPerson, RefreshPerson, LinkCheck, Saveable } from '../../interfaces';

/**
 * Implements a family block within a person page.
 *
 * Inputs:
 *  string: the ID string of the family
 *  person: the person object of the containing block
 *  index: the index of the family in the order list for the containing person
 *
 * Fetches:
 *  family the family identified by the ID
 */
@Component({
    standalone: false,
    selector: 'app-person-family',
    template: `<mat-card>
  <mat-card-title>
    <mat-toolbar color="primary">Family {{ index + 1 }}<span *ngIf="spouse()">&nbsp;-&nbsp;</span>
      <app-person-family-spouse *ngIf="spouse()"
          [dataset]="dataset" [parent]="this" [attribute]="spouse()"></app-person-family-spouse>
      <span *ngIf="spouse() && hasSignedIn()">
        <button mat-icon-button matTooltip="Unlink self from family" color="accent" (click)="unlink()">
          <mat-icon matListIcon>link_off</mat-icon></button>
      </span>
      <span *ngIf="!spouse()" class="example-fill-remaining-space"></span>
      <span *ngIf="!spouse() && hasSignedIn()">
        <app-new-person
            [sex]="sex" [surname]="surname" [label]="'Create spouse'"
            (emitOK)="createPerson($event)"></app-new-person>
        <app-link-person
            [parent]="this" [dataset]="dataset" [multi]="false" [label]="'Link parent'"
            (emitOK)="linkPerson($event)"></app-link-person>
        <button mat-icon-button matTooltip="Unlink self from family" color="accent" (click)="unlink()">
          <mat-icon matListIcon>link_off</mat-icon></button>
      </span>
    </mat-toolbar>
  </mat-card-title>
  <mat-card-content>
  <div class="ui-g">
    <div class="ui-g-1"></div>
    <div class="ui-g-10">
      <app-attribute-list [dataset]="dataset" [parent]="this" [attributes]="family?.attributes"
          [styleClass]="'ui-panel-titlebar-success'"></app-attribute-list>
    </div>
    <div class="ui-g-1"></div>
  </div>
  <div class="ui-g">
    <div class="ui-g-1"></div>
    <div class="ui-g-10">
      <app-multimedia-gallery [dataset]="dataset" [parent]="this" [multimedia]="family?.images"
          [styleClass]="'ui-panel-titlebar-success'"></app-multimedia-gallery>
    </div>
    <div class="ui-g-1"></div>
  </div>
  <app-person-family-child-list *ngIf="family?.children"
      [children]="family?.children" [family]="family" [parent]="this"
      [dataset]="dataset"></app-person-family-child-list>
  </mat-card-content>
</mat-card>
<br/>`,
    styles: []
})
export class PersonFamilyComponent extends InitablePersonCreator
    implements HasAttributeList, LinkCheck, Saveable {
    @Input() dataset: string;
    @Input() parent: HasPerson & RefreshPerson;
    @Input() string: string;
    @Input() index: number;
    @Input() person: ApiPerson;

    family: ApiFamily;
    attributes: Array<ApiAttribute>;
    initialized = false;
    private _options: Array<SelectItem> = [
        { value: 'Annulment', label: 'Annulment' },
        { value: 'Census', label: 'Census' },
        { value: 'Children Count', label: 'Children Count' },
        { value: 'Divorce', label: 'Divorce' },
        { value: 'Divorce Filed', label: 'Divorce Filed' },
        { value: 'Event', label: 'Event' },
        { value: 'Engagement', label: 'Engagement' },
        { value: 'Marriage', label: 'Marriage' },
        { value: 'Marriage Bann', label: 'Marriage Bann' },
        { value: 'Marriage Contract', label: 'Marriage Contract' },
        { value: 'Marriage License', label: 'Marriage License' },
        { value: 'Marriage Settlement', label: 'Marriage Settlement' },
        { value: 'Note', label: 'Note' },
        { value: 'Multimedia', label: 'Multimedia' },
        { value: 'Residence', label: 'Residence' },
        { value: 'Restriction', label: 'Restriction' },
        { value: 'Sealing Child', label: 'Sealing Child' },
        { value: 'Sealing Spouse', label: 'Sealing Spouse' },
        { value: 'Source', label: 'Source' },
    ];
    sex: string;
    surname: string;

    constructor(@Inject(FamilyService) private familyService: FamilyService,
        @Inject(PersonService) public personService: PersonService,
        @Inject(UserService) private userService: UserService) {
        super(personService);
    }

    init(): void {
        this.familyService.getOne(this.dataset, this.string)
            .subscribe((family: ApiFamily) => {
                this.family = family;
                this.attributes = family.attributes;
                this.initialized = true;
            });
        this.surname = '?';
        this.sex = NewPersonHelper.guessPartnerSex(this.person);
    }

    familyString() {
        return this.family.string;
    }

    spouse(): ApiAttribute {
        if (this.family === undefined) {
            return null;
        }
        for (const attribute of this.family.spouses) {
            if (!this.isThisPerson(attribute)) {
                return attribute;
            }
        }
        return null;
    }

    private isThisPerson(attribute: ApiAttribute): boolean {
        return (attribute.string === this.person.string);
    }

    personUB(): UrlBuilder {
        return new UrlBuilder(this.dataset, 'families', 'spouses');
    }

    personAnchor() {
        return this.family.string;
    }

    refreshPerson() {
        this.ngOnInit();
    }

    save() {
        this.familyService.put(this.dataset, this.family).subscribe(
            (data: ApiFamily) => this.family = data);
    }

    options(): Array<SelectItem> {
        return this._options;
    }

    defaultData(): AttributeDialogData {
        return {
            insert: true, index: 0, type: 'Marriage', text: '', date: '',
            place: '', note: '', originalType: '', originalText: '',
            originalDate: '', originalPlace: '', originalNote: ''
        };
    }

    unlink(): void {
        const ub: UrlBuilder = new UrlBuilder(this.dataset, 'families', 'spouses');
        this.personService.deleteLink(ub, this.family.string, this.person)
            .subscribe((data: ApiPerson) => { this.parent.refreshPerson(); });
    }

    spouseLinked(person: ApiPerson): boolean {
        return false;
    }

    childLinked(person: ApiPerson): boolean {
        return false;
    }

    hasSignedIn() {
        return !!this.userService.currentUser;
    }
}
