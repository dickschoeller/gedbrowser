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
  galleryOptions: Array<NgxGalleryOptions>;
  constructor(private route: ActivatedRoute,
    private personService: PersonService,
    private router: Router,
  ) {}

  ngOnInit() {
    this.route.data.subscribe(
      (data: {person: ApiPerson}) => {
        this.person = data.person;
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
        && !new ImageUtil(attribute).isImageWrapper()) {
        stripped.push(attribute);
      }
    }
    return stripped;
  }

  imageAttributes(): Array<ApiAttribute> {
    const images: Array<ApiAttribute> = new Array<ApiAttribute>();
    for (const attribute of this.person.attributes) {
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
