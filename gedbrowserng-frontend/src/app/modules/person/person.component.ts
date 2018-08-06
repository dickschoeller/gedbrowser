import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxGalleryOptions, NgxGalleryImage } from 'ngx-gallery';
import { SelectItem } from 'primeng/api';

import { ApiPerson, ApiAttribute, AttributeDialogData } from '../../models';
import { PersonService } from '../../services';
import { AttributeDialogHelper, LifespanUtil, ImageUtil } from '../../utils';
import { HasAttributeList } from '../../interfaces';

import { AttributeListComponent } from '../../components/attribute-list';
import { HasPerson, Saveable } from '../../interfaces';

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
export class PersonComponent implements OnInit, HasAttributeList, HasPerson, Saveable {
  dataset: string;
  person: ApiPerson;
  attributes: Array<ApiAttribute>;
  imageUtil = new ImageUtil();
  galleryOptions = this.imageUtil.galleryOptions();
  attributeDialogHelper: AttributeDialogHelper = new AttributeDialogHelper(this);
  _options: Array<SelectItem> = [
      {value: 'Adoption', label: 'Adoption'},
      {value: 'Adult Baptism', label: 'Adult Baptism'},
      {value: 'Adult Christening', label: 'Adult Christening'},
      {value: 'Age', label: 'Age'},
      {value: 'Alias', label: 'Alias'},
      {value: 'Ancestor Interest', label: 'Ancestor Interest'},
      {value: 'Ancestors', label: 'Ancestors'},
      {value: 'Associates', label: 'Associates'},
      {value: 'Baptism', label: 'Baptism'},
      {value: 'Baptism LDS', label: 'Baptism LDS'},
      {value: 'Bar Mitzvah', label: 'Bar Mitzvah'},
      {value: 'Bas Mitzvah', label: 'Bas Mitzvah'},
      {value: 'Birth', label: 'Birth'},
      {value: 'Blessing', label: 'Blessing'},
      {value: 'Burial', label: 'Burial'},
      {value: 'Caste', label: 'Caste'},
      {value: 'Census', label: 'Census'},
      {value: 'Children Count', label: 'Children Count'},
      {value: 'Christening', label: 'Christening'},
      {value: 'Confirmation', label: 'Confirmation'},
      {value: 'Confirmation LDS', label: 'Confirmation LDS'},
      {value: 'Cremation', label: 'Cremation'},
      {value: 'Death', label: 'Death'},
      {value: 'Descendant Interest', label: 'Descendant Interest'},
      {value: 'Descendants', label: 'Descendants'},
      {value: 'Education', label: 'Education'},
      {value: 'Email', label: 'Email'},
      {value: 'Emigration', label: 'Emigration'},
      {value: 'Endowment', label: 'Endowment'},
      {value: 'Event', label: 'Event'},
      {value: 'Fact', label: 'Fact'},
      {value: 'Fax', label: 'Fax'},
      {value: 'First Communion', label: 'First Communion'},
      {value: 'Given Name', label: 'Given Name'},
      {value: 'Graduation', label: 'Graduation'},
      {value: 'ID Number', label: 'ID Number'},
      {value: 'Immigration', label: 'Immigration'},
      {value: 'Language', label: 'Language'},
      {value: 'Name', label: 'Name'},
      {value: 'Name Prefix', label: 'Name Prefix'},
      {value: 'Name Suffix', label: 'Name Suffix'},
      {value: 'Nationality', label: 'Nationality'},
      {value: 'Naturalization', label: 'Naturalization'},
      {value: 'Nickname', label: 'Nickname'},
      {value: 'Marriage Count', label: 'Marriage Count'},
      {value: 'Note', label: 'Note'},
      {value: 'Multimedia', label: 'Multimedia'},
      {value: 'Occupation', label: 'Occupation'},
      {value: 'Ordinance', label: 'Ordinance'},
      {value: 'Ordination', label: 'Ordination'},
      {value: 'Pedigree', label: 'Pedigree'},
      {value: 'Phone', label: 'Phone'},
      {value: 'Physical Description', label: 'Physical Description'},
      {value: 'Probate', label: 'Probate'},
      {value: 'Publication', label: 'Publication'},
      {value: 'Quality of Data', label: 'Quality of Data'},
      {value: 'Reference', label: 'Reference'},
      {value: 'Relationship', label: 'Relationship'},
      {value: 'Religion', label: 'Religion'},
      {value: 'Residence', label: 'Residence'},
      {value: 'Restriction', label: 'Restriction'},
      {value: 'Retirement', label: 'Retirement'},
      {value: 'Sex', label: 'Sex'},
      {value: 'Source', label: 'Source'},
      {value: 'Surname Prefix', label: 'Surname Prefix'},
      {value: 'Social Security Number', label: 'Social Security Number'},
      {value: 'Status', label: 'Status'},
      {value: 'Surname', label: 'Surname'},
      {value: 'Text', label: 'Text'},
    ];

  constructor(private route: ActivatedRoute,
    private service: PersonService,
    private router: Router
  ) {}

  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.dataset = params['dataset'];
    });
    this.route.data.subscribe(
      (data: {dataset: string, person: ApiPerson}) => {
        this.person = data.person;
        this.attributes = this.person.attributes;
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
    this.service.put(this.dataset, this.person).subscribe(
      (data: ApiPerson) => {
        this.person = data;
      }
    );
  }

  options(): Array<SelectItem> {
    return this._options;
  }

  defaultData(): AttributeDialogData {
    return {
      insert: true, index: 0, type: 'Name', text: '', date: '',
      place: '', note: '', originalType: '', originalText: '',
      originalDate: '', originalPlace: '', originalNote: ''
    };
  }
}
