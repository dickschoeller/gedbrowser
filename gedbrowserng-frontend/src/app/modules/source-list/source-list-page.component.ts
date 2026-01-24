import { RefreshSource } from '../../interfaces';
import { Component, OnInit, OnChanges , Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { ApiSource } from '../../models';
import { SourceService } from '../../services';
import { ApiComparators } from '../../utils';

@Component({
  standalone: false,
  selector: 'app-source-list-page',
  template: `<app-source-list [parent]="this" [dataset]="dataset" [sources]="sources"></app-source-list>`,
    styles: []
})
export class SourceListPageComponent implements OnInit, OnChanges, RefreshSource {
  dataset: string;
  sources: Array<ApiSource>;

  constructor(@Inject(ActivatedRoute) @Inject(ActivatedRoute) @Inject(ActivatedRoute) @Inject(ActivatedRoute) private route: ActivatedRoute,
    @Inject(SourceService) @Inject(SourceService) @Inject(SourceService) private sourceService: SourceService,
    @Inject(Router) @Inject(Router) @Inject(Router) private router: Router) { }

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
        this.sources.sort(ApiComparators.compareSources);
      }
    );
  }

  refreshSource(source: ApiSource): void {
    this.router.routeReuseStrategy.shouldReuseRoute = function() {
      return false;
    };

    const currentUrl = this.router.url + '?';

    this.sourceService.getAll(this.dataset).subscribe(
      (sources: Array<ApiSource>) => {
        this.sources = sources.sort(ApiComparators.compareSources);
      }
    );
  }
}
