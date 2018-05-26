import {Component, OnInit, OnChanges} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

import {ApiSource} from '../../models';
import {SourceService} from '../../services';

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
        this.sources = data.sources;
        this.sources.sort(this.compare);
      }
    );
  }

  /**
   * Comparison function to sort the persons returned.
   * Index name is the first level sort.
   * If those are the same, then by ID.
   */
  compare = function(a: ApiSource, b: ApiSource) {
    const val = a.title.localeCompare(b.title);
    if (val !== 0) {
      return val;
    }
    return a.string.localeCompare(b.string);
  };

  refreshSource(): void {
    this.router.routeReuseStrategy.shouldReuseRoute = function() {
      return false;
    };

    const currentUrl = this.router.url + '?';

    this.sourceService.getAll(this.dataset).subscribe(
      (sources: Array<ApiSource>) => {
        this.sources = sources.sort(this.compare);
        alert('there are ' + this.sources.length + ' sources');
      }
    );
  }
}
