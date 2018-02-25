import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {NgxGalleryOptions, NgxGalleryImage} from 'ngx-gallery';
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
  galleryOptions: Array<NgxGalleryOptions> = new Array<NgxGalleryOptions>();
  strippedAttributes: Array<ApiAttribute> = new Array<ApiAttribute>();
  imageAttributes: Array<ApiAttribute> = new Array<ApiAttribute>();

  constructor(private route: ActivatedRoute,
    private service: SourceService,
    private router: Router
  ) {}

  ngOnInit() {
    this.route.data.subscribe(
      (data: {source: ApiSource}) => {
        this.source = data.source;
        this.initLists();
      }
    );
  }

  initLists() {
    this.imageUtil = new ImageUtil();
    this.galleryOptions = this.imageUtil.galleryOptions();
    this.strippedAttributes = this.createStrippedAttributes();
    this.imageAttributes = this.createImageAttributes();
  }

  createStrippedAttributes(): Array<ApiAttribute> {
    const stripped: Array<ApiAttribute> = new Array<ApiAttribute>();
    for (const attribute of this.source.attributes) {
      if (!this.imageUtil.isImageWrapper(attribute)) {
        stripped.push(attribute);
      }
    }
    return stripped;
  }

  createImageAttributes(): Array<ApiAttribute> {
    return this.imageUtil.imageAttributes(this.source.attributes);
  }

  galleryImages(): Array<NgxGalleryImage> {
    return this.imageUtil.galleryImages(this.source.attributes);
  }

  save() {
    this.source.attributes = new Array<ApiAttribute>()
      .concat(this.strippedAttributes)
      .concat(this.imageAttributes);
    this.service.put('schoeller', this.source).subscribe(
      (data: ApiSource) => {
        this.source = data;
        this.initLists();
      }
    );
  }
}
