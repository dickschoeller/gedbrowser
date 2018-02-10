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
  }

  lifespanDateString() {
    return new LifespanUtil(this.person.lifespan).lifespanDateString();
  }

  famsAttributes(): Array<ApiAttribute> {
    const fams: Array<ApiAttribute> = new Array<ApiAttribute>();
    for (const attribute of this.person.attributes) {
      if (attribute.type === 'fams') {
        fams.push(attribute);
      }
    }
    return fams;
  }

  famcAttributes(): Array<ApiAttribute> {
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
  strippedAttributes(): Array<ApiAttribute> {
    const stripped: Array<ApiAttribute> = new Array<ApiAttribute>();
    for (const attribute of this.person.attributes) {
      if (attribute.type !== 'fams' && attribute.type !== 'famc'
        && !this.imageUtil.isImageWrapper(attribute)) {
        stripped.push(attribute);
      }
    }
    return stripped;
  }

  imageAttributes(): Array<ApiAttribute> {
    return this.imageUtil.imageAttributes(this.person.attributes);
  }

  galleryImages(): Array<NgxGalleryImage> {
    return this.imageUtil.galleryImages(this.person.attributes);
  }
}
