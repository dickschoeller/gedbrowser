import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

import {
  ApiSubmitter,
  AttributeListComponent,
  SubmitterService,
 } from '../shared';

@Component({
  selector: 'app-submitter',
  templateUrl: './submitter.component.html',
  styleUrls: ['./submitter.component.css']
})
export class SubmitterComponent implements OnInit {
  submitter: ApiSubmitter;

  constructor(private route: ActivatedRoute,
    private submitterService: SubmitterService,
    private router: Router
  ) { }

  ngOnInit() {
    this.route.data.subscribe(
      (data: {submitter: ApiSubmitter}) => {
        this.submitter = data.submitter;
      }
    );
  }
}
