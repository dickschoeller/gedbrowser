import {ApiPerson, LinkPersonDialogData} from '../models';
import {LifespanUtil} from './lifespan-util';

export class LinkPersonHelper {

  onLinkChildDialogOpen(dialogComponent: any, component: any) {
    component.personService.getAll(dialogComponent.dataset).subscribe(
      (value: ApiPerson[]) => {
        dialogComponent.persons = value;
        dialogComponent.persons.sort(dialogComponent.compare);
        dialogComponent._data = new LinkPersonDialogData();
        for (const person of dialogComponent.persons) {
          if (this.alreadyLinked(person, component)) {
            continue;
          }
          this.pushDataItem(dialogComponent._data, person);
        }
      }
    );
  }

  private alreadyLinked(person: ApiPerson, component): boolean {
    if (this.spouseLinked(person, component)) {
      return true;
    }
    return component.childLinked(person);
  }

  private spouseLinked(person: ApiPerson, component): boolean {
    for (const spouse of component.family.spouses) {
      if (spouse.string === person.string) {
        return true;
      }
    }
    return false;
  }

  private pushDataItem(data: LinkPersonDialogData, person: ApiPerson) {
    const lifespanUtil = new LifespanUtil(person.lifespan);
    data.items.push({
      id: person.string,
      label: person.indexName
        + lifespanUtil.lifespanYearString()
        + ' [' + person.string + ']',
      person: person
    });
  }
}
