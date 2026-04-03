import { ChangeDetectorRef, NgZone } from '@angular/core';
import { RefreshPerson } from '../../interfaces';
import {ApiPerson} from '../../models';
import {PersonService } from '../../services';
import {UrlBuilder, LifespanUtil} from '../../utils';

export abstract class PersonGetter implements RefreshPerson {
  hiddenDataset: string;
  person: ApiPerson;
  famMemberType: string;
  private latestRequestToken = 0;

  constructor(private readonly personService: PersonService,
    private readonly zone?: NgZone,
    private readonly cdr?: ChangeDetectorRef) {}

  abstract refreshPerson(): void;
  abstract familyString(): string;

  init(dataset: string, attrString: string): void {
    const requestToken = ++this.latestRequestToken;
    this.hiddenDataset = dataset;
    this.get(dataset, attrString, (person: ApiPerson) => {
      if (requestToken !== this.latestRequestToken) {
        return;
      }
      this.runInAngular(() => {
        this.person = person;
      });
    });
  }

  get(dataset: string, id: string, callback: any): void {
    this.personService.getOne(dataset, id)
      .subscribe(callback);
  }

  unlink(): void {
    const ub: UrlBuilder = new UrlBuilder(this.hiddenDataset, 'families', this.famMemberType);
    this.personService.deleteLink(ub, this.familyString(), this.person)
      .subscribe(() => {
        this.runInAngular(() => {
          this.refreshPerson();
        });
      });
  }

  private runInAngular(action: () => void): void {
    if (this.zone) {
      this.zone.run(() => {
        action();
        this.cdr?.markForCheck();
      });
      return;
    }

    action();
    this.cdr?.markForCheck();
  }

  lifespanYearString(): string {
    return new LifespanUtil(this.person.lifespan).lifespanYearString();
  }
}
