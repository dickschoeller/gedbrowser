import {MatDialogRef, MatDialog} from '@angular/material';
import {Component, OnInit, Input} from '@angular/core';
import {NgxGalleryOptions, NgxGalleryImage} from 'ngx-gallery';
import {Observable} from 'rxjs/Observable';

import {ApiAttribute, ApiFamily, ApiPerson} from '../../models';
import {FamilyService, PersonService, NewPersonLinkService, UrlBuilder} from '../../services';
import {ImageUtil} from '../../utils';

import {NewPersonDialogData, NewPersonDialogComponent, NewPersonHelper} from '../../components/new-person-dialog';

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
export class PersonFamilyComponent extends PersonCreator implements OnInit {
  @Input() string: string;
  @Input() person: ApiPerson;
  @Input() index: number;

  family: ApiFamily;
  imageUtil = new ImageUtil();
  galleryOptions = this.imageUtil.galleryOptions();
  initialized = false;
  nph = new NewPersonHelper();

  constructor(public dialog: MatDialog,
    private familyService: FamilyService,
    private personService: PersonService,
    private newPersonLinkService: NewPersonLinkService) {
    super(dialog);
  }

  ngOnInit() {
    this.familyService.getOne('schoeller', this.string)
      .subscribe((family: ApiFamily) => {
        this.family = family;
        this.initialized = true;
    });
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
    this.newPersonDialog2('F', 'Anonyma',
      this.newPersonLinkService, new UrlBuilder('schoeller', 'families', 'spouses'));
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
    this.familyService.put('schoeller', this.family).subscribe(
      (data: ApiFamily) => this.family = data);
  }
}
