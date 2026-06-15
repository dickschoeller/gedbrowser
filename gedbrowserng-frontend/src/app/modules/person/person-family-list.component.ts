import { ChangeDetectorRef, Component, Inject, Input, NgZone } from '@angular/core';
import { CdkDragDrop, moveItemInArray, CdkDropList, CdkDrag } from '@angular/cdk/drag-drop';
import { MatDialog } from '@angular/material/dialog';

import { InitablePersonCreator } from '../../bases';
import { HasPerson, LinkCheck, Saveable } from '../../interfaces';
import { ApiPerson, NewPersonDialogData, LinkPersonDialogData, LinkPersonItem } from '../../models';
import { UrlBuilder, NewPersonHelper } from '../../utils';
import { PersonService, UserService } from '../../services';
import { MatCard, MatCardTitle, MatCardContent } from '@angular/material/card';
import { MatToolbar } from '@angular/material/toolbar';
import { MatIconButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { PersonFamilyComponent } from './person-family.component';
import { MatTooltip } from '@angular/material/tooltip';
import {
    AddLinkPersonDialogComponent,
    AddLinkPersonDialogResult
} from './add-link-person-dialog.component';

/**
 * Implements a the list of families on a person page
 *
 * Inputs:
 *  person: the person this page is for
 */
@Component({
    standalone: true,
    selector: 'app-person-family-list',
    template: `<mat-card>
    <mat-card-title class="custom-section-colors">
    <mat-toolbar class="custom-section-colors">
    <span class="list-toolbar-title custom-section-colors">Spouses and Children</span>
    <span class="example-fill-remaining-space custom-section-colors"></span>
      <button mat-icon-button
        [attr.aria-label]="showSpousesAndChildren ? 'Collapse spouses and children' : 'Expand spouses and children'"
        (click)="showSpousesAndChildren = !showSpousesAndChildren">
        <mat-icon>{{ showSpousesAndChildren ? 'expand_less' : 'expand_more' }}</mat-icon>
      </button>
    </mat-toolbar>
  </mat-card-title>
    @if (showSpousesAndChildren) {
    <mat-card-content class="custom-section-colors">
    <div cdkDropList class="family-list custom-section-colors" (cdkDropListDropped)="drop($event)"
        [cdkDropListDisabled]="!hasSignedIn()">
      @for (attribute of person.famss; track $index; let i = $index) {
        <div cdkDrag class="{{ hasSignedIn() ? 'family-box' : '' }}">
          <app-person-family
              [dataset]="dataset" [person]="person" [parent]="this" [string]="attribute.string"
              [index]="i"></app-person-family>
        </div>
      }
    </div>
    @if (hasSignedIn()) {
      <div class="family-action-buttons">
                <button mat-icon-button color="primary" aria-label="Add spouse" matTooltip="Add spouse" (click)="openSpouseDialog()">
                    <mat-icon>person_add</mat-icon>
                </button>
                <button mat-icon-button color="primary" aria-label="Add child" matTooltip="Add child" (click)="openChildDialog()">
                    <mat-icon>person_add</mat-icon>
                </button>
      </div>
    }
  </mat-card-content>
    }
</mat-card>`,
    styles: [`
.family-action-buttons {
  display: flex;
  flex-wrap: wrap;
  gap: 4px;
}
`],
    imports: [MatCard, MatCardTitle, MatToolbar, MatIconButton, MatIcon, MatCardContent, CdkDropList, CdkDrag, PersonFamilyComponent, MatTooltip]
})
export class PersonFamilyListComponent extends InitablePersonCreator implements LinkCheck {
    @Input() dataset: string;
    @Input() parent: HasPerson & Saveable;
    @Input() person: ApiPerson;
    partnerSurname: string;
    childSurname: string;
    partnerSex: string;
    childSex = 'M';
    showSpousesAndChildren = true;
    _ub: UrlBuilder;

    constructor(@Inject(PersonService) public readonly personService: PersonService,
        @Inject(UserService) private readonly userService: UserService,
        @Inject(MatDialog) private readonly dialog: MatDialog,
        @Inject(NgZone) private readonly zone: NgZone,
        @Inject(ChangeDetectorRef) private readonly cdr: ChangeDetectorRef) {
        super(personService);
    }

    override ngOnChanges(): void {
        this.init();
        this.cdr.markForCheck();
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
            (person: ApiPerson) => {
                this.zone.run(() => {
                    this.updatePerson(person);
                    this.cdr.markForCheck();
                });
            });
    }

    private updatePerson(person: ApiPerson) {
        this.person = person;
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
        const famss = mainPerson.famss.at(-1);
        const ub = new UrlBuilder(this.dataset, 'families', 'children');
        for (const selected of data.selected) {
            this.personService.putLink(ub, famss.string, selected.person)
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

    openSpouseDialog(): void {
        this.openAddOrLinkDialog('spouse');
    }

    openChildDialog(): void {
        this.openAddOrLinkDialog('child');
    }

    private openAddOrLinkDialog(relationship: 'spouse' | 'child'): void {
        const defaults = relationship === 'spouse'
            ? NewPersonHelper.initNew(this.partnerSex, this.partnerSurname)
            : NewPersonHelper.initNew(this.childSex, this.childSurname);
        const title = relationship === 'spouse' ? 'Add spouse' : 'Add child';
        const dialogRef = this.dialog.open(AddLinkPersonDialogComponent, {
            width: '72rem',
            maxWidth: '95vw',
            data: {
                title,
                defaultNewPerson: defaults
            }
        });

        dialogRef.afterClosed().subscribe((result: AddLinkPersonDialogResult | undefined) => {
            if (!result) {
                return;
            }

            if (result.mode === 'new' && result.newPersonData) {
                if (relationship === 'spouse') {
                    this.createSpouse(result.newPersonData);
                } else {
                    this.createChild(result.newPersonData);
                }
                return;
            }

            if (result.mode === 'existing' && result.existingPersonId) {
                if (relationship === 'spouse') {
                    this.linkSpouse(LinkPersonDialogData.fromPersonId(result.existingPersonId));
                } else {
                    this.linkChildren(LinkPersonDialogData.fromPersonId(result.existingPersonId));
                }
            }
        });
    }

    drop(event: CdkDragDrop<string[]>) {
        moveItemInArray(this.person.famss, event.previousIndex, event.currentIndex);
        this.parent.save();
    }

    hasSignedIn() {
        return !!this.userService.currentUser;
    }
}
