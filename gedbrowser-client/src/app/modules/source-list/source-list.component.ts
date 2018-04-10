import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

import {ApiSource} from '../../models';
import {SourceService} from '../../services';

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
    const val = a.title.localeCompare(b.title);
    if (val !== 0) {
      return val;
    }
    return a.string.localeCompare(b.string);
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
