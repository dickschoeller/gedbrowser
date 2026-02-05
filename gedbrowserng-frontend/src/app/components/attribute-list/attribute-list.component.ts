import { Component, OnInit, Input, OnChanges , Inject } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { CdkDragDrop, moveItemInArray, CdkDropList, CdkDrag } from '@angular/cdk/drag-drop';

import { ApiAttribute, AttributeDialogData, SelectItem } from '../../models';
import { AttributeDialogHelper, AttributeAnalyzer } from '../../utils';
import { HasAttributeList } from '../../interfaces';

import { HasAttributeDialog } from './has-attribute-dialog';
import { UserService } from '../../services';
import { MatCard, MatCardTitle, MatCardContent } from '@angular/material/card';
import { MatToolbar } from '@angular/material/toolbar';
import { NgIf, NgFor } from '@angular/common';
import { MatIconButton } from '@angular/material/button';
import { MatTooltip } from '@angular/material/tooltip';
import { MatIcon } from '@angular/material/icon';
import { NoteButtonComponent } from '../note-button/note-button.component';
import { SourceButtonComponent } from '../source-button/source-button.component';
import { SubmitterButtonComponent } from '../submitter-button/submitter-button.component';
import { AttributeListItemComponent } from './attribute-list-item.component';

@Component({
    selector: 'app-attribute-list',
    template: `<mat-card>
  <mat-card-title>
    <mat-toolbar>
      Attributes
      <span class="example-fill-remaining-space"></span>
      <span *ngIf="hasSignedIn()">
        <button (click)="openCreateAttributeDialog()" mat-icon-button color="primary"
            matTooltip="Add attribute"><mat-icon>add_box</mat-icon></button>
        <app-note-button *ngIf="showNotes" [parent]="this" [dataset]="dataset"></app-note-button>
        <app-source-button *ngIf="showSources" [parent]="this" [dataset]="dataset"></app-source-button>
        <app-submitter-button *ngIf="showSubmitters" [parent]="this" [dataset]="dataset"></app-submitter-button>
      </span>
    </mat-toolbar>
  </mat-card-title>
  <mat-card-content>
    <div cdkDropList class="attribute-list" (cdkDropListDropped)="drop($event)"
        [cdkDropListDisabled]="!hasSignedIn()">
      <div cdkDrag class="{{ hasSignedIn() ? 'attribute-box' : '' }}"
          *ngFor="let attribute of attributes; let i=index">
        <app-attribute-list-item
            [parent]="parent" [dataset]="dataset" [attribute]="attribute"
            [attributeList]="attributes" [index]="i"></app-attribute-list-item>
      </div>
    </div>
  </mat-card-content>
</mat-card>`,
    styles: [],
    imports: [MatCard, MatCardTitle, MatToolbar, NgIf, MatIconButton, MatTooltip, MatIcon, NoteButtonComponent, SourceButtonComponent, SubmitterButtonComponent, MatCardContent, CdkDropList, NgFor, CdkDrag, AttributeListItemComponent]
})
export class AttributeListComponent extends HasAttributeDialog implements OnInit, OnChanges, HasAttributeList {
    @Input() attributes: Array<ApiAttribute>;
    @Input() parent: HasAttributeList;
    @Input() toggleable = false;
    @Input() styleClass: string;
    @Input() dataset: string;
    @Input() showAdd = true;
    @Input() showNotes = true;
    @Input() showSources = true;
    @Input() showSubmitters = true;

    index;
    attributeDialogHelper = new AttributeDialogHelper(this);
    attributeUtil = new AttributeAnalyzer(this);

    constructor(@Inject(MatDialog) @Inject(MatDialog) @Inject(MatDialog) @Inject(MatDialog) public dialog: MatDialog,
        @Inject(UserService) @Inject(UserService) @Inject(UserService) private userService: UserService) {
        super(dialog);
    }

    ngOnInit() {
        this.index = this.attributeUtil.lastIndex();
    }

    ngOnChanges() {
        this.index = this.attributeUtil.lastIndex();
    }

    openCreateAttributeDialog() {
        this.openAttributeDialog(result => { this.createAttribute(result); });
    }

    defaultData(): AttributeDialogData {
        return this.parent.defaultData();
    }

    createAttribute(data: AttributeDialogData) {
        if (data != null) {
            const attribute: ApiAttribute =
                this.attributeDialogHelper.populateNewAttribute(data);
            this.attributes.splice(0, 0, attribute);
            this.parent.save();
        }
    }

    options(): Array<SelectItem> {
        return this.parent.options();
    }

    drop(event: CdkDragDrop<string[]>) {
        moveItemInArray(this.attributes, event.previousIndex, event.currentIndex);
        this.save();
    }

    save() {
        this.parent.save();
    }

    hasSignedIn() {
        return !!this.userService.currentUser;
    }
}
