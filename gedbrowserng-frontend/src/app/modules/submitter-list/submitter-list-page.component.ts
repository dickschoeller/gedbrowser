import { Component, OnInit, OnChanges } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { ApiSubmitter } from '../../models';
import { SubmitterService } from '../../services';
import { ApiComparators } from '../../utils';

@Component({
  selector: 'app-submitter-list-page',
  templateUrl: './submitter-list-page.component.html',
  styleUrls: ['./submitter-list-page.component.css']
})
export class SubmitterListPageComponent implements OnInit, OnChanges {
  dataset: string;
  submitters: Array<ApiSubmitter>;

  constructor(private route: ActivatedRoute,
    private submitterService: SubmitterService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.init();
  }

  ngOnChanges(): void {
    this.init();
  }

  init() {
    this.route.params.subscribe((params) => {
      this.dataset = params['dataset'];
    });
    this.route.data.subscribe(
      (data: {submitters: ApiSubmitter[]}) => {
        this.submitters = data.submitters;
        this.submitters.sort(ApiComparators.compareSubmitters);
      }
    );
  }

  refreshSubmitter(submitter: ApiSubmitter): void {
    this.router.routeReuseStrategy.shouldReuseRoute = function() {
      return false;
    };

    const currentUrl = this.router.url + '?';

    this.submitterService.getAll(this.dataset).subscribe(
      (submitters: Array<ApiSubmitter>) => {
        this.submitters = submitters.sort(ApiComparators.compareSubmitters);
      }
    );
  }
}
