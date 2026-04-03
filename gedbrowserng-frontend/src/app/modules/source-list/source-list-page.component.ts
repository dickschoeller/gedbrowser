import { RefreshSource } from '../../interfaces';
import { Component, OnInit, OnChanges , Inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ApiSource } from '../../models';
import { SourceService } from '../../services';
import { ApiComparators } from '../../utils';
import { SourceListComponent } from './source-list.component';

@Component({
    selector: 'app-source-list-page',
    template: `<app-source-list [parent]="this" [dataset]="dataset" [sources]="sources"></app-source-list>`,
    styles: [],
    imports: [SourceListComponent]
})
export class SourceListPageComponent implements OnInit, OnChanges, RefreshSource {
  dataset: string;
  sources: Array<ApiSource>;

  constructor(@Inject(ActivatedRoute) private readonly route: ActivatedRoute,
    @Inject(SourceService) private readonly sourceService: SourceService) { }

  ngOnInit(): void {
    this.init();
  }

  ngOnChanges(): void {
    this.init();
  }

  private init(): void {
    this.route.params.subscribe((params) => {
      this.dataset = params['dataset'];
    });
    this.route.data.subscribe(
      (data: {dataset: string, sources: ApiSource[]}) => {
        this.sources = data.sources;
        this.sources = this.sources.toSorted(ApiComparators.compareSources);
      }
    );
  }

  refreshSource(source: ApiSource): void {
    this.sourceService.getAll(this.dataset).subscribe(
      (sources: Array<ApiSource>) => {
        this.sources = sources.toSorted(ApiComparators.compareSources);
      }
    );
  }
}
