import { LinkCheck } from '../interfaces';
import { ApiPerson, LinkPersonDialogData } from '../models';
import { PersonService } from '../services';
import { LifespanUtil } from './lifespan-util';

export class LinkPersonHelper {
  constructor(private personService: PersonService) {}

  onLinkChildDialogOpen(dialogComponent: any, component: LinkCheck) {
    this.personService.getAll(dialogComponent.dataset).subscribe(
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

  private alreadyLinked(person: ApiPerson, component: LinkCheck): boolean {
    if (component.spouseLinked(person)) {
      return true;
    }
    return component.childLinked(person);
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
