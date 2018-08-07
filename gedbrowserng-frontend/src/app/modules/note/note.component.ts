import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SelectItem } from 'primeng/api';

import { AttributeListComponent } from '../../components';
import { ApiNote, ApiAttribute, AttributeDialogData } from '../../models';
import { NoteService } from '../../services';
import { HasAttributeList } from '../../interfaces';
import { StringUtil, AttributeDialogHelper } from '../../utils';

@Component({
  selector: 'app-note',
  templateUrl: './note.component.html',
  styleUrls: ['./note.component.css']
})
export class NoteComponent implements OnInit, HasAttributeList {
  dataset: string;
  note: ApiNote;
  attributes: Array<ApiAttribute>;
  _options: Array<SelectItem> = [
      {value: 'Sourcelink', label: 'Source Link'},
    ];

  constructor(private route: ActivatedRoute,
    private service: NoteService,
    private router: Router
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
    return AttributeDialogHelper.dialogData('Sourcelink');
  }

  truncateNote(length = 80): string {
    return StringUtil.truncate(this.note.tail, length);
  }
}
