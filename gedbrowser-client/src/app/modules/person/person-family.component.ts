import {Component, Input} from '@angular/core';
import {NgxGalleryOptions, NgxGalleryImage} from 'ngx-gallery';
import {Observable} from 'rxjs/Observable';
import {SelectItem} from 'primeng/api';

import {AttributeDialogData, NewPersonDialogData, NewPersonDialog2Component} from '../../components';
import {ApiAttribute, ApiFamily, ApiPerson} from '../../models';
import {FamilyService, PersonService, NewPersonLinkService, UrlBuilder} from '../../services';
import {ImageUtil} from '../../utils';
import {HasAttributeList} from '../../interfaces';

import {PersonCreator} from './person-creator';

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
export class PersonFamilyComponent extends PersonCreator implements HasAttributeList {
  @Input() dataset: string;
  @Input() person: ApiPerson;
  @Input() string: string;
  @Input() index: number;

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
  display = false;
  sex: string;

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

  ub(): UrlBuilder {
      return new UrlBuilder(this.dataset, 'families', 'spouses');
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
    this.display = true;
  }

  onDialogOpen(data: NewPersonDialog2Component) {
    data._data = this.nph.initNew(this.sex, this.surname);
  }

  closeDialog() {
    this.display = false;
  }

  anchor() {
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
}
