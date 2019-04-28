import { ApiPerson, ApiAttribute, NewPersonDialogData } from '../models';
import { StringUtil } from './string-util';
import { AttributeDialogHelper } from './attribute-dialog-helper';

export class NewPersonHelper {
  public static buildPerson(data: NewPersonDialogData): ApiPerson {
    if (StringUtil.isEmpty(data.name)) {
      data.name = NewPersonHelper.defaultGiven(data.sex);
    }
    const person: ApiPerson = new ApiPerson();
    person.attributes = new Array<ApiAttribute>();
    NewPersonHelper.addName(data.name, person);
    NewPersonHelper.addSex(data.sex, person);
    NewPersonHelper.addBirth(data.birthDate, data.birthPlace, person);
    NewPersonHelper.addDeath(data.deathDate, data.deathPlace, person);
    return person;
  }

  private static addName(name, person) {
    const adh: AttributeDialogHelper = new AttributeDialogHelper(person);
    person.attributes.push(adh.simpleAttribute('Name', name));
  }

  private static addSex(sex, person) {
    const adh: AttributeDialogHelper = new AttributeDialogHelper(person);
    person.attributes.push(adh.simpleAttribute('Sex', sex));
  }

  private static addBirth(birthDate, birthPlace, person) {
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

  private static addDeath(deathDate, deathPlace, person) {
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

  public static config(dataIn) {
    return {data: dataIn};
  }

  public static initNew(sex: string, surname: string): NewPersonDialogData {
    return {
      sex: sex, name: NewPersonHelper.defaultGiven(sex) + '/' + surname + '/',
      birthDate: '', birthPlace: '', deathDate: '', deathPlace: ''
    };
  }

  public static guessPartnerSex(person: ApiPerson): string {
    for (const a of person.attributes) {
      if (a.string === 'Sex') {
        return NewPersonHelper.oppositeSex(a.tail);
      }
    }
    return 'M';
  }

  private static oppositeSex(sex: string): string {
    if (sex === 'F') {
      return 'M';
    }
    return 'F';
  }

  private static defaultGiven(sex: string): string {
    if (sex === 'F') {
      return 'Anonyma';
    }
    return 'Anonymous';
  }
}
