import { Component, Input , Inject } from '@angular/core';

import { InitablePersonCreator } from '../../bases';
import { HasFamily, HasPerson, RefreshPerson, Saveable, LinkCheck } from '../../interfaces';
import { LinkPersonDialogComponent } from '../../components';
import { ApiAttribute, ApiFamily, ApiPerson, LinkPersonDialogData } from '../../models';
import { PersonService, FamilyService, UserService } from '../../services';
import { UrlBuilder, LifespanUtil } from '../../utils';
import { CdkDragDrop, moveItemInArray } from '@angular/cdk/drag-drop';

/**
 * Implements a child list within a family on a person page.
 *
 * Inputs:
 *  children: the attributes referring to the children
 */
@Component({
    standalone: false,
    selector: 'app-person-family-child-list',
    template: `<div class="ui-g">
  <div class="ui-g-1"></div>
  <div class="ui-g-10">
    <mat-card>
      <mat-card-title>
        <mat-toolbar>
          Children
          <span class="example-fill-remaining-space"></span>
          <span *ngIf="hasSignedIn()">
            <app-new-person
                [sex]="sex" [surname]="surname" [label]="'Create child'"
                color="primary"
                (emitOK)="createPerson($event)"></app-new-person>
            <app-link-person
                [parent]="this" [dataset]="dataset" [multi]="true" [label]="'Link child'"
                color="primary"
                (emitOK)="linkChild($event)"></app-link-person>
          </span>
        </mat-toolbar>
      </mat-card-title>
      <mat-card-content>
        <div cdkDropList class="child-list" (cdkDropListDropped)="drop($event)"
            [cdkDropListDisabled]="!hasSignedIn()">
          <div cdkDrag class="{{ hasSignedIn() ? 'child-box' : '' }}"
              *ngFor="let child of children; let i=index">
            <app-person-family-child [child]="child" [index]="i"
                [parent]="this" [dataset]="dataset"></app-person-family-child>
          </div>
        </div>
      </mat-card-content>
    </mat-card>
  </div>
  <div class="ui-g-1"></div>
</div>`,
    styles: []
})
export class PersonFamilyChildListComponent extends InitablePersonCreator
    implements HasFamily, Saveable, LinkCheck {
    @Input() dataset: string;
    @Input() parent: RefreshPerson & HasPerson & Saveable;
    @Input() family: ApiFamily;
    @Input() children: Array<ApiAttribute>;

    sex = 'M';
    surname: string;

    constructor(@Inject(PersonService) public personService: PersonService,
        @Inject(FamilyService) public familyService: FamilyService,
        @Inject(UserService) private userService: UserService) {
        super(personService);
    }

    init(): void {
        const h = this.husbandId();
        if (h !== '') {
            this.personService.getOne(this.dataset, h)
                .subscribe((person: ApiPerson) => {
                    this.surname = person.surname;
                });
        } else {
            this.surname = this.parent.person.surname;
        }
    }

    private husbandId(): string {
        for (const spouse of this.family.spouses) {
            if (spouse.type === 'husband') {
                return spouse.string;
            }
        }
        return '';
    }

    personUB(): UrlBuilder {
        return new UrlBuilder(this.dataset, 'families', 'children');
    }

    personAnchor(): string {
        return this.family.string;
    }

    refreshPerson(): void {
        this.personService.getOne(this.dataset, this.parent.person.string).subscribe(
            (person: any) => this.parent.refreshPerson());
    }

    spouseLinked(person: ApiPerson): boolean {
        for (const spouse of this.family.spouses) {
            if (spouse.string === person.string) {
                return true;
            }
        }
        return false;
    }

    childLinked(person: ApiPerson): boolean {
        for (const child of this.children) {
            if (child.string === person.string) {
                return true;
            }
        }
        return false;
    }

    linkChild(data: LinkPersonDialogData) {
        for (const item of data.selected) {
            this.personService.putLink(this.personUB(), this.personAnchor(), item.person)
                .subscribe((person: ApiPerson) => { this.refreshPerson(); });
        }
    }

    preferredSurname(): string {
        return this.parent.person.surname;
    }

    familyString(): string {
        return this.family.string;
    }

    drop(event: CdkDragDrop<string[]>) {
        moveItemInArray(this.family.children, event.previousIndex, event.currentIndex);
        this.familyService.put(this.dataset, this.family)
            .subscribe((family: ApiFamily) => { this.children = family.children; });
    }

    save(): void {
        this.parent.save();
    }

    hasSignedIn() {
        return !!this.userService.currentUser;
    }
}
