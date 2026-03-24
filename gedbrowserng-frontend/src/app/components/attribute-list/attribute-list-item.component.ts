import { Component, Input , Inject } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { HasAttributeList } from '../../interfaces';
import { ApiAttribute, AttributeDialogData, SelectItem } from '../../models';
import { UserService } from '../../services';
import { AttributeDialogHelper, AttributeAnalyzer } from '../../utils';

import { HasAttributeDialog } from './has-attribute-dialog';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { AttributeListItemDetailListComponent } from './attribute-list-item-detail-list.component';
import { MatIconButton } from '@angular/material/button';
import { MatTooltip } from '@angular/material/tooltip';
import { MatIcon } from '@angular/material/icon';
import { MultimediaEditButtonComponent } from '../multimedia-edit-button/multimedia-edit-button.component';
import { SourceButtonComponent } from '../source-button/source-button.component';

@Component({
    selector: 'app-attribute-list-item',
    template: `<div class="parent">
    <b class="attribute-label">{{ attributeUtil.label() }}:&nbsp;</b>
    @if (attributeUtil.contents()) {
        <span>
            @if (href()) {
                <span><a href="{{ href() }}">[{{ attributeUtil.contents() }}]</a></span>
            } @else {
                <span>{{ attributeUtil.contents() }}</span>
            }
            @if (attribute?.attributes.length) { <span>,</span> }
        </span>
    }
  <app-attribute-list-item-detail-list [dataset]="dataset" [attributes]="attribute?.attributes">
    </app-attribute-list-item-detail-list>
  <span class="example-fill-remaining-space"></span>
    @if (attributeUtil.editable() && hasSignedIn()) {
        <span class="hidden">
            <button mat-icon-button matTooltip="Edit" color="primary" (click)="edit()">
                <mat-icon matListIcon>edit</mat-icon></button>
            @if (attributeUtil.multimedia()) {
                <app-multimedia-edit-button
                        [parent]="this" [dataset]="dataset" [attributes]="attributeList" [index]="index">
                </app-multimedia-edit-button>
            }
            <button mat-icon-button matTooltip="Delete" color="warn" (click)="delete()">
                <mat-icon matListIcon>delete</mat-icon></button>
            @if (!href()) {
                <app-source-button [parent]="this" [dataset]="dataset"></app-source-button>
            }
        </span>
    }
</div>`,
    styles: [],
    imports: [AttributeListItemDetailListComponent, MatIconButton, MatTooltip, MatIcon, MultimediaEditButtonComponent, SourceButtonComponent]
})
export class AttributeListItemComponent extends HasAttributeDialog {
    @Input() attribute: ApiAttribute;
    @Input() attributeList: Array<ApiAttribute>;
    @Input() index: number;
    @Input() parent: HasAttributeList;
    @Input() dataset: string;

    attributeUtil = new AttributeAnalyzer(this);
    attributeDialogHelper: AttributeDialogHelper = new AttributeDialogHelper(this);
    _data: AttributeDialogData;
    get attributes(): Array<ApiAttribute> {
        return this.attribute.attributes;
    }

    constructor(@Inject(MatDialog) public readonly dialog: MatDialog,
        @Inject(UserService) private readonly userService: UserService) {
        super(dialog);
    }

    edit() {
        this.openAttributeDialog(result => { this.modifyAttribute(result); });
    }

    defaultData(): AttributeDialogData {
        const adh: AttributeDialogHelper = new AttributeDialogHelper(this);
        return adh.buildData(false);
    }

    modifyAttribute(data: AttributeDialogData) {
        this.attributeDialogHelper.populateParentAttribute(data);
        this.parent.save();
    }

    options(): Array<SelectItem> {
        return this.parent.options();
    }

    delete(): void {
        const dialogRef = this.dialog.open(ConfirmDialogComponent, {
            data: { message: 'Are you sure you want to delete this attribute?' }
        });
        dialogRef.afterClosed().subscribe((confirmed: boolean) => {
            if (confirmed) {
                const index = this.attributeList.indexOf(this.attribute);
                this.attributeList.splice(index, 1);
                this.parent.save();
            }
        });
    }

    href() {
        return this.attributeUtil.href();
    }

    save() {
        this.parent.save();
    }

    hasSignedIn() {
        return !!this.userService.currentUser;
    }
}
