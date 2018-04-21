import {Component, Inject, Output, EventEmitter} from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';

import {ApiAttribute} from '../../models';

import {AttributeDialogData} from './attribute-dialog-data';

@Component({
  selector: 'app-attribute-dialog',
  templateUrl: './attribute-dialog.component.html',
  styleUrls: ['./attribute-dialog.component.css']
})
export class AttributeDialogComponent {
  @Output() onOK = new EventEmitter<AttributeDialogData>();
  options: Array<String> = [
      'Abbreviation',
      'Address',
      'Address1',
      'Address2',
      'Adoption',
      'Adult Baptism',
      'Adult Christening',
      'Age',
      'Agency',
      'Alias',
      'Ancestor Interest',
      'Ancestors',
      'Ancestral File Number',
      'Annulment',
      'Associates',
      'Author',
      'Baptism',
      'Baptism LDS',
      'Bar Mitzvah',
      'Bas Mitzvah',
      'Birth',
      'Blessing',
      'Burial',
      'Call Number',
      'Caste',
      'Cause',
      'Census',
      'Character',
      'Children Count',
      'Christening',
      'City',
      'Confirmation',
      'Confirmation LDS',
      'Copyright',
      'Corporate',
      'Cremation',
      'Country',
      'Data',
      'Date',
      'Death',
      'Descendant Interest',
      'Descendants',
      'Destination',
      'Divorce',
      'Divorce Filed',
      'Education',
      'Email',
      'Emigration',
      'Endowment',
      'Engagement',
      'Event',
      'Fact',
      'Family File',
      'Fax',
      'First Communion',
      'File',
      'Format',
      'GEDCOM',
      'Given Name',
      'Graduation',
      'ID Number',
      'Immigration',
      'Language',
      'Latitude',
      'Legatee',
      'Longitude',
      'Map',
      'Marriage',
      'Marriage Bann',
      'Marriage Contract',
      'Marriage License',
      'Marriage Settlement',
      'Media',
      'Name',
      'Name Prefix',
      'Name Suffix',
      'Nationality',
      'Naturalization',
      'Nickname',
      'Marriage Count',
      'Note',
      'Multimedia',
      'Occupation',
      'Ordinance',
      'Ordination',
      'Page',
      'Pedigree',
      'Phone',
      'Phonetic',
      'Physical Description',
      'Place',
      'Postal Code',
      'Probate',
      'Property',
      'Publication',
      'Quality of Data',
      'Reference',
      'Relationship',
      'Religion',
      'Repository',
      'Residence',
      'Restriction',
      'Retirement',
      'Record ID Number',
      'Role',
      'Romanized',
      'Sex',
      'Sealing Child',
      'Sealing Spouse',
      'Source',
      'Surname Prefix',
      'Social Security Number',
      'State',
      'Status',
      'Surname',
      'Temple',
      'Text',
      'Time',
      'Title',
      'Type',
      'Version',
      'Web',
      'Will',
  ];

  getOptions(): any {
    const ret: Array<any> = new Array<any>();
    for (const option of this.options) {
      ret.push({ label: option, value: option });
    }
    return ret;
  }

  constructor(public dialogRef: MatDialogRef<AttributeDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: AttributeDialogData) {}

  onClickOK() {
    this.onOK.emit(this.data);
  }

  close(): void {
    this.dialogRef.close();
  }
}
