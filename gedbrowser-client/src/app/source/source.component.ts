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
  imageUtil: ImageUtil;
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
    this.imageUtil = new ImageUtil();
    this.galleryOptions = this.imageUtil.galleryOptions();
  }

  /**
   * Remove family links and images.
   * Those will be handled elsewhere.
   */
  strippedAttributes(): Array<ApiAttribute> {
    const stripped: Array<ApiAttribute> = new Array<ApiAttribute>();
    for (const attribute of this.source.attributes) {
      if (!this.imageUtil.isImageWrapper(attribute)) {
        stripped.push(attribute);
      }
    }
    return stripped;
  }

  imageAttributes(): Array<ApiAttribute> {
    return this.imageUtil.imageAttributes(this.source.attributes);
  }

  galleryImages(): Array<NgxGalleryImage> {
    return this.imageUtil.galleryImages(this.source.attributes);
  }
}
