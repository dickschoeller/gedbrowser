import { Component, OnInit, Input, Inject, ChangeDetectorRef, OnChanges, SimpleChanges, AfterViewChecked } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { LightgalleryModule } from 'lightgallery/angular';
import { LightGallerySettings } from 'lightgallery/lg-settings';
import { BeforeSlideDetail, InitDetail } from 'lightgallery/lg-events';
import lgThumbnail from 'lightgallery/plugins/thumbnail';
import lgVideo from 'lightgallery/plugins/video';
import type { LightGallery } from 'lightgallery/lightgallery';

import { HasMultimedia, Saveable } from '../../interfaces';
import { ApiAttribute, MultimediaDialogData } from '../../models';
import { ImageUtil, MultimediaDialogHelper, GalleryImage } from '../../utils';
import { MultimediaDialogComponent } from '../multimedia-dialog';
import { UserService } from '../../services';
import { MatCard, MatCardTitle, MatCardContent } from '@angular/material/card';
import { MatToolbar } from '@angular/material/toolbar';
import { MatIconButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { MultimediaAddButtonComponent } from '../multimedia-add-button/multimedia-add-button.component';

@Component({
    selector: 'app-multimedia-gallery',
    template: `<mat-card class="custom-section-colors">
    <mat-card-title class="custom-section-colors">
        <mat-toolbar class="custom-section-colors">
            <span class="list-toolbar-title custom-section-colors">Multimedia</span>
            <span class="example-fill-remaining-space custom-section-colors"></span>
            @if (hasSignedIn()) {
                <span>
                    <app-multimedia-add-button [parent]="this" [dataset]="dataset"></app-multimedia-add-button>
                </span>
            }
            <button mat-icon-button [attr.aria-label]="showMultimedia ? 'Collapse multimedia' : 'Expand multimedia'" (click)="showMultimedia = !showMultimedia">
                <mat-icon>{{ showMultimedia ? 'expand_less' : 'expand_more' }}</mat-icon>
            </button>
        </mat-toolbar>
    </mat-card-title>
    @if (showMultimedia) {
        @if (galleryImagesList.length) {
            <mat-card-content class="custom-section-colors">
                <lightgallery [settings]="lightGallerySettings" [onInit]="onGalleryInit" [onBeforeSlide]="onBeforeSlide" class="custom-section-colors">
                    @for (image of galleryImagesList; track image.url; let i = $index) {
                        <a [href]="image.url" [attr.data-src]="image.mediaType === 'video' ? null : image.url" [attr.data-poster]="image.mediaType === 'video' ? image.small : null" [attr.data-sub-html]="escapeHtml(image.description) || 'Image'" [attr.data-gallery-index]="i" [attr.data-video]="image.mediaType === 'video' ? image.videoData : null" class="multimedia-thumb-wrapper">
                            <img [src]="image.small" [alt]="image.description || 'Image'" class="multimedia-thumb multimedia-video-preview" />
                            @if (image.mediaType === 'video' || image.mediaType === 'youtube') {
                                <div class="multimedia-video-play">
                                    <i class="fa fa-play-circle"></i>
                                </div>
                            }
                            @if (hasSignedIn()) {
                                <div class="multimedia-thumb-overlay">
                                    <button mat-icon-button class="multimedia-thumb-action" (click)="editButtonClicked($event, i)" [attr.aria-label]="'Edit image ' + (i + 1)">
                                        <i class="fa fa-pencil"></i>
                                    </button>
                                    <button mat-icon-button class="multimedia-thumb-action" (click)="deleteButtonClicked($event, i)" [attr.aria-label]="'Delete image ' + (i + 1)">
                                        <i class="fa fa-trash"></i>
                                    </button>
                                </div>
                            }
                        </a>
                    }
                </lightgallery>
            </mat-card-content>
        }
        @if (!galleryImagesList.length) {
            <mat-card-content class="custom-section-colors"></mat-card-content>
        }
    }
</mat-card>`,
    styles: [
        '.multimedia-thumb-wrapper { position: relative; display: inline-block; width: 120px; height: 90px; margin: 0 6px 6px 0; }',
        '.multimedia-thumb { width: 120px; height: 90px; object-fit: cover; border-radius: 4px; display: block; }',
        '.multimedia-video-preview { pointer-events: none; background: #111; }',
        '.multimedia-video-play { position: absolute; inset: 0; display: flex; align-items: center; justify-content: center; color: #fff; font-size: 28px; text-shadow: 0 1px 3px rgba(0, 0, 0, 0.8); pointer-events: none; }',
        '.multimedia-thumb-overlay { position: absolute; top: 0; left: 0; right: 0; bottom: 0; display: flex; align-items: center; justify-content: center; gap: 4px; background: rgba(0, 0, 0, 0.5); border-radius: 4px; opacity: 0; visibility: hidden; transition: opacity 0.2s, visibility 0.2s; pointer-events: none; }',
        '.multimedia-thumb-wrapper:hover .multimedia-thumb-overlay, .multimedia-thumb-wrapper:focus-within .multimedia-thumb-overlay { opacity: 1; visibility: visible; pointer-events: auto; }',
        '.multimedia-thumb-action { color: white !important; width: 32px; height: 32px; min-width: 32px; min-height: 32px; background: rgba(0, 0, 0, 0.7) !important; }',
        '.multimedia-thumb-action:hover { background: rgba(0, 0, 0, 0.9) !important; }',
        '.multimedia-thumb-action .fa { font-size: 16px; }'
    ],
    imports: [MatCard, MatCardTitle, MatToolbar, MatIconButton, MatIcon, MultimediaAddButtonComponent, MatCardContent, LightgalleryModule]
})
export class MultimediaGalleryComponent implements OnInit, OnChanges, AfterViewChecked, HasMultimedia {
    @Input() dataset: string;
    @Input() parent: Saveable;
    @Input() multimedia: Array<ApiAttribute>;
    @Input() styleClass: string;

    galleryImagesList: Array<GalleryImage> = [];
    selectedImageIndex = 0;
    dialogIndex = -1;
    showMultimedia = true;
    lightGallerySettings: LightGallerySettings = {
        plugins: [lgThumbnail, lgVideo],
        thumbnail: true,
        download: false,
        counter: true,
        selector: 'a'
    };

    onBeforeSlide = (detail: BeforeSlideDetail): void => {
        this.selectedImageIndex = detail.index;
    };

    onGalleryInit = (detail: InitDetail): void => {
        this.lightGallery = detail.instance;
    };

    private lightGallery?: LightGallery;
    private needGalleryRefresh = false;

    constructor(
        @Inject(MatDialog) public readonly dialog: MatDialog,
        @Inject(UserService) private readonly userService: UserService,
        private readonly cdr: ChangeDetectorRef
    ) {}

    ngOnInit(): void {
        this.refreshGalleryImages();
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (changes.multimedia) {
            this.refreshGalleryImages();
        }
    }

    ngAfterViewChecked(): void {
        if (this.needGalleryRefresh && this.lightGallery) {
            this.lightGallery.refresh();
            this.needGalleryRefresh = false;
        }
    }

    galleryImages(): Array<GalleryImage> {
        this.refreshGalleryImages();
        return this.galleryImagesList;
    }

    editButtonClicked(event: Event, i: number): void {
        event.preventDefault();
        event.stopPropagation();
        this.dialogIndex = i;
        this.update = this.update.bind(this);
        const dialogRef = this.dialog.open(MultimediaDialogComponent, {
            data: MultimediaDialogHelper.buildMultimediaDialogData(this.multimedia, this.dialogIndex)
        });

        dialogRef.afterClosed().subscribe((result: MultimediaDialogData) => {
            if (result !== undefined) {
                this.update(result);
            }
        });
    }

    deleteButtonClicked(event: Event, i: number): void {
        event.preventDefault();
        event.stopPropagation();
        this.multimedia.splice(i, 1);
        this.selectedImageIndex = Math.max(0, Math.min(this.selectedImageIndex, this.multimedia.length - 1));
        this.forceViewRefresh();
        this.save();
    }

    save(): void {
        this.parent.save();
    }

    update(data: MultimediaDialogData): void {
        const updatedAttribute = MultimediaDialogHelper.buildMultimediaAttribute(data);
        this.multimedia.splice(this.dialogIndex, 1, updatedAttribute);
        this.forceViewRefresh();
        this.save();
    }

    refreshMultimedia(): void {
        this.forceViewRefresh();
    }

    hasSignedIn(): boolean {
        return !!this.userService.currentUser;
    }

    /**
     * Escapes HTML special characters in user-supplied text to prevent XSS
     * when content is injected into HTML attributes (e.g. data-sub-html).
     */
    escapeHtml(text: string | null | undefined): string {
        return (text || '')
            .replaceAll('&', '&amp;')
            .replaceAll('<', '&lt;')
            .replaceAll('>', '&gt;')
            .replaceAll('"', '&quot;')
            .replaceAll("'", '&#39;');
    }

    private refreshGalleryImages(): void {
        if (!this.multimedia || this.multimedia.length === 0) {
            this.galleryImagesList = [];
            this.selectedImageIndex = 0;
            this.needGalleryRefresh = true;
            return;
        }

        try {
            this.galleryImagesList = ImageUtil.galleryImages(this.multimedia);
            this.selectedImageIndex = Math.min(this.selectedImageIndex, Math.max(0, this.galleryImagesList.length - 1));
        } catch {
            this.galleryImagesList = [];
            this.selectedImageIndex = 0;
        }
        this.needGalleryRefresh = true;
    }

    private forceViewRefresh(): void {
        this.refreshGalleryImages();
        queueMicrotask(() => this.cdr?.detectChanges());
    }
}
