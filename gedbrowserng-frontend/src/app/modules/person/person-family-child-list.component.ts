import { Component, Input } from '@angular/core';

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
    selector: 'app-person-family-child-list',
    templateUrl: './person-family-child-list.component.html',
    styleUrls: ['./person-family-child-list.component.css']
})
export class PersonFamilyChildListComponent extends InitablePersonCreator
    implements HasFamily, Saveable, LinkCheck {
    @Input() dataset: string;
    @Input() parent: RefreshPerson & HasPerson & Saveable;
    @Input() family: ApiFamily;
    @Input() children: Array<ApiAttribute>;

    sex = 'M';
    surname: string;

    constructor(public personService: PersonService,
        public familyService: FamilyService,
        private userService: UserService) {
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
