import {NewPersonDialogData, NewPersonDialogComponent, NewPersonHelper} from '../new-person-dialog';
import {Component, Input} from '@angular/core';
import {ApiAttribute, ApiPerson, PersonService, FamilyService, ApiFamily} from '../shared';
import {MatDialogRef, MatDialog} from '@angular/material';

@Component({
  selector: 'app-person-parent-families',
  templateUrl: './person-parent-families.component.html',
  styleUrls: ['./person-parent-families.component.css']
})
export class PersonParentFamiliesComponent {
  @Input() famcAttributes: Array<ApiAttribute>;
  @Input() person: ApiPerson;
  @Input() parent: any;

  constructor(public dialog: MatDialog,
    private personService: PersonService,
    private familyService: FamilyService) {}

  createParentFamily() {
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
      this.saveNewParent(dialogData);
    });
  }

  private saveNewParent(dialogData: NewPersonDialogData) {
    const nph = new NewPersonHelper();
    const newPerson: ApiPerson = nph.buildPerson(dialogData);
    this.personService.post('schoeller', newPerson).subscribe(
      (data: ApiPerson) => {
        const person: ApiPerson = data;
        this.addNewFamilyWithParent(this.person, person);
      }
    );
  }

  private addNewFamilyWithParent(person: ApiPerson, parent: ApiPerson) {
    const family: ApiFamily = {type: 'Family', string: '', attributes: new Array<ApiAttribute>()};
    family.attributes.push(this.childLink(person));
    family.attributes.push(this.spouseLink(parent));
    this.familyService.post('schoeller', family).subscribe(
      (f: ApiFamily) => {
        this.newPersonFamsLink(f);
        this.personFamcLink(f);
      }
    );
  }

  private childLink(p: ApiPerson): ApiAttribute {
    return {type: 'child', string: p.string, tail: '', attributes: new Array<ApiAttribute>()};
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

  private personFamcLink(f: ApiFamily) {
    this.pushFamc(this.person, f);
    this.personService.put('schoeller', this.person).subscribe(
      (data: ApiPerson) => {
        this.parent.initLists();
      }
    );
  }

  private pushFamc(p: ApiPerson, f: ApiFamily): void {
    p.attributes.push(
      {type: 'famc', string: f.string, tail: '', attributes: new Array<ApiAttribute>()}
    );
  }
}
