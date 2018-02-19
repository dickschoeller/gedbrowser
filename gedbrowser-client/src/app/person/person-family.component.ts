import {Component, OnInit, Input} from '@angular/core';
import {NgxGalleryOptions, NgxGalleryImage} from 'ngx-gallery';
import {Observable} from 'rxjs/Observable';

import {ApiAttribute, ApiFamily, ApiPerson, FamilyService, ImageUtil} from '../shared';

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
  galleryOptions: Array<NgxGalleryOptions>;
  spouseAttributes: Array<ApiAttribute>;
  childrenAttributes: Array<ApiAttribute>;
  strippedAttributes: Array<ApiAttribute>;
  imageAttributes: Array<ApiAttribute>;

  constructor(
    private familyService: FamilyService,
  ) { }

  ngOnInit() {
    this.familyService.getOne('schoeller', this.string)
      .subscribe((family: ApiFamily) => {
        this.family = family;
    });
    this.imageUtil = new ImageUtil();
    this.spouseAttributes = this.createSpouseAttributes();
    this.strippedAttributes = this.createStrippedAttributes();
    this.childrenAttributes = this.createChildrenAttributes();
    this.imageAttributes = this.createImageAttributes();

    this.galleryOptions = this.imageUtil.galleryOptions();
  }

  familyString() {
    return this.family.string;
  }

  spouse(): ApiAttribute {
    const stripped: Array<ApiAttribute> = new Array<ApiAttribute>();
    for (const attribute of this.family.attributes) {
      if (this.isSpouse(attribute) && !this.isThisPerson(attribute)) {
        return attribute;
      }
    }
    return null;
  }

  private isThisPerson(attribute: ApiAttribute): boolean {
    return (attribute.string === this.person.string);
  }

  private isSpouse(attribute: ApiAttribute): boolean {
    return (attribute.type === 'husband' || attribute.type === 'wife');
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

  galleryImages(): Array<NgxGalleryImage> {
    return this.imageUtil.galleryImages(this.family.attributes);
  }

  save() {
    this.family.attributes = new Array<ApiAttribute>();
    this.family.attributes.concat(this.spouseAttributes);
    this.family.attributes.concat(this.strippedAttributes);
    this.family.attributes.concat(this.childrenAttributes);
    this.family.attributes.concat(this.imageAttributes);
    this.familyService.put('schoeller', this.family);
  }
}
