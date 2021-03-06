import { Component, Input } from '@angular/core';
import { CdkDragDrop, moveItemInArray } from '@angular/cdk/drag-drop';

import { InitablePersonCreator } from '../../bases';
import { HasPerson, LinkCheck, Saveable } from '../../interfaces';
import { ApiPerson, NewPersonDialogData, LinkPersonDialogData, LinkPersonItem } from '../../models';
import { UrlBuilder, NewPersonHelper } from '../../utils';
import { PersonService, UserService } from '../../services';

/**
 * Implements a the list of families on a person page
 *
 * Inputs:
 *  person: the person this page is for
 */
@Component({
    selector: 'app-person-family-list',
    templateUrl: './person-family-list.component.html',
    styleUrls: ['./person-family-list.component.css']
})
export class PersonFamilyListComponent extends InitablePersonCreator implements LinkCheck {
    @Input() dataset: string;
    @Input() parent: HasPerson & Saveable;
    @Input() person: ApiPerson;
    partnerSurname: string;
    childSurname: string;
    partnerSex: string;
    childSex = 'M';
    _ub: UrlBuilder;

    constructor(public personService: PersonService,
        private userService: UserService) {
        super(personService);
    }

    init() {
        this.partnerSex = NewPersonHelper.guessPartnerSex(this.person);
        if (this.partnerSex === 'M') {
            this.childSurname = '?';
        } else {
            this.childSurname = this.person.surname;
        }
        this.partnerSurname = '?';
    }

    personUB(): UrlBuilder {
        return this._ub;
    }

    personAnchor(): string {
        return this.person.string;
    }

    refreshPerson(): void {
        this.personService.getOne(this.dataset, this.person.string).subscribe(
            (person: ApiPerson) => this.updatePerson(person));
    }

    private updatePerson(person: ApiPerson) {
        this.parent.person = person;
    }

    spouseLinked(person: ApiPerson): boolean {
        return this.person.string === person.string;
    }

    childLinked(person: ApiPerson): boolean {
        return false;
    }

    linkChildren(data: LinkPersonDialogData) {
        this._ub = new UrlBuilder(this.dataset, 'persons', 'children');
        const selected: Array<LinkPersonItem> = data.selected.splice(0, 1);
        this.personService.putLink(this.personUB(), this.personAnchor(), selected[0].person)
            .subscribe((person: ApiPerson) => {
                this.linkChildrenToMainPerson(data);
            });
    }

    private linkChildrenToMainPerson(data: LinkPersonDialogData) {
        this.personService.getOne(this.dataset, this.person.string)
            .subscribe((mainPerson: ApiPerson) => {
                this.linkRemainingChildren(data, mainPerson);
            });
    }

    private linkRemainingChildren(data: LinkPersonDialogData, mainPerson: ApiPerson): void {
        this.updatePerson(mainPerson);
        const fams = mainPerson.fams[mainPerson.fams.length - 1];
        const ub = new UrlBuilder(this.dataset, 'families', 'children');
        for (const selected of data.selected) {
            this.personService.putLink(ub, fams.string, selected.person)
                .subscribe((p: ApiPerson) => { this.refreshPerson(); });
        }
    }

    linkSpouse(data: LinkPersonDialogData) {
        this._ub = new UrlBuilder(this.dataset, 'persons', 'spouses');
        this.linkPerson(data);
    }

    createSpouse(data: NewPersonDialogData): void {
        this._ub = new UrlBuilder(this.dataset, 'persons', 'spouses');
        this.createPerson(data);
    }

    createChild(data: NewPersonDialogData): void {
        this._ub = new UrlBuilder(this.dataset, 'persons', 'children');
        this.createPerson(data);
    }

    drop(event: CdkDragDrop<string[]>) {
        moveItemInArray(this.person.fams, event.previousIndex, event.currentIndex);
        this.parent.save();
    }

    hasSignedIn() {
        return !!this.userService.currentUser;
    }
}
