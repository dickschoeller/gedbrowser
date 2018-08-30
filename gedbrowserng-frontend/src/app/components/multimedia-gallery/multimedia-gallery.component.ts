import { Component, OnInit, Input } from '@angular/core';
import { NgxGalleryOptions, NgxGalleryImage, NgxGalleryAction } from 'ngx-gallery';

import { HasMultimedia, Saveable } from '../../interfaces';
import { ApiAttribute, MultimediaDialogData, MultimediaFileData, MultimediaFormat } from '../../models';
import { ImageUtil, StringUtil, MultimediaDialogHelper, ArrayUtil } from '../../utils';
import { MultimediaDialogComponent, } from '../multimedia-dialog';


@Component({
  selector: 'app-multimedia-gallery',
  templateUrl: './multimedia-gallery.component.html',
  styleUrls: ['./multimedia-gallery.component.css']
})
export class MultimediaGalleryComponent implements OnInit, HasMultimedia {
  @Input() dataset: string;
  @Input() parent: Saveable;
  @Input() multimedia: Array<ApiAttribute>;
  @Input() styleClass: string;
  galleryOptions: NgxGalleryOptions[];
  displayDialog = false;
  dialogIndex = -1;

  constructor() { }

  ngOnInit() {
    this.galleryOptions = this.buildGalleryOptions();
  }

  galleryImages(): Array<NgxGalleryImage> {
    if (this.multimedia === undefined || this.multimedia == null || this.multimedia.length === 0) {
      return new Array<NgxGalleryImage>();
    }
    return ImageUtil.galleryImages(this.multimedia);
  }

  galleryImageActions() {
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
    this.displayDialog = true;
    this.dialogIndex = i;
    this.onDialogOpen = this.onDialogOpen.bind(this);
    this.create = this.create.bind(this);
  }

  deleteButtonClicked(event, i) {
    this.multimedia.splice(i, 1);
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

  onDialogClose() {
    this.displayDialog = false;
  }

  onDialogOpen(data: MultimediaDialogComponent) {
    if (data !== undefined) {
      data._data = MultimediaDialogHelper.buildMultimediaDialogData(this.multimedia, this.dialogIndex);
    }
  }

  create(data: MultimediaDialogData) {
    const attribute: ApiAttribute = MultimediaDialogHelper.buildMultimediaAttribute(data);
    this.multimedia.splice(this.dialogIndex, 1, attribute);
    this.save();
  }
}
