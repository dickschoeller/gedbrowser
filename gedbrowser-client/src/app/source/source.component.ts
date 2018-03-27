import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {NgxGalleryOptions, NgxGalleryImage} from 'ngx-gallery';

import {ApiSource, ApiAttribute} from '../shared/models';
import {SourceService} from '../shared/services';
import {ImageUtil} from '../shared/util';
import {AttributeListComponent} from '../shared';

@Component({
  selector: 'app-source',
  templateUrl: './source.component.html',
  styleUrls: ['./source.component.css']
})
export class SourceComponent implements OnInit {
  source: ApiSource;
  imageUtil = new ImageUtil();
  galleryOptions = this.imageUtil.galleryOptions();

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
  }

  galleryImages(): Array<NgxGalleryImage> {
    return this.imageUtil.galleryImages(this.source.images);
  }

  save() {
    this.sourceService.put('schoeller', this.source).subscribe(
      (data: ApiSource) => {
        this.source = data;
      }
    );
  }
}
