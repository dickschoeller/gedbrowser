import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { AttributeListComponent } from '../../components';
import { ApiSubmitter, ApiAttribute, AttributeDialogData, SelectItem } from '../../models';
import { SubmitterService } from '../../services';
import { HasAttributeList } from '../../interfaces';
import { AttributeDialogHelper } from '../../utils';

@Component({
  standalone: false,
  selector: 'app-submitter',
  template: `<app-main-layout [dataset]="dataset">
  <mat-card>
    <mat-card-title><mat-icon>contact_mail</mat-icon> {{ submitter?.name }}
    </mat-card-title>
    <mat-card-subtitle>{{ submitter?.string }}</mat-card-subtitle>
    <mat-card-content>
      <app-attribute-list [dataset]="dataset" [attributes]="submitter?.attributes" [parent]="this" [showSources]=false [showSubmitters]=false></app-attribute-list>
    </mat-card-content>
  </mat-card>
</app-main-layout>`,
    styles: []
})
export class SubmitterComponent implements OnInit, HasAttributeList {
  dataset: string;
  submitter: ApiSubmitter;
  attributes: Array<ApiAttribute>;
  _options: Array<SelectItem> = [
      {value: 'Address', label: 'Address'},
      {value: 'Email', label: 'Email'},
      {value: 'Fax', label: 'Fax'},
      {value: 'Language', label: 'Language'},
      {value: 'Multimedia', label: 'Multimedia'},
      {value: 'Name', label: 'Name'},
      {value: 'Note', label: 'Note'},
      {value: 'Phone', label: 'Phone'},
      {value: 'Reference', label: 'Reference'},
      {value: 'Web', label: 'Web'},
    ];

  constructor(private route: ActivatedRoute,
    private service: SubmitterService,
    private router: Router
  ) { }

  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.dataset = params['dataset'];
    });
    this.route.data.subscribe(
      (data: {dataset: string, submitter: ApiSubmitter}) => {
        this.submitter = data.submitter;
        this.attributes = this.submitter.attributes;
      }
    );
  }

  save() {
    this.service.put(this.dataset, this.submitter).subscribe(
      (data: ApiSubmitter) => {
        this.submitter = data;
      }
    );
  }

  options(): Array<SelectItem> {
    return this._options;
  }

  defaultData(): AttributeDialogData {
    return AttributeDialogHelper.dialogData('Name');
  }
}
