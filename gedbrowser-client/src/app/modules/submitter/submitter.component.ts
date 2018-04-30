import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {SelectItem} from 'primeng/api';

import {AttributeListComponent, AttributeDialogData} from '../../components';
import {ApiSubmitter} from '../../models';
import {SubmitterService} from '../../services';
import {HasAttributeList} from '../../interfaces';

@Component({
  selector: 'app-submitter',
  templateUrl: './submitter.component.html',
  styleUrls: ['./submitter.component.css']
})
export class SubmitterComponent implements OnInit, HasAttributeList {
  submitter: ApiSubmitter;
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
    this.route.data.subscribe(
      (data: {submitter: ApiSubmitter}) => {
        this.submitter = data.submitter;
      }
    );
  }

  save() {
    this.service.put('schoeller', this.submitter).subscribe(
      (data: ApiSubmitter) => {
        this.submitter = data;
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
