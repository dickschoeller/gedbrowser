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
    person.attributes.push(adh.populateNewAttribute({
      insert: true, index: 0,
      type: 'Name', text: data.name, date: '', place: '', note: '',
      originalType: 'Name', originalText: data.name, originalDate: '',
      originalPlace: '', originalNote: ''
    }));
    person.attributes.push(adh.populateNewAttribute({
      insert: true, index: 1,
      type: 'Sex', text: data.sex, date: '', place: '', note: '',
      originalType: '', originalText: '', originalDate: '', originalPlace: '',
      originalNote: ''
    }));
    if (data.birthDate !== '' || data.birthPlace !== '') {
      person.attributes.push(adh.populateNewAttribute({
        insert: true, index: 1,
        type: 'Birth', text: '', date: data.birthDate, place: data.birthPlace,
        note: '',
        originalType: '', originalText: '', originalDate: '', originalPlace: '',
        originalNote: ''
      }));
    }
    if (data.deathDate !== '' || data.deathPlace !== '') {
      person.attributes.push(adh.populateNewAttribute({
        insert: true, index: 1,
        type: 'Death', text: '', date: data.deathDate, place: data.deathPlace,
        note: '',
        originalType: '', originalText: '', originalDate: '', originalPlace: '',
        originalNote: ''
      }));
    }
    return person;
  }
}
