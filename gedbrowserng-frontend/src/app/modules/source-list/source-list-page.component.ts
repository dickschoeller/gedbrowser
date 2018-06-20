import { Component, OnInit, OnChanges } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { ApiSource } from '../../models';
import { SourceService } from '../../services';
import { ApiComparators } from '../../utils';

@Component({
  selector: 'app-source-list-page',
  templateUrl: './source-list-page.component.html',
  styleUrls: ['./source-list-page.component.css']
})
export class SourceListPageComponent implements OnInit, OnChanges {
  dataset: string;
  sources: Array<ApiSource>;

  constructor(private route: ActivatedRoute,
    private sourceService: SourceService,
    private router: Router) { }

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
        const comparator: ApiComparators = new ApiComparators();
        this.sources = data.sources;
        this.sources.sort(comparator.compareSources);
      }
    );
  }

  refreshSource(): void {
    this.router.routeReuseStrategy.shouldReuseRoute = function() {
      return false;
    };

    const currentUrl = this.router.url + '?';

    this.sourceService.getAll(this.dataset).subscribe(
      (sources: Array<ApiSource>) => {
        const comparator: ApiComparators = new ApiComparators();
        this.sources = sources.sort(comparator.compareSources);
      }
    );
  }
}
