import { Component, Input } from '@angular/core';
import { NgxGalleryOptions, NgxGalleryImage } from 'ngx-gallery';
import { Observable } from 'rxjs/Observable';
import { MenuItem, SelectItem } from 'primeng/api';

import { AttributeDialogData, NewPersonDialogComponent, LinkPersonDialogComponent } from '../../components';
import { ApiAttribute, ApiFamily, ApiPerson, NewPersonDialogData, LinkPersonDialogData } from '../../models';
import { FamilyService, PersonService, NewPersonLinkService } from '../../services';
import { ImageUtil, UrlBuilder, LinkPersonHelper } from '../../utils';
import { HasAttributeList } from '../../interfaces';
import { InitablePersonCreator } from '../../bases';
import { HasPerson, RefreshPerson } from '../../interfaces';

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
  selector: 'app-person-family',
  templateUrl: './person-family.component.html',
  styleUrls: ['./person-family.component.css']
})
export class PersonFamilyComponent extends InitablePersonCreator implements HasAttributeList {
  @Input() dataset: string;
  @Input() parent: HasPerson & RefreshPerson;
  @Input() string: string;
  @Input() index: number;
  get person(): ApiPerson {
    return this.parent.person;
  }

  family: ApiFamily;
  imageUtil = new ImageUtil();
  galleryOptions = this.imageUtil.galleryOptions();
  initialized = false;
  _options: Array<SelectItem> = [
      {value: 'Annulment', label: 'Annulment'},
      {value: 'Census', label: 'Census'},
      {value: 'Children Count', label: 'Children Count'},
      {value: 'Divorce', label: 'Divorce'},
      {value: 'Divorce Filed', label: 'Divorce Filed'},
      {value: 'Event', label: 'Event'},
      {value: 'Engagement', label: 'Engagement'},
      {value: 'Marriage', label: 'Marriage'},
      {value: 'Marriage Bann', label: 'Marriage Bann'},
      {value: 'Marriage Contract', label: 'Marriage Contract'},
      {value: 'Marriage License', label: 'Marriage License'},
      {value: 'Marriage Settlement', label: 'Marriage Settlement'},
      {value: 'Note', label: 'Note'},
      {value: 'Multimedia', label: 'Multimedia'},
      {value: 'Residence', label: 'Residence'},
      {value: 'Restriction', label: 'Restriction'},
      {value: 'Sealing Child', label: 'Sealing Child'},
      {value: 'Sealing Spouse', label: 'Sealing Spouse'},
      {value: 'Source', label: 'Source'},
    ];
  surname: string;
  displayPersonDialog = false;
  displayLinkSpouseDialog = false;
  lph: LinkPersonHelper = new LinkPersonHelper();
  sex: string;
  items: MenuItem[] = [
    {
      label: 'Create parent', icon: 'fa-user', command: (event: Event) => {  /** this.displayPersonDialog = true; */ }
    },
    {
      label: 'Link parent', icon: 'fa-link', command: (event: Event) => { /** this.displayLinkParentDialog = true; */ }
    },
  ];

  constructor(private familyService: FamilyService,
    private personService: PersonService,
    newPersonLinkService: NewPersonLinkService) {
    super(newPersonLinkService);
  }

  init(): void {
    this.familyService.getOne(this.dataset, this.string)
      .subscribe((family: ApiFamily) => {
        this.family = family;
        this.initialized = true;
    });
    this.surname = '?';
    this.sex = this.nph.guessPartnerSex(this.person);
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

  createSpouse(): void {
    this.displayPersonDialog = true;
  }

  onDialogOpen(data: NewPersonDialogComponent) {
    data._data = this.nph.initNew(this.sex, this.surname);
  }

  personUB(): UrlBuilder {
      return new UrlBuilder(this.dataset, 'families', 'spouses');
  }

  closePersonDialog() {
    this.displayPersonDialog = false;
  }

  personAnchor() {
    return this.family.string;
  }

  refreshPerson() {
    this.ngOnInit();
  }

  galleryImages(): Array<NgxGalleryImage> {
    if (this.imageUtil === undefined || this.family === undefined) {
      return new Array<NgxGalleryImage>();
    }
    return this.imageUtil.galleryImages(this.family.images);
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
    this.newPersonLinkService.delete(ub, this.family.string, this.person)
      .subscribe((data: ApiPerson) => { this.parent.refreshPerson(); });
  }

  openLinkSpouseDialog(): void {
    this.displayLinkSpouseDialog = true;
  }

  onLinkSpouseDialogClose(): void {
    this.displayLinkSpouseDialog = false;
  }

  onLinkSpouseDialogOpen(dialogComponent: LinkPersonDialogComponent): void {
    this.lph.onLinkChildDialogOpen(dialogComponent, this);
  }

  linkSpouse(data: LinkPersonDialogData): void {
    this.newPersonLinkService.put(this.personUB(), this.personAnchor(), data.selectOne.person)
      .subscribe((person: ApiPerson) => { this.refreshPerson(); });
  }

  spouseLinked(): boolean {
    return false;
  }

  childLinked(): boolean {
    return false;
  }
}
