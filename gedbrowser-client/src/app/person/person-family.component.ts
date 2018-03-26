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
  initialized = false;

  constructor(public dialog: MatDialog,
    private familyService: FamilyService,
    private personService: PersonService,
    private spouseService: SpouseService) {}

  ngOnInit() {
    this.familyService.getOne('schoeller', this.string)
      .subscribe((family: ApiFamily) => {
        this.family = family;
        this.initLists();
        this.initialized = true;
    });
  }

  initLists() {
    this.imageUtil = new ImageUtil();
    this.galleryOptions = this.imageUtil.galleryOptions();
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
    return this.imageUtil.galleryImages(this.family.images);
  }

  save() {
    this.familyService.put('schoeller', this.family).subscribe(
      (data: ApiFamily) => {
        this.family = data;
        this.initLists();
      }
    );
  }
}
