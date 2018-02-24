import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {NgxGalleryOptions, NgxGalleryImage} from 'ngx-gallery';

import {
  ApiAttribute,
  ApiPerson,
  AttributeListComponent,
  LifespanUtil,
  PersonService,
  ImageUtil,
} from '../shared';
import { AttributeDialogHelper } from '../shared/components/attribute-dialog-helper';

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
  imageUtil: ImageUtil;
  galleryOptions: Array<NgxGalleryOptions> = new Array<NgxGalleryOptions>();
  famsAttributes: Array<ApiAttribute> = new Array<ApiAttribute>();
  famcAttributes: Array<ApiAttribute> = new Array<ApiAttribute>();
  imageAttributes: Array<ApiAttribute> = new Array<ApiAttribute>();
  strippedAttributes: Array<ApiAttribute> = new Array<ApiAttribute>();
  attributeDialogHelper: AttributeDialogHelper = new AttributeDialogHelper(this);

  constructor(private route: ActivatedRoute,
    private service: PersonService,
    private router: Router
  ) {}

  ngOnInit() {
    this.route.data.subscribe(
      (data: {person: ApiPerson}) => {
        this.person = data.person;
        this.initLists();
      }
    );
  }

  initLists() {
    this.imageUtil = new ImageUtil();
    this.galleryOptions = this.imageUtil.galleryOptions();
    this.famsAttributes = this.createAttributeListOfType('fams');
    this.famcAttributes = this.createAttributeListOfType('famc');
    this.imageAttributes = this.createImageAttributes();
    this.strippedAttributes = this.createStrippedAttributes();
  }

  lifespanDateString() {
    return new LifespanUtil(this.person.lifespan).lifespanDateString();
  }

  createAttributeListOfType(type: string) {
    const fams: Array<ApiAttribute> = new Array<ApiAttribute>();
    for (const attribute of this.person.attributes) {
      if (attribute.type === type) {
        fams.push(attribute);
      }
    }
    return fams;
  }

  createStrippedAttributes(): Array<ApiAttribute> {
    const stripped: Array<ApiAttribute> = new Array<ApiAttribute>();
    for (const attribute of this.person.attributes) {
      if (attribute.type !== 'fams' && attribute.type !== 'famc'
        && !this.imageUtil.isImageWrapper(attribute)) {
        stripped.push(attribute);
      }
    }
    return stripped;
  }

  createImageAttributes(): Array<ApiAttribute> {
    return this.imageUtil.imageAttributes(this.person.attributes);
  }

  galleryImages(): Array<NgxGalleryImage> {
    return this.imageUtil.galleryImages(this.person.attributes);
  }

  save() {
    this.person.attributes = new Array<ApiAttribute>()
      .concat(this.strippedAttributes)
      .concat(this.imageAttributes)
      .concat(this.famcAttributes)
      .concat(this.famsAttributes);
    this.service.put('schoeller', this.person).subscribe(
      (data: ApiPerson) => {
        this.person = data;
        this.initLists();
      }
    );
  }
}
