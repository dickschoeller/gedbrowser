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
        <div class="card-header-line">
          <mat-card-title><mat-icon inline=true>comment</mat-icon> {{ truncateNote(70) }}</mat-card-title>
          <mat-card-subtitle>{{ note?.string }}</mat-card-subtitle>
        </div>
    <mat-card-content>
      <p class="multi_lines_text">{{ note?.tail }}</p>
      <div class="attributes-section">
        <app-attribute-list [dataset]="dataset" [attributes]="note?.attributes" [parent]="this" [showAdd]="false" [showNotes]="false" [showSubmitters]="false"></app-attribute-list>
      </div>
    </mat-card-content>
  </mat-card>
</app-main-layout>`,
    styles: [`
.mat-card-footer {
  margin: 24px;
}

mat-card-title {
  margin: 0;
  padding: 0;
}

mat-card-title mat-icon {
  margin-right: 10px;
}

mat-card-subtitle {
  align-self: center;
  margin: 0;
  padding: 0;
}

.mat-icon {
    vertical-align: top;
    font-size: 1.25em;
}

.attributes-section {
  margin-top: 10px;
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
