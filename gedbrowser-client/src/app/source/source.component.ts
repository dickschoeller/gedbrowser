import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {NgxGalleryOptions, NgxGalleryImage, NgxGalleryAnimation,
  NgxGalleryImageSize, NgxGalleryComponent, NgxGalleryLayout,
  NgxGalleryOrder} from 'ngx-gallery';

import {
  ApiSource,
  AttributeListComponent,
  SourceService,
ApiAttribute,
ImageUtil,
} from '../shared';

@Component({
  selector: 'app-source',
  templateUrl: './source.component.html',
  styleUrls: ['./source.component.css']
})
export class SourceComponent implements OnInit {
  source: ApiSource;
  galleryOptions: Array<NgxGalleryOptions>;

  constructor(private route: ActivatedRoute,
    private sourceService: SourceService,
    private router: Router
  ) {}

  ngOnInit() {
    this.route.data.subscribe(
      (data: {source: ApiSource}) => {
        this.source = data.source;
      }
    );

    this.galleryOptions = [
      {
        image: false,
        preview: true,
        previewCloseOnClick: true,
        previewCloseOnEsc: true,
        previewKeyboardNavigation: true,
        previewFullscreen: true,
        height: '100px',
        thumbnailsColumns: 4,
      },
      {
        preview: true,
        breakpoint: 500,
        width: '300px',
        thumbnailsColumns: 3,
      },
      {
        breakpoint: 300,
        width: '100%',
        thumbnailsColumns: 2,
      }
    ];
  }

  /**
   * Remove family links and images.
   * Those will be handled elsewhere.
   */
  strippedAttributes(): Array<ApiAttribute> {
    const stripped: Array<ApiAttribute> = new Array<ApiAttribute>();
    for (const attribute of this.source.attributes) {
      if (!new ImageUtil(attribute).isImageWrapper()) {
        stripped.push(attribute);
      }
    }
    return stripped;
  }

  imageAttributes(): Array<ApiAttribute> {
    const images: Array<ApiAttribute> = new Array<ApiAttribute>();
    for (const attribute of this.source.attributes) {
      if (new ImageUtil(attribute).isImageWrapper()) {
        images.push(attribute);
      }
    }
    return images;
  }

  galleryImages(): Array<any> {
    const gallery: Array<any> = new Array<any>();
    for (const attribute of this.imageAttributes()) {
      gallery.push(new ImageUtil(attribute).galleryImage());
    }
    return gallery;
  }
}
