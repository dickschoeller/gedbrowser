import { Component, Input } from '@angular/core';

import { ApiAttribute, ApiFamily, ApiPerson, AttributeDialogData, SelectItem } from '../../models';
import { FamilyService, PersonService, UserService } from '../../services';
import { UrlBuilder, NewPersonHelper } from '../../utils';
import { InitablePersonCreator } from '../../bases';
import { HasAttributeList, HasPerson, RefreshPerson, LinkCheck, Saveable } from '../../interfaces';

/**
 * Implements a family block within a person page.
 *
 * Inputs:
 *  string: the ID string of the family
 *  person: the person object of the containing block
 *  index: the index of the family in the order list for the containing person
 *
 * Fetches:
 *  family the family identified by the ID
 */
@Component({
    selector: 'app-person-family',
    templateUrl: './person-family.component.html',
    styleUrls: ['./person-family.component.css']
})
export class PersonFamilyComponent extends InitablePersonCreator
    implements HasAttributeList, LinkCheck, Saveable {
    @Input() dataset: string;
    @Input() parent: HasPerson & RefreshPerson;
    @Input() string: string;
    @Input() index: number;
    @Input() person: ApiPerson;

    family: ApiFamily;
    attributes: Array<ApiAttribute>;
    initialized = false;
    private _options: Array<SelectItem> = [
        { value: 'Annulment', label: 'Annulment' },
        { value: 'Census', label: 'Census' },
        { value: 'Children Count', label: 'Children Count' },
        { value: 'Divorce', label: 'Divorce' },
        { value: 'Divorce Filed', label: 'Divorce Filed' },
        { value: 'Event', label: 'Event' },
        { value: 'Engagement', label: 'Engagement' },
        { value: 'Marriage', label: 'Marriage' },
        { value: 'Marriage Bann', label: 'Marriage Bann' },
        { value: 'Marriage Contract', label: 'Marriage Contract' },
        { value: 'Marriage License', label: 'Marriage License' },
        { value: 'Marriage Settlement', label: 'Marriage Settlement' },
        { value: 'Note', label: 'Note' },
        { value: 'Multimedia', label: 'Multimedia' },
        { value: 'Residence', label: 'Residence' },
        { value: 'Restriction', label: 'Restriction' },
        { value: 'Sealing Child', label: 'Sealing Child' },
        { value: 'Sealing Spouse', label: 'Sealing Spouse' },
        { value: 'Source', label: 'Source' },
    ];
    sex: string;
    surname: string;

    constructor(private familyService: FamilyService,
        public personService: PersonService,
        private userService: UserService) {
        super(personService);
    }

    init(): void {
        this.familyService.getOne(this.dataset, this.string)
            .subscribe((family: ApiFamily) => {
                this.family = family;
                this.attributes = family.attributes;
                this.initialized = true;
            });
        this.surname = '?';
        this.sex = NewPersonHelper.guessPartnerSex(this.person);
    }

    familyString() {
        return this.family.string;
    }

    spouse(): ApiAttribute {
        if (this.family === undefined) {
            return null;
        }
        for (const attribute of this.family.spouses) {
            if (!this.isThisPerson(attribute)) {
                return attribute;
            }
        }
        return null;
    }

    private isThisPerson(attribute: ApiAttribute): boolean {
        return (attribute.string === this.person.string);
    }

    personUB(): UrlBuilder {
        return new UrlBuilder(this.dataset, 'families', 'spouses');
    }

    personAnchor() {
        return this.family.string;
    }

    refreshPerson() {
        this.ngOnInit();
    }

    save() {
        this.familyService.put(this.dataset, this.family).subscribe(
            (data: ApiFamily) => this.family = data);
    }

    options(): Array<SelectItem> {
        return this._options;
    }

    defaultData(): AttributeDialogData {
        return {
            insert: true, index: 0, type: 'Marriage', text: '', date: '',
            place: '', note: '', originalType: '', originalText: '',
            originalDate: '', originalPlace: '', originalNote: ''
        };
    }

    unlink(): void {
        const ub: UrlBuilder = new UrlBuilder(this.dataset, 'families', 'spouses');
        this.personService.deleteLink(ub, this.family.string, this.person)
            .subscribe((data: ApiPerson) => { this.parent.refreshPerson(); });
    }

    spouseLinked(person: ApiPerson): boolean {
        return false;
    }

    childLinked(person: ApiPerson): boolean {
        return false;
    }

    hasSignedIn() {
        return !!this.userService.currentUser;
    }
}
