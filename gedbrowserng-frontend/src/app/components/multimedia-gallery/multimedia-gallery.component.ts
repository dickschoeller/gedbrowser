import { Component, OnInit, Input , Inject, ChangeDetectorRef, OnChanges, SimpleChanges } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { NgxGalleryOptions, NgxGalleryImage, NgxGalleryAction, NgxGalleryModule } from 'ngx-gallery-15';

import { HasMultimedia, Saveable } from '../../interfaces';
import { ApiAttribute, MultimediaDialogData, MultimediaFileData, MultimediaFormat } from '../../models';
import { ImageUtil, StringUtil, MultimediaDialogHelper, ArrayUtil } from '../../utils';
import { MultimediaDialogComponent, } from '../multimedia-dialog';
import { UserService } from '../../services';
import { MatCard, MatCardTitle, MatCardContent } from '@angular/material/card';
import { MatToolbar } from '@angular/material/toolbar';
import { MatIconButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { MultimediaAddButtonComponent } from '../multimedia-add-button/multimedia-add-button.component';

@Component({
    selector: 'app-multimedia-gallery',
    template: `<mat-card>
  <mat-card-title>
    <mat-toolbar>
            <span class="list-toolbar-title">Multimedia</span>
      <span class="example-fill-remaining-space"></span>
            @if (hasSignedIn()) {
                <span>
                    <app-multimedia-add-button [parent]="this" [dataset]="dataset"></app-multimedia-add-button>
                </span>
            }
            <button mat-icon-button
                    [attr.aria-label]="showMultimedia ? 'Collapse multimedia' : 'Expand multimedia'"
                    (click)="showMultimedia = !showMultimedia">
                <mat-icon>{{ showMultimedia ? 'expand_less' : 'expand_more' }}</mat-icon>
            </button>
    </mat-toolbar>
  </mat-card-title>
        @if (showMultimedia) {
    @if (galleryImagesList.length) {
        <mat-card-content>
            <ngx-gallery [options]="galleryOptions" [images]="galleryImagesList"></ngx-gallery>
        </mat-card-content>
    }
    @if (!galleryImagesList.length) {
        <mat-card-content></mat-card-content>
    }
    }
</mat-card>`,
    styles: [],
    imports: [MatCard, MatCardTitle, MatToolbar, MatIconButton, MatIcon, MultimediaAddButtonComponent, MatCardContent, NgxGalleryModule]
})
export class MultimediaGalleryComponent implements OnInit, OnChanges, HasMultimedia {
    @Input() dataset: string;
    @Input() parent: Saveable;
    @Input() multimedia: Array<ApiAttribute>;
    @Input() styleClass: string;
    galleryOptions: NgxGalleryOptions[];
    galleryImagesList: Array<NgxGalleryImage> = [];
    dialogIndex = -1;
    showMultimedia = true;

    constructor(@Inject(MatDialog) @Inject(MatDialog) @Inject(MatDialog) @Inject(MatDialog) public readonly dialog: MatDialog,
        @Inject(UserService) @Inject(UserService) @Inject(UserService) private readonly userService: UserService,
        private readonly cdr: ChangeDetectorRef) { }

    ngOnInit() {
        this.galleryOptions = this.buildGalleryOptions();
        this.refreshGalleryImages();
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (changes.multimedia) {
            this.refreshGalleryImages();
        }
    }

    galleryImages(): Array<NgxGalleryImage> {
        this.refreshGalleryImages();
        return this.galleryImagesList;
    }

    galleryImageActions() {
        if (!this.hasSignedIn()) {
            return;
        }
        this.editButtonClicked = this.editButtonClicked.bind(this);
        this.deleteButtonClicked = this.deleteButtonClicked.bind(this);

        const editAction = <NgxGalleryAction>{
            icon: 'fa fa-pencil',
            titleText: 'Edit',
            onClick: this.editButtonClicked
        };

        const deleteAction = <NgxGalleryAction>{
            icon: 'fa fa-trash',
            titleText: 'Delete',
            onClick: this.deleteButtonClicked
        };

        return [editAction, deleteAction];
    }

    editButtonClicked(event, i) {
        this.dialogIndex = i;
        this.update = this.update.bind(this);
        const dialogRef = this.dialog.open(
            MultimediaDialogComponent,
            {
                data: MultimediaDialogHelper.buildMultimediaDialogData(this.multimedia, this.dialogIndex)
            });

        dialogRef.afterClosed().subscribe((result: MultimediaDialogData) => {
            if (result !== undefined) {
                this.update(result);
            }
        });
    }

    deleteButtonClicked(event, i) {
        this.multimedia.splice(i, 1);
        this.forceViewRefresh();
        this.save();
    }

    buildGalleryOptions(): Array<NgxGalleryOptions> {
        return [
            this.galleryOptionsDefault(),
            this.galleryOptionsMediumWidth(),
            this.galleryOptionsNarrow()
        ];
    }

    private galleryOptionsDefault(): NgxGalleryOptions {
        return {
            image: false,
            preview: true,
            previewCloseOnClick: true,
            previewCloseOnEsc: true,
            previewKeyboardNavigation: true,
            previewFullscreen: true,
            height: '200px',
            width: '800px',
            thumbnailsColumns: 6,
            thumbnailActions: this.galleryImageActions(),
            imageActions: this.galleryImageActions(),
        };
    }

    private galleryOptionsMediumWidth(): NgxGalleryOptions {
        return {
            preview: true,
            breakpoint: 500,
            width: '300px',
            thumbnailsColumns: 3,
        };
    }

    private galleryOptionsNarrow(): NgxGalleryOptions {
        return {
            breakpoint: 300,
            width: '100%',
            thumbnailsColumns: 2,
        };
    }

    save(): void {
        this.parent.save();
    }

    update(data: MultimediaDialogData) {
        const updatedAttribute = MultimediaDialogHelper.buildMultimediaAttribute(data);
        this.multimedia.splice(this.dialogIndex, 1, updatedAttribute);
        this.forceViewRefresh();
        this.save();
    }

    refreshMultimedia(): void {
        this.forceViewRefresh();
    }

    hasSignedIn() {
        return !!this.userService.currentUser;
    }

    private refreshGalleryImages(): void {
        if (!this.multimedia || this.multimedia.length === 0) {
            this.galleryImagesList = [];
            return;
        }
        try {
            this.galleryImagesList = ImageUtil.galleryImages(this.multimedia);
        } catch {
            this.galleryImagesList = [];
        }
    }

    private forceViewRefresh(): void {
        this.refreshGalleryImages();
        queueMicrotask(() => this.cdr?.detectChanges());
    }
}
