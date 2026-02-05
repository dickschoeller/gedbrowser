import { Component, OnInit , Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { AttributeListComponent } from '../../components';
import { ApiHead, ApiAttribute, AttributeDialogData, SelectItem } from '../../models';
import { HeadService } from '../../services';
import { AttributeDialogHelper } from '../../utils';
import { HasAttributeList } from '../../interfaces';
import { MainLayoutComponent } from '../../components/main-layout/main-layout.component';
import { AttributeListComponent as AttributeListComponent_1 } from '../../components/attribute-list/attribute-list.component';

@Component({
    selector: 'app-head',
    template: `<app-main-layout [dataset]="dataset">
  <app-attribute-list [dataset]="dataset" [attributes]="head?.attributes" [parent]="this"></app-attribute-list>
</app-main-layout>`,
    styles: [],
    imports: [MainLayoutComponent, AttributeListComponent_1]
})
export class HeadComponent implements OnInit, HasAttributeList {
  dataset: string;
  head: ApiHead;
  attributes: Array<ApiAttribute>;
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

  constructor(@Inject(ActivatedRoute) @Inject(ActivatedRoute) @Inject(ActivatedRoute) @Inject(ActivatedRoute) private route: ActivatedRoute,
    @Inject(HeadService) @Inject(HeadService) @Inject(HeadService) private headService: HeadService,
    @Inject(Router) @Inject(Router) @Inject(Router) private router: Router
  ) {}

  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.dataset = params['dataset'];
    });
    this.route.data.subscribe(
      (data: {dataset: string, head: ApiHead}) => {
        this.head = data.head;
        this.attributes = this.head.attributes;
      }
    );
  }

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

  defaultData(): AttributeDialogData {
    return AttributeDialogHelper.dialogData('Note');
  }
}
