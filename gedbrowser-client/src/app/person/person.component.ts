import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {NgxGalleryOptions, NgxGalleryImage, NgxGalleryAnimation,
  NgxGalleryImageSize, NgxGalleryComponent, NgxGalleryLayout,
  NgxGalleryOrder} from 'ngx-gallery';

import {
  ApiAttribute,
  ApiPerson,
  AttributeListComponent,
  LifespanUtil,
  PersonService,
  ImageUtil,
} from '../shared';

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
  galleryOptions: Array<NgxGalleryOptions>;
  famsAttributes: Array<ApiAttribute>;
  famcAttributes: Array<ApiAttribute>;
  imageAttributes: Array<ApiAttribute>;
  strippedAttributes: Array<ApiAttribute>;

  constructor(private route: ActivatedRoute,
    private personService: PersonService,
    private router: Router
  ) {}

  ngOnInit() {
    this.route.data.subscribe(
      (data: {person: ApiPerson}) => {
        this.person = data.person;
      }
    );
    this.imageUtil = new ImageUtil();
    this.galleryOptions = this.imageUtil.galleryOptions();
    this.famsAttributes = this.createFamsAttributes();
    this.famcAttributes = this.createFamcAttributes();
    this.imageAttributes = this.createImageAttributes();
    this.strippedAttributes = this.createStrippedAttributes();
  }

  lifespanDateString() {
    return new LifespanUtil(this.person.lifespan).lifespanDateString();
  }

  createFamsAttributes(): Array<ApiAttribute> {
    const fams: Array<ApiAttribute> = new Array<ApiAttribute>();
    for (const attribute of this.person.attributes) {
      if (attribute.type === 'fams') {
        fams.push(attribute);
      }
    }
    return fams;
  }

  createFamcAttributes(): Array<ApiAttribute> {
    const fams: Array<ApiAttribute> = new Array<ApiAttribute>();
    for (const attribute of this.person.attributes) {
      if (attribute.type === 'famc') {
        fams.push(attribute);
      }
    }
    return fams;
  }

  /**
   * Remove family links and images.
   * Those will be handled elsewhere.
   */
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
    this.person.attributes = new Array<ApiAttribute>();
    this.person.attributes.concat(this.strippedAttributes);
    this.person.attributes.concat(this.imageAttributes);
    this.person.attributes.concat(this.famcAttributes);
    this.person.attributes.concat(this.famsAttributes);
    this.personService.put('schoeller', this.person);
  }
}
