import {NewPersonDialogData, NewPersonDialogComponent, NewPersonHelper} from '../new-person-dialog';
import {Component, Input} from '@angular/core';
import {ApiAttribute, ApiPerson} from '../shared';
import { ApiFamily } from '../shared/models';
import { PersonService, FamilyService } from '../shared/services';
import { PersonComponent } from './person.component';
import { MatDialogRef, MatDialog } from '@angular/material';

/**
 * Implements a the list of families on a person page
 *
 * Inputs:
 *  attributes: the attributes that refer to families
 *  person: the person this page is for
 */
@Component({
  selector: 'app-person-family-list',
  templateUrl: './person-family-list.component.html',
  styleUrls: ['./person-family-list.component.css']
})
export class PersonFamilyListComponent {
  @Input() attributes: Array<ApiAttribute>;
  @Input() person: ApiPerson;
  @Input() parent: PersonComponent;

  constructor(public dialog: MatDialog,
    private personService: PersonService,
    private familyService: FamilyService) { }

  createFamilyWithChild(): void {
    const dataIn: NewPersonDialogData = {
      sex: 'M', name: 'Anonymous',
      birthDate: '', birthPlace: '', deathDate: '', deathPlace: ''
    };
    const dialogRef: MatDialogRef<NewPersonDialogComponent> =
      this.dialog.open(NewPersonDialogComponent, {
        width: '500px',
        height: '600px',
        data: dataIn,
      });
    alert('create child');
  }

  createFamilyWithSpouse(): void {
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
    this.personService.post('schoeller', newPerson).subscribe(
      (data: ApiPerson) => {
        const person: ApiPerson = data;
        this.addNewFamilyWithSpouse(this.person, person);
      }
    );
  }

  private addNewFamilyWithSpouse(person1: ApiPerson, person2: ApiPerson) {
    const family: ApiFamily = {type: 'Family', string: '', attributes: new Array<ApiAttribute>()};
    family.attributes.push(this.spouseLink(person1));
    family.attributes.push(this.spouseLink(person2));
    this.familyService.post('schoeller', family).subscribe(
      (f: ApiFamily) => {
        this.newPersonFamsLink(f);
        this.personFamsLink(f);
      }
    );
  }

  private newPersonFamsLink(f: ApiFamily) {
    for (const a of f.attributes) {
      if (a.type !== 'husband' && a.type !== 'wife') {
        continue;
      }
      if (this.person.string === a.string) {
        continue;
      }
      this.personService.getOne('schoeller', a.string).subscribe(
        (p: ApiPerson) => {
          this.pushFams(p, f);
          this.personService.put('schoeller', p).subscribe((pback: ApiPerson) => {});
        }
      );
    }
  }

  private pushFams(p: ApiPerson, f: ApiFamily): void {
    p.attributes.push(
      {type: 'fams', string: f.string, tail: '', attributes: new Array<ApiAttribute>()}
    );
  }

  private personFamsLink(f: ApiFamily) {
    this.pushFams(this.person, f);
    this.personService.put('schoeller', this.person).subscribe(
      (data: ApiPerson) => {
        this.parent.initLists();
      }
    );
  }

  private spouseLink(p: ApiPerson): ApiAttribute {
    for (const a of p.attributes) {
      if (a.string !== 'Sex') {
        continue;
      }
      let t = 'spouse';
      if (a.tail === 'M') {
        t = 'husband';
      } else if (a.tail === 'F') {
        t = 'wife';
      }
      return {type: t, string: p.string, tail: '', attributes: new Array<ApiAttribute>()};
    }
  }
}
