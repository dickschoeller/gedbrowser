import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {NgxGalleryOptions, NgxGalleryImage} from 'ngx-gallery';

import {AttributeListComponent} from '../shared/components';
import {AttributeDialogHelper} from '../shared/components/attribute-dialog-helper';
import {ApiPerson} from '../shared/models';
import {PersonService} from '../shared/services';
import {LifespanUtil, ImageUtil} from '../shared/util';

/**
 * Implements a person page.
 *
 * Fetches:
 *  person: the person routed by the module
 */
@Component({
  selector: 'app-person',
  templateUrl: './person.component.html',
  styleUrls: ['./person.component.css']
})
export class PersonComponent implements OnInit {
  person: ApiPerson;
  imageUtil = new ImageUtil();
  galleryOptions = this.imageUtil.galleryOptions();
  attributeDialogHelper: AttributeDialogHelper = new AttributeDialogHelper(this);

  constructor(private route: ActivatedRoute,
    private service: PersonService,
    private router: Router
  ) {}

  ngOnInit() {
    this.route.data.subscribe(
      (data: {person: ApiPerson}) => {
        this.person = data.person;
      }
    );
  }

  lifespanDateString() {
    return new LifespanUtil(this.person.lifespan).lifespanDateString();
  }

  galleryImages(): Array<NgxGalleryImage> {
    return this.imageUtil.galleryImages(this.person.images);
  }

  save() {
    this.service.put('schoeller', this.person).subscribe(
      (data: ApiPerson) => {
        this.person = data;
      }
    );
  }
}
