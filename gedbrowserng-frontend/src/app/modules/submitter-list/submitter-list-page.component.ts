import { Component, OnInit, OnChanges , Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { ApiSubmitter } from '../../models';
import { SubmitterService } from '../../services';
import { ApiComparators } from '../../utils';
import { SubmitterListComponent } from './submitter-list.component';

@Component({
    selector: 'app-submitter-list-page',
    template: `<app-submitter-list [parent]="this" [dataset]="dataset" [submitters]="submitters"></app-submitter-list>`,
    styles: [],
    imports: [SubmitterListComponent]
})
export class SubmitterListPageComponent implements OnInit, OnChanges {
  dataset: string;
  submitters: Array<ApiSubmitter>;

  constructor(@Inject(ActivatedRoute) private route: ActivatedRoute,
    @Inject(SubmitterService) private submitterService: SubmitterService,
    @Inject(Router) private router: Router
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
