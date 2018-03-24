import {NewPersonDialogData, NewPersonDialogComponent, NewPersonHelper} from '../new-person-dialog';
import {Component, OnInit, Input} from '@angular/core';
import {NgxGalleryOptions, NgxGalleryImage} from 'ngx-gallery';
import {Observable} from 'rxjs/Observable';

import {ApiAttribute, ApiFamily, ApiPerson, FamilyService, ImageUtil, PersonService, SpouseService} from '../shared';
import {MatDialogRef, MatDialog} from '@angular/material';

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
export class PersonFamilyComponent implements OnInit {
  @Input() string: string;
  @Input() person: ApiPerson;
  @Input() index: number;

  family: ApiFamily;
  imageUtil: ImageUtil;
  galleryOptions: Array<NgxGalleryOptions> = new Array<NgxGalleryOptions>();
  spouseAttributes: Array<ApiAttribute> = new Array<ApiAttribute>();
  childrenAttributes: Array<ApiAttribute> = new Array<ApiAttribute>();
  strippedAttributes: Array<ApiAttribute> = new Array<ApiAttribute>();
  imageAttributes: Array<ApiAttribute> = new Array<ApiAttribute>();

  constructor(public dialog: MatDialog,
    private familyService: FamilyService,
    private personService: PersonService,
    private spouseService: SpouseService) {}

  ngOnInit() {
    this.familyService.getOne('schoeller', this.string)
      .subscribe((family: ApiFamily) => {
        this.family = family;
        this.initLists();
    });
  }

  initLists() {
    this.imageUtil = new ImageUtil();
    this.spouseAttributes = this.createSpouseAttributes();
    this.strippedAttributes = this.createStrippedAttributes();
    this.childrenAttributes = this.createChildrenAttributes();
    this.imageAttributes = this.createImageAttributes();
    this.galleryOptions = this.imageUtil.galleryOptions();
  }

  private createStrippedAttributes(): Array<ApiAttribute> {
    const stripped: Array<ApiAttribute> = new Array<ApiAttribute>();
    for (const attribute of this.family.attributes) {
      if (!this.isFamilyMember(attribute) &&
      !this.imageUtil.isImageWrapper(attribute)) {
        stripped.push(attribute);
      }
    }
    return stripped;
  }

  private isFamilyMember(attribute: ApiAttribute): boolean {
    return (attribute.type === 'husband' || attribute.type === 'wife'
        || attribute.type === 'child');
  }

  private createChildrenAttributes(): Array<ApiAttribute> {
    const stripped: Array<ApiAttribute> = new Array<ApiAttribute>();
    for (const attribute of this.family.attributes) {
      if (attribute.type === 'child') {
        stripped.push(attribute);
      }
    }
    return stripped;
  }

  private createSpouseAttributes(): Array<ApiAttribute> {
    const spouses: Array<ApiAttribute> = new Array<ApiAttribute>();
    for (const attribute of this.family.attributes) {
      if (this.isSpouse(attribute)) {
        spouses.push(attribute);
      }
    }
    return spouses;
  }

  private createImageAttributes(): Array<ApiAttribute> {
    return this.imageUtil.imageAttributes(this.family.attributes);
  }

  familyString() {
    return this.family.string;
  }

  spouse(): ApiAttribute {
    if (this.family === undefined) {
      return null;
    }
    for (const attribute of this.family.attributes) {
      if (this.isSpouse(attribute) && !this.isThisPerson(attribute)) {
        return attribute;
      }
    }
    return null;
  }

  private isSpouse(attribute: ApiAttribute): boolean {
    return (attribute.type === 'husband' || attribute.type === 'wife');
  }

  private isThisPerson(attribute: ApiAttribute): boolean {
    return (attribute.string === this.person.string);
  }

  createSpouse(): void {
    const dataIn: NewPersonDialogData = {
      sex: 'F', name: 'Anonyma',
      birthDate: '', birthPlace: '', deathDate: '', deathPlace: ''
    };
    const dialogRef: MatDialogRef<NewPersonDialogComponent> =
      this.dialog.open(NewPersonDialogComponent, {
        width: '500px',
        height: '600px',
        data: dataIn,
      });
    dialogRef.afterClosed().subscribe(result => {
      if (result === null || result === undefined) {
        return;
      }
      const dialogData: NewPersonDialogData = result;
      this.saveNewSpouse(dialogData);
    });
  }

  private saveNewSpouse(dialogData: NewPersonDialogData): void {
    const nph = new NewPersonHelper();
    const newPerson: ApiPerson = nph.buildPerson(dialogData);
    this.spouseService.postToFamily('schoeller', this.family.string, newPerson).subscribe(
      (data: ApiPerson) => {
        this.ngOnInit();
      }
    );
  }

  galleryImages(): Array<NgxGalleryImage> {
    if (this.imageUtil === undefined || this.family === undefined) {
      return new Array<NgxGalleryImage>();
    }
    return this.imageUtil.galleryImages(this.family.attributes);
  }

  // TODO make this go away
  save() {
    this.family.attributes = new Array<ApiAttribute>()
      .concat(this.spouseAttributes)
      .concat(this.strippedAttributes)
      .concat(this.childrenAttributes)
      .concat(this.imageAttributes);
    this.familyService.put('schoeller', this.family).subscribe(
      (data: ApiFamily) => {
        this.family = data;
        this.initLists();
      }
    );
  }
}
