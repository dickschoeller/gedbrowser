import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxGalleryOptions, NgxGalleryImage } from 'ngx-gallery';
import { SelectItem } from 'primeng/api';

import { AttributeListComponent, AttributeDialogData } from '../../components';
import { ApiHead, ApiAttribute } from '../../models';
import { HeadService } from '../../services';
import { ImageUtil } from '../../utils';
import { HasAttributeList } from '../../interfaces';

@Component({
  selector: 'app-head',
  templateUrl: './head.component.html',
  styleUrls: ['./head.component.css']
})
export class HeadComponent implements OnInit, HasAttributeList {
  dataset: string;
  head: ApiHead;
  imageUtil = new ImageUtil();
  galleryOptions = this.imageUtil.galleryOptions();
  _options: Array<SelectItem> = [
      {value: 'Character Set', label: 'Character Set'},
      {value: 'Copyright', label: 'Copyright'},
      {value: 'Date', label: 'Date'},
      {value: 'Destination', label: 'Destination'},
      {value: 'File', label: 'File'},
      {value: 'GEDCOM', label: 'GEDCOM'},
      {value: 'Languange', label: 'Language'},
      {value: 'Note', label: 'Note'},
      {value: 'Place', label: 'Place'},
      {value: 'Source', label: 'Source'},
      {value: 'Submissionlink', label: 'Submissionlink'},
      {value: 'Submitterlink', label: 'Submitterlink'},
    ];

  constructor(private route: ActivatedRoute,
    private headService: HeadService,
    private router: Router
  ) {}

  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.dataset = params['dataset'];
    });
    this.route.data.subscribe(
      (data: {dataset: string, head: ApiHead}) => {
        this.head = data.head;
      }
    );
  }

//  galleryImages(): Array<NgxGalleryImage> {
//    return this.imageUtil.galleryImages(this.head.images);
//  }

  save() {
    this.headService.put(this.dataset, this.head).subscribe(
      (data: ApiHead) => {
        this.head = data;
      }
    );
  }

  options(): Array<SelectItem> {
    return this._options;
  }

  defaultData(): any { // AttributeDialogData {
    return {
      insert: true, index: 0, type: 'Submitterlink', text: '', date: '',
      place: '', note: '', originalType: '', originalText: '',
      originalDate: '', originalPlace: '', originalNote: ''
    };
  }
}