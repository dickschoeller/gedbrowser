import { Component, OnInit, OnChanges , Inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

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

  constructor(@Inject(ActivatedRoute) private readonly route: ActivatedRoute,
    @Inject(SubmitterService) private readonly submitterService: SubmitterService,
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
        this.submitters = this.submitters.toSorted(ApiComparators.compareSubmitters);
      }
    );
  }

  refreshSubmitter(submitter: ApiSubmitter): void {
    this.submitterService.getAll(this.dataset).subscribe(
      (submitters: Array<ApiSubmitter>) => {
        this.submitters = submitters.toSorted(ApiComparators.compareSubmitters);
      }
    );
  }
}
