import {ApiPerson, ApiAttribute} from '../../models';
import {AttributeDialogHelper} from '../attribute-dialog/attribute-dialog-helper';
import {NewPersonDialogData} from './new-person-dialog-data';

export class NewPersonHelper {
  constructor() {}

  buildPerson(data: NewPersonDialogData): ApiPerson {
    if (data.name === '' || data.name === undefined || data.name === null) {
      data.name = this.defaultGiven(data.sex);
    }
    const person: ApiPerson = new ApiPerson();
    person.attributes = new Array<ApiAttribute>();
    this.addName(data.name, person);
    this.addSex(data.sex, person);
    this.addBirth(data.birthDate, data.birthPlace, person);
    this.addDeath(data.deathDate, data.deathPlace, person);
    return person;
  }

  addName(name, person) {
    const adh: AttributeDialogHelper = new AttributeDialogHelper(person);
    person.attributes.push(adh.populateNewAttribute({
      insert: true, index: 0,
      type: 'Name', text: name, date: '', place: '', note: '',
      originalType: 'Name', originalText: name, originalDate: '',
      originalPlace: '', originalNote: ''
    }));
  }

  addSex(sex, person) {
    const adh: AttributeDialogHelper = new AttributeDialogHelper(person);
    person.attributes.push(adh.populateNewAttribute({
      insert: true, index: 1,
      type: 'Sex', text: sex, date: '', place: '', note: '',
      originalType: '', originalText: '', originalDate: '', originalPlace: '',
      originalNote: ''
    }));
  }

  addBirth(birthDate, birthPlace, person) {
    const adh: AttributeDialogHelper = new AttributeDialogHelper(person);
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

  addDeath(deathDate, deathPlace, person) {
    const adh: AttributeDialogHelper = new AttributeDialogHelper(person);
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

  config(dataIn) {
    return { data: dataIn };
  }

  initNew(sex: string, surname: string): NewPersonDialogData {
    return {
      sex: sex, name: this.defaultGiven(sex) + '/' + surname + '/',
      birthDate: '', birthPlace: '', deathDate: '', deathPlace: ''
    };
  }

  empty(result): boolean {
    return (result === null || result === undefined);
  }

  guessPartnerSex(person: ApiPerson): string {
    for (const a of person.attributes) {
      if (a.string === 'Sex') {
        if (a.tail === 'F') {
          return 'M';
        }
        return 'F';
      }
    }
    return 'M';
  }

  defaultGiven(sex: string): string {
    if (sex === 'F') {
      return 'Anonyma';
    }
    return 'Anonymous';
  }
}
