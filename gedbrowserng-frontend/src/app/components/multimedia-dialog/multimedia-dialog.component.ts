import { Component, Inject, Input, EventEmitter, OnInit, Output } from '@angular/core';
import { CdkDragDrop, moveItemInArray } from '@angular/cdk/drag-drop';

import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

import { MultimediaDialogData, MultimediaFileData, MultimediaFormat, MultimediaSourceType } from '../../models';
import { SelectItem } from '../../models/select-item';
import { UserService } from '../../services';

@Component({
    selector: 'app-multimedia-dialog',
    templateUrl: './multimedia-dialog.component.html',
    styleUrls: ['./multimedia-dialog.component.css']
})
export class MultimediaDialogComponent implements OnInit {
    formats: Array<SelectItem>;
    sourceTypes: Array<SelectItem>;

    constructor(
        public dialogRef: MatDialogRef<MultimediaDialogComponent>,
        private userService: UserService,
        @Inject(MAT_DIALOG_DATA) public data: MultimediaDialogData) {
    }

    ngOnInit() {
        this.formats = this.initFormats();
        this.sourceTypes = this.initSourceTypes();
    }

    onNoClick(): void {
        this.dialogRef.close();
    }

    initFormats(): Array<SelectItem> {
        const formats: Array<SelectItem> = new Array<SelectItem>();
        for (const formatString of Object.keys(MultimediaFormat)) {
            formats.push({ label: formatString, value: MultimediaFormat[formatString] });
        }
        return formats;
    }

    initSourceTypes(): Array<SelectItem> {
        const sourceTypes: Array<SelectItem> = new Array<SelectItem>();
        for (const sourceTypeString of Object.keys(MultimediaSourceType)) {
            sourceTypes.push({ label: sourceTypeString, value: MultimediaSourceType[sourceTypeString] });
        }
        return sourceTypes;
    }

    drop(event: CdkDragDrop<string[]>) {
        moveItemInArray(this.data.files, event.previousIndex, event.currentIndex);
    }

    hasSignedIn() {
        return !!this.userService.currentUser;
    }
}
