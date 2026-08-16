import { ChangeDetectorRef, Component, Inject, Input, NgZone } from '@angular/core';

import { ApiAttribute, ApiFamily, ApiPerson, AttributeDialogData, SelectItem } from '../../models';
import { FamilyService, PersonService, UserService } from '../../services';
import { UrlBuilder, NewPersonHelper } from '../../utils';
import { InitablePersonCreator } from '../../bases';
import { HasAttributeList, HasPerson, RefreshPerson, LinkCheck, Saveable } from '../../interfaces';
import { MatCard, MatCardTitle, MatCardContent } from '@angular/material/card';
import { MatToolbar } from '@angular/material/toolbar';
import { PersonFamilySpouseComponent } from './person-family-spouse.component';
import { MatIconButton } from '@angular/material/button';
import { MatTooltip } from '@angular/material/tooltip';
import { MatIcon } from '@angular/material/icon';
import { NewPersonComponent } from './new-person.component';
import { LinkPersonComponent } from './link-person.component';
import { AttributeListComponent } from '../../components/attribute-list/attribute-list.component';
import { MultimediaGalleryComponent } from '../../components/multimedia-gallery/multimedia-gallery.component';
import { PersonFamilyChildListComponent } from './person-family-child-list.component';

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
    standalone: true,
    selector: 'app-person-family',
    template: `<mat-card class="custom-main-colors">
  <mat-card-title>
    <mat-toolbar class="custom-toolbar-colors">Family {{ index + 1 }}
      @if (spouse()) { <span>&nbsp;-&nbsp;</span> }
      @if (spouse()) {
        <app-person-family-spouse
            [dataset]="dataset" [parent]="this" [attribute]="spouse()"></app-person-family-spouse>
      }
      @if (spouse() && hasSignedIn()) {
        <span>
          <button mat-icon-button matTooltip="Unlink self from family" color="accent" (click)="unlink()">
            <mat-icon matListIcon>link_off</mat-icon>
          </button>
        </span>
      }
      @if (!spouse()) {
        <span class="example-fill-remaining-space"></span>
      }
      @if (!spouse() && hasSignedIn()) {
        <span>
          <app-new-person
              [sex]="sex" [surname]="surname" [label]="'Create spouse'"
              (emitOK)="createPerson($event)"></app-new-person>
          <app-link-person
              [parent]="this" [dataset]="dataset" [multi]="false" [label]="'Link parent'"
              (emitOK)="linkPerson($event)"></app-link-person>
          <button mat-icon-button matTooltip="Unlink self from family" color="accent" (click)="unlink()">
          <mat-icon matListIcon>link_off</mat-icon></button>
        </span>
      }
    </mat-toolbar>
  </mat-card-title>
    <mat-card-content class="custom-main-colors">
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
    @if (family?.children) {
        <app-person-family-child-list
                [children]="family?.children" [family]="family" [parent]="this"
                [dataset]="dataset"></app-person-family-child-list>
    }
  </mat-card-content>
</mat-card>
<br/>`,
    styles: [],
    imports: [MatCard, MatCardTitle, MatToolbar, PersonFamilySpouseComponent, MatIconButton, MatTooltip, MatIcon, NewPersonComponent, LinkPersonComponent, MatCardContent, AttributeListComponent, MultimediaGalleryComponent, PersonFamilyChildListComponent]
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
    private readonly _options: Array<SelectItem> = [
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

    constructor(@Inject(FamilyService) private readonly familyService: FamilyService,
        @Inject(PersonService) public readonly personService: PersonService,
        @Inject(UserService) private readonly userService: UserService,
        @Inject(NgZone) private readonly zone: NgZone,
        @Inject(ChangeDetectorRef) private readonly cdr: ChangeDetectorRef) {
        super(personService);
    }

    init(): void {
        this.familyService.getOne(this.dataset, this.string)
            .subscribe((family: ApiFamily) => {
                this.zone.run(() => {
                    this.family = family;
                    this.attributes = family.attributes;
                    this.initialized = true;
                    this.cdr.markForCheck();
                });
            });
        this.surname = '?';
        this.sex = NewPersonHelper.guessPartnerSex(this.person);
    }

    familyString() {
        return this.family.string;
    }

    preferredSurname(): string {
        return this.surname;
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
            (data: ApiFamily) => {
                this.zone.run(() => {
                    this.family = data;
                    this.cdr.markForCheck();
                });
            });
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
            .subscribe(() => {
                this.zone.run(() => {
                    this.parent.refreshPerson();
                    this.cdr.markForCheck();
                });
            });
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
