import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { AttributeListComponent } from '../../components';
import { ApiSource, ApiAttribute, AttributeDialogData, SelectItem } from '../../models';
import { SourceService } from '../../services';
import { AttributeDialogHelper } from '../../utils';
import { HasAttributeList } from '../../interfaces';

@Component({
  selector: 'app-source',
  templateUrl: './source.component.html',
  styleUrls: ['./source.component.css']
})
export class SourceComponent implements OnInit, HasAttributeList {
  dataset: string;
  source: ApiSource;
  attributes: Array<ApiAttribute>;
  _options: Array<SelectItem> = [
      {value: 'Abbreviation', label: 'Abbreviation'},
      {value: 'Address', label: 'Address'},
      {value: 'Address1', label: 'Address1'},
      {value: 'Address2', label: 'Address2'},
      {value: 'Adoption', label: 'Adoption'},
      {value: 'Adult Baptism', label: 'Adult Baptism'},
      {value: 'Adult Christening', label: 'Adult Christening'},
      {value: 'Age', label: 'Age'},
      {value: 'Agency', label: 'Agency'},
      {value: 'Alias', label: 'Alias'},
      {value: 'Ancestor Interest', label: 'Ancestor Interest'},
      {value: 'Ancestors', label: 'Ancestors'},
      {value: 'Ancestral File Number', label: 'Ancestral File Number'},
      {value: 'Annulment', label: 'Annulment'},
      {value: 'Associates', label: 'Associates'},
      {value: 'Author', label: 'Author'},
      {value: 'Baptism', label: 'Baptism'},
      {value: 'Baptism LDS', label: 'Baptism LDS'},
      {value: 'Bar Mitzvah', label: 'Bar Mitzvah'},
      {value: 'Bas Mitzvah', label: 'Bas Mitzvah'},
      {value: 'Birth', label: 'Birth'},
      {value: 'Blessing', label: 'Blessing'},
      {value: 'Burial', label: 'Burial'},
      {value: 'Call Number', label: 'Call Number'},
      {value: 'Caste', label: 'Caste'},
      {value: 'Cause', label: 'Cause'},
      {value: 'Census', label: 'Census'},
      {value: 'Character', label: 'Character'},
      {value: 'Children Count', label: 'Children Count'},
      {value: 'Christening', label: 'Christening'},
      {value: 'City', label: 'City'},
      {value: 'Confirmation', label: 'Confirmation'},
      {value: 'Confirmation LDS', label: 'Confirmation LDS'},
      {value: 'Copyright', label: 'Copyright'},
      {value: 'Corporate', label: 'Corporate'},
      {value: 'Cremation', label: 'Cremation'},
      {value: 'Country', label: 'Country'},
      {value: 'Data', label: 'Data'},
      {value: 'Date', label: 'Date'},
      {value: 'Death', label: 'Death'},
      {value: 'Descendant Interest', label: 'Descendant Interest'},
      {value: 'Descendants', label: 'Descendants'},
      {value: 'Destination', label: 'Destination'},
      {value: 'Divorce', label: 'Divorce'},
      {value: 'Divorce Filed', label: 'Divorce Filed'},
      {value: 'Education', label: 'Education'},
      {value: 'Email', label: 'Email'},
      {value: 'Emigration', label: 'Emigration'},
      {value: 'Endowment', label: 'Endowment'},
      {value: 'Engagement', label: 'Engagement'},
      {value: 'Event', label: 'Event'},
      {value: 'Fact', label: 'Fact'},
      {value: 'Family File', label: 'Family File'},
      {value: 'Fax', label: 'Fax'},
      {value: 'First Communion', label: 'First Communion'},
      {value: 'File', label: 'File'},
      {value: 'Format', label: 'Format'},
      {value: 'GEDCOM', label: 'GEDCOM'},
      {value: 'Given Name', label: 'Given Name'},
      {value: 'Graduation', label: 'Graduation'},
      {value: 'ID Number', label: 'ID Number'},
      {value: 'Immigration', label: 'Immigration'},
      {value: 'Language', label: 'Language'},
      {value: 'Latitude', label: 'Latitude'},
      {value: 'Legatee', label: 'Legatee'},
      {value: 'Longitude', label: 'Longitude'},
      {value: 'Map', label: 'Map'},
      {value: 'Marriage', label: 'Marriage'},
      {value: 'Marriage Bann', label: 'Marriage Bann'},
      {value: 'Marriage Contract', label: 'Marriage Contract'},
      {value: 'Marriage Count', label: 'Marriage Count'},
      {value: 'Marriage License', label: 'Marriage License'},
      {value: 'Marriage Settlement', label: 'Marriage Settlement'},
      {value: 'Media', label: 'Media'},
      {value: 'Name', label: 'Name'},
      {value: 'Name Prefix', label: 'Name Prefix'},
      {value: 'Name Suffix', label: 'Name Suffix'},
      {value: 'Nationality', label: 'Nationality'},
      {value: 'Naturalization', label: 'Naturalization'},
      {value: 'Nickname', label: 'Nickname'},
      {value: 'Note', label: 'Note'},
      {value: 'Occupation', label: 'Occupation'},
      {value: 'Ordinance', label: 'Ordinance'},
      {value: 'Ordination', label: 'Ordination'},
      {value: 'Page', label: 'Page'},
      {value: 'Pedigree', label: 'Pedigree'},
      {value: 'Phone', label: 'Phone'},
      {value: 'Phonetic', label: 'Phonetic'},
      {value: 'Physical Description', label: 'Physical Description'},
      {value: 'Place', label: 'Place'},
      {value: 'Postal Code', label: 'Postal Code'},
      {value: 'Probate', label: 'Probate'},
      {value: 'Property', label: 'Property'},
      {value: 'Publication', label: 'Publication'},
      {value: 'Quality of Data', label: 'Quality of Data'},
      {value: 'Reference', label: 'Reference'},
      {value: 'Relationship', label: 'Relationship'},
      {value: 'Religion', label: 'Religion'},
      {value: 'Repository', label: 'Repository'},
      {value: 'Residence', label: 'Residence'},
      {value: 'Restriction', label: 'Restriction'},
      {value: 'Retirement', label: 'Retirement'},
      {value: 'Record ID Number', label: 'Record ID Number'},
      {value: 'Role', label: 'Role'},
      {value: 'Romanized', label: 'Romanized'},
      {value: 'Sex', label: 'Sex'},
      {value: 'Sealing Child', label: 'Sealing Child'},
      {value: 'Sealing Spouse', label: 'Sealing Spouse'},
      {value: 'Source', label: 'Source'},
      {value: 'Surname Prefix', label: 'Surname Prefix'},
      {value: 'Social Security Number', label: 'Social Security Number'},
      {value: 'State', label: 'State'},
      {value: 'Status', label: 'Status'},
      {value: 'Surname', label: 'Surname'},
      {value: 'Temple', label: 'Temple'},
      {value: 'Text', label: 'Text'},
      {value: 'Time', label: 'Time'},
      {value: 'Title', label: 'Title'},
      {value: 'Type', label: 'Type'},
      {value: 'Version', label: 'Version'},
      {value: 'Web', label: 'Web'},
      {value: 'Will', label: 'Will'},
    ];

  constructor(private route: ActivatedRoute,
    private sourceService: SourceService,
    private router: Router
  ) {}

  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.dataset = params['dataset'];
    });
    this.route.data.subscribe(
      (data: {dataset: string, source: ApiSource}) => {
        this.source = data.source;
        this.attributes = this.source.attributes;
      }
    );
  }

  save() {
    this.sourceService.put(this.dataset, this.source).subscribe(
      (data: ApiSource) => {
        this.source = data;
      }
    );
  }

  options(): Array<SelectItem> {
    return this._options;
  }

  defaultData(): AttributeDialogData {
    return AttributeDialogHelper.dialogData('Title');
  }
}
