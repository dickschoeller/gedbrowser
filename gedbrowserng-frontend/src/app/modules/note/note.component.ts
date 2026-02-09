import { Component, OnInit , Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { ApiNote, ApiAttribute, AttributeDialogData, SelectItem } from '../../models';
import { NoteService } from '../../services';
import { HasAttributeList } from '../../interfaces';
import { StringUtil, AttributeDialogHelper } from '../../utils';
import { MainLayoutComponent } from '../../components/main-layout/main-layout.component';
import { MatCard, MatCardTitle, MatCardSubtitle, MatCardContent } from '@angular/material/card';
import { MatIcon } from '@angular/material/icon';
import { AttributeListComponent } from '../../components/attribute-list/attribute-list.component';

@Component({
    selector: 'app-note',
    standalone: true,
    template: `<app-main-layout [dataset]="dataset">
  <mat-card>
    <mat-card-title><mat-icon inline=true>comment</mat-icon> {{ truncateNote(70) }}</mat-card-title>
    <mat-card-subtitle>{{ note?.string }}</mat-card-subtitle>
    <mat-card-content>
      <p class="multi_lines_text">{{ note?.tail }}</p>
      <app-attribute-list [dataset]="dataset" [attributes]="note?.attributes" [parent]="this" [showAdd]="false" [showNotes]="false" [showSubmitters]="false"></app-attribute-list>
    </mat-card-content>
  </mat-card>
</app-main-layout>`,
    styles: [`
button[icon="fa-angle-up"] {
  display: none !important;
}

button[icon="fa-angle-down"] {
  display: none !important;
}

button[icon="fa-angle-double-up"] {
    display: none !important;
}

button[ng-reflect-icon="fa-angle-double-up"] {
    display: none !important;
}

div.ui-orderlist-controls {
    display: none !important;
}

.mat-card-footer {
  margin: 24px;
}

mat-card-title {
  padding-left: 10px;
  padding-top: 10px;
  padding-right: 0;
  padding-bottom: 0;
}

mat-card-title mat-icon {
  margin-left: 10px;
  margin-right: 10px;
}

mat-card-subtitle {
  padding-left: 60px;
  padding-bottom: 10px;
  padding-right: 0;
  padding-top: 0;
}

.mat-icon {
    vertical-align: top;
    font-size: 1.25em;
}
    `],
    imports: [MainLayoutComponent, MatCard, MatCardTitle, MatIcon, MatCardSubtitle, MatCardContent, AttributeListComponent]
})
export class NoteComponent implements OnInit, HasAttributeList {
  dataset: string;
  note: ApiNote;
  attributes: Array<ApiAttribute>;
  _options: Array<SelectItem> = [
      {value: 'sourcelink', label: 'Source Link'},
    ];

  constructor(@Inject(ActivatedRoute) private readonly route: ActivatedRoute,
    @Inject(NoteService) private readonly service: NoteService,
    @Inject(Router) private readonly router: Router
  ) { }

  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.dataset = params['dataset'];
    });
    this.route.data.subscribe(
      (data: {dataset: string, note: ApiNote}) => {
        this.note = data.note;
        this.attributes = this.note.attributes;
      }
    );
  }

  save() {
    this.service.put(this.dataset, this.note).subscribe(
      (data: ApiNote) => {
        this.note = data;
      }
    );
  }

  options(): Array<SelectItem> {
    return this._options;
  }

  defaultData(): AttributeDialogData {
    return AttributeDialogHelper.dialogData('sourcelink');
  }

  truncateNote(length = 80): string {
    return StringUtil.truncate(this.note.tail, length);
  }
}
