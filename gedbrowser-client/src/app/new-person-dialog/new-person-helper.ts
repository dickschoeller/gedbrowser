import {AttributeDialogHelper} from '../shared/components/attribute-dialog-helper';
import {ApiPerson, ApiAttribute} from '../shared/models';
import {NewPersonDialogData} from './new-person-dialog-data';
export class NewPersonHelper {
  constructor() {}

  buildPerson(data: NewPersonDialogData): ApiPerson {
    if (data.name === '' || data.name === undefined || data.name === null) {
      if (data.sex === 'F') {
        data.name = 'Anonyma';
      } else {
        data.name = 'Anonymous';
      }
    }
    const person: ApiPerson = new ApiPerson();
    person.attributes = new Array<ApiAttribute>();
    const adh: AttributeDialogHelper = new AttributeDialogHelper(person);
    this.addName(adh, data.name, person);
    this.addSex(adh, data.sex, person);
    this.addBirth(adh, data.birthDate, data.birthPlace, person);
    this.addDeath(adh, data.deathDate, data.deathPlace, person);
    return person;
  }

  addName(adh, name, person) {
    person.attributes.push(adh.populateNewAttribute({
      insert: true, index: 0,
      type: 'Name', text: name, date: '', place: '', note: '',
      originalType: 'Name', originalText: name, originalDate: '',
      originalPlace: '', originalNote: ''
    }));
  }

  addSex(adh, sex, person) {
    person.attributes.push(adh.populateNewAttribute({
      insert: true, index: 1,
      type: 'Sex', text: sex, date: '', place: '', note: '',
      originalType: '', originalText: '', originalDate: '', originalPlace: '',
      originalNote: ''
    }));
  }

  addBirth(adh, birthDate, birthPlace, person) {
    if (birthDate !== '' || birthPlace !== '') {
      person.attributes.push(adh.populateNewAttribute({
        insert: true, index: 0,
        type: 'Birth', text: '', date: birthDate, place: birthPlace,
        note: '',
        originalType: '', originalText: '', originalDate: '', originalPlace: '',
        originalNote: ''
      }));
    }
  }

  addDeath(adh, deathDate, deathPlace, person) {
    if (deathDate !== '' || deathPlace !== '') {
      person.attributes.push(adh.populateNewAttribute({
        insert: true, index: 0,
        type: 'Death', text: '', date: deathDate, place: deathPlace,
        note: '',
        originalType: '', originalText: '', originalDate: '', originalPlace: '',
        originalNote: ''
      }));
    }
  }
}
