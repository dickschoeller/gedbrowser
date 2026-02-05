import { Component, OnInit , Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { AttributeListComponent } from '../../components';
import { ApiNote, ApiAttribute, AttributeDialogData, SelectItem } from '../../models';
import { NoteService } from '../../services';
import { HasAttributeList } from '../../interfaces';
import { StringUtil, AttributeDialogHelper } from '../../utils';
import { MainLayoutComponent } from '../../components/main-layout/main-layout.component';
import { MatCard, MatCardTitle, MatCardSubtitle, MatCardContent } from '@angular/material/card';
import { MatIcon } from '@angular/material/icon';
import { AttributeListComponent as AttributeListComponent_1 } from '../../components/attribute-list/attribute-list.component';

@Component({
    selector: 'app-note',
    template: `<app-main-layout [dataset]="dataset">
  <mat-card>
    <mat-card-title><mat-icon>comment</mat-icon> {{ truncateNote(70) }}</mat-card-title>
    <mat-card-subtitle>{{ note?.string }}</mat-card-subtitle>
    <mat-card-content>
      <p class="multi_lines_text">{{ note?.tail }}</p>
      <app-attribute-list [dataset]="dataset" [attributes]="note?.attributes" [parent]="this" [showAdd]="false" [showNotes]="false" [showSubmitters]="false"></app-attribute-list>
    </mat-card-content>
  </mat-card>
</app-main-layout>`,
    styles: [],
    imports: [MainLayoutComponent, MatCard, MatCardTitle, MatIcon, MatCardSubtitle, MatCardContent, AttributeListComponent_1]
})
export class NoteComponent implements OnInit, HasAttributeList {
  dataset: string;
  note: ApiNote;
  attributes: Array<ApiAttribute>;
  _options: Array<SelectItem> = [
      {value: 'sourcelink', label: 'Source Link'},
    ];

  constructor(@Inject(ActivatedRoute) private route: ActivatedRoute,
    @Inject(NoteService) private service: NoteService,
    @Inject(Router) private router: Router
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
