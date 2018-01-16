import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

import {ApiSource, SourceService} from '../shared';

@Component({
  selector: 'app-source-list',
  templateUrl: './source-list.component.html',
  styleUrls: ['./source-list.component.css']
})
export class SourceListComponent implements OnInit {
  sources: ApiSource[];

  constructor(private route: ActivatedRoute,
    private sourceService: SourceService,
    private router: Router
  ) {}

  /**
   * Comparison function to sort the persons returned.
   * Index name is the first level sort.
   * If those are the same, then by ID.
   */
  compare = function(a: ApiSource, b: ApiSource) {
    if (a.title < b.title) {
      return -1;
    }
    if (a.title > b.title) {
      return 1;
    }
    if (a.string < b.string) {
      return -1;
    }
    if (a.string > b.string) {
      return 1;
    }
    return 0;
  };

  /**
   * Prepare the page display.
   *
   * Read sources from the web service and sort using our comparator.
   */
  ngOnInit() {
    this.route.data.subscribe(
      (data: {sources: ApiSource[]}) => {
        this.sources = data.sources;
        this.sources.sort(this.compare);
      }
    );
  }
}
